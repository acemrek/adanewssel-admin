package com.ac.newsadmin.model.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Column extends AbstractWrittenContent implements Serializable{
	
	private Columnist columnist;
	
	public Columnist getColumnist() {
		return columnist;
	}
	public void setColumnist(Columnist columnist) {
		this.columnist = columnist;
	}

}