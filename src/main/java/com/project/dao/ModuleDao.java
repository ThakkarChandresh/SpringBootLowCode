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
	List<ModuleVO> findByLoginVO_UsernameAndModuleNameAndProjectVO_Id(String username,String moduleName, Long projectId);

	// To find module of a project
	List<ModuleVO> findByLoginVO_UsernameAndProjectVO_IdAndArchiveStatus(String username,Long projectId, boolean status);

	// To fetch data by query in pageable
	@Query("from ModuleVO mvo where mvo.moduleName like %:#{#moduleName}% and mvo.projectVO.loginVO.username=:#{#username} and mvo.projectVO.id=:#{#projectId} and mvo.archiveStatus=:#{#status} or mvo.moduleDescription like %:#{#moduleDescription}% and mvo.projectVO.loginVO.username=:#{#username} and mvo.projectVO.id=:#{#projectId} or mvo.projectVO.projectName like %:#{#projectName}% and mvo.projectVO.loginVO.username=:#{#username} and mvo.projectVO.id=:#{#projectId}")
	Page<ModuleVO> searchModuleByQuery(@Param("moduleName") String moduleName,
			@Param("moduleDescription") String moduleDescription, @Param("projectName") String projectName,
			@Param("username") String username, @Param("status") boolean status, @Param("projectId") Long id,
			Pageable pageable);

	// To fetch all data in pageable
	Page<ModuleVO> findByLoginVO_UsernameAndProjectVO_IdAndArchiveStatus(String username,Long projectId, boolean status, Pageable pageable);

	// To archive all modules of a project
	@Modifying
	@Query("update ModuleVO mvo set mvo.archiveStatus=:#{#status} where mvo.projectVO.id=:#{#id}")
	public void archiveProjectModule(@Param("id") long projectId, @Param("status") boolean status);

	// To archive one module
	@Modifying
	@Query("update ModuleVO mvo set mvo.archiveStatus=:#{#status} where mvo.id=:#{#id}")
	public void archiveModule(@Param("id") long moduleId, @Param("status") boolean status);
	
	@Query("select mvo.projectVO.id from ModuleVO mvo where mvo.id=:#{#id}")
	public long moduleProjectId(@Param("id") long moduleId);
}
