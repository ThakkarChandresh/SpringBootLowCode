package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.dao.UserDetailDao;
import com.project.dto.RequiredFieldsDTO;
import com.project.model.LoginVO;
import com.project.model.RegisterVO;

@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailService {

	@Autowired
	private UserDetailDao detailDao;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Page<RequiredFieldsDTO> searchUser(Pageable pageable) {
		Page<RegisterVO> ls = detailDao.searchUser(pageable);

		return ls.map(new Converter<RegisterVO, RequiredFieldsDTO>() {

			@Override
			public RequiredFieldsDTO convert(RegisterVO arg0) {
				return setRequiredFields(arg0);
			}
		});
	}

	@Override
	public List<LoginVO> searchUserID(LoginVO loginVO) {
		return detailDao.searchUserID(loginVO);
	}

	@Override
	public List<RegisterVO> getCurrentUser(LoginVO loginVO) {
		return this.detailDao.getCurrentUser(loginVO);

	}

	@Override
	public boolean checkOldPassword(LoginVO loginVO) {
		List<LoginVO> loginList = this.detailDao.findByUsername(loginVO.getUsername());

		return bCryptPasswordEncoder.matches(loginVO.getPassword(), loginList.get(0).getPassword());
	}

	@Override
	public Page<RequiredFieldsDTO> searchUserByQuery(String text, Pageable pageable) {
		Page<RegisterVO> ls = this.detailDao.searchUserByQuery(text, pageable);

		return ls.map(new Converter<RegisterVO, RequiredFieldsDTO>() {
			@Override
			public RequiredFieldsDTO convert(RegisterVO arg0) {
				return setRequiredFields(arg0);
			}
		});
	}

	public RequiredFieldsDTO setRequiredFields(RegisterVO arg0) {
		RequiredFieldsDTO dto = new RequiredFieldsDTO();

		dto.setFirstName(arg0.getFirstName());
		dto.setLastName(arg0.getLastName());
		dto.setUsername(arg0.getLoginVO().getUsername());
		dto.setPassword(arg0.getLoginVO().getPassword());
		dto.setEnabled(arg0.getLoginVO().getEnabled());
		dto.setEnabled(arg0.getLoginVO().getEnabled());
		dto.setStatus(arg0.getEmailVerificationVO().isStatus());
		dto.setLoginId(arg0.getLoginVO().getLoginId());
		dto.setCreatedDate(arg0.getCreatedDate());
		dto.setUpdatedDate(arg0.getUpdatedDate());

		return dto;
	}
}
