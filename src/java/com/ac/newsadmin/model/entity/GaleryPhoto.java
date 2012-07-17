package com.ac.newsadmin.model.entity;

import java.io.Serializable;

import com.ac.common.model.entity.IdedBase;

@SuppressWarnings("serial")
public class GaleryPhoto extends IdedBase implements Serializable{

	private PhotoGalery photoGalery;
	private Photo photo;
	private String description;
	private int listOrder;
	
	public Photo getPhoto() {
		return photo;
	}
	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getListOrder() {
		return listOrder;
	}
	public void setListOrder(int listOrder) {
		this.listOrder = listOrder;
	}
	public PhotoGalery getPhotoGalery() {
		return photoGalery;
	}
	public void setPhotoGalery(PhotoGalery photoGalery) {
		this.photoGalery = photoGalery;
	}
	
}
