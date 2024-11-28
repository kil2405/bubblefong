package com.bubbleShooter.controller.test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.GameResource;
import com.bubbleShooter.common.RepositoryService;
import com.bubbleShooter.controller.card.CardService;
import com.bubbleShooter.controller.user.UserService;
import com.bubbleShooter.domain.Character;
import com.bubbleShooter.domain.User;
import com.bubbleShooter.resource.CharacterResource;
import com.bubbleShooter.resource.IdListResource;
import com.bubbleShooter.util.FindData;
import com.bubbleShooter.util.MapperVO;
import com.bubbleShooter.util.TimeCalculation;
	
@RestController
@RequestMapping("api/")
public class TestController {
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private GameResource gameResource;
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MapperVO mapperVO;
	
	@Autowired
	private FindData findData;
	
	@RequestMapping(value = "client/make-bot", method = RequestMethod.POST)
	public Object MakeBot(@RequestBody int count) throws Exception {
		
		List<IdListResource> ids = gameResource.getIdList().get();
		if(ids == null || ids.isEmpty())
			return "ID List Table Empty Error";
		
		int userId = 10000;
		for(int i = 0; i < count; i++)
		{
			User user = new User();
			user.setId(userId);
			user.setUuid(findData.getRandString(16));
			user.setNickname(ids.get(i).getId());
			user.setLevel(ConstantVal.INIT_ACCOUNT_LEVEL);
			user.setExp(ConstantVal.INIT_ACCOUNT_EXP);
			user.setSeason(gameResource.getRankingSeason().getSeason());
			user.setRankPoint(ConstantVal.DEFAULT_ZERO);
			user.setTier(ConstantVal.DEFAULT_ZERO);
			user.setVip(ConstantVal.DEFAULT_ZERO);
			user.setGrade(ConstantVal.USER_GRADE_NORMAL);
			user.setSecret(UUID.randomUUID().toString().replace("-", ""));
			user.setEncryption(UUID.randomUUID().toString().replace("-", ""));
			user.setWallet("");
			user.setMarket(ConstantVal.MARKET_TYPE_IOS);
			user.setAttendanceDay(ConstantVal.DEFAULT_ZERO);
			user.setAttendanceDate(ConstantVal.DEFAULT_ZERO);
			user.setIsGuest(ConstantVal.IS_TRUE);
			user.setLanguage("Korean");
			user.setRegion("IV");
			user.setLoginTime(TimeCalculation.getCurrentUnixTime());
			user.setNickUpdate(TimeCalculation.getIntTime());
			
			repositoryService.setbotUser(userId, user);
			
			//카드 지급
			List<CharacterResource> charactersRS = gameResource.getCharacter().getCharacters();
			if(charactersRS == null || charactersRS.isEmpty())
				return "Character Resource Null Fail";
			
			CharacterResource characterRS = charactersRS.get(findData.getRandInt(0, charactersRS.size() - 1));
			if(characterRS == null)
				return "Character RandomId Fail";
			
			List<Character> characters = new ArrayList<>();
			Character newCharacter = mapperVO.makeCharacter(userId, characterRS.getId(), ConstantVal.CARD_GRADE_R);
			cardService.AddCharacter(userId, newCharacter, characters, ConstantVal.LOG_TYPE_GET_CREATE_ACCOUNT);
			
			//아이템 적용
			userService.initUserItem(userId);
			
			//프리셋 적용
			userService.initPreset(userId);
			
			userId++;
		}
		
		return "success";
	}
		
	@RequestMapping(value = "client/test", method = RequestMethod.POST)
	public Object TestRankingPoint(@RequestBody int id) throws Exception
	{
		Character character = mapperVO.makeCharacter(100150, id, ConstantVal.CARD_GRADE_R);
		cardService.AddCharacter(100150, character, null, "test");
		
		return true;
	}
}
