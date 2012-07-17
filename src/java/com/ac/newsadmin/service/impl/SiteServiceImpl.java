package com.ac.newsadmin.service.impl;

import static com.ac.newsadmin.model.entity.PortalPreference.COLUMNIST_DEFAULT_FEMALE_PHOTO_ID;
import static com.ac.newsadmin.model.entity.PortalPreference.COLUMNIST_DEFAULT_MALE_PHOTO_ID;
import static com.ac.newsadmin.model.entity.PortalPreference.COLUMNIST_IMG_LARGE_WIDTH;
import static com.ac.newsadmin.model.entity.PortalPreference.COLUMNIST_IMG_MEDIUM_WIDTH;
import static com.ac.newsadmin.model.entity.PortalPreference.COLUMNIST_IMG_SMALL_WIDTH;
import static com.ac.newsadmin.model.entity.PortalPreference.COLUMNIST_IMG_THUMB_WIDTH;
import static com.ac.newsadmin.model.entity.PortalPreference.GALERY_PHOTO_LARGE_WIDTH;
import static com.ac.newsadmin.model.entity.PortalPreference.GALERY_PHOTO_MEDIUM_WIDTH;
import static com.ac.newsadmin.model.entity.PortalPreference.GALERY_PHOTO_SMALL_WIDTH;
import static com.ac.newsadmin.model.entity.PortalPreference.GALERY_PHOTO_THUMB_WIDTH;
import static com.ac.newsadmin.model.entity.PortalPreference.NEWS_INLINE_PHOTO_LARGE_WIDTH;
import static com.ac.newsadmin.model.entity.PortalPreference.NEWS_INLINE_PHOTO_MEDIUM_WIDTH;
import static com.ac.newsadmin.model.entity.PortalPreference.NEWS_INLINE_PHOTO_SMALL_WIDTH;
import static com.ac.newsadmin.model.entity.PortalPreference.NEWS_INLINE_PHOTO_THUMB_WIDTH;
import static com.ac.newsadmin.model.entity.PortalPreference.VIDEO_THUMBNAIL_LARGE_WIDTH;
import static com.ac.newsadmin.model.entity.PortalPreference.VIDEO_THUMBNAIL_MEDIUM_WIDTH;
import static com.ac.newsadmin.model.entity.PortalPreference.VIDEO_THUMBNAIL_SMALL_WIDTH;
import static com.ac.newsadmin.model.entity.PortalPreference.VIDEO_THUMBNAIL_THUMB_WIDTH;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ac.newsadmin.dao.SiteDao;
import com.ac.newsadmin.model.entity.Site;
import com.ac.newsadmin.service.ContentService;
import com.ac.newsadmin.service.PortalPreferencesService;
import com.ac.newsadmin.service.SiteService;

@Service("siteService")
public class SiteServiceImpl implements SiteService{
	
	@Resource private SiteDao siteDao;
	@Resource private PortalPreferencesService portalPreferencesService;
	@Resource private ContentService contentService;
	
	@Override
	@Transactional
	public void saveNewSite(Site site) {
		siteDao.saveOrUpdateSite(site);
		
		portalPreferencesService.addNewSitePreference(site, COLUMNIST_IMG_THUMB_WIDTH, "80");
		portalPreferencesService.addNewSitePreference(site, COLUMNIST_IMG_SMALL_WIDTH, "160");
		portalPreferencesService.addNewSitePreference(site, COLUMNIST_IMG_MEDIUM_WIDTH, "240");
		portalPreferencesService.addNewSitePreference(site, COLUMNIST_IMG_LARGE_WIDTH, "320");
		portalPreferencesService.addNewSitePreference(site, COLUMNIST_DEFAULT_FEMALE_PHOTO_ID, "1");
		portalPreferencesService.addNewSitePreference(site, COLUMNIST_DEFAULT_MALE_PHOTO_ID, "2");
		portalPreferencesService.addNewSitePreference(site, GALERY_PHOTO_THUMB_WIDTH, "130");
		portalPreferencesService.addNewSitePreference(site, GALERY_PHOTO_SMALL_WIDTH, "220");
		portalPreferencesService.addNewSitePreference(site, GALERY_PHOTO_MEDIUM_WIDTH, "340");
		portalPreferencesService.addNewSitePreference(site, GALERY_PHOTO_LARGE_WIDTH, "480");
		portalPreferencesService.addNewSitePreference(site, VIDEO_THUMBNAIL_THUMB_WIDTH, "200");
		portalPreferencesService.addNewSitePreference(site, VIDEO_THUMBNAIL_SMALL_WIDTH, "400");
		portalPreferencesService.addNewSitePreference(site, VIDEO_THUMBNAIL_MEDIUM_WIDTH, "600");
		portalPreferencesService.addNewSitePreference(site, VIDEO_THUMBNAIL_LARGE_WIDTH, "800");
		portalPreferencesService.addNewSitePreference(site, NEWS_INLINE_PHOTO_THUMB_WIDTH, "130");
		portalPreferencesService.addNewSitePreference(site, NEWS_INLINE_PHOTO_SMALL_WIDTH, "220");
		portalPreferencesService.addNewSitePreference(site, NEWS_INLINE_PHOTO_MEDIUM_WIDTH, "340");
		portalPreferencesService.addNewSitePreference(site, NEWS_INLINE_PHOTO_LARGE_WIDTH, "480");

		contentService.addCategory("politics", "category.default.politics", site);
		contentService.addCategory("economy", "category.default.economy", site);
		contentService.addCategory("magazine", "category.default.magazine", site);
		contentService.addCategory("sport", "category.default.sport", site);
	}
	
	@Override
	@Transactional
	public void updateSite(Site site) {
		siteDao.saveOrUpdateSite(site);
	}
	
	@Override
	@Transactional
	public void mergeSite(Site site) {
		siteDao.mergeSite(site);
	}

	@Override
	@Transactional
	public void deleteSite(Site site){
		//TODO delete all its foreign keys
		//siteDao.deleteSite(site);
	}

	@Override
	public List<Site> getAllSites() {
		return siteDao.findAllSites();
	}

	@Override
	public Site getSiteById(long id) {
		return siteDao.findSiteById(id);
	}

	@Override
	public Site getSiteByDomainName(String domainName) {
		return siteDao.findSiteByDomainName(domainName);
	}

}
