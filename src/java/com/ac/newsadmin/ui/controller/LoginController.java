package com.ac.newsadmin.ui.controller;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ac.common.utility.FacesUtils;
import com.ac.newsadmin.model.entity.PortalAdmin;
import com.ac.newsadmin.service.AuthenticateService;
import com.ac.newsadmin.service.UserService;
import com.ac.newsadmin.ui.GeneralConstants;
import com.ac.newsadmin.ui.SessionData;

@SuppressWarnings("serial")
@Component("loginBean")
@Scope("view")
public class LoginController implements Serializable{
	
	private static final String ADMIN_AUTH_FAILED = "portalAdminAuth.authFailed";
	
	@Resource private AuthenticateService adminAuthenticateService;
	@Resource private UserService userService;
	@Resource private SessionData sessionData;
	
	private String username = "acemrek";
	private String password = "8861";
	
	public String login(){
		boolean authenticated = adminAuthenticateService.login(username, password);
		password = null;
		
		if(authenticated == false){
			FacesUtils.addError(null, ADMIN_AUTH_FAILED, "loginForm:submit");
			return StringUtils.EMPTY;
		}
		else{
			PortalAdmin portalAdmin = userService.getPortalAdminByUsername(username);
			sessionData.setCurrentUser(portalAdmin);
			sessionData.setCurrentSite(portalAdmin.getSites().get(0));
			
			return SessionData.COLUMNIST_OPERATIONS_MAIN_PAGE + GeneralConstants.FACES_REDIRECT;
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
