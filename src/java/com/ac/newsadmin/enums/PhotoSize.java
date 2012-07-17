package com.ac.newsadmin.enums;

public enum PhotoSize {
	THUMBNAIL("thumb"),
	SMALL("small"),
	MEDIUM("medium"),
	LARGE("large"),
	ORIGINAL("org");
	
	private String val;

	private PhotoSize(String val) {
		this.val = val;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}
	
}
