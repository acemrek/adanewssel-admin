package com.ac.newsadmin.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class PortalAdmin extends AbstractMember implements Serializable{
	
	private List<Site> sites = new ArrayList<Site>(1);

	public List<Site> getSites() {
		return sites;
	}

	public void setSites(List<Site> sites) {
		this.sites = sites;
	}
}
