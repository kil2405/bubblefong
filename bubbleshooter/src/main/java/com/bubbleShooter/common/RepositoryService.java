package com.bubbleShooter.common;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bubbleShooter.domain.AttendanceEventRewardLog;
import com.bubbleShooter.domain.AttendanceRewardLog;
import com.bubbleShooter.domain.CdnInfo;
import com.bubbleShooter.domain.ChangeNicknameLog;
import com.bubbleShooter.domain.Character;
import com.bubbleShooter.domain.ContentsAdminssion;
import com.bubbleShooter.domain.Event;
import com.bubbleShooter.domain.EventLog;
import com.bubbleShooter.domain.GachaList;
import com.bubbleShooter.domain.GachaReward;
import com.bubbleShooter.domain.GachaRewardLog;
import com.bubbleShooter.domain.Mail;
import com.bubbleShooter.domain.MiniGameRoom;
import com.bubbleShooter.domain.MinigamePlayLog;
import com.bubbleShooter.domain.Mission;
import com.bubbleShooter.domain.MissionLog;
import com.bubbleShooter.domain.NFT;
import com.bubbleShooter.domain.NftImportErrorLog;
import com.bubbleShooter.domain.NftItemExportLog;
import com.bubbleShooter.domain.NftItemImportLog;
import com.bubbleShooter.domain.Notice;
import com.bubbleShooter.domain.Partner;
import com.bubbleShooter.domain.Preset;
import com.bubbleShooter.domain.Profile;
import com.bubbleShooter.domain.Rank;
import com.bubbleShooter.domain.RankingRewardLog;
import com.bubbleShooter.domain.Room;
import com.bubbleShooter.domain.ShopPurchaseLog;
import com.bubbleShooter.domain.User;
import com.bubbleShooter.domain.UserBalanceExportLog;
import com.bubbleShooter.domain.UserConnectLog;
import com.bubbleShooter.domain.UserItem;
import com.bubbleShooter.domain.UserProfileLog;
import com.bubbleShooter.domain.UserShopBuyLog;
import com.bubbleShooter.domain.UserVersion;
import com.bubbleShooter.util.TimeCalculation;
import com.google.gson.Gson;

@Service
public class RepositoryService {

	@Autowired
	private StorageContext storageContext;

	////////////////////////////////////////////////////////////////
	// �이변�
	////////////////////////////////////////////////////////////////
	public boolean setChangedItem(int userId, int itemType, List<UserItem> items, int addValue, String logType, boolean dbUpdate) throws Exception
	{
		return setChangedItem(userId, itemType, items, addValue, logType, "", dbUpdate);
	}
	
	public boolean setChangedItem(int userId, int itemType, List<UserItem> items, int addValue, String logType, String text, boolean dbUpdate) throws Exception
	{
		int itemCount = items.get(itemType).getItemCount();
		if(addValue < 0)
		{
			if((itemCount + addValue) < 0)
				return false;
		}
		
		User user = getUser(userId, false);
		if(user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_REPOSITORY_1300);
		
		items.get(itemType).setItemCount(items.get(itemType).getItemCount() + addValue);
		
		if(dbUpdate)
			setUserItem(userId, itemType, items);
		
		//Log 추�
		GameMoneyLog log = new GameMoneyLog();
		log.setPtn_month((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_MONTH, 0));
		log.setPtn_day((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_DAY, 0));
		log.setLog_date(TimeCalculation.getCurrentUnixTime());
		log.setLog_type(logType);
		log.setUser_id(userId);
		log.setMoney_type(items.get(itemType).getItemType());
		log.setItem_id(items.get(itemType).getItemId());
		log.setAdd_value(addValue);
		log.setTotal_value(items.get(itemType).getItemCount());
		log.setDescription(text);
		setGameMoneyLog(log);
		return true;
	}
	
	////////////////////////////////////////////////////////////////
	// Mail Log
	//////////////////////////////////////////////////////////////
//	public void setMailOpenLog(long userId, long mailId, String logType, byte mailType, List<MailItem> mailItems) throws Exception
//	{
//		MailLog log = new MailLog();
//		log.setPtn_month((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_MONTH, 0));
//		log.setPtn_day((byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_DAY, 0));
//		log.setLog_date(TimeCalculation.getCurrentUnixTime());
//		log.setUser_id(userId);
//		log.setMail_id(mailId);
//		log.setLog_mail_type(mailType);
//		
//		for(int index = 0; index < mailItems.size(); index++)
//		{
//			if(mailItems.get(index) == null)
//				continue;
//			
//			switch(index)
//			{
//			case 0:
//				log.setMailItem_index0(mailItems.get(index).getIndex());
//				break;
//			case 1:
//				log.setMailItem_index1(mailItems.get(index).getIndex());
//				break;
//			case 2:
//				log.setMailItem_index2(mailItems.get(index).getIndex());
//				break;
//			case 3:
//				log.setMailItem_index3(mailItems.get(index).getIndex());
//				break;
//			case 4:
//				log.setMailItem_index4(mailItems.get(index).getIndex());
//				break;
//			}
//		}
//		setMailLog(userId, log);
//		
//	}
	
	public void setErrorLog(int userId, Object param)
	{
		try{
			storageContext.getDataOne(ConstantVal.LOG_DB, "error_log_INS_SP", param);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////////////////////
	// Filter
	////////////////////////////////////////////////////////////////
	public User getRedisUser(int userId, Gson gson) {
		String json = storageContext.getJsonData(ConstantVal.GAME_DB, "user:" + userId);
		if (json == null)
			return null;

		User user = gson.fromJson(json, User.class);
		return user;
	}
	
	public UserVersion getUserVersion(int userId, Gson gson) {
		String json = storageContext.getJsonData(ConstantVal.GAME_DB, "user_version:" + userId);
		if (json == null)
			return null;

		UserVersion userVersion = gson.fromJson(json, UserVersion.class);
		return userVersion;
	}

	public void setUserVersion(int userId, User user, UserVersion userVersion, Gson gson) {
		if (user == null || userVersion == null)
			return;

		String json = gson.toJson(user, User.class);
		storageContext.putJsonData(ConstantVal.GAME_DB, "user:" + userId, json);

		json = gson.toJson(userVersion, UserVersion.class);
		storageContext.putJsonData(ConstantVal.GAME_DB, "user_version:" + userId, json);
	}
	
	//User
	public User getUser(long userId) throws Exception
	{
		String redisKey = "user:";
		return storageContext.<User> getFromStorage(ConstantVal.GAME_DB, "user_LST_SP", redisKey + userId, userId, User.class, true);
	}
	
	public User getUser(String uuid) throws Exception
	{
		return storageContext.<User> getDataOne(ConstantVal.GAME_DB, "user_uuid_LST_SP", uuid);
	}
	
	public User getUser(int userId, boolean dbForceSearch) throws Exception
	{
		String redisKey = "user:";
		return storageContext.<User> getFromStorage(ConstantVal.GAME_DB, "user_LST_SP", redisKey + userId, userId, User.class, dbForceSearch);
	}

	public User getUserToNickname(String nickname) throws Exception
	{
		return storageContext.<User> getDataOne(ConstantVal.GAME_DB, "user_nickname_LST_SP", nickname);
	}
	
	public void setUser(int userId, User user) throws Exception
	{
		String redisKey = "user:";
		storageContext.<User> setToStorage(ConstantVal.GAME_DB, "user_UDT_SP", redisKey + userId, user);
	}
	
	public void setNewUser(int userId, User user) throws Exception
	{
		String redisKey = "user:";
		if(userId <= 0)
		{
			Gson gson = new Gson();
			storageContext.getDataOne(ConstantVal.GAME_DB, "user_INS_SP", user);
			User newUser = getUser(user.getUuid());
			user.setId(newUser.getId());
			
			String json = gson.toJson(user);
			storageContext.putJsonData(ConstantVal.GAME_DB, redisKey + user.getId(), json);
		}
	}
	
	public void setbotUser(int userId, User user) throws Exception
	{
		String redisKey = "user:";
		storageContext.<User> setToStorage(ConstantVal.GAME_DB, "user_bot_INS_SP", redisKey + userId, user);
	}
	
	public Integer checkNickname(String nickname) throws Exception
	{
		return storageContext.getDataOne(ConstantVal.GAME_DB, "check_nickname_LST_SP", nickname);
	}
	
	//-------------------------------------------------------------------------------------------------------------------------
	//item
	public List<UserItem> getUserItems(int userId, boolean dbForceSearch) throws Exception
	{
		String redisKey = "user_item:";
		return storageContext.<UserItem> getFromListStorage(ConstantVal.GAME_DB, "user_item_LST_SP", redisKey + userId, userId, UserItem[].class, dbForceSearch);
	}
	
	public void setUserItem(int userId, int updateNum, List<UserItem> items) throws Exception
	{
		if(items.size() < (updateNum -1))
			return;
		
		String redisKey = "user_item:";
		storageContext.setToListStorage(ConstantVal.GAME_DB, "user_item_INSUDT_SP", redisKey + userId, updateNum, items, UserItem[].class);
	}
	
	public void setUserItem(int userId, UserItem item) throws Exception
	{
		storageContext.getDataOne(ConstantVal.GAME_DB, "user_item_INSUDT_SP", item);
	}
	
	public void initUserItemData(int userId, HashMap<String,List<UserItem>> param) throws Exception
	{
		storageContext.getDataOne(ConstantVal.GAME_DB, "init_item_INS", param);
	}
	
	//character
	public List<Character> getCharacters(int userId, boolean dbForceSearch) throws Exception
	{
		String redisKey = "character:";
		return storageContext.<Character> getFromListStorage(ConstantVal.GAME_DB, "character_LST_SP", redisKey + userId, userId, Character[].class, dbForceSearch);
	}
	
	public void setCharacter(int userId, int updateNum, List<Character> characters) throws Exception
	{
		if(characters.size() < (updateNum -1))
			return;
		
		String redisKey = "character:";
		storageContext.setToListStorage(ConstantVal.GAME_DB, "character_INSUDT_SP", redisKey + userId, updateNum, characters, Character[].class);
	}
	
	public void setCharacter(int userId, Character character) throws Exception
	{
		storageContext.getDataOne(ConstantVal.GAME_DB, "character_INSUDT_SP", character);
	}
	
	//partner
	public List<Partner> getPartners(int userId, boolean dbForceSearch) throws Exception
	{
		String redisKey = "partner:";
		return storageContext.<Partner> getFromListStorage(ConstantVal.GAME_DB, "partner_LST_SP", redisKey + userId, userId, Partner[].class, dbForceSearch);
	}
	
	public void setPartner(int userId, int updateNum, List<Partner> partners) throws Exception
	{
		if(partners.size() < (updateNum - 1))
			return;
		
		String redisKey = "partner:";
		storageContext.setToListStorage(ConstantVal.GAME_DB, "partner_INSUDT_SP", redisKey + userId, updateNum, partners, Partner[].class);
	}
	
	public void setPartner(int userId, Partner partner) throws Exception
	{
		storageContext.getDataOne(ConstantVal.GAME_DB, "partner_INSUDT_SP", partner);
	}
	
	public Preset getPreset(int userId, boolean dbForceSearch) throws Exception
	{
		String redisKey = "preset:";
		return storageContext.getFromStorage(ConstantVal.GAME_DB, "preset_LST_SP", redisKey + userId, userId, Preset.class, dbForceSearch);
	}
	
	public void setPreset(int userId, Preset preset) throws Exception
	{
		String redisKey = "preset:";
		storageContext.setToStorage(ConstantVal.GAME_DB, "preset_INSUDT_SP", redisKey + userId,  preset);
	}
	
	//Room
	public Room getRoom(String uuid, boolean dbForceSearch) throws Exception
	{
		String redisKey = "room:";
		return storageContext.getFromStorage(ConstantVal.GAME_DB, "room_LST_SP", redisKey + uuid, uuid, Room.class, dbForceSearch);
	}
	
	public void setRoom(String uuid, Room room) throws Exception
	{
		String redisKey = "room:";
		storageContext.setToStorage(ConstantVal.GAME_DB, "room_INSUDT_SP", redisKey + uuid, room);
	}
	
	public Rank getRank(int userId, String roomId) throws Exception
	{
		HashMap<Object, Object> param = new HashMap<>();
		param.put("userId", userId);
		param.put("roomUid", roomId);
		
		return storageContext.getDataOne(ConstantVal.GAME_DB, "rank_LST_SP", param);
	}
	
	public void setRank(int userId, Rank rank) throws Exception
	{
		storageContext.getDataOne(ConstantVal.GAME_DB, "rank_INSUDT_SP", rank);
	}
	
	public List<Notice> getNotice() throws Exception
	{
		return storageContext.<Notice> getDataList(ConstantVal.GAME_DB, "notice_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
	}
	
	//minigame
	public void setMiniGameRoom(String uuid, MiniGameRoom room) throws Exception
	{
		String redisKey = "mini_game_room:";
		storageContext.setToStorage(ConstantVal.GAME_DB, "mini_game_room_INSUDT_SP", redisKey + uuid, room);
	}
	
	public MiniGameRoom getMinigameRoom(String uuid, boolean dbForceSearch) throws Exception
	{
		String redisKey = "mini_game_room:";
		return storageContext.getFromStorage(ConstantVal.GAME_DB, "mini_game_room_LST_SP", redisKey + uuid, uuid, MiniGameRoom.class, dbForceSearch);
	}
	
	//-------------------------------------------------------------------------------------------------------------------------
	//other
	public String getPatchUrl() throws Exception
	{
		CdnInfo info = storageContext.getDataOne(ConstantVal.GAME_DB, "patch_LST_SP");
		if(info == null)
			return null;
		
		return info.getCdnUrl();
		
	}
	
	public void onlySetRedis(byte division, String redisKey, String json, int expiredTime) throws Exception
	{
		storageContext.getRedis(division).opsForValue().set(redisKey, json, expiredTime, TimeUnit.MINUTES);
	}
	
	//-------------------------------------------------------------------------------------------------------------------------
	//mail
	public List<Mail> getMail(int userId) throws Exception
	{
		return storageContext.getDataList(ConstantVal.GAME_DB, "mail_LST_SP", userId);
	}
	
	public Mail getMailOne(int mailId) throws Exception
	{
		return storageContext.getDataOne(ConstantVal.GAME_DB, "mail_ONE_LST_SP", mailId);
	}
	
	public void updateMail(int userId, Mail mail) throws Exception
	{
		storageContext.<Mail, Mail>getDataOne(ConstantVal.GAME_DB, "mail_UDT_SP", mail);
	}
	
	public void setMail(int userId, Mail mail) throws Exception
	{
		storageContext.getDataOne(ConstantVal.GAME_DB, "mail_INSUDT_SP", mail);
	}
	
	//mission
	public List<Mission> getMission(int userId, boolean dbForceSearch) throws Exception
	{
		String redisKey = "mission:";
		return storageContext.<Mission> getFromListStorage(ConstantVal.GAME_DB, "mission_LST_SP", redisKey + userId, userId, Mission[].class, dbForceSearch);
	}
	
	public void setMission(int userId, int updateNum, List<Mission> missions) throws Exception
	{
		if(missions.size() < (updateNum - 1))
			return;
		
		String redisKey = "mission:";
		storageContext.setToListStorage(ConstantVal.GAME_DB, "mission_INSUDT_SP", redisKey + userId, updateNum, missions, Mission[].class);
	}
	
	//gacha
	public List<GachaList> getGachaList() throws Exception
	{
		return storageContext.getDataList(ConstantVal.GAME_DB, "gacha_list_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
	}
	
	public void setGacha(GachaList gacha) throws Exception
	{
		storageContext.getDataOne(ConstantVal.GAME_DB, "gacha_list_INSUDT_SP", gacha);
	}
	
	public List<GachaReward> getGachaRewards(int seq, int gachaId) throws Exception
	{
		HashMap<Object, Object> param = new HashMap<>();
		param.put("seq", seq);
		param.put("gachaId", gachaId);
		
		return storageContext.getDataList(ConstantVal.GAME_DB, "gacha_reward_LST_SP", param);
	}
	
	public void setGachaReward(GachaReward reward) throws Exception
	{
		storageContext.getDataOne(ConstantVal.GAME_DB, "gacha_reward_INSUDT_SP", reward);
	}
	
	public Integer gachaPickWinner(int seq, int gachaId) throws Exception
	{
		HashMap<Object, Object> param = new HashMap<>();
		param.put("seq", seq);
		param.put("gachaId", gachaId);
		
		return storageContext.getDataOne(ConstantVal.GAME_DB, "gacha_pick_winner_INSUDT_SP", param);
	}
	
	//contents admission
	public ContentsAdminssion getContentsAdmission(int contentsId) throws Exception
	{
		HashMap<Object, Object> param = new HashMap<>();
		param.put("contentsId", contentsId);
		
		return storageContext.getDataOne(ConstantVal.GAME_DB, "contents_admission_LST_SP", param);
	}
	
	public List<Event> getEvent() throws Exception
	{
		return storageContext.<Event> getDataList(ConstantVal.GAME_DB, "event_LST_SP", ConstantVal.NONE_SEARCHE_KEY);
	}
	
	//nft
	public void setNftInfo(NFT nft) throws Exception
	{
		storageContext.getDataOne(ConstantVal.GAME_DB, "nft_INSUDT_SP", nft);
	}
	
	public void updateNftInfo(String hash, int valid) throws Exception
	{
		HashMap<Object, Object> param = new HashMap<>();
		param.put("hash", hash);
		param.put("isValid", valid);
		
		storageContext.getDataOne(ConstantVal.GAME_DB, "nft_UDT_SP", param);
	}
	
	public NFT getNftInfo(String hash) throws Exception
	{
		HashMap<Object, Object> param = new HashMap<>();
		param.put("hash", hash);
		
		return storageContext.getDataOne(ConstantVal.GAME_DB, "nft_LST_SP", param);
	}
	
	// LOG 관련함수 -------------------------------------------------------------------------------------------
	public void setUserConnectLog(UserConnectLog log) throws Exception
	{
		storageContext.getDataOne(ConstantVal.LOG_DB, "user_connect_log_INS_SP", log);
	}
	
	public void setGameMoneyLog(GameMoneyLog log) throws Exception
	{
		storageContext.getDataOne(ConstantVal.LOG_DB, "game_money_log_INS_SP", log);
	}
	
	public void setChangeNickNameLog(int userId, ChangeNicknameLog log) throws Exception
	{
		storageContext.getDataOne(ConstantVal.LOG_DB, "change_nickname_log_INS_SP", log);
	}
	
	public ChangeNicknameLog getChangeNicknameLog(int userId) throws Exception
	{
		return storageContext.getDataOne(ConstantVal.LOG_DB, "change_nickname_log_LST_SP", userId);
	}
	
	public List<UserShopBuyLog> getUserShopBuyLog(int userId, boolean dbForceSearch) throws Exception
	{
		String redisKey = "user_shop_buy_log:";
		return storageContext.<UserShopBuyLog> getFromListStorage(ConstantVal.GAME_DB, "user_shop_buy_log_LST_SP", redisKey + userId, userId, UserShopBuyLog[].class, dbForceSearch);
	}
	
	public void setUserShopBuyLog(int userId, UserShopBuyLog log, ShopPurchaseLog purchaseLog) throws Exception
	{
		String redisKey = "user_shop_buy_log:";
		storageContext.<UserShopBuyLog> setToStorage(ConstantVal.GAME_DB, "user_shop_buy_log_INSUDP_SP", redisKey + userId, log);
		
		List<UserShopBuyLog> logs = getUserShopBuyLog(userId, true);
		storageContext.putJsonData(ConstantVal.GAME_DB, redisKey + userId, logs, UserShopBuyLog[].class);
		
		//운영툴용 로그 추가
		storageContext.getDataOne(ConstantVal.LOG_DB, "shop_purchase_log_INS_SP", purchaseLog);
	}
	
	public void setRankingRewardLog(int userId, RankingRewardLog log) throws Exception
	{
		storageContext.getDataOne(ConstantVal.LOG_DB, "ranking_reward_log_INS_SP", log);
	}
	
	public RankingRewardLog getRankingRewardLog(int userId, int season) throws Exception
	{
		HashMap<Object, Object> param = new HashMap<>();
		param.put("user_id", userId);
		param.put("reward_season", season);
		
		return storageContext.getDataOne(ConstantVal.LOG_DB, "ranking_reward_log_LST_SP", param);
	}
	
	// attendanceReward Log
	public List<AttendanceRewardLog> getAttendanceLog(long userId) throws Exception
	{
		return storageContext.getDataList(ConstantVal.LOG_DB, "attendance_log_LST_SP", userId);
	}
	
	public void setAttendanceLog(long userId, AttendanceRewardLog log) throws Exception
	{
		storageContext.getDataOne(ConstantVal.LOG_DB, "attendance_log_INS_SP", log);
	}
	
	// attendanceEventReward Log
	public AttendanceEventRewardLog getAttendanceEventLog(long userId, int event_no) throws Exception
	{
		HashMap<Object, Object> param = new HashMap<>();
		param.put("user_id", userId);
		param.put("event_no", event_no);
		
		return storageContext.getDataOne(ConstantVal.LOG_DB, "attendance_event_log_LST_SP", param);
	}
	
	public void setAttendanceEventLog(long userId, AttendanceEventRewardLog log) throws Exception
	{
		storageContext.getDataOne(ConstantVal.LOG_DB, "attendance_event_log_INS_SP", log);
	}

	// Profile
	public List<Profile> getProfiles(int userId, boolean dbForceSearch) throws Exception
	{
		String redisKey = "profile:";
		return storageContext.<Profile> getFromListStorage(ConstantVal.GAME_DB, "profile_LST_SP", redisKey + userId, userId, Profile[].class, dbForceSearch);
	}

	public void setProfile(int userId, int updateNum, List<Profile> profiles) throws Exception
	{
		if(profiles.size() < (updateNum - 1))
			return;
		
		String redisKey = "profile:";
		storageContext.setToListStorage(ConstantVal.GAME_DB, "profile_INSUDT_SP", redisKey + userId, updateNum, profiles, Profile[].class);
	}

	public void setProfile(int userId, Profile profile) throws Exception
	{
		storageContext.getDataOne(ConstantVal.GAME_DB, "profile_INSUDT_SP", profile);
	}

	public void initProfile(int userId, HashMap<String,List<Profile>> param) throws Exception
	{
		storageContext.getDataOne(ConstantVal.GAME_DB, "init_profile_INS", param);
	}
	
	//NFT
	public void setNftItemImportLog(long userId, NftItemImportLog log) throws Exception
	{
		storageContext.getDataOne(ConstantVal.LOG_DB, "nft_item_import_log_INS_SP", log);
	}
	
	public int setNftItemExportLog(long userId, NftItemExportLog log) throws Exception
	{
		return storageContext.getDataOne(ConstantVal.LOG_DB, "nft_item_export_log_INS_SP", log);
	}
	
	public void setUserBalanceExportLog(int userId, UserBalanceExportLog log) throws Exception
	{
		storageContext.getDataOne(ConstantVal.GAME_DB, "user_balance_export_log_INSUDT_SP", log);
	}
	
	public UserBalanceExportLog getUserBalanceExportLog(int userId) throws Exception
	{
		return storageContext.getDataOne(ConstantVal.GAME_DB, "user_balance_export_log_LST_SP", userId);
	}
	
	public List<NftItemExportLog> getNftItemExportLog(long userId) throws Exception
	{
		HashMap<String, Object> param = new HashMap<>();
		param.put("ptn_month", (byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_MONTH, 0));
		param.put("ptn_day", (byte) TimeCalculation.getCurCalendar(ConstantVal.DATE_SECTION_DAY, 0));
		param.put("user_id", userId);
		
		return storageContext.getDataList(ConstantVal.LOG_DB, "nft_item_export_log_LST_SP", param);
	}
	
	// Mission Log
	public void setMissionLog(int userId, MissionLog log) throws Exception
	{
		storageContext.getDataOne(ConstantVal.LOG_DB, "mission_log_INS_SP", log);
	}
	
	// Gacha Reward Log
	public void setGachaRewardLog(int userId, GachaRewardLog log) throws Exception
	{
		storageContext.getDataOne(ConstantVal.LOG_DB, "gacha_reward_log_INS_SP", log);
	}
	
	// minigame play log
	public void setMiniGamePlayLog(MinigamePlayLog log) throws Exception
	{
		storageContext.getDataOne(ConstantVal.LOG_DB, "mini_game_play_log_INS_SP", log);
	}
	
	public MinigamePlayLog getMinigamePlayLog(int userId, String roomId) throws Exception
	{
		HashMap<Object, Object> param = new HashMap<>();
		param.put("user_id", userId);
		param.put("room_id", roomId);
		
		return storageContext.getDataOne(ConstantVal.LOG_DB, "mini_game_play_log_LST_SP", param);
	}
	
	// minigame play log
	public void setEventLog(EventLog log) throws Exception
	{
		storageContext.getDataOne(ConstantVal.LOG_DB, "event_log_INS_SP", log);
	}
	
	public List<EventLog> getEventLog(int userId) throws Exception
	{
		return storageContext.<EventLog> getDataList(ConstantVal.LOG_DB, "event_log_LST_SP", userId);
	}
	
	// nft import Error log
	public void setNftImportErrorLog(NftImportErrorLog log) throws Exception
	{
		storageContext.getDataOne(ConstantVal.LOG_DB, "nft_import_error_log_INS_SP", log);
	}
	
	public List<NftImportErrorLog> getNftImportErrorLog(int userId) throws Exception
	{
		return storageContext.<NftImportErrorLog> getDataList(ConstantVal.LOG_DB, "nft_import_error_log_LST_SP", userId);
	}

	// profile log
	public void setProfileLog(int userId, UserProfileLog log) throws Exception
	{
		storageContext.getDataOne(ConstantVal.LOG_DB, "user_profile_log_INS_SP", log);
	}
	
	//--------------------------------------------------------------------------------------------------------
}
