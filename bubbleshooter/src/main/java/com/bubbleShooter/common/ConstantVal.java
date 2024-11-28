package com.bubbleShooter.common;

public class ConstantVal {
	public static final String ERROR_MES_DT = "BRTC:";
	
	//Join
	public static final byte AUTH_JOIN = 1;
	public static final byte AUTH_FIND_PASSWORD = 2;
	
	// Login result Type
	public static final int LOGIN_TYPE_SUCCESS = 100;						// �공
	public static final int LOGIN_TYPE_NOT_FOUND_USER = 101;				// �� �음 (�용�함)
	public static final int LOGIN_TYPE_BLOCK = 102;							// 차단��
	
	// Market Type
	public static final byte MARKET_TYPE_IOS = 0;							// Apple - GameCenter
	public static final byte MARKET_TYPE_AOS = 1;							// Google - PlayStore
	
	// Social Platform
	public static final byte SOCIAL_PLATFORM_GOOGLE = 0;					// 구� 계정�동
	public static final byte SOCIAL_PLATFORM_IOS = 1;						// IOS 계정�동
	public static final byte SOCIAL_PLATFORM_GAMEBASE = 2;					// GameBase 계정 �동
	
	// ITEM_TYPE
	public static final byte ITEM_TYPE_GOLD = 0;
	public static final byte ITEM_TYPE_RUBY = 1;
	public static final byte ITEM_TYPE_DIAMOND = 2;
	public static final byte ITEM_TYPE_ITEM = 3;
	public static final byte ITEM_TYPE_CHARACTER = 4;
	public static final byte ITEM_TYPE_PARTNER = 5;
	public static final byte ITEM_TYPE_RANDOM_PACK_TICKET = 6;
	public static final byte ITEM_TYPE_RANDOM_MAIL_PACK = 7;
	public static final byte ITEM_TYPE_EMOTICON = 8;
	public static final byte ITEM_TYPE_TROPHY = 9;
	
	public static final int PARTNER_SKILL_CHANGE_RUBY = 50;
	public static final int RANDOM_BOX_TICKET_ID = 1000002;
	public static final int COMPETITION_TICKET_ID = 1000001;
	public static final int MINIGAME_TICKET_ID = 1000008;
	
	// BP
	public static final int MAX_BP_POINT = 300;
	
	// Log Type
	// 소모
	public static final String LOG_TYPE_USE_CHARACTER_UPGRADE = "USE_CHARACTER_UPGRADE";
	public static final String LOG_TYPE_USE_CHARACTER_ENHANCE = "USE_CHARACTER_ENHANCE";
	public static final String LOG_TYPE_USE_PARTNER_UPGRADE = "USE_PARTNER_UPGRADE";
	public static final String LOG_TYPE_USE_PARTNER_ADVANCE = "USE_PARTNER_ADVANCE";
	public static final String LOG_TYPE_USE_PARTNER_ENHANCE = "USE_PARTNER_ENHANCE";
	public static final String LOG_TYPE_USE_STAR_PACK = "USE_STAR_PACK";
	public static final String LOG_TYPE_USE_PARTNER_CHANGE_SKILL = "LOG_TYPE_USE_PARTNER_CHANGE_SKILL";
	public static final String LOG_TYPE_USE_NFT_MARKET = "LOG_TYPE_USE_NFT_MARKET";
	public static final String LOG_TYPE_USE_BUY_SHOP = "LOG_TYPE_USE_BUY_SHOP";
	public static final String LOG_TYPE_USE_OPEN_STAR_BOX = "LOG_TYPE_USE_OPEN_STAR_BOX";
	public static final String LOG_TYPE_USE_COMPOSE_CHARACTER = "LOG_TYPE_USE_COMPOSE_CHARACTER";
	public static final String LOG_TYPE_USE_COMPOSE_PARTNER = "LOG_TYPE_USE_COMPOSE_PARTNER";
	public static final String LOG_TYPE_USE_RANDOM_BOX = "LOG_TYPE_USE_RANDOM_BOX";
	public static final String LOG_TYPE_USE_COMPETITION_TICKET = "LOG_TYPE_USE_COMPETITION_TICKET";
	public static final String LOG_TYPE_USE_MINIGAME_TICKET = "LOG_TYPE_USE_MINIGAME_TICKET";
		
	// 획득
	public static final String LOG_TYPE_GET_ATTENDANCE_EVENT = "GET_ATTENDANCE_EVENT";
	public static final String LOG_TYPE_GET_GAME_REWARD = "GET_GAME_REWARD";
	public static final String LOG_TYPE_GET_CREATE_ACCOUNT = "LOG_TYPE_GET_CREATE_ACCOUNT";
	public static final String LOG_TYPE_GET_MAIL_ITEM = "LOG_TYPE_GET_MAIL_ITEM";
	public static final String LOG_TYPE_GET_PARTNER_ADVANECE = "LOG_TYPE_GET_PARTNER_ADVANECE";
	public static final String LOG_TYPE_GET_STAR_PACK = "LOG_TYPE_GET_STAR_PACK";
	public static final String LOG_TYPE_GET_MINING_REWARD = "LOG_TYPE_GET_MINING_REWARD";
	public static final String LOG_TYPE_GET_BUY_SHOP = "LOG_TYPE_GET_BUY_SHOP";
	public static final String LOG_TYPE_GET_RANKING_REWARD = "LOG_TYPE_GET_RANKING_REWARD";
	public static final String LOG_TYPE_GET_STAR_BOX_KEY = "LOG_TYPE_GET_STAR_BOX_KEY";
	public static final String LOG_TYPE_GET_COMPOSE_CHARACTER = "LOG_TYPE_USE_COMPOSE_CHARACTER";
	public static final String LOG_TYPE_GET_COMPOSE_PARTNER = "LOG_TYPE_GET_COMPOSE_PARTNER";
	public static final String LOG_TYPE_GET_MINIGAME_REWARD = "LOG_TYPE_GET_MINIGAME_REWARD";
	
	
	// Valid Type
	public static final byte DISABLE = 0;
	public static final byte ENABLE = 1;
	public static final byte DISABLE_BAN = 2;
	
	//최초 계정생성 시 필요데이터
	public static final int INIT_ACCOUNT_LEVEL = 1;
	public static final int INIT_ACCOUNT_EXP = 0;
	public static final int[] DEFAULT_CREATE_CHARACTER = { 1020000 };
	public static final int[] NFT_CHARACTER_ID_LIST = {1020001, 1020002, 1020003, 1020004, 1020005, 1020006, 1020007, 1020008};
	public static final int[][] DAILYEARN_LIMIT_BY_GRADE = { {0, 0, 0, 0}, {30, 36, 45, 60}, {60, 72, 90, 120}, {100, 120, 150, 200}};
	
	public static final int[] CHARACTER_LIMIT_LEVEL_BY_UPGRADE_ = {5, 10, 15, 20};
	public static final int CHARACTER_CARD_MAX_LEVEL = 20;
	
	public static final int[] PARTNER_LIMIT_LEVEL_BY_UPGRADE_ = {5, 10, 15, 20};
	public static final int PARTNER_MAX_UPGRADE = 4;
	
	
	// User grade
	public static final byte USER_GRADE_NORMAL = 0;
	public static final byte USER_GRADE_ADMIN = 1;
	public static final byte USER_GRADE_BLOCK = 2;
	public static final byte USER_GRADE_AI = 3;
	public static final int USER_ACCOUNT_MAX_LEVEL = 100;
	
	// User Type
	public static final byte DEFAULT_USER = 0;
	public static final byte NEW_USER = 1;
	public static final byte COMBACK_USER = 2;
	
	public static final int NORMAL_USER_ID = 100000;
	
	
	// Time
	public static final int CALIBRATION_TIME = 5;										// �간 보정�: 5�
	public static final int MINUTE_OF_SECOND = 60;										// 1��
	public static final byte HOUR_OF_DAY = 24;											// �루 (�간)
	public static final int HOUR_OF_SECOND = 3600;										// 1�간 (�
	public static final int DAY_OF_SECOND = 86400;										// �루(�
	public static final int DAY_OF_MINUTE = 1440;										// �루(�
	public static final int WEEK_OF_SECOND = DAY_OF_SECOND * 7;							// �주�
	public static final int MINUTE_OF_HOUR = 60;										// 1�간 (�
	public static final int DAY_OF_MONTH = 30;											// �달 (�자)
	public static final long MONTH_OF_MINUTE = (MINUTE_OF_HOUR * HOUR_OF_DAY * 31); 	// �달
	public static final long MAIL_EXPIRE_TIME = WEEK_OF_SECOND * 4;						// 2�� 
	public static final String MAIL_CASH_EXPIRE_TIME = "2099-12-31 00:00:00";			// 캐쉬메일 �나�짜(�간)
	
	
	// Day
	public static final int ALWAYS = 0;
	public static final int SUNDAY = 1;
	public static final int MONDAY = 2;
	public static final int TUESDAY = 3;
	public static final int WEDNESDAY = 4;
	public static final int THURSDAY = 5;
	public static final int FRIDAY = 6;
	public static final int SATURDAY = 7;
	public static final int DAY_WEEK_COUNT = 7;
	
	
	// Default
	public static final byte DEFAULT_ZERO = 0;
	public static final byte DEFAULT_VALUE = -1;
	public static final byte DEFAULT_ONE = 1;
	public static final byte DEFAULT_SUCCESS = 100;
	public static final byte DEFAULT_FAIL = 101;
	
	
	//Version Check Type
	public static final byte VERSION_CHECK_OK = 0;
	public static final byte VERSION_CHECK_CDN = 1;
	public static final byte VERSION_CHECK_NO_SERVER_INFO = 2;
	public static final byte VERSION_CHECK_APPVERSION = 3;
	public static final byte VERSION_CHECK_NO_SESSION = 4;
	public static final byte VERSION_CHECK_SERVER_INSPECTION = 5;
	
	// Admission result Type
	public static final int ADMISSION_TYPE_SUCCESS = 100;					// �공
	public static final int ADMISSION_TYPE_APP_VERSION_UP = 101;			// App Version Upgrade
	public static final int ADMISSION_TYPE_VERSION_NOT_EXISTENCE = 102;		// App Version Not Found ( 검증서버로 �전 )
	public static final int ADMISSION_TYPE_BLOCK = 103;						// 차단�� or �버 �속 차단
	public static final int ADMISSION_TYPE_NOT_FOUND_USER = 104;			// 계정�보 ��( �라�언�에�로계정 진행 �� �인 )
	
	// Database Type
	public static final byte GAME_DB = 0;
	public static final byte STATIC_DB = 1;
	public static final byte LOG_DB = 2;
	public static final byte RANK_DB = 3;
	public static final byte DB_TOTAL_COUNT = 4;
	
	// REDIS TYPE
	public static final byte NONE_SEARCHE_KEY = 0;
	
	
	// date division
	public static final byte DATE_SECTION_YEAR = 1;
	public static final byte DATE_SECTION_MONTH = 2;
	public static final byte DATE_SECTION_DAY = 3;
	public static final byte DATE_SECTION_HOUR = 4;
	public static final byte DATE_SECTION_HOUR_FULL = 5;
	public static final byte DATE_SECTION_MINUTE = 6;
	
	
	// PvP Server Useable
	public static final byte SERVER_USEABLE_FALSE = 0;
	public static final byte SERVER_USEABLE_TRUE = 1;
	
	// PvP Server Select
	public static final int PVPSERVER_USER_COUNT = 400;
	public static final int PVPSERVER_CPU_SHARE = 80;
	
	public static final byte IS_FALSE = 0;
	public static final byte IS_TRUE = 1;
	
	// Redis Value
	public static final byte REDIS_NOTICE_EXPIRE_TIME = 1;					// 공� Redis 만료�간 (minute)
	public static final byte REDIS_PATCH_CDN_URL_EXPIRE_TIME = 1;			// Patch CDN Url Redis 만료�간 (minute)
	public static final byte REDIS_SERVER_INFO_EXPIRE_TIME = 1;				// �버버전 Redis 만료�간 (minute)
	public static final byte REDIS_CLIENT_VERSION_EXPIRE_TIME = 1;			// �라버전 Redis 만료�간 (minute)
	public static final byte REDIS_VERSION_INFO_EXPIRE_TIME = 1;			// VersionInfo Redis 만료�간 (minute)
	public static final byte REDIS_VALIDATE_PVP_SERVER_EXPIRE_TIME = 2;		// PVP �버 �인
	public static final int REDIS_DEFAULT_EXPIRE_MINUTES = 60 * 4;			// 레디스 기본 만료시간
	
	//Match
	public static final int GAME_MODE_COMPETITION = 0;						// 경쟁전
	public static final int GAME_MODE_MINI_GAME_1 = 10;						// 미니게임 1
	public static final int GAME_MODE_MINI_GAME_2 = 11;						// 미니게임 2
	public static final int GAME_MODE_MINI_GAME_3 = 12;						// 미니게임 3
	
	
	// Season Reward
	public static final int SEASON_REWARD_SUCCESS = 100;
	public static final int SEASON_REWARD_NO_REWARD = 101;
	public static final int DEFAULT_SEASON = 0;
	
	//CardGrade
	public static final int CARD_GRADE_SSR = 3;
	public static final int CARD_GRADE_SR = 2;
	public static final int CARD_GRADE_R = 1;
	public static final int CARD_GRADE_N = 0;
	
	public static final int PARTNER_C_GRADE_SKILL_MAX_COUNT = 1;
	public static final int PARTNER_B_GRADE_SKILL_MAX_COUNT = 2;
	public static final int PARTNER_A_GRADE_SKILL_MAX_COUNT = 3;
	
	public static final int PARTNER_SKILL_SLOT_1 = 0;
	public static final int PARTNER_SKILL_SLOT_2 = 1;
	public static final int PARTNER_SKILL_SLOT_3 = 2;
	public static final int CHARACTER_DEFAULT_MINING = 0;
	public static final int PARTNER_CARD_MAX_LEVEL = 20;
	public static final int CHARACTER_MAX_ENHANCE = 3;
	public static final int MAX_RATE = 100000;
	public static final int ASSISTANCE_ADVANCE_RATE = 10000;
	
	public static final int[] PARTNER_ITEM_COMPOSE_LIST = {1000003, 1000004};
	
	//Rank Tier Grade
	public static final byte RANK_TIER_BRONZE_3 = 0;
	public static final byte RANK_TIER_BRONZE_2 = 1;
	public static final byte RANK_TIER_BRONZE_1 = 2;
	public static final byte RANK_TIER_SILVER_3 = 3;
	public static final byte RANK_TIER_SILVER_2 = 4;
	public static final byte RANK_TIER_SILVER_1 = 5;
	public static final byte RANK_TIER_GOLD_3 = 6;
	public static final byte RANK_TIER_GOLD_2 = 7;
	public static final byte RANK_TIER_GOLD_1 = 8;
	public static final byte RANK_TIER_DIAMOND_3 = 9;
	public static final byte RANK_TIER_DIAMOND_2 = 10;
	public static final byte RANK_TIER_DIAMOND_1 = 11;
	
	public static final byte RANKING_REWARD_TYPE_TIER = 0;
	public static final byte RANKING_REWARD_TYPE_RANKING = 1;
	
	public static final int RANK_MAX_USER_COUNT = 1000;
	public static final int RANK_MAX_RANKER = 150; 				// 리스�태롄달�는 �용�
	public static final int RANK_USER_MAX_RANKER = 100;
	
	//Mail
	public static final byte MAIL_STATE_NEW = 1;				// �규
	public static final byte MAIL_STATE_CONFIRM = 2;			// �인
	public static final byte MAIL_STATE_REWARD = 3;				// 보상
	public static final byte MAIL_ITEM_CNT = 5;
	public static final byte MAIL_TYPE_NORMAL = 0;				// �반 �편
	public static final byte MAIL_TYPE_CASH = 1;				// 캐시 �편
	public static final byte MAIL_EXPIRED_REMOVE = 1;			// mail remove
	
	
	//NFT
	public static final String NFT_TYPE_CHARACTER = "character";	// 캐릭
	public static final String NFT_TYPE_PARTNER = "friends";		// �료
	public static final String NFT_TYPE_ITEM = "item";				// �이
	public static final int NFT_BALANCE_MIN_COUNT = 1000;		// nft�이�을 가�오긄한 최� 개수
	public static final int NFT_BALANCE_MAX_COUNT = 2000;		// nft�이�을 가�오긄한 최� 개수
	
	//Mission
	public static final int MISSION_DEFAULT_COUNT = 5;			//임무 기본 개수 (VIP에 따라 증가할 수 있음)
	public static final int MISSION_MAX_COUNT = 10;				//임무 최대 개수
	public static final byte MISSION_STATUS_DEFAULT = 0;		// 임무 기본상태
	public static final byte MISSION_STATUS_START = 1;			// 임무 시작상태
	public static final byte MISSION_STATUS_COMPLETE = 2;		// 임무가 완료되었으나 보상을 받지 않은 상태
	public static final byte MISSION_STATUS_REWARD = 3;			// 보상 완료
	
	//Shop
	public static final byte SHOP_CATEGORY_PACKAGE = 0;
	public static final byte SHOP_CATEGORY_GOLD = 1;
	public static final byte SHOP_CATEGORY_FRIENDS_PACK = 2;
	
	public static final int SHOP_SELL_TYPE_UNLIMITED = 0;
	public static final int SHOP_SELL_TYPE_ACCOUNT = 1;
	public static final int SHOP_SELL_TYPE_DAILY = 2;
	public static final int SHOP_SELL_TYPE_WEEKLY = 3;
	public static final int SHOP_SELL_TYPE_MONTHLY = 4;
	
	// VIP
	public static final int VIP_LEVEL_1 = 1;
	public static final int VIP_LEVEL_2 = 2;
	public static final int VIP_LEVEL_3 = 3;
	public static final int VIP_LEVEL_4 = 4;
	public static final int VIP_LEVEL_5 = 5;
	public static final int VIP_LEVEL_6 = 6;
	public static final int VIP_LEVEL_7 = 7;
	
	//event
	public static final int ATTENDANCE_EVENT_ID = 1;

	// Mail Text
	public static final int MAIL_TEXT_DISCOMFORT_REWARD = 1;
	public static final int MAIL_TEXT_CHECK_REWARD = 2;
	public static final int MAIL_TEXT_ATTENDANCE_REWARD = 3;
	public static final int MAIL_TEXT_SHOP_BUY_GOLD = 4;
	public static final int MAIL_TEXT_SHOP_BUY_PACKAGE = 5;
	public static final int MAIL_TEXT_SHOP_BUY_FRIENDSPACK = 6;
	public static final int MAIL_TEXT_SHOP_BUY_ITEM = 7;
	public static final int MAIL_TEXT_RANKING_TIER_REWARD  = 8;
	public static final int MAIL_TEXT_RANKING_REWARD = 9;
	public static final int MAIL_TEXT_EVENT_REWARD = 10;
	public static final int MAIL_TEXT_BUY_STARPACK = 11;
	public static final int MAIL_TEXT_IMPORT_NFT = 12;
	public static final int MAIL_TEXT_COUPON_REWARD = 13;
	public static final int MAIL_TEXT_STARBOX_REWARD = 14;
}
