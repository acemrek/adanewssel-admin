package com.ac.newsadmin.dao;

import java.util.List;

import com.ac.common.dao.BaseDao;
import com.ac.newsadmin.model.entity.PortalPreference;
import com.ac.newsadmin.model.entity.Site;

public interface SiteDao extends BaseDao{
	void saveOrUpdateSite(Site site);
	void mergeSite(Site site);
	Site findSiteByDomainName(String domainName);
	Site findSiteById(Long id);
	List<Site> findAllSites();
	void deleteSite(Site site);
	void saveOrUpdatePortalPreference(PortalPreference portalPreference);
	PortalPreference findPreferance(Site site, String key);
}
