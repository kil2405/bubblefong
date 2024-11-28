package com.bubbleShooter.domain;

public class ChangeNicknameLog {
	private byte ptn_month;
	private int log_date;
	private long user_id;
	private String prev_nickname;
	private String new_nickname;
	private int limit_date;

	public byte getPtn_month() {
		return ptn_month;
	}

	public void setPtn_month(byte ptn_month) {
		this.ptn_month = ptn_month;
	}

	public int getLog_date() {
		return log_date;
	}

	public void setLog_date(int log_date) {
		this.log_date = log_date;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getPrev_nickname() {
		return prev_nickname;
	}

	public void setPrev_nickname(String prev_nickname) {
		this.prev_nickname = prev_nickname;
	}

	public String getNew_nickname() {
		return new_nickname;
	}

	public void setNew_nickname(String new_nickname) {
		this.new_nickname = new_nickname;
	}

	public int getLimit_date() {
		return limit_date;
	}

	public void setLimit_date(int limit_date) {
		this.limit_date = limit_date;
	}

}
