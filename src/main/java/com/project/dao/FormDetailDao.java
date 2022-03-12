package com.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.model.FormDetailsVO;

public interface FormDetailDao extends JpaRepository<FormDetailsVO, Long> {
	//find all details of current form
	List<FormDetailsVO> findByFormsVO_FormId(Long id);
}
