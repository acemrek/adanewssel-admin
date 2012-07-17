package com.ac.newsadmin.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ac.common.model.entity.IdedBase;

@SuppressWarnings("serial")
public class Site extends IdedBase implements Serializable{

	private String domainName;
	private String description;
	private Date serviceStartDate; 
	private Date serviceEndDate;
	
	private List<PortalAdmin> admins = new ArrayList<PortalAdmin>(1);
	
	public Site() {
	}
	
	public Site(String domainName) {
		this.domainName = domainName;
	}
	
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getServiceStartDate() {
		return serviceStartDate;
	}
	public void setServiceStartDate(Date serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}
	
	public Date getServiceEndDate() {
		return serviceEndDate;
	}
	public void setServiceEndDate(Date serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}
	
	public List<PortalAdmin> getAdmins() {
		admins.size();
		return admins;
	}

	public void setAdmins(List<PortalAdmin> admins) {
		this.admins = admins;
	}
}
