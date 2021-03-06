package com.ac.newsadmin.model.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Video extends AbstractMediaContent implements Serializable{
	
	private int watchCount;

	private Photo videoThumbnail;
	private String oggTheoraPath;
	private String mp4Path;
	private String webMPath;
	
	public int getWatchCount() {
		return watchCount;
	}
	public void setWatchCount(int watchCount) {
		this.watchCount = watchCount;
	}
	public Photo getVideoThumbnail(){
		return videoThumbnail;
	}
	public void setVideoThumbnail(Photo videoThumbnail) {
		this.videoThumbnail = videoThumbnail;
	}
	public String getOggTheoraPath() {
		return oggTheoraPath;
	}
	public void setOggTheoraPath(String oggTheoraPath) {
		this.oggTheoraPath = oggTheoraPath;
	}
	public String getMp4Path() {
		return mp4Path;
	}
	public void setMp4Path(String mp4Path) {
		this.mp4Path = mp4Path;
	}
	public String getWebMPath() {
		return webMPath;
	}
	public void setWebMPath(String webMPath) {
		this.webMPath = webMPath;
	}
	
	
}
