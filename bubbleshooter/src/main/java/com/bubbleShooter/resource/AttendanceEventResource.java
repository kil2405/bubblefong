package com.bubbleShooter.resource;

public class AttendanceEventResource {
	private int index;
	private int eventNo;
	private int day;
	private int itemId;
	private int itemType;
	private String itemIcon;
	private int itemCount;

	public AttendanceEventResource(int index, int eventNo, int day, int itemId, int itemType, String itemIcon,
			int itemCount) {
		super();
		this.index = index;
		this.eventNo = eventNo;
		this.day = day;
		this.itemId = itemId;
		this.itemType = itemType;
		this.itemIcon = itemIcon;
		this.itemCount = itemCount;
	}

	public int getIndex() {
		return index;
	}

	public int getEventNo() {
		return eventNo;
	}

	public int getDay() {
		return day;
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
