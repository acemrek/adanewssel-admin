package com.ac.newsadmin.ui.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlForm;

import org.jets3t.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ac.common.utility.FacesUtils;
import com.ac.common.utility.HashingUtils;
import com.ac.newsadmin.enums.Gender;
import com.ac.newsadmin.enums.PhotoSize;
import com.ac.newsadmin.model.entity.Columnist;
import com.ac.newsadmin.model.entity.Photo;
import com.ac.newsadmin.model.transfer.WillBeStoredImg;
import com.ac.newsadmin.ui.tools.photoUpload.AspectRatio;
import com.ac.newsadmin.ui.tools.photoUpload.PhotoUploadCallbacks;
import com.ac.newsadmin.ui.tools.photoUpload.PhotoUploadTool;

@SuppressWarnings("serial")
@Component("columnistOperationsBean")
@Scope("view")
public class ColumnistOperationsController extends AbstractOperationController implements Serializable{

	private static final Logger logger = LoggerFactory.getLogger(ColumnistOperationsController.class);
	
	private static final String COLUMNIST_OPERATION_CREATE_COLUMNIST = "createColumnist";
	private static final String COLUMNIST_OPERATION_EDIT_COLUMNIST = "editColumnist";
	
	private static final String ERROR_KEY_COLUMNIST = "columnist.error";
	private static final String ERROR_KEY_COLUMNIST_SAME_USERNAME = "columnist.error.sameUsername";
	private static final String ERROR_KEY_COLUMNIST_SAME_EMAIL = "columnist.error.sameEmail";
	
	private static final String COMPONENT_ID_CREATE_COLUMNIST_USERNAME = "createColumnistForm:username";
	private static final String COMPONENT_ID_CREATE_COLUMNIST_EMAIL = "createColumnistForm:email";
	private static final String COMPONENT_ID_EDIT_COLUMNIST_USERNAME = "editColumnistTabs:editColumnistForm:username";
	private static final String COMPONENT_ID_EDIT_COLUMNIST_EMAIL = "editColumnistTabs:editColumnistForm:email";
	
	private List<Columnist> allColumnists = null;
	private Columnist willBeCreatedColumnist = null;
	private Columnist willBeEditedColumnist = null;
	private Columnist willBeDeletedColumnist = null; 

	private PhotoUploadTool photoUploadTool = null;
	
	private Photo defaultFemalePhoto;
	private Photo defaultMalePhoto;
	
	private HtmlForm editColumnistForm;
	
	@PostConstruct
	public void init(){
		Long  femalePhotoId = portalPreferencesService.getColumnistDefaultFemalePhotoId(sessionData.getCurrentSite());
		Long  malePhotoId   = portalPreferencesService.getColumnistDefaultMalePhotoId(sessionData.getCurrentSite());
		
		defaultFemalePhoto = contentService.getPhotoById(femalePhotoId);
		defaultMalePhoto   = contentService.getPhotoById(malePhotoId);
		
		showEditColumnistForm();
	}
	
	//create new portal admin
	public void showCreateNewColumnistForm(){
		if(willBeCreatedColumnist == null){
			willBeCreatedColumnist = new Columnist();
		}
		setSelectedOperation(COLUMNIST_OPERATION_CREATE_COLUMNIST);
	}
	
	public void createColumnist(){
		Columnist dbColumnistByUsername = userService.getColumnistByUsername(willBeCreatedColumnist.getUsername(), sessionData.getCurrentSite());
		if(dbColumnistByUsername != null){
			FacesUtils.addError(ERROR_KEY_COLUMNIST, ERROR_KEY_COLUMNIST_SAME_USERNAME, COMPONENT_ID_CREATE_COLUMNIST_USERNAME, new Object[]{willBeCreatedColumnist.getUsername(), sessionData.getCurrentSite().getDomainName()});
			return;
		}
		
		Columnist dbColumnistByEmail = userService.getColumnistByEmail(willBeCreatedColumnist.getEmail(), sessionData.getCurrentSite());
		if(dbColumnistByEmail != null){
			FacesUtils.addError(ERROR_KEY_COLUMNIST, ERROR_KEY_COLUMNIST_SAME_EMAIL, COMPONENT_ID_CREATE_COLUMNIST_EMAIL, new Object[]{willBeCreatedColumnist.getEmail(), sessionData.getCurrentSite().getDomainName()});
			return;
		}
		
		String hashedPassword = HashingUtils.getMD5Hash(willBeCreatedColumnist.getPassword());
		willBeCreatedColumnist.setPassword(hashedPassword);
		willBeCreatedColumnist.setPhoto(willBeCreatedColumnist.getGender() == Gender.FEMALE ? defaultFemalePhoto : defaultMalePhoto);
		userService.saveNewColumnist(willBeCreatedColumnist, sessionData.getCurrentSite());
		willBeCreatedColumnist = null;
	
		this.showEditColumnistForm();
	}
	
	public void showEditColumnistForm(){
		allColumnists = userService.getAllColumnists(sessionData.getCurrentSite());
		setSelectedOperation(COLUMNIST_OPERATION_EDIT_COLUMNIST);	
	}
	
	public void selectWillBeEditedColumnist(long columnistId){
		setSelectedOperation(COLUMNIST_OPERATION_EDIT_COLUMNIST);
		willBeEditedColumnist = userService.getColumnistById(columnistId);
		FacesUtils.resetForm(editColumnistForm);
		initEditColumnistPhoto();
	}
	
	public void editColumnist(){
		
		Columnist dbColumnistByUsername = userService.getColumnistByUsername(willBeEditedColumnist.getUsername(), sessionData.getCurrentSite());
		if(dbColumnistByUsername != null && willBeEditedColumnist.getId() != dbColumnistByUsername.getId()){
			FacesUtils.addError(ERROR_KEY_COLUMNIST, ERROR_KEY_COLUMNIST_SAME_USERNAME, COMPONENT_ID_EDIT_COLUMNIST_USERNAME, new Object[]{sessionData.getCurrentSite().getDomainName(), willBeEditedColumnist.getUsername()});
			return;
		}
		
		Columnist dbColumnistByEmail = userService.getColumnistByEmail(willBeEditedColumnist.getEmail(), sessionData.getCurrentSite());
		if(dbColumnistByEmail != null && willBeEditedColumnist.getId() != dbColumnistByEmail.getId()){
			FacesUtils.addError(ERROR_KEY_COLUMNIST, ERROR_KEY_COLUMNIST_SAME_EMAIL, COMPONENT_ID_EDIT_COLUMNIST_EMAIL, new Object[]{sessionData.getCurrentSite().getDomainName(), willBeEditedColumnist.getEmail()});
			return;
		}
		
		userService.mergeColumnist(willBeEditedColumnist);
		
		willBeEditedColumnist = null;
		showEditColumnistForm();
	}

	public void selectWillBeDeletedColumnist(Columnist willBeDeletedColumnist) {
		this.willBeDeletedColumnist = willBeDeletedColumnist;
	}

	public void deleteColumnist(){
		try {
			userService.deleteColumnist(willBeDeletedColumnist);
		} 
		catch (ServiceException e) {
			logger.error("Error while deleteing columnist with id " + willBeDeletedColumnist.getId(), e);
		}
		showEditColumnistForm();		
	}
	
	public void initEditColumnistPhoto(){
		
		photoUploadTool = new PhotoUploadTool(new PhotoUploadCallbacks() {

			@Override
			public String getFullStorePath() {
				return "columnists/" + willBeEditedColumnist.getId() + "/photos/profilePhoto";
			}

			@Override
			public AspectRatio[] getPossibleAspectRatios() {
				return new AspectRatio[]{AspectRatio.RATIO_1_1};
			}
			
			@Override
			public void setAssetDimensions(WillBeStoredImg willBeStoredImg, int originalImageWidth, int originalImageHeight) {
				willBeStoredImg.setThumbDimension(portalPreferencesService.getColmnistiImageSize(sessionData.getCurrentSite(), PhotoSize.THUMBNAIL));
				willBeStoredImg.setSmallDimension(portalPreferencesService.getColmnistiImageSize(sessionData.getCurrentSite(), PhotoSize.SMALL));
				willBeStoredImg.setMediumDimension(portalPreferencesService.getColmnistiImageSize(sessionData.getCurrentSite(), PhotoSize.MEDIUM));
				willBeStoredImg.setLargeDimension(portalPreferencesService.getColmnistiImageSize(sessionData.getCurrentSite(), PhotoSize.LARGE));
			}
			
			@Override
			@Transactional
			public void finish(boolean success, Photo photo) {
				
				if(success){
					Photo columnistPhoto = willBeEditedColumnist.getPhoto();
					if(columnistPhoto == null || columnistPhoto.getId() == defaultFemalePhoto.getId() || columnistPhoto.getId() == defaultMalePhoto.getId()){
						columnistPhoto = photo;
						contentService.savePhoto(columnistPhoto);
						willBeEditedColumnist.setPhoto(columnistPhoto);
						userService.mergeColumnist(willBeEditedColumnist);
					}
					else{
						Photo dbPhoto = contentService.getPhotoById(willBeEditedColumnist.getPhoto().getId());
						contentService.updatePhoto(dbPhoto);
					}
				}
			}
		});
		
		
		springBeanUtils.autowireBean(photoUploadTool);
	}
	
	@Override
	public String getDiscriminator() {
		return "columnistOperations";
	}

	public List<Columnist> getAllColumnists() {
		return allColumnists;
	}

	public void setAllColumnists(List<Columnist> allColumnists) {
		this.allColumnists = allColumnists;
	}

	public PhotoUploadTool getPhotoUploadTool() {
		return photoUploadTool;
	}

	public void setPhotoUploadTool(PhotoUploadTool photoUploadTool) {
		this.photoUploadTool = photoUploadTool;
	}

	public Columnist getWillBeCreatedColumnist() {
		return willBeCreatedColumnist;
	}

	public void setWillBeCreatedColumnist(Columnist willBeCreatedColumnist) {
		this.willBeCreatedColumnist = willBeCreatedColumnist;
	}

	public Columnist getWillBeEditedColumnist() {
		return willBeEditedColumnist;
	}

	public void setWillBeEditedColumnist(Columnist willBeEditedColumnist) {
		this.willBeEditedColumnist = willBeEditedColumnist;
	}

	public Columnist getWillBeDeletedColumnist() {
		return willBeDeletedColumnist;
	}

	public void setWillBeDeletedColumnist(Columnist willBeDeletedColumnist) {
		this.willBeDeletedColumnist = willBeDeletedColumnist;
	}

	public HtmlForm getEditColumnistForm() {
		return editColumnistForm;
	}

	public void setEditColumnistForm(HtmlForm editColumnistForm) {
		this.editColumnistForm = editColumnistForm;
	}

}