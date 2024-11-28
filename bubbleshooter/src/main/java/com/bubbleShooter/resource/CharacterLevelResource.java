package com.bubbleShooter.resource;

public class CharacterLevelResource {
	private int index;
	private byte isNft;
	private int level;
	private byte grade;
	private int itemId0;
	private int itemType0;
	private int itemCount0;
	private int itemId1;
	private int itemType1;
	private int itemCount1;

	public CharacterLevelResource(int index, byte isNft, int level, byte grade, int itemId0, int itemType0,
			int itemCount0, int itemId1, int itemType1, int itemCount1) {
		super();
		this.index = index;
		this.isNft = isNft;
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

	public byte getIsNft() {
		return isNft;
	}

	public int getLevel() {
		return level;
	}

	public byte getGrade() {
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
