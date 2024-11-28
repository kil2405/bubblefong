//package com.bubbleShooter;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//import java.util.List;
//
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.bubbleShooter.VO.MailVO;
//import com.bubbleShooter.VO.PresetVO;
//import com.bubbleShooter.VO.ShopVO;
//import com.bubbleShooter.common.ConstantVal;
//import com.bubbleShooter.common.RepositoryService;
//import com.bubbleShooter.controller.card.CardService;
//import com.bubbleShooter.controller.mail.MailService;
//import com.bubbleShooter.controller.match.MatchService;
//import com.bubbleShooter.controller.rank.RankService;
//import com.bubbleShooter.controller.shop.ShopService;
//import com.bubbleShooter.controller.user.UserService;
//import com.bubbleShooter.domain.Character;
//import com.bubbleShooter.domain.Partner;
//import com.bubbleShooter.request.ReqAdmission;
//import com.bubbleShooter.request.ReqCardLock;
//import com.bubbleShooter.request.ReqChangeOption;
//import com.bubbleShooter.request.ReqChangePreset;
//import com.bubbleShooter.request.ReqCharacterEnhance;
//import com.bubbleShooter.request.ReqGameOver;
//import com.bubbleShooter.request.ReqLogin;
//import com.bubbleShooter.request.ReqMailConfirm;
//import com.bubbleShooter.request.ReqMailOpen;
//import com.bubbleShooter.request.ReqMatchEnd;
//import com.bubbleShooter.request.ReqMatchResult;
//import com.bubbleShooter.request.ReqMatchStart;
//import com.bubbleShooter.request.ReqMiningReward;
//import com.bubbleShooter.request.ReqOpenStarPack;
//import com.bubbleShooter.request.ReqPartnerEnhance;
//import com.bubbleShooter.request.ReqShopBuyProduct;
//import com.bubbleShooter.response.ResAdmission;
//import com.bubbleShooter.response.ResAllMailOpen;
//import com.bubbleShooter.response.ResCardInfo;
//import com.bubbleShooter.response.ResCardLock;
//import com.bubbleShooter.response.ResChangeOption;
//import com.bubbleShooter.response.ResChangePreset;
//import com.bubbleShooter.response.ResCharacterEnhance;
//import com.bubbleShooter.response.ResCharacterUpgrade;
//import com.bubbleShooter.response.ResLogin;
//import com.bubbleShooter.response.ResMailConfirm;
//import com.bubbleShooter.response.ResMailList;
//import com.bubbleShooter.response.ResMailOpen;
//import com.bubbleShooter.response.ResMatchResult;
//import com.bubbleShooter.response.ResMatchStart;
//import com.bubbleShooter.response.ResMiningReward;
//import com.bubbleShooter.response.ResPartnerUpgrade;
//import com.bubbleShooter.response.ResRankingUser;
//import com.bubbleShooter.response.ResShopBuyProduct;
//import com.bubbleShooter.response.ResShopInfo;
//import com.bubbleShooter.response.ResStarPack;
//import com.bubbleShooter.response.ResStarPackRate;
//import com.bubbleShooter.util.FindData;
//
//@SpringBootTest
//@TestMethodOrder(OrderAnnotation.class)
//public class ApplicationTests {
//	
//	private static long userid;
//	private static List<MailVO> mailvolist;
//	private static List<ShopVO> shopvolist;
//	
//	private static long matchid;
//	private static byte mode;
//	private static int ranking;
//	
//	@Autowired
//	private FindData findData;
//	@Autowired
//	private UserService userService;
//	@Autowired
//	private MatchService matchService;
//	@Autowired
//	private MailService mailService;
//	@Autowired
//	private RankService rankService;
//	@Autowired
//	private CardService cardService;
//	@Autowired
//	private ShopService shopService;
//	@Autowired
//	private RepositoryService repositoryService;
//	
//	///////////////////////		UserService		//////////////////////////
//	@Order(1)
//	@Test
//	@DisplayName("Admission 테스트")
//	void AdmissionTest() throws Exception
//	{
//		System.out.println("Admission 시작");
//		
//		ReqAdmission admissionReq = new ReqAdmission();
//		admissionReq.version = "0.0.17";
//		admissionReq.language = "kr";
//		admissionReq.market = 1;
//		
//		ResAdmission res = userService.Admission(admissionReq, "127.0.0.1"); 
//		assertNotNull(res);
//	}
//	
//	@SuppressWarnings("static-access")
//	@Order(2)
//	@Test
//	@DisplayName("Login 테스트")
//	void LoginTest() throws Exception
//	{
//		System.out.println("Login 시작");
//		
//		ReqLogin req = new ReqLogin();
//		req.version = "0.0.17";
//		req.language = "KR";
//		req.market = 1;
//		req.assetVersion = 1;
//		req.socialPlatform = 2;
//		//req.socialId = "s5Wem591HOeB9xf";
//		req.socialId = findData.getRandString(15);
//		
//		ResLogin resLogin = userService.Login(req, "127.0.0.1");
//		assertNotNull(resLogin);
//		this.userid = resLogin.account.userId;
//	}
//	
//	
//	////////////////////////////////		MatchService		/////////////////////////////
//	@SuppressWarnings("static-access")
//	@Order(3)
//	@Test
//	@DisplayName("MatchStart 테스트")
//	void MatchStartTest() throws Exception
//	{
//		System.out.println("MatchStart 시작");
//		
//		ReqMatchStart req = new ReqMatchStart();
//		req.serverId = 1;	// pvp서버 고유 ID
//		req.roomId = "3f442533438e281d497ea038ea603fc2cb35";
//		req.mode = 0;	// 0: 101모드, 1: 4vs4모드
//		req.isRank = 1;	// 0: 랭킹전X, 1: 랭킹전O
//
//		Long[][] player = new Long[1][1];	// 0번 index blueTeam, 1번 index redTeam, 101모드의 경우 0번index
//		player[0][0] = userid;
//		
//		req.players = player;
//		
//		ResMatchStart res = matchService.MatchStart(req);
//		assertNotNull(res);
//		
//		this.matchid = res.matchId;
//		this.mode = req.mode;
//	}
//	
//	@SuppressWarnings("static-access")
//	@Order(4)
//	@Test
//	@DisplayName("GameOver 테스트")
//	void GameOverTest() throws Exception
//	{
//		System.out.println("GameOver 시작");
//		
//		ReqGameOver req = new ReqGameOver();
//		req.userId = userid;
//		req.matchId = matchid;
//		req.ranking = 3;
//		req.playTime = 100;
//		
//		matchService.GameOver(req);
//		
//		this.ranking = req.ranking;
//	}
//	
//	@Order(5)
//	@Test
//	@DisplayName("MatchEnd 테스트")
//	void MatchEndTest() throws Exception
//	{
//		System.out.println("MatchEnd 시작");
//		
//		ReqMatchEnd req = new ReqMatchEnd();
//		req.matchId = matchid;
//		req.winTeam = 0;
//		req.playTime = 500;
//		
//		matchService.MatchEnd(req);
//	}
//	
//	@Order(6)
//	@Test
//	@DisplayName("MatchResult 테스트")
//	void MatchResultTest() throws Exception
//	{
//		System.out.println("MatchResult 시작");
//		
//		ReqMatchResult req = new ReqMatchResult();
//		req.matchId = matchid;
//		req.mode = mode;
//		req.ranking = ranking;
//		req.isMvp = 0;
//		
//		ResMatchResult res = matchService.MatchResult(userid, req);
//		assertNotNull(res);
//		assertNotNull(res.account);
//		assertNotNull(res.rewardPoint);
//		assertEquals(100, res.result);
//		
//	}
//	
//
//	////////////////////////////////		ShopService		/////////////////////////////////
//	@SuppressWarnings("static-access")
//	@Order(15)
//	@Test
//	@DisplayName("ShopInfo 테스트")
//	void ShopInfoTest() throws Exception
//	{
//		System.out.println("ShopInfo 시작");
//		
//		ResShopInfo res = shopService.ShopInfo(userid);
//		
//		assertNotNull(res);
//		assertNotNull(res.shop);
//		this.shopvolist = res.shop;
//		
//	}
//	
//	@Order(16)
//	@Test
//	@DisplayName("ShopBuyProduct 테스트")
//	void ShopBuyProductTest() throws Exception
//	{
//		System.out.println("ShopBuyProduct 시작");
//		
//		//req
//		ReqShopBuyProduct req = new ReqShopBuyProduct();
//		req.productId = shopvolist.get(0).productId;
//		
//		//res
//		ResShopBuyProduct res = shopService.ShopBuyProduct(userid, req);
//		assertNotNull(res);
//		assertNotNull(res.account);
//		assertNotNull(res.shop);
//		assertNotNull(res.rewards);
//	}
//	
//	@Order(17)
//	@Test
//	@DisplayName("OpenStarPack 테스트")
//	void OpenStarPackTest() throws Exception
//	{
//		System.out.println("OpenStarPack 시작");
//		
//		//req
//		ReqOpenStarPack req = new ReqOpenStarPack();
//		req.type = 0;
//		
//		//res
//		ResStarPack res = shopService.OpenStarPack(userid, req);
//		assertNotNull(res);
//		assertNotNull(res.account);
//		assertNotNull(res.rewards);
//	}
//	
//	@Order(18)
//	@Test
//	@DisplayName("StarPackRate 테스트")
//	void StarPackRateTest() throws Exception
//	{
//		System.out.println("StarPackRate 시작");
//		
//		//res
//		ResStarPackRate res = shopService.StarPackRate(userid);
//		assertNotNull(res);
//		assertNotNull(res.starPackRate);
//		assertNotNull(res.superStarPackRate);
//	}
//	
//	
//	////////////////////////////////		RankService		/////////////////////////////////
//	@Order(19)
//	@Test
//	@DisplayName("GetRankingUserList 테스트")
//	void GetRankingUserListTest() throws Exception
//	{
//		System.out.println("GetRankingUserList 시작");
//		
//		//res
//		ResRankingUser res = rankService.getRankingUserList(userid);
//		
//		assertNotNull(res);
//		assertNotNull(res.rankers);
//		assertNotNull(res.userRecord);
//		assertNotNull(res.tierRewards);
//		assertNotNull(res.rankingRewards);
//	}
//	
////	// 시즌 끝나고 보상이므로 시즌 끝나고 가능
////	@Order(21)
////	@Test
////	@DisplayName("SeasonReward 테스트")
////	void SeasonRewardTest() throws Exception
////	{
////		System.out.println("SeasonReward 시작");
////	
////		//res
////		ResRankingSeasonReward res = rankService.SeasonReward(userid);
////		
////		assertNotNull(res);
////		assertEquals(100, res.result);
////		assertNotNull(res.account);
////		assertNotNull(res.rankingReward);
////		assertNotNull(res.tierReward);
////	}
//	
//	
//	////////////////////////////////		CardService		/////////////////////////////////
//	@Order(9)
//	@Test
//	@DisplayName("CharacterInfo 테스트")
//	void CharacterInfoTest() throws Exception
//	{
//		System.out.println("CharacterInfo 시작");
//		
//		ResCardInfo res = cardService.CharacterInfo(userid);
//		
//		assertNotNull(res);
//		assertEquals(18, res.characters.size());
//		assertEquals(48, res.partners.size());
//	}
//	
//	@Order(10)
//	@Test
//	@DisplayName("CharacterUpgrade 테스트")
//	void CharacterUpgradeTest() throws Exception
//	{
//		System.out.println("CharacterUpgrade 시작");
//		
//		//req
//		List<Character> characters = repositoryService.getCharacters(userid, false);
//		int nftIndex = findData.findNftCharacterIndex(characters);
//		String characterUid =  characters.get(nftIndex).getUid();
//		
//		//res
//		ResCharacterUpgrade res = cardService.CharacterUpgrade(userid, characterUid);
//
//		assertNotNull(res);
//		assertNotNull(res.account);
//		assertEquals(characterUid, res.characterUid);
//	}
//	
//	// 캐릭터 업그레이드 다 하고 나서 가능
//	@Order(11)
//	@Test
//	@DisplayName("CharacterEnhance 테스트")
//	void CharacterEnhanceTest() throws Exception
//	{
//		System.out.println("CharacterEnhance 시작");
//		
//		//req
//		List<Character> characters = repositoryService.getCharacters(userid, false);
//		int nftIndex = findData.findNftCharacterIndex(characters);
//		String characterUid =  characters.get(nftIndex).getUid();
//		
//		for(int index = characters.get(nftIndex).getCharacterLevel(); index < 5; index++)
//		{
//			cardService.CharacterUpgrade(userid, characterUid);
//		}
//		
//		ReqCharacterEnhance req = new ReqCharacterEnhance();
//		req.upgradeCharacterUid = characters.get(nftIndex).getUid();
//		req.materialCharacterUid = characters.get(nftIndex + 1).getUid();
//		
//		//res
//		ResCharacterEnhance res = cardService.CharacterEnhance(userid, req);
//		
//		assertNotNull(res);
//		assertNotNull(res.account);
//		assertEquals(req.upgradeCharacterUid, res.upgradeCharacterUid);
//	}
//	
//	@Order(12)
//	@Test
//	@DisplayName("PartnerUpgrade 테스트")
//	void PartnerUpgradeTest() throws Exception
//	{
//		System.out.println("PartnerUpgrade 시작");
//		
//		//req
//		List<Partner> partners = repositoryService.getPartners(userid, false);
//		String partnerUid = partners.get(0).getUid();
//		
//		//res
//		ResPartnerUpgrade res = cardService.PartnerUpgrade(userid, partnerUid);
//		
//		assertNotNull(res);
//		assertNotNull(res.account);
//		assertEquals(partnerUid, res.partnerUid);
//	}
//	
//	// 파트너 업그레이드가 다 하고 나서 가능 
//	@Order(13)
//	@Test
//	@DisplayName("PartnerAdvance 테스트")
//	void PartnerAdvanceTest() throws Exception
//	{
//		System.out.println("PartnerAdvance 시작");
//		//req
//		List<Partner> partners = repositoryService.getPartners(userid, false);
//		String partnerUid = partners.get(0).getUid();
//		ReqPartnerEnhance req = new ReqPartnerEnhance();
//		req.upgradePartnerUid = partners.get(0).getUid();
//		req.materialPartnerUid = partners.get(1).getUid();
//		byte[] useItems = {0, 0, 0}; 
//		req.useItem = useItems;
//		
//		for(int index = partners.get(0).getPartnerLevel(); index < 5; index++)
//		{
//			cardService.PartnerUpgrade(userid, partnerUid);
//		}
//		
//		//res
//		ResPartnerUpgrade res = cardService.PartnerAdvanece(userid, req);
//		
//		assertNotNull(res);
//		assertNotNull(res.account);
//		assertEquals(req.upgradePartnerUid, res.partnerUid);
//	}
//	
//	@Order(14)
//	@Test
//	@DisplayName("ChangePreset 테스트")
//	void ChangePresetTest() throws Exception
//	{
//		System.out.println("ChangePreset 시작");
//		
//		//req
//		List<Character> characters = repositoryService.getCharacters(userid, false);
//		List<Partner> partners = repositoryService.getPartners(userid, false);
//		int nftIndex = findData.findNftCharacterIndex(characters);
//		PresetVO presetVO = new PresetVO();
//		
//		ReqChangePreset req = new ReqChangePreset();
//		
//		req.preset = presetVO;
//		req.preset.characterUid = characters.get(nftIndex).getUid();
//		req.preset.partner1Uid = partners.get(0).getUid();
//		req.preset.partner2Uid = partners.get(2).getUid();
//		req.preset.partner3Uid = partners.get(4).getUid();
//		
//		//res
//		ResChangePreset res = cardService.ChangePreset(userid, req);
//		
//		assertNotNull(res);
//		assertNotNull(res.account);
//	}
//	
//	@Order(8)
//	@Test
//	@DisplayName("ChangePartnerOption 테스트")
//	void ChangePartnerOptionTest() throws Exception
//	{
//		System.out.println("ChangePartnerOption 시작");
//		
//		//req
//		List<Partner> partners = repositoryService.getPartners(userid, false);
//		
//		ReqChangeOption req = new ReqChangeOption();
//		req.partnerUid = partners.get(0).getUid();
//		req.changeSlot = 0;
//		
//		//res
//		ResChangeOption res = cardService.ChangePartnerOption(userid, req);
//		
//		assertNotNull(res);
//		assertNotNull(res.account);
//		assertEquals(req.partnerUid, res.partnerUid);
//	}
//	
//	@Order(20)
//	@Test
//	@DisplayName("CardLocked 테스트")
//	void CardLockedTest() throws Exception
//	{
//		System.out.println("CardLocked 시작");
//		
//		//req
//		List<Character> characters = repositoryService.getCharacters(userid, false);
//		int nftIndex = findData.findNftCharacterIndex(characters);
//		//List<Partner> partners = repositoryService.getPartners(userId, false);
//		
//		ReqCardLock req = new ReqCardLock();
//		// 캐릭터를 잠글 경우, 동료를 잠글꺼라면 characters를 partners로 바꿔야함, nft케릭터여야함
//		req.uid = characters.get(nftIndex).getUid();
//		req.type = 0;	// 0: 캐릭터, 1: 동료
//		req.isLock = 1;	// 0: 해제, 1:잠금
//		
//		//res
//		ResCardLock res = cardService.CardLocked(userid, req);
//		
//		assertNotNull(res);
//		assertNotNull(res.account);
//		assertEquals(req.uid, res.uid);
//		assertEquals(req.type, res.type);
//	}
//	
//	@Order(7)
//	@Test
//	@DisplayName("CharacterMiningReward 테스트")
//	void CharacterMiningRewardTest() throws Exception
//	{
//		System.out.println("CharacterMiningReward 시작");
//		
//		//req
//		List<Character> characters = repositoryService.getCharacters(userid, false);
//		if(characters == null || characters.isEmpty())
//			System.out.println("캐릭터 정보를 찾을 수 없습니다");
//		String presetCharacterUid = repositoryService.getPreset(userid, false).getCharacterUid();
//		if(presetCharacterUid.isEmpty())
//			System.out.println("장착된 캐릭터가 없습니다");
//		int characterIndex = findData.findCharacterIndex(characters, presetCharacterUid);
//		if(characterIndex <= ConstantVal.DEFAULT_VALUE)
//			System.out.println("캐릭터 정보를 찾을 수 없습니다");
//		
//		ReqMiningReward req = new ReqMiningReward();
//		req.characterUid = characters.get(characterIndex).getUid();
//
//		// BP 받기 위해 Match 3번 돌림 -> daily_mining = 30 되면 current_reward = 10
//		for(int index = 0; index < 3; index++)
//		{
//			MatchStartTest();
//			GameOverTest();
//			MatchEndTest();
//			MatchResultTest();
//		}
//
//		//res
//		ResMiningReward res = cardService.CharacterMiningReward(userid, req);
//		
//		assertNotNull(res);
//		assertNotNull(res.account);
//		assertEquals(10, res.rewardCount);
//		assertEquals(req.characterUid, res.characterUid);
//	}
//	
//	
//	////////////////////////////////		MailService		/////////////////////////////////
//	@Order(22)
//	@Test
//	@DisplayName("MailInfo 테스트")
//	@SuppressWarnings("static-access")
//	void MailInfoTest() throws Exception 
//	{
//		System.out.println("MailInfo 시작");
//		
//		ResMailList res = mailService.MailInfo(userid);
//		
//		assertNotNull(res);
//		
//		this.mailvolist = res.mails;
//	}
//	
//	@Order(23)
//	@Test
//	@DisplayName("MailConfirm 테스트")
//	void MailConfirmTest() throws Exception
//	{
//		System.out.println("MailConfirm 시작");
//
//		//req		
//		ReqMailConfirm req = new ReqMailConfirm();
//		req.mailId = mailvolist.get(0).mailId;
//		
//		//res
//		ResMailConfirm res = mailService.MailConfirm(userid, req);
//		
//		assertNotNull(res);
//		assertEquals(req.mailId, res.mailId);
//	}
//	
//	@Order(24)
//	@Test
//	@DisplayName("MailOpen 테스트")
//	void MailOpenTest() throws Exception
//	{
//		System.out.println("MailOpen 시작");
//		
//		//req
//		ReqMailOpen req = new ReqMailOpen();
//		req.mailId = mailvolist.get(0).mailId;
//		
//		//res
//		ResMailOpen res = mailService.MailOpen(userid, req);
//		
//		assertNotNull(res);
//		assertEquals(req.mailId, res.mailId);
//	}
//
//	@Order(25)
//	@Test
//	@DisplayName("AllMailOpen 테스트")
//	void AllMailOpenTest() throws Exception
//	{
//		System.out.println("AllMailOpen 시작");
//		
//		//res
//		ResAllMailOpen res = mailService.AllMailOpen(userid);
//		
//		assertNotNull(res);
//		assertNotNull(res.account);
//		assertNotNull(res.rewards);
//	}
//	
//
//	@AfterAll
//	static void afterAll() {
//		System.out.println("모든 테스트 종료 후 1회만 호출");
//	}
//
//}
