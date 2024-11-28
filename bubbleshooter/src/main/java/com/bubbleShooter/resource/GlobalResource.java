package com.bubbleShooter.resource;

public class GlobalResource {
	private int index;
	private int id;
	private String name;
	private int value;
	private String desc1;
	private String desc2;

	public GlobalResource(int index, int id, String name, int value, String desc1, String desc2) {
		super();
		this.index = index;
		this.id = id;
		this.name = name;
		this.value = value;
		this.desc1 = desc1;
		this.desc2 = desc2;
	}

	public int getIndex() {
		return index;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public String getDesc1() {
		return desc1;
	}

	public String getDesc2() {
		return desc2;
	}

}
