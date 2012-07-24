package com.ac.newsadmin.ui.controller;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ac.newsadmin.ui.tools.configTools.PhotoSizeConfigTool;

@Component("configOperationsBean")
@Scope("view")
public class ConfigOperationsController extends AbstractOperationController implements Serializable{

	private static final long serialVersionUID = 1L;

	private static final String WRITTEN_CONTENT_PHOTO_SIZES = "writtenContentPhotoSizes";
	private static final String COLUMNIST_PHOTO_SIZES = "columnistPhotoSizes";
	private static final String PHOTO_GALERY_PHOTO_SIZES = "photoGaleryPhotoSizes";
	private static final String VIDEO_THUMBNAIL_PHOTO_SIZES = "videoThumbnailPhotoSizes";
	
	private PhotoSizeConfigTool writtenContentPhotoSizeTool;
	
	public void showWrittenContentPhotoSizesForm(){
		if(writtenContentPhotoSizeTool == null){
			writtenContentPhotoSizeTool = new PhotoSizeConfigTool();
		}
		setSelectedOperation(WRITTEN_CONTENT_PHOTO_SIZES);
	}

	public void showColumnistPhotoSizesForm(){
		setSelectedOperation(COLUMNIST_PHOTO_SIZES);
	}
	
	public void showPhotoGaleryPhotoSizesForm(){
		setSelectedOperation(PHOTO_GALERY_PHOTO_SIZES);
	}
	
	public void showVideoThumbnailPhotoSizesForm(){
		setSelectedOperation(VIDEO_THUMBNAIL_PHOTO_SIZES);
	}
	
	public PhotoSizeConfigTool getWrittenContentPhotoSizeTool() {
		return writtenContentPhotoSizeTool;
	}

	public void setWrittenContentPhotoSizeTool(PhotoSizeConfigTool writtenContentPhotoSizeTool) {
		this.writtenContentPhotoSizeTool = writtenContentPhotoSizeTool;
	}

	@Override
	public String getDiscriminator() {
		return "configOperations";
	}

}
