package com.ac.newsadmin.model.entity;

import java.util.Date;

import com.ac.newsadmin.enums.PublishStatus;

public abstract class AbstractContent extends AbstractSiteEntity{
	
	private Date createDate;
	private PublishStatus publishStatus = PublishStatus.OFF_THE_AIR;

	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public PublishStatus getPublishStatus() {
		return publishStatus;
	}
	public void setPublishStatus(PublishStatus publishStatus) {
		this.publishStatus = publishStatus;
	}
}
