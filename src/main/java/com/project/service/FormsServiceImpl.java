package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.dao.FormDetailDao;
import com.project.dao.FormsDao;
import com.project.model.FormDetailsVO;
import com.project.model.FormsVO;
import com.project.model.LoginVO;
import com.project.model.ModuleVO;

@Service
@Transactional
public class FormsServiceImpl implements FormsService {

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private FormsDao formsDao;

	@Autowired
	private FormDetailDao formDetailDao;

	@Override
	public List<FormsVO> getCurrentModuleForms(LoginVO loginVO, ModuleVO moduleVO) {

		return this.formsDao.getCurrentModuleForms(loginVO, moduleVO);

	}

	@Override
	public Page<FormsVO> findAllForms(String username, Long id, Pageable pageable) {
		return formsDao.findByLoginVO_UsernameAndModuleVO_IdAndArchiveStatus(username, id, pageable, false);
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
		Long formProjectId = this.formsDao.formProjectId(formsVO.getFormId());
		this.projectService.setMonolithicStatus(formProjectId, false);
		this.projectService.setMicroserviceStatus(formProjectId, false);
		this.formsDao.delete(formsVO);

	}

	@Override
	public void insertForm(FormsVO formsVO) {
		this.projectService.setMonolithicStatus(formsVO.getProjectVO().getId(), false);
		this.projectService.setMicroserviceStatus(formsVO.getProjectVO().getId(), false);
		this.formsDao.save(formsVO);

	}

	@Override
	public void insertFormDetails(FormDetailsVO formDetailsVO) {
		this.formDetailDao.save(formDetailsVO);
	}

	@Override
	public void archiveForm(long formId, boolean status) {
		this.formsDao.archiveForm(formId, status);
	}

	@Override
	public List<FormDetailsVO> findFormDetails(Long id) {
		return this.formDetailDao.findByFormsVO_FormId(id);
	}

	@Override
	public List<FormsVO> findForm(Long id) {
		return this.formsDao.findByFormId(id);
	}

	@Override
	public boolean checkFormName(FormsVO formVO, String username) {
		List<FormsVO> formList = this.formsDao.findByLoginVO_UsernameAndFormNameAndModuleVO_IdAndProjectVO_Id(username,
				formVO.getFormName(), formVO.getModuleVO().getId(), formVO.getProjectVO().getId());

		return formList.isEmpty();
	}

}
