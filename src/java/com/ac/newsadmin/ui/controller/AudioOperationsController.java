package com.ac.newsadmin.ui.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlForm;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ac.common.utility.FacesUtils;
import com.ac.newsadmin.model.entity.Audio;
import com.ac.newsadmin.ui.tools.audioUpload.AudioUploadCallbacks;
import com.ac.newsadmin.ui.tools.audioUpload.AudioUploadTool;

@SuppressWarnings("serial")
@Component("audioOperationsBean")
@Scope("view")
public class AudioOperationsController extends AbstractOperationController implements Serializable{

	private static final String AUDIO_OPERATION_CREATE_AUDIO = "createAudio";
	private static final String AUDIO_OPERATION_EDIT_AUDIO = "editAudio";
	
	private List<Audio> allAudios;
	
	private Audio willBeCreatedAudio;
	private Audio willBeEditedAudio;
	private Audio willBeDeletedAudio;
	
	private HtmlForm editAudioForm;
	
	private AudioUploadTool audioUploadTool;
	
	@PostConstruct
	public void init(){
		showEditAudioForm();
	}
	
	public void showCreateAudioForm(){
		if(willBeCreatedAudio == null){
			willBeCreatedAudio = new Audio();
		}
		setSelectedOperation(AUDIO_OPERATION_CREATE_AUDIO);
	}
	
	public void createAudio(){
		contentService.saveAudio(sessionData.getCurrentSite(), willBeCreatedAudio);
		willBeCreatedAudio = null;
		showEditAudioForm();
	}
	
	public void showEditAudioForm(){
		allAudios = contentService.getAudioBySite(sessionData.getCurrentSite());
		setSelectedOperation(AUDIO_OPERATION_EDIT_AUDIO);
	}
	
	public void updateAudio(){
		contentService.mergeAudio(willBeEditedAudio);
		willBeEditedAudio = null;
		showEditAudioForm();
	}
	
	public void selectWillBeEditedAudio(long audioId){
		willBeEditedAudio = contentService.getAudioById(audioId);
		FacesUtils.resetForm(editAudioForm);
		if(willBeEditedAudio.getBinaryUrl() == null){
			initAudioUploadTool();
		}
		
	}
	
	public void selectWillBeDeletedAudio(Audio audio){
		willBeDeletedAudio = audio;
	}
	
	public void deleteAudio(){
		
	}
	
	private void initAudioUploadTool(){
		audioUploadTool = new AudioUploadTool(willBeEditedAudio, new AudioUploadCallbacks() {
			@Override
			public void finishedSusccessfully(Audio audio) {
				contentService.mergeAudio(audio);
				willBeEditedAudio = null;
			}
		});
		
		springBeanUtils.autowireBean(audioUploadTool);
	}
	
	@Override
	public String getDiscriminator() {
		return "audioOperations";
	}

	public Audio getWillBeCreatedAudio() {
		return willBeCreatedAudio;
	}

	public void setWillBeCreatedAudio(Audio willBeCreatedAudio) {
		this.willBeCreatedAudio = willBeCreatedAudio;
	}

	public Audio getWillBeEditedAudio() {
		return willBeEditedAudio;
	}

	public void setWillBeEditedAudio(Audio willBeEditedAudio) {
		this.willBeEditedAudio = willBeEditedAudio;
	}

	public Audio getWillBeDeletedAudio() {
		return willBeDeletedAudio;
	}

	public void setWillBeDeletedAudio(Audio willBeDeletedAudio) {
		this.willBeDeletedAudio = willBeDeletedAudio;
	}

	public List<Audio> getAllAudios() {
		return allAudios;
	}

	public void setAllAudios(List<Audio> allAudios) {
		this.allAudios = allAudios;
	}

	public HtmlForm getEditAudioForm() {
		return editAudioForm;
	}

	public void setEditAudioForm(HtmlForm editAudioForm) {
		this.editAudioForm = editAudioForm;
	}

	public AudioUploadTool getAudioUploadTool() {
		return audioUploadTool;
	}

	public void setAudioUploadTool(AudioUploadTool audioUploadTool) {
		this.audioUploadTool = audioUploadTool;
	}
	
	
}
