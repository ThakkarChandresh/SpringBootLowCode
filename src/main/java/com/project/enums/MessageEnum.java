package com.project.enums;

public enum MessageEnum {
	
	OLD_PASSWORD_NOT_VALID("Old passsword is not valid"),
	SOMETHING_WRONG("Something went wrong"),
	EMAIL_ALREADY_VERIFIED("E-mail already verified"),
	EMAIL_VERIFIED("E-mail verified successfully"),
	LOGOUT("Logout Successfully!"),
	SUCCESS("success");
	
	private String message;

	MessageEnum(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
