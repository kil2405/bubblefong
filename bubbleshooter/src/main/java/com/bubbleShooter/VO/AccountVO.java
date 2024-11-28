package com.bubbleShooter.VO;

public class AccountVO extends UserBaseVO
{
	public int level;
	public byte vip;
	public String wallet;
	public int exp;
	public int maxExp;
	public int preLevelMaxExp;
	public long remainTime;
	public int tier;
	public int score;
	public String region; // KR이면 지갑연동 지워..
}
