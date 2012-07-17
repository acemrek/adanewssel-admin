package com.ac.newsadmin.ui.tools.videoUpload;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jets3t.service.ServiceException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.ac.common.integration.spring.SpringBeanUtils;
import com.ac.common.utility.FacesUtils;
import com.ac.newsadmin.enums.PhotoSize;
import com.ac.newsadmin.model.entity.Photo;
import com.ac.newsadmin.model.transfer.VideoBinary;
import com.ac.newsadmin.model.transfer.WillBeStoredImg;
import com.ac.newsadmin.model.transfer.WillBeStoredVideo;
import com.ac.newsadmin.service.BinaryStorageService;
import com.ac.newsadmin.service.PortalPreferencesService;
import com.ac.newsadmin.ui.SessionData;
import com.ac.newsadmin.ui.tools.photoUpload.AspectRatio;
import com.ac.newsadmin.ui.tools.photoUpload.PhotoUploadCallbacks;
import com.ac.newsadmin.ui.tools.photoUpload.PhotoUploadTool;

@SuppressWarnings("serial")
public class VideoUploadTool implements Serializable{
	
	@Resource private SessionData sessionData;
	@Resource private PortalPreferencesService portalPreferencesService;
	@Resource private SpringBeanUtils springBeanUtils; 
	@Resource private BinaryStorageService binaryStorageService;
	
	private VideoUploadCallbacks callbacks;

	private boolean finishedSuccessfully;
	private VideoBinary videoBinary = new VideoBinary();
	
	private PhotoUploadTool photoUploadTool;
	
	private String tempUploadPath;
	private String videoPath;
	
	public VideoUploadTool(VideoUploadCallbacks callbacks) {
		super();
		this.callbacks = callbacks;
		tempUploadPath = "temp/videoTemps/" + System.currentTimeMillis() + "/";
		videoPath = "video/" + System.currentTimeMillis() + "/";
	}
	
	public void initPhotoUploadTool(){
		photoUploadTool = new PhotoUploadTool(new PhotoUploadCallbacks() {
			
			@Override
			public void setAssetDimensions(WillBeStoredImg willBeStoredImg, int originalImageWidth, int originalImageHeight) {
				willBeStoredImg.setThumbDimension(portalPreferencesService.getVideoThumbnailImageSize(sessionData.getCurrentSite(), PhotoSize.THUMBNAIL));
				willBeStoredImg.setSmallDimension(portalPreferencesService.getVideoThumbnailImageSize(sessionData.getCurrentSite(), PhotoSize.SMALL));
				willBeStoredImg.setMediumDimension(portalPreferencesService.getVideoThumbnailImageSize(sessionData.getCurrentSite(), PhotoSize.MEDIUM));
				willBeStoredImg.setLargeDimension(portalPreferencesService.getVideoThumbnailImageSize(sessionData.getCurrentSite(), PhotoSize.LARGE));
			}
			
			@Override
			public AspectRatio[] getPossibleAspectRatios() {
				return new AspectRatio[]{AspectRatio.RATIO_4_3};
			}
			
			@Override
			public String getFullStorePath() {
				return tempUploadPath + System.currentTimeMillis();
			}
			
			@Override
			public void finish(boolean success, Photo photo) {
				if(success){
					videoBinary.setVideoThumbnail(photo);	
				}
			}
		});
		springBeanUtils.autowireBean(photoUploadTool);
	}

	public void handleMp4Upload(FileUploadEvent event){
		videoBinary.setMp4Path(handleVideoUpload(event));
	}
	
	public void handleOggTheoraUpload(FileUploadEvent event){
		videoBinary.setOggTheoraPath(handleVideoUpload(event));
	}
	
	public void handleWebmUpload(FileUploadEvent event){
		videoBinary.setWebMPath(handleVideoUpload(event));
	}
	
	private String handleVideoUpload(FileUploadEvent event){
		
		try {
			UploadedFile uploadedFile = event.getFile();
			String binaryUrl = tempUploadPath + System.currentTimeMillis() + '.' + FilenameUtils.getExtension(uploadedFile.getFileName());
			WillBeStoredVideo willBeStoredVideo = new WillBeStoredVideo();
			
			willBeStoredVideo.setFullStorePath(binaryUrl);
			willBeStoredVideo.setContentType(uploadedFile.getContentType());
			willBeStoredVideo.setData(uploadedFile.getContents());
			willBeStoredVideo.setSize(uploadedFile.getSize());
			binaryStorageService.storeBinary(sessionData.getCurrentSite(), willBeStoredVideo);
			
			return binaryUrl;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public PhotoUploadTool getPhotoUploadTool() {
		return photoUploadTool;
	}

	public void setPhotoUploadTool(PhotoUploadTool photoUploadTool) {
		this.photoUploadTool = photoUploadTool;
	}
	
	public void finishVideoUpload(){
		try {
			if(finishedSuccessfully == false && StringUtils.isNotBlank(videoBinary.getMp4Path()) && StringUtils.isNotBlank(videoBinary.getOggTheoraPath()) && StringUtils.isNotBlank(videoBinary.getWebMPath()) && videoBinary.getVideoThumbnail() != null){
				finishedSuccessfully = true;
				binaryStorageService.moveVideo(sessionData.getCurrentSite(), videoBinary, videoPath);
				callbacks.finishedSusccessfully(videoBinary);
				FacesUtils.addMessage("videoUpload.message", "videoTool.all.uploaded.successfully", "editVideoTabs:videoFinishForm:finishVideoUploadButton");
			}
			else{
				FacesUtils.addError("videoUpload.error", "videoUpload.notAllAssetsUploaded", "editVideoTabs:videoFinishForm:finishVideoUploadButton");
			}
		} 
		catch (ServiceException e) {
			finishedSuccessfully = false;
			e.printStackTrace();
		}
	}

	public VideoBinary getVideoBinary() {
		return videoBinary;
	}

	public boolean isFinishedSuccessfully() {
		return finishedSuccessfully;
	}


}
