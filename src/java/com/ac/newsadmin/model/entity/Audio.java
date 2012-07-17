package com.ac.newsadmin.model.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Audio extends AbstractMediaContent implements Serializable{
	
	private String binaryUrl;

	public String getBinaryUrl() {
		return binaryUrl;
	}
	public void setBinaryUrl(String binaryUrl) {
		this.binaryUrl = binaryUrl;
	}

}
