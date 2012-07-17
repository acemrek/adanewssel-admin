package com.ac.newsadmin.model.entity;

import java.io.Serializable;

import javax.persistence.Transient;

import com.ac.newsadmin.enums.Gender;

@SuppressWarnings("serial")
public class Columnist extends AbstractSiteMember implements Serializable{
	
	private Photo photo;
	private Gender gender;

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	@Transient
	public String getPhotoUrl(){
		return null;
	}
}
