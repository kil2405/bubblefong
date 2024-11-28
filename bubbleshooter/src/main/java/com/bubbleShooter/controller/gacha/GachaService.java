package com.bubbleShooter.controller.gacha;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bubbleShooter.VO.RewardItemVO;
import com.bubbleShooter.common.BubbleException;
import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.ErrorCodeInfo;
import com.bubbleShooter.common.GameResource;
import com.bubbleShooter.common.RepositoryService;
import com.bubbleShooter.controller.mail.MailService;
import com.bubbleShooter.domain.GachaList;
import com.bubbleShooter.domain.GachaReward;
import com.bubbleShooter.domain.GachaRewardLog;
import com.bubbleShooter.domain.User;
import com.bubbleShooter.domain.UserItem;
import com.bubbleShooter.request.ReqGachaPick;
import com.bubbleShooter.resource.GachaListResource;
import com.bubbleShooter.response.ResGachaList;
import com.bubbleShooter.response.ResGachaPick;
import com.bubbleShooter.util.FindData;
import com.bubbleShooter.util.MapperVO;
import com.bubbleShooter.util.TimeCalculation;
import com.google.gson.Gson;

@Service
public class GachaService {
	
	@Autowired
	private GameResource gameResource;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private MapperVO mapperVO;
	
	@Autowired
	private FindData findData;
	
	public ResGachaList GachaInfo(int userId) throws Exception
	{
		ResGachaList res = new ResGachaList();

		User user = repositoryService.getUser(userId);
		if(user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_GACHA_6108);

		String language = user.getLanguage();
		
		List<GachaList> gachas = repositoryService.getGachaList();
		if(gachas == null || gachas.isEmpty())
		{
			res.result = ConstantVal.DEFAULT_FAIL;
			if(!language.equals("Korean"))
				res.message = "There are currently no events.";
			else
				res.message = "현재 이벤트는 없습니다.";
			
			return res;
		}
		
		int index = ConstantVal.DEFAULT_VALUE;
		for(GachaList data : gachas)
		{
			if(TimeCalculation.checkTimeNow(data.getStartTime(), data.getEndTime()))
			{
				if(data.getVaild() <= ConstantVal.IS_FALSE)
					continue;
				
				index = gachas.indexOf(data);
				break;
			}
		}
		
		if(index <= ConstantVal.DEFAULT_VALUE)
		{
			res.result = ConstantVal.DEFAULT_FAIL;
			if(!language.equals("Korean"))
				res.message = "The event has ended";
			else
				res.message = "이벤트가 종료되었습니다";
			
			return res;
		}
		
		if(gachas.get(index).getVaild() < ConstantVal.IS_TRUE)
		{
			res.result = ConstantVal.DEFAULT_FAIL;
			if(!language.equals("Korean"))
				res.message = "The event has ended";
			else
				res.message = "이벤트가 종료되었습니다";

			return res;
		}
		
		if(gachas.get(index).getGradeList() == null || gachas.get(index).getGradeList().isEmpty())
		{
			List<Integer> ids = gameResource.getGachaList().getGradeIds(gachas.get(index).getGachaId());
			if(ids == null || ids.isEmpty())
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_GACHA_6101);
			
			Collections.shuffle(ids);
			
			Gson gson = new Gson();
			gachas.get(index).setOrderIndex(ConstantVal.DEFAULT_ZERO);
			gachas.get(index).setGradeList(gson.toJson(ids));
			
			//db set
			repositoryService.setGacha(gachas.get(index));
			
			//뽑기 보상 목록을 만들어 둔다.
			//1 ~ 5등까지의 당첨 아이템과, 등수 별 당첨자 수까지 세팅
			List<GachaListResource> gachaListRS = gameResource.getGachaList().get(gachas.get(index).getGachaId());
			if(gachaListRS == null)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_GACHA_6102);
			
			for(int i = 0; i < gachaListRS.size(); i++)
			{
				GachaReward gr = new GachaReward();
				gr.setSeq(gachas.get(index).getSeq());
				gr.setGachaId(gachas.get(index).getGachaId());
				gr.setGrade((byte)(i + 1));
				
				RewardItemVO reward = gameResource.getGachaReward().getGachaRewardItem(i + 1);
				if(reward == null)
					throw new BubbleException(ErrorCodeInfo.ERROR_CODE_GACHA_6103);
				
				gr.setItemType(reward.itemType);
				gr.setItemId(reward.itemId);
				gr.setItemCount(reward.itemCount);
				int grade = reward.grade;
				gr.setHeroGrade((byte)grade);
				gr.setTotalCount(getPrizeTotalCount(gachaListRS, (byte)(i + 1)));
				gr.setPrizeCount(ConstantVal.DEFAULT_ZERO);
				
				//dbset
				repositoryService.setGachaReward(gr);
			}
		}
		
		List<GachaReward> rewards = repositoryService.getGachaRewards(gachas.get(index).getSeq(), gachas.get(index).getGachaId());
		if(rewards == null || rewards.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_GACHA_6104);
		
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.remainTime = gachas.get(index).getEndTime() - TimeCalculation.getCurrentUnixTime();
		res.rewards = mapperVO.makeGachaRewardVO(rewards);
		
		return res;
	}
	
	
	public ResGachaPick GachaPick(int userId, ReqGachaPick req) throws Exception
	{
		ResGachaPick res = new ResGachaPick();

		User user = repositoryService.getUser(userId);
		if(user == null)
			throw new BubbleException(-1);

		String region = user.getRegion();
		
		//현재 랜덤뽑기 이벤트가 진행중인지 체크
		List<GachaList> gachas = repositoryService.getGachaList();
		if(gachas == null || gachas.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_GACHA_6105);
		
		int index = ConstantVal.DEFAULT_VALUE;
		for(GachaList data : gachas)
		{
			if(TimeCalculation.checkTimeNow(data.getStartTime(), data.getEndTime()))
			{
				if(data.getVaild() <= ConstantVal.IS_FALSE)
					continue;
				
				index = gachas.indexOf(data);
				break;
			}
		}
		
		if(index <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_GACHA_6106);
		
		if(req.seq != gachas.get(index).getSeq() || req.gachaId != gachas.get(index).getGachaId())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_GACHA_6107);
		
		List<UserItem> items = repositoryService.getUserItems(userId, false);
		if(items == null || items.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_GACHA_6108);
		
		int itemIndex = findData.findUserItemIndex(ConstantVal.ITEM_TYPE_ITEM, ConstantVal.RANDOM_BOX_TICKET_ID, items);
		if(itemIndex <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_GACHA_6109);
		
		if(items.get(itemIndex).getItemCount() < ConstantVal.ENABLE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_GACHA_6110);
		
		if(!repositoryService.setChangedItem(userId, itemIndex, items, -1, ConstantVal.LOG_TYPE_USE_RANDOM_BOX, false))
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_GACHA_6111);
		
		Integer pickGrade = repositoryService.gachaPickWinner(gachas.get(index).getSeq(), gachas.get(index).getGachaId());
		if(pickGrade == null || pickGrade <= ConstantVal.DEFAULT_ZERO)
		{
			List<GachaReward> rewards = repositoryService.getGachaRewards(gachas.get(index).getSeq(), gachas.get(index).getGachaId());
			if(rewards == null || rewards.isEmpty())
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_GACHA_6112);
			
			
			res.result = ConstantVal.DEFAULT_FAIL;
			if(!region.equals("KR"))
				res.message = "All products have been sold out";
			else
				res.message = "모든 제품이 품절되었습니다";

			res.rewardGrade = 0;
			res.rewards = null;
			res.gachaRewardBoard = mapperVO.makeGachaRewardVO(rewards);
			res.remainTime = gachas.get(index).getEndTime() - TimeCalculation.getCurrentUnixTime();
			return res;
		}
		
		List<GachaReward> rewards = repositoryService.getGachaRewards(gachas.get(index).getSeq(), gachas.get(index).getGachaId());
		if(rewards == null || rewards.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_GACHA_6113);
		
		int rewardIndex = ConstantVal.DEFAULT_VALUE;
		for(GachaReward gr : rewards)
		{
			if(gr.getGrade() == pickGrade)
			{
				rewardIndex = rewards.indexOf(gr);
				break;
			}
		}
		
		List<RewardItemVO> rewardVO = new ArrayList<>();
		rewardVO.add(mapperVO.makeRewardItemVO(rewards.get(rewardIndex).getItemType(), rewards.get(rewardIndex).getItemId(), rewards.get(rewardIndex).getItemCount(), (int)rewards.get(rewardIndex).getHeroGrade()));
		
		mailService.SendMails(userId, ConstantVal.MAIL_TYPE_NORMAL, "Drawing Lot Reward", mailContentsString(pickGrade, region), TimeCalculation.getCurrentUnixTime() + ConstantVal.MAIL_EXPIRE_TIME, rewardVO);
		repositoryService.setUserItem(userId, itemIndex, items);
		
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.rewardGrade = pickGrade;
		res.rewards = mapperVO.makeRewardItemVO(rewards.get(rewardIndex).getItemType(), rewards.get(rewardIndex).getItemId(), rewards.get(rewardIndex).getItemCount(), (int)rewards.get(rewardIndex).getHeroGrade());
		res.gachaRewardBoard = mapperVO.makeGachaRewardVO(rewards);
		res.remainTime = gachas.get(index).getEndTime() - TimeCalculation.getCurrentUnixTime();
		res.items = mapperVO.makeItemVO(userId, items, false);
		res.mails = mapperVO.makeMailsVO(userId, null);
		
		// Gacha Reward Log
		GachaRewardLog(userId, gachas, index, pickGrade, rewardVO);
		
		return res;
	}
	
	private String mailContentsString(int grade, String region)
	{
		String mailContents = "";
		if(region.equals("KR"))
		{
			switch(grade)
			{
			case 1:
				mailContents = "1등 보상";
				break;
			case 2:
				mailContents = "2등 보상";
				break;
			case 3:
				mailContents = "3등 보상";
				break;
			case 4:
				mailContents = "4등 보상";
				break;
			case 5:
				mailContents = "5등 보상";
				break;
			}
		}
		else
		{
			switch(grade)
			{
			case 1:
				mailContents = "1st Reward";
				break;
			case 2:
				mailContents = "2nd Reward";
				break;
			case 3:
				mailContents = "3rd Reward";
				break;
			case 4:
				mailContents = "4th Reward";
				break;
			case 5:
				mailContents = "5th Reward";
				break;
			}
		}

		return mailContents;
	}
	
	
	private int getPrizeTotalCount(List<GachaListResource> gachaList, byte grade)
	{
		for(GachaListResource rs : gachaList)
		{
			if(rs.getGrade() == grade)
			{
				return rs.getCount();
			}
		}
		
		return ConstantVal.DEFAULT_VALUE;
	}
	
	
	private void GachaRewardLog(int userId, List<GachaList> gachas, int index, int pickGrade, List<RewardItemVO> rewardVO) throws Exception
	{
		GachaRewardLog log = new GachaRewardLog();
		
		log.setPtn_month((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_MONTH, 0));
		log.setPtn_day((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_DAY, 0));
		log.setLog_time(TimeCalculation.getCurrentUnixTime());
		log.setUser_id(userId);
		log.setGacha_index(gachas.get(index).getSeq());
		log.setGacha_id(gachas.get(index).getGachaId());
		log.setReward_grade(pickGrade);
		log.setGacha_reward_item(mailService.makeRewardItemToString(rewardVO.get(0)));
		
		repositoryService.setGachaRewardLog(userId, log);
	}
}
