package com.ac.newsadmin.ui.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlForm;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ac.common.utility.FacesUtils;
import com.ac.newsadmin.enums.PhotoSize;
import com.ac.newsadmin.model.entity.GaleryPhoto;
import com.ac.newsadmin.model.entity.Photo;
import com.ac.newsadmin.model.entity.PhotoGalery;
import com.ac.newsadmin.model.transfer.WillBeStoredImg;
import com.ac.newsadmin.ui.tools.photoUpload.AspectRatio;
import com.ac.newsadmin.ui.tools.photoUpload.PhotoUploadCallbacks;
import com.ac.newsadmin.ui.tools.photoUpload.PhotoUploadTool;

@SuppressWarnings("serial")
@Component("photoGaleryOperationsBean")
@Scope("view")
public class PhotoGaleryController extends AbstractOperationController implements Serializable{

	private static final String PHOTO_GALERY_OPERATION_CREATE_PHOTO_GALERY = "createPhotoGalery";
	private static final String PHOTO_GALERY_OPERATION_EDIT_PHOTO_GALERY = "editPhotoGalery";
	
	private static final String PHOTO_GALERY_CREATED_SUCCESSFULLY = "photoGalery.createdSuccessfully";
	private static final String PHOTO_GALERY_UPDATED_SUCCESSFULLY = "photoGalery.updatedSuccessfully";
	private static final String PHOTO_GALERY_GALERY_PHOTO_UPDATED_SUCCESSFULLY = "photoGalery.galeryPhoto.updatedSuccessfully";

	private static final String HTTP_PARAM_WILL_BE_EDITED_GALERY_PHOTO_ID = "willBeEditedGaleryPhotoId";
	private static final String HTTP_PARAM_WILL_BE_DELETED_GALERY_PHOTO_ID = "willBeDeletedGaleryPhotoId";
	
	private List<PhotoGalery> allPhotogaleries = null;
	
	private PhotoGalery willBeCreatedPhotoGalery = null;
	private PhotoGalery willBeEditedPhotoGalery = null;
	private PhotoGalery willBeDeletedPhotoGalery = null;
	private HtmlForm selectedPhotoGaleryForm;
	
	private GaleryPhoto willBeEditedGaleryPhoto;
	private GaleryPhoto willBeDeletedGaleryPhoto;
	private HtmlForm selectedGaleryPhotoForm;
	
	private PhotoUploadTool photoUploadTool = null;
	
	@PostConstruct
	public void init(){
		showEditPhotoGaleryForm();
	}
	
	//creating new photo galery
	public void showAddPhotoGaleryForm(){
		willBeCreatedPhotoGalery = new PhotoGalery();
		setSelectedOperation(PHOTO_GALERY_OPERATION_CREATE_PHOTO_GALERY);
	}
	
	public void createPhotoGalery(){
		contentService.addNewPhotoGalery(willBeCreatedPhotoGalery, sessionData.getCurrentSite());
		willBeCreatedPhotoGalery = null;
		showEditPhotoGaleryForm();
		FacesUtils.addMessage(null, PHOTO_GALERY_CREATED_SUCCESSFULLY, null);
	}
	
	//edit photo galery
	public void showEditPhotoGaleryForm(){
		allPhotogaleries = contentService.getPhotoGaleryBySite(sessionData.getCurrentSite());
		setSelectedOperation(PHOTO_GALERY_OPERATION_EDIT_PHOTO_GALERY);
	}
	
	public void selectWillBeEditedPhotoGalery(long galeryId){
		PhotoGalery galeryFromView = contentService.getPhotoGaleryById(galeryId);
		willBeEditedPhotoGalery = galeryFromView;
		willBeEditedGaleryPhoto = null;
		FacesUtils.resetForm(selectedPhotoGaleryForm);
		initPhotoUploadTool();
	}

	public void updatePhotoGalery(){
		contentService.mergePhotoGalery(willBeEditedPhotoGalery);
		showEditPhotoGaleryForm();
		FacesUtils.addMessage(null, PHOTO_GALERY_UPDATED_SUCCESSFULLY, null);
	}

	public void selectWillBeDeletedPhotoGalery(PhotoGalery photoGalery){
		this.willBeDeletedPhotoGalery = photoGalery;
	}

	public void deletePhotoGalery(){
//		contentService.deletePhotoGalery(sessionData.getCurrentSite(), willBeEditedPhotoGalery);
//		willBeEditedPhotoGalery = null;
	}
	
	public void selectWillBeEditedGaleryPhoto(){
		long galeryPhotoId = FacesUtils.getHttpParamAsLong(HTTP_PARAM_WILL_BE_EDITED_GALERY_PHOTO_ID);
		GaleryPhoto galeryPhoto = contentService.getGaleryPhotoById(galeryPhotoId);
		this.willBeEditedGaleryPhoto = galeryPhoto;
		FacesUtils.resetForm(selectedGaleryPhotoForm);
	}
	
	public void updateGaleryPhoto(){
		contentService.mergeGaleryPhoto(willBeEditedGaleryPhoto);
		willBeEditedGaleryPhoto = null;
		FacesUtils.addMessage(null, PHOTO_GALERY_GALERY_PHOTO_UPDATED_SUCCESSFULLY, null);
	}

	public void selectWillBeDeletedGaleryPhoto(){
		long galeryPhotoId = FacesUtils.getHttpParamAsLong(HTTP_PARAM_WILL_BE_DELETED_GALERY_PHOTO_ID);
		for(GaleryPhoto galeryPhoto : willBeEditedPhotoGalery.getPhotos()){
			if(galeryPhoto.getId() == galeryPhotoId){
				willBeDeletedGaleryPhoto = galeryPhoto;
				break;
			}
		}
	}
	
	public void deleteGaleryPhoto(){
		contentService.deleteGaleryPhoto(sessionData.getCurrentSite(), willBeEditedPhotoGalery, willBeDeletedGaleryPhoto);
		willBeDeletedGaleryPhoto = null;
	}

	private void initPhotoUploadTool(){
		photoUploadTool = new PhotoUploadTool(
			new PhotoUploadCallbacks() {
				@Override
				public void setAssetDimensions(WillBeStoredImg willBeStoredImg, int originalImageWidth, int originalImageHeight) {
					willBeStoredImg.setThumbDimension(portalPreferencesService.getGaleryPhotoImageSize(sessionData.getCurrentSite(), PhotoSize.THUMBNAIL, originalImageWidth, originalImageHeight));
					willBeStoredImg.setSmallDimension(portalPreferencesService.getGaleryPhotoImageSize(sessionData.getCurrentSite(), PhotoSize.SMALL, originalImageWidth, originalImageHeight));
					willBeStoredImg.setMediumDimension(portalPreferencesService.getGaleryPhotoImageSize(sessionData.getCurrentSite(), PhotoSize.MEDIUM, originalImageWidth, originalImageHeight));
					willBeStoredImg.setLargeDimension(portalPreferencesService.getGaleryPhotoImageSize(sessionData.getCurrentSite(), PhotoSize.LARGE, originalImageWidth, originalImageHeight));
				}
				
				@Override
				public AspectRatio[] getPossibleAspectRatios() {
					return null;
				}
				
				@Override
				public String getFullStorePath() {
					return "photoGalery/" + willBeEditedPhotoGalery.getId() + "/" +  System.currentTimeMillis();
				}
				
				@Override
				@Transactional
				public void finish(boolean success, Photo photo) {
					contentService.savePhoto(photo);
					
					GaleryPhoto galeryPhoto = new GaleryPhoto();
					galeryPhoto.setListOrder(willBeEditedPhotoGalery.getPhotos().size() + 1);
					galeryPhoto.setPhoto(photo);
					galeryPhoto.setPhotoGalery(willBeEditedPhotoGalery);
					
					contentService.addNewGaleryPhoto(galeryPhoto);

					willBeEditedPhotoGalery.getPhotos().add(galeryPhoto);
				}
			}
		);
				
		springBeanUtils.autowireBean(photoUploadTool);		
	}
	
	public void orderGaleryPhotos(){
		contentService.orderGaleryPhotos(willBeEditedPhotoGalery.getPhotos());
	}
	
	@Override
	public String getDiscriminator() {
		return "photoGaleryOperations";
	}

	public PhotoUploadTool getPhotoUploadTool() {
		return photoUploadTool;
	}

	public void setPhotoUploadTool(PhotoUploadTool photoUploadTool) {
		this.photoUploadTool = photoUploadTool;
	}

	public List<PhotoGalery> getAllPhotogaleries() {
		return allPhotogaleries;
	}

	public HtmlForm getSelectedPhotoGaleryForm() {
		return selectedPhotoGaleryForm;
	}

	public void setSelectedPhotoGaleryForm(HtmlForm selectedPhotoGaleryForm) {
		this.selectedPhotoGaleryForm = selectedPhotoGaleryForm;
	}
	
	public PhotoGalery getWillBeCreatedPhotoGalery() {
		return willBeCreatedPhotoGalery;
	}

	public void setWillBeCreatedPhotoGalery(PhotoGalery willBeCreatedPhotoGalery) {
		this.willBeCreatedPhotoGalery = willBeCreatedPhotoGalery;
	}

	public PhotoGalery getWillBeEditedPhotoGalery() {
		return willBeEditedPhotoGalery;
	}

	public void setWillBeEditedPhotoGalery(PhotoGalery willBeEditedPhotoGalery) {
		this.willBeEditedPhotoGalery = willBeEditedPhotoGalery;
	}

	public PhotoGalery getWillBeDeletedPhotoGalery() {
		return willBeDeletedPhotoGalery;
	}

	public void setWillBeDeletedPhotoGalery(PhotoGalery willBeDeletedPhotoGalery) {
		this.willBeDeletedPhotoGalery = willBeDeletedPhotoGalery;
	}

	public GaleryPhoto getWillBeDeletedGaleryPhoto() {
		return willBeDeletedGaleryPhoto;
	}

	public void setWillBeDeletedGaleryPhoto(GaleryPhoto willBeDeletedGaleryPhoto) {
		this.willBeDeletedGaleryPhoto = willBeDeletedGaleryPhoto;
	}

	public GaleryPhoto getWillBeEditedGaleryPhoto() {
		return willBeEditedGaleryPhoto;
	}

	public void setWillBeEditedGaleryPhoto(GaleryPhoto willBeEditedGaleryPhoto) {
		this.willBeEditedGaleryPhoto = willBeEditedGaleryPhoto;
	}

	public HtmlForm getSelectedGaleryPhotoForm() {
		return selectedGaleryPhotoForm;
	}

	public void setSelectedGaleryPhotoForm(HtmlForm selectedGaleryPhotoForm) {
		this.selectedGaleryPhotoForm = selectedGaleryPhotoForm;
	}
	
}