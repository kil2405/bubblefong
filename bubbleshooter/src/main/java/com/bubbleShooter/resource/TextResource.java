package com.bubbleShooter.resource;

public class TextResource {
	private int index;
	private int id;
	private String kr;
	private String us;
	private String desc;

	public TextResource(int index, int id, String kr, String us, String desc) {
		super();
		this.index = index;
		this.id = id;
		this.kr = kr;
		this.us = us;
		this.desc = desc;
	}

	public int getIndex() {
		return index;
	}

	public int getId() {
		return id;
	}

	public String getKr() {
		return kr;
	}

	public String getUs() {
		return us;
	}

	public String getDesc() {
		return desc;
	}

}
