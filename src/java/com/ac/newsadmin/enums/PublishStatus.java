package com.ac.newsadmin.enums;

public enum PublishStatus {
	OFF_THE_AIR("0"),
	ON_THE_AIR("1");
	
	private String value;
	
	private PublishStatus(String value) {
		this.value = value;
	}
	
	public static PublishStatus fromString(String val) {
		for (PublishStatus p : PublishStatus.values()) {
			if (p.value.equals(val)) {
				return p;
			}
		}
		return null;
	}

	public String getValue() {
		return value;
	}

	public String stringValue() {
		return value;
	}
	
}
