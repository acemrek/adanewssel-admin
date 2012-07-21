package com.ac.newsadmin.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

@SuppressWarnings("serial")
public class PhotoGalery extends AbstractMediaContent implements Serializable{
	
	private List<GaleryPhoto> photos = new ArrayList<GaleryPhoto>();
	
	public List<GaleryPhoto> getPhotos() {
		return photos;
	}

	public void setPhotos(List<GaleryPhoto> photos) {
		this.photos = photos;
	}
	
	public GaleryPhoto getCoverPhoto(){
		if(CollectionUtils.isEmpty(photos) == false){
			return photos.get(0);
		}
		return null;
	}
	
	public Integer getNumberOfPhotos(){
		return CollectionUtils.isEmpty(photos) ? 0 : photos.size();
	}
	
	public GaleryPhoto getPhotoByIndex(int index){
		return photos.get(index);
	}
	
}
