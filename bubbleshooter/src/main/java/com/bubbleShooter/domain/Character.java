package com.bubbleShooter.domain;

public class Character {
	private int userId;
	private String uid;
	private int characterId;
	private int grade;
	private int characterLevel;
	private int characterUpgrade;
	private int activeSkill;
	private byte isLock;
	private int dailyearn;
	private int dailyearnlimit;
	private byte isReward;
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

	public int getCharacterId() {
		return characterId;
	}

	public void setCharacterId(int characterId) {
		this.characterId = characterId;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getCharacterLevel() {
		return characterLevel;
	}

	public void setCharacterLevel(int characterLevel) {
		this.characterLevel = characterLevel;
	}

	public int getCharacterUpgrade() {
		return characterUpgrade;
	}

	public void setCharacterUpgrade(int characterUpgrade) {
		this.characterUpgrade = characterUpgrade;
	}

	public int getActiveSkill() {
		return activeSkill;
	}

	public void setActiveSkill(int activeSkill) {
		this.activeSkill = activeSkill;
	}

	public byte getIsLock() {
		return isLock;
	}

	public void setIsLock(byte isLock) {
		this.isLock = isLock;
	}

	public int getDailyearn() {
		return dailyearn;
	}

	public void setDailyearn(int dailyearn) {
		this.dailyearn = dailyearn;
	}

	public int getDailyearnlimit() {
		return dailyearnlimit;
	}

	public void setDailyearnlimit(int dailyearnlimit) {
		this.dailyearnlimit = dailyearnlimit;
	}

	public byte getIsReward() {
		return isReward;
	}

	public void setIsReward(byte isReward) {
		this.isReward = isReward;
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
