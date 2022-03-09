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

	boolean checkModuleName(ModuleVO moduleVO, String username);

	List<ModuleVO> getCurrentProjectModule(String username,ProjectVO projectVO);

	Page<ModuleVO> searchByProjectId(String username,Long projectId,boolean isArchive,Pageable pageable);

	Page<ModuleVO> searchCurrentProjectModules(Long id, String moduleName, String moduleDescription, String projectName,boolean isArchive,
			String username,Pageable pageable);
	
	void archiveProjectModule(long projectId, boolean status);
	
	void archiveModule(long moduleId, boolean status);

}
