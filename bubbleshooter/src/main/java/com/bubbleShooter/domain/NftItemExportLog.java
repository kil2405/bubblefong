package com.bubbleShooter.domain;

public class NftItemExportLog {
	private byte ptn_month;
	private int ptn_day;
	private long log_date;
	private int user_id;
	private String wallet;
	private String url;
	private String uid;
	private String meta_data;
	private String resCode;

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

	public String getWallet() {
		return wallet;
	}

	public void setWallet(String wallet) {
		this.wallet = wallet;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMeta_data() {
		return meta_data;
	}

	public void setMeta_data(String meta_data) {
		this.meta_data = meta_data;
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

}
