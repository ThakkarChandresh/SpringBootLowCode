package com.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.RegisterVO;

@Repository
public interface RegisterDao extends JpaRepository<RegisterVO, Long> {

}
