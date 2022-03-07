package com.project.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.model.ForgotPasswordVO;

public interface ForgotPasswordDao extends JpaRepository<ForgotPasswordVO, Long> {

	// To update token and otp
	public List<ForgotPasswordVO> findByEmail(String email);

	// To validate Email otp and token
	public List<ForgotPasswordVO> findByEmailAndTokenAndOtp(String email, String token, String otp);

	@Modifying
	@Query("update ForgotPasswordVO fpvo set fpvo.status='EXPIRED' where fpvo.updatedDate <= :#{#updated_date}")
	public void expireOTP(@Param("updated_date") Date date);
	
	@Modifying
	public void deleteByStatus(String status);
}
