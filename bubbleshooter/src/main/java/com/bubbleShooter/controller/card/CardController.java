package com.bubbleShooter.controller.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bubbleShooter.common.BaseSessionClass;
import com.bubbleShooter.request.ReqChangeOption;
import com.bubbleShooter.request.ReqChangePreset;
import com.bubbleShooter.request.ReqCharacterConfirm;
import com.bubbleShooter.request.ReqCharacterLevelUp;
import com.bubbleShooter.request.ReqCharacterLock;
import com.bubbleShooter.request.ReqCharacterUpgrade;
import com.bubbleShooter.request.ReqPartnerConfirm;
import com.bubbleShooter.request.ReqPartnerLevelUp;
import com.bubbleShooter.request.ReqPartnerLock;
import com.bubbleShooter.request.ReqPartnerUpgrade;

@RestController
@RequestMapping("api/")
public class CardController {
	@Autowired
	private CardService cardService;
	
	@Autowired
	private BaseSessionClass baseSessionClass;
	
	@RequestMapping(value = "client-secure/character/refresh", method = RequestMethod.POST)
	public Object CharacterInfo() throws Exception {
		return cardService.CharacterInfo(baseSessionClass.getUserId());
	}
	
	@RequestMapping(value = "client-secure/character/lock", method = RequestMethod.POST)
	public Object CardLock(@RequestBody ReqCharacterLock req) throws Exception {
		return cardService.CharacterLock(baseSessionClass.getUserId(), req);
	}
	
	@RequestMapping(value = "client-secure/character/levelup", method = RequestMethod.POST)
	public Object CharacterLevelUp(@RequestBody ReqCharacterLevelUp req) throws Exception {
		return cardService.CharacterLevelUp(baseSessionClass.getUserId(), req.characterUid);
	}
	
	@RequestMapping(value = "client-secure/character/upgrade", method = RequestMethod.POST)
	public Object CharacterUpgrade(@RequestBody ReqCharacterUpgrade req) throws Exception {
		return cardService.CharacterUpgrade(baseSessionClass.getUserId(), req);
	}
	
	@RequestMapping(value = "client-secure/character/confirm", method = RequestMethod.POST)
	public Object CharacterConfirm(@RequestBody ReqCharacterConfirm req) throws Exception {
		return cardService.CharacterConfirm(baseSessionClass.getUserId(), req);
	}
	
	@RequestMapping(value = "client-secure/partner/refresh", method = RequestMethod.POST)
	public Object PartnerInfo() throws Exception {
		return cardService.PartnerInfo(baseSessionClass.getUserId());
	}
	
	@RequestMapping(value = "client-secure/partner/lock", method = RequestMethod.POST)
	public Object PartnerLock(@RequestBody ReqPartnerLock req) throws Exception {
		return cardService.PartnerLock(baseSessionClass.getUserId(), req);
	}
	
	@RequestMapping(value = "client-secure/partner/change-option", method = RequestMethod.POST)
	public Object ChangeOption(@RequestBody ReqChangeOption req) throws Exception {
		return cardService.ChangePartnerOption(baseSessionClass.getUserId(), req);
	}
	
	@RequestMapping(value = "client-secure/partner/levelup", method = RequestMethod.POST)
	public Object PartnerLevelUp(@RequestBody ReqPartnerLevelUp req) throws Exception {
		return cardService.PartnerLevelUp(baseSessionClass.getUserId(), req.partnerUid);
	}
	
	@RequestMapping(value = "client-secure/partner/upgrade", method = RequestMethod.POST)
	public Object PartnerUpgrade(@RequestBody ReqPartnerUpgrade req) throws Exception {
		return cardService.PartnerUpgrade(baseSessionClass.getUserId(), req);
	}
	
	@RequestMapping(value = "client-secure/partner/confirm", method = RequestMethod.POST)
	public Object CharacterConfirm(@RequestBody ReqPartnerConfirm req) throws Exception {
		return cardService.PartnerConfirm(baseSessionClass.getUserId(), req);
	}
	
	@RequestMapping(value = "client-secure/preset/equip", method = RequestMethod.POST)
	public Object ChangePreset(@RequestBody ReqChangePreset req) throws Exception {
		return cardService.ChangePreset(baseSessionClass.getUserId(), req);
	}
}
