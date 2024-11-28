package com.bubbleShooter.domain;

public class ShopPurchaseLog {
	private long seq;
	private byte ptn_month;
	private long log_date;
	private int user_id;
	private int product_id;
	private int price;

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

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
