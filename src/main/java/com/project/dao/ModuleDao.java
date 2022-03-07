package com.project.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.model.ModuleVO;

@Repository
public interface ModuleDao extends JpaRepository<ModuleVO, Long> {

	// In Edit
	List<ModuleVO> findById(Long moduleId);

	// To avoid duplication of module in a project
	List<ModuleVO> findByModuleNameAndProjectVO_Id(String moduleName, Long projectId);

	// To find module of a project
	List<ModuleVO> findByProjectVO_IdAndArchiveStatus(Long projectId, boolean status);

	// To fetch data by query in pageable
	Page<ModuleVO> findByModuleNameContainingAndProjectVO_IdAndArchiveStatusOrModuleDescriptionContainingAndProjectVO_IdAndArchiveStatusOrProjectVO_ProjectNameContainingAndProjectVO_IdAndArchiveStatus(
			String moduleName, Long id, boolean status, String moduleDescription, Long id1, boolean status1,
			String projectName, Long id2, boolean status2, Pageable pageable);

	// To fetch all data in pageable
	Page<ModuleVO> findByProjectVO_IdAndArchiveStatus(Long projectId, boolean status, Pageable pageable);

	// To archive all modules of a project
	@Modifying
	@Query("update ModuleVO mvo set mvo.archiveStatus=:#{#status} where mvo.projectVO.id=:#{#id}")
	public void archiveProjectModule(@Param("id") long projectId, @Param("status") boolean status);

	// To archive one module
	@Modifying
	@Query("update ModuleVO mvo set mvo.archiveStatus=:#{#status} where mvo.id=:#{#id}")
	public void archiveModule(@Param("id") long moduleId, @Param("status") boolean status);
}
