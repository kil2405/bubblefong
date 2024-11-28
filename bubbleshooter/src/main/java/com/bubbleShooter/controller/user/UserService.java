package com.bubbleShooter.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bubbleShooter.VO.AttendanceEventVO;
import com.bubbleShooter.VO.AttendanceVO;
import com.bubbleShooter.VO.RewardItemVO;
import com.bubbleShooter.common.BubbleException;
import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.ErrorCodeInfo;
import com.bubbleShooter.common.GameResource;
import com.bubbleShooter.common.RepositoryService;
import com.bubbleShooter.common.ServerConfig;
import com.bubbleShooter.controller.Profile.ProfileService;
import com.bubbleShooter.controller.card.CardService;
import com.bubbleShooter.controller.mail.MailService;
import com.bubbleShooter.controller.mission.MissionService;
import com.bubbleShooter.controller.nft.NFTService;
import com.bubbleShooter.controller.rank.RankService;
import com.bubbleShooter.domain.AttendanceEventRewardLog;
import com.bubbleShooter.domain.AttendanceRewardLog;
import com.bubbleShooter.domain.ChangeNicknameLog;
import com.bubbleShooter.domain.Character;
import com.bubbleShooter.domain.Event;
import com.bubbleShooter.domain.EventLog;
import com.bubbleShooter.domain.Notice;
import com.bubbleShooter.domain.Partner;
import com.bubbleShooter.domain.Preset;
import com.bubbleShooter.domain.User;
import com.bubbleShooter.domain.UserConnectLog;
import com.bubbleShooter.domain.UserItem;
import com.bubbleShooter.domain.UserVersion;
import com.bubbleShooter.relation.Location;
import com.bubbleShooter.relation.MailTitle;
import com.bubbleShooter.request.ReqChangeNickname;
import com.bubbleShooter.request.ReqLogin;
import com.bubbleShooter.request.ReqUserDebug;
import com.bubbleShooter.resource.AttendanceDailyResource;
import com.bubbleShooter.resource.AttendanceEventResource;
import com.bubbleShooter.resource.CharacterResource;
import com.bubbleShooter.resource.EventResource;
import com.bubbleShooter.resource.ItemResource;
import com.bubbleShooter.resource.PartnerResource;
import com.bubbleShooter.resource.SkillPartnerGroupResource;
import com.bubbleShooter.response.BaseResponse;
import com.bubbleShooter.response.ResChangeNickname;
import com.bubbleShooter.response.ResItemRefresh;
import com.bubbleShooter.response.ResLogin;
import com.bubbleShooter.response.ResNotice;
import com.bubbleShooter.response.ResUserRefresh;
import com.bubbleShooter.util.FindData;
import com.bubbleShooter.util.GeoIP2;
import com.bubbleShooter.util.MapperVO;
import com.bubbleShooter.util.TimeCalculation;
import com.google.gson.Gson;

@Service
public class UserService {
	
	@Value("${bubblefong.master.server}")
	private String master_sever_ip;
	
	@Value("${bubblefong.master.port}")
	private int master_server_port;
	
	@Value("${spring.server.mode}")
	private String serverMode;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private GameResource gameResource;
	
	@Autowired
	private MapperVO mapperVO;
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private RankService rankService;
	
	@Autowired
	private FindData findData;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private NFTService nftService;
	
	@Autowired
	private MissionService missionService;

	@Autowired
	private ProfileService profileService;
	
	String pattern = "^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|]+$";
	
	public ResLogin Login(ReqLogin req, String clientIp) throws Exception
	{
		ResLogin res = new ResLogin();
		res.attendance = new AttendanceVO();
		res.events = new ArrayList<>();
		User user = null;
		boolean isNewUser = false;
		
		String encryption = UUID.randomUUID().toString().replace("-", "");
		encryption = encryption.substring(0, 16);
		
		user = repositoryService.getUser(req.socialId);
		if(user == null)
		{
			if(req.isGuest == 2)
			{
				user = repositoryService.getUser(gameResource.getIdList().getBotId());
				if(user == null)
					throw new BubbleException(ErrorCodeInfo.ERROR_CODE_USER_1016);
				
				CheckBotPartner(user.getId());
			}
			else
			{
				//신규 계정생성 처리
				String secret = UUID.randomUUID().toString().replace("-", "");
				
				user = CreateAccount(secret, encryption, req.language, req.market, req.region, req.socialId, req.isGuest);
				if(user == null)
					throw new BubbleException(ErrorCodeInfo.ERROR_CODE_USER_1006);
			}
		}
		
		//마켓에서 vip 레벨 얻어옴
		byte vip = user.getVip();
		
		//마켓에서 얻어온 VIP레벨이, user테이블의 vip와 다르다면 vip레벨 업데이트
		if(vip != user.getVip())
			user.setVip(vip);

		//System.out.println("region : "  + region + " | Client IP : " + clientIp);
		// if(region.equals("KR") && user.getIsGuest() != 2)
		// {
		// 	if(user.getGrade() <= 0)
		// 	{
		// 		res.result = ConstantVal.LOGIN_TYPE_BLOCK;
		// 		return res;
		// 	}
		// }

		String region = getClientRegion(clientIp);
		user.setRegion(region);
		System.out.println("region : "  + region + " | Client IP : " + clientIp + " | userId : " + user.getId());
		//user.setRegion("IV");

		//true : 접속한지 하루가 지남.
		//false : 접속한지 하루가 지나지 않음
		res.dayChanged = !TimeCalculation.checkToday(user.getLoginTime());
		if(res.dayChanged)
		{
			//캐릭터 채굴량 리셋
			List<Character> characters = repositoryService.getCharacters(user.getId(), true);
			if(characters != null)
			{
				for(Character c : characters)
				{
					if(c.getIsNft() > 0)
						c.setDailyearn(ConstantVal.DEFAULT_ZERO);
					
					repositoryService.setCharacter(user.getId(), c);
				}
			}
		}
		
		// 출석 처리( 밤 12시 기준 )
		if(TimeCalculation.getIntTime() > user.getAttendanceDate())
		{
			// 출석보상 처리
			AttendanceCheck(user, res.attendance);
		}
		
		if(user.getNickname().isEmpty() && req.isGuest != 2)
			isNewUser = true;

		//RefreshRankingTier(user);

		//response
		res.isEncryption = ServerConfig.USE_ENCRYPTION;
		res.encryption = encryption;
		res.account = mapperVO.makeAccountVO(user);
		res.patchHost = repositoryService.getPatchUrl();
		res.isNewUser = (isNewUser) ? (byte)1 : (byte)0;
		res.items = mapperVO.makeItemVO(user.getId(), null, true);
		res.characters = mapperVO.makeCharacterVO(user.getId(), null, true);
		res.partners = mapperVO.makePartnerVO(user.getId(), null, true);
		res.preset = mapperVO.makePresetVO(user.getId(), null, true);
		
		//유저 정보 업데이트
		UpdateUserLogin(user, req.version, req.market, req.language, encryption, req.isGuest);
		
		res.missions = missionService.getMissionInfo(user.getId());
		res.masterServerIp = master_sever_ip;
		res.masterServerPort = master_server_port;
		
		if(!res.attendance.isReward)
		{
			res.attendance.attendanceDay = user.getAttendanceDay();
			res.attendance.attendanceBoard = mapperVO.makeAttendanceRewardVO();
		}
		
		res.events = AttendanceEventCheck(user.getId());
		res.profiles = profileService.getProfile(user.getId(), null, true).profiles;
		
		//접속로그 처리
		res.result = ConstantVal.LOGIN_TYPE_SUCCESS;
		return res;
	}
	
	private void UpdateUserLogin(User user, String version, byte market, String language, String encryption, byte inGuest) throws Exception
	{
		user.setMarket(market);
		user.setRegion(user.getRegion());
		user.setEncryption(encryption);
		user.setIsGuest(inGuest);
		
		boolean combackUser = false;
		boolean isNewUser = false;
		
		long loginCalDay = TimeCalculation.diffOfDays(user.getLoginTime(), TimeCalculation.getCurrentUnixTime());
		if(loginCalDay >= ConstantVal.DAY_OF_MONTH)
			combackUser = true;
		
		long createCalDay = TimeCalculation.diffOfDays(user.getRegDate(), TimeCalculation.getCurrentUnixTime());
		if(createCalDay <= ConstantVal.DAY_WEEK_COUNT)
			isNewUser = true;
		
		List<UserItem> items = repositoryService.getUserItems(user.getId(), false);
		
		UserConnectLog log = new UserConnectLog().Set(user, items, version, user.getRegion());
		if(isNewUser)
			log.setIs_new(ConstantVal.NEW_USER);
		else if(combackUser)
			log.setIs_new(ConstantVal.COMBACK_USER);
		else
			log.setIs_new(ConstantVal.DEFAULT_USER);
		
		checkEvent(user.getId(), log.getIs_new());
		
//		테스트 메일 발송
//		List<RewardItemVO> rewardItems = new ArrayList<>();
//		rewardItems.add(mapperVO.makeRewardItemVO(ConstantVal.ITEM_TYPE_PARTNER, 1050001, 1, ConstantVal.CARD_GRADE_SSR, 1, 0, null, null, null));
//		mailService.SendMails(user.getId(), ConstantVal.MAIL_TYPE_NORMAL, "Daily Reward", "This is a daily compensation payment item", TimeCalculation.getCurrentUnixTime() + ConstantVal.MAIL_EXPIRE_TIME, rewardItems);
		
		//로그인시간 세팅
		user.setLoginTime(TimeCalculation.getCurrentUnixTime());
		
		UserVersion uv = new UserVersion();
		uv.setVersion(market, user.getRegion(), version);
		repositoryService.setUserVersion(user.getId(), user, uv, new Gson());
		
		//dbSet
		repositoryService.setUser(user.getId(), user);
		repositoryService.setUserConnectLog(log);
	}
	
	public User CreateAccount(String secretKey, String encryption, String language, byte market, String region, String socialId, byte isGuest) throws Exception
	{
		User user = new User();
		user.setUuid(socialId);
		user.setNickname("");
		user.setLevel(ConstantVal.INIT_ACCOUNT_LEVEL);
		user.setExp(ConstantVal.INIT_ACCOUNT_EXP);
		user.setSeason(gameResource.getRankingSeason().getSeason());
		user.setRankPoint(ConstantVal.DEFAULT_ZERO);
		user.setTier(ConstantVal.DEFAULT_ZERO);
		user.setVip(ConstantVal.DEFAULT_ZERO);
		user.setGrade(ConstantVal.USER_GRADE_NORMAL);
		user.setSecret(secretKey);
		user.setEncryption(encryption);
		user.setWallet("");
		user.setMarket(market);
		user.setAttendanceDay(ConstantVal.DEFAULT_ZERO);
		user.setAttendanceDate(ConstantVal.DEFAULT_ZERO);
		user.setIsGuest(isGuest);
		user.setLanguage(language);
		user.setRegion(region);
		user.setLoginTime(ConstantVal.DEFAULT_ZERO);
		user.setNickUpdate(ConstantVal.DEFAULT_ZERO);
		
		//DB Set
		repositoryService.setNewUser(0, user);

		//프로필 초기화
		profileService.initUserProfile(user.getId(), null);
		
		//최초 지급되는 캐릭터 추가
		initCharacter(user.getId());
		
		//최초 지급되는 동료 추가
		initPartner(user.getId());
		
		//최초 지급되는 유저 아이템
		initUserItem(user.getId());
		
		//최초 프리셋 적용
		initPreset(user.getId());
		
		//신규 출석부
		//진행되는 이벤트
		
		return user;
	}
	
	public ResUserRefresh UserProfile(int userId) throws Exception
	{
		User user = repositoryService.getUser(userId, false);
		if(user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_USER_1013);

		ResUserRefresh res = new ResUserRefresh();
		res.account = mapperVO.makeAccountVO(user);
		res.result = ConstantVal.DEFAULT_SUCCESS;
		
		return res;
	}
	
	public ResChangeNickname ChangeNickname(int userId, ReqChangeNickname req) throws Exception
	{
		ResChangeNickname res = new ResChangeNickname();
		
		if(req.nickname == null || req.nickname.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_USER_1201);
		
		User user = repositoryService.getUser(userId, false);
		if(user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_USER_1014);
		
		int nickNameLength = findData.getStringLength(req.nickname);
		if(nickNameLength > 12)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_USER_1205);
		
		if(!findData.checkInputOnlyNumberAndAlphabet(req.nickname))
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_USER_1204);
		
		if(req.isNewUser <= ConstantVal.IS_FALSE)
		{
			ChangeNicknameLog log = repositoryService.getChangeNicknameLog(userId);
			if(log != null)
			{
				if(TimeCalculation.checkLimitTimeNow(log.getLimit_date()))
				{
					res.result = ConstantVal.DEFAULT_FAIL;
					res.remainTime = log.getLimit_date() - TimeCalculation.getCurrentUnixTime();
					return res;
				}
			}
		}
		
		Integer checkId = repositoryService.checkNickname(req.nickname);
		if(checkId != null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_USER_1206);
		
		//금칙어 검사 필요
		if(!gameResource.getForbiddenWords().IsValidString(user.getLanguage(), req.nickname, true))
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_USER_1203);
		
		String oldNickname = user.getNickname();
		user.setNickname(req.nickname);
		user.setNickUpdate(TimeCalculation.getIntTime());
		
		repositoryService.setUser(userId, user);
		//랭킹 닉네임 변경
		rankService.setUserRanking(user);
		
		if(!user.getWallet().isBlank())
		{
			// 마켓 플레이스 닉네임 변경
			nftService.changeNickname(user.getWallet(), user.getUuid(), user.getNickname());
		}
		
		int limitDate = TimeCalculation.getCurrentUnixTime();
		if(req.isNewUser < ConstantVal.IS_TRUE)
		{
			limitDate += ConstantVal.DAY_OF_SECOND;
		}
		
		//log 추가
		ChangeNicknameLog changeLog = new ChangeNicknameLog();
		changeLog.setPtn_month((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_MONTH, 0));
		changeLog.setLog_date(TimeCalculation.getCurrentUnixTime());
		changeLog.setUser_id(userId);
		changeLog.setPrev_nickname(oldNickname);
		changeLog.setNew_nickname(req.nickname);
		changeLog.setLimit_date(limitDate);
		
		repositoryService.setChangeNickNameLog(userId, changeLog);
		
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.nickname = req.nickname;
		res.remainTime = changeLog.getLimit_date() - TimeCalculation.getCurrentUnixTime();
		
		return res;
	}
	
	public ResNotice notice(int userId) throws Exception
	{
		ResNotice res = new ResNotice();
		
		List<Notice> notices = repositoryService.getNotice();
		if(notices == null || notices.isEmpty())
		{
			res.result = ConstantVal.DEFAULT_SUCCESS;
			res.notices = new ArrayList<>();
			return res;
		}
		
		res.notices = mapperVO.makeNoticeVO(notices);
		res.result = ConstantVal.DEFAULT_SUCCESS;
		
		return res;
	}
	
	public ResItemRefresh ItemRefresh(int userId) throws Exception
	{
		ResItemRefresh res = new ResItemRefresh();
		
		List<UserItem> items = repositoryService.getUserItems(userId, false);
		if(items == null || items.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_USER_1008);
		
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.items = mapperVO.makeItemVO(userId, items, false);
		
		return res;
	}

	public BaseResponse ChangeLanguage(int userId, String language) throws Exception
	{
		BaseResponse res = new BaseResponse();
		
		User user = repositoryService.getUser(userId, false);
		if(user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_USER_1014);
		
		user.setLanguage(language);
		repositoryService.setUser(userId, user);
		
		res.result = ConstantVal.DEFAULT_SUCCESS;
		return res;
	}
	
	public BaseResponse UserDebug(int userId, ReqUserDebug req) throws Exception
	{
		BaseResponse res = new BaseResponse();
		
		if(serverMode.equals("live"))
			return res;
		
		if(req.itemType > ConstantVal.DEFAULT_VALUE)
		{
			List<UserItem> items = repositoryService.getUserItems(userId, false);
			if(items == null)
			{
				res.result = ConstantVal.DEFAULT_FAIL;
				return res;
			}
			
			int index = req.itemType;
			switch(index)
			{
			case 0:
				items.get(index).setItemCount(items.get(index).getItemCount() + 10000);
				repositoryService.setUserItem(userId, index, items);
				break;
			case 1:
				items.get(index).setItemCount(items.get(index).getItemCount() + 10000);
				repositoryService.setUserItem(userId, index, items);
				break;
			case 2:
				items.get(index).setItemCount(items.get(index).getItemCount() + 10000);
				repositoryService.setUserItem(userId, index, items);
				break;
			case 3:
				index = findData.findUserItemIndex(ConstantVal.ITEM_TYPE_ITEM, ConstantVal.COMPETITION_TICKET_ID, items);
				items.get(index).setItemCount(items.get(index).getItemCount() + 10000);
				repositoryService.setUserItem(userId, index, items);
				break;
			case 4:
				index = findData.findUserItemIndex(ConstantVal.ITEM_TYPE_ITEM, ConstantVal.RANDOM_BOX_TICKET_ID, items);
				items.get(index).setItemCount(items.get(index).getItemCount() + 10000);
				repositoryService.setUserItem(userId, index, items);
				break;
			case 5:
				index = findData.findUserItemIndex(ConstantVal.ITEM_TYPE_ITEM, ConstantVal.MINIGAME_TICKET_ID, items);
				items.get(index).setItemCount(items.get(index).getItemCount() + 10000);
				repositoryService.setUserItem(userId, index, items);
				break;
			case 6:
				for(int i = 0; i < items.size(); i++)
				{
					if(i < 5 || i == 8)
						continue;
					
					items.get(i).setItemCount(items.get(i).getItemCount() + 10000);
					repositoryService.setUserItem(userId, i, items);
				}
				break;
			}
		}
		else
		{
			if(req.heroType == ConstantVal.ITEM_TYPE_CHARACTER)
			{
				List<Character> characters = repositoryService.getCharacters(userId, false);
				if(characters == null)
				{
					res.result = ConstantVal.DEFAULT_FAIL;
					return res;
				}
				
				if(req.grade == 0)
				{
					cardService.AddCharacter(userId, mapperVO.makeCharacter(userId, 1020000, req.grade), characters, "USER_DEBUG_CREATE");
				}
				else
				{
					List<CharacterResource> characterRS = gameResource.getCharacter().getCharacters();
					for(CharacterResource data : characterRS)
					{
						if(data.getIsNft() <= 0)
							continue;
						
						cardService.AddCharacter(userId, mapperVO.makeCharacter(userId, data.getId(), req.grade), characters, "USER_DEBUG_CREATE");
					}
				}
			}
			else
			{
				List<Partner> partners = repositoryService.getPartners(userId, false);
				if(partners == null)
				{
					res.result = ConstantVal.DEFAULT_FAIL;
					return res;
				}
				
				List<PartnerResource> partnerRS = gameResource.getPartner().getPartners();
				for(PartnerResource data : partnerRS)
				{
					cardService.AddPartner(userId, mapperVO.makePartner(userId, data.getId(), req.grade), partners, "USER_DEBUG_CREATE");
				}
			}
		}
		
		res.result = ConstantVal.DEFAULT_SUCCESS;
		return res;
	}


	private void initCharacter(int userId) throws Exception
	{
		List<Character> characters = new ArrayList<>();
		
		for(int i = 0; i < ConstantVal.DEFAULT_CREATE_CHARACTER.length; i++)
		{
			if(ConstantVal.DEFAULT_CREATE_CHARACTER[i] <= 0)
				continue;
			
			CharacterResource characterRS = gameResource.getCharacter().get(ConstantVal.DEFAULT_CREATE_CHARACTER[i]);
			if(characterRS == null)
				continue;
			
			Character newCharacter = mapperVO.makeCharacter(userId, characterRS.getId());
			cardService.AddCharacter(userId, newCharacter, characters, ConstantVal.LOG_TYPE_GET_CREATE_ACCOUNT);
		}
	}
	
	public void initUserItem(int userId) throws Exception
	{
		List<UserItem> list = new ArrayList<>();
		
		List<ItemResource> itemResource = gameResource.getItem().getItemList();
		if(itemResource == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_USER_1002);
		
		for(int i = 0; i < itemResource.size(); i++)
		{
			UserItem item = new UserItem();
			item.setUserId(userId);
			item.setItemType(itemResource.get(i).getItemType());
			item.setItemId(itemResource.get(i).getItemId());
			item.setItemCount(0);
			list.add(item);
		}
		
		list.get(ConstantVal.ITEM_TYPE_GOLD).setItemCount(0);
		list.get(ConstantVal.ITEM_TYPE_RUBY).setItemCount(0);

		HashMap<String,List<UserItem>> param = new HashMap<>();
		param.put("itemList", list);
		repositoryService.initUserItemData(userId, param);
	}

	
	private void initPartner(int userId) throws Exception
	{
//		List<Partner> partners = new ArrayList<>();
//
//		Partner newPartner = mapperVO.makePartner(userId, 1050001, ConstantVal.CARD_GRADE_R);
//		if(newPartner != null)
//			cardService.AddPartner(userId, newPartner, partners, ConstantVal.LOG_TYPE_GET_CREATE_ACCOUNT);
	}
	
	// 최초 생성 프리셋
	public void initPreset(int userId) throws Exception
	{
		List<Character> characters = repositoryService.getCharacters(userId, false);
		if(characters == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_USER_1000);

		Preset userPreset = new Preset();
		userPreset.setUserId(userId);
		userPreset.setCharacterUid(characters.get(0).getUid());
		userPreset.setPartner1Uid("");
		userPreset.setPartner2Uid("");
		userPreset.setPartner3Uid("");

		//DB set
		repositoryService.setPreset(userId, userPreset);
	}
	
	
	// 출석체크 보상
	private void AttendanceCheck(User user, AttendanceVO attVO) throws Exception
	{
		int nextDay = 1;
		if(user.getAttendanceDay() > ConstantVal.DEFAULT_ZERO)
		{
			AttendanceDailyResource attendanceDayilyRS = gameResource.getAttendanceDaily().get(user.getAttendanceDay());
			if(attendanceDayilyRS == null)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_USER_1003);
			
			nextDay = attendanceDayilyRS.getNextIndex();
		}
		
		AttendanceDailyResource curAttendanceRewardRS = gameResource.getAttendanceDaily().get(nextDay);
		if(curAttendanceRewardRS == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_USER_1015);
		
		// 출석보상 지급
		List<RewardItemVO> rewardItems = new ArrayList<>();
		rewardItems.add(mapperVO.makeRewardItemVO(curAttendanceRewardRS.getItemType(), curAttendanceRewardRS.getItemId(), curAttendanceRewardRS.getItemCount()));
		
		// 메일로
		MailTitle mailRS = gameResource.getMail().get(ConstantVal.MAIL_TEXT_ATTENDANCE_REWARD, user.getLanguage());
		mailService.SendMails(user.getId(), ConstantVal.MAIL_TYPE_NORMAL, mailRS.title, mailRS.contents, TimeCalculation.getCurrentUnixTime() + ConstantVal.MAIL_EXPIRE_TIME, rewardItems);
		
		//클라로 보내줄 데이터(VO)를 만든다
		mapperVO.makeAttendanceVO(curAttendanceRewardRS.getDays(), attVO);
		
		AttendanceRewardLog log = new AttendanceRewardLog();
		log.setPtn_month((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_MONTH, 0));
		log.setPtn_day((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_DAY, 0));
		log.setLog_time(TimeCalculation.getCurrentUnixTime());
		log.setUser_id(user.getId());
		log.setItem_type(curAttendanceRewardRS.getItemType());
		log.setItem_id(curAttendanceRewardRS.getItemId());
		log.setItem_count(curAttendanceRewardRS.getItemCount());
		
		// 유저 출석일을 다음날로 바꾸기
		user.setAttendanceDay(curAttendanceRewardRS.getDays());
		// 유저 출석 날짜 오늘 날짜로 넣기
		user.setAttendanceDate(TimeCalculation.getIntTime());
		
		repositoryService.setAttendanceLog(user.getId(), log);
	}
	
	private List<AttendanceEventVO> AttendanceEventCheck(int userId) throws Exception
	{
		List<AttendanceEventVO> res = new ArrayList<>();

		User user = repositoryService.getUser(userId, false);
		if(user == null)
			return res;
		
		List<EventResource> events = gameResource.getEvent().get();
		if(events == null || events.isEmpty())
			return res;
		
		for(EventResource event : events)
		{
			int day = 0;
			AttendanceEventVO vo = new AttendanceEventVO();
			
			AttendanceEventRewardLog logs = repositoryService.getAttendanceEventLog(userId, event.getEventNo());
			if(logs != null)
			{
				day = logs.getEvent_day();
				if(TimeCalculation.getIntTime() == logs.getReward_day())
				{
					mapperVO.makeAttendanceEventVO(day, event.getEventNo(), vo, false);
					res.add(vo);
					continue;
				}
			}
			
			List<AttendanceEventResource> attendanceRS = gameResource.getAttendanceEvent().get(event.getEventNo(), day + 1);
			if(attendanceRS == null || attendanceRS.isEmpty())
				continue;
			
			int curDay = TimeCalculation.getIntTime();
			List<RewardItemVO> rewards = new ArrayList<>();
			
			for(AttendanceEventResource rs : attendanceRS)
			{
				if(event.getStartDate() <= curDay && event.getEndDate() >= curDay)
				{
					rewards.add(mapperVO.makeRewardItemVO(rs.getItemType(), rs.getItemId(), rs.getItemCount()));
				}
			}
			
			if(rewards.size() > 0)
			{
				MailTitle mailRS = gameResource.getMail().get(ConstantVal.MAIL_TEXT_EVENT_REWARD, user.getLanguage());
				mailService.SendMails(userId, ConstantVal.MAIL_TYPE_NORMAL, mailRS.title, mailRS.contents, TimeCalculation.getCurrentUnixTime() + ConstantVal.MAIL_EXPIRE_TIME, rewards);
				
				AttendanceEventRewardLog log = new AttendanceEventRewardLog();
				log.setPtn_month((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_MONTH, 0));
				log.setPtn_day((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_DAY, 0));
				log.setLog_time(TimeCalculation.getCurrentUnixTime());
				log.setUser_id(userId);
				log.setEvent_no(event.getEventNo());
				log.setEvent_day(day + 1);
				log.setReward_day(TimeCalculation.getIntTime());
				
				repositoryService.setAttendanceEventLog(userId, log);
				mapperVO.makeAttendanceEventVO(day + 1, event.getEventNo(), vo, true);
			}
			else
				continue;
			
			res.add(vo);
		}
		
		return res;
	}
	
	public void checkEvent(int userId, byte userType) throws Exception
	{
		List<Event> events = repositoryService.getEvent();
		if(events == null || events.isEmpty())
			return;
		
		List<EventLog> logs = repositoryService.getEventLog(userId);
		if(logs == null)
			logs = new ArrayList<>();
		
		User user = repositoryService.getUser(userId, false);
		if(user == null)
			return;
		
		String language = user.getLanguage();
		
		//여기서 보상 지급
		List<RewardItemVO> rewardItems = null;
		
		for(Event event : events)
		{
			rewardItems = new ArrayList<>();
			
			//시작되지 않은 이벤트는 제외
			if(event.getValid() < ConstantVal.ENABLE)
				continue;
			
			//이벤트 기간중이냐 체크
			if(!TimeCalculation.checkTimeNow(event.getStartTime(), event.getEndTime()))
				continue;
			
			// 이벤트 보상을 받았는지 로그를 확인하여 체크
			if(eventRewardCheck(event.getEventId(), logs))
				continue;
			
			// 이벤트 타입 체크 (0: 모든유저, 1: 신규유저, 2: 복귀유저)
			if(event.getEventType() != ConstantVal.DEFAULT_USER && event.getEventType() != userType)
				continue;
			
			if(!event.getItem0().isEmpty())
				rewardItems.add(makeEventItemVO(event.getItem0()));
			
			if(!event.getItem1().isEmpty())
				rewardItems.add(makeEventItemVO(event.getItem1()));
			
			if(!event.getItem2().isEmpty())
				rewardItems.add(makeEventItemVO(event.getItem2()));
			
			if(!event.getItem3().isEmpty())
				rewardItems.add(makeEventItemVO(event.getItem3()));
			
			if(!event.getItem4().isEmpty())
				rewardItems.add(makeEventItemVO(event.getItem4()));
			
			if(rewardItems.size() > ConstantVal.DEFAULT_ZERO)
			{
				MailTitle mailRS = gameResource.getMail().get(ConstantVal.MAIL_TEXT_EVENT_REWARD, language);
				mailService.SendMails(userId, ConstantVal.MAIL_TYPE_NORMAL, event.getEventName(), mailRS.contents, TimeCalculation.getCurrentUnixTime() + ConstantVal.MAIL_EXPIRE_TIME, rewardItems);
				
				//log 추가
				EventLog log = new EventLog();
				log.setEvent_id(event.getEventId());
				log.setUser_id(userId);
				log.setEvent_name(event.getEventName());
				
				//eventLogs 로그 추가
				repositoryService.setEventLog(log);
			}
		}
	}
	
	private boolean eventRewardCheck(int eventId, List<EventLog> logs)
	{
		for(EventLog log : logs)
		{
			if(log.getEvent_id() == eventId)
				return true;
		}
		
		return false;
	}
	
	private RewardItemVO makeEventItemVO(String item)
	{
		RewardItemVO vo = new RewardItemVO();
		String[] str = item.split("\\|");
		if(str.length > ConstantVal.DEFAULT_ZERO)
		{
			vo.itemType = Integer.parseInt(str[0]);
			vo.itemId = Integer.parseInt(str[1]);
			vo.itemCount = Integer.parseInt(str[2]);
			vo.grade = str.length > 3 ? Integer.parseInt(str[3]) : null;
			vo.level = str.length > 4 ? Integer.parseInt(str[4]) : null;
			vo.upGrade = str.length > 5 ? Integer.parseInt(str[5]) : null;
			vo.skill1 = str.length > 6 ? Integer.parseInt(str[6]) : null;
			vo.skill2 = str.length > 7 ? Integer.parseInt(str[7]) : null;
			vo.skill3 = str.length > 8 ? Integer.parseInt(str[8]) : null;
		}
		return vo;
	}
	
	private void CheckBotPartner(int userId) throws Exception
	{
		List<Partner> partners = repositoryService.getPartners(userId, false);
		if(!partners.isEmpty())
			return;
		
		Preset preset = repositoryService.getPreset(userId, false);
		if(preset == null)
			return;
		
		List<Character> characters = repositoryService.getCharacters(userId, false);
		if(characters.isEmpty())
			return;
		
		int characterIndex = findData.findCharacterIndex(characters, preset.getCharacterUid());
		if(characterIndex <= ConstantVal.DEFAULT_VALUE)
			return;
		
		SkillPartnerGroupResource groupRS = gameResource.getPartnerGroupSkill().get(characters.get(characterIndex).getCharacterId());
		if(groupRS == null)
			return;
		
		cardService.AddPartner(userId, mapperVO.makePartner(userId, groupRS.getIdPartner1(), ConstantVal.CARD_GRADE_R), partners, "TEST");
		cardService.AddPartner(userId, mapperVO.makePartner(userId, groupRS.getIdPartner2(), ConstantVal.CARD_GRADE_R), partners, "TEST");
		cardService.AddPartner(userId, mapperVO.makePartner(userId, groupRS.getIdPartner3(), ConstantVal.CARD_GRADE_R), partners, "TEST");
		
		preset.setPartner1Uid(partners.get(0).getUid());
		preset.setPartner2Uid(partners.get(1).getUid());
		preset.setPartner3Uid(partners.get(2).getUid());
		
		repositoryService.setPreset(userId, preset);
	}

	private String getClientRegion(String clientIp)
	{
		Location clientLocation = null;
		String clientCountryCode = "";
		
		try
		{
			clientLocation = GeoIP2.getInstance().lookup(clientIp);
			if(clientLocation != null)
				clientCountryCode = clientLocation.getCountryCode();
		} 
		catch (Exception e) 
		{
			clientCountryCode = "KR";
			return clientCountryCode;
		}
		
		return clientCountryCode;
	}
}
