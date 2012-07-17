package com.ac.newsadmin.model.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PortalPreference implements Serializable{

	public static final String COLUMNIST_IMG_THUMB_WIDTH   = "columnist.img.thumb.width";
	public static final String COLUMNIST_IMG_SMALL_WIDTH   = "columnist.img.small.width";;
	public static final String COLUMNIST_IMG_MEDIUM_WIDTH  = "columnist.img.medium.width";
	public static final String COLUMNIST_IMG_LARGE_WIDTH   = "columnist.img.large.width";

	public static final String COLUMNIST_DEFAULT_MALE_PHOTO_ID   = "columnist.default.male.photo.id";
	public static final String COLUMNIST_DEFAULT_FEMALE_PHOTO_ID   = "columnist.default.female.photo.id";
	
	public static final String GALERY_PHOTO_THUMB_WIDTH = "galeryPhoto.thumb.width";
	public static final String GALERY_PHOTO_SMALL_WIDTH = "galeryPhoto.small.width";
	public static final String GALERY_PHOTO_MEDIUM_WIDTH = "galeryPhoto.medium.width";
	public static final String GALERY_PHOTO_LARGE_WIDTH = "galeryPhoto.large.width";
	
	public static final String VIDEO_THUMBNAIL_THUMB_WIDTH = "videoThumbnail.thumb.width";
	public static final String VIDEO_THUMBNAIL_SMALL_WIDTH = "videoThumbnail.small.width";
	public static final String VIDEO_THUMBNAIL_MEDIUM_WIDTH = "videoThumbnail.medium.width";
	public static final String VIDEO_THUMBNAIL_LARGE_WIDTH = "videoThumbnail.large.width";
	
	public static final String NEWS_INLINE_PHOTO_THUMB_WIDTH = "newsInlinePhoto.thumb.width";
	public static final String NEWS_INLINE_PHOTO_SMALL_WIDTH = "newsInlinePhoto.small.width";
	public static final String NEWS_INLINE_PHOTO_MEDIUM_WIDTH = "newsInlinePhoto.medium.width";
	public static final String NEWS_INLINE_PHOTO_LARGE_WIDTH = "newsInlinePhoto.large.width";
	
	private String name;
	private String value;
	private Site site;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}
	
}
