package com.bubbleShooter.controller.nft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bubbleShooter.common.BaseSessionClass;
import com.bubbleShooter.request.ReqBalanceExport;
import com.bubbleShooter.request.ReqCardExport;
import com.bubbleShooter.request.ReqItemExport;
import com.bubbleShooter.request.ReqNftRegister;

@RestController
@RequestMapping("api/")
public class NFTController {
	@Autowired
	private BaseSessionClass baseSessionClass;
	
	@Autowired
	private NFTService nftService;
	
	@RequestMapping(value = "client-secure/nft/user-regist", method = RequestMethod.POST)
	public Object register(@RequestBody ReqNftRegister req) throws Exception {
		return nftService.UserRegister(baseSessionClass.getUserId(), req);
	}
	
	@RequestMapping(value = "client-secure/nft/user-unregist", method = RequestMethod.POST)
	public Object unlink() throws Exception {
		return nftService.UserUnlink(baseSessionClass.getUserId());
	}
	
	@RequestMapping(value = "client-secure/nft/character-mint", method = RequestMethod.POST)
	public Object CharacterMint(@RequestBody ReqCardExport req) throws Exception {
		return nftService.characterMint(baseSessionClass.getUserId(), req);
	}
	
	@RequestMapping(value = "client-secure/nft/friend-mint", method = RequestMethod.POST)
	public Object FriendMint(@RequestBody ReqCardExport req) throws Exception {
		return nftService.friendsMint(baseSessionClass.getUserId(), req);
	}
	
	@RequestMapping(value = "client-secure/nft/item-mint", method = RequestMethod.POST)
	public Object ItemExport(@RequestBody ReqItemExport req) throws Exception {
		return nftService.ItemExport(baseSessionClass.getUserId(), req);
	}
	
//	@RequestMapping(value = "client-secure/nft/deposit", method = RequestMethod.POST)
//	public Object NftImport() throws Exception {
//		return nftService.ItemImport(baseSessionClass.getUserId());
//	}
	
	@RequestMapping(value = "client-secure/nft/check-nft", method = RequestMethod.POST)
	public Object ExportCheck() throws Exception {
		return nftService.GoodsCheckExport(baseSessionClass.getUserId());
	}
	
	@RequestMapping(value = "client-secure/nft/balance/export", method = RequestMethod.POST)
	public Object BalanceExport(@RequestBody ReqBalanceExport req) throws Exception {
		return nftService.BalanceExport(baseSessionClass.getUserId(), req);
	}
	
//	@RequestMapping(value = "client-secure/nft/balance/deposit", method = RequestMethod.POST)
//	public Object DepositList() throws Exception {
//		return nftService.BalanceDepositList(baseSessionClass.getUserId());
//	}
}
