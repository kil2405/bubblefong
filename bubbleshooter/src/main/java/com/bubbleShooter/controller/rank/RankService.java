package com.bubbleShooter.controller.rank;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bubbleShooter.VO.RankerVO;
import com.bubbleShooter.VO.RewardItemVO;
import com.bubbleShooter.VO.SeasonRewardVO;
import com.bubbleShooter.common.BubbleException;
import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.ErrorCodeInfo;
import com.bubbleShooter.common.GameResource;
import com.bubbleShooter.common.RepositoryService;
import com.bubbleShooter.common.StorageContext;
import com.bubbleShooter.controller.mail.MailService;
import com.bubbleShooter.domain.RankingRewardLog;
import com.bubbleShooter.domain.User;
import com.bubbleShooter.relation.MailTitle;
import com.bubbleShooter.resource.RankingRewardResource;
import com.bubbleShooter.resource.RankingTierResource;
import com.bubbleShooter.response.ResRankingUser;
import com.bubbleShooter.response.ResSeasonRefresh;
import com.bubbleShooter.util.MapperVO;
import com.bubbleShooter.util.TimeCalculation;
import com.google.gson.Gson;

@Service
public class RankService {
	@Autowired
	private StorageContext storageContext;
	
	@Autowired
	private GameResource gameResource;
	
	@Autowired
	private MapperVO mapperVO;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private MailService mailService;
	
	// Redis Keys
	private String rankDS_total = "rank_total_score_";
	
	public ResRankingUser getRankingUserList(int userId) throws Exception
	{
		ResRankingUser res = new ResRankingUser();

		User user = repositoryService.getUser(userId, false);
		if(user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_RANKING_1803);
		
		res.isSeason = true;
		res.season = gameResource.getRankingSeason().getSeason();
		if(res.season <= ConstantVal.DEFAULT_VALUE)
		{
			res.season = gameResource.getRankingSeason().getPreSeason();
			res.isSeason = false;
		}
		
		if(res.isSeason)
			res.seasonRemainTime = gameResource.getRankingSeason().get().getSeasonEnd() - TimeCalculation.getCurrentUnixTime();
		else
			res.seasonRemainTime = gameResource.getRankingSeason().getSeason(res.season + 1).getSeasonStart() - TimeCalculation.getCurrentUnixTime();
		
		res.rankingList = getUserRankers(0);
		res.userRecord = mapperVO.makeUserRankerVO(userId, null);
		res.rankingRewards = gameResource.getRankingReward().getRankingRewards(user.getRegion());
		res.tierRewards = gameResource.getRankingReward().getTierRewards(user.getRegion());
		
		if(res.userRecord.ranking <= ConstantVal.RANK_USER_MAX_RANKER)
		{
			int myRanking = findMyRanking(userId, res.rankingList);
			if(myRanking > ConstantVal.DEFAULT_VALUE)
				res.userRecord.ranking = myRanking;
			else
				res.userRecord.ranking = ConstantVal.DEFAULT_VALUE;
		}
		
		res.result = ConstantVal.DEFAULT_SUCCESS;
		return res;
	}
	
	public ResSeasonRefresh seasonRefresh(int userId) throws Exception
	{
		ResSeasonRefresh res = new ResSeasonRefresh();
		
		User user = repositoryService.getUser(userId, false);
		if(user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_RANKING_1803);
		
		//현재 시즌 얻어옴, 비시즌이면 -1
		int curSeason = gameResource.getRankingSeason().getSeason();
		
		//현재 동일 시즌이면, 그냥 패스
		if(user.getSeason() == curSeason)
		{
			res.result = ConstantVal.DEFAULT_FAIL;
			res.season = curSeason;
			res.newSeason = false;
			res.seasonRewards = null;
			res.userRecord = null;
			return res;
		}
		
		// 일반 유저만 보상을 지급 (봇 유저는 패스)
		if(user.getId() >= ConstantVal.NORMAL_USER_ID)
		{
			//여긴 시즌이 다르면 들어오는 곳.
			//보상 받았는지 여부 체크
			boolean isReward = isSeasonReward(userId, user);
			if(isReward)
			{
				res.result = ConstantVal.DEFAULT_FAIL;
				res.season = curSeason;
				res.newSeason = false;
				res.seasonRewards = null;
				res.userRecord = null;
			}
			else
			{
				res.result = ConstantVal.DEFAULT_SUCCESS;
				res.season = curSeason;
				res.newSeason = false;
				res.seasonRewards = SeasonReward(user, curSeason);
				res.userRecord = null;
			}
		}
		
		//새로운 시즌이 시작되었다.
		if(user.getSeason() != curSeason && curSeason != ConstantVal.DEFAULT_VALUE)
		{
			RankingTierResource myTierRS = gameResource.getRankingTier().get(curSeason, user.getTier());
			if(myTierRS == null)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_RANKING_1808);
			
			RankingTierResource initTier = gameResource.getRankingTier().get(curSeason, myTierRS.getRegulateTier());
			if(initTier == null)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_RANKING_1809);
			
			user.setSeason(curSeason);
			user.setTier(initTier.getRankingTier());
			user.setRankPoint(initTier.getRankingTierStandardPoint());
			if(user.getRankPoint() > 0)
				setUserRanking(user);
			
			repositoryService.setUser(userId, user);
			res.userRecord = mapperVO.makeUserRankerVO(userId, user);
			res.newSeason = true;
		}
		
		return res;
	}
	
	public SeasonRewardVO SeasonReward(User user, int curSeason) throws Exception
	{
		SeasonRewardVO res = new SeasonRewardVO();
		
		//동일한 시즌중에는 보상을 받을 수 없다. (비시즌일경우 -1)
		if(curSeason == user.getSeason())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_RANKING_1805);
		
		RankingRewardLog log = repositoryService.getRankingRewardLog(user.getId(), user.getSeason());
		if(log != null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_RANKING_1806);
		
		List<RewardItemVO> tierRewardVO = new ArrayList<>();
		List<RewardItemVO> rankingRewardVO = new ArrayList<>();
		
		//티어 보상 목록 만들기
		List<RankingRewardResource> tierRewardRS = gameResource.getRankingReward().get(user.getRegion(), user.getSeason(), ConstantVal.RANKING_REWARD_TYPE_TIER, user.getTier());
		if(tierRewardRS == null || tierRewardRS.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_RANKING_1807);
		
		for(RankingRewardResource tierReward : tierRewardRS)
			tierRewardVO.add(mapperVO.makeRewardItemVO(tierReward.getItemType(), tierReward.getItemId(), tierReward.getItemCount()));
		
		//랭킹 보상 만들기
		int myRanking = getUserRanking(user.getId(), user.getSeason());
		if(myRanking > ConstantVal.DEFAULT_ZERO)
		{
			List<RankingRewardResource> rankingRewardRS = gameResource.getRankingReward().get(user.getRegion(), user.getSeason(), ConstantVal.RANKING_REWARD_TYPE_RANKING, myRanking);
			if(rankingRewardRS.size() > ConstantVal.DEFAULT_ZERO)
			{
				for(RankingRewardResource rankingReward : rankingRewardRS)
					rankingRewardVO.add(mapperVO.makeRewardItemVO(rankingReward.getItemType(), rankingReward.getItemId(), rankingReward.getItemCount()));
			}
		}
		
		if(tierRewardVO.size() > ConstantVal.DEFAULT_ZERO)
		{
			//티어보상 메일보내기
			MailTitle mailRS = gameResource.getMail().get(ConstantVal.MAIL_TEXT_RANKING_TIER_REWARD, user.getLanguage());
			mailService.SendMails(user.getId(), ConstantVal.MAIL_TYPE_NORMAL, mailRS.title, mailRS.contents, TimeCalculation.getCurrentUnixTime() + ConstantVal.MAIL_EXPIRE_TIME, tierRewardVO);
		}
		
		if(rankingRewardVO.size() > ConstantVal.DEFAULT_ZERO)
		{
			//랭킹 보상 메일보내기
			MailTitle mailRS = gameResource.getMail().get(ConstantVal.MAIL_TEXT_RANKING_REWARD, user.getLanguage());
			mailService.SendMails(user.getId(), ConstantVal.MAIL_TYPE_NORMAL, mailRS.title, mailRS.contents, TimeCalculation.getCurrentUnixTime() + ConstantVal.MAIL_EXPIRE_TIME, rankingRewardVO);
		}
		
		setRankingRewardLog(user.getId(), user, myRanking);
		
		res.rewardSeason = user.getSeason();
		res.tier = user.getTier();
		res.ranking = myRanking > 0 ? myRanking : ConstantVal.DEFAULT_VALUE;
		res.tierRewards = tierRewardVO;
		res.rankRewards = rankingRewardVO;
		res.mails = mapperVO.makeMailsVO(user.getId(), null);
		
		return res;
	}
	
	
	// 사용자 스코어만 Ranking 갱신하는 함수
	public void setUserRankingScore(User user) throws Exception
	{
		int userId = user.getId();
		
		int season = getSeasonNumber(true);
		if(user.getSeason() != season)
			return;
		
		int score = user.getRankPoint();
		String redisKey = rankDS_total + season;
		
		if(score <= ConstantVal.DEFAULT_ZERO)
			return;
		
		//통합 랭킹
		storageContext.putSortedData(ConstantVal.RANK_DB, redisKey, Long.toString(userId), score, ConstantVal.DEFAULT_ZERO);
	}
	
	// 사용자 스코어 변경 시 랭킹 정보를 저장하는 함수 
	public void setUserRanking(User user) throws Exception
	{
		int userId = user.getId();
		
		int season = getSeasonNumber(true);
		if(season <= ConstantVal.DEFAULT_VALUE || user.getSeason() != season) 
			return;
		
		//int score = user.getRankPoint();
		String rankKey = rankDS_total + season;
		String json = null;
		
		if(user.getRankPoint() < ConstantVal.DEFAULT_ZERO)
			return;
		
		//랭킹포인트 점수 처리 변경 (점수 + 시간값)
		double curTime = 1.0d - (TimeCalculation.getCurrentUnixTime() * 0.0000000001);
		double score = user.getRankPoint() + curTime;
		
		// 모든 사용자는 userId 만 포함하여 랭킹에 저장된다. (통합 랭킹)
		storageContext.putSortedData(ConstantVal.RANK_DB, rankKey, Long.toString(userId), score, ConstantVal.DEFAULT_ZERO);
		
		int newRanking = getUserRanking(userId, true);
		if(newRanking <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_RANKING_1801);
		
		// 사용자 랭킹이 1000등이 넘어갈 경우 저장하지 않는다.
		if(newRanking > ConstantVal.RANK_MAX_USER_COUNT)
			return;
		
		// 클라이언트로 전달할 100명(예비 50명)의 데이터만 렝커 데이터를 만든다.
		if(newRanking <= ConstantVal.RANK_MAX_RANKER)
		{
			Gson gson = new Gson();
			String redisKey = rankKey + "_ranker:" + userId;
			RankerVO rankerVO = mapperVO.makeUserRankerVO(userId, user);
			
			json = gson.toJson(rankerVO, RankerVO.class);
			
			//KDG 이전 시즌 정보때문에 2개 시즌값을 넣는다. (여기 문제체크)
			int expiredTime = (gameResource.getRankingSeason().getSeasonDayCount() * ConstantVal.DAY_OF_MINUTE) * 2;
			
			if(expiredTime <= ConstantVal.DEFAULT_ZERO) {
				System.out.println("expiredTime Error : " + expiredTime);
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_RANKING_1802); //레디스 만료시간 설정 에러
			}
			
			// 통합 랭킹 Ranker 정보 Redis 입력
			storageContext.putJsonData(ConstantVal.RANK_DB, redisKey, json, expiredTime);
		}
	}
	
	// 시즌 정보를 가져온다.
	public int getSeasonNumber(boolean season) throws Exception
	{
		//현재시즌
		if(season)
			return gameResource.getRankingSeason().getSeason();
		//이전시즌
		else
			return gameResource.getRankingSeason().getSeason() - 1;
	}
	
	public int getUserRanking(int userId, boolean season) throws Exception
	{
		int seasonNum = getSeasonNumber(season);
		if(seasonNum <= ConstantVal.DEFAULT_VALUE)
		{
			seasonNum = gameResource.getRankingSeason().getPreSeason();
		}
		
		String rankKey = rankDS_total + seasonNum;
		return (int)storageContext.getSortedRank(ConstantVal.RANK_DB, rankKey, Long.toString(userId)) + 1;
	}
	
	public int getUserRanking(int userId, int season) throws Exception
	{
		String rankKey = rankDS_total + season;
		return (int)storageContext.getSortedRank(ConstantVal.RANK_DB, rankKey, Long.toString(userId)) + 1;
	}
	
	public String makeRankerRedisKey(long userId) throws Exception
	{
		int seasonNum = getSeasonNumber(true);
		if(seasonNum <= ConstantVal.DEFAULT_VALUE)
		{
			seasonNum = gameResource.getRankingSeason().getPreSeason();
		}
		return rankDS_total + seasonNum + "_ranker:" + Long.toString(userId);
	}
	
	public List<RankerVO> getUserRankers(int from) throws Exception
	{
		List<RankerVO> rankerVOs = new ArrayList<>();
		int season = getSeasonNumber(true);
		if(season <= ConstantVal.DEFAULT_VALUE)
		{
			season = gameResource.getRankingSeason().getPreSeason();
		}
		
		String rankKey = rankDS_total + season; 
		Gson gson = new Gson();
		
		List<String> rankerUserIds = new ArrayList<>();
		Set<String> rankerIds = (Set<String>) storageContext.getSortedRankRange(ConstantVal.RANK_DB, rankKey, 0, 99);
		for(String id : rankerIds)
		{
			String temp = rankKey + "_ranker:" + id;
			rankerUserIds.add(temp);
		}
		
		List<String> rankUers = storageContext.mgetOtherJsonData(ConstantVal.RANK_DB, rankerUserIds, 0);
		
		int startNumber = from;
		if(startNumber == 0)
			startNumber++;
		
		for(String strUser : rankUers)
		{
			RankerVO vo = gson.fromJson(strUser, RankerVO.class);
			if(vo == null)
				continue;
			
			if(vo.score <= ConstantVal.DEFAULT_ZERO || vo.ranking <= ConstantVal.DEFAULT_ZERO)
				continue;
			
			vo.ranking = startNumber;
			
			rankerVOs.add(vo);
			startNumber++;
		}
		
		return rankerVOs;
	}
	
	public int findMyRanking(long userId, List<RankerVO> ranker)
	{
		if(ranker.isEmpty())
			return ConstantVal.DEFAULT_VALUE;
		
		for(RankerVO vo : ranker)
		{
			if(vo.userId == userId)
				return vo.ranking;
		}
		
		return ConstantVal.DEFAULT_VALUE;
	}
	
	private void setRankingRewardLog(int userId, User user, int ranking) throws Exception
	{
		RankingRewardLog log = new RankingRewardLog();
		
		log.setPtn_month((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_MONTH, 0));
		log.setPtn_day((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_DAY, 0));
		log.setLog_date(TimeCalculation.getCurrentUnixTime());
		log.setUser_id(userId);
		log.setReward_season(user.getSeason());
		log.setReward_tier(user.getTier());
		log.setReward_score(user.getRankPoint());
		log.setReward_ranking(ranking);
		
		repositoryService.setRankingRewardLog(userId, log);
	}
	
	public boolean isSeasonReward(int userId, User user) throws Exception
	{
		RankingRewardLog log = repositoryService.getRankingRewardLog(userId, user.getSeason());
		if(log != null)
			return true;
		
		return false;
	}
}
