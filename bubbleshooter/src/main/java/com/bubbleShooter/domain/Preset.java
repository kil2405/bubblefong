package com.bubbleShooter.domain;

public class Preset {
	private int userId;
	private String characterUid;
	private String partner1Uid;
	private String partner2Uid;
	private String partner3Uid;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCharacterUid() {
		return characterUid;
	}

	public void setCharacterUid(String characterUid) {
		this.characterUid = characterUid;
	}

	public String getPartner1Uid() {
		return partner1Uid;
	}

	public void setPartner1Uid(String partner1Uid) {
		this.partner1Uid = partner1Uid;
	}

	public String getPartner2Uid() {
		return partner2Uid;
	}

	public void setPartner2Uid(String partner2Uid) {
		this.partner2Uid = partner2Uid;
	}

	public String getPartner3Uid() {
		return partner3Uid;
	}

	public void setPartner3Uid(String partner3Uid) {
		this.partner3Uid = partner3Uid;
	}

}
