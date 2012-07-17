package com.ac.common.model.entity;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class IdedBase {
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && ((IdedBase)obj).getId() == id;
	}
	
	@Override
	public int hashCode() {
    	return new HashCodeBuilder().append(getId()).toHashCode();
	}
}
