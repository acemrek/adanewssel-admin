package com.ac.newsadmin.ui.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlForm;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ac.common.utility.FacesUtils;
import com.ac.newsadmin.model.entity.PortalAdmin;
import com.ac.newsadmin.model.entity.Site;

@SuppressWarnings("serial")
@Component("siteOperationsBean")
@Scope("view")
public class SiteOperationsController extends AbstractOperationController implements Serializable{

	private static final Logger logger = LoggerFactory.getLogger(SiteOperationsController.class);
	
	private static final String ERROR_KEY_SITE = "site.error";
	private static final String ERROR_KEY_SITE_SAME_DOMAIN_NAME = "site.error.sameDomainName";
	
	private static final String COMPONENT_ID_CREATESITE_DOMAIN_NAME = "createSiteForm:domainName";
	private static final String COMPONENT_ID_EDITSITE_DOMAIN_NAME = "editSiteTabs:editSiteGeneralInformationForm:domainName";
																	 
	
	private static final String SITE_OPERATION_CREATE_SITE = "createSite";
	private static final String SITE_OPERATION_EDIT_SITE = "editSite";

	private List<Site> allSites = null;
	private Site willBeCreatedSite = null;
	private Site willBeEditedSite = null;
	private Site willBeDeletedSite = null;
	private List<PortalAdmin> willBeAddedPortalAdmins = new ArrayList<PortalAdmin>();
	
	private HtmlForm editSiteForm;
	
	@PostConstruct
	public void init(){
		showEditSiteForm();
	}
	
	//create new site
	public void showCreateSiteForm(){
		if(willBeCreatedSite == null){
			willBeCreatedSite = new Site();
		}
		setSelectedOperation(SITE_OPERATION_CREATE_SITE);
	}
	
	public void createNewSite(){
		Site siteFromDb = siteService.getSiteByDomainName(willBeCreatedSite.getDomainName());
		if(siteFromDb != null){
			FacesUtils.addError(ERROR_KEY_SITE, ERROR_KEY_SITE_SAME_DOMAIN_NAME, COMPONENT_ID_CREATESITE_DOMAIN_NAME, new Object[]{willBeCreatedSite.getDomainName()});
			return;
		}
		siteService.saveNewSite(willBeCreatedSite);
		
		logger.info("New site created with domain name {}", willBeCreatedSite.getDomainName());
		
		willBeCreatedSite = null;
		this.showEditSiteForm();
	}
	
	//list and edit site
	public void showEditSiteForm(){
		allSites = siteService.getAllSites();
		willBeEditedSite = null;
		willBeAddedPortalAdmins.clear();
		setSelectedOperation(SITE_OPERATION_EDIT_SITE);
	}
	
	public void selectWillBeEditedSite(long siteId){
		willBeEditedSite = siteService.getSiteById(siteId);
		FacesUtils.resetForm(editSiteForm);
	}
	
	public void editSite(){
		Site siteFromDb = siteService.getSiteByDomainName(willBeEditedSite.getDomainName());
		if(siteFromDb != null && siteFromDb.getId() != willBeEditedSite.getId()){
			FacesUtils.addError(ERROR_KEY_SITE, ERROR_KEY_SITE_SAME_DOMAIN_NAME, COMPONENT_ID_EDITSITE_DOMAIN_NAME, new Object[]{willBeEditedSite.getDomainName()});
			return;			
		}
		siteService.mergeSite(willBeEditedSite);
		willBeEditedSite = null;
		showEditSiteForm();
	}
	
	
	public void removeAdminFromSite(Site site, PortalAdmin admin){
		site.getAdmins().remove(admin);
		siteService.updateSite(site);
	}
	
	public List<PortalAdmin> queryAdminAutoComplete(String queryTxt){
		List<PortalAdmin> allAdmins = userService.getAllPortalAdmins();
		List<PortalAdmin> admins = new ArrayList<PortalAdmin>();
		for(PortalAdmin admin : allAdmins){
			if(StringUtils.startsWithIgnoreCase(admin.getFirstname(), queryTxt)){
				admins.add(admin);
			}
		}
		return admins;
	}
	
	public void addPortalAdminsToSite(){
		for(PortalAdmin admin :  willBeAddedPortalAdmins){
			if(willBeEditedSite.getAdmins().contains(admin) == false){
				willBeEditedSite.getAdmins().add(admin);
			}
		}
		
		siteService.mergeSite(willBeEditedSite);
		allSites = siteService.getAllSites();
		willBeAddedPortalAdmins.clear();
	}
	
	public void selectWillBeDeletedSite(Site willBeDeletedSite) {
		this.willBeDeletedSite = willBeDeletedSite;
	}
	
	public void deleteSite(){
		siteService.deleteSite(willBeDeletedSite);
		showEditSiteForm();		
	}
	@Override
	public String getDiscriminator() {
		return "siteOperations";
	}

	public List<Site> getAllSites() {
		return allSites;
	}

	public List<PortalAdmin> getWillBeAddedPortalAdmins() {
		return willBeAddedPortalAdmins;
	}

	public Site getWillBeCreatedSite() {
		return willBeCreatedSite;
	}

	public Site getWillBeEditedSite() {
		return willBeEditedSite;
	}

	public Site getWillBeDeletedSite() {
		return willBeDeletedSite;
	}

	public void setWillBeAddedPortalAdmins(List<PortalAdmin> willBeAddedPortalAdmins) {
		if(willBeAddedPortalAdmins != null){
			this.willBeAddedPortalAdmins = willBeAddedPortalAdmins;	
		}
		else{
			this.willBeAddedPortalAdmins.clear();
		}
	}

	public HtmlForm getEditSiteForm() {
		return editSiteForm;
	}

	public void setEditSiteForm(HtmlForm editSiteForm) {
		this.editSiteForm = editSiteForm;
	}

}
