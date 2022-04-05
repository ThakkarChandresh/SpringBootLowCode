package com.project.enums;

public enum CCodeEnum {
	PUBLIC("public"),
	SERVICE("Service"),
	GET_MAPPING("GetMapping(value=\""),
	MODELANDVIEW("ModelAndView"),
	THIS("this."),
	OVERRIDE("Override"),
	ID("\" id=\""),
	VIEW("/view");

	private String value;

	CCodeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
