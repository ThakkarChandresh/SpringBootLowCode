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
	private ModuleDao moduleDao;

	@Override
	public void addModule(ModuleVO moduleVO) {

		this.moduleDao.save(moduleVO);

	}

	@Override
	public void deleteModule(ModuleVO moduleVO) {

		this.moduleDao.delete(moduleVO);

	}

	@Override
	public List<ModuleVO> getCurrentModuleData(ModuleVO moduleVO) {

		return this.moduleDao.findById(moduleVO.getId());

	}

	@Override
	public boolean checkModuleName(ModuleVO moduleVO) {
		boolean expression;

		List<ModuleVO> moduleList = this.moduleDao.findByModuleNameAndProjectVO_Id(moduleVO.getModuleName(),
				moduleVO.getProjectVO().getId());

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
	public List<ModuleVO> getCurrentProjectModule(ProjectVO projectVO) {
		return this.moduleDao.findByProjectVO_IdAndArchiveStatus(projectVO.getId(), false);
	}

	@Override
	public Page<ModuleVO> searchCurrentProjectModules(Long id, String moduleName, String moduleDescription,
			String projectName,boolean isArchive, Pageable pageable) {

		return moduleDao
				.findByModuleNameContainingAndProjectVO_IdAndArchiveStatusOrModuleDescriptionContainingAndProjectVO_IdAndArchiveStatusOrProjectVO_ProjectNameContainingAndProjectVO_IdAndArchiveStatus(
						moduleName, id, isArchive, moduleDescription, id, isArchive, projectName, id, isArchive, pageable);
	}

	@Override
	public Page<ModuleVO> searchByProjectId(Long projectId,boolean isArchive,Pageable pageable) {
		return moduleDao.findByProjectVO_IdAndArchiveStatus(projectId, isArchive, pageable);
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
