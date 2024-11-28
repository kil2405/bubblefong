package com.bubbleShooter.resource;

public class EventResource {
	private int index;
	private int eventNo;
	private byte uiType;
	private int eventTitle;
	private long startDate;
	private long endDate;

	public EventResource(int index, int eventNo, byte uiType, int eventTitle, long startDate, long endDate) {
		super();
		this.index = index;
		this.eventNo = eventNo;
		this.uiType = uiType;
		this.eventTitle = eventTitle;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getIndex() {
		return index;
	}

	public int getEventNo() {
		return eventNo;
	}

	public byte getUiType() {
		return uiType;
	}

	public int getEventTitle() {
		return eventTitle;
	}

	public long getStartDate() {
		return startDate;
	}

	public long getEndDate() {
		return endDate;
	}

}
