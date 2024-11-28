package com.bubbleShooter.resource;

public class PartnerLevelResource {
	private int index;
	private int level;
	private int grade;
	private int itemId0;
	private int itemType0;
	private int itemCount0;
	private int itemId1;
	private int itemType1;
	private int itemCount1;

	public PartnerLevelResource(int index, int level, int grade, int itemId0, int itemType0, int itemCount0,
			int itemId1, int itemType1, int itemCount1) {
		super();
		this.index = index;
		this.level = level;
		this.grade = grade;
		this.itemId0 = itemId0;
		this.itemType0 = itemType0;
		this.itemCount0 = itemCount0;
		this.itemId1 = itemId1;
		this.itemType1 = itemType1;
		this.itemCount1 = itemCount1;
	}

	public int getIndex() {
		return index;
	}

	public int getLevel() {
		return level;
	}

	public int getGrade() {
		return grade;
	}

	public int getItemId0() {
		return itemId0;
	}

	public int getItemType0() {
		return itemType0;
	}

	public int getItemCount0() {
		return itemCount0;
	}

	public int getItemId1() {
		return itemId1;
	}

	public int getItemType1() {
		return itemType1;
	}

	public int getItemCount1() {
		return itemCount1;
	}

}
