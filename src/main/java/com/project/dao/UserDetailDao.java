package com.project.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.model.LoginVO;
import com.project.model.RegisterVO;

@Repository
public interface UserDetailDao extends JpaRepository<LoginVO, Long> {

	@Query("from RegisterVO registerVO where registerVO.loginVO.role = 'ROLE_USER'")
	Page<RegisterVO> searchUser(Pageable pageable);

	@Query("from RegisterVO registerVO where registerVO.firstName like %:text% and registerVO.loginVO.role = 'ROLE_USER' or registerVO.lastName like %:text% and registerVO.loginVO.role = 'ROLE_USER' or registerVO.loginVO.username like %:text% and registerVO.loginVO.role = 'ROLE_USER'")
	Page<RegisterVO> searchUserByQuery(@Param("text") String text, Pageable pageable);

	@Query("from LoginVO where loginId=:#{#loginVO.loginId}")
	List<LoginVO> searchUserID(@Param("loginVO") LoginVO loginVO);

	@Query("from RegisterVO where loginVO.username =:#{#loginVO.username}")
	List<RegisterVO> getCurrentUser(@Param("loginVO") LoginVO loginVO);

	List<LoginVO> findByUsername(String username);
}
