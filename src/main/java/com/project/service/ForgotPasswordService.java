package com.project.service;

import java.util.List;

import com.project.model.ForgotPasswordVO;

public interface ForgotPasswordService {
	public void setTokenAndOtp(ForgotPasswordVO forgotPasswordVO);

	public List<ForgotPasswordVO> checkEmail(String email);

	public List<ForgotPasswordVO> checkTokenAndOtp(ForgotPasswordVO forgotPasswordVO);
}
