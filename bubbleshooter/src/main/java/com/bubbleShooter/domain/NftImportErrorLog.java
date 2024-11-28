package com.bubbleShooter.domain;

public class NftImportErrorLog {
	private byte ptn_month;
	private int ptn_day;
	private long log_date;
	private int user_id;
	private String created_game_yn;
	private String hash;
	private String secret;
	private String unique;
	private String json;

	public byte getPtn_month() {
		return ptn_month;
	}

	public void setPtn_month(byte ptn_month) {
		this.ptn_month = ptn_month;
	}

	public int getPtn_day() {
		return ptn_day;
	}

	public void setPtn_day(int ptn_day) {
		this.ptn_day = ptn_day;
	}

	public long getLog_date() {
		return log_date;
	}

	public void setLog_date(long log_date) {
		this.log_date = log_date;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getCreated_game_yn() {
		return created_game_yn;
	}

	public void setCreated_game_yn(String created_game_yn) {
		this.created_game_yn = created_game_yn;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getUnique() {
		return unique;
	}

	public void setUnique(String unique) {
		this.unique = unique;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

}
