package com.project.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.model.ProjectVO;

@Repository
public interface ProjectDao extends JpaRepository<ProjectVO, Long> {

	// In Edit
	List<ProjectVO> findById(Long projectId);

	// To avoid duplicate project
	List<ProjectVO> findByProjectNameAndLoginVO_Username(String projectName, String username);

	// To fetch projects matching with query
	Page<ProjectVO> findByProjectNameContainingAndLoginVO_UsernameAndArchiveStatusOrProjectDescriptionContainingAndLoginVO_UsernameAndArchiveStatus(
			String projectName, String username, boolean status, String projectDescription, String username1,
			boolean status1, Pageable pageable);

	// To fetch all projects in pageable
	Page<ProjectVO> findByLoginVO_UsernameAndArchiveStatus(String username, boolean status, Pageable pageable);

	// Dropdown of projects in modules page
	List<ProjectVO> findByLoginVO_UsernameAndArchiveStatus(String username, boolean status);

	// To archive project
	@Modifying
	@Query("update ProjectVO pvo set pvo.archiveStatus=:#{#status} where pvo.id=:#{#id}")
	void archiveProject(@Param("id") long id, @Param("status") boolean status);

	@Modifying
	@Query("update ProjectVO pvo set pvo.generatedMonolithic=:#{#status} where pvo.id=:#{#id}")
	void setMonolithicStatus(@Param("id") long id, @Param("status") boolean status);

	@Modifying
	@Query("update ProjectVO pvo set pvo.generatedMicroservice=:#{#status} where pvo.id=:#{#id}")
	void setMicroserviceStatus(@Param("id") long id, @Param("status") boolean status);
}
