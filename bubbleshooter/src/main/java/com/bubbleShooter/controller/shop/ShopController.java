package com.bubbleShooter.controller.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bubbleShooter.common.BaseSessionClass;
import com.bubbleShooter.request.ReqComposeCharacter;
import com.bubbleShooter.request.ReqComposePartner;
import com.bubbleShooter.request.ReqShopBuyProduct;
import com.bubbleShooter.request.ReqStarPackRate;

@RestController
@RequestMapping("api/")
public class ShopController {
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private BaseSessionClass baseSessionClass;
	
	@RequestMapping(value = "client-secure/shop/info", method = RequestMethod.POST)
	public Object ShopInfo() throws Exception {
		return shopService.ShopInfo(baseSessionClass.getUserId());
	}
	
	@RequestMapping(value = "client-secure/shop/random-rate", method = RequestMethod.POST)
	public Object RandomRate(@RequestBody ReqStarPackRate req) throws Exception {
		return shopService.RandomRate(baseSessionClass.getUserId(), req);
	}
	
	@RequestMapping(value = "client-secure/shop/buy-product", method = RequestMethod.POST)
	public Object ShopBuyProduct(@RequestBody ReqShopBuyProduct req) throws Exception {
		return shopService.ShopBuyProduct(baseSessionClass.getUserId(), req);
	}
	
	@RequestMapping(value = "client-secure/item/compose/Character", method = RequestMethod.POST)
	public Object ComposeCharacter(@RequestBody ReqComposeCharacter req) throws Exception {
		return shopService.ComposeCharacter(baseSessionClass.getUserId(), req);
	}
	
	@RequestMapping(value = "client-secure/item/compose/partner", method = RequestMethod.POST)
	public Object ComposePartner(@RequestBody ReqComposePartner req) throws Exception {
		return shopService.ComposePartner(baseSessionClass.getUserId(), req);
	}
	
}
