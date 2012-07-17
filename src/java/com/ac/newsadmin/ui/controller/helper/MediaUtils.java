package com.ac.newsadmin.ui.controller.helper;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ac.newsadmin.model.entity.Photo;
import com.ac.newsadmin.model.entity.Video;

@SuppressWarnings("serial")
@Component("mediaUtils")
@Scope("session")
public class MediaUtils implements Serializable{
	
	private Photo willBeViewedPhoto;
	private String willBeViewedPhotoSize;
	
	public String getWillBeViewedPhotoSize() {
		return willBeViewedPhotoSize;
	}
	public void setWillBeViewedPhotoSize(String willBeViewedPhotoSize) {
		this.willBeViewedPhotoSize = willBeViewedPhotoSize;
	}
	private Video willBeViewedVideo;
	
	public Photo getWillBeViewedPhoto() {
		return willBeViewedPhoto;
	}
	public void setWillBeViewedPhoto(Photo willBeViewedPhoto) {
		this.willBeViewedPhoto = willBeViewedPhoto;
	}
	
	public Video getWillBeViewedVideo() {
		return willBeViewedVideo;
	}
	public void setWillBeViewedVideo(Video willBeViewedVideo) {
		this.willBeViewedVideo = willBeViewedVideo;
	}

}
