package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.LoginDao;
import com.project.dao.RegisterDao;
import com.project.model.LoginVO;
import com.project.model.RegisterVO;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginDao loginDAO;

	@Autowired
	private RegisterDao registerDao;

	public void insertLogin(LoginVO loginVO) {
		loginDAO.save(loginVO);
	}

	public List<LoginVO> searchLoginID(LoginVO loginVO) {
		return loginDAO.searchLoginID(loginVO);
	}

	@Override
	public boolean checkEmail(LoginVO loginVO) {
		List<LoginVO> loginList = this.loginDAO.searchLoginID(loginVO);
		return !loginList.isEmpty();
	}

	@Override
	public void insertRegister(RegisterVO registerVO) {
		registerDao.save(registerVO);
	}

}