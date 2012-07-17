package com.ac.newsadmin.model.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractWrittenContent extends AbstractContent{

	private String title;
	private String htmlText;
	private int listOrder;
	
	private Set<Photo> photos = new HashSet<Photo>(2);
	private Set<Video> videos = new HashSet<Video>(1);
	private Set<Audio> audios = new HashSet<Audio>(1);
	private Set<PhotoGalery> photoGaleries = new HashSet<PhotoGalery>(1);
	
	public Set<Photo> getPhotos() {
		return photos;
	}
	public void setPhotos(Set<Photo> photos) {
		this.photos = photos;
	}
	public Set<Video> getVideos() {
		return videos;
	}
	public void setVideos(Set<Video> videos) {
		this.videos = videos;
	}
	public Set<Audio> getAudios() {
		return audios;
	}
	public void setAudios(Set<Audio> audios) {
		this.audios = audios;
	}
	public Set<PhotoGalery> getPhotoGaleries() {
		return photoGaleries;
	}
	public void setPhotoGaleries(Set<PhotoGalery> photoGaleries) {
		this.photoGaleries = photoGaleries;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHtmlText() {
		return htmlText;
	}
	public void setHtmlText(String htmlText) {
		this.htmlText = htmlText;
	}
	public int getListOrder() {
		return listOrder;
	}
	public void setListOrder(int listOrder) {
		this.listOrder = listOrder;
	}
	
	public List<Photo> getPhotosAsList(){
		return new ArrayList<Photo>(photos);
	}

	public List<Video> getVideosAsList(){
		return new ArrayList<Video>(videos);
	}
	
	public List<Audio> getAudiosAsList(){
		return new ArrayList<Audio>(audios);
	}
	
	public List<PhotoGalery> getPhotoGaleriesAsList(){
		return new ArrayList<PhotoGalery>(photoGaleries);
	}
}
