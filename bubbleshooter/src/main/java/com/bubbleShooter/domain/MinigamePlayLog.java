package com.bubbleShooter.domain;

public class MinigamePlayLog {
	private long seq;
	private byte ptn_month;
	private byte ptn_day;
	private int log_date;
	private String room_uid;
	private int user_id;
	private int game_id;
	private int rank;
	private int ruby;
	private byte is_ticket;

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

	public String getRoom_uid() {
		return room_uid;
	}

	public void setRoom_uid(String room_uid) {
		this.room_uid = room_uid;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getGame_id() {
		return game_id;
	}

	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getRuby() {
		return ruby;
	}

	public void setRuby(int ruby) {
		this.ruby = ruby;
	}

	public byte getIs_ticket() {
		return is_ticket;
	}

	public void setIs_ticket(byte is_ticket) {
		this.is_ticket = is_ticket;
	}

}
