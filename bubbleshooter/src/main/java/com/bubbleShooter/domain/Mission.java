package com.bubbleShooter.domain;

public class Mission {
	private int userId;
	private int index;
	private int missionId;
	private byte status;
	private byte heroType;
	private String heroUUid;
	private long missionStartTime;
	private long missionRewardTime;
	private int missionUpdateTime;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getMissionId() {
		return missionId;
	}

	public void setMissionId(int missionId) {
		this.missionId = missionId;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public byte getHeroType() {
		return heroType;
	}

	public void setHeroType(byte heroType) {
		this.heroType = heroType;
	}

	public String getHeroUUid() {
		return heroUUid;
	}

	public void setHeroUUid(String heroUUid) {
		this.heroUUid = heroUUid;
	}

	public long getMissionStartTime() {
		return missionStartTime;
	}

	public void setMissionStartTime(long missionStartTime) {
		this.missionStartTime = missionStartTime;
	}

	public long getMissionRewardTime() {
		return missionRewardTime;
	}

	public void setMissionRewardTime(long missionRewardTime) {
		this.missionRewardTime = missionRewardTime;
	}

	public int getMissionUpdateTime() {
		return missionUpdateTime;
	}

	public void setMissionUpdateTime(int missionUpdateTime) {
		this.missionUpdateTime = missionUpdateTime;
	}

}
