package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.EmailVerificationDao;
import com.project.model.EmailVerificationVO;

@Service
@Transactional
public class EmailVerificationServiceImpl implements EmailVerificationService {

	@Autowired
	private EmailVerificationDao emailverificationDAO;

	public void verifyemailinsert(EmailVerificationVO emailverificationVO) {
		emailverificationDAO.save(emailverificationVO);
	}

	@Override
	public List<EmailVerificationVO> searchUser(EmailVerificationVO emailVerificationVO) {

		return emailverificationDAO.findByEmailAndUuid(emailVerificationVO.getEmail(), emailVerificationVO.getUuid());
	}

}
