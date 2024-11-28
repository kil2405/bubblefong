package com.bubbleShooter.resource;

public class UserExpResource {
	private int index;
	private int number;
	private int value;
	private int need;

	public UserExpResource(int index, int number, int value, int need) {
		super();
		this.index = index;
		this.number = number;
		this.value = value;
		this.need = need;
	}

	public int getIndex() {
		return index;
	}

	public int getNumber() {
		return number;
	}

	public int getValue() {
		return value;
	}

	public int getNeed() {
		return need;
	}

}
