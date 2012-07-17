package com.ac.newsadmin.service;

import java.util.List;

import org.jets3t.service.ServiceException;

import com.ac.newsadmin.model.entity.Columnist;
import com.ac.newsadmin.model.entity.Photo;
import com.ac.newsadmin.model.entity.Site;
import com.ac.newsadmin.model.transfer.VideoBinary;
import com.ac.newsadmin.model.transfer.WillBeStoredFileItem;

public interface BinaryStorageService {
	void storeBinary(Site site, WillBeStoredFileItem willBeStoredFileItem) throws Exception;
	void deletePhotoAssets(Site site,  Photo photo) throws ServiceException;
	void deleteAllPhotoAlbum(Site site, List<Photo> photos, String albumPath) throws ServiceException;
	void deleteColumnist(Site site, Columnist columnist) throws ServiceException;
	void moveObject(Site site, String oldKey, String newKey) throws ServiceException;
	VideoBinary moveVideo(Site site, VideoBinary videoBinary, String underPath) throws ServiceException;
	Photo movePhoto(Site site, Photo photo, String underPath) throws ServiceException;
}
