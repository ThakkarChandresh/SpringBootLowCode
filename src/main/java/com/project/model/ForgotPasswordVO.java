package com.project.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "forgot_password_details")
public class ForgotPasswordVO extends AuditDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String email;

	@Column
	private String otp;

	@Column
	private String token;

	@Column
	private String status = "NOT_VERIFIED";

	@Column
	private int attempt = 0;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getToken() {
		return token;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getStatus() {
		return status;
	}

	public int getAttempt() {
		return attempt;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}
}
