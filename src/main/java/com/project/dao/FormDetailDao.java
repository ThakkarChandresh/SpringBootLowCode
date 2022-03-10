package com.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.model.FormDetailsVO;

public interface FormDetailDao extends JpaRepository<FormDetailsVO, Long> {
	
	

}
