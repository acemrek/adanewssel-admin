package com.ac.newsadmin.ui.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlForm;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ac.common.utility.FacesUtils;
import com.ac.common.utility.HashingUtils;
import com.ac.newsadmin.model.entity.PortalAdmin;
import com.ac.newsadmin.model.entity.Site;

@SuppressWarnings("serial")
@Component("portalAdminOperationsBean")
@Scope("view")
public class PortalAdminOperationsController extends AbstractOperationController implements Serializable{
	
	private static final String ERROR_KEY_PORTAL_ADMIN = "portalAdmin.error";
	private static final String ERROR_KEY_PORTAL_ADMIN_SAME_USERNAME = "portalAdmin.error.sameUsername";
	private static final String ERROR_KEY_PORTAL_ADMIN_SAME_EMAIL = "portalAdmin.error.sameEmail";
	
	private static final String COMPONENT_ID_CREATE_PORTAL_ADMIN_USERNAME = "createPortalAdminForm:username";
	private static final String COMPONENT_ID_CREATE_PORTAL_ADMIN_EMAIL = "createPortalAdminForm:email";
	private static final String COMPONENT_ID_EDIT_PORTAL_ADMIN_USERNAME = "editPortalAdminTabs:editPortalAdminGeneralInformationForm:username";
	private static final String COMPONENT_ID_EDIT_PORTAL_ADMIN_EMAIL = "editPortalAdminTabs:editPortalAdminGeneralInformationForm:email";
	
	private static final String SITE_OPERATION_CREATE_PORTAL_ADMIN = "createPortalAdmin";
	private static final String SITE_OPERATION_EDIT_PORTAL_ADMIN = "editPortalAdmin";
	
	private List<PortalAdmin> allPortalAdmins = null;
	private PortalAdmin willBeCreatedPortalAdmin = null;
	private PortalAdmin willBeEditedPortalAdmin = null;
	private PortalAdmin willBeDeletedPortalAdmin = null;
	
	private HtmlForm editPortalAdminForm;
	
	@PostConstruct
	public void init(){
		showEditPortalAdminForm();
	}
	
	@Override
	public String getDiscriminator() {
		return "portalAdminOperations";
	}
	
	//create new portal admin
	public void showCreatePortalAdminForm(){
		if(willBeCreatedPortalAdmin == null){
			willBeCreatedPortalAdmin = new PortalAdmin();
		}
		setSelectedOperation(SITE_OPERATION_CREATE_PORTAL_ADMIN);
	}
	
	public void createNewPortalAdmin(){
		PortalAdmin portalAdmin = userService.getPortalAdminByUsername(willBeCreatedPortalAdmin.getUsername());
		if(portalAdmin != null){
			FacesUtils.addError(ERROR_KEY_PORTAL_ADMIN, ERROR_KEY_PORTAL_ADMIN_SAME_USERNAME, COMPONENT_ID_CREATE_PORTAL_ADMIN_USERNAME, new Object[]{willBeCreatedPortalAdmin.getUsername()});
			return;
		}
		
		portalAdmin = userService.getPortalAdminByEmail(willBeCreatedPortalAdmin.getEmail());
		if(portalAdmin != null){
			FacesUtils.addError(ERROR_KEY_PORTAL_ADMIN, ERROR_KEY_PORTAL_ADMIN_SAME_EMAIL, COMPONENT_ID_CREATE_PORTAL_ADMIN_EMAIL, new Object[]{willBeCreatedPortalAdmin.getEmail()});
			return;
		}
		
		willBeCreatedPortalAdmin.setPassword(HashingUtils.getMD5Hash(willBeCreatedPortalAdmin.getPassword()));
		userService.saveNewPortalAdmin(willBeCreatedPortalAdmin);
		willBeCreatedPortalAdmin = null;
		this.showEditPortalAdminForm();
	}
	
	//list and edit portal admin
	public void showEditPortalAdminForm(){
		allPortalAdmins = userService.getAllPortalAdmins();
		willBeEditedPortalAdmin = null;
		setSelectedOperation(SITE_OPERATION_EDIT_PORTAL_ADMIN);
	}
	
	public void selectWillBeEditedPortalAdmin(long portalAdminId){
		willBeEditedPortalAdmin = userService.getPortalAdminById(portalAdminId);
		FacesUtils.resetForm(editPortalAdminForm);
	}
	
	public void editPortalAdmin(){
		PortalAdmin portalAdminByUsernameFromDb = userService.getPortalAdminByUsername(willBeEditedPortalAdmin.getUsername());
		if(portalAdminByUsernameFromDb != null && portalAdminByUsernameFromDb.getId() != willBeEditedPortalAdmin.getId()){
			FacesUtils.addError(ERROR_KEY_PORTAL_ADMIN, ERROR_KEY_PORTAL_ADMIN_SAME_USERNAME, COMPONENT_ID_EDIT_PORTAL_ADMIN_USERNAME, new Object[]{willBeEditedPortalAdmin.getUsername()});
			return;
		}
		
		PortalAdmin portalAdminByEmailFromDb = userService.getPortalAdminByEmail(willBeEditedPortalAdmin.getEmail());
		if(portalAdminByEmailFromDb != null && portalAdminByEmailFromDb.getId() != willBeEditedPortalAdmin.getId()){
			FacesUtils.addError(ERROR_KEY_PORTAL_ADMIN, ERROR_KEY_PORTAL_ADMIN_SAME_EMAIL, COMPONENT_ID_EDIT_PORTAL_ADMIN_EMAIL, new Object[]{willBeEditedPortalAdmin.getEmail()});
			return;
		}

		userService.mergePortalAdmin(willBeEditedPortalAdmin);
		willBeEditedPortalAdmin = null;
		showEditPortalAdminForm();
	}
	
	public void selectWillBeDeletedPortalAdmin(PortalAdmin admin) {
		this.willBeDeletedPortalAdmin = admin;
	}
	
	public void deletePortalAdmin(){
		userService.deletePortalAdmin(willBeDeletedPortalAdmin);
		showEditPortalAdminForm();		
	}
	
	public void removeSiteFromPortalAdmin(PortalAdmin portalAdmin, Site site){
		portalAdmin.getSites().remove(site);
		userService.mergePortalAdmin(portalAdmin);
	}
	public List<PortalAdmin> getAllPortalAdmins() {
		return allPortalAdmins;
	}

	public void setAllPortalAdmins(List<PortalAdmin> allPortalAdmins) {
		this.allPortalAdmins = allPortalAdmins;
	}

	public PortalAdmin getWillBeDeletedPortalAdmin() {
		return willBeDeletedPortalAdmin;
	}

	public PortalAdmin getWillBeCreatedPortalAdmin() {
		return willBeCreatedPortalAdmin;
	}

	public PortalAdmin getWillBeEditedPortalAdmin() {
		return willBeEditedPortalAdmin;
	}

	public HtmlForm getEditPortalAdminForm() {
		return editPortalAdminForm;
	}

	public void setEditPortalAdminForm(HtmlForm editPortalAdminForm) {
		this.editPortalAdminForm = editPortalAdminForm;
	}

}
