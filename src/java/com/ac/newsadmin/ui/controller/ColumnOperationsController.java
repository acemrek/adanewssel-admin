package com.ac.newsadmin.ui.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ac.common.utility.CommonUtils;
import com.ac.common.utility.FacesUtils;
import com.ac.newsadmin.enums.PhotoSize;
import com.ac.newsadmin.model.entity.Audio;
import com.ac.newsadmin.model.entity.Column;
import com.ac.newsadmin.model.entity.Columnist;
import com.ac.newsadmin.model.entity.Photo;
import com.ac.newsadmin.model.entity.PhotoGalery;
import com.ac.newsadmin.model.entity.Video;
import com.ac.newsadmin.model.transfer.WillBeStoredImg;
import com.ac.newsadmin.ui.tools.photoUpload.AspectRatio;
import com.ac.newsadmin.ui.tools.photoUpload.PhotoUploadCallbacks;
import com.ac.newsadmin.ui.tools.photoUpload.PhotoUploadTool;

@SuppressWarnings("serial")
@Component("columnOperationsBean")
@Scope("view")
public class ColumnOperationsController extends AbstractOperationController implements Serializable{

	private static final String COLUMN_OPERATION_CREATE_COLUMN = "createColumn";
	private static final String COLUMN_OPERATION_EDIT_COLUMN = "editColumn";
	private static final String COLUMN_OPERATION_ORDER_ACTIVE_COLUMNS = "orderActiveColumns";

	private Column willBeCreatedColumn;
	private Column willBeEditedColumn;
	private Column willBeDeletedColumn;

	private List<Columnist> allColumnists;
	private Columnist selectedColumnist;
	
	private List<Column> columnsByColumnist; 
	private List<Column> activeColumns; 
	
	private PhotoUploadTool photoUploadTool = null;
	
	private Long photoGaleryId;
	private Long videoId;
	private Long audioId;
	
	@PostConstruct
	public void init(){
		showEditColumnForm(null);
	}

	//create new portal admin
	public void showCreateColumnForm(){
		if(willBeCreatedColumn == null){
			willBeCreatedColumn = new Column();
		}
		setSelectedOperation(COLUMN_OPERATION_CREATE_COLUMN);
	}
	
	public void createColumn(){
		contentService.saveColumn(willBeCreatedColumn, sessionData.getCurrentSite());
		this.showEditColumnForm(willBeCreatedColumn.getColumnist());
		willBeCreatedColumn = null;
		
	}
	
	public void showEditColumnForm(){
		showEditColumnForm(null);
	}
	
	public void showEditColumnForm(Columnist columnist){
		if(columnist != null){
			selectedColumnist = columnist;
			columnsByColumnist = contentService.getColumnsByColumnist(columnist);
		}
		willBeEditedColumn = null;
		setSelectedOperation(COLUMN_OPERATION_EDIT_COLUMN);
	}
	
	public void handleColumnistChange(){
		columnsByColumnist = contentService.getColumnsByColumnist(selectedColumnist);
		willBeEditedColumn = null;
	}
	
	public void selectWillBeEditedColumn(long columnId){
		willBeEditedColumn = contentService.getColumnById(columnId);
		setSelectedOperation(COLUMN_OPERATION_EDIT_COLUMN);
		initInlinePhotosTool();
	}
	
	public void editColumn(){
		contentService.mergeColumn(willBeEditedColumn);
		showEditColumnForm(selectedColumnist);
	}
	
	public void deleteColumn(Column column){
		contentService.deleteColumn(column);
		showEditColumnForm(selectedColumnist);		
	}
	
	public void showOrderActiveColumnsList(){
		setSelectedOperation(COLUMN_OPERATION_ORDER_ACTIVE_COLUMNS);
		activeColumns = contentService.getActiveColumns(sessionData.getCurrentSite());
	}
	
	public void activeColumnsOrderChanged(){
		contentService.orderActiveColumns(activeColumns);
	}
	
	public void initInlinePhotosTool(){
		photoUploadTool = new PhotoUploadTool(new PhotoUploadCallbacks() {
			
			@Override
			public void setAssetDimensions(WillBeStoredImg willBeStoredImg, int originalImageWidth, int originalImageHeight) {
				willBeStoredImg.setThumbDimension(portalPreferencesService.getNewsPhotoImageSize(sessionData.getCurrentSite(), PhotoSize.THUMBNAIL, originalImageWidth, originalImageHeight));
				willBeStoredImg.setSmallDimension(portalPreferencesService.getNewsPhotoImageSize(sessionData.getCurrentSite(), PhotoSize.SMALL, originalImageWidth, originalImageHeight));
				willBeStoredImg.setMediumDimension(portalPreferencesService.getNewsPhotoImageSize(sessionData.getCurrentSite(), PhotoSize.MEDIUM, originalImageWidth, originalImageHeight));
				willBeStoredImg.setLargeDimension(portalPreferencesService.getNewsPhotoImageSize(sessionData.getCurrentSite(), PhotoSize.LARGE, originalImageWidth, originalImageHeight));
			}
			
			@Override
			public AspectRatio[] getPossibleAspectRatios() {
				return null;
			}
			
			@Override
			public String getFullStorePath() {
				return "columns/" + willBeEditedColumn.getId() + "/photos/inlinePhoto" + System.currentTimeMillis();
			}
			
			@Override
			@Transactional
			public void finish(boolean success, Photo photo) {
				if(success){
					contentService.savePhoto(photo);
					willBeEditedColumn.getPhotos().add(photo);
					contentService.updateColumn(willBeEditedColumn);
				}
			}
		});
		
		springBeanUtils.autowireBean(photoUploadTool);
	}
	
	public void deleteInlinePhoto(Photo photo){
		contentService.deleteColumnPhoto(willBeEditedColumn, photo, sessionData.getCurrentSite());
	}
	
	public void queryForPhotoGalery(){
		if(CommonUtils.numberIsNotNullAndNotZero(photoGaleryId)){
			PhotoGalery willBeAddedPhotoGalery = contentService.getPhotoGaleryById(photoGaleryId);
			if(willBeAddedPhotoGalery != null){
				willBeEditedColumn.getPhotoGaleries().add(willBeAddedPhotoGalery);
				contentService.updateColumn(willBeEditedColumn);
				return;
			}
		}
		
		FacesUtils.addError(null, "writtenContent.noPhotoGaleryForId", "editColumnTabs:columnsPhotoGaleryForm:addPhotoGalery", photoGaleryId);
	}
	
	public void removeFromPhotoGaleries(PhotoGalery photoGalery){
		willBeEditedColumn.getPhotoGaleries().remove(photoGalery);
		contentService.updateColumn(willBeEditedColumn);
	}
	
	public void queryForVideo(){
		if(CommonUtils.numberIsNotNullAndNotZero(videoId)){
			Video willBeAddedVideo = contentService.getVideoById(videoId);
			if(willBeAddedVideo != null){
				willBeEditedColumn.getVideos().add(willBeAddedVideo);
				contentService.updateColumn(willBeEditedColumn);
				return;
			}
		}
		
		FacesUtils.addError(null, "writtenContent.noVideoForId", "editColumnTabs:columnsVideoForm:addVideo", videoId);
	}
	
	public void removeFromVideos(Video video){
		willBeEditedColumn.getVideos().remove(video);
		contentService.updateColumn(willBeEditedColumn);
	}
	
	public void queryForAudio(){
		if(CommonUtils.numberIsNotNullAndNotZero(audioId)){
			Audio willBeAddedAudio = contentService.getAudioById(audioId);
			if(willBeAddedAudio != null){
				willBeEditedColumn.getAudios().add(willBeAddedAudio);
				contentService.updateColumn(willBeEditedColumn);
				return;
			}
		}
		
		FacesUtils.addError(null, "writtenContent.noAudioForId", "editColumnTabs:columnsAudioForm:addAudio", audioId);
	}
	
	public void removeFromAudios(Audio audio){
		willBeEditedColumn.getAudios().remove(audio);
		contentService.updateColumn(willBeEditedColumn);
	}
	
	@Override
	public String getDiscriminator() {
		return "columnOperations";
	}

	public List<Columnist> getAllColumnists() {
		allColumnists = userService.getAllColumnists(sessionData.getCurrentSite());
		return allColumnists;
	}
	public void setAllColumnists(List<Columnist> allColumnists) {
		this.allColumnists = allColumnists;
	}

	public List<Column> getColumnsByColumnist() {
		return columnsByColumnist;
	}

	public void setColumnsByColumnist(List<Column> columnsByColumnist) {
		this.columnsByColumnist = columnsByColumnist;
	}

	public Columnist getSelectedColumnist() {
		return selectedColumnist;
	}

	public void setSelectedColumnist(Columnist selectedColumnist) {
		this.selectedColumnist = selectedColumnist;
	}

	public List<Column> getActiveColumns() {
		return activeColumns;
	}

	public void setActiveColumns(List<Column> activeColumns) {
		this.activeColumns = activeColumns;
	}
	
	public Column getWillBeCreatedColumn() {
		return willBeCreatedColumn;
	}

	public void setWillBeCreatedColumn(Column willBeCreatedColumn) {
		this.willBeCreatedColumn = willBeCreatedColumn;
	}

	public Column getWillBeEditedColumn() {
		return willBeEditedColumn;
	}

	public void setWillBeEditedColumn(Column willBeEditedColumn) {
		this.willBeEditedColumn = willBeEditedColumn;
	}
	
	public Column getWillBeDeletedColumn() {
		return willBeDeletedColumn;
	}

	public void setWillBeDeletedColumn(Column willBeDeletedColumn) {
		this.willBeDeletedColumn = willBeDeletedColumn;
	}

	public PhotoUploadTool getPhotoUploadTool() {
		return photoUploadTool;
	}

	public void setPhotoUploadTool(PhotoUploadTool photoUploadTool) {
		this.photoUploadTool = photoUploadTool;
	}
	
	public Long getPhotoGaleryId() {
		return photoGaleryId;
	}

	public void setPhotoGaleryId(Long photoGaleryId) {
		this.photoGaleryId = photoGaleryId;
	}

	public Long getVideoId() {
		return videoId;
	}

	public void setVideoId(Long videoId) {
		this.videoId = videoId;
	}

	public Long getAudioId() {
		return audioId;
	}

	public void setAudioId(Long audioId) {
		this.audioId = audioId;
	}
}
