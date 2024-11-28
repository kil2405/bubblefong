package com.bubbleShooter.controller.nft;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.bubbleShooter.VO.RewardItemVO;
import com.bubbleShooter.common.BubbleException;
import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.ErrorCodeInfo;
import com.bubbleShooter.common.GameResource;
import com.bubbleShooter.common.RepositoryService;
import com.bubbleShooter.controller.mail.MailService;
import com.bubbleShooter.domain.Character;
import com.bubbleShooter.domain.NFT;
import com.bubbleShooter.domain.NftImportErrorLog;
import com.bubbleShooter.domain.NftItemExportLog;
import com.bubbleShooter.domain.NftItemImportLog;
import com.bubbleShooter.domain.Partner;
import com.bubbleShooter.domain.Preset;
import com.bubbleShooter.domain.User;
import com.bubbleShooter.domain.UserBalanceExportLog;
import com.bubbleShooter.domain.UserItem;
import com.bubbleShooter.domain.UserVersion;
import com.bubbleShooter.relation.MailTitle;
import com.bubbleShooter.request.ReqBalanceExport;
import com.bubbleShooter.request.ReqCardExport;
import com.bubbleShooter.request.ReqItemExport;
import com.bubbleShooter.request.ReqNftRegister;
import com.bubbleShooter.resource.CharacterResource;
import com.bubbleShooter.resource.ItemResource;
import com.bubbleShooter.resource.PartnerResource;
import com.bubbleShooter.response.ResBalanceExport;
import com.bubbleShooter.response.ResDepositList;
import com.bubbleShooter.response.ResGoodsCheckExport;
import com.bubbleShooter.response.ResImportNft;
import com.bubbleShooter.response.ResNftCardExport;
import com.bubbleShooter.response.ResNftItemExport;
import com.bubbleShooter.response.ResNftRegister;
import com.bubbleShooter.response.ResNftUnLink;
import com.bubbleShooter.util.FindData;
import com.bubbleShooter.util.MapperVO;
import com.bubbleShooter.util.TimeCalculation;
import com.google.gson.Gson;

@Service
public class NFTService {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private FindData findData;

	@Autowired
	private MakeMetaData metaData;

	@Autowired
	private GameResource gameResource;

	@Autowired
	private MapperVO mapperVO;

	// @Autowired
	// private ProfileService profileService;

	@Autowired
	private MailService mailService;

	@Value("${server.nft.relay_server_url}")
	private String nft_relay_server;

	@Value("${server.nft.metadata_path}")
	private String metadata_path;

	@Value("${server.nft.key}")
	private String mmd_key;

	@Value("${server.nft.iv}")
	private String mmd_iv;

	@Value("${spring.server.mode}")
	private String serverMode;

	public ResNftRegister UserRegister(int userId, ReqNftRegister req) throws Exception {
		ResNftRegister res = new ResNftRegister();

		if (req.wallet.isBlank())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4105);

		User user = repositoryService.getUser(userId, false);
		if (user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4000);
		
		if(serverMode.equals("live"))
		{
			if(user.getIsGuest() >= ConstantVal.IS_TRUE)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4114);
		}

		if (req.wallet.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4001);

		if (!findData.checkInputOnlyNumberAndAlphabet(user.getNickname()))
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4103);

		if (user.getWallet() != null && !user.getWallet().isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4002);

		user.setWallet(req.wallet.trim());

		MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();

		// parameters
		parameters.add("address", user.getWallet().trim());
		parameters.add("uid", user.getUuid());
		parameters.add("name", user.getNickname());

		JSONObject result = send(nft_relay_server + "users/regist", parameters);
		res.code = result.getString("code");
		res.result = result.getString("result");
		res.message = result.getString("msg");

		// db set
		if (res.result.equals("true")) {
			repositoryService.setUser(userId, user);
		} else {
			res.result = "false";
		}

		return res;
	}

	public boolean changeNickname(String wallet, String uuid, String nickname) throws Exception {
		MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();

		// parameters
		parameters.add("address", wallet.trim());
		parameters.add("uid", uuid.trim());
		parameters.add("name", nickname.trim());

		JSONObject result = send(nft_relay_server + "users/regist", parameters);
		return result.getBoolean("result");
	}

	public ResNftUnLink UserUnlink(int userId) throws Exception {
		ResNftUnLink res = new ResNftUnLink();

		User user = repositoryService.getUser(userId, false);
		if (user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4040);

		if (user.getWallet().isBlank())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4104);

		// parameters .add
		MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
		parameters.add("address", user.getWallet());
		parameters.add("uid", user.getUuid());

		JSONObject result = send(nft_relay_server + "users/unlink", parameters);
		res.code = result.getString("code");
		res.result = result.getString("result");
		res.message = result.getString("msg");

		if (res.result.equals("true")) {
			user.setWallet("");

			repositoryService.setUser(userId, user);
		} else {
			res.result = "false";
		}

		return res;
	}

	public ResNftItemExport ItemExport(int userId, ReqItemExport req) throws Exception {
		ResNftItemExport res = new ResNftItemExport();

		User user = repositoryService.getUser(userId, false);
		if (user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4020);

		if(serverMode.equals("live"))
		{
			if(user.getIsGuest() >= ConstantVal.IS_TRUE)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4114);
		}

		// nft 지갑 정보 확인
		if (user.getWallet().isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4094);

		// 유저 아이템 정보 얻어오기
		List<UserItem> userItem = repositoryService.getUserItems(userId, false);
		if (userItem == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4075);

		// 인덱스 찾기
		byte index = findData.findUserItemIndex(req.item_type, req.item_id, userItem);
		if (index <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4076);

		// 트로피가 아니면 내보낼 수 없다.
		if (userItem.get(index).getItemType() != ConstantVal.ITEM_TYPE_TROPHY)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4115);

		// nft 발행 개수 체크
		if (userItem.get(index).getItemCount() < req.count)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4033);

		// 아이템 테이블에서 해당 아이템이 있는지 찾기
		ItemResource itemRS = gameResource.getItem().get(userItem.get(index).getItemId());
		if (itemRS == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4034);

		if (itemRS.getIsNft() < ConstantVal.IS_TRUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4031);

		UserVersion userVersion = repositoryService.getUserVersion(userId, new Gson());
		if (userVersion == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4107);

		String version = String.valueOf(userVersion.getMajor()) + "." + String.valueOf(userVersion.getMinor()) + "."
				+ String.valueOf(userVersion.getPatch());

		// metadata json �일 만들�
		String json = metaData.makeItemJson(userItem.get(index).getItemType(), userItem.get(index).getItemId(),
				req.count, version);

		File metadata = new File(metadata_path + user.getId() + "_" + TimeCalculation.getCurrentUnixTime() + ".json");
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(metadata));
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4032);
		}

		// security 키 만들어서 패킷 발송
		String secret = UUID.randomUUID().toString().replace("-", "").substring(0, 16);

		// 마켓에 민팅정보 전송
		JSONObject result = send(user.getUuid(), user.getWallet(), nft_relay_server + "nft/mint",
				userItem.get(index).getItemId(), ConstantVal.NFT_TYPE_ITEM, metadata, secret);

		res.code = result.getString("code");
		res.result = result.getString("result");
		res.message = result.getString("msg");

		int lastId = setNftItemExportLog(userId, user.getWallet(), "nft/mint", itemRS.getDesc() + "," + req.count, json,
				res.code);
		if (lastId <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4116);

		if (res.result.equals("true")) {
			res.unique = result.getString("unique");
			res.hash = result.getString("hash");

			String mintKey = secret + res.unique;

			String encryptData = encrypt(mintKey);
			if (!encryptData.equals(res.hash))
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4117);

			// 민팅 내보낸 개수 삭제 처리
			if (!repositoryService.setChangedItem(userId, index, userItem, -req.count,
					ConstantVal.LOG_TYPE_USE_NFT_MARKET, true))
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4077);

			setNFTInfo(res.hash, secret, res.unique, lastId);
		} else {
			// 에러처리 로그를 남기고
			// res.msg 값에 nft 내보내기에 실패했습니다.
			res.result = "false";
			res.message = "NFT issue failed.";
		}

		// metadata 파일 삭제
		metadata.delete();

		res.items = mapperVO.makeItemVO(userId, userItem, false);
		return res;
	}

	public ResGoodsCheckExport GoodsCheckExport(int userId) throws Exception {
		ResGoodsCheckExport res = new ResGoodsCheckExport();

		UserBalanceExportLog log = repositoryService.getUserBalanceExportLog(userId);
		if (log == null) {
			log = new UserBalanceExportLog();
			log.setUserId(userId);
			log.setItemType(ConstantVal.ITEM_TYPE_DIAMOND);
			log.setItemCount(ConstantVal.DEFAULT_ZERO);
			log.setItemId(ConstantVal.ITEM_TYPE_DIAMOND);
			log.setUpdateDate(TimeCalculation.getIntTime());
		}

		if (log.getUpdateDate() != TimeCalculation.getIntTime()) {
			log.setItemCount(0);
			log.setUpdateDate(TimeCalculation.getIntTime());

			repositoryService.setUserBalanceExportLog(userId, log);
		}

		res.remainCount = ConstantVal.NFT_BALANCE_MAX_COUNT - log.getItemCount();
		if (res.remainCount < ConstantVal.DEFAULT_ZERO)
			res.remainCount = ConstantVal.NFT_BALANCE_MAX_COUNT;

		res.remainTime = TimeCalculation
				.diffOfUnixTime(TimeCalculation.doDateUnixTime(ConstantVal.DATE_SECTION_DAY, 1));

		res.result = ConstantVal.DEFAULT_SUCCESS;
		return res;
	}

	public ResImportNft ItemImport(int userId) throws Exception {
		ResImportNft res = new ResImportNft();

		User user = repositoryService.getUser(userId, false);
		if (user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4030);

		MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
		parameters.add("address", user.getWallet());
		parameters.add("uid", user.getUuid());
		parameters.add("limit", String.valueOf(10));

		JSONObject mintResult = send(nft_relay_server + "nft/depositList", parameters);
		if (mintResult == null)
			return null;

		String result = mintResult.getString("result");
		if (!result.equals("true")) {
			res.result = result;
			return res;
		}

		JSONArray array = mintResult.getJSONArray("data");
		if (array.length() <= ConstantVal.DEFAULT_ZERO) {
			// 받을 게 없다.
			res.result = result;
			return res;
		}

		for (int i = 0; i < array.length(); i++) {
			String type = array.getJSONObject(i).getString("type");
			String marketId = array.getJSONObject(i).getString("id");
			String createdGameYn = array.getJSONObject(i).getString("createdGameYn");

			if (createdGameYn.equals("Y")) {
				// 해시값 얻어온다.
				String hash = array.getJSONObject(i).getString("hash");
				// 해시값을 복호화하여, secret값과 unique값을 비교한다.
				// 맞으면 게임에 넣어주고, 없다면, 버린다.

				// hash값을 복호화 한다.
				// DB에 저장해둔 secret값과 unique값이 hash복호화 한값과 동일한지 체크
				String value = decrypt(hash);

				// 16자리씩 문자열을 자른다.
				String secret = value.substring(0, 16);
				String unique = value.substring(16, value.length());

				NFT nft = repositoryService.getNftInfo(hash);
				if (nft == null) {
					setNftImportErrorLog(userId, createdGameYn, hash, secret, unique, array.getJSONObject(i).toString());

					parameters.clear();
					parameters.add("id", marketId);
					parameters.add("address", user.getWallet());
					send(nft_relay_server + "nft/deposit", parameters);
					continue;
				}

				if (!nft.getSecret().equals(secret) || !nft.getUnique().equals(unique)) {
					setNftImportErrorLog(userId, createdGameYn, hash, secret, unique, array.getJSONObject(i).toString());

					parameters.clear();
					parameters.add("id", marketId);
					parameters.add("address", user.getWallet());
					send(nft_relay_server + "nft/deposit", parameters);
					continue;
				}

				if (nft.getIsValid() > ConstantVal.IS_FALSE) {
					setNftImportErrorLog(userId, createdGameYn, hash, secret, unique, array.getJSONObject(i).toString());

					parameters.clear();
					parameters.add("id", marketId);
					parameters.add("address", user.getWallet());
					send(nft_relay_server + "nft/deposit", parameters);
					continue;
				}

				nft.setIsValid(ConstantVal.IS_TRUE);
				repositoryService.updateNftInfo(nft.getHash(), nft.getIsValid());
			}

			RewardItemVO vo = new RewardItemVO();
			
			int ino = array.getJSONObject(i).getInt("ino");
			
			switch (type) {
			case ConstantVal.NFT_TYPE_CHARACTER:
				JSONArray jsonCharacter = array.getJSONObject(i).getJSONArray("attr");
				vo = makeRewardToJson(ConstantVal.NFT_TYPE_CHARACTER, ino, jsonCharacter);
				break;
			case ConstantVal.NFT_TYPE_PARTNER:
				JSONArray jsonPartner = array.getJSONObject(i).getJSONArray("attr");
				vo = makeRewardToJson(ConstantVal.NFT_TYPE_PARTNER, ino, jsonPartner);
				break;
			case ConstantVal.NFT_TYPE_ITEM:
				JSONArray jsonitem = array.getJSONObject(i).getJSONArray("attr");
				vo = makeRewardToJson(ConstantVal.NFT_TYPE_ITEM, ino, jsonitem);
				break;
			}

			parameters.clear();
			parameters.add("id", marketId);
			parameters.add("address", user.getWallet());
			send(nft_relay_server + "nft/deposit", parameters);
			
			// import log 추�
			setNftItemImportLog(userId, user.getWallet(), "nft/depositList", marketId, createdGameYn, vo);
			
			List<RewardItemVO> vos = new ArrayList<>();
			vos.add(vo);
			
			MailTitle mailRS = gameResource.getMail().get(ConstantVal.MAIL_TEXT_IMPORT_NFT, user.getLanguage());
			mailService.SendMails(userId, ConstantVal.MAIL_TYPE_CASH, mailRS.title,
					mailRS.contents, TimeCalculation.getCurrentUnixTime() + ConstantVal.MAIL_EXPIRE_TIME, vos);
		}

		res.result = result;
		return res;
	}

	public ResBalanceExport BalanceExport(int userId, ReqBalanceExport req) throws Exception {
		Gson gson = new Gson();

		ResBalanceExport res = new ResBalanceExport();
		req.count = ConstantVal.NFT_BALANCE_MIN_COUNT;

		User user = repositoryService.getUser(userId, false);
		if (user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4118);
		
		if(serverMode.equals("live"))
		{
			if(user.getIsGuest() >= ConstantVal.IS_TRUE)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4114);
		}

		if (user.getWallet().isBlank())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4119);

		List<UserItem> items = repositoryService.getUserItems(userId, false);
		if (items == null || items.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4120);

		int itemIndex = findData.findUserItemIndex(ConstantVal.ITEM_TYPE_DIAMOND, ConstantVal.ITEM_TYPE_DIAMOND, items);
		if (itemIndex <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4121);

		if (items.get(itemIndex).getItemCount() < req.count)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4122);

		ItemResource itemRS = gameResource.getItem().get(items.get(itemIndex).getItemId());
		if (itemRS == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4130);

		UserBalanceExportLog log = repositoryService.getUserBalanceExportLog(userId);
		if (log == null) {
			log = new UserBalanceExportLog();
			log.setUserId(userId);
			log.setItemType(ConstantVal.ITEM_TYPE_DIAMOND);
			log.setItemCount(ConstantVal.DEFAULT_ZERO);
			log.setItemId(ConstantVal.ITEM_TYPE_DIAMOND);
			log.setUpdateDate(TimeCalculation.getIntTime());
		}

		if (log.getUpdateDate() != TimeCalculation.getIntTime()) {
			log.setUpdateDate(TimeCalculation.getIntTime());
			log.setItemCount(ConstantVal.DEFAULT_ZERO);
		}

		if (log.getItemCount() + req.count > ConstantVal.NFT_BALANCE_MAX_COUNT)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4123);

		log.setItemCount(log.getItemCount() + req.count);

		MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();

		// parameters
		parameters.add("address", user.getWallet().trim());
		parameters.add("uid", user.getUuid());
		parameters.add("diamond", req.count);

		JSONObject result = send(nft_relay_server + "balance/withdraw", parameters);
		res.code = result.getString("code");
		res.result = result.getString("result");
		res.message = result.getString("msg");

		if (res.result.equals("true")) {
			// 다이아몬트 차감
			if (!repositoryService.setChangedItem(userId, itemIndex, items, -req.count,
					ConstantVal.LOG_TYPE_USE_NFT_MARKET, true))
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4124);

			repositoryService.setUserBalanceExportLog(userId, log);
			setNftItemExportLog(user.getId(), user.getWallet(), "balance/withdraw", itemRS.getDesc(), gson.toJson(log),
					res.code);
		} else {
			res.result = "false";
			res.message = "NFT issue failed.";
		}

		res.items = mapperVO.makeItemVO(userId, items, false);
		return res;
	}

	public ResDepositList BalanceDepositList(int userId) throws Exception {
		ResDepositList res = new ResDepositList();

		User user = repositoryService.getUser(userId, false);
		if (user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4125);

		if(serverMode.equals("live"))
		{
			if(user.getIsGuest() >= ConstantVal.IS_TRUE)
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4114);
		}

		MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
		parameters.add("address", user.getWallet());
		parameters.add("uid", user.getUuid());
		parameters.add("limit", String.valueOf(10));

		JSONObject mintResult = send(nft_relay_server + "balance/depositList", parameters);
		if (mintResult == null)
			return null;

		res.result = mintResult.getString("result");
		res.code = mintResult.getString("code");
		res.message = mintResult.getString("msg");

		if (!res.result.equals("true")) {
			res.result = "false";
			res.message = "NFT issue failed.";
			return res;
		} else {
			JSONArray array = mintResult.getJSONArray("data");
			if (array.length() <= ConstantVal.DEFAULT_ZERO) {
				// 받을 게 없다.
				return res;
			}

			// int totalSum = mintResult.getInt("totalSum");
			List<RewardItemVO> mailItems = new ArrayList<>();
			for (int i = 0; i < array.length(); i++) {
				mailItems.clear();

				String type = array.getJSONObject(i).getString("type");
				if (!type.equals("deposit"))
					continue;

				String id = array.getJSONObject(i).getString("id");

				String uid = array.getJSONObject(i).getString("uid");
				if (!user.getUuid().equals(uid)) {
					System.out.println("wrong uid - user:" + user.getUuid() + "|uid:" + uid);
				}

				String address = array.getJSONObject(i).getString("address");
				if (!user.getWallet().equals(address)) {
					System.out.println("wrong address - wallet:" + user.getWallet() + "|address:" + address);
				}

				int amount = array.getJSONObject(i).getInt("amount");
				if (amount > 0)
					mailItems.add(mapperVO.makeRewardItemVO((int) ConstantVal.ITEM_TYPE_RUBY,
							(int) ConstantVal.ITEM_TYPE_RUBY, amount));

				parameters.clear();
				parameters.add("id", id);
				parameters.add("address", user.getWallet());
				parameters.add("uid", user.getUuid());
				send(nft_relay_server + "balance/deposit", parameters);

				MailTitle mailRS = gameResource.getMail().get(ConstantVal.MAIL_TEXT_IMPORT_NFT, user.getLanguage());
				mailService.SendMails(userId, ConstantVal.MAIL_TYPE_CASH, mailRS.title,
						mailRS.contents, TimeCalculation.getCurrentUnixTime() + ConstantVal.MAIL_EXPIRE_TIME,
						mailItems);

				setNftItemImportLog(userId, user.getWallet(), "balance/depositList", id, "Y", mailItems.get(0));
			}
		}

		return res;
	}

	public RewardItemVO makeRewardToJson(String type, int itmeId, JSONArray array) throws Exception {
		RewardItemVO vo = null;

		switch (type) {
		case ConstantVal.NFT_TYPE_CHARACTER: {
			int characterId = Integer.valueOf(getJsonValue("character_id", array));
			int grade = findData.getStringToGrade(getJsonValue("grade", array));
			int level = Integer.valueOf(getJsonValue("level", array));
			int upgrade = Integer.valueOf(getJsonValue("enhance", array));
			int skill = Integer.valueOf(getJsonValue("active_skill_id", array));

			vo = mapperVO.makeRewardItemVO(ConstantVal.ITEM_TYPE_CHARACTER, characterId, 1, grade, level, upgrade,
					skill, null, null);
		}
			break;
		case ConstantVal.NFT_TYPE_PARTNER: {
			int partnerId = Integer.valueOf(getJsonValue("friends_id", array));
			int grade = findData.getStringToGrade(getJsonValue("grade", array));
			int level = Integer.valueOf(getJsonValue("level", array));
			int upgrade = Integer.valueOf(getJsonValue("enhance", array));
			int skill1 = Integer.valueOf(getJsonValue("skill_id_1", array));
			int skill2 = Integer.valueOf(getJsonValue("skill_id_2", array));
			int skill3 = Integer.valueOf(getJsonValue("skill_id_3", array));

			vo = mapperVO.makeRewardItemVO(ConstantVal.ITEM_TYPE_PARTNER, partnerId, 1, grade, level, upgrade, skill1,
					skill2, skill3);
		}
			break;
		case ConstantVal.NFT_TYPE_ITEM: {
			int itemtype = Integer.valueOf(getJsonValue("type", array));
			int itemId = Integer.valueOf(getJsonValue("item_id", array));
			int itemCount = Integer.valueOf(getJsonValue("item_count", array));

			vo = mapperVO.makeRewardItemVO(itemtype, itemId, itemCount);
		}
			break;
		}

		return vo;
	}

	private String getJsonValue(String key, JSONArray array) throws Exception {
		String data = "";
		for (int i = 0; i < array.length(); i++) {
			if (!array.getJSONObject(i).isNull(key)) {
				data = array.getJSONObject(i).getString(key);
				break;
			}
		}
		return data;
	}

	public ResNftCardExport characterMint(int userId, ReqCardExport req) throws Exception {
		User user = repositoryService.getUser(userId, false);
		if (user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4010);

		if (user.getWallet().isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4004);

		Preset user_preset = repositoryService.getPreset(userId, false);
		if (user_preset == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4078);

		List<Character> characters = repositoryService.getCharacters(user.getId(), false);
		if (characters == null || characters.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4039);

		int index = findData.findCharacterIndex(characters, req.uuid);
		if (index <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4096);

		if (characters.get(index).getIsNft() < ConstantVal.IS_TRUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4021);

		if (characters.get(index).getGrade() <= ConstantVal.CARD_GRADE_N)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4131);

		// 임무중인 캐릭터는 민팅을 할 수 없다.
		if (characters.get(index).getIsMission() > ConstantVal.IS_FALSE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4112);

		if (user_preset.getCharacterUid().equals(characters.get(index).getUid()))
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4022);

		CharacterResource characterRS = gameResource.getCharacter().get(characters.get(index).getCharacterId());
		if (characterRS == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4023);

		UserVersion userVersion = repositoryService.getUserVersion(userId, new Gson());
		if (userVersion == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4110);

		String version = String.valueOf(userVersion.getMajor()) + "." + String.valueOf(userVersion.getMinor()) + "."
				+ String.valueOf(userVersion.getPatch());

		String json = metaData.makeCharacterJson(characters.get(index), version);

		File metadata = new File(metadata_path + user.getId() + "_" + TimeCalculation.getCurrentUnixTime() + ".json");
		metadata.setWritable(true);
		metadata.setReadable(true);

		BufferedWriter writer = new BufferedWriter(new FileWriter(metadata, true));
		try {
			writer.write(json);
		} catch (IOException e) {
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4024, e.toString());
		} finally {
			writer.close();
		}

		// security 키 만들어서 패킷 발송
		String secret = UUID.randomUUID().toString().replace("-", "").substring(0, 16);

		JSONObject mintResult = send(user.getUuid(), user.getWallet(), nft_relay_server + "nft/mint",
				characters.get(index).getCharacterId(), ConstantVal.NFT_TYPE_CHARACTER, metadata, secret);

		ResNftCardExport res = new ResNftCardExport();
		res.result = mintResult.getString("result");
		res.code = mintResult.getString("code");
		res.message = mintResult.getString("msg");

		int lastId = setNftItemExportLog(user.getId(), user.getWallet(), "nft/mint", characters.get(index).getUid(),
				json, res.code);
		if (lastId <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4126);

		if (res.result.equals("true")) {
			res.unique = mintResult.getString("unique");
			res.hash = mintResult.getString("hash");

			String encryptData = encrypt(secret + res.unique);
			if (!encryptData.equals(res.hash))
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4127);

			characters.get(index).setUseOrNot(ConstantVal.IS_FALSE);
			repositoryService.setCharacter(userId, index, characters);

			setNFTInfo(res.hash, secret, res.unique, lastId);
		} else {
			// 클라이언트로 실패 결과 알려주기
			res.result = "false";
			res.message = "NFT issue failed.";
		}

		metadata.delete();

		res.characters = mapperVO.makeCharacterVO(userId, characters, false);
		
		//프로필 제거
		//profileService.delProfileToCardId(userId, characters.get(index).getCharacterId(), null);
		return res;
	}

	public ResNftCardExport friendsMint(int userId, ReqCardExport req) throws Exception {
		User user = repositoryService.getUser(userId, false);
		if (user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4095);

		if (user.getWallet().isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4005);

		Preset user_preset = repositoryService.getPreset(userId, false);
		if (user_preset == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4079);

		List<Partner> partners = repositoryService.getPartners(userId, false);
		if (partners == null || partners.isEmpty())
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4025);

		int index = findData.findPartnerIndex(partners, req.uuid);
		if (index <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4097);

		// CBT를 위한 임시 코드
		if (partners.get(index).getIsNft() < ConstantVal.IS_TRUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4026);

		if (partners.get(index).getGrade() <= ConstantVal.CARD_GRADE_N)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4132);

		// 임무중인 파트너는 민팅을 할 수 없다.
		if (partners.get(index).getIsMission() > ConstantVal.IS_FALSE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4113);

		List<String> equipPartners = new ArrayList<>();
		if (!user_preset.getPartner1Uid().isEmpty())
			equipPartners.add(user_preset.getPartner1Uid());

		if (!user_preset.getPartner2Uid().isEmpty())
			equipPartners.add(user_preset.getPartner2Uid());

		if (!user_preset.getPartner3Uid().isEmpty())
			equipPartners.add(user_preset.getPartner3Uid());

		equipPartners.add(partners.get(index).getUid());

		// 중복값을 �외개수가, 리스개수� �르멐러(중복� �� �리�에 �착�� �음)
		if (equipPartners.size() != equipPartners.stream().distinct().count()) {
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4027);
		}

		PartnerResource partnerRS = gameResource.getPartner().get(partners.get(index).getPartnerId());
		if (partnerRS == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4098);

		UserVersion userVersion = repositoryService.getUserVersion(userId, new Gson());
		if (userVersion == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4111);

		String version = String.valueOf(userVersion.getMajor()) + "." + String.valueOf(userVersion.getMinor()) + "."
				+ String.valueOf(userVersion.getPatch());

		String json = metaData.makeFriendsJson(partners.get(index), version);

		File metadata = new File(metadata_path + user.getId() + "_" + TimeCalculation.getCurrentUnixTime() + ".json");
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(metadata));
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4099);
		}

		// security 키 만들어서 패킷 발송
		String secret = UUID.randomUUID().toString().replace("-", "").substring(0, 16);

		JSONObject mintResult = send(user.getUuid(), user.getWallet(), nft_relay_server + "nft/mint",
				partners.get(index).getPartnerId(), ConstantVal.NFT_TYPE_PARTNER, metadata, secret);

		ResNftCardExport res = new ResNftCardExport();
		res.result = mintResult.getString("result");
		res.code = mintResult.getString("code");
		res.message = mintResult.getString("msg");

		int lastId = setNftItemExportLog(user.getId(), user.getWallet(), "nft/mint", partners.get(index).getUid(), json,
				res.code);
		if (lastId <= ConstantVal.DEFAULT_VALUE)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4128);

		if (res.result.equals("true")) {
			res.unique = mintResult.getString("unique");
			res.hash = mintResult.getString("hash");

			String encryptData = encrypt(secret + res.unique);
			if (!encryptData.equals(res.hash))
				throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4129);

			partners.get(index).setUseOrNot(ConstantVal.IS_FALSE);
			repositoryService.setPartner(userId, index, partners);

			setNFTInfo(res.hash, secret, res.unique, lastId);
		} else {
			// 실패 로그
			// 클라이언트로 실패 결과 보내주기
			res.result = "false";
			res.message = "NFT issue failed.";
		}

		// 저장된 메타데이터 삭제
		metadata.delete();

		res.partners = mapperVO.makePartnerVO(userId, partners, false);

		// 프로필 제거
		//profileService.delProfileToCardId(userId, partners.get(index).getPartnerId(), null);
		return res;
	}

	private JSONObject send(String url, MultiValueMap<String, Object> parameter) throws Exception {
		try {
			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
			factory.setConnectTimeout(5000); // ��아�정 5�
			factory.setReadTimeout(5000);// ��아�정 5�

			RestTemplate restTemplate = new RestTemplate(factory);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(parameter, headers);
			String result = restTemplate.postForObject(new URI(url), request, String.class);

			JSONObject jsonObj = new JSONObject(result);
			return jsonObj;

		} catch (HttpClientErrorException | HttpServerErrorException e) {
			return null;

		} catch (Exception e) {
			return null;
		}
	}

	private JSONObject send(String uuid, String wallet, String url, int itemId, String type, File metaData,
			String secret) throws Exception {
		MultipartUtility multipart = new MultipartUtility();

		try {
			List<String> response = multipart.send_file_execute(uuid, wallet, url, itemId, type, metaData, secret);
			String res = response.get(0);
			JSONObject jsonObj = new JSONObject(res);

			return jsonObj;

		} catch (Exception ex) {
			System.out.println(ex);
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_NFT_4102);
		} finally {

		}
	}

	public String encrypt(String value) {
		try {
			IvParameterSpec ivspec = new IvParameterSpec(hexToBytes(mmd_iv));
			SecretKeySpec skeySpec = new SecretKeySpec(hexToBytes(mmd_key), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivspec);

			byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
			return bytesToHex(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public byte[] hexToBytes(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((java.lang.Character.digit(s.charAt(i), 16) << 4)
					+ java.lang.Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	public static String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	public String decrypt(String encrypted) throws Exception {
		try {
			IvParameterSpec ivspec = new IvParameterSpec(hexToBytes(mmd_iv));
			SecretKeySpec skeySpec = new SecretKeySpec(hexToBytes(mmd_key), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivspec);

			byte[] original = cipher.doFinal(hexToBytes(encrypted));

			return new String(original, StandardCharsets.UTF_8);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private void setNftItemImportLog(int userId, String wallet, String url, String id, String createdGameYn, RewardItemVO nft)
			throws Exception {
		NftItemImportLog log = new NftItemImportLog();
		log.setPtn_month((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_MONTH, 0));
		log.setPtn_day((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_DAY, 0));
		log.setLog_date(TimeCalculation.getCurrentUnixTime());
		log.setUser_id(userId);
		log.setWallet(wallet);
		log.setUrl(url);
		log.setMarket_id(id);
		log.setCreated_game_yn(createdGameYn);
		log.setItem_type(nft.itemType);
		log.setItem_id(nft.itemId);
		log.setItem_count(nft.itemCount);
		log.setGrade(nft.grade != null ? nft.grade : ConstantVal.DEFAULT_VALUE);
		log.setLevel(nft.level != null ? nft.level : ConstantVal.DEFAULT_VALUE);
		log.setUpgrade(nft.upGrade != null ? nft.upGrade : ConstantVal.DEFAULT_VALUE);
		log.setSkill1(nft.skill1 != null ? nft.skill1 : ConstantVal.DEFAULT_VALUE);
		log.setSkill2(nft.skill2 != null ? nft.skill2 : ConstantVal.DEFAULT_VALUE);
		log.setSkill3(nft.skill3 != null ? nft.skill3 : ConstantVal.DEFAULT_VALUE);

		repositoryService.setNftItemImportLog(userId, log);
	}

	private int setNftItemExportLog(int userId, String wallet, String url, String uid, String metaData, String resCode)
			throws Exception {
		NftItemExportLog log = new NftItemExportLog();
		log.setPtn_month((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_MONTH, 0));
		log.setPtn_day((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_DAY, 0));
		log.setLog_date(TimeCalculation.getCurrentUnixTime());
		log.setUser_id(userId);
		log.setWallet(wallet);
		log.setUrl(url);
		log.setUid(uid);
		log.setMeta_data(metaData);
		log.setResCode(resCode);

		return repositoryService.setNftItemExportLog(userId, log);
	}

	private void setNFTInfo(String hash, String secret, String unique, int logId) throws Exception {
		NFT nft = new NFT();
		nft.setHash(hash);
		nft.setSecret(secret);
		nft.setUnique(unique);
		nft.setLogId(logId);
		nft.setIsValid(ConstantVal.DEFAULT_ZERO);

		repositoryService.setNftInfo(nft);
	}

	private void setNftImportErrorLog(int userId, String createdGameYn, String hash, String secret, String unique, String json)
			throws Exception {
		NftImportErrorLog log = new NftImportErrorLog();

		log.setPtn_month((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_MONTH, 0));
		log.setPtn_day((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_DAY, 0));
		log.setLog_date(TimeCalculation.getCurrentUnixTime());
		log.setUser_id(userId);
		log.setCreated_game_yn(createdGameYn);
		log.setHash(hash);
		log.setSecret(secret);
		log.setUnique(unique);
		log.setJson(json);

		repositoryService.setNftImportErrorLog(log);
	}
}
