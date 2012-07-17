package com.ac.newsadmin.ui.tools.photoUpload;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.UploadedFile;
import org.springframework.transaction.annotation.Transactional;

import com.ac.common.utility.FacesUtils;
import com.ac.newsadmin.model.entity.Photo;
import com.ac.newsadmin.model.transfer.WillBeStoredImg;
import com.ac.newsadmin.service.ContentService;
import com.ac.newsadmin.service.PhotoService;
import com.ac.newsadmin.ui.SessionData;

@SuppressWarnings("serial")
public class PhotoUploadTool implements Serializable{

	@Resource protected PhotoService photoService;
	@Resource protected ContentService contentService;
	@Resource protected SessionData  sessionData;

	private AspectRatio []possibleAspectRatios;
	private AspectRatio selectedAspectRatio;
	
	private PhotoUploadCallbacks callback;
	
	private Photo resultPhoto;
	private WillBeStoredImg originalUploadedImgData;
	private String tempImgUrl = null;
	private CroppedImage croppedImage;
	private boolean completedSuccessfully;
	
	public PhotoUploadTool(PhotoUploadCallbacks callback) {
		super();
		this.callback = callback;
		
		possibleAspectRatios = callback.getPossibleAspectRatios();
		if(possibleAspectRatios != null){
			this.selectedAspectRatio = possibleAspectRatios[0];
		}
	}
	
	public void refreshTool(){
		resultPhoto = null;
		originalUploadedImgData = null;
		tempImgUrl = null;
		croppedImage = null;
		completedSuccessfully = false;		
	}

	@Transactional
	public void handlePhotoUpload(FileUploadEvent event){
		try{
			UploadedFile uploadedFile = event.getFile();

			WillBeStoredImg willBeStoredImg = new WillBeStoredImg();
			
			willBeStoredImg.setFullStorePath(uploadedFile.getFileName());
			willBeStoredImg.setContentType(uploadedFile.getContentType());
			willBeStoredImg.setData(uploadedFile.getContents());
			willBeStoredImg.setSize(uploadedFile.getSize());
			
			tempImgUrl = photoService.storeTempImage(sessionData.getCurrentSite(), willBeStoredImg);
			originalUploadedImgData = willBeStoredImg;
		} 
		catch (Exception e) {
			e.printStackTrace();
			FacesUtils.addError("photoUploadTool.error.summary", "photoUploadTool.error.upload", null);
			callback.finish(false, resultPhoto);
		}
	}
	
	public void crop(){  
		try{
			String extension = FilenameUtils.getExtension(croppedImage.getOriginalFilename());
			finishPhotoUpload(croppedImage.getBytes(), croppedImage.getBytes().length, originalUploadedImgData.getContentType(), extension);
		}
		catch (Exception e) {
			e.printStackTrace();
			FacesUtils.addError("photoUploadTool.error.summary", "photoUploadTool.error.upload", null);
			callback.finish(false, resultPhoto);
		}
    }  
	
	private void finishPhotoUpload(byte []data, long size, String contentType, String extension) throws Exception{
		resultPhoto = createAssets(data, size, contentType, extension);
		callback.finish(true, resultPhoto);
		completedSuccessfully = true;
	}
	
	private Photo createAssets(byte []data, long size, String contentType, String extension) throws Exception{
		WillBeStoredImg willBeStoredImg = new WillBeStoredImg();
		willBeStoredImg.setFullStorePath(callback.getFullStorePath() + "." + extension);
		willBeStoredImg.setContentType(contentType);
		willBeStoredImg.setData(data);
		willBeStoredImg.setSize(size);

		InputStream in = new ByteArrayInputStream(data);
		BufferedImage bImageFromConvert = ImageIO.read(in);

		int imageWidth  = bImageFromConvert.getWidth();
		int imageHeight = bImageFromConvert.getHeight();
			
		callback.setAssetDimensions(willBeStoredImg, imageWidth, imageHeight);
		
		return photoService.createNewPhoto(sessionData.getCurrentSite(), willBeStoredImg);
	}

	public String getTempImgUrl() {
		return tempImgUrl;
	}

	public void setTempImgUrl(String tempImgUrl) {
		this.tempImgUrl = tempImgUrl;
	}

	public CroppedImage getCroppedImage() {
		return croppedImage;
	}

	public void setCroppedImage(CroppedImage croppedImage) {
		this.croppedImage = croppedImage;
	}

	public Photo getResultPhoto() {
		return resultPhoto;
	}

	public void setResultPhoto(Photo resultPhoto) {
		this.resultPhoto = resultPhoto;
	}

	public boolean isCompletedSuccessfully() {
		return completedSuccessfully;
	}

	public void setCompletedSuccessfully(boolean completedSuccessfully) {
		this.completedSuccessfully = completedSuccessfully;
	}

	public AspectRatio[] getPossibleAspectRatios() {
		return possibleAspectRatios;
	}

	public void setPossibleAspectRatios(AspectRatio[] possibleAspectRatios) {
		this.possibleAspectRatios = possibleAspectRatios;
	}

	public AspectRatio getSelectedAspectRatio() {
		return selectedAspectRatio;
	}

	public void setSelectedAspectRatio(AspectRatio selectedAspectRatio) {
		this.selectedAspectRatio = selectedAspectRatio;
	}
}
