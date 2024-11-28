package com.bubbleShooter.common;

import javax.servlet.ServletException;

public class BubbleException extends ServletException {
	private static final long serialVersionUID = 1L;
	public long userId;
	public int errorCode;
	public String message;

	public BubbleException(int errorCode) {
		this.errorCode = errorCode;
		this.message = "";
		this.userId = -1;
	}
	
	public BubbleException(int errorCode, long userId) {
		this.errorCode = errorCode;
		this.userId = userId;
		this.message = "";
	}

	public BubbleException(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
		this.userId = -1;
	}
	
	public BubbleException(long userId, int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
		this.userId = userId;
	}
}
