package com.project.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "register")
public class RegisterVO extends AuditDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long registerId;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@ManyToOne
	LoginVO loginVO;

	@ManyToOne
	EmailVerificationVO emailVerificationVO;

	public EmailVerificationVO getEmailVerificationVO() {
		return emailVerificationVO;
	}

	public void setEmailVerificationVO(EmailVerificationVO emailVerificationVO) {
		this.emailVerificationVO = emailVerificationVO;
	}

	protected static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getRegisterId() {
		return registerId;
	}

	public void setRegisterId(Long registerId) {
		this.registerId = registerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LoginVO getLoginVO() {
		return loginVO;
	}

	public void setLoginVO(LoginVO loginVO) {
		this.loginVO = loginVO;
	}

}
