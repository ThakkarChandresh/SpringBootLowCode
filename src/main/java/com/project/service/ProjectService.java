package com.project.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.model.ProjectVO;

public interface ProjectService {

	List<ProjectVO> searchCurrentProject(ProjectVO projectVO);

	boolean checkProjectName(ProjectVO projectVO);

	void insertProject(ProjectVO projectVO);

	void deleteProject(ProjectVO projectVO);

	List<ProjectVO> getActiveUserProjects(String username);

	Page<ProjectVO> getAllCurrentUserProjects(String username, boolean isArchive, Pageable pageable);

	Page<ProjectVO> searchProject(String username, String projectName, String projectDescription, boolean isArchive,
			Pageable pageable);

	void archiveProject(long id, boolean status);

	void setMonolithicStatus(long id, boolean status);

	void setMicroserviceStatus(long id, boolean status);
}
