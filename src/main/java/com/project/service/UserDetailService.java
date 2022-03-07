package com.project.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.dto.RequiredFieldsDTO;
import com.project.model.LoginVO;
import com.project.model.RegisterVO;

public interface UserDetailService {

	Page<RequiredFieldsDTO> searchUser(Pageable pageable);

	List<LoginVO> searchUserID(LoginVO loginVO);

	List<RegisterVO> getCurrentUser(LoginVO loginVO);

	boolean checkOldPassword(LoginVO loginVO);

	Page<RequiredFieldsDTO> searchUserByQuery(String text, Pageable pageable);
}
