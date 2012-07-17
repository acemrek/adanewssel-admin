package com.ac.newsadmin.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.ac.newsadmin.model.entity.Photo;
import com.ac.newsadmin.model.entity.Site;
import com.ac.newsadmin.model.transfer.WillBeStoredImg;
import com.ac.newsadmin.service.BinaryStorageService;
import com.ac.newsadmin.service.ImageResizer;
import com.ac.newsadmin.service.PhotoService;
import com.ac.newsadmin.service.PortalPreferencesService;
import com.ac.newsadmin.ui.GeneralConstants;

@SuppressWarnings("serial")
@Service
public class PhotoServiceImpl implements PhotoService, Serializable{
	
	@Resource private ImageResizer imageResizer;
	@Resource private BinaryStorageService binaryStorageService;
	@Resource protected PortalPreferencesService portalPreferencesService;
	
	public Photo createNewPhoto(Site site, WillBeStoredImg willBeStoredImg) throws Exception{

		Photo photo = new Photo();

		String extension = FilenameUtils.getExtension(willBeStoredImg.getFullStorePath());
		byte []orgData = willBeStoredImg.getData();
		String orgFullStorePath = willBeStoredImg.getFullStorePath();
		willBeStoredImg.setFullStorePath(createAssetFullPath(orgFullStorePath, "org"));
		
		binaryStorageService.storeBinary(site, willBeStoredImg);
		photo.setOriginalPath(willBeStoredImg.getFullStorePath());
		
		if(willBeStoredImg.getThumbDimension() != null){
			byte []thumbData  = imageResizer.scaleImg(orgData, willBeStoredImg.getThumbDimension().getWidth(), willBeStoredImg.getThumbDimension().getHeight(), extension);
			willBeStoredImg.setData(thumbData);
			willBeStoredImg.setFullStorePath(createAssetFullPath(orgFullStorePath, "thumb"));
			binaryStorageService.storeBinary(site, willBeStoredImg);
			photo.setThumbnailPath(willBeStoredImg.getFullStorePath());
		}
		
		if(willBeStoredImg.getSmallDimension() != null){
			byte []smallData  = imageResizer.scaleImg(orgData, willBeStoredImg.getSmallDimension().getWidth(), willBeStoredImg.getSmallDimension().getHeight(), extension);
			willBeStoredImg.setData(smallData);
			willBeStoredImg.setFullStorePath(createAssetFullPath(orgFullStorePath, "small"));
			binaryStorageService.storeBinary(site, willBeStoredImg);
			photo.setSmallPath(willBeStoredImg.getFullStorePath());
		}
		
		if(willBeStoredImg.getMediumDimension() != null){
			byte []mediumData  = imageResizer.scaleImg(orgData, willBeStoredImg.getMediumDimension().getWidth(), willBeStoredImg.getMediumDimension().getHeight(), extension);
			willBeStoredImg.setData(mediumData);
			willBeStoredImg.setFullStorePath(createAssetFullPath(orgFullStorePath, "medium"));
			binaryStorageService.storeBinary(site, willBeStoredImg);
			photo.setMediumPath(willBeStoredImg.getFullStorePath());
		}
		
		if(willBeStoredImg.getLargeDimension() != null){
			byte []largeData  = imageResizer.scaleImg(orgData, willBeStoredImg.getLargeDimension().getWidth(), willBeStoredImg.getLargeDimension().getHeight(), extension);
			willBeStoredImg.setData(largeData);
			willBeStoredImg.setFullStorePath(createAssetFullPath(orgFullStorePath, "large"));
			binaryStorageService.storeBinary(site, willBeStoredImg);
			photo.setLargePath(willBeStoredImg.getFullStorePath());
		}
		
		return photo;
	}
	
	public String storeTempImage(Site site, WillBeStoredImg willBeStoredImg) throws Exception{
		String []chunks = StringUtils.split(willBeStoredImg.getFullStorePath(), '.');
		willBeStoredImg.setFullStorePath("temp/" + System.currentTimeMillis() + "." + chunks[1]);
		binaryStorageService.storeBinary(site, willBeStoredImg);
		return new StringBuffer()
					.append(GeneralConstants.BINARY_BASE_URL)
					.append(site.getDomainName())
					.append('/')
					.append(willBeStoredImg.getFullStorePath()).
					toString();
	}
	
	private String createAssetFullPath(String fullStrorePath, String asset){
		String []chunks = StringUtils.split(fullStrorePath, '.');
		return new StringBuffer()
					.append(chunks[0])
					.append("_")
					.append(asset)
					.append(".")
					.append(chunks[1])
					.toString();
	}

}