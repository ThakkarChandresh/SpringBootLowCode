package com.project.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "forms")
public class FormsVO extends AuditDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long formId;

	@Column
	private String formName;

	@Column
	private String formDescription;

	@Column
	private boolean archiveStatus = false;

	@ManyToOne
	private LoginVO loginVO;

	@ManyToOne
	private ProjectVO projectVO;

	@ManyToOne
	private ModuleVO moduleVO;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "formsVO", orphanRemoval = true)
	private List<FormDetailsVO> formDetailsVO = new ArrayList<FormDetailsVO>();

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public ModuleVO getModuleVO() {
		return moduleVO;
	}

	public void setModuleVO(ModuleVO moduleVO) {
		this.moduleVO = moduleVO;
	}

	public boolean isArchiveStatus() {
		return archiveStatus;
	}

	public void setArchiveStatus(boolean archiveStatus) {
		this.archiveStatus = archiveStatus;
	}

	public String getForDescription() {
		return formDescription;
	}

	public void setForDescription(String forDescription) {
		this.formDescription = forDescription;
	}

	public LoginVO getLoginVO() {
		return loginVO;
	}

	public void setLoginVO(LoginVO loginVO) {
		this.loginVO = loginVO;
	}

	public ProjectVO getProjectVO() {
		return projectVO;
	}

	public void setProjectVO(ProjectVO projectVO) {
		this.projectVO = projectVO;
	}
}
