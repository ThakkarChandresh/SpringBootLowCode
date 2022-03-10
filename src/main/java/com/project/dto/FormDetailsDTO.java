package com.project.dto;

import java.util.List;

public class FormDetailsDTO {

	private String fieldName;
	private String fieldType;
	private List<OptionsDTO> options;

	public String getFieldName() {
		return fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public List<OptionsDTO> getOptions() {
		return options;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public void setOptions(List<OptionsDTO> options) {
		this.options = options;
	}

}
