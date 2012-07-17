package com.ac.newsadmin.ui.tools.photoUpload;

public enum AspectRatio {
	
	RATIO_1_1("1/1", 1, 100, 100, "1_1"),
	RATIO_3_4("3/4", 0.5, 75, 100, "3_4"),
	RATIO_4_3("4/3", 1.33, 100, 75, "4_3");
	
	private String name;
	private double value;
	private int cropAreaDefaultWidth;
	private int cropAreaDefaultHeight;
	private String imageName;
	
	private AspectRatio(String name, double value, int cropAreaDefaultWidth, int cropAreaDefaultHeight, String imageName){
		this.name  = name;
		this.value = value;
		this.cropAreaDefaultWidth = cropAreaDefaultWidth;
		this.cropAreaDefaultHeight = cropAreaDefaultHeight;
		this.imageName = imageName;
	}

	public static AspectRatio fromDobule(double val) {
		for (AspectRatio r : AspectRatio.values()) {
			if (r.value == val) {
				return r;
			}
		}
		return null;
	}
	
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCropAreaDefaultWidth() {
		return cropAreaDefaultWidth;
	}

	public void setCropAreaDefaultWidth(int cropAreaDefaultWidth) {
		this.cropAreaDefaultWidth = cropAreaDefaultWidth;
	}

	public int getCropAreaDefaultHeight() {
		return cropAreaDefaultHeight;
	}

	public void setCropAreaDefaultHeight(int cropAreaDefaultHeight) {
		this.cropAreaDefaultHeight = cropAreaDefaultHeight;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

}
