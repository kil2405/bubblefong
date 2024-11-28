package com.bubbleShooter.resource;

public class ForbiddenWordsResource {
	private int index;
	private String language;
	private String text;

	public ForbiddenWordsResource(int index, String language, String text) {
		super();
		this.index = index;
		this.language = language;
		this.text = text;
	}

	public int getIndex() {
		return index;
	}

	public String getLanguage() {
		return language;
	}

	public String getText() {
		return text;
	}

}
