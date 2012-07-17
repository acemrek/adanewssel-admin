package com.ac.newsadmin.model.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class News extends AbstractWrittenContent implements Serializable{

	private Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
}
