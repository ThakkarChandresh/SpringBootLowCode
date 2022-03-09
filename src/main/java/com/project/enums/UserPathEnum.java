package com.project.enums;

public enum UserPathEnum {
	
	USER_PROFILE("user/profile"),
	USER_CHANGE_PASSWORD("user/changepassword"),
	USER_PROJECT("user/project"),
	USER_MODULE("user/module"),
	USER_ADD_FORM("user/AddForm"),
	USER_INDEX("user/index"),
	USER_FORMS("user/form"),
	USER_PROJECT_EXPLORER("user/projectexplorer");
	private String path;

	UserPathEnum(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
	
}
