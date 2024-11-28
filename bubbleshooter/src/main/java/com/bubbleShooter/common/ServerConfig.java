package com.bubbleShooter.common;

public class ServerConfig {
	public static boolean USE_REDIS_SERVER = true;
	public static int SERVER_ID = 1;
	public static String SERVER_IP = "";
	public static String SERVER_NAME = "Unknown";
	public static String VENDOR = "en";
	public static String SERVER_LANGUAGE = "";
	public static String SERVER_REGION = "";
	public static String SERVER_MODE = "";
	
	// Admin DB
	public static boolean AI_ENABLED = false;
	public static boolean USE_ENCRYPTION = false;
	public static boolean SERVER_INSPECTION = false;
	public static boolean IOS_VERIFY_SERVER = false;
	public static boolean PACKET_SAVE_REDIS = true;
	
	//ResourceDB
	public static int INIT_ACCOUNT_LEVEL = 0;
	public static int INIT_ACCOUNT_EXP = 0;
	public static int INIT_GOLD = 0;
	public static int INIT_STAR = 0;
	public static int INIT_RANK_POINT = 0;
	public static int[] INIT_CHARACTER = {0, 1, 2, 3};
	public static int[] INIT_PARTNER = {0, 1, 2, 3};
	public static byte[] GRADE_START_LEVEL = {0, 1, 2, 3};
	public static int PARTNER_SKILL_CHANGE = 0;
	public static int[] PARTNER_SKILL_CHANGE_RATE = {0, 1, 2};
	public static int[] RANKING_REWARD_RP_101 = {0, 1, 2, 3, 4, 5, 6, 7};
	public static int RANKING_WINS_STREAK_BONUS_RP_101 = 0;
	public static int[] RANKING_REWARD_RP_4VS4 = {0, 1};
	public static int[] RANKING_LOSE_TEAM_BONUS_RP_4VS4 = {0, 1};
	public static int RANKING_MVP_BONUS_RP_4VS4 = 0;
	public static int RANKING_MVP_STREAK_3_BONUS_RP_4VS4 = 0;
	public static int RANKING_MVP_STREAK_5_BONUS_RP_4VS4 = 0;
	public static int RANKING_MVP_STREAK_10_BONUS_RP_4VS4 = 0;
	public static int[] RANKING_WIN_TEAM_ALL_SURVIVAL_BONUS_RP_4VS4 = {0, 1};
	public static int[] BATTLE_PASS_MAX_XP = {0, 1};
	public static int[] BATTLE_PASS_REWARD_XP_101 = {0, 1, 2, 3, 4, 5, 6, 7};
	public static int[] BATTLE_PASS_REWARD_XP_4VS4 = {0, 1};
	public static int BATTLE_PASS_MVP_BONUS_XP_4VS4 = 0;
	public static int MAIL_VALIDITY = 0;
	public static int DORMANT_ACCOUNT_MINUS_RP = 0;
	public static int[] MINING_BP = {0, 1, 3};
	
	public static int USE_STARKEY = 0;
	public static String STARBOX_NOTICE_TEXT = "";
	public static int STARBOX_RANDOM_ID = 0;
	public static int STARBOX_MINIMUM_NUMBER = 0;
	public static int STARBOX_DAILY_RECHARGE_NUMBER = 0;
	public static int SCRAMBLE_ENTRANCE_FEE = 0;
	public static int SCRAMBLE_COMMISSION = 0;
}
