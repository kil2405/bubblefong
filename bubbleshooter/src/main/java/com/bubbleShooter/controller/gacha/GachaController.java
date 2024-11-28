package com.bubbleShooter.controller.gacha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bubbleShooter.common.BaseSessionClass;
import com.bubbleShooter.request.ReqGachaPick;

@RestController
@RequestMapping("api/")
public class GachaController {

	@Autowired
	private GachaService gachaService;
	
	@Autowired
	private BaseSessionClass baseSessionClass;
	
	@RequestMapping(value = "client-secure/gacha/info", method = RequestMethod.POST)
	public Object GachaInfo() throws Exception {
		return gachaService.GachaInfo(baseSessionClass.getUserId());
	}
	
	@RequestMapping(value = "client-secure/gacha/pick", method = RequestMethod.POST)
	public Object GachaPick(@RequestBody ReqGachaPick req) throws Exception {
		return gachaService.GachaPick(baseSessionClass.getUserId(), req);
	}
	
}
