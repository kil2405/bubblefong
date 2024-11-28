package com.bubbleShooter.resource;

public class SkillCharacterActiveResource {
	private int index;
	private int id;
	private String iconName;
	private int level;
	private int idTextSkill;
	private int idTextInfo;
	private int idTextUpgradeValue;
	private int maxGauge;
	private int value1;
	private int value2;
	private int value3;
	private int value4;
	private String skillName;
	private String desc1;
	private String desc2;
	private String desc3;

	public SkillCharacterActiveResource(int index, int id, String iconName, int level, int idTextSkill, int idTextInfo,
			int idTextUpgradeValue, int maxGauge, int value1, int value2, int value3, int value4, String skillName,
			String desc1, String desc2, String desc3) {
		super();
		this.index = index;
		this.id = id;
		this.iconName = iconName;
		this.level = level;
		this.idTextSkill = idTextSkill;
		this.idTextInfo = idTextInfo;
		this.idTextUpgradeValue = idTextUpgradeValue;
		this.maxGauge = maxGauge;
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
		this.value4 = value4;
		this.skillName = skillName;
		this.desc1 = desc1;
		this.desc2 = desc2;
		this.desc3 = desc3;
	}

	public int getIndex() {
		return index;
	}

	public int getId() {
		return id;
	}

	public String getIconName() {
		return iconName;
	}

	public int getLevel() {
		return level;
	}

	public int getIdTextSkill() {
		return idTextSkill;
	}

	public int getIdTextInfo() {
		return idTextInfo;
	}

	public int getIdTextUpgradeValue() {
		return idTextUpgradeValue;
	}

	public int getMaxGauge() {
		return maxGauge;
	}

	public int getValue1() {
		return value1;
	}

	public int getValue2() {
		return value2;
	}

	public int getValue3() {
		return value3;
	}

	public int getValue4() {
		return value4;
	}

	public String getSkillName() {
		return skillName;
	}

	public String getDesc1() {
		return desc1;
	}

	public String getDesc2() {
		return desc2;
	}

	public String getDesc3() {
		return desc3;
	}

}
