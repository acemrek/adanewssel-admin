package com.ac.newsadmin.model.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class HeadLine extends AbstractSiteEntity implements Serializable{

	private Photo headLinePhoto;
	private News news;
	private int listOrder;

	public Photo getHeadLinePhoto() {
		return headLinePhoto;
	}
	public void setHeadLinePhoto(Photo headLinePhoto) {
		this.headLinePhoto = headLinePhoto;
	}
	public News getNews() {
		return news;
	}
	public void setNews(News news) {
		this.news = news;
	}
	public int getListOrder() {
		return listOrder;
	}
	public void setListOrder(int listOrder) {
		this.listOrder = listOrder;
	}
	
}
