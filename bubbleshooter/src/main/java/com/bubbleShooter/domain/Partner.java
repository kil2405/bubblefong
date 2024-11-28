package com.bubbleShooter.domain;

public class Partner {
	private int userId;
	private String uid;
	private int partnerId;
	private int grade;
	private int partnerLevel;
	private int upgrade;
	private int skill1;
	private int skill2;
	private int skill3;
	private byte isLock;
	private byte isNft;
	private byte isNew;
	private byte isMission;
	private byte useOrNot;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getPartnerLevel() {
		return partnerLevel;
	}

	public void setPartnerLevel(int partnerLevel) {
		this.partnerLevel = partnerLevel;
	}

	public int getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(int upgrade) {
		this.upgrade = upgrade;
	}

	public int getSkill1() {
		return skill1;
	}

	public void setSkill1(int skill1) {
		this.skill1 = skill1;
	}

	public int getSkill2() {
		return skill2;
	}

	public void setSkill2(int skill2) {
		this.skill2 = skill2;
	}

	public int getSkill3() {
		return skill3;
	}

	public void setSkill3(int skill3) {
		this.skill3 = skill3;
	}

	public byte getIsLock() {
		return isLock;
	}

	public void setIsLock(byte isLock) {
		this.isLock = isLock;
	}

	public byte getIsNft() {
		return isNft;
	}

	public void setIsNft(byte isNft) {
		this.isNft = isNft;
	}

	public byte getIsNew() {
		return isNew;
	}

	public void setIsNew(byte isNew) {
		this.isNew = isNew;
	}

	public byte getIsMission() {
		return isMission;
	}

	public void setIsMission(byte isMission) {
		this.isMission = isMission;
	}

	public byte getUseOrNot() {
		return useOrNot;
	}

	public void setUseOrNot(byte useOrNot) {
		this.useOrNot = useOrNot;
	}

}
