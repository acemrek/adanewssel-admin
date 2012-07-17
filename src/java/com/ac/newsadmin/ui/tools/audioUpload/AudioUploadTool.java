package com.ac.newsadmin.ui.tools.audioUpload;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.ac.newsadmin.model.entity.Audio;
import com.ac.newsadmin.model.transfer.WillBeStoredAudio;
import com.ac.newsadmin.service.BinaryStorageService;
import com.ac.newsadmin.ui.SessionData;

@SuppressWarnings("serial")
public class AudioUploadTool implements Serializable{
	
	@Resource private SessionData sessionData;
	@Resource private BinaryStorageService binaryStorageService;
	
	private AudioUploadCallbacks callbacks;
	private boolean finishedSuccessfully;
	private Audio audio;

	public AudioUploadTool(Audio audio, AudioUploadCallbacks callbacks) {
		super();
		this.audio = audio;
		this.callbacks = callbacks;
	}
	
	public void handleAudioUpload(FileUploadEvent event){
		try {
			UploadedFile uploadedFile = event.getFile();
			
			String binaryUrl = "audio/" + System.currentTimeMillis() + '.' + FilenameUtils.getExtension(uploadedFile.getFileName());
			
			WillBeStoredAudio willBeStoredAudio = new WillBeStoredAudio();
			
			willBeStoredAudio.setFullStorePath(binaryUrl);
			willBeStoredAudio.setContentType(uploadedFile.getContentType());
			willBeStoredAudio.setData(uploadedFile.getContents());
			willBeStoredAudio.setSize(uploadedFile.getSize());
			binaryStorageService.storeBinary(sessionData.getCurrentSite(), willBeStoredAudio);
			
			audio.setBinaryUrl(binaryUrl);
			callbacks.finishedSusccessfully(audio);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SessionData getSessionData() {
		return sessionData;
	}

	public void setSessionData(SessionData sessionData) {
		this.sessionData = sessionData;
	}

	public BinaryStorageService getBinaryStorageService() {
		return binaryStorageService;
	}

	public void setBinaryStorageService(BinaryStorageService binaryStorageService) {
		this.binaryStorageService = binaryStorageService;
	}

	public AudioUploadCallbacks getCallbacks() {
		return callbacks;
	}

	public void setCallbacks(AudioUploadCallbacks callbacks) {
		this.callbacks = callbacks;
	}

	public boolean isFinishedSuccessfully() {
		return finishedSuccessfully;
	}

	public void setFinishedSuccessfully(boolean finishedSuccessfully) {
		this.finishedSuccessfully = finishedSuccessfully;
	}

	public Audio getAudio() {
		return audio;
	}

	public void setAudio(Audio audio) {
		this.audio = audio;
	}
	
	
}
