package com.project.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.model.FormDetailsVO;
import com.project.model.FormsVO;
import com.project.model.LoginVO;
import com.project.model.ModuleVO;

public interface FormsService {
	List<FormsVO> getCurrentModuleForms(LoginVO loginVO, ModuleVO moduleVO);

	Page<FormsVO> findAllForms(String username, Long id, Pageable pageable);

	Page<FormsVO> searchInCurrentModule(Long id, String formName, String moduleName, String projectName,
			Pageable pageable);

	void deleteForm(FormsVO formsVO);

	void insertForm(FormsVO formsVO);

	void insertFormDetails(FormDetailsVO formDetailsVO);

	void archiveForm(long formId, boolean status);

	List<FormDetailsVO> findFormDetails(Long id);

	List<FormsVO> findForm(Long id);

	boolean checkFormName(FormsVO formVO, String username);
}
