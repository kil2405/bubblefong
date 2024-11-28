package com.bubbleShooter.resource;

public class ItemResource {
	private int itemType;
	private int itemId;
	private String itemName;
	private String itemIcon;
	private byte isNft;
	private String desc;

	public ItemResource(int itemType, int itemId, String itemName, String itemIcon, byte isNft, String desc) {
		super();
		this.itemType = itemType;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemIcon = itemIcon;
		this.isNft = isNft;
		this.desc = desc;
	}

	public int getItemType() {
		return itemType;
	}

	public int getItemId() {
		return itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public String getItemIcon() {
		return itemIcon;
	}

	public byte getIsNft() {
		return isNft;
	}

	public String getDesc() {
		return desc;
	}

}
