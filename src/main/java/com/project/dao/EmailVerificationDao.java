package com.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.EmailVerificationVO;

@Repository
public interface EmailVerificationDao extends JpaRepository<EmailVerificationVO, Long> {

	// E-mail Verification
	List<EmailVerificationVO> findByEmailAndUuid(String email, String uuid);
}
