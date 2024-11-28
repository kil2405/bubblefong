package com.bubbleShooter.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.VO.AccountVO;
import com.bubbleShooter.VO.AttendanceEventVO;
import com.bubbleShooter.VO.AttendanceRewardVO;
import com.bubbleShooter.VO.AttendanceVO;
import com.bubbleShooter.VO.CharacterVO;
import com.bubbleShooter.VO.GachaRewardVO;
import com.bubbleShooter.VO.ItemVO;
import com.bubbleShooter.VO.MailVO;
import com.bubbleShooter.VO.MissionVO;
import com.bubbleShooter.VO.NoticeVO;
import com.bubbleShooter.VO.PartnerVO;
import com.bubbleShooter.VO.PresetVO;
import com.bubbleShooter.VO.ProfileVO;
import com.bubbleShooter.VO.RankerVO;
import com.bubbleShooter.VO.RewardItemVO;
import com.bubbleShooter.VO.ShopProductRateVO;
import com.bubbleShooter.VO.ShopVO;
import com.bubbleShooter.VO.UserBaseVO;
import com.bubbleShooter.common.BubbleException;
import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.ErrorCodeInfo;
import com.bubbleShooter.common.GameResource;
import com.bubbleShooter.common.RepositoryService;
import com.bubbleShooter.controller.card.CardService;
import com.bubbleShooter.controller.mail.MailService;
import com.bubbleShooter.controller.rank.RankService;
import com.bubbleShooter.controller.shop.ShopService;
import com.bubbleShooter.domain.Character;
import com.bubbleShooter.domain.GachaReward;
import com.bubbleShooter.domain.Mail;
import com.bubbleShooter.domain.Mission;
import com.bubbleShooter.domain.Notice;
import com.bubbleShooter.domain.Partner;
import com.bubbleShooter.domain.Preset;
import com.bubbleShooter.domain.Profile;
import com.bubbleShooter.domain.User;
import com.bubbleShooter.domain.UserItem;
import com.bubbleShooter.relation.MailItem;
import com.bubbleShooter.resource.AttendanceDailyResource;
import com.bubbleShooter.resource.AttendanceEventResource;
import com.bubbleShooter.resource.CharacterResource;
import com.bubbleShooter.resource.EventResource;
import com.bubbleShooter.resource.ItemResource;
import com.bubbleShooter.resource.MissionResource;
import com.bubbleShooter.resource.PartnerResource;
import com.bubbleShooter.resource.ProfileResource;
import com.bubbleShooter.resource.ShopRandomResource;
import com.bubbleShooter.resource.ShopResource;
import com.bubbleShooter.resource.UserExpResource;

@Component
public class MapperVO
{
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private GameResource gameResource;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private RankService rankService;
	
	@Autowired
	private FindData findData;
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private ShopService shopService;
	
	public AccountVO makeAccountVO(User user) throws Exception
	{
		int userId = user.getId();
		AccountVO accountVO = new AccountVO();
		
		makeUserBase(userId, user, accountVO);
		
		UserExpResource nextLevelExpRS = gameResource.getUserExp().get(user.getLevel() + 1);
		if(nextLevelExpRS == null)
		{
			if(user.getLevel() >= ConstantVal.USER_ACCOUNT_MAX_LEVEL)
				nextLevelExpRS = new UserExpResource(ConstantVal.DEFAULT_VALUE, ConstantVal.DEFAULT_VALUE, ConstantVal.DEFAULT_VALUE, ConstantVal.DEFAULT_VALUE);
		}
		
		UserExpResource expRS = gameResource.getUserExp().get(user.getLevel());
		if(expRS == null)
			return null;
		
		accountVO.level = user.getLevel();
		accountVO.wallet = user.getWallet();
		accountVO.exp = user.getExp();
		accountVO.vip = user.getVip();
		accountVO.maxExp = nextLevelExpRS.getValue();
		accountVO.preLevelMaxExp = expRS.getValue();
		accountVO.tier = user.getTier();
		accountVO.score = user.getRankPoint();
		if(user.getLevel() >= ConstantVal.USER_ACCOUNT_MAX_LEVEL)
		{
			accountVO.maxExp = ConstantVal.DEFAULT_VALUE;
			accountVO.preLevelMaxExp = ConstantVal.DEFAULT_VALUE;
		}
		accountVO.region = user.getRegion();
		accountVO.remainTime = TimeCalculation.diffOfUnixTime(TimeCalculation.doDateUnixTime(ConstantVal.DATE_SECTION_DAY, 1));
		return accountVO;
	}
	
	public void makeUserBase(int userId, User user, UserBaseVO userBaseVO) throws Exception
	{
		if(user == null)
			user = repositoryService.getUser(userId, false);
		
		userBaseVO.userId = user.getId();
		userBaseVO.uuid = user.getUuid();
		userBaseVO.nickname = user.getNickname();
	}
	
	public List<ItemVO> makeItemVO(int userId, List<UserItem> items, boolean dbForceSearch) throws Exception
	{
		if(items == null)
		{
			items = repositoryService.getUserItems(userId, dbForceSearch);
			if(items == null || items.isEmpty())
				return null;
		}
		
		//아이템 종료가 추가되었을 경우 userItem table에 추가된 값을 넣어준다.
		List<ItemResource> itemRS = gameResource.getItem().getItemList();
		if(items.size() != itemRS.size())
		{
			for(ItemResource rs : itemRS)
			{
				if(findData.findUserItemIndex(rs.getItemType(), rs.getItemId(), items) <= ConstantVal.DEFAULT_VALUE)
				{
					UserItem userItem = new UserItem();
					userItem.setUserId(userId);
					userItem.setItemId(rs.getItemId());
					userItem.setItemType(rs.getItemType());
					userItem.setItemCount(ConstantVal.DEFAULT_ZERO);
					
					items.add(userItem);
					repositoryService.setUserItem(userId, findData.findUserItemIndex(userItem.getItemType(), userItem.getItemId(), items), items);;
				}
			}
		}
		
		List<ItemVO> itemVOs = new ArrayList<>();
		for(UserItem item : items)
		{
			ItemVO vo = new ItemVO();
			vo.itemType = item.getItemType();
			vo.itemId = item.getItemId();
			vo.itemCount = item.getItemCount();
			itemVOs.add(vo);
		}
		return itemVOs;
	}
	
	public List<CharacterVO> makeCharacterVO(int userId, List<Character> characters, boolean dbForceSearch) throws Exception
	{
		if(characters == null)
		{
			characters = repositoryService.getCharacters(userId, dbForceSearch);
			if(characters == null || characters.isEmpty())
				return null;
		}
		
		List<CharacterVO> characterVOs = new ArrayList<>();
		for(Character character : characters)
		{
			if(character.getUseOrNot() <= ConstantVal.IS_FALSE)
				continue;
			
			CharacterVO vo = new CharacterVO();
			vo.uid = character.getUid();
			vo.characterId = character.getCharacterId();
			vo.grade = character.getGrade();
			vo.level = character.getCharacterLevel();
			vo.upgrade = character.getCharacterUpgrade();
			vo.activeSkill = character.getActiveSkill();
			vo.dailyearn = character.getDailyearn();
			vo.dailyearnlimit = character.getDailyearnlimit();
			vo.isLock = character.getIsLock();
			vo.isNft = character.getIsNft();
			vo.isNew = character.getIsNew();
			vo.isMission = character.getIsMission();
			
			characterVOs.add(vo);
		}
		return characterVOs;
	}

	
	public List<PartnerVO> makePartnerVO(int userId, List<Partner> partners, boolean dbForceSearch) throws Exception
	{
		List<PartnerVO> partnerVOs = new ArrayList<>();
		if(partners == null)
		{
			partners = repositoryService.getPartners(userId, dbForceSearch);
			if(partners == null || partners.isEmpty())
				return partnerVOs;
		}
		
		for(Partner partner : partners)
		{
			if(partner.getUseOrNot() <= ConstantVal.IS_FALSE)
				continue;
			
			PartnerVO vo = new PartnerVO();
			vo.partnerId = partner.getPartnerId();
			vo.uid = partner.getUid();
			vo.grade = partner.getGrade();
			vo.level = partner.getPartnerLevel();
			vo.upgrade = partner.getUpgrade();
			vo.skill1 = partner.getSkill1();
			vo.skill2 = partner.getSkill2();
			vo.skill3 = partner.getSkill3();
			vo.isLock = partner.getIsLock();
			vo.isNft = partner.getIsNft();
			vo.isNew = partner.getIsNew();
			vo.isMission = partner.getIsMission();
			
			partnerVOs.add(vo);
		}
		return partnerVOs;
	}
	
	public List<NoticeVO> makeNoticeVO(List<Notice> notice) throws Exception
	{
		if(notice == null)
			notice = repositoryService.getNotice();
		
		List<NoticeVO> vos = new ArrayList<>();
		for(Notice no : notice)
		{
			if(no.getValid() <= ConstantVal.DEFAULT_ZERO)
				continue;
			
			if(!TimeCalculation.checkTimeNow(no.getStartTime(), no.getEndTime()))
				continue;
				
			NoticeVO vo = new NoticeVO();
			vo.noticeTitle = no.getNoticeTitle();
			vo.type = no.getType();
			vo.text = no.getText();
			vo.imageAddress = no.getImageAddress();
			vo.remainTime = (int)TimeCalculation.diffOfUnixTime(no.getEndTime());
			vo.sortNumber = no.getSortNumber();
			
			vos.add(vo);
		}
		
		return vos;
	}
	
	public PresetVO makePresetVO(int userId, Preset preset, boolean dbForceSearch) throws Exception
	{
		if(preset == null)
		{
			preset = repositoryService.getPreset(userId, dbForceSearch);
			if(preset == null)
				return null;
		}
		
		PresetVO presetVO = new PresetVO();
		presetVO.characterUid = preset.getCharacterUid();
		presetVO.partner1Uid = preset.getPartner1Uid();
		presetVO.partner2Uid = preset.getPartner2Uid();
		presetVO.partner3Uid = preset.getPartner3Uid();
		
		return presetVO;
	}
	
	// mail 공용�로 �용�때
	public List<MailVO> makeMailsVO(int userId, List<Mail> mails) throws Exception
	{
		if(mails == null)
		{
			mails = repositoryService.getMail(userId);
			if(mails == null)
				return null;
		}
		
		List<MailVO> mailVOs = new ArrayList<MailVO>();
		for(Mail mail : mails)
		{
			if(mail.getExpiredTime() < TimeCalculation.getCurrentUnixTime())
				continue;
			
			if(mail.getIs_expired() >= ConstantVal.MAIL_EXPIRED_REMOVE)
				continue;
			
			mailVOs.add(makeMailVO(userId, mail));
		}
		
		return mailVOs;
	}
	
	public MailVO makeMailVO(int userId, Mail mail) throws Exception
	{
		MailVO vo = new MailVO();
		
		vo.mailId = mail.getMailId();
		vo.mailType = mail.getMailType();
		vo.title = mail.getTitle();
		vo.description = mail.getDescription();
		vo.state = (byte)mail.getState();
		vo.mailItem = mailService.makeMailItems(mail);
		vo.receiveTime = mail.getReceiveTime();
		vo.createTime = mail.getCreateTime();
		vo.remainTime = (int)TimeCalculation.diffOfUnixTime(mail.getExpiredTime());
		if(vo.remainTime <= ConstantVal.DEFAULT_ZERO)
			vo.remainTime = ConstantVal.DEFAULT_ZERO;
		
		return vo;
	}
	
	public RewardItemVO makeRewardItemVO(int itemType, int itemId, int itemCount, Integer grade, Integer level, Integer upGrade, Integer skill1, Integer skill2, Integer skill3)
	{
		RewardItemVO value = new RewardItemVO();
		value.itemType = itemType;
		value.itemId = itemId;
		value.itemCount = itemCount;
		if(grade != null)
			value.grade = grade;
		if(level != null)
			value.level = level;
		if(upGrade != null)
			value.upGrade = upGrade;
		if(skill1 != null)
			value.skill1 = skill1;
		if(skill2 != null)
			value.skill2 = skill2;
		if(skill3 != null)
			value.skill3 = skill3;

		return value;
	}
	
	public RewardItemVO makeRewardItemVO(int itemType, int itemId, int itemCount, Integer grade)
	{
		RewardItemVO value = new RewardItemVO();
		value.itemType = itemType;
		value.itemId = itemId;
		value.itemCount = itemCount;
		if(grade != null)
			value.grade = grade;
		
		return value;
	}
	
	public RewardItemVO makeRewardItemVO(int itemType, int itemId, int itemCount)
	{
		RewardItemVO value = new RewardItemVO();
		value.itemType = itemType;
		value.itemId = itemId;
		value.itemCount = itemCount;

		return value;
	}
	
	public RewardItemVO makeRewardItemVO(MailItem item)
	{
		if(item == null)
			return null;
		
		switch(item.getItemType())
		{
		case ConstantVal.ITEM_TYPE_CHARACTER:
			CharacterResource charRS = gameResource.getCharacter().get(item.getItemId());
			if(charRS == null)
				return null;
			
			if(item.getSkill1() != charRS.getIdCharacterActive())
				return null;
			
			break;
			
		case ConstantVal.ITEM_TYPE_PARTNER:
			PartnerResource partnerRS = gameResource.getPartner().get(item.getItemId());
			if(partnerRS == null)
				return null;
			
			break;
		}
		
		RewardItemVO vo = new RewardItemVO();
		vo.itemType = item.getItemType();
		vo.itemId = item.getItemId();
		vo.itemCount = item.getItemCount();
		
		if(item.getGrade() != null)
			vo.grade = item.getGrade();
		if(item.getLevel() != null)
			vo.level = item.getLevel();
		if(item.getUpGrade() != null)
			vo.upGrade = item.getUpGrade();
		if(item.getSkill1() != null)
			vo.skill1 = item.getSkill1();
		if(item.getSkill2() != null)
			vo.skill2 = item.getSkill2();
		if(item.getSkill3() != null)
			vo.skill3 = item.getSkill3();
		
		return vo;
	}
	
	
	public Character makeCharacter(int userId, RewardItemVO reward)
	{
		CharacterResource characterRS = gameResource.getCharacter().get(reward.itemId);
		if(characterRS == null)
			return null;
		
		Character character = new Character();
		
		character.setUid(UUID.randomUUID().toString().replace("-", ""));
		character.setUserId(userId);
		character.setCharacterId(reward.itemId);
		character.setGrade(reward.grade == null ? null : reward.grade);
		character.setCharacterLevel(reward.level == null ? 1 : reward.level);
		character.setCharacterUpgrade(reward.upGrade == null ? 0 : reward.upGrade);
		character.setActiveSkill(characterRS.getIdCharacterActive());
		character.setIsLock(ConstantVal.IS_FALSE);
		character.setDailyearn(ConstantVal.DEFAULT_ZERO);
		character.setDailyearnlimit(characterRS.getIsNft() == ConstantVal.IS_TRUE ? ConstantVal.DAILYEARN_LIMIT_BY_GRADE[reward.grade][character.getCharacterUpgrade()] : ConstantVal.DEFAULT_VALUE);
		character.setIsReward(ConstantVal.IS_FALSE);
		character.setIsNft(characterRS.getIsNft());
		character.setIsNew(ConstantVal.IS_TRUE);
		character.setIsMission(ConstantVal.IS_FALSE);
		character.setUseOrNot(ConstantVal.IS_TRUE);
		
		return character;
	}
	
	//루아 캐릭터 지급 (루아의 등급은 N등급)
	public Character makeCharacter(int userId, int characterId)
	{
		CharacterResource characterRS = gameResource.getCharacter().get(characterId);
		if(characterRS == null)
			return null;
		
		Character newCharacter = new Character();
		newCharacter.setUid(UUID.randomUUID().toString().replace("-", ""));
		newCharacter.setUserId(userId);
		newCharacter.setCharacterId(characterId);
		newCharacter.setGrade(ConstantVal.CARD_GRADE_N);
		newCharacter.setCharacterLevel(1);
		newCharacter.setCharacterUpgrade(ConstantVal.DEFAULT_ZERO);
		newCharacter.setActiveSkill(characterRS.getIdCharacterActive());
		newCharacter.setIsLock(ConstantVal.IS_FALSE);
		newCharacter.setDailyearn(ConstantVal.DEFAULT_ZERO);
		newCharacter.setDailyearnlimit(characterRS.getIsNft() == ConstantVal.IS_TRUE ? ConstantVal.DAILYEARN_LIMIT_BY_GRADE[newCharacter.getGrade()][newCharacter.getCharacterUpgrade()] : ConstantVal.DEFAULT_VALUE);
		newCharacter.setIsReward(ConstantVal.IS_FALSE);
		newCharacter.setIsNft(characterRS.getIsNft());
		newCharacter.setIsNew(ConstantVal.IS_TRUE);
		newCharacter.setIsMission(ConstantVal.IS_FALSE);
		newCharacter.setUseOrNot(ConstantVal.IS_TRUE);
		
		return newCharacter;
	}
	
	//NFT캐릭터 지급 // Grade R 이상
	public Character makeCharacter(int userId, int characterId, int grade)
	{
		CharacterResource characterRS = gameResource.getCharacter().get(characterId);
		if(characterRS == null)
			return null;
		
		Character newCharacter = new Character();
		newCharacter.setUid(UUID.randomUUID().toString().replace("-", ""));
		newCharacter.setUserId(userId);
		newCharacter.setCharacterId(characterId);
		newCharacter.setGrade(grade);
		newCharacter.setCharacterLevel(1);
		newCharacter.setCharacterUpgrade(ConstantVal.DEFAULT_ZERO);
		newCharacter.setActiveSkill(characterRS.getIdCharacterActive());
		newCharacter.setIsLock(ConstantVal.IS_FALSE);
		newCharacter.setDailyearn(ConstantVal.DEFAULT_ZERO);
		newCharacter.setDailyearnlimit(characterRS.getIsNft() == ConstantVal.IS_TRUE ? ConstantVal.DAILYEARN_LIMIT_BY_GRADE[grade][newCharacter.getCharacterUpgrade()] : ConstantVal.DEFAULT_VALUE);
		newCharacter.setIsReward(ConstantVal.IS_FALSE);
		newCharacter.setIsNft(characterRS.getIsNft());
		newCharacter.setIsNew(ConstantVal.IS_TRUE);
		newCharacter.setIsMission(ConstantVal.IS_FALSE);
		newCharacter.setUseOrNot(ConstantVal.IS_TRUE);
		
		return newCharacter;
	}
	
	public Partner makePartner(int userId, RewardItemVO reward)
	{
		PartnerResource partnerRS = gameResource.getPartner().get(reward.itemId);
		if(partnerRS == null)
			return null;
		
		Partner partner = new Partner();
		
		partner.setUserId(userId);
		partner.setUid(UUID.randomUUID().toString().replace("-", ""));
		partner.setPartnerId(reward.itemId);
		partner.setGrade(reward.grade == null ? null : reward.grade);
		partner.setPartnerLevel(reward.level == null ? 1 : reward.level );
		partner.setUpgrade(reward.upGrade == null ? 0 : reward.upGrade);
		partner.setSkill1(reward.skill1 == null ? -1 : reward.skill1);
		partner.setSkill2(reward.skill2 == null ? -1 : reward.skill2);
		partner.setSkill3(reward.skill3 == null ? -1 : reward.skill3);
		partner.setIsLock(ConstantVal.IS_FALSE);
		partner.setIsNft(partnerRS.getIsNft());
		partner.setIsNew(ConstantVal.IS_TRUE);
		partner.setIsMission(ConstantVal.IS_FALSE);
		partner.setUseOrNot(ConstantVal.IS_TRUE);
		
		return partner;
	}
	
	public Partner makePartner(int userId, int partnerId, int grade)
	{
		PartnerResource partnerRS = gameResource.getPartner().get(partnerId);
		if(partnerRS == null)
			return null;
		
		Partner newPartner = new Partner();
		newPartner.setUserId(userId);
		newPartner.setUid(UUID.randomUUID().toString().replace("-", ""));
		newPartner.setPartnerId(partnerId);
		newPartner.setGrade(grade);
		newPartner.setPartnerLevel(1);
		newPartner.setUpgrade(ConstantVal.DEFAULT_ZERO);
		newPartner.setSkill1(ConstantVal.DEFAULT_VALUE);
		newPartner.setSkill2(ConstantVal.DEFAULT_VALUE);
		newPartner.setSkill3(ConstantVal.DEFAULT_VALUE);
		newPartner.setIsLock(ConstantVal.IS_FALSE);
		newPartner.setIsNft(partnerRS.getIsNft());
		newPartner.setIsNew(ConstantVal.IS_TRUE);
		newPartner.setIsMission(ConstantVal.IS_FALSE);
		newPartner.setUseOrNot(ConstantVal.IS_TRUE);
		
		return newPartner;
	}
	
	//rewards�는 보상값을 �어주면�다(차감경우 itemcount�- 롴주멜다)
	public List<RewardItemVO> makeRewardResult(int userId, List<UserItem> userItems, List<RewardItemVO> rewards, String logType) throws Exception
	{
		return makeRewardResult(userId, userItems, rewards, logType, "");
	}
	
	public List<RewardItemVO> makeRewardResult(int userId, List<UserItem> userItems, List<RewardItemVO> rewards, String logType, String desc) throws Exception
	{
		List<RewardItemVO> newRewards = new ArrayList<>();
		
		if(rewards.isEmpty())
			return newRewards;
		
		if(userItems == null)
			userItems = repositoryService.getUserItems(userId, false);
		
		List<Byte> updateNum = new ArrayList<>();
		byte itemIndex = ConstantVal.DEFAULT_VALUE;
		
		for(int index = 0; index < rewards.size(); index++)
		{
			RewardItemVO reward = rewards.get(index);
			switch(reward.itemType)
			{
			case ConstantVal.ITEM_TYPE_GOLD:
			case ConstantVal.ITEM_TYPE_RUBY:
			case ConstantVal.ITEM_TYPE_DIAMOND:
			case ConstantVal.ITEM_TYPE_ITEM:
			case ConstantVal.ITEM_TYPE_TROPHY:
			case ConstantVal.ITEM_TYPE_RANDOM_PACK_TICKET:
				itemIndex = findData.findUserItemIndex(reward.itemType, reward.itemId, userItems);
				if(!repositoryService.setChangedItem(userId, itemIndex, userItems, reward.itemCount, logType, desc, false))
					throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MAPPERVO_1401);
				
				updateNum.add(itemIndex);
				newRewards.add(reward);
				
				break;
				
			case ConstantVal.ITEM_TYPE_CHARACTER:
				Character character = makeCharacter(userId, reward);
				if(character == null)
					continue;

				cardService.AddCharacter(userId, character, null, logType);
				newRewards.add(reward);
				break;
				
			case ConstantVal.ITEM_TYPE_PARTNER:
				Partner partner = makePartner(userId, reward);
				if(partner == null)
					continue;
				
				cardService.AddPartner(userId, partner, null, logType);
				
				newRewards.add(reward);
				break;
			
			
			case ConstantVal.ITEM_TYPE_RANDOM_MAIL_PACK:
				List<RewardItemVO> boxRewards = shopService.openBox(userId, reward.itemId, reward.itemCount, true);
				if(boxRewards.size() > 0)
				{
					for(int i = 0; i < boxRewards.size(); i++)
						newRewards.add(boxRewards.get(i));
				}
				break;
				
			case ConstantVal.ITEM_TYPE_EMOTICON:
				break;
				
			default:
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_MAPPERVO_1402);
			}
		}
		
		for(int i = 0; i < updateNum.size(); i++)
			repositoryService.setUserItem(userId, updateNum.get(i), userItems);

		return newRewards;
	}
	
	public RankerVO makeUserRankerVO(int userId, User user) throws Exception
	{
		RankerVO rankerVO = new RankerVO();
		
		if(user == null)
			user = repositoryService.getUser(userId, false);
		
		rankerVO.userId = userId;
		rankerVO.nickname = user.getNickname();
		rankerVO.tier = user.getTier();
		rankerVO.score = user.getRankPoint();
		 
		rankerVO.ranking = rankService.getUserRanking(userId, true);
		if(rankerVO.ranking == 0)
			rankerVO.ranking = ConstantVal.DEFAULT_VALUE;
		
		return rankerVO;
	}
	
	public ShopVO makeShopVO(long userId, ShopResource resource) throws Exception
	{
		ShopVO vo = new ShopVO();
		vo.productId = resource.getProductId();
		vo.category = (byte)resource.getCategory();
		vo.productName = resource.getProductName();
		vo.productIcon = resource.getProductIcon();
		
		vo.isTicket = (byte)resource.getIsTicket();
		vo.ticketId = resource.getTicketId();
		
		vo.priceItemType = (byte)resource.getPriceItemType();
		vo.priceItemId = resource.getPriceItemId();
		vo.price = resource.getPrice();
		
		vo.discountRate = resource.getDiscountRate();
		vo.discountPrice = resource.getDiscountPrice();
		vo.sellType = (byte)resource.getSellType();
		vo.buyLimit = resource.getBuyLimit();
		vo.sellStartDate = resource.getSellStartDate();
		vo.sellEndDate = resource.getSellEndDate();
		vo.gatchaType = resource.getGachaType();
		vo.sortNumber = (byte)resource.getSortNumber();
		
		String[] temp = resource.getTag().split(",");
		vo.tag = new ArrayList<>();
		for(int i = 0; i < temp.length; i++)
			vo.tag.add(Byte.parseByte(temp[i]));
		
		return vo;
	}
	
	public ShopProductRateVO makeShopProductRateVO(ShopRandomResource resource) throws Exception
	{
		ShopProductRateVO vo = new ShopProductRateVO();
		
		vo.itemId = resource.getItemId();
		vo.itemType = (byte)resource.getItemType();
		vo.minCount = resource.getMinCount();
		vo.maxCount = resource.getMaxCount();
		vo.rate = (float)resource.getRate() / 1000;
		
		return vo;
	}
	
	public void makeAttendanceVO(int attendanceDay, AttendanceVO vo) throws Exception
	{
		vo.attendanceDay = attendanceDay;
		vo.attendanceBoard = makeAttendanceRewardVO();
		vo.isReward = true;
	}
	
	public List<AttendanceRewardVO> makeAttendanceRewardVO()
	{
		List<AttendanceRewardVO> arVOList = new ArrayList<>();
		
		for(int index = 1; index <= gameResource.getAttendanceDaily().getAttendanceMaxCount(); index++)
		{
			AttendanceRewardVO arVO = new AttendanceRewardVO();
			
			AttendanceDailyResource attendanceResourceRS = gameResource.getAttendanceDaily().get(index);
			if(attendanceResourceRS == null)
				return null;
			
			arVO.days = attendanceResourceRS.getDays();
			arVO.nextIndex = attendanceResourceRS.getNextIndex();
			arVO.itemId = attendanceResourceRS.getItemId();
			arVO.itemType = attendanceResourceRS.getItemType();
			arVO.itemIcon = attendanceResourceRS.getItemIcon();
			arVO.itemCount = attendanceResourceRS.getItemCount();
			
			arVOList.add(arVO);
		}
		return arVOList;
	}
	
	public void makeAttendanceEventVO(int day, int eventId, AttendanceEventVO vo, boolean isReward) throws Exception
	{
		List<AttendanceEventResource> rs = gameResource.getAttendanceEvent().get(eventId, day);
		if(rs == null || rs.isEmpty())
			return;
		
		EventResource eventRS = gameResource.getEvent().get(eventId);
		if(eventRS == null)
			return;
		
		vo.index = rs.get(0).getEventNo();
		vo.startDay = (int)eventRS.getStartDate();
		vo.endDay = (int)eventRS.getEndDate();
		vo.attendanceDay = day;
		vo.uiType = eventRS.getUiType();
		vo.eventTitle = eventRS.getEventTitle();
		vo.attendanceBoard = gameResource.getAttendanceEvent().AttendanceEventRewards(eventId);
		vo.isReward = isReward;
	}
	
	public List<MissionVO> makeMissionVO(List<Mission> missions) throws Exception
	{
		List<MissionVO> vos = new ArrayList<>();
		
		for(int i = 0; i < missions.size(); i++)
		{
			if(missions.get(i).getStatus() == ConstantVal.MISSION_STATUS_REWARD)
				continue;
			
			if(missions.get(i).getMissionId() <= ConstantVal.DEFAULT_ZERO)
				continue;
			
			MissionVO vo = new MissionVO();
			
			MissionResource missionRS = gameResource.getMission().get(missions.get(i).getMissionId());
			if(missionRS == null)
				return null;
			
			vo.missionId = missions.get(i).getMissionId();
			vo.difficulty = missionRS.getDifficulty();
			vo.missionTitleId = missionRS.getMissionTitle();
			vo.missionDetail = missionRS.getMissionDetail();
			vo.heroType = missionRS.getHeroType();
			vo.heroId = missionRS.getHeroId();
			vo.heroGrade = missionRS.getHeroGrade();
			vo.heroLevel = missionRS.getHeroLevel();
			vo.time = missionRS.getTime();
			
			//기본 보상 처리
			vo.basicReward = new ArrayList<>();
			if(missionRS.getbType1() > ConstantVal.DEFAULT_VALUE)
				vo.basicReward.add(makeRewardItemVO(missionRS.getbType1(), missionRS.getBasicReward1(), missionRS.getbCount1()));
			
			if(missionRS.getbType2() > ConstantVal.DEFAULT_VALUE)
				vo.basicReward.add(makeRewardItemVO(missionRS.getbType2(), missionRS.getBasicReward2(), missionRS.getbCount2()));
			
			//대박 보상 처리
			vo.grandReward = new ArrayList<>();
			if(missionRS.getgType1() > ConstantVal.DEFAULT_VALUE)
				vo.grandReward.add(makeRewardItemVO(missionRS.getgType1(), missionRS.getGrandReward1(), missionRS.getgCount1()));
			
			if(missionRS.getgType2() > ConstantVal.DEFAULT_VALUE)
				vo.grandReward.add(makeRewardItemVO(missionRS.getgType2(), missionRS.getGrandReward2(), missionRS.getgCount2()));
			
			if(missionRS.getgType3() > ConstantVal.DEFAULT_VALUE)
				vo.grandReward.add(makeRewardItemVO(missionRS.getgType3(), missionRS.getGrandReward3(), missionRS.getgCount3()));
			
			vo.status = missions.get(i).getStatus();
			if(vo.status != ConstantVal.MISSION_STATUS_DEFAULT)
			{
				vo.useHeroType = missions.get(i).getHeroType();
				vo.uuid = missions.get(i).getHeroUUid();
				
				if(vo.status == ConstantVal.MISSION_STATUS_START)
				{
					vo.remainTime = missionRS.getTime() - (int)TimeCalculation.diffOfUnixTime(missions.get(i).getMissionStartTime(), TimeCalculation.getCurrentUnixTime());
					
					if(vo.remainTime < 0)
					{
						vo.status = ConstantVal.MISSION_STATUS_COMPLETE;
						vo.remainTime = 0;
						
						missions.get(i).setStatus(ConstantVal.MISSION_STATUS_COMPLETE);
						repositoryService.setMission(missions.get(i).getUserId(), i, missions);
					}
				}
			}
			
			vos.add(vo);
		}
		
		return vos;
	}
	
	public List<GachaRewardVO> makeGachaRewardVO(List<GachaReward> rewards)
	{
		if(rewards == null)
			return null;
		
		List<GachaRewardVO> vos = new ArrayList<>();
		for(GachaReward data : rewards)
		{
			GachaRewardVO vo = new GachaRewardVO();
			vo.seq = data.getSeq();
			vo.gachaId = data.getGachaId();
			vo.grade = data.getGrade();
			vo.itemType = data.getItemType();
			vo.itemId = data.getItemId();
			vo.itemCount = data.getItemCount();
			vo.heroGrade = data.getHeroGrade();
			vo.remainCount = data.getTotalCount() - data.getPrizeCount();
			vo.totalCount = data.getTotalCount();
			
			vos.add(vo);
		}
		return vos;
	}

	public List<ProfileVO> makeProfileVO(List<Profile> profiles) throws Exception
	{
		if(profiles == null)
			return null;
		
		List<ProfileVO> vos = new ArrayList<>();
		for(Profile data : profiles)
		{
			 ProfileResource pfData = gameResource.getProfile().get(data.getProfileId());
            if(pfData == null)
				return null;
			
			if(pfData.getStartDate() > 0)
			{
				long startDate = TimeCalculation.StringDateToUnixTime(String.valueOf(pfData.getStartDate())) / 1000;
				long endDate = Math.max(TimeCalculation.StringDateToUnixTime(String.valueOf(pfData.getEndDate())) / 1000, 0);
				if(!TimeCalculation.checkTimeNow(startDate, endDate))
					continue;
			}
			
			ProfileVO vo = new ProfileVO();
			vo.id = pfData.getId();
			vo.idCharacter = pfData.getIdCharacter();
			vo.iconSpineProfile = pfData.getIconSpineProfile();
			vo.idTextName = pfData.getIdTextName();
			vo.idTextIntro = pfData.getIdTextIntro();
			vo.sortNumber = pfData.getSortNumber();
			vo.isHave = data.getIsHave();
			vo.isNew = data.getIsNew();
			vo.isUse = data.getIsUse();
			
			vos.add(vo);
		}

		return vos;
	}
}

