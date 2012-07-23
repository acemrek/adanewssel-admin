package com.ac.newsadmin.ui.controller.helper;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ac.newsadmin.model.entity.Audio;
import com.ac.newsadmin.model.entity.Photo;
import com.ac.newsadmin.model.entity.PhotoGalery;
import com.ac.newsadmin.model.entity.Video;
import com.ac.newsadmin.service.ContentService;

@SuppressWarnings("serial")
@Component("mediaUtils")
@Scope("session")
public class MediaUtils implements Serializable{
	
	@Resource ContentService contentService;
	
	private Photo willBeViewedPhoto;
	private String willBeViewedPhotoSize;
	
	private PhotoGalery willBeViewedPhotoGalery;
	private int galeryPhotoIndex;
	private String willBeViewedPhotoGalerySize;
	
	private Video willBeViewedVideo;
	
	private Audio willBeListenedAudio;
	
	public void setPhotoInfoById(Long willBeViewedPhotoId, String willBeViewedPhotoSize){
		willBeViewedPhoto = contentService.getPhotoById(willBeViewedPhotoId);
		this.willBeViewedPhotoSize = willBeViewedPhotoSize;
	}
	
	public void incrementGaleryPhotoIndex(){
		galeryPhotoIndex++;
	}
	
	public void decrementGaleryPhotoIndex(){
		galeryPhotoIndex--;
	}

	/* getter and setters */
	public Photo getWillBeViewedPhoto() {
		return willBeViewedPhoto;
	}

	public void setWillBeViewedPhoto(Photo willBeViewedPhoto) {
		this.willBeViewedPhoto = willBeViewedPhoto;
	}

	public String getWillBeViewedPhotoSize() {
		return willBeViewedPhotoSize;
	}

	public void setWillBeViewedPhotoSize(String willBeViewedPhotoSize) {
		this.willBeViewedPhotoSize = willBeViewedPhotoSize;
	}

	public PhotoGalery getWillBeViewedPhotoGalery() {
		return willBeViewedPhotoGalery;
	}

	public void setWillBeViewedPhotoGalery(PhotoGalery willBeViewedPhotoGalery) {
		this.willBeViewedPhotoGalery = willBeViewedPhotoGalery;
	}

	public int getGaleryPhotoIndex() {
		return galeryPhotoIndex;
	}

	public void setGaleryPhotoIndex(int galeryPhotoIndex) {
		this.galeryPhotoIndex = galeryPhotoIndex;
	}

	public String getWillBeViewedPhotoGalerySize() {
		return willBeViewedPhotoGalerySize;
	}

	public void setWillBeViewedPhotoGalerySize(String willBeViewedPhotoGalerySize) {
		this.willBeViewedPhotoGalerySize = willBeViewedPhotoGalerySize;
	}

	public Video getWillBeViewedVideo() {
		return willBeViewedVideo;
	}

	public void setWillBeViewedVideo(Video willBeViewedVideo) {
		this.willBeViewedVideo = willBeViewedVideo;
	}
	
	public Audio getWillBeListenedAudio() {
		return willBeListenedAudio;
	}

	public void setWillBeListenedAudio(Audio willBeListenedAudio) {
		this.willBeListenedAudio = willBeListenedAudio;
	}
}
