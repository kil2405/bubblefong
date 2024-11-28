package com.bubbleShooter.domain;

public class GachaList {
	private int seq;
	private int gachaId;
	private int orderIndex;
	private String gradeList;
	private long startTime;
	private long endTime;
	private byte vaild;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getGachaId() {
		return gachaId;
	}

	public void setGachaId(int gachaId) {
		this.gachaId = gachaId;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public String getGradeList() {
		return gradeList;
	}

	public void setGradeList(String gradeList) {
		this.gradeList = gradeList;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public byte getVaild() {
		return vaild;
	}

	public void setVaild(byte vaild) {
		this.vaild = vaild;
	}

}
