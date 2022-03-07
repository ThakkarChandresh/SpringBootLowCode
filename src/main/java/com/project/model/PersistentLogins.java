package com.project.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "persistent_logins")
public class PersistentLogins implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private String username;

	@Id
	@NotNull
	private String series;

	@NotNull
	private String token;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUsed;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUsername() {
		return username;
	}

	public String getSeries() {
		return series;
	}

	public String getToken() {
		return token;
	}

	public Date getLastUsed() {
		return lastUsed;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}
}
