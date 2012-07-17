package com.ac.newsadmin.enums;

public enum Gender {
	
	MALE("M"),
	FEMALE("F");
	
	private String value;
	
	private Gender(String value){
		this.value = value;
	}
	
	public static Gender fromString(String val) {
		for (Gender g : Gender.values()) {
			if (g.value.equals(val)) {
				return g;
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
