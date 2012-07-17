package com.ac.newsadmin.ui.controller;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.ac.common.integration.spring.SpringBeanUtils;
import com.ac.common.utility.FacesUtils;
import com.ac.newsadmin.service.ContentService;
import com.ac.newsadmin.service.PhotoService;
import com.ac.newsadmin.service.PortalPreferencesService;
import com.ac.newsadmin.service.SiteService;
import com.ac.newsadmin.service.UserService;
import com.ac.newsadmin.ui.SessionData;


@SuppressWarnings("serial")
public abstract class AbstractOperationController implements Serializable{

	@Resource protected UserService userService;
	@Resource protected SiteService siteService;
	@Resource protected PhotoService photoService;
	@Resource protected ContentService contentService;
	@Resource protected PortalPreferencesService portalPreferencesService;
	@Resource protected SessionData sessionData;
	@Resource protected SpringBeanUtils springBeanUtils; 
	
	private String selectedOperation;
	
	public abstract String  getDiscriminator();

	public String getSelectedOperation() {
		return selectedOperation;
	}

	public void setSelectedOperation(String selectedOperation) {
		this.selectedOperation = selectedOperation;
	}

	public String getCssClass(String operation){
		return selectedOperation != null && selectedOperation.equalsIgnoreCase(operation) ? "ui-state-active" : StringUtils.EMPTY;
	}
	
	public String getOperationHeader(){
		return StringUtils.isNotBlank(selectedOperation) ? FacesUtils.getI18nMessage(getDiscriminator() + "." + selectedOperation) : StringUtils.EMPTY;
	}

}
