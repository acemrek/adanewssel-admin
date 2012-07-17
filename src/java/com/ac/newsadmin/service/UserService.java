package com.ac.newsadmin.service;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.jets3t.service.ServiceException;

import com.ac.newsadmin.GlobalConstants;
import com.ac.newsadmin.model.entity.Columnist;
import com.ac.newsadmin.model.entity.PortalAdmin;
import com.ac.newsadmin.model.entity.Site;

public interface UserService {
	@RolesAllowed({GlobalConstants.ROLE_SUPER_ADMIN})
	void saveNewPortalAdmin(PortalAdmin portalAdmin);
	
	@RolesAllowed({GlobalConstants.ROLE_SUPER_ADMIN})
	void mergePortalAdmin(PortalAdmin portalAdmin);
	
	@RolesAllowed({GlobalConstants.ROLE_SUPER_ADMIN})
	void updatePortalAdmin(PortalAdmin portalAdmin);
	
	PortalAdmin getPortalAdminById(long id);
	
	List<PortalAdmin> getAllPortalAdmins();
	
	@RolesAllowed({GlobalConstants.ROLE_SUPER_ADMIN})
	void deletePortalAdmin(PortalAdmin portalAdmin);
	
	PortalAdmin getPortalAdminByEmail(String email);
	PortalAdmin getPortalAdminByUsername(String username);

	void saveNewColumnist(Columnist columnist, Site site);
	void updateColumnist(Columnist columnist);
	void mergeColumnist(Columnist columnist);
	List<Columnist> getAllColumnists(Site site);
	void deleteColumnist(Columnist columnist) throws ServiceException;
	Columnist getColumnistById(long id);
	Columnist getColumnistByUsername(String username, Site site);
	Columnist getColumnistByEmail(String email, Site site);
}
