package com.ac.newsadmin.ui.controller.helper;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ac.common.utility.FacesUtils;
import com.ac.newsadmin.model.entity.Photo;
import com.ac.newsadmin.model.entity.Video;
import com.ac.newsadmin.service.ContentService;

@SuppressWarnings("serial")
@Component("mediaUtils")
@Scope("session")
public class MediaUtils implements Serializable{
	
	@Resource ContentService contentService;
	
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
	
	public void setPhotoInfoById(){
		long photoId = FacesUtils.getHttpParamAsLong("willBeViewedPhotoId");
		willBeViewedPhoto = contentService.getPhotoById(photoId);
		willBeViewedPhotoSize = FacesUtils.getHttpParamAsString("willBeViewedPhotoSize");
	}

}
