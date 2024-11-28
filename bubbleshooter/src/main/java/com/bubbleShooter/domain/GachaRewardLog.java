package com.bubbleShooter.domain;

public class GachaRewardLog {
	private long seq;
	private byte ptn_month;
	private byte ptn_day;
	private int log_time;
	private int user_id;
	private int gacha_index;
	private int gacha_id;
	private int reward_grade;
	private String gacha_reward_item;

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public byte getPtn_month() {
		return ptn_month;
	}

	public void setPtn_month(byte ptn_month) {
		this.ptn_month = ptn_month;
	}

	public byte getPtn_day() {
		return ptn_day;
	}

	public void setPtn_day(byte ptn_day) {
		this.ptn_day = ptn_day;
	}

	public int getLog_time() {
		return log_time;
	}

	public void setLog_time(int log_time) {
		this.log_time = log_time;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getGacha_index() {
		return gacha_index;
	}

	public void setGacha_index(int gacha_index) {
		this.gacha_index = gacha_index;
	}

	public int getGacha_id() {
		return gacha_id;
	}

	public void setGacha_id(int gacha_id) {
		this.gacha_id = gacha_id;
	}

	public int getReward_grade() {
		return reward_grade;
	}

	public void setReward_grade(int reward_grade) {
		this.reward_grade = reward_grade;
	}

	public String getGacha_reward_item() {
		return gacha_reward_item;
	}

	public void setGacha_reward_item(String gacha_reward_item) {
		this.gacha_reward_item = gacha_reward_item;
	}

}
