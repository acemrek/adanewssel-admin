package com.ac.newsadmin.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ac.newsadmin.dao.SiteDao;
import com.ac.newsadmin.enums.PhotoSize;
import com.ac.newsadmin.model.entity.PortalPreference;
import com.ac.newsadmin.model.entity.Site;
import com.ac.newsadmin.model.transfer.ImageDimension;
import com.ac.newsadmin.service.PortalPreferencesService;

@SuppressWarnings("serial")
@Service("portalPreferencesService")
public class PortalPreferencesServiceImpl implements PortalPreferencesService, Serializable{
	
	@Resource private SiteDao siteDao;
	
	public ImageDimension getColmnistiImageSize(Site site, PhotoSize photoSize){
		
		String widthKey  = null;
		
		switch (photoSize) {
			case THUMBNAIL:
				widthKey  = PortalPreference.COLUMNIST_IMG_THUMB_WIDTH;
				break;
				
			case SMALL:
				widthKey  = PortalPreference.COLUMNIST_IMG_SMALL_WIDTH;
				break;
				
			case MEDIUM:
				widthKey  = PortalPreference.COLUMNIST_IMG_MEDIUM_WIDTH;				
				break;
				
			case LARGE:
				widthKey  = PortalPreference.COLUMNIST_IMG_LARGE_WIDTH;
				break;
		}
		
		PortalPreference widthPreference  = siteDao.findPreferance(site, widthKey);
		
		return new ImageDimension(Integer.valueOf(widthPreference.getValue()), Integer.valueOf(widthPreference.getValue()));
	}
	
	public ImageDimension getGaleryPhotoImageSize(Site site, PhotoSize photoSize, int originalWidth, int originalHeight){
		String desiredPhotoWidthStr = null;
		
		switch (photoSize) {
			case THUMBNAIL:
				desiredPhotoWidthStr = siteDao.findPreferance(site, PortalPreference.GALERY_PHOTO_THUMB_WIDTH).getValue();
				break;
			case SMALL:
				desiredPhotoWidthStr = siteDao.findPreferance(site, PortalPreference.GALERY_PHOTO_SMALL_WIDTH).getValue();
				break;
			case MEDIUM:
				desiredPhotoWidthStr = siteDao.findPreferance(site, PortalPreference.GALERY_PHOTO_MEDIUM_WIDTH).getValue();
				break;
			case LARGE:
				desiredPhotoWidthStr = siteDao.findPreferance(site, PortalPreference.GALERY_PHOTO_LARGE_WIDTH).getValue();
				break;
		}
		
		return getDimensionsByWidth(originalWidth, originalHeight, NumberUtils.toInt(desiredPhotoWidthStr));
	}

	public ImageDimension getNewsPhotoImageSize(Site site, PhotoSize photoSize, int originalWidth, int originalHeight){
		String desiredPhotoWidthStr = null;
		
		switch (photoSize) {
			case THUMBNAIL:
				desiredPhotoWidthStr = siteDao.findPreferance(site, PortalPreference.NEWS_INLINE_PHOTO_THUMB_WIDTH).getValue();
				break;
			case SMALL:
				desiredPhotoWidthStr = siteDao.findPreferance(site, PortalPreference.NEWS_INLINE_PHOTO_SMALL_WIDTH).getValue();
				break;
			case MEDIUM:
				desiredPhotoWidthStr = siteDao.findPreferance(site, PortalPreference.NEWS_INLINE_PHOTO_MEDIUM_WIDTH).getValue();
				break;
			case LARGE:
				desiredPhotoWidthStr = siteDao.findPreferance(site, PortalPreference.NEWS_INLINE_PHOTO_LARGE_WIDTH).getValue();
				break;
		}
		
		return getDimensionsByWidth(originalWidth, originalHeight, NumberUtils.toInt(desiredPhotoWidthStr));
	}
	
	private ImageDimension getDimensionsByWidth(int originalWidth, int originalHeight, int desiredPhotoWidth){
		int deliverPhotoWidth = 0;
		int deliverPhotoHeight = 0;
		
		if(deliverPhotoWidth < originalWidth){
			deliverPhotoWidth = desiredPhotoWidth;
			deliverPhotoHeight = (int)((deliverPhotoWidth * originalHeight) / (double) originalWidth);
		}
		else{
			deliverPhotoWidth = originalWidth;
			deliverPhotoHeight = originalHeight;
		}
		
		return new ImageDimension(deliverPhotoWidth, deliverPhotoHeight);
	}

	public ImageDimension getVideoThumbnailImageSize(Site site, PhotoSize photoSize){
		Integer desiredPhotoWidth = null;
		
		switch (photoSize) {
			case THUMBNAIL:
				desiredPhotoWidth = getPreferenceValueAsInt(site, PortalPreference.GALERY_PHOTO_THUMB_WIDTH);
				break;
			case SMALL:
				desiredPhotoWidth = getPreferenceValueAsInt(site, PortalPreference.GALERY_PHOTO_SMALL_WIDTH);
				break;
			case MEDIUM:
				desiredPhotoWidth = getPreferenceValueAsInt(site, PortalPreference.GALERY_PHOTO_MEDIUM_WIDTH);
				break;
			case LARGE:
				desiredPhotoWidth = getPreferenceValueAsInt(site, PortalPreference.GALERY_PHOTO_LARGE_WIDTH);
				break;
		}
		return new ImageDimension(desiredPhotoWidth, (desiredPhotoWidth * 4) / 3);
	}
	
	@Override
	@Transactional
	public void addNewSitePreference(Site site, String name, String value) {
		PortalPreference portalPreference = new PortalPreference();
		portalPreference.setSite(site);
		portalPreference.setName(name);
		portalPreference.setValue(value);
		
		siteDao.saveOrUpdatePortalPreference(portalPreference);
	}
	

	
	@Override
	public Long getColumnistDefaultMalePhotoId(Site site) {
		return getPreferenceValueAsLong(site, PortalPreference.COLUMNIST_DEFAULT_MALE_PHOTO_ID);
	}
	
	@Override
	public Long getColumnistDefaultFemalePhotoId(Site site) {
		return getPreferenceValueAsLong(site, PortalPreference.COLUMNIST_DEFAULT_FEMALE_PHOTO_ID);
	}	
	
//	private String getPreferenceValueAsString(Site site, String key){
//		PortalPreference pref = siteDao.findPreferance(site, key);
//		return pref != null ? pref.getValue() : null;	
//	}
	
	public Long getPreferenceValueAsLong(Site site, String key) {
		PortalPreference pref = siteDao.findPreferance(site, key);
		return pref != null ? Long.valueOf(pref.getValue()) : null;
	}
	
	public Integer getPreferenceValueAsInt(Site site, String key) {
		PortalPreference pref = siteDao.findPreferance(site, key);
		return pref != null ? Integer.valueOf(pref.getValue()) : null;
	}
}
