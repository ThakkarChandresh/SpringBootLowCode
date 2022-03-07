package com.project.service;

import java.util.List;

import com.project.model.LoginVO;
import com.project.model.RegisterVO;

public interface LoginService {

	void insertRegister(RegisterVO registerVO);

	void insertLogin(LoginVO loginVO);

	List<LoginVO> searchLoginID(LoginVO loginVO);

	boolean checkEmail(LoginVO loginVO);

}
