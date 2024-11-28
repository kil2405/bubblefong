package com.bubbleShooter.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.VO.RankingRewardVO;
import com.bubbleShooter.VO.RewardItemVO;
import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.GameResource;
import com.bubbleShooter.common.StorageContext;
import com.bubbleShooter.util.MapperVO;

@Component
public class RankingRewardComponent {
	@Autowired
	private StorageContext storageContext;
	
	@Autowired
	private GameResource gameResource;
	
	@Autowired
	private MapperVO mapperVO;
	
	private HashMap<Integer, List<RankingRewardResource>> resource = new HashMap<>();
	//key : isKor (0 : 글로벌, 1: 한국)
	private HashMap<Byte, List<RankingRewardVO>> tierRewards = new HashMap<>();
	//key : isKor (0 : 글로벌, 1: 한국)
	private HashMap<Byte, List<RankingRewardVO>> rankingRewards = new HashMap<>();
	
	private int createKey(byte kor, int seasonId, int rewardType)
	{
		return (kor * 1000) + (seasonId * 100) + rewardType;
	}
	
	public void LoadResource() throws Exception
	{
		List<RankingRewardResource> rankingRewardRS = storageContext.<RankingRewardResource>getDataList(ConstantVal.STATIC_DB, "ranking_reward_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
		if(rankingRewardRS == null || rankingRewardRS.isEmpty())
		{
			System.out.println("RankingRewardResource No data");
			System.exit(0);
		}
		
		for(RankingRewardResource data : rankingRewardRS)
		{
			int key = createKey(data.getKorCheck(), data.getRankingSeasonId(), data.getRewardType());
			if(resource.containsKey(key))
			{
				List<RankingRewardResource> rewards = resource.get(key);
				if(rewards == null || rewards.isEmpty())
				{
					System.out.println("RankingRewardResource No data");
					System.exit(0);
				}
				
				rewards.add(data);
				resource.put(key, rewards);
			}
			else
			{
				List<RankingRewardResource> rewards = new ArrayList<>();
				rewards.add(data);
				resource.put(key, rewards);
			}
		}
		
		makeRankingRewardVO();
		
		System.out.println("RankingRewardResource Loading Complete");
	}
	
	public List<RankingRewardResource> get(String region, int seasonId, int rewardType, int ranking)
	{
		byte isKor = 0;
		if(region.equals("KR"))
			isKor = 1;
		
		return get(isKor, seasonId, rewardType, ranking);
	}

	public List<RankingRewardResource> get(byte isKor, int seasonId, int rewardType, int ranking)
	{
		int key = createKey(isKor, seasonId, rewardType);
		List<RankingRewardResource> rewards = resource.get(key);
		if(rewards == null || rewards.isEmpty())
		{
			key = createKey(isKor, ConstantVal.DEFAULT_SEASON, rewardType);
			rewards = resource.get(key);
		}
		
		if(rewards == null || rewards.isEmpty())
			return null;
		
		List<RankingRewardResource> value = new ArrayList<>();
		for(RankingRewardResource reward : rewards)
		{
			if(reward == null)
				return null;
			
			if(reward.getFromTier() <= ranking && reward.getToTier() >= ranking)
				value.add(reward);
		}
		return value;
	}
	
	public List<RankingRewardResource> get(String region, int seasonId, byte rewardType)
	{
		byte isKor = 0;
		if(region.equals("KR"))
			isKor = 1;

		return get(isKor, seasonId, rewardType);
	}

	public List<RankingRewardResource> get(byte isKor, int seasonId, byte rewardType)
	{
		int key = createKey(isKor, seasonId, rewardType);
		if(!resource.containsKey(key))
			key = createKey(isKor, ConstantVal.DEFAULT_SEASON, rewardType);
		
		return resource.get(key);
	}
	
	private void makeRankingRewardVO() throws Exception
	{
		int curSeason = gameResource.getRankingSeason().getSeason();
		
		for(byte isKor = 0; isKor < 2; isKor++)
		{
			HashMap<Integer, List<RewardItemVO>> rewards = new HashMap<>();

			List<RankingRewardResource> tierRewardList = get(isKor, curSeason, ConstantVal.RANKING_REWARD_TYPE_TIER);
			if(tierRewardList == null)
			{
				curSeason = ConstantVal.DEFAULT_SEASON;
				tierRewardList = get(isKor, curSeason, ConstantVal.RANKING_REWARD_TYPE_TIER);
				if(tierRewardList == null) {
					System.out.println("RankingRewardResource Default Season not data");
					System.exit(0);
				}
			}
			
			for(RankingRewardResource rs : tierRewardList)
			{
				if(rewards.containsKey(rs.getFromTier()))
				{
					List<RewardItemVO> vos = rewards.get(rs.getFromTier());
					vos.add(mapperVO.makeRewardItemVO(rs.getItemType(), rs.getItemId(), rs.getItemCount()));
				}
				else
				{
					List<RewardItemVO> vos = new ArrayList<>();
					vos.add(mapperVO.makeRewardItemVO(rs.getItemType(), rs.getItemId(), rs.getItemCount()));
					rewards.put(rs.getFromTier(), vos);
				}
			}
			
			List<RankingRewardVO> tierReward = new ArrayList<>();
			for(int key : rewards.keySet())
			{
				List<RewardItemVO> vos = rewards.get(key);
				
				RankingRewardVO rankingRewardVO = new RankingRewardVO();
				rankingRewardVO.from = key;
				rankingRewardVO.to = key;
				rankingRewardVO.rewards = vos;
				
				tierReward.add(rankingRewardVO);
			}

			tierRewards.put(isKor, tierReward);
		}
		
		for(byte isKor = 0; isKor < 2; isKor ++)
		{
			List<RankingRewardResource> ranking = get(isKor, curSeason, ConstantVal.RANKING_REWARD_TYPE_RANKING);
			List<RankingRewardVO> rankingReward = new ArrayList<>();
			for(int i = 0; i < ranking.size(); i++)
			{
				RankingRewardVO vo = new RankingRewardVO();
				vo.from = ranking.get(i).getFromTier();
				vo.to = ranking.get(i).getToTier();
				vo.rewards = new ArrayList<>();
				vo.rewards.add(mapperVO.makeRewardItemVO(ranking.get(i).getItemType(), ranking.get(i).getItemId(), ranking.get(i).getItemCount()));
				
				rankingReward.add(vo);
			}

			rankingRewards.put(isKor, rankingReward);
		}
	}
	
	public List<RankingRewardVO> getTierRewards(String region)
	{
		if(region.equals("KR"))
			return tierRewards.get((byte)1);
		else
			return tierRewards.get((byte)0);
	}
	
	public List<RankingRewardVO> getRankingRewards(String region)
	{
		if(region.equals("KR"))
			return rankingRewards.get((byte)1);
		else
			return rankingRewards.get((byte)0);
	}
	
	public void VerifyResource() throws Exception
	{
		System.out.println("RankingRewardResource Verify Complete");
	}
	
}
