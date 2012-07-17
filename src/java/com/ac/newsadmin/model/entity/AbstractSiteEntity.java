package com.ac.newsadmin.model.entity;

import com.ac.common.model.entity.IdedBase;

public abstract class AbstractSiteEntity extends IdedBase{
	private Site site;
	
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}
}
