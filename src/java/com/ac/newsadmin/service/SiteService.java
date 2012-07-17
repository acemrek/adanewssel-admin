package com.ac.newsadmin.service;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import com.ac.newsadmin.GlobalConstants;
import com.ac.newsadmin.model.entity.Site;

public interface SiteService {
	
	@RolesAllowed({GlobalConstants.ROLE_SUPER_ADMIN})	
	void saveNewSite(Site site);
	
	@RolesAllowed({GlobalConstants.ROLE_SUPER_ADMIN})	
	void updateSite(Site site);
	
	@RolesAllowed({GlobalConstants.ROLE_SUPER_ADMIN})
	void mergeSite(Site site);

	@RolesAllowed({GlobalConstants.ROLE_SUPER_ADMIN})
	void deleteSite(Site site);
	
	List<Site> getAllSites();
	Site getSiteById(long id);
	Site getSiteByDomainName(String domainName);
}
