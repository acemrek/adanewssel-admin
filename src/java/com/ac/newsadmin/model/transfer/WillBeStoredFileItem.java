package com.ac.newsadmin.model.transfer;


public abstract class WillBeStoredFileItem {
	
	private String fullStorePath;
	private byte []data;
	private String contentType;
	private Long size;
	
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getFullStorePath() {
		return fullStorePath;
	}
	public void setFullStorePath(String fullStorePath) {
		this.fullStorePath = fullStorePath;
	}


}
