package com.bubbleShooter.domain;

public class GachaReward {
	private int seq;
	private int gachaId;
	private byte grade;
	private int itemType;
	private int itemId;
	private int itemCount;
	private byte heroGrade;
	private int totalCount;
	private int prizeCount;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getGachaId() {
		return gachaId;
	}

	public void setGachaId(int gachaId) {
		this.gachaId = gachaId;
	}

	public byte getGrade() {
		return grade;
	}

	public void setGrade(byte grade) {
		this.grade = grade;
	}

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public byte getHeroGrade() {
		return heroGrade;
	}

	public void setHeroGrade(byte heroGrade) {
		this.heroGrade = heroGrade;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPrizeCount() {
		return prizeCount;
	}

	public void setPrizeCount(int prizeCount) {
		this.prizeCount = prizeCount;
	}

}
