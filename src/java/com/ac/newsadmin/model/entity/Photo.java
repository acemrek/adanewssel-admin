package com.ac.newsadmin.model.entity;

import java.io.Serializable;

import com.ac.common.model.entity.IdedBase;

@SuppressWarnings("serial")
public class Photo extends IdedBase implements Serializable{
	
	private String originalPath;
	private String thumbnailPath;
	private String smallPath;
	private String mediumPath;
	private String largePath;
	
	public String getOriginalPath() {
		return originalPath;
	}
	public void setOriginalPath(String originalPath) {
		this.originalPath = originalPath;
	}
	public String getThumbnailPath() {
		return thumbnailPath;
	}
	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
	public String getSmallPath() {
		return smallPath;
	}
	public void setSmallPath(String smallPath) {
		this.smallPath = smallPath;
	}
	public String getMediumPath() {
		return mediumPath;
	}
	public void setMediumPath(String mediumPath) {
		this.mediumPath = mediumPath;
	}
	public String getLargePath() {
		return largePath;
	}
	public void setLargePath(String largePath) {
		this.largePath = largePath;
	}

	
}
