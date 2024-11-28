package com.bubbleShooter.resource;

public class MailResource {
	private int index;
	private String mailTitleKr;
	private String mailContentsKr;
	private String mailTitleEn;
	private String mailContentsEn;
	private String desc;

	public MailResource(int index, String mailTitleKr, String mailContentsKr, String mailTitleEn, String mailContentsEn,
			String desc) {
		super();
		this.index = index;
		this.mailTitleKr = mailTitleKr;
		this.mailContentsKr = mailContentsKr;
		this.mailTitleEn = mailTitleEn;
		this.mailContentsEn = mailContentsEn;
		this.desc = desc;
	}

	public int getIndex() {
		return index;
	}

	public String getMailTitleKr() {
		return mailTitleKr;
	}

	public String getMailContentsKr() {
		return mailContentsKr;
	}

	public String getMailTitleEn() {
		return mailTitleEn;
	}

	public String getMailContentsEn() {
		return mailContentsEn;
	}

	public String getDesc() {
		return desc;
	}

}
