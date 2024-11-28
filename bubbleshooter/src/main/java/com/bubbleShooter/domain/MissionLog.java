package com.bubbleShooter.domain;

public class MissionLog {
	private long seq;
	private byte ptn_month;
	private byte ptn_day;
	private long log_time;
	private int user_id;
	private int index;
	private int mission_id;
	private byte status;
	private byte hero_type;
	private String hero_uuid;
	private long mission_start_time;
	private long mission_reward_time;
	private int mission_update_time;
	private String reward_item_0;
	private String reward_item_1;
	private String reward_item_2;
	private String reward_item_3;
	private String reward_item_4;

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

	public long getLog_time() {
		return log_time;
	}

	public void setLog_time(long log_time) {
		this.log_time = log_time;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getMission_id() {
		return mission_id;
	}

	public void setMission_id(int mission_id) {
		this.mission_id = mission_id;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public byte getHero_type() {
		return hero_type;
	}

	public void setHero_type(byte hero_type) {
		this.hero_type = hero_type;
	}

	public String getHero_uuid() {
		return hero_uuid;
	}

	public void setHero_uuid(String hero_uuid) {
		this.hero_uuid = hero_uuid;
	}

	public long getMission_start_time() {
		return mission_start_time;
	}

	public void setMission_start_time(long mission_start_time) {
		this.mission_start_time = mission_start_time;
	}

	public long getMission_reward_time() {
		return mission_reward_time;
	}

	public void setMission_reward_time(long mission_reward_time) {
		this.mission_reward_time = mission_reward_time;
	}

	public int getMission_update_time() {
		return mission_update_time;
	}

	public void setMission_update_time(int mission_update_time) {
		this.mission_update_time = mission_update_time;
	}

	public String getReward_item_0() {
		return reward_item_0;
	}

	public void setReward_item_0(String reward_item_0) {
		this.reward_item_0 = reward_item_0;
	}

	public String getReward_item_1() {
		return reward_item_1;
	}

	public void setReward_item_1(String reward_item_1) {
		this.reward_item_1 = reward_item_1;
	}

	public String getReward_item_2() {
		return reward_item_2;
	}

	public void setReward_item_2(String reward_item_2) {
		this.reward_item_2 = reward_item_2;
	}

	public String getReward_item_3() {
		return reward_item_3;
	}

	public void setReward_item_3(String reward_item_3) {
		this.reward_item_3 = reward_item_3;
	}

	public String getReward_item_4() {
		return reward_item_4;
	}

	public void setReward_item_4(String reward_item_4) {
		this.reward_item_4 = reward_item_4;
	}

}
