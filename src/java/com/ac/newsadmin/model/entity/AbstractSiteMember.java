package com.ac.newsadmin.model.entity;


public abstract class AbstractSiteMember extends AbstractMember{
	
	private Site site;
	
	public Site getSite() {
		return site;
	}
	
	public void setSite(Site site) {
		this.site = site;
	}

}
