package com.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.model.LoginVO;

@Repository
public interface LoginDao extends JpaRepository<LoginVO, Long> {

	@Query("from LoginVO where username=:#{#loginVO.username}")
	List<LoginVO> searchLoginID(@Param("loginVO") LoginVO loginVO);

}
