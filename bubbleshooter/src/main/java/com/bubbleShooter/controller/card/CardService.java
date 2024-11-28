package com.bubbleShooter.controller.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bubbleShooter.VO.RewardItemVO;
import com.bubbleShooter.common.BubbleException;
import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.ErrorCodeInfo;
import com.bubbleShooter.common.GameMoneyLog;
import com.bubbleShooter.common.GameResource;
import com.bubbleShooter.common.RepositoryService;
import com.bubbleShooter.controller.Profile.ProfileService;
import com.bubbleShooter.domain.Character;
import com.bubbleShooter.domain.Partner;
import com.bubbleShooter.domain.Preset;
import com.bubbleShooter.domain.User;
import com.bubbleShooter.domain.UserItem;
import com.bubbleShooter.request.ReqChangeOption;
import com.bubbleShooter.request.ReqChangePreset;
import com.bubbleShooter.request.ReqCharacterConfirm;
import com.bubbleShooter.request.ReqCharacterLock;
import com.bubbleShooter.request.ReqCharacterUpgrade;
import com.bubbleShooter.request.ReqPartnerConfirm;
import com.bubbleShooter.request.ReqPartnerLock;
import com.bubbleShooter.request.ReqPartnerUpgrade;
import com.bubbleShooter.resource.CharacterLevelResource;
import com.bubbleShooter.resource.CharacterResource;
import com.bubbleShooter.resource.CharacterUpgradeResource;
import com.bubbleShooter.resource.PartnerLevelResource;
import com.bubbleShooter.resource.PartnerResource;
import com.bubbleShooter.resource.PartnerUpgradeResource;
import com.bubbleShooter.response.ResChangeOption;
import com.bubbleShooter.response.ResChangePreset;
import com.bubbleShooter.response.ResCharacterConfirm;
import com.bubbleShooter.response.ResCharacterInfo;
import com.bubbleShooter.response.ResCharacterLevelUp;
import com.bubbleShooter.response.ResCharacterLock;
import com.bubbleShooter.response.ResCharacterUpgrade;
import com.bubbleShooter.response.ResPartnerConfirm;
import com.bubbleShooter.response.ResPartnerInfo;
import com.bubbleShooter.response.ResPartnerLevelUp;
import com.bubbleShooter.response.ResPartnerLock;
import com.bubbleShooter.response.ResPartnerUpgrade;
import com.bubbleShooter.util.FindData;
import com.bubbleShooter.util.MapperVO;
import com.bubbleShooter.util.TimeCalculation;

@Service
public class CardService {
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private MapperVO mapperVO;
	
	@Autowired
	private FindData findData;
	
	@Autowired
	private GameResource gameResource;

	@Autowired
	private ProfileService profileService;
	
	public ResCharacterInfo CharacterInfo(int userId) throws Exception
	{
		ResCharacterInfo res = new ResCharacterInfo();
		
		List<Character> characters = repositoryService.getCharacters(userId, false);
		if(characters == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2001);
		
		res.characters = mapperVO.makeCharacterVO(userId, characters, false);
		res.result = ConstantVal.DEFAULT_SUCCESS;
		
		return res;
	}
	
	public ResCharacterLock CharacterLock(int userId, ReqCharacterLock req) throws Exception
	{
		User user = repositoryService.getUser(userId, false);
		if(user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2080);
	
		List<Character> characters = repositoryService.getCharacters(userId, false);
		if(characters == null || characters.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2084);
		
		int index = findData.findCharacterIndex(characters, req.characterUid);
		if(index <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2086);
		
		if(characters.get(index).getIsNft() <= ConstantVal.IS_FALSE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2082);
		
		characters.get(index).setIsLock(req.isLock);
		
		repositoryService.setCharacter(userId, index, characters);
		
		ResCharacterLock res = new ResCharacterLock();
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.characters = mapperVO.makeCharacterVO(userId, characters, false);
		
		return res;
	}
	
	public ResCharacterLevelUp CharacterLevelUp(int userId, String characterUid) throws Exception
	{
		User user = repositoryService.getUser(userId, false);
		if(user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2000);
		
		List<Character> characters = repositoryService.getCharacters(userId, false);
		if(characters == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2081);
		
		int index = findData.findCharacterIndex(characters, characterUid);
		if(index <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2062);
		
		//최고 레벨 체크
		if(characters.get(index).getCharacterLevel() >= ConstantVal.CHARACTER_CARD_MAX_LEVEL)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2004);
		
		//임무중에는 레벨업을 할 수가 없다.
		if(characters.get(index).getIsMission() > ConstantVal.IS_FALSE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2097);
		
		CharacterLevelResource levelRS = gameResource.getCharacterLevel().get(characters.get(index).getIsNft(), characters.get(index).getCharacterLevel(), (byte)characters.get(index).getGrade());
		if(levelRS == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2054);
		
		if(characters.get(index).getIsNft() >= ConstantVal.IS_TRUE)
		{
			//캐릭터 업그레이드 제한 등급 체크
			CharacterUpgradeResource upgradeRS = gameResource.getCharacterUpgrade().get((characters.get(index).getCharacterUpgrade()));
			if(upgradeRS == null)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2088);

			int unlockLevel = upgradeRS.getUpgradeUnlock();
			
			if(characters.get(index).getCharacterLevel() >= unlockLevel) {
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2055);
			}
		}

		List<RewardItemVO> useItems = new ArrayList<>();
		List<UserItem> items = repositoryService.getUserItems(userId, false);
		if(items == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2056);
		
		if(levelRS.getItemType0() > ConstantVal.DEFAULT_VALUE)
		{
			RewardItemVO useItem = new RewardItemVO();
			useItem.itemType = levelRS.getItemType0();
			useItem.itemId = levelRS.getItemId0();
			useItem.itemCount = -levelRS.getItemCount0();
			
			if(levelRS.getItemType0() == ConstantVal.ITEM_TYPE_ITEM)
				useItem.itemId = characters.get(index).getCharacterId();
			
			useItems.add(useItem);
		}
		
		if(levelRS.getItemType1() > ConstantVal.DEFAULT_VALUE)
		{
			RewardItemVO useItem = new RewardItemVO();
			useItem.itemType = levelRS.getItemType1();
			useItem.itemId = levelRS.getItemId1();
			useItem.itemCount = -levelRS.getItemCount1();
			
			if(levelRS.getItemType1() == ConstantVal.ITEM_TYPE_ITEM)
				useItem.itemId = characters.get(index).getCharacterId();
			
			useItems.add(useItem);
		}
		
		// 캐릭터 레벨 업그레이드
		characters.get(index).setCharacterLevel((byte)(levelRS.getLevel() + 1));
		
		// 소모아이템 처리
		mapperVO.makeRewardResult(userId, items, useItems, ConstantVal.LOG_TYPE_USE_CHARACTER_UPGRADE);
		
		// db set
		repositoryService.setCharacter(userId, index, characters);
		
		// 캐릭터 업그레이드 로그
//		repositoryService.setCardUseLog(userId, ConstantVal.LOG_TYPE_USE_CHARACTER_UPGRADE, characters.get(index).getCharacterId(), characterUid, "", null, ConstantVal.DEFAULT_VALUE, (byte)1);
		
		//response
		ResCharacterLevelUp res = new ResCharacterLevelUp();
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.characterUid = characterUid;
		res.characters = mapperVO.makeCharacterVO(userId, characters, false);
		res.items = mapperVO.makeItemVO(userId, items, false);
		
		return res;
	}
	
	public ResCharacterUpgrade CharacterUpgrade(int userId, ReqCharacterUpgrade req) throws Exception
	{
		ResCharacterUpgrade res = new ResCharacterUpgrade();
		
		List<Character> characters = repositoryService.getCharacters(userId, false);
		if(characters == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2021);
		
		//업그레이드 할 캐릭터 index 찾아오기
		int upgradeCharacterIndex = findData.findCharacterIndex(characters, req.upgradeCharacterUid);
		if(upgradeCharacterIndex <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2091);
		
		if(characters.get(upgradeCharacterIndex).getIsNft() < ConstantVal.IS_TRUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2014);
		
		//임무중에는 강화를 할 수가 없다.
		if(characters.get(upgradeCharacterIndex).getIsMission() > ConstantVal.IS_FALSE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2098);
		
		//재료 캐릭터 index 찾아오기
		int materialCharacterIndex = findData.findCharacterIndex(characters, req.materialCharacterUid);
		if(materialCharacterIndex <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2022);
		
		if(characters.get(materialCharacterIndex).getIsNft() < ConstantVal.IS_TRUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2024);
		
		if(characters.get(materialCharacterIndex).getIsLock() >= ConstantVal.IS_TRUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2057);
		
		//임무중인 캐릭터는 강화 재료로 사용될 수 없다.
		if(characters.get(materialCharacterIndex).getIsMission() > ConstantVal.IS_FALSE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2099);
		
		if(characters.get(upgradeCharacterIndex).getCharacterId() != characters.get(materialCharacterIndex).getCharacterId())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2016);
		
		//같은 등급인지 체크
		if(characters.get(upgradeCharacterIndex).getGrade() != characters.get(materialCharacterIndex).getGrade())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2058);
		
		//최고 강화 레벨 체크
		if(characters.get(upgradeCharacterIndex).getCharacterUpgrade() >= ConstantVal.CHARACTER_MAX_ENHANCE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2013);
		
		//강화 캐릭터 + 1 증가시켜준다.
		characters.get(upgradeCharacterIndex).setCharacterUpgrade(characters.get(upgradeCharacterIndex).getCharacterUpgrade() + 1);
		
		//일일 다이아 획득량 증가시켜준다.
		characters.get(upgradeCharacterIndex).setDailyearnlimit(ConstantVal.DAILYEARN_LIMIT_BY_GRADE[characters.get(upgradeCharacterIndex).getGrade()][characters.get(upgradeCharacterIndex).getCharacterUpgrade()]);
		
		//재료 아이템 삭제
		characters.get(materialCharacterIndex).setUseOrNot(ConstantVal.IS_FALSE);

		// 캐릭터 강화 로그
		//repositoryService.setCardUseLog(userId, ConstantVal.LOG_TYPE_USE_CHARACTER_ENHANCE, characters.get(upgradeCharacterIndex).getCharacterId(), req.upgradeCharacterUid, req.materialCharacterUid, null, ConstantVal.DEFAULT_VALUE, res.success ? ConstantVal.IS_TRUE : ConstantVal.IS_FALSE);
		
		//db set
		repositoryService.setCharacter(userId, upgradeCharacterIndex, characters);
		repositoryService.setCharacter(userId, materialCharacterIndex, characters);
		
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.upgradeCharacterUid = req.upgradeCharacterUid;
		res.characters = mapperVO.makeCharacterVO(userId, characters, false);
		
		return res;
	}
	
	public ResCharacterConfirm CharacterConfirm(int userId, ReqCharacterConfirm req) throws Exception
	{
		ResCharacterConfirm res = new ResCharacterConfirm();
		
		List<Character> characters = repositoryService.getCharacters(userId, false);
		if(characters == null || characters.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2059);
		
		int index = findData.findCharacterIndex(characters, req.characterUid);
		if(index <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2015);
		
		CharacterResource characterRS = gameResource.getCharacter().get(characters.get(index).getCharacterId());
		if(characterRS == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2065);
		
		characters.get(index).setIsNew(ConstantVal.IS_FALSE);
		
		repositoryService.setCharacter(userId, index, characters);
		
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.characters = mapperVO.makeCharacterVO(userId, characters, false);
		
		return res;
	}
	
	public ResPartnerInfo PartnerInfo(int userId) throws Exception
	{
		ResPartnerInfo res = new ResPartnerInfo();
		
		List<Partner> partners = repositoryService.getPartners(userId, false);
		if(partners == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2071);
		
		res.partners = mapperVO.makePartnerVO(userId, partners, false);
		res.result = ConstantVal.DEFAULT_SUCCESS;
		
		return res;
	}
	
	public ResPartnerLock PartnerLock(int userId, ReqPartnerLock req) throws Exception
	{
		User user = repositoryService.getUser(userId, false);
		if(user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2083);
	
		List<Partner> partners = repositoryService.getPartners(userId, false);
		if(partners == null || partners.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2085);
		
		int index = findData.findPartnerIndex(partners, req.uid);
		if(index <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2087);
		
		if(partners.get(index).getIsNft() <= ConstantVal.IS_FALSE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2092);
		
		partners.get(index).setIsLock(req.isLock);
		
		repositoryService.setPartner(userId, index, partners);
		
		ResPartnerLock res = new ResPartnerLock();
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.partners = mapperVO.makePartnerVO(userId, partners, false);
		
		return res;
	}
	
	public ResChangeOption ChangePartnerOption(int userId, ReqChangeOption req) throws Exception
	{
		ResChangeOption res = new ResChangeOption();

		User user = repositoryService.getUser(userId, false);
		if(user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2050);
		
		List<Partner> partners = repositoryService.getPartners(userId, false);
		if(partners == null || partners.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2038);
		
		int index = findData.findPartnerIndex(partners, req.partnerUid);
		if(index <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2039);
		
		List<UserItem> userItems = repositoryService.getUserItems(userId, false);
		if(userItems == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2066);
		
		byte itemIndex = findData.findUserItemIndex(ConstantVal.ITEM_TYPE_RUBY, 1, userItems);
		if(itemIndex <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2052);
		
		if(userItems.get(itemIndex).getItemCount() < ConstantVal.PARTNER_SKILL_CHANGE_RUBY)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2067);
		
		if(!repositoryService.setChangedItem(userId, itemIndex, userItems, -ConstantVal.PARTNER_SKILL_CHANGE_RUBY, ConstantVal.LOG_TYPE_USE_PARTNER_CHANGE_SKILL, false))
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2053);
		
		if(!setPartnerSkill(partners, index, partners.get(index).getGrade()))
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2072);
		
		//db set
		repositoryService.setUserItem(userId, itemIndex, userItems);
		repositoryService.setPartner(userId, index, partners);
		
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.partnerUid = req.partnerUid;
		res.items = mapperVO.makeItemVO(userId, userItems, false);
		res.partners = mapperVO.makePartnerVO(userId, partners, false);
		
		return res;
	}
	
	
	public ResPartnerLevelUp PartnerLevelUp(int userId, String partnerUid) throws Exception
	{
		User user = repositoryService.getUser(userId, false);
		if(user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2010);
		
		List<Partner> partners = repositoryService.getPartners(userId, false);
		if(partners == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2007);
		
		int index = findData.findPartnerIndex(partners, partnerUid);
		if(index <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2063);
		
		//최고 레벨 체크
		if(partners.get(index).getPartnerLevel() >= ConstantVal.PARTNER_CARD_MAX_LEVEL)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2008);
		
		//임무중인 파트너는 레벨업을 할 수 없다.
		if(partners.get(index).getIsMission() > ConstantVal.IS_FALSE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2104);
		
		PartnerLevelResource levelRS = gameResource.getPartnerLevel().get(partners.get(index).getGrade(), partners.get(index).getPartnerLevel());
		if(levelRS == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2068);
		
		//파트너 레벨업 제한 등급 체크
		PartnerUpgradeResource upgradeRS = gameResource.getPartnerUpgrade().get((partners.get(index).getUpgrade()));
		if(upgradeRS == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2089);

		int unlockLevel = upgradeRS.getLevelUnlock();
		
		if(partners.get(index).getPartnerLevel() >= unlockLevel) {
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2069);
		}

		List<RewardItemVO> useItems = new ArrayList<>();
		List<UserItem> items = repositoryService.getUserItems(userId, false);
		if(items == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2075);
		
		if(levelRS.getItemType0() > ConstantVal.DEFAULT_VALUE)
		{
			RewardItemVO useItem = new RewardItemVO();
			useItem.itemType = levelRS.getItemType0();
			useItem.itemId = levelRS.getItemId0();
			useItem.itemCount = -levelRS.getItemCount0();

			useItems.add(useItem);
		}
		
		if(levelRS.getItemType1() > ConstantVal.DEFAULT_VALUE)
		{
			RewardItemVO useItem = new RewardItemVO();
			useItem.itemType = levelRS.getItemType1();
			useItem.itemId = levelRS.getItemId1();
			useItem.itemCount = -levelRS.getItemCount1();
			
			useItems.add(useItem);
		}
		
		// 캐릭터 레벨 업그레이드
		partners.get(index).setPartnerLevel(partners.get(index).getPartnerLevel() + 1);
		
		// 소모아이템 처리
		mapperVO.makeRewardResult(userId, items, useItems, ConstantVal.LOG_TYPE_USE_CHARACTER_UPGRADE);
		
		// db set
		repositoryService.setPartner(userId, index, partners);
		
		// 캐릭터 업그레이드 로그
//		repositoryService.setCardUseLog(userId, ConstantVal.LOG_TYPE_USE_CHARACTER_UPGRADE, characters.get(index).getCharacterId(), characterUid, "", null, ConstantVal.DEFAULT_VALUE, (byte)1);
		
		//response
		ResPartnerLevelUp res = new ResPartnerLevelUp();
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.partnerUid = partnerUid;
		res.partners = mapperVO.makePartnerVO(userId, partners, false);
		res.items = mapperVO.makeItemVO(userId, items, false);
		
		return res;
	}
	
	
	public ResPartnerUpgrade PartnerUpgrade(int userId, ReqPartnerUpgrade req) throws Exception
	{
		List<Partner> partners = repositoryService.getPartners(userId, false);
		if(partners == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2041);
		
		//업그레이드 할 캐릭터 index 찾아오기
		int upgradePartnerIndex = findData.findPartnerIndex(partners, req.upgradePartnerUid);
		if(upgradePartnerIndex <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2019);
		
		//nft 캐릭터가 아니면 업그레이드 할 수 없다.
		if(partners.get(upgradePartnerIndex).getIsNft() < ConstantVal.IS_TRUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2009);
		
		//사용중이지 않는 캐릭터는 업그레이드 할 수 없다.
		if(partners.get(upgradePartnerIndex).getUseOrNot() < ConstantVal.IS_TRUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2076);
		
		//임무중인 파트너는 업그레이드를 할 수 없다.
		if(partners.get(upgradePartnerIndex).getIsMission() > ConstantVal.IS_FALSE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2105);
		
		PartnerUpgradeResource upgradeRS = gameResource.getPartnerUpgrade().get(partners.get(upgradePartnerIndex).getUpgrade());
		if(upgradeRS == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2077);
		
		//강화수치가 레벨 제한에 도달하지 못했다면 에러
		if(partners.get(upgradePartnerIndex).getPartnerLevel() < upgradeRS.getLevelUnlock())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2078);
		
		//재료 캐릭터 index 찾아오기
		int materialParnterIndex = findData.findPartnerIndex(partners, req.materialPartnerUid);
		if(materialParnterIndex <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2023);
		
		if(partners.get(materialParnterIndex).getIsNft() < ConstantVal.IS_TRUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2025);
		
		if(partners.get(materialParnterIndex).getIsLock() >= ConstantVal.IS_TRUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2079);
		
		//임무중인 파트너는 강화 재료로 사용될 수 없다.
		if(partners.get(materialParnterIndex).getIsMission() > ConstantVal.IS_FALSE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2106);
		
		//같은 종류의 동료가 아니면 에러
		if(partners.get(upgradePartnerIndex).getPartnerId() != partners.get(materialParnterIndex).getPartnerId())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2046);
		
		//같은 등급인지 체크
		if(partners.get(upgradePartnerIndex).getGrade() != partners.get(materialParnterIndex).getGrade())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2094);
		
		//최고 강화 레벨 체크
		if(partners.get(upgradePartnerIndex).getUpgrade() >= ConstantVal.CHARACTER_MAX_ENHANCE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2043);
		
		//강화 1 증가시켜준다.
		partners.get(upgradePartnerIndex).setUpgrade(partners.get(upgradePartnerIndex).getUpgrade() + 1);
		
		//재료 아이템 삭제
		partners.get(materialParnterIndex).setUseOrNot(ConstantVal.IS_FALSE);

		// 캐릭터 강화 로그
		//repositoryService.setCardUseLog(userId, ConstantVal.LOG_TYPE_USE_CHARACTER_ENHANCE, characters.get(upgradeCharacterIndex).getCharacterId(), req.upgradeCharacterUid, req.materialCharacterUid, null, ConstantVal.DEFAULT_VALUE, res.success ? ConstantVal.IS_TRUE : ConstantVal.IS_FALSE);
		
		//db set
		repositoryService.setPartner(userId, upgradePartnerIndex, partners);
		repositoryService.setPartner(userId, materialParnterIndex, partners);
		
		ResPartnerUpgrade res = new ResPartnerUpgrade();
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.upgradePartnerUid = req.upgradePartnerUid;
		res.partners = mapperVO.makePartnerVO(userId, partners, false);
		
		return res;
	}
	
	
	public ResPartnerConfirm PartnerConfirm(int userId, ReqPartnerConfirm req) throws Exception
	{
		ResPartnerConfirm res = new ResPartnerConfirm();
		
		List<Partner> Partners = repositoryService.getPartners(userId, false);
		if(Partners == null || Partners.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2095);
		
		int index = findData.findPartnerIndex(Partners, req.partnerUid);
		if(index <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2044);
		
		PartnerResource partnerRS = gameResource.getPartner().get(Partners.get(index).getPartnerId());
		if(partnerRS == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2096);
		
		Partners.get(index).setIsNew(ConstantVal.IS_FALSE);
		
		repositoryService.setPartner(userId, index, Partners);
		
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.partners = mapperVO.makePartnerVO(userId, Partners, false);
		
		return res;
	}
	
	
	public ResChangePreset ChangePreset(int userId, ReqChangePreset req) throws Exception
	{	
		Preset preset = repositoryService.getPreset(userId, false);
		if(preset == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2061);
		
		List<Character> characters = repositoryService.getCharacters(userId, false);
		if(characters == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2026);
		
		List<Partner> partners = repositoryService.getPartners(userId, false);
		if(partners == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2027);
		
		//중복 체크용 리스트
		List<Integer> reqCards = new ArrayList<>();
		if(req.character_uid.length() > 0)
			reqCards.add(characters.get(findData.findCharacterIndex(characters, req.character_uid)).getCharacterId());
		
		if(req.partner1_uid.length() > 0)
			reqCards.add(partners.get(findData.findPartnerIndex(partners, req.partner1_uid)).getPartnerId());
		
		if(req.partner2_uid.length() > 0)
			reqCards.add(partners.get(findData.findPartnerIndex(partners, req.partner2_uid)).getPartnerId());
		
		if(req.partner3_uid.length() > 0)
			reqCards.add(partners.get(findData.findPartnerIndex(partners, req.partner3_uid)).getPartnerId());
		
		//중복 값을 제외한 개수가, 리스트 개수와 다르면 에러(중복이 있음)
		if(reqCards.size() != reqCards.stream().distinct().count()){
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2051);
		}
		
		preset.setCharacterUid("");
		preset.setPartner1Uid("");
		preset.setPartner2Uid("");
		preset.setPartner3Uid("");
		
		//중복 장착 여부 체크 필요
		if(req.character_uid.length() > 0)
		{
			if(findData.findCharacterIndex(characters, req.character_uid) <= ConstantVal.DEFAULT_VALUE)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2028);
			
			preset.setCharacterUid(req.character_uid);
		}	
		
		if(req.partner1_uid.length() > 0)
		{
			if(findData.findPartnerIndex(partners, req.partner1_uid) <= ConstantVal.DEFAULT_VALUE)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2029);
			
			preset.setPartner1Uid(req.partner1_uid);
		}	
		
		if(req.partner2_uid.length() > 0)
		{
			if(findData.findPartnerIndex(partners, req.partner2_uid) <= ConstantVal.DEFAULT_VALUE)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2037);
			
			preset.setPartner2Uid(req.partner2_uid);
		}
		
		if(req.partner3_uid.length() > 0)
		{
			if(findData.findPartnerIndex(partners, req.partner3_uid) <= ConstantVal.DEFAULT_VALUE)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2036);
			
			preset.setPartner3Uid(req.partner3_uid);
		}
		
		repositoryService.setPreset(userId, preset);
		
		ResChangePreset res = new ResChangePreset();
		res.result = ConstantVal.DEFAULT_SUCCESS;
		res.preset = mapperVO.makePresetVO(userId, preset, false);
		
		return res;
	}
	
	
	public void AddCharacter(int userId, Character character, List<Character> characters, String logType) throws Exception
	{
		if(characters == null)
		{
			characters = repositoryService.getCharacters(userId, false);
			if(characters == null || characters.isEmpty())
				characters = new ArrayList<>();
		}
		
		CharacterResource characterRS = gameResource.getCharacter().get(character.getCharacterId());
		if(characterRS == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2011);
		
		int emptySlot = findData.findCharacterEmptySlot(characters);
		if(emptySlot > ConstantVal.DEFAULT_VALUE)
		{
			characters.get(emptySlot).setUserId(character.getUserId());
			characters.get(emptySlot).setUid(character.getUid());
			characters.get(emptySlot).setCharacterId(character.getCharacterId());
			characters.get(emptySlot).setGrade(character.getGrade());
			characters.get(emptySlot).setCharacterLevel(character.getCharacterLevel());
			characters.get(emptySlot).setCharacterUpgrade(character.getCharacterUpgrade());
			characters.get(emptySlot).setActiveSkill(character.getActiveSkill());
			characters.get(emptySlot).setIsLock(character.getIsLock());
			characters.get(emptySlot).setDailyearn(character.getDailyearn());
			characters.get(emptySlot).setDailyearnlimit(character.getDailyearnlimit());
			characters.get(emptySlot).setIsReward(character.getIsReward());
			characters.get(emptySlot).setIsNft(characterRS.getIsNft());
			characters.get(emptySlot).setIsNew(character.getIsNew());
			characters.get(emptySlot).setIsMission(character.getIsMission());
			characters.get(emptySlot).setUseOrNot(character.getUseOrNot());
		}
		else
		{
			characters.add(character);
			emptySlot = characters.indexOf(character);
		}
		
		//db set
		repositoryService.setCharacter(userId, emptySlot, characters);
		AddLog(userId, logType, ConstantVal.ITEM_TYPE_CHARACTER, 1, 1, String.valueOf(character.getCharacterId()));

		// 프로필 추가
		profileService.AddProfile(userId, character.getCharacterId());
	}
	
	public List<Partner> AddPartner(int userId, Partner partner, List<Partner> partners, String logType) throws Exception
	{
		if(partners == null)
		{
			partners = repositoryService.getPartners(userId, false);
			if(partners == null || partners.isEmpty())
				partners = new ArrayList<>();
		}
		
		PartnerResource partnerRS = gameResource.getPartner().get(partner.getPartnerId());
		if(partnerRS == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2031);
		
		int emptySlot = findData.findPartnerEmptySlot(partners);
		if(emptySlot > ConstantVal.DEFAULT_VALUE)
		{
			partners.get(emptySlot).setUserId(userId);
			partners.get(emptySlot).setUid(partner.getUid());
			partners.get(emptySlot).setPartnerId(partner.getPartnerId());
			partners.get(emptySlot).setGrade(partner.getGrade());
			partners.get(emptySlot).setPartnerLevel(partner.getPartnerLevel());
			partners.get(emptySlot).setUpgrade(partner.getUpgrade());
			partners.get(emptySlot).setSkill1(partner.getSkill1());
			partners.get(emptySlot).setSkill2(partner.getSkill2());
			partners.get(emptySlot).setSkill3(partner.getSkill3());
			partners.get(emptySlot).setIsLock(partner.getIsLock());
			partners.get(emptySlot).setIsNft(partnerRS.getIsNft());
			partners.get(emptySlot).setIsNew(partner.getIsNew());
			partners.get(emptySlot).setIsMission(partner.getIsMission());
			partners.get(emptySlot).setUseOrNot(partner.getUseOrNot());
		}
		else
		{
			partners.add(partner);
			emptySlot = partners.indexOf(partner);
		}
		
		//스킬이 모두 세팅이 안되어있을 경우에만 아래 함수를 태운다
		if(partners.get(emptySlot).getSkill1() <= ConstantVal.DEFAULT_VALUE &&
				partners.get(emptySlot).getSkill2() <= ConstantVal.DEFAULT_VALUE &&
				partners.get(emptySlot).getSkill3() <= ConstantVal.DEFAULT_VALUE)
		if(!setPartnerSkill(partners, emptySlot, partners.get(emptySlot).getGrade()))
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_2072);
		
		AddLog(userId, logType, ConstantVal.ITEM_TYPE_PARTNER, 1, 1, String.valueOf(partner.getPartnerId()));
		
		//db set
		repositoryService.setPartner(userId, emptySlot, partners);

		profileService.AddProfile(userId, partner.getPartnerId());
		return partners;
	}

	
//	private List<Partner> PartnerLock(long userId, String uid, byte lock) throws Exception
//	{
//		List<Partner> partners = repositoryService.getPartners(userId, false);
//		if(partners == null || partners.isEmpty())
//			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_10073);
//		
//		int index = findData.findPartnerIndex(partners, uid);
//		if(index <= ConstantVal.DEFAULT_VALUE)
//			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_10074);
//		
//		if(partners.get(index).getIsNft() <= ConstantVal.IS_FALSE)
//			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_10075);
//		
//		partners.get(index).setLock(lock);
//		
//		repositoryService.setStoragePartners(userId, index, partners);
//		return partners;
//	}
//	
//	public ResMiningReward CharacterMiningReward(long userId, ReqMiningReward req) throws Exception
//	{
//		//캐릭터 정보 얻어오기
//		List<Character> characters = repositoryService.getCharacters(userId, false);
//		if(characters == null || characters.isEmpty())
//			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_10076);
//		
//		//프리셋 정보 얻어오기
//		Preset preset = repositoryService.getPreset(userId, false);
//		if(preset == null)
//			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_10077);
//		
//		//장착되어 있는 캐릭터 정보 확인
//		if(!preset.getCharacterUid().equals(req.characterUid))
//			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_10078);
//		
//		//캐릭터 인덱스 찾아오기
//		int index = findData.findCharacterIndex(characters, req.characterUid);
//		if(index <= ConstantVal.DEFAULT_VALUE)
//			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_10079);
//		
//		if(characters.get(index).getIsNft() < ConstantVal.IS_TRUE)
//			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_10080);
//		
//		//보상을 받았는지 여부 체크
//		if(characters.get(index).getIsReward() >= ConstantVal.IS_TRUE)
//			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_10081);
//		
//		//bp 값 체크
//		if(characters.get(index).getDailyMining() <= 0)
//			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_10082);
//		
//		//보상 데이터 얻어오기
//		int rewardIndex = ConstantVal.DEFAULT_VALUE;
//		for(int i = 0; i < ServerConfig.MINING_BP.length; i++)
//		{
//			if(characters.get(index).getDailyMining() >= ServerConfig.MINING_BP[i])
//				rewardIndex = i;
//		}
//		
//		if(rewardIndex <= ConstantVal.DEFAULT_VALUE)
//			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_10083);
//		
//		CharacterResource characterRS = gameResource.getCharacter().get(characters.get(index).getCharacterId());
//		if(characterRS == null)
//			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_10084);
//		
//		int rewardCount = characterRS.getMiningDiamond()[rewardIndex];
//		if(rewardCount <= ConstantVal.DEFAULT_ZERO)
//			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_10085);
//		
//		//현재 받을 보상값과, 계산 값이 다르면 에러
//		if(characters.get(index).getCurrentReward() != rewardCount)
//			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_10086);
//		
//		//보상 지급
//		List<UserItem> items = repositoryService.getUserItems(userId, false);
//		byte itemIndex = findData.findUserItemIndex(ConstantVal.ITEM_TYPE_PAID_DIAMOND, ConstantVal.ITEM_TYPE_PAID_DIAMOND, items);
//		if(!repositoryService.setChangedItem(userId, itemIndex, items, rewardCount, ConstantVal.LOG_TYPE_GET_MINING_REWARD, false))
//			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_10087);
//		
//		//캐릭터 정보 변경
//		characters.get(index).setIsReward(ConstantVal.IS_TRUE);
//		//characters.get(index).setCurrentReward(ConstantVal.DEFAULT_ZERO);
//		
//		//db set
//		repositoryService.setStorageCharacters(userId, index, characters);
//		repositoryService.setUserItem(userId, itemIndex, items);
//		
//		//res 만들기
//		User user = repositoryService.getUser(userId, false);
//		if(user == null)
//			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_10088);
//		
//		ResMiningReward res = new ResMiningReward();
//		res.account = mapperVO.makeAccountVO(user, characters, null, preset, false);
//		res.rewardCount = rewardCount;
//		res.characterUid = req.characterUid;
//		
//		return res;
//	}
//	
//	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void AddLog(int userId, String logType, byte itemType, int addValue, long totalValue, String description) throws Exception
	{
		GameMoneyLog log = new GameMoneyLog();
		log.setPtn_month((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_MONTH, 0));
		log.setPtn_day((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_DAY, 0));
		log.setLog_date(TimeCalculation.getCurrentUnixTime());
		log.setLog_type(logType);
		log.setUser_id(userId);
		log.setMoney_type(itemType);
		log.setAdd_value(addValue);
		log.setTotal_value(totalValue);
		log.setDescription(description);
		
		repositoryService.setGameMoneyLog(log);
	}
	
	private boolean setPartnerSkill(List<Partner> partners, int index, int grade)
	{
		partners.get(index).setSkill1(ConstantVal.DEFAULT_VALUE);
		partners.get(index).setSkill2(ConstantVal.DEFAULT_VALUE);
 		partners.get(index).setSkill3(ConstantVal.DEFAULT_VALUE);
		
       	Map<Integer, List<Integer>> slotSkills = gameResource.getPartnerSkill().getSlotPartnerSkills();
		if(slotSkills == null)
			return false;
		
		
		List<Integer> skills = new ArrayList<>();
		if(grade > 0)
		{
			skills = slotSkills.get(ConstantVal.CARD_GRADE_R);
      		Collections.shuffle(skills);
			
 			partners.get(index).setSkill1(skills.get(0));
			skills.remove(0);
		}
		if(grade > 1)
		{
			skills.addAll(slotSkills.get(ConstantVal.CARD_GRADE_SR));
			Collections.shuffle(skills);
			
			partners.get(index).setSkill2(skills.get(0));
			skills.remove(0);
		}
		if(grade > 2)
		{
			skills.addAll(slotSkills.get(ConstantVal.CARD_GRADE_SSR));
			Collections.shuffle(skills);
			
			partners.get(index).setSkill3(skills.get(0));
		}
		
		return true;
	}
	
//	private boolean AllChangePartnerSkill(List<Partner> partners, int index, byte grade, byte[] useItem)
//	{
//		int skillCount = getPartnerSkillCount(grade);
//		if(skillCount <= ConstantVal.DEFAULT_ZERO)
//			return false;
//		
//		List<Integer> skills = gameResource.getPartnerSkill().getRandomShufflePatnerSkill();
//		if(skills == null || skills.isEmpty())
//			return false;
//		
//		if(skillCount > 0)
//		{
//			if(useItem[0] <= ConstantVal.DEFAULT_ZERO)
//			{
//				partners.get(index).setSkill1(skills.get(0));
//				partners.get(index).setSkillEffect1(getRandomSkillEffect());
//			}
//		}
//		if(skillCount > 1)
//		{
//			if(useItem[1] <= ConstantVal.DEFAULT_ZERO)
//			{
//				partners.get(index).setSkill2(skills.get(1));
//				partners.get(index).setSkillEffect2(getRandomSkillEffect());
//			}
//		}
//		if(skillCount > 2)
//		{
//			partners.get(index).setSkill3(skills.get(2));
//			partners.get(index).setSkillEffect3(getRandomSkillEffect());
//		}
//		return true;
//	}
//	
//	public byte getRandomSkillEffect()
//	{
//		int rate = findData.getRandInt(ConstantVal.MAX_RATE);
//		byte skillEffect = ConstantVal.DEFAULT_VALUE;
//		
//		int totalRate = 0;
//		for(int i = 0; i < ServerConfig.PARTNER_SKILL_CHANGE_RATE.length; i++)
//		{
//			totalRate += ServerConfig.PARTNER_SKILL_CHANGE_RATE[i];
//			if(totalRate >= rate)
//			{
//				skillEffect = (byte)i;
//				break;
//			}
//		}
//		return skillEffect;
//	}
//	
//	public boolean ChangePartnerSkill(long userId, List<Partner> partners, int index, byte grade, int slot)
//	{
//		List<Integer> skills = gameResource.getPartnerSkill().getRandomShufflePatnerSkill();
//		if(skills == null || skills.isEmpty())
//			return false;
//		
//		int newSkill = 0;
//		switch(slot)
//		{
//		case ConstantVal.PARTNER_SKILL_SLOT_1:	
//			partners.get(index).setSkill1(ConstantVal.DEFAULT_VALUE);
//			partners.get(index).setSkillEffect1(ConstantVal.DEFAULT_VALUE);
//			
//			newSkill = CheckDuplicatedPartnerSkill(skills, ConstantVal.PARTNER_SKILL_SLOT_1, partners.get(index));
//			
//			partners.get(index).setSkill1(newSkill);
//			partners.get(index).setSkillEffect1(getRandomSkillEffect());
//			break;
//			
//		case ConstantVal.PARTNER_SKILL_SLOT_2:
//			partners.get(index).setSkill2(ConstantVal.DEFAULT_VALUE);
//			partners.get(index).setSkillEffect2(ConstantVal.DEFAULT_VALUE);
//			
//			newSkill = CheckDuplicatedPartnerSkill(skills, ConstantVal.PARTNER_SKILL_SLOT_2, partners.get(index));
//			
//			partners.get(index).setSkill2(newSkill);
//			partners.get(index).setSkillEffect2(getRandomSkillEffect());
//			break;
//			
//		case ConstantVal.PARTNER_SKILL_SLOT_3:
//			partners.get(index).setSkill3(ConstantVal.DEFAULT_VALUE);
//			partners.get(index).setSkillEffect3(ConstantVal.DEFAULT_VALUE);
//			
//			newSkill = CheckDuplicatedPartnerSkill(skills, ConstantVal.PARTNER_SKILL_SLOT_3, partners.get(index));
//			
//			partners.get(index).setSkill3(newSkill);
//			partners.get(index).setSkillEffect3(getRandomSkillEffect());
//			break;
//		}
//		return true;
//	}
//	
//	private int CheckDuplicatedPartnerSkill(List<Integer> skills, int slot, Partner partner)
//	{
//		int skillId = 0;
//		
//		for(int i = 0; i < skills.size(); i++)
//		{
//			if(partner.getSkill1() == skills.get(i))
//			{
//				if(slot != ConstantVal.PARTNER_SKILL_SLOT_1)
//					continue;
//			}
//			
//			if(partner.getSkill2() == skills.get(i))
//			{
//				if(slot != ConstantVal.PARTNER_SKILL_SLOT_2)
//					continue;
//			}
//			
//			if(partner.getSkill3() == skills.get(i))
//			{
//				if(slot != ConstantVal.PARTNER_SKILL_SLOT_3)
//					continue;
//			}
//			
//			skillId = skills.get(i);
//			break;
//		}
//		
//		return skillId;
//	}
//	
//	public void updateNewCard(long userId, List<Character> characters, List<Partner> partners) throws Exception
//	{
//		if(characters == null)
//			characters = repositoryService.getCharacters(userId, false);
//		
//		if(partners == null)
//			partners = repositoryService.getPartners(userId, false);
//		
//		boolean updateCharacter = false;
//		boolean updatePartner = false;
//		
//		for(Character character : characters)
//		{
//			if(character.getIsNew() == ConstantVal.IS_TRUE)
//			{
//				character.setIsNew(ConstantVal.DEFAULT_ZERO);
//				repositoryService.setStorageCharacter(userId, character);
//				updateCharacter = true;
//			}
//		}
//		
//		for(Partner partner : partners)
//		{
//			if(partner.getIsNew() == ConstantVal.IS_TRUE)
//			{
//				partner.setIsNew(ConstantVal.DEFAULT_ZERO);
//				repositoryService.setStoragePartner(userId, partner);
//				updatePartner = true;
//			}
//		}
//		
//		if(updateCharacter)
//			repositoryService.setStorageCharacters(userId, characters.size() - 1, characters);
//		
//		if(updatePartner)
//			repositoryService.setStoragePartners(userId, partners.size() - 1, partners);
//	}
//	
//	public void InitializationCharacterBp(long userId) throws Exception
//	{
//		List<Character> characters = repositoryService.getCharacters(userId, false);
//		if(characters == null || characters.isEmpty())
//			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_CARD_10089);
//		
//		boolean dbUpdate = false;
//		
//		for(Character character : characters)
//		{
//			if(character.getIsReward() > 0 || character.getDailyMining() > 0)
//			{
//				character.setDailyMining(0);
//				character.setCurrentReward(0);
//				character.setIsReward(ConstantVal.IS_FALSE);
//				
//				repositoryService.setStorageCharacter(userId, character);
//				dbUpdate = true;
//			}
//		}
//		
//		if(dbUpdate)
//			repositoryService.setStorageCharacters(userId, characters.size() - 1, characters);
//	}
}
