package com.project.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.ForgotPasswordDao;
import com.project.model.ForgotPasswordVO;

@Service
@Transactional
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

	@Autowired
	private ForgotPasswordDao forgotPasswordDao;

	@Override
	public void setTokenAndOtp(ForgotPasswordVO forgotPasswordVO) {
		this.forgotPasswordDao.save(forgotPasswordVO);
	}

	@Override
	public List<ForgotPasswordVO> checkTokenAndOtp(ForgotPasswordVO forgotPasswordVO) {
		Date date = new Date(System.currentTimeMillis() - 60000);
		
		this.forgotPasswordDao.deleteByStatus("EXPIRED");
		this.forgotPasswordDao.expireOTP(date);

		return this.forgotPasswordDao.findByEmailAndTokenAndOtp(forgotPasswordVO.getEmail(),
				forgotPasswordVO.getToken(), forgotPasswordVO.getOtp());
	}

	@Override
	public List<ForgotPasswordVO> checkEmail(String email) {
		return this.forgotPasswordDao.findByEmail(email);
	}

}
