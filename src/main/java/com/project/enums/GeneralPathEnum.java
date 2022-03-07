package com.project.enums;

public enum GeneralPathEnum {
	
	HANDLE_404("404"),
	VERIFY_EMAIL("verifyEmail"),
	LOGIN("login"),
	REGISTER("register"),
	FORGOT_PASSWORD("forgotpassword"),
	RESET_PASSWORD("resetpassword");
	
	private String path;

	GeneralPathEnum(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
