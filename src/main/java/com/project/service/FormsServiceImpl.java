package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.dao.FormsDao;
import com.project.model.FormsVO;
import com.project.model.LoginVO;
import com.project.model.ModuleVO;

@Service
@Transactional
public class FormsServiceImpl implements FormsService {

	@Autowired
	private FormsDao formsDao;

	@Override
	public List<FormsVO> getCurrentModuleForms(LoginVO loginVO, ModuleVO moduleVO) {

		return this.formsDao.getCurrentModuleForms(loginVO, moduleVO);

	}

	@Override
	public Page<FormsVO> findAllForms(Long id, Pageable pageable) {
		return formsDao.findByModuleVO_Id(id, pageable);
	}

	@Override
	public Page<FormsVO> searchInCurrentModule(Long id, String formName, String moduleName, String projectName,
			Pageable pageable) {
		return formsDao
				.findByFormNameContainingAndModuleVO_IdOrModuleVO_ModuleNameContainingAndModuleVO_IdOrModuleVO_ProjectVO_ProjectNameContainingAndModuleVO_Id(
						formName, id, moduleName, id, projectName, id, pageable);
	}

	@Override
	public void deleteForm(FormsVO formsVO) {
		this.formsDao.delete(formsVO);
		
	}

}
