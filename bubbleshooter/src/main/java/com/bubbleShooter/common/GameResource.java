package com.bubbleShooter.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bubbleShooter.resource.AttendanceDailyComponent;
import com.bubbleShooter.resource.AttendanceEventComponent;
import com.bubbleShooter.resource.CharacterComponent;
import com.bubbleShooter.resource.CharacterDiaComponent;
import com.bubbleShooter.resource.CharacterLevelComponent;
import com.bubbleShooter.resource.CharacterUpgradeComponent;
import com.bubbleShooter.resource.ErrorCodeComponent;
import com.bubbleShooter.resource.EventComponent;
import com.bubbleShooter.resource.ForbiddenWordsComponent;
import com.bubbleShooter.resource.GachaListComponent;
import com.bubbleShooter.resource.GachaRewardComponent;
import com.bubbleShooter.resource.GameRewardComponent;
import com.bubbleShooter.resource.GlobalComponent;
import com.bubbleShooter.resource.IdListComponent;
import com.bubbleShooter.resource.ItemComponent;
import com.bubbleShooter.resource.MailComponent;
import com.bubbleShooter.resource.MissionComponent;
import com.bubbleShooter.resource.PartnerComponent;
import com.bubbleShooter.resource.PartnerLevelComponent;
import com.bubbleShooter.resource.PartnerUpgradeComponent;
import com.bubbleShooter.resource.ProfileComponent;
import com.bubbleShooter.resource.RankingRewardComponent;
import com.bubbleShooter.resource.RankingSeasonComponent;
import com.bubbleShooter.resource.RankingTierComponent;
import com.bubbleShooter.resource.ShopComponent;
import com.bubbleShooter.resource.ShopRandomComponent;
import com.bubbleShooter.resource.ShopRewardComponent;
import com.bubbleShooter.resource.SkillCharacterActiveComponent;
import com.bubbleShooter.resource.SkillPartnerComponent;
import com.bubbleShooter.resource.SkillPartnerGroupComponent;
import com.bubbleShooter.resource.SynthesisitemComponent;
import com.bubbleShooter.resource.TextComponent;
import com.bubbleShooter.resource.UserExpComponent;

@Component
public class GameResource {
	@Autowired
	private AttendanceDailyComponent attendanceDaily;
	
	@Autowired
	private AttendanceEventComponent attendanceEvent;
	
	@Autowired
	private CharacterDiaComponent characterDia;
	
	@Autowired
	private CharacterComponent character;
	
	@Autowired
	private CharacterLevelComponent characterLevel;
	
	@Autowired
	private CharacterUpgradeComponent characterUpgrade;
	
	@Autowired
	private ErrorCodeComponent errorCode;
	
	@Autowired
	private EventComponent event;
	
	@Autowired
	private ForbiddenWordsComponent forbiddenWords;
	
	@Autowired
	private GameRewardComponent gameReward;
	
	@Autowired
	private GlobalComponent global;
	
	@Autowired
	private GachaListComponent gachaList;
	
	@Autowired
	private GachaRewardComponent gachaReward;
	
	@Autowired
	private IdListComponent idList;
	
	@Autowired
	private ItemComponent item;
	
	@Autowired
	private MailComponent mail;
	
	@Autowired
	private MissionComponent mission;
	
	@Autowired
	private PartnerComponent partner;
	
	@Autowired
	private PartnerLevelComponent partnerLevel;
	
	@Autowired
	private PartnerUpgradeComponent partnerUpgrade;

	@Autowired
	private ProfileComponent profile;
	
	@Autowired
	private RankingRewardComponent rankingReward;
	
	@Autowired
	private RankingSeasonComponent rankingSeason;
	
	@Autowired
	private RankingTierComponent rankingTier;
	
	@Autowired
	private ShopComponent shop;
	
	@Autowired
	private ShopRandomComponent shopRandom;
	
	@Autowired
	private ShopRewardComponent shopReward;
	
	@Autowired
	private SkillCharacterActiveComponent skillCharacterActive;
	
	@Autowired
	private SkillPartnerComponent skillPartner;
	
	@Autowired
	private SkillPartnerGroupComponent skillPartnerGroup;

	@Autowired
	private SynthesisitemComponent synthesisitem;
	
	@Autowired
	private TextComponent text;
	
	@Autowired
	private UserExpComponent userExp;
	
	
	
	public void LoadResource() throws Exception
	{
		//시즌정보를 찾기위해 가장먼저 로드 한다.
		rankingSeason.LoadResource();
		rankingReward.LoadResource();
		
		attendanceDaily.LoadResource();
		attendanceEvent.LoadResource();
		characterDia.LoadResource();
		character.LoadResource();
		characterLevel.LoadResource();
		characterUpgrade.LoadResource();
		errorCode.LoadResource();
		event.LoadResource();
		forbiddenWords.LoadResource();
		gameReward.LoadResource();
		global.LoadResource();
		gachaList.LoadResource();
		gachaReward.LoadResource();
		idList.LoadResource();
		item.LoadResource();
		mail.LoadResource();
		mission.LoadResource();
		partner.LoadResource();
		partnerLevel.LoadResource();
		partnerUpgrade.LoadResource();
		profile.LoadResource();
		rankingTier.LoadResource();
		shop.LoadResource();
		shopRandom.LoadResource();
		shopReward.LoadResource();
		skillCharacterActive.LoadResource();
		skillPartner.LoadResource();
		skillPartnerGroup.LoadResource();
		synthesisitem.LoadResource();
		text.LoadResource();
		userExp.LoadResource();
	}
	
	public AttendanceDailyComponent getAttendanceDaily()
	{
		return attendanceDaily;
	}
	
	public AttendanceEventComponent getAttendanceEvent()
	{
		return attendanceEvent;
	}
	
	public CharacterDiaComponent getCharacterDia()
	{
		return characterDia;
	}
	
	public CharacterComponent getCharacter()
	{
		return character;
	}
	
	public CharacterLevelComponent getCharacterLevel()
	{
		return characterLevel;
	}
	
	public CharacterUpgradeComponent getCharacterUpgrade()
	{
		return characterUpgrade;
	}
	
	public ErrorCodeComponent getErrorCode()
	{
		return errorCode;
	}
	
	public EventComponent getEvent()
	{
		return event;
	}
	
	public ForbiddenWordsComponent getForbiddenWords()
	{
		return forbiddenWords;
	}
	
	public GameRewardComponent getGameReward()
	{
		return gameReward;
	}
	
	public GlobalComponent getGlobal()
	{
		return global;
	}
	
	public GachaListComponent getGachaList()
	{
		return gachaList;
	}
	
	public GachaRewardComponent getGachaReward()
	{
		return gachaReward;
	}
	
	public IdListComponent getIdList()
	{
		return idList;
	}
	
	public ItemComponent getItem()
	{
		return item;
	}
	
	public MailComponent getMail()
	{
		return mail;
	}
	
	public MissionComponent getMission()
	{
		return mission;
	}
	
	public PartnerComponent getPartner()
	{
		return partner;
	}
	
	public PartnerLevelComponent getPartnerLevel()
	{
		return partnerLevel;
	}
	
	public PartnerUpgradeComponent getPartnerUpgrade()
	{
		return partnerUpgrade;
	}

	public ProfileComponent getProfile()
	{
		return profile;
	}
	
	public RankingRewardComponent getRankingReward()
	{
		return rankingReward;
	}
	
	public RankingSeasonComponent getRankingSeason()
	{
		return rankingSeason;
	}
	
	public RankingTierComponent getRankingTier()
	{
		return rankingTier;
	}
	
	public ShopComponent getShop()
	{
		return shop;
	}
	
	public ShopRandomComponent getShopRandom()
	{
		return shopRandom;
	}
	
	public ShopRewardComponent getShopReward()
	{
		return shopReward;
	}
	
	public SkillCharacterActiveComponent getCharacterActiveSkill()
	{
		return skillCharacterActive;
	}
	
	public SkillPartnerComponent getPartnerSkill()
	{
		return skillPartner;
	}
	
	public SkillPartnerGroupComponent getPartnerGroupSkill()
	{
		return skillPartnerGroup;
	}

	public SynthesisitemComponent getSynthesisitem()
	{
		return synthesisitem;
	}
	
	public TextComponent getText()
	{
		return text;
	}
	
	public UserExpComponent getUserExp()
	{
		return userExp;
	}
	
	//Table 검증 코드
	public void verify() throws Exception
	{
		rankingSeason.LoadResource();
		attendanceDaily.VerifyResource();
		attendanceEvent.VerifyResource();
		characterDia.VerifyResource();
		character.VerifyResource();
		characterLevel.VerifyResource();
		characterUpgrade.VerifyResource();
		errorCode.VerifyResource();
		event.VerifyResource();
		forbiddenWords.VerifyResource();
		gameReward.VerifyResource();
		global.VerifyResource();
		gachaList.VerifyResource();
		gachaReward.VerifyResource();
		item.VerifyResource();
		mail.VerifyResource();
		mission.VerifyResource();
		partner.VerifyResource();
		partnerLevel.VerifyResource();
		partnerUpgrade.VerifyResource();
		profile.VerifyResource();
		rankingTier.VerifyResource();
		shop.VerifyResource();
		shopRandom.VerifyResource();
		shopReward.VerifyResource();
		skillCharacterActive.VerifyResource();
		skillPartner.VerifyResource();
		skillPartnerGroup.VerifyResource();
		synthesisitem.VerifyResource();
		text.VerifyResource();
		userExp.VerifyResource();
		
		System.out.println("Table Resource verify Complete");
	}
}
