package com.bubbleShooter.domain;

public class AttendanceEventRewardLog {
	private byte ptn_month;
	private byte ptn_day;
	private long log_time;
	private int user_id;
	private int event_no;
	private int event_day;
	private int reward_day;

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

	public int getEvent_no() {
		return event_no;
	}

	public void setEvent_no(int event_no) {
		this.event_no = event_no;
	}

	public int getEvent_day() {
		return event_day;
	}

	public void setEvent_day(int event_day) {
		this.event_day = event_day;
	}

	public int getReward_day() {
		return reward_day;
	}

	public void setReward_day(int reward_day) {
		this.reward_day = reward_day;
	}

}
