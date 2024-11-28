package com.bubbleShooter.resource;

public class GachaRewardResource {
	private int index;
	private int grade;
	private int itemType;
	private int itemId;
	private int itemCount;
	private byte heroGrade;

	public GachaRewardResource(int index, int grade, int itemType, int itemId, int itemCount, byte heroGrade) {
		super();
		this.index = index;
		this.grade = grade;
		this.itemType = itemType;
		this.itemId = itemId;
		this.itemCount = itemCount;
		this.heroGrade = heroGrade;
	}

	public int getIndex() {
		return index;
	}

	public int getGrade() {
		return grade;
	}

	public int getItemType() {
		return itemType;
	}

	public int getItemId() {
		return itemId;
	}

	public int getItemCount() {
		return itemCount;
	}

	public byte getHeroGrade() {
		return heroGrade;
	}

}
