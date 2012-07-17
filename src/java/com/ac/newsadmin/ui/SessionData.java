package com.ac.newsadmin.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.component.menuitem.MenuItem;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ac.common.utility.FacesUtils;
import com.ac.newsadmin.GlobalConstants;
import com.ac.newsadmin.model.entity.PortalAdmin;
import com.ac.newsadmin.model.entity.Site;

@SuppressWarnings("serial")
@Component("sessionData")
@Scope("session")
public class SessionData implements Serializable{
	
	public static final String SITE_OPERATIONS_MAIN_PAGE = "/ui/operations/site/main.xhtml";
	public static final String PORTAL_ADMIN_OPERATIONS_MAIN_PAGE = "/ui/operations/portalAdmin/main.xhtml";
	public static final String COLUMNIST_OPERATIONS_MAIN_PAGE = "/ui/operations/columnist/main.xhtml";
	public static final String COLUMN_OPERATIONS_MAIN_PAGE = "/ui/operations/column/main.xhtml";
	public static final String PHOTO_GALERY_OPERATIONS_MAIN_PAGE = "/ui/operations/photoGalery/main.xhtml";
	public static final String VIDEO_OPERATIONS_MAIN_PAGE = "/ui/operations/video/main.xhtml";
	public static final String AUDIO_OPERATIONS_MAIN_PAGE = "/ui/operations/audio/main.xhtml";
	public static final String NEWS_OPERATIONS_MAIN_PAGE = "/ui/operations/news/main.xhtml";
	public static final String CONFIG_OPERATIONS_MAIN_PAGE = "/ui/operations/config/main.xhtml";

	private static final String OPERATIONS_TABS_COLUMNIST = "operations.tabs.columnists";
	private static final String OPERATIONS_TABS_COLUMNS = "operations.tabs.columns";
	private static final String OPERATIONS_TABS_PHOTO_GALERY = "operations.tabs.photoGalery";
	private static final String OPERATIONS_TABS_VIDEO = "operations.tabs.video";
	private static final String OPERATIONS_TABS_AUDIO = "operations.tabs.audio";
	private static final String OPERATIONS_TABS_NEWS = "operations.tabs.news";
	private static final String OPERATIONS_TABS_CONFIG = "operations.tabs.config";
	private static final String OPERATIONS_TABS_WEBSITES = "operations.tabs.websites";
	private static final String OPERATIONS_TABS_PORTAL_ADMIN = "operations.tabs.portalAdmin";
	
	private static final String ICON_WRITER = "ui-icon-writer";
	private static final String ICON_COLUMN = "ui-icon-column";
	private static final String ICON_PHOTO = "ui-icon-photo";
	private static final String ICON_SITE_VIDEO = "ui-icon-site-video";
	private static final String ICON_AUDIO = "ui-icon-audio";
	private static final String ICON_NEWS = "ui-icon-news";
	private static final String ICON_CONFIG = "ui-icon-wrench";
	private static final String ICON_WEBSITES = "ui-icon-websites";
	private static final String ICON_PORTAL_ADMIN = "ui-icon-portalAdmin";
	
	private PortalAdmin currentUser;
	
	public boolean isSuperAdmin(){
		for(GrantedAuthority authority : getAuth().getAuthorities()){
			if(authority.getAuthority().equalsIgnoreCase(GlobalConstants.ROLE_SUPER_ADMIN)){
				return true;
			}
		}
		return false;
	}
	
	private Authentication getAuth(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth;
	}

	public PortalAdmin getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(PortalAdmin currentUser) {
		this.currentUser = currentUser;
	}

	//site info
	private Site currentSite;

	public Site getCurrentSite() {
		return currentSite;
	}

	public void setCurrentSite(Site currentSite) {
		this.currentSite = currentSite;
	}
	
	//general tabs info
	private List<MenuItem> operationTabs = null;
	
	public List<MenuItem> getOperationTabs() {
		
		if (operationTabs == null) {
			
			operationTabs = new ArrayList<MenuItem>();
			
			MenuItem columnistOperations = new MenuItem();
			columnistOperations.setUrl(COLUMNIST_OPERATIONS_MAIN_PAGE);
			columnistOperations.setIcon(ICON_WRITER);
			columnistOperations.setValue(FacesUtils.getI18nMessage(OPERATIONS_TABS_COLUMNIST));
			operationTabs.add(columnistOperations);

			MenuItem columnOperations = new MenuItem();
			columnOperations.setUrl(COLUMN_OPERATIONS_MAIN_PAGE);
			columnOperations.setIcon(ICON_COLUMN);
			columnOperations.setValue(FacesUtils.getI18nMessage(OPERATIONS_TABS_COLUMNS));
			operationTabs.add(columnOperations);

			MenuItem photoGaleryOperations = new MenuItem();
			photoGaleryOperations.setUrl(PHOTO_GALERY_OPERATIONS_MAIN_PAGE);
			photoGaleryOperations.setIcon(ICON_PHOTO);
			photoGaleryOperations.setValue(FacesUtils.getI18nMessage(OPERATIONS_TABS_PHOTO_GALERY));
			operationTabs.add(photoGaleryOperations);

			MenuItem videoOperations = new MenuItem();
			videoOperations.setUrl(VIDEO_OPERATIONS_MAIN_PAGE);
			videoOperations.setIcon(ICON_SITE_VIDEO);
			videoOperations.setValue(FacesUtils.getI18nMessage(OPERATIONS_TABS_VIDEO));
			operationTabs.add(videoOperations);

			MenuItem audioOperations = new MenuItem();
			audioOperations.setUrl(AUDIO_OPERATIONS_MAIN_PAGE);
			audioOperations.setIcon(ICON_AUDIO);
			audioOperations.setValue(FacesUtils.getI18nMessage(OPERATIONS_TABS_AUDIO));
			operationTabs.add(audioOperations);
			
			MenuItem newsOperations = new MenuItem();
			newsOperations.setUrl(NEWS_OPERATIONS_MAIN_PAGE);
			newsOperations.setIcon(ICON_NEWS);
			newsOperations.setValue(FacesUtils.getI18nMessage(OPERATIONS_TABS_NEWS));
			operationTabs.add(newsOperations);

			MenuItem configOperations = new MenuItem();
			configOperations.setUrl(CONFIG_OPERATIONS_MAIN_PAGE);
			configOperations.setIcon(ICON_CONFIG);
			configOperations.setValue(FacesUtils.getI18nMessage(OPERATIONS_TABS_CONFIG));
			operationTabs.add(configOperations);

			if(this.isSuperAdmin()){
				MenuItem mSiteOperations = new MenuItem();
				mSiteOperations.setUrl(SITE_OPERATIONS_MAIN_PAGE);
				mSiteOperations.setIcon(ICON_WEBSITES);
				mSiteOperations.setValue(FacesUtils.getI18nMessage(OPERATIONS_TABS_WEBSITES));
				operationTabs.add(mSiteOperations);
				
				MenuItem portalAdminOperations = new MenuItem();
				portalAdminOperations.setUrl(PORTAL_ADMIN_OPERATIONS_MAIN_PAGE);
				portalAdminOperations.setIcon(ICON_PORTAL_ADMIN);
				portalAdminOperations.setValue(FacesUtils.getI18nMessage(OPERATIONS_TABS_PORTAL_ADMIN));
				operationTabs.add(portalAdminOperations);
			}
			
		}
		
		return operationTabs;
	}

	public void setOperationTabs(List<MenuItem> operationTabs) {
		this.operationTabs = operationTabs;
	}

}