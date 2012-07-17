package com.ac.newsadmin.model.transfer;

public class WillBeStoredImg extends WillBeStoredFileItem{
	
	private ImageDimension thumbDimension;
	private ImageDimension smallDimension;
	private ImageDimension mediumDimension;
	private ImageDimension largeDimension;
	
	public ImageDimension getThumbDimension() {
		return thumbDimension;
	}
	public void setThumbDimension(ImageDimension thumbDimension) {
		this.thumbDimension = thumbDimension;
	}
	public ImageDimension getSmallDimension() {
		return smallDimension;
	}
	public void setSmallDimension(ImageDimension smallDimension) {
		this.smallDimension = smallDimension;
	}
	public ImageDimension getMediumDimension() {
		return mediumDimension;
	}
	public void setMediumDimension(ImageDimension mediumDimension) {
		this.mediumDimension = mediumDimension;
	}
	public ImageDimension getLargeDimension() {
		return largeDimension;
	}
	public void setLargeDimension(ImageDimension largeDimension) {
		this.largeDimension = largeDimension;
	}

	
}
