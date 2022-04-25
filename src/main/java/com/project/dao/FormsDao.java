package com.project.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.model.FormsVO;
import com.project.model.LoginVO;
import com.project.model.ModuleVO;

@Repository
public interface FormsDao extends JpaRepository<FormsVO, Long> {

	@Query("from FormsVO where moduleVO.projectVO.loginVO.username=:#{#loginVO.username} and moduleVO.id=:#{#moduleVO.id}")
	List<FormsVO> getCurrentModuleForms(@Param("loginVO") LoginVO loginVO, @Param("moduleVO") ModuleVO moduleVO);

	Page<FormsVO> findByFormNameContainingAndModuleVO_IdOrModuleVO_ModuleNameContainingAndModuleVO_IdOrModuleVO_ProjectVO_ProjectNameContainingAndModuleVO_Id(
			String formName, Long id, String moduleName, Long id1, String projectName, Long id2, Pageable pageable);

	// find currents module's forms
	Page<FormsVO> findByLoginVO_UsernameAndModuleVO_IdAndArchiveStatus(String username, Long id, Pageable pageable,
			boolean staus);

	// To archive one form
	@Modifying
	@Query("update FormsVO fvo set fvo.archiveStatus=:#{#status} where fvo.formId=:#{#id}")
	public void archiveForm(@Param("id") long formId, @Param("status") boolean status);

	// find form by id
	List<FormsVO> findByFormId(long formId);
	
	List<FormsVO> findByLoginVO_UsernameAndFormNameAndModuleVO_IdAndProjectVO_Id(String username,String formName, Long moduleId ,Long projectId);
	
	@Query("select fvo.projectVO.id from FormsVO fvo where fvo.formId=:#{#id}")
	public long formProjectId(@Param("id") long formId);
}
