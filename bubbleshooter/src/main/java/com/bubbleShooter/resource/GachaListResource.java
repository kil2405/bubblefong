package com.bubbleShooter.resource;

public class GachaListResource {
	private int index;
	private int gachaId;
	private int grade;
	private int count;

	public GachaListResource(int index, int gachaId, int grade, int count) {
		super();
		this.index = index;
		this.gachaId = gachaId;
		this.grade = grade;
		this.count = count;
	}

	public int getIndex() {
		return index;
	}

	public int getGachaId() {
		return gachaId;
	}

	public int getGrade() {
		return grade;
	}

	public int getCount() {
		return count;
	}

}
