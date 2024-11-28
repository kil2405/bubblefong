package com.bubbleShooter.resource;

public class ErrorCodeResource {
	private int errorCode;
	private byte actionCode;
	private String messageKo;
	private String messageEn;

	public ErrorCodeResource(int errorCode, byte actionCode, String messageKo, String messageEn) {
		super();
		this.errorCode = errorCode;
		this.actionCode = actionCode;
		this.messageKo = messageKo;
		this.messageEn = messageEn;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public byte getActionCode() {
		return actionCode;
	}

	public String getMessageKo() {
		return messageKo;
	}

	public String getMessageEn() {
		return messageEn;
	}

}
