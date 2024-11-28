package com.bubbleShooter.domain;

public class RankingRewardLog {
	private long seq;
	private byte ptn_month;
	private byte ptn_day;
	private int log_date;
	private int user_id;
	private int reward_season;
	private int reward_tier;
	private int reward_score;
	private int reward_ranking;

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

	public int getLog_date() {
		return log_date;
	}

	public void setLog_date(int log_date) {
		this.log_date = log_date;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getReward_season() {
		return reward_season;
	}

	public void setReward_season(int reward_season) {
		this.reward_season = reward_season;
	}

	public int getReward_tier() {
		return reward_tier;
	}

	public void setReward_tier(int reward_tier) {
		this.reward_tier = reward_tier;
	}

	public int getReward_score() {
		return reward_score;
	}

	public void setReward_score(int reward_score) {
		this.reward_score = reward_score;
	}

	public int getReward_ranking() {
		return reward_ranking;
	}

	public void setReward_ranking(int reward_ranking) {
		this.reward_ranking = reward_ranking;
	}

}
