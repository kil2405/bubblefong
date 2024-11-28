package com.bubbleShooter.domain;

public class UserShopBuyLog {
	private int userId;
	private int productId;
	private int buyCount;
	private long limitDate;
	private int lastBuyTime;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}

	public long getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(long limitDate) {
		this.limitDate = limitDate;
	}

	public int getLastBuyTime() {
		return lastBuyTime;
	}

	public void setLastBuyTime(int lastBuyTime) {
		this.lastBuyTime = lastBuyTime;
	}

}
