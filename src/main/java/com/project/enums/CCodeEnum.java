package com.project.enums;

public enum CCodeEnum {
	PUBLIC("public"),
	SERVICE("Service"),
	GET_MAPPING("GetMapping"),
	VALUE("(value=\""),
	MODELANDVIEW("ModelAndView"),
	THIS("this."),
	OVERRIDE("Override"),
	ID("\" id=\""),
	SLASH("/"),
	HTTPSTATUS("HttpStatus.OK"),
	LIST("List<"),
	OBJECT("Object>"),
	INSERT("insert"),
	RESPONSEENTITY("ResponseEntity<");

	private String value;

	CCodeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
