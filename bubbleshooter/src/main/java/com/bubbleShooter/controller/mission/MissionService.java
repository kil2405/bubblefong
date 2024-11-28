package com.bubbleShooter.controller.mission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bubbleShooter.VO.MissionVO;
import com.bubbleShooter.VO.RewardItemVO;
import com.bubbleShooter.common.BubbleException;
import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.ErrorCodeInfo;
import com.bubbleShooter.common.GameResource;
import com.bubbleShooter.common.RepositoryService;
import com.bubbleShooter.controller.mail.MailService;
import com.bubbleShooter.domain.Character;
import com.bubbleShooter.domain.Mission;
import com.bubbleShooter.domain.MissionLog;
import com.bubbleShooter.domain.Partner;
import com.bubbleShooter.request.ReqMissionReward;
import com.bubbleShooter.request.ReqMissionStart;
import com.bubbleShooter.resource.ErrorCodeResource;
import com.bubbleShooter.resource.MissionResource;
import com.bubbleShooter.response.ResMissionInfo;
import com.bubbleShooter.response.ResMissionReward;
import com.bubbleShooter.response.ResMissionStart;
import com.bubbleShooter.util.FindData;
import com.bubbleShooter.util.MapperVO;
import com.bubbleShooter.util.TimeCalculation;

@Service
public class MissionService {
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private GameResource gameResource;
	
	@Autowired
	private MapperVO mapperVO;
	
	@Autowired
	private FindData findData;
	
	public ResMissionInfo MissionInfo(int userId) throws Exception
	{
		List<Mission> missions = repositoryService.getMission(userId, false);
		if(missions == null || missions.isEmpty())
		{
			missions = new ArrayList<>();
			RefreshMission(userId, missions);
		}
		
		for(int i = 0; i < missions.size(); i++)
		{
			if(missions.get(i).getMissionUpdateTime() < TimeCalculation.getIntTime())
				RefreshMission(userId, missions);
		}
		
		ResMissionInfo res = new ResMissionInfo();
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.missions = mapperVO.makeMissionVO(missions);
		return res;
	}
	
	public List<MissionVO> getMissionInfo(int userId) throws Exception
	{
		List<Mission> missions = repositoryService.getMission(userId, true);
		if(missions == null || missions.isEmpty())
		{
			missions = new ArrayList<>();
			RefreshMission(userId, missions);
		}
		
		for(int i = 0; i < missions.size(); i++)
		{
			if(missions.get(i).getMissionUpdateTime() < TimeCalculation.getIntTime())
				RefreshMission(userId, missions);
		}
		
		return mapperVO.makeMissionVO(missions);
	}
	
	public ResMissionStart MissionStart(int userId, ReqMissionStart req) throws Exception
	{
		ResMissionStart res = new ResMissionStart();
		
		List<Mission> missions = repositoryService.getMission(userId, false);
		if(missions == null || missions.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MISSION_6000);
		
		int index = findData.findMissionIndex(missions, req.missionId);
		if(index <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MISSION_6001);
		
		if(missions.get(index).getStatus() > ConstantVal.MISSION_STATUS_DEFAULT)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MISSION_6002);
		
		if(missions.get(index).getMissionUpdateTime() != TimeCalculation.getIntTime())
		{
			res.result = ConstantVal.DEFAULT_FAIL;
			ErrorCodeResource errorCode = gameResource.getErrorCode().get(ErrorCodeInfo.ERROR_CODE_MISSION_6020);
			if(errorCode != null)
			{
				res.message = errorCode.getMessageEn();
			}
			return res;
		}
		
		List<Character> characters = repositoryService.getCharacters(userId, false);
		if(characters == null || characters.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MISSION_6003);
		
		List<Partner> partners = repositoryService.getPartners(userId, false);
		if(partners == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MISSION_6004);
		
		switch(req.type)
		{
		case ConstantVal.ITEM_TYPE_CHARACTER:
			int characterIndex = findData.findCharacterIndex(characters, req.uid);
			if(characterIndex <= ConstantVal.DEFAULT_VALUE)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MISSION_6005);
			
			if(characters.get(characterIndex).getIsMission() > ConstantVal.IS_FALSE)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MISSION_6018);
			
			characters.get(characterIndex).setIsMission(ConstantVal.IS_TRUE);
			
			repositoryService.setCharacter(userId, characterIndex, characters);
			break;
			
		case ConstantVal.ITEM_TYPE_PARTNER:
			int partnerIndex = findData.findPartnerIndex(partners, req.uid);
			if(partnerIndex <= ConstantVal.DEFAULT_VALUE)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MISSION_6006);
			
			if(partners.get(partnerIndex).getIsMission() > ConstantVal.IS_FALSE)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MISSION_6019);
			
			partners.get(partnerIndex).setIsMission(ConstantVal.IS_TRUE);
			repositoryService.setPartner(userId, partnerIndex, partners);
			break;
		
		default:
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MISSION_6007);
		}
		
		missions.get(index).setStatus(ConstantVal.MISSION_STATUS_START);
		missions.get(index).setHeroType(req.type);
		missions.get(index).setHeroUUid(req.uid);
		missions.get(index).setMissionStartTime(TimeCalculation.getCurrentUnixTime());
		
		repositoryService.setMission(userId, index, missions);
		
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.characters = mapperVO.makeCharacterVO(userId, characters, false);
		res.partners = mapperVO.makePartnerVO(userId, partners, false);
		res.missions = mapperVO.makeMissionVO(missions);
		
		// mission Log
		missionLog(userId, missions, index, null);
		
		return res;
	}
	
	public ResMissionReward MissionReward(int userId, ReqMissionReward req) throws Exception
	{
		List<Mission> missions = repositoryService.getMission(userId, false);
		if(missions == null || missions.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MISSION_6008);
		
		int index = findData.findMissionIndex(missions, req.missionId);
		if(index <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MISSION_6009);
		
		if(missions.get(index).getStatus() < ConstantVal.MISSION_STATUS_COMPLETE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MISSION_6010);
		
		//이미 보상을 받았는지 체크
		if(missions.get(index).getStatus() >= ConstantVal.MISSION_STATUS_REWARD)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MISSION_6021);
		
		MissionResource missionRS = gameResource.getMission().get(req.missionId);
		if(missionRS == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MISSION_6011);
		
		// 미션 진행시간 체크, remainTime 0보다 크면 아직 시간이 남아있음
		int remainTime = missionRS.getTime() - (int)TimeCalculation.diffOfUnixTime(missions.get(index).getMissionStartTime(), TimeCalculation.getCurrentUnixTime());
		if(remainTime > 0)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MISSION_6012);
		
		List<RewardItemVO> basicRewards = new ArrayList<>();
		List<RewardItemVO> grandRewards = new ArrayList<>();
		
		//기본보상템은 무조건 지급한다.
		if(missionRS.getbType1() > ConstantVal.DEFAULT_VALUE)
			basicRewards.add(mapperVO.makeRewardItemVO(missionRS.getbType1(), missionRS.getBasicReward1(), missionRS.getbCount1()));
		
		if(missionRS.getbType2() > ConstantVal.DEFAULT_VALUE)
			basicRewards.add(mapperVO.makeRewardItemVO(missionRS.getbType2(), missionRS.getBasicReward2(), missionRS.getbCount2()));
		
		//대박보상 지급 체크
		int missionCompleteCount = 0;
		if(missions.get(index).getHeroType() == ConstantVal.ITEM_TYPE_CHARACTER)
		{
			List<Character> characters = repositoryService.getCharacters(userId, false);
			if(characters == null || characters.isEmpty())
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MISSION_6013);
			
			int heroIndex = findData.findCharacterIndex(characters, missions.get(index).getHeroUUid());
			if(heroIndex <= ConstantVal.DEFAULT_VALUE)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MISSION_6014);
			
			if(missionRS.getHeroId() == characters.get(heroIndex).getCharacterId())
				missionCompleteCount++;
			
			if(characters.get(heroIndex).getGrade() >= missionRS.getHeroGrade())
				missionCompleteCount++;
			
			if(characters.get(heroIndex).getCharacterLevel() >= missionRS.getHeroLevel())
				missionCompleteCount++;
			
			//미션 진행중 플래그 삭제
			characters.get(heroIndex).setIsMission(ConstantVal.IS_FALSE);
			repositoryService.setCharacter(userId, heroIndex, characters);
		}
		else
		{
			List<Partner> partners = repositoryService.getPartners(userId, false);
			if(partners == null || partners.isEmpty())
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MISSION_6015);
			
			int heroIndex = findData.findPartnerIndex(partners, missions.get(index).getHeroUUid());
			if(heroIndex <= ConstantVal.DEFAULT_VALUE)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MISSION_6016);
			
			if(missionRS.getHeroId() == partners.get(heroIndex).getPartnerId())
				missionCompleteCount++;
			
			if(partners.get(heroIndex).getGrade() >= missionRS.getHeroGrade())
				missionCompleteCount++;
			
			if(partners.get(heroIndex).getPartnerLevel() >= missionRS.getHeroLevel())
				missionCompleteCount++;
			
			//미션 진행중 플래그 삭제
			partners.get(heroIndex).setIsMission(ConstantVal.IS_FALSE);
			repositoryService.setPartner(userId, heroIndex, partners);
		}
		
		if(missionCompleteCount > 0)
		{
			if(missionRS.getgType1() > ConstantVal.DEFAULT_VALUE)
				grandRewards.add(mapperVO.makeRewardItemVO(missionRS.getgType1(), missionRS.getGrandReward1(), missionRS.getgCount1()));
		}
		
		if(missionCompleteCount > 1)
		{
			if(missionRS.getgType2() > ConstantVal.DEFAULT_VALUE)
				grandRewards.add(mapperVO.makeRewardItemVO(missionRS.getgType2(), missionRS.getGrandReward2(), missionRS.getgCount2()));
		}
		
		if(missionCompleteCount > 2)
		{
			if(missionRS.getgType3() > ConstantVal.DEFAULT_VALUE)
				grandRewards.add(mapperVO.makeRewardItemVO(missionRS.getgType3(), missionRS.getGrandReward3(), missionRS.getgCount3()));
		}
		
		List<RewardItemVO> rewards = new ArrayList<>();
		rewards.addAll(basicRewards);
		rewards.addAll(grandRewards);
		
		missions.get(index).setStatus(ConstantVal.MISSION_STATUS_REWARD);
		missions.get(index).setMissionRewardTime(TimeCalculation.getCurrentUnixTime());
		
		//미션 완료처리
		repositoryService.setMission(userId, index, missions);;
		
		//보상 아이템 우편함으로 지급
		mailService.SendMails(userId, ConstantVal.MAIL_TYPE_NORMAL, "Mission Rewards", "It's a mission reward.", TimeCalculation.getCurrentUnixTime() + ConstantVal.MAIL_EXPIRE_TIME, rewards);
		
		ResMissionReward res = new ResMissionReward();
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.characters = mapperVO.makeCharacterVO(userId, null, false);
		res.partners = mapperVO.makePartnerVO(userId, null, false);
		res.missions = mapperVO.makeMissionVO(missions);
		res.basicRewards = basicRewards;
		res.grandRewards = grandRewards;
		res.mails = mapperVO.makeMailsVO(userId, null);
		
		// mission Log
		missionLog(userId, missions, index, rewards);
		
		return res;
	}
	
	
	private void RefreshMission(int userId, List<Mission> missions) throws Exception
	{
		if(missions == null || missions.isEmpty())
		{
			for(int i = 0; i < ConstantVal.MISSION_MAX_COUNT; i++)
			{
				Mission mission = new Mission();
				mission.setUserId(userId);
				mission.setIndex(i);
				mission.setMissionId(ConstantVal.DEFAULT_ZERO);
				mission.setStatus(ConstantVal.DEFAULT_ZERO);
				mission.setHeroType(ConstantVal.DEFAULT_ZERO);
				mission.setHeroUUid("");
				mission.setMissionStartTime(0);
				mission.setMissionRewardTime(0);
				mission.setMissionUpdateTime(ConstantVal.DEFAULT_ZERO);
				
				missions.add(mission);
			}
		}
		
		List<Integer> missionIds = gameResource.getMission().getAllMissionIds();
		if(missionIds == null || missionIds.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MISSION_6017);
		
		List<Integer> onGoingMissionIds = new ArrayList<>();
		for(int i = 0; i < missions.size(); i++)
		{
			if(missions.get(i).getStatus() > ConstantVal.MISSION_STATUS_DEFAULT && missions.get(i).getStatus() < ConstantVal.MISSION_STATUS_REWARD)
				onGoingMissionIds.add(missions.get(i).getMissionId());
		}
		
		missionIds.removeAll(onGoingMissionIds);
		
		Collections.shuffle(missionIds);
		
		//먼저 진행중인 미션들은, updateTime만 오늘로 변경해 둔다.
		for(int i = 0; i < ConstantVal.MISSION_MAX_COUNT; i++)
		{
			if(missions.get(i).getStatus() > ConstantVal.MISSION_STATUS_DEFAULT && missions.get(i).getStatus() < ConstantVal.MISSION_STATUS_REWARD)
			{
				missions.get(i).setMissionUpdateTime(TimeCalculation.getIntTime());
				repositoryService.setMission(userId, i, missions);
			}
		}
		
		int missionCount = 0;
		for(int i = 0; i < ConstantVal.MISSION_MAX_COUNT; i++)
		{
			if(missions.get(i).getMissionUpdateTime() == TimeCalculation.getIntTime())
				continue;
			
			if(missionCount < ConstantVal.MISSION_DEFAULT_COUNT)
			{
				missions.get(i).setMissionId(missionIds.get(missionCount));
				missions.get(i).setStatus(ConstantVal.DEFAULT_ZERO);
				missions.get(i).setHeroType(ConstantVal.DEFAULT_ZERO);
				missions.get(i).setHeroUUid("");
				missions.get(i).setMissionStartTime(0);
				missions.get(i).setMissionRewardTime(0);
				missions.get(i).setMissionUpdateTime(TimeCalculation.getIntTime());
				
				missionCount++;
			}
			else
			{
				missions.get(i).setMissionId(ConstantVal.DEFAULT_ZERO);
				missions.get(i).setStatus(ConstantVal.DEFAULT_ZERO);
				missions.get(i).setHeroType(ConstantVal.DEFAULT_ZERO);
				missions.get(i).setHeroUUid("");
				missions.get(i).setMissionStartTime(0);
				missions.get(i).setMissionRewardTime(0);
				missions.get(i).setMissionUpdateTime(TimeCalculation.getIntTime());
			}
			
			repositoryService.setMission(userId, i, missions);
		}
	}
	
	private void missionLog(int userId, List<Mission> missions, int index, List<RewardItemVO> rewards) throws Exception
	{
		MissionLog log = new MissionLog();
		log.setPtn_month((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_MONTH, 0));
		log.setPtn_day((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_DAY, 0));
		log.setLog_time(TimeCalculation.getCurrentUnixTime());
		log.setUser_id(userId);
		log.setIndex(index);
		log.setMission_id(missions.get(index).getMissionId());
		log.setStatus(missions.get(index).getStatus());
		log.setHero_type(missions.get(index).getHeroType());
		log.setHero_uuid(missions.get(index).getHeroUUid());
		log.setMission_start_time(missions.get(index).getMissionStartTime());
		if((missions.get(index).getMissionRewardTime()) != 0)
			log.setMission_reward_time(missions.get(index).getMissionRewardTime());
		log.setMission_update_time(missions.get(index).getMissionUpdateTime());
		
		if(rewards != null)
		{
			for(int i=0; i < rewards.size(); i++)
			{
				switch(i)
				{
					case 0:
						log.setReward_item_0(mailService.makeRewardItemToString(rewards.get(0)));
						break;
					case 1:
						log.setReward_item_1(mailService.makeRewardItemToString(rewards.get(1)));
						break;
					case 2:
						log.setReward_item_2(mailService.makeRewardItemToString(rewards.get(2)));		
						break;
					case 3:
						log.setReward_item_3(mailService.makeRewardItemToString(rewards.get(3)));
						break;
					case 4:
						log.setReward_item_4(mailService.makeRewardItemToString(rewards.get(4)));
						break;
				}
			}
		}
		
		repositoryService.setMissionLog(userId, log);
	}
}
