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
@Table(name = "projects")
public class ProjectVO extends AuditDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String projectName;

	@Column
	private String projectDescription;

	@Column
	private String projectIcon;

	@Column
	private boolean archiveStatus = false;

	@Column
	private boolean generatedMonolithic = false;

	@Column
	private boolean generatedMicroservice = false;

	@ManyToOne
	private LoginVO loginVO;

	protected static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public LoginVO getLoginVO() {
		return loginVO;
	}

	public void setLoginVO(LoginVO loginVO) {
		this.loginVO = loginVO;
	}

	public String getProjectIcon() {
		return projectIcon;
	}

	public void setProjectIcon(String projectIcon) {
		this.projectIcon = projectIcon;
	}

	public boolean isArchiveStatus() {
		return archiveStatus;
	}

	public void setArchiveStatus(boolean archiveStatus) {
		this.archiveStatus = archiveStatus;
	}

	public boolean isGeneratedMonolithic() {
		return generatedMonolithic;
	}

	public boolean isGeneratedMicroservice() {
		return generatedMicroservice;
	}

	public void setGeneratedMonolithic(boolean generatedMonolithic) {
		this.generatedMonolithic = generatedMonolithic;
	}

	public void setGeneratedMicroservice(boolean generatedMicroservice) {
		this.generatedMicroservice = generatedMicroservice;
	}
}
