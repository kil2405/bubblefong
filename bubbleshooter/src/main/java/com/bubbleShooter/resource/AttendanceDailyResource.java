package com.bubbleShooter.resource;

public class AttendanceDailyResource {
	private int days;
	private int nextIndex;
	private int itemId;
	private int itemType;
	private String itemIcon;
	private int itemCount;

	public AttendanceDailyResource(int days, int nextIndex, int itemId, int itemType, String itemIcon, int itemCount) {
		super();
		this.days = days;
		this.nextIndex = nextIndex;
		this.itemId = itemId;
		this.itemType = itemType;
		this.itemIcon = itemIcon;
		this.itemCount = itemCount;
	}

	public int getDays() {
		return days;
	}

	public int getNextIndex() {
		return nextIndex;
	}

	public int getItemId() {
		return itemId;
	}

	public int getItemType() {
		return itemType;
	}

	public String getItemIcon() {
		return itemIcon;
	}

	public int getItemCount() {
		return itemCount;
	}

}
