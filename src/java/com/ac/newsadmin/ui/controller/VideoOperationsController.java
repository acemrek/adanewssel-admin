package com.ac.newsadmin.ui.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlForm;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ac.common.utility.FacesUtils;
import com.ac.newsadmin.model.entity.Video;
import com.ac.newsadmin.model.transfer.VideoBinary;
import com.ac.newsadmin.ui.tools.videoUpload.VideoUploadCallbacks;
import com.ac.newsadmin.ui.tools.videoUpload.VideoUploadTool;

@SuppressWarnings("serial")
@Component("videoOperationsBean")
@Scope("view")
public class VideoOperationsController extends AbstractOperationController implements Serializable{

	private static final String VIDEO_OPERATION_CREATE_VIDEO = "createVideo";
	private static final String VIDEO_OPERATION_EDIT_VIDEO = "editVideo";
	
	private List<Video> allVideos;
	
	private Video willBeCreatedVideo;
	private Video willBeEditedVideo;
	private Video willBeDeletedVideo;
	
	private VideoUploadTool videoUploadTool;
	
	private HtmlForm editVideoForm;
	
	@PostConstruct
	public void init(){
		showEditVideoForm();
	}
	
	public void showCreateVideoForm(){
		if(willBeCreatedVideo == null){
			willBeCreatedVideo = new Video();
		}
		setSelectedOperation(VIDEO_OPERATION_CREATE_VIDEO);
	}
	
	public void createVideo(){
		contentService.saveVideo(sessionData.getCurrentSite(), willBeCreatedVideo);
		willBeCreatedVideo = null;
		showEditVideoForm();
	}
	
	public void showEditVideoForm(){
		allVideos = contentService.getVideoBySite(sessionData.getCurrentSite());
		setSelectedOperation(VIDEO_OPERATION_EDIT_VIDEO);
	}
	
	public void selectWillBeEditedVideo(long videoId){
		willBeEditedVideo = contentService.getVideoById(videoId);
		FacesUtils.resetForm(editVideoForm);
		if(willBeEditedVideo.getVideoThumbnail() == null){
			initVideoTool();
		}
	}
	
	public void updateVideo(){
		contentService.mergeVideo(willBeEditedVideo);
		willBeEditedVideo = null;
		showEditVideoForm();
	}
	
	public void initVideoTool(){
		videoUploadTool = new VideoUploadTool(new VideoUploadCallbacks() {
			@Override
			@Transactional
			public void finishedSusccessfully(VideoBinary videoBinary) {
				contentService.savePhoto(videoBinary.getVideoThumbnail());
				
				willBeEditedVideo.setVideoThumbnail(videoBinary.getVideoThumbnail());
				willBeEditedVideo.setMp4Path(videoBinary.getMp4Path());
				willBeEditedVideo.setOggTheoraPath(videoBinary.getOggTheoraPath());
				willBeEditedVideo.setWebMPath(videoBinary.getWebMPath());
				
				contentService.mergeVideo(willBeEditedVideo);
			}
		});
		springBeanUtils.autowireBean(videoUploadTool);
		videoUploadTool.initPhotoUploadTool();
	}
	
	public void selectWillBeDeletedVideo(Video video){
		willBeDeletedVideo = video;
	}
	
	public void deleteVideo(){
//		Video binaryVideo = willBeDeletedVideo.getVideo();
//		Photo thumbnailPhoto = willBeDeletedVideo.getVideo().getVideoThumbnail();
//		contentService.removeSiteVideo(willBeDeletedVideo);
//		contentService.removeVideoBinaryInfo(binaryVideo);
//		contentService.deletePhoto(thumbnailPhoto);
	}
	
	@Override
	public String getDiscriminator() {
		return "videoOperations";
	}

	public List<Video> getAllVideos(){
		return allVideos;
	}
	
	public VideoUploadTool getVideoUploadTool() {
		return videoUploadTool;
	}

	public Video getWillBeCreatedVideo() {
		return willBeCreatedVideo;
	}

	public void setWillBeCreatedVideo(Video willBeCreatedVideo) {
		this.willBeCreatedVideo = willBeCreatedVideo;
	}

	public Video getWillBeEditedVideo() {
		return willBeEditedVideo;
	}

	public void setWillBeEditedVideo(Video willBeEditedVideo) {
		this.willBeEditedVideo = willBeEditedVideo;
	}

	public Video getWillBeDeletedVideo() {
		return willBeDeletedVideo;
	}

	public void setWillBeDeletedVideo(Video willBeDeletedVideo) {
		this.willBeDeletedVideo = willBeDeletedVideo;
	}

	public HtmlForm getEditVideoForm() {
		return editVideoForm;
	}

	public void setEditVideoForm(HtmlForm editVideoForm) {
		this.editVideoForm = editVideoForm;
	}

	
}
