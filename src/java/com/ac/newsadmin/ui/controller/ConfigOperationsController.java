package com.ac.newsadmin.ui.controller;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("configOperationsBean")
@Scope("view")
public class ConfigOperationsController extends AbstractOperationController implements Serializable{

	private static final long serialVersionUID = 1L;

	private static final String CONFIG_OPERATION_CHANGE_PREFERENCES = "changePreferences";
	
	public void showChangePreferencesForm(){
		setSelectedOperation(CONFIG_OPERATION_CHANGE_PREFERENCES);
	}
	
	@Override
	public String getDiscriminator() {
		return "configOperations";
	}

}
