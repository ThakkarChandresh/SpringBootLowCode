package com.project.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.model.ModuleVO;
import com.project.model.ProjectVO;

public interface ModuleService {

	void addModule(ModuleVO moduleVO);

	void deleteModule(ModuleVO moduleVO);

	List<ModuleVO> getCurrentModuleData(ModuleVO moduleVO);

	boolean checkModuleName(ModuleVO moduleVO);

	List<ModuleVO> getCurrentProjectModule(ProjectVO projectVO);

	Page<ModuleVO> searchByProjectId(Long projectId,boolean isArchive,Pageable pageable);

	Page<ModuleVO> searchCurrentProjectModules(Long id, String moduleName, String moduleDescription, String projectName,boolean isArchive,
			Pageable pageable);
	
	void archiveProjectModule(long projectId, boolean status);
	
	void archiveModule(long moduleId, boolean status);

}
