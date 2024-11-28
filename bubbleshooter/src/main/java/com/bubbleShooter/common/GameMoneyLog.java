package com.bubbleShooter.common;

public class GameMoneyLog {
	private long seq;
	private byte ptn_month;
	private byte ptn_day;
	private int log_date;
	private String log_type;
	private int user_id;
	private int money_type;
	private int item_id;
	private int add_value;
	private long total_value;
	private String description;

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

	public String getLog_type() {
		return log_type;
	}

	public void setLog_type(String log_type) {
		this.log_type = log_type;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getMoney_type() {
		return money_type;
	}

	public void setMoney_type(int money_type) {
		this.money_type = money_type;
	}

	public int getItem_id() {
		return item_id;
	}

	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	public int getAdd_value() {
		return add_value;
	}

	public void setAdd_value(int add_value) {
		this.add_value = add_value;
	}

	public long getTotal_value() {
		return total_value;
	}

	public void setTotal_value(long total_value) {
		this.total_value = total_value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
