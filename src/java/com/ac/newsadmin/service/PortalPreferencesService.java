package com.ac.newsadmin.service;

import com.ac.newsadmin.enums.PhotoSize;
import com.ac.newsadmin.model.entity.Site;
import com.ac.newsadmin.model.transfer.ImageDimension;

public interface PortalPreferencesService {
	ImageDimension getColmnistiImageSize(Site site, PhotoSize photoSize);
	void addNewSitePreference(Site site, String name, String value);
	Long getColumnistDefaultMalePhotoId(Site site);
	Long getColumnistDefaultFemalePhotoId(Site site);
	ImageDimension getGaleryPhotoImageSize(Site site, PhotoSize photoSize, int originalWidth, int originalHeight);
	ImageDimension getVideoThumbnailImageSize(Site site, PhotoSize photoSize);
	ImageDimension getNewsPhotoImageSize(Site site, PhotoSize photoSize, int originalWidth, int originalHeight);
}
