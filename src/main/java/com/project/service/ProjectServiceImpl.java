package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.dao.ProjectDao;
import com.project.model.ProjectVO;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectDao projectDao;

	@Override
	public void insertProject(ProjectVO projectVO) {
		
		projectVO.setGeneratedMonolithic(false);
		projectVO.setGeneratedMicroservice(false);
		this.projectDao.save(projectVO);

	}

	@Override
	public void deleteProject(ProjectVO projectVO) {

		this.projectDao.delete(projectVO);

	}

	@Override
	public List<ProjectVO> searchCurrentProject(ProjectVO projectVO) {

		return this.projectDao.findById(projectVO.getId());

	}

	@Override
	public boolean checkProjectName(ProjectVO projectVO) {
		boolean expression;

		List<ProjectVO> projectList = this.projectDao.findByProjectNameAndLoginVO_Username(projectVO.getProjectName(),
				projectVO.getLoginVO().getUsername());

		if (!projectList.isEmpty() && projectVO.getId() == 0) {
			expression = false;
		} else if (!projectList.isEmpty() && projectVO.getId() != projectList.get(0).getId()) {
			expression = false;
		} else {
			expression = true;
		}

		return expression;
	}

	@Override
	public Page<ProjectVO> searchProject(String username, String projectName, String projectDescription,
			boolean isArchive, Pageable pageable) {

		return projectDao
				.findByProjectNameContainingAndLoginVO_UsernameAndArchiveStatusOrProjectDescriptionContainingAndLoginVO_UsernameAndArchiveStatus(
						projectName, username, isArchive, projectDescription, username, isArchive, pageable);
	}

	@Override
	public Page<ProjectVO> getAllCurrentUserProjects(String username, boolean isArchive, Pageable pageable) {
		return projectDao.findByLoginVO_UsernameAndArchiveStatus(username, isArchive, pageable);
	}

	@Override
	public List<ProjectVO> getActiveUserProjects(String username) {

		return projectDao.findByLoginVO_UsernameAndArchiveStatus(username, false);
	}

	@Override
	public void archiveProject(long id, boolean status) {
		this.projectDao.archiveProject(id, status);
	}

	@Override
	public void setMonolithicStatus(long id, boolean status) {
		this.projectDao.setMonolithicStatus(id, status);
	}

	@Override
	public void setMicroserviceStatus(long id, boolean status) {
		this.projectDao.setMicroserviceStatus(id, status);

	}
}
