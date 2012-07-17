package com.ac.newsadmin.model.entity;

import java.io.Serializable;

import com.ac.common.model.entity.IdedBase;

@SuppressWarnings("serial")
public class Category extends IdedBase implements Serializable{
	
	private String name;
	private String i18nKey;
	private Site site;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getI18nKey() {
		return i18nKey;
	}
	public void setI18nKey(String i18nKey) {
		this.i18nKey = i18nKey;
	}
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}
}
