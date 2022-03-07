package com.project.enums;

public enum AdminPathEnum {
	
	ADMIN_VIEW_USER("admin/user"),
	ADMIN_INDEX("admin/index"),
	ADMIN_MANAGE_TEMPLETE("admin/manageTemplate");
	
	private String path;

	AdminPathEnum(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
