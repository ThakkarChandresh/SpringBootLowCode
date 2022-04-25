package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.dao.ModuleDao;
import com.project.model.ModuleVO;
import com.project.model.ProjectVO;

@Service
@Transactional
public class ModuleServiceImpl implements ModuleService {
	@Autowired
	private ProjectService projectService;

	@Autowired
	private ModuleDao moduleDao;

	@Override
	public void addModule(ModuleVO moduleVO) {
		this.projectService.setMicroserviceStatus(moduleVO.getProjectVO().getId(), false);
		this.projectService.setMonolithicStatus(moduleVO.getProjectVO().getId(), false);
		this.moduleDao.save(moduleVO);

	}

	@Override
	public void deleteModule(ModuleVO moduleVO) {
		
		Long moduleProjectId = this.moduleDao.moduleProjectId(moduleVO.getId());
		this.projectService.setMicroserviceStatus(moduleProjectId, false);
		this.projectService.setMonolithicStatus(moduleProjectId, false);
		this.moduleDao.delete(moduleVO);

	}

	@Override
	public List<ModuleVO> getCurrentModuleData(ModuleVO moduleVO) {

		return this.moduleDao.findById(moduleVO.getId());
	}

	@Override
	public boolean checkModuleName(ModuleVO moduleVO, String username) {
		boolean expression;

		List<ModuleVO> moduleList = this.moduleDao.findByLoginVO_UsernameAndModuleNameAndProjectVO_Id(username,
				moduleVO.getModuleName(), moduleVO.getProjectVO().getId());

		if (!moduleList.isEmpty() && moduleVO.getId() == 0) {
			expression = false;
		} else if (!moduleList.isEmpty() && !moduleVO.getId().equals(moduleList.get(0).getId())) {
			expression = false;
		} else {
			expression = true;
		}

		return expression;
	}

	@Override
	public List<ModuleVO> getCurrentProjectModule(String username, ProjectVO projectVO) {
		return this.moduleDao.findByLoginVO_UsernameAndProjectVO_IdAndArchiveStatus(username, projectVO.getId(), false);
	}

	@Override
	public Page<ModuleVO> searchCurrentProjectModules(Long id, String moduleName, String moduleDescription,
			String projectName, boolean isArchive, String username, Pageable pageable) {

		return this.moduleDao.searchModuleByQuery(moduleName, moduleDescription, projectName, username, isArchive, id,
				pageable);
	}

	@Override
	public Page<ModuleVO> searchByProjectId(String username, Long projectId, boolean isArchive, Pageable pageable) {
		return moduleDao.findByLoginVO_UsernameAndProjectVO_IdAndArchiveStatus(username, projectId, isArchive,
				pageable);
	}

	@Override
	public void archiveProjectModule(long projectId, boolean status) {
		this.moduleDao.archiveProjectModule(projectId, status);

	}

	@Override
	public void archiveModule(long moduleId, boolean status) {
		this.moduleDao.archiveModule(moduleId, status);
	}
}
