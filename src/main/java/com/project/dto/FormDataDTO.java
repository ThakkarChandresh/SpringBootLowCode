package com.project.dto;

import java.util.List;

public class FormDataDTO {
	private Long projectId;
	private Long moduleId;
	private String formName;
	private String formDescription;
	private List<FormDetailsDTO> formDetails;

	public String getFormName() {
		return formName;
	}

	public String getFormDescription() {
		return formDescription;
	}

	public List<FormDetailsDTO> getFormDetails() {
		return formDetails;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public void setFormDescription(String formDescription) {
		this.formDescription = formDescription;
	}

	public void setFormDetails(List<FormDetailsDTO> formDetails) {
		this.formDetails = formDetails;
	}

	public Long getProjectId() {
		return projectId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

}
