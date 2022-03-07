package com.project.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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

	Page<FormsVO> findByModuleVO_Id(Long id, Pageable pageable);
}
