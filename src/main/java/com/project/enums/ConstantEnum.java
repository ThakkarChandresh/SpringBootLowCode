package com.project.enums;

public enum ConstantEnum {
	
	KEY("key"),
	EMAIL("email"),
	PORT_NUMBER("port_no"),
	
	STATUS("status"),
	MESSAGE("message"),
	
	STATUS_TEXT("statusText"),
	TEMP_STATUS("tempStatus"),
	PASSWORD_ERROR("passwordError"),
	FORGOT_PASSWORD_VO("forgotPasswordVO"),
	REGISTER_VO("RegisterVO"),
	USER_LIST("userList"),
	REGISTER("register"),
	PROJECT_VO("projectVO"),
	PROJECT("project"),
	MODULE_VO("moduleVO"),
	MODULES("modules"),
	CURRENT_PROJECT("currentProject"),
	CURRENT_MODULE("currentModule"),
	MODULE_LIST("moduleList"),
	FORMS_LIST("formsList"),
	PROJECT_LIST("projectList"),
	
	MODULE_ID("moduleId"),
	PROJECT_ID("projectId"),
	
	EXCEPTION_MESSAGE("Exception");

	private String value;

	ConstantEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
