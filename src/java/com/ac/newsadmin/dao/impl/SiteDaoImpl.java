package com.ac.newsadmin.dao.impl;

import java.io.Serializable;
import java.util.List;

import com.ac.common.dao.BaseDaoImpl;
import com.ac.newsadmin.dao.SiteDao;
import com.ac.newsadmin.model.entity.PortalPreference;
import com.ac.newsadmin.model.entity.Site;

@SuppressWarnings("serial")
public class SiteDaoImpl extends BaseDaoImpl implements SiteDao, Serializable{

	public void saveOrUpdateSite(Site site) {
		super.saveOrUpdate(site);
	}
	
	public void mergeSite(Site site){
		getSession().merge(site);
	}

	public Site findSiteByDomainName(String domainName) {
		return super.findEntityByPropertyUnique(Site.class, "domainName", domainName);
	}
	
	public Site findSiteById(Long id){
		return (Site)super.findById(Site.class, id);
	}
	
	public List<Site> findAllSites(){
		return super.findAllEntities(Site.class, true);
	}
	
	public void deleteSite(Site site){
		super.delete(site);
	}
	
	public void saveOrUpdatePortalPreference(PortalPreference portalPreference) {
		super.saveOrUpdate(portalPreference);
	}
	
	public PortalPreference findPreferance(Site site, String key) {
		return findEntityByPropertyUnique(PortalPreference.class, new String[]{"name","site"}, new Object[]{key, site});
	}
}
