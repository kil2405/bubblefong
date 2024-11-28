package com.bubbleShooter.domain;

public class Mail {

	private int mailId;
	private byte mailType;
	private int userId;
	private int mailIdVerify;
	private String title;
	private String description;
	private String mailItem0;
	private String mailItem1;
	private String mailItem2;
	private String mailItem3;
	private String mailItem4;
	private int state;
	private long expiredTime;
	private long openTime;
	private long receiveTime;
	private byte is_expired;
	private byte is_new;
	private long createTime;

	public int getMailId() {
		return mailId;
	}

	public void setMailId(int mailId) {
		this.mailId = mailId;
	}

	public byte getMailType() {
		return mailType;
	}

	public void setMailType(byte mailType) {
		this.mailType = mailType;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getMailIdVerify() {
		return mailIdVerify;
	}

	public void setMailIdVerify(int mailIdVerify) {
		this.mailIdVerify = mailIdVerify;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMailItem0() {
		return mailItem0;
	}

	public void setMailItem0(String mailItem0) {
		this.mailItem0 = mailItem0;
	}

	public String getMailItem1() {
		return mailItem1;
	}

	public void setMailItem1(String mailItem1) {
		this.mailItem1 = mailItem1;
	}

	public String getMailItem2() {
		return mailItem2;
	}

	public void setMailItem2(String mailItem2) {
		this.mailItem2 = mailItem2;
	}

	public String getMailItem3() {
		return mailItem3;
	}

	public void setMailItem3(String mailItem3) {
		this.mailItem3 = mailItem3;
	}

	public String getMailItem4() {
		return mailItem4;
	}

	public void setMailItem4(String mailItem4) {
		this.mailItem4 = mailItem4;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public long getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(long expiredTime) {
		this.expiredTime = expiredTime;
	}

	public long getOpenTime() {
		return openTime;
	}

	public void setOpenTime(long openTime) {
		this.openTime = openTime;
	}

	public long getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(long receiveTime) {
		this.receiveTime = receiveTime;
	}

	public byte getIs_expired() {
		return is_expired;
	}

	public void setIs_expired(byte is_expired) {
		this.is_expired = is_expired;
	}

	public byte getIs_new() {
		return is_new;
	}

	public void setIs_new(byte is_new) {
		this.is_new = is_new;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

}
