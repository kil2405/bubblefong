package com.bubbleShooter.resource;

public class SkillPartnerGroupResource {
	private int index;
	private int id;
	private int idTextName;
	private int idTextInfo;
	private int idCharacter;
	private int idPartner1;
	private int idPartner2;
	private int idPartner3;
	private int targetCount;
	private int value1;
	private String desc;

	public SkillPartnerGroupResource(int index, int id, int idTextName, int idTextInfo, int idCharacter, int idPartner1,
			int idPartner2, int idPartner3, int targetCount, int value1, String desc) {
		super();
		this.index = index;
		this.id = id;
		this.idTextName = idTextName;
		this.idTextInfo = idTextInfo;
		this.idCharacter = idCharacter;
		this.idPartner1 = idPartner1;
		this.idPartner2 = idPartner2;
		this.idPartner3 = idPartner3;
		this.targetCount = targetCount;
		this.value1 = value1;
		this.desc = desc;
	}

	public int getIndex() {
		return index;
	}

	public int getId() {
		return id;
	}

	public int getIdTextName() {
		return idTextName;
	}

	public int getIdTextInfo() {
		return idTextInfo;
	}

	public int getIdCharacter() {
		return idCharacter;
	}

	public int getIdPartner1() {
		return idPartner1;
	}

	public int getIdPartner2() {
		return idPartner2;
	}

	public int getIdPartner3() {
		return idPartner3;
	}

	public int getTargetCount() {
		return targetCount;
	}

	public int getValue1() {
		return value1;
	}

	public String getDesc() {
		return desc;
	}

}
