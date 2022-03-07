package com.project.service;

import java.util.List;

import com.project.model.EmailVerificationVO;

public interface EmailVerificationService {

	void verifyemailinsert(EmailVerificationVO emailverificationVO);

	List<EmailVerificationVO> searchUser(EmailVerificationVO emailVerificationVO);

}
