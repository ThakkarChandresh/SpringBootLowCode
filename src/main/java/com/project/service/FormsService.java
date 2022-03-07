package com.project.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.model.FormsVO;
import com.project.model.LoginVO;
import com.project.model.ModuleVO;

public interface FormsService {
	List<FormsVO> getCurrentModuleForms(LoginVO loginVO, ModuleVO moduleVO);

	Page<FormsVO> findAllForms(Long id, Pageable pageable);

	Page<FormsVO> searchInCurrentModule(Long id,String formName, String moduleName, String projectName, Pageable pageable);
}
