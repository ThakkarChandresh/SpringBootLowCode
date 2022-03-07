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
@Table(name = "forms")
public class FormsVO extends AuditDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;

	@Column
	private String formName;

	@Column
	private boolean archiveStatus = false;

	@ManyToOne
	private ModuleVO moduleVO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
}
