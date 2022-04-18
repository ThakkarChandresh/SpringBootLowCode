package com.project.enums;

public enum TypeEnum {
	MICROSERVICE("microservice"), 
	MONOLITHIC("monolithic");

	private String value;

	TypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
