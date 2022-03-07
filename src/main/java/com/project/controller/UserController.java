package com.project.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.project.dto.RequiredFieldsDTO;
import com.project.enums.AdminPathEnum;
import com.project.enums.ConstantEnum;
import com.project.enums.MessageEnum;
import com.project.enums.UserPathEnum;
import com.project.model.LoginVO;
import com.project.model.RegisterVO;
import com.project.service.LoginService;
import com.project.service.UserDetailService;
import com.project.util.BaseMethods;

@Controller
public class UserController {

	@Autowired
	private UserDetailService detailService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private BaseMethods baseMethods;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping(value = "admin/users")
	public ModelAndView getAllUser() {

		return new ModelAndView(AdminPathEnum.ADMIN_VIEW_USER.getPath());
	}

	@GetMapping(value = "user/viewprofile")
	public ModelAndView viewProfile(@ModelAttribute LoginVO loginVO) {

		loginVO.setUsername(this.baseMethods.getUsername());
		List<RegisterVO> registerVO = this.detailService.getCurrentUser(loginVO);

		return new ModelAndView(UserPathEnum.USER_PROFILE.getPath(), ConstantEnum.REGISTER_VO.getValue(),
				registerVO.get(0));
	}

	@GetMapping(value = "admin/activeUser")
	public ModelAndView activeUser(@ModelAttribute LoginVO loginVO, @RequestParam long id) {
		loginVO.setLoginId(id);
		List<LoginVO> userid = this.detailService.searchUserID(loginVO);

		LoginVO loginVO1 = userid.get(0);
		loginVO1.setEnabled(1);

		this.loginService.insertLogin(loginVO1);
		return new ModelAndView("redirect:/admin/users");
	}

	@GetMapping(value = "admin/deactiveUser")
	public ModelAndView deactiveUser(@ModelAttribute LoginVO loginVO, @RequestParam long id) {
		loginVO.setLoginId(id);
		List<LoginVO> userid = this.detailService.searchUserID(loginVO);

		LoginVO loginVO1 = userid.get(0);
		loginVO1.setEnabled(0);

		this.loginService.insertLogin(loginVO1);
		return new ModelAndView("redirect:/admin/users");
	}

	@PostMapping(value = "user/update")
	public ModelAndView update(@ModelAttribute RegisterVO registerVO, @ModelAttribute LoginVO loginVO) {

		loginVO.setUsername(this.baseMethods.getUsername());
		List<RegisterVO> registerVO1 = this.detailService.getCurrentUser(loginVO);

		registerVO.setRegisterId(registerVO1.get(0).getRegisterId());

		registerVO.getLoginVO().setLoginId(registerVO1.get(0).getLoginVO().getLoginId());
		registerVO.getLoginVO().setEnabled(registerVO1.get(0).getLoginVO().getEnabled());
		registerVO.getLoginVO().setRole(registerVO1.get(0).getLoginVO().getRole());

		registerVO.setEmailVerificationVO(registerVO1.get(0).getEmailVerificationVO());

		this.loginService.insertRegister(registerVO);
		return new ModelAndView("redirect:/user/viewprofile");
	}

	@GetMapping(value = "user/changePassword")
	public ModelAndView changePassword() {
		return new ModelAndView(UserPathEnum.USER_CHANGE_PASSWORD.getPath());
	}

	@PostMapping(value = "user/updatePassword")
	public ModelAndView updatePassword(@ModelAttribute LoginVO loginVO, HttpServletRequest request) {
		loginVO.setUsername(this.baseMethods.getUsername());
		loginVO.setPassword(request.getParameter("oldpassword"));
		boolean status = this.detailService.checkOldPassword(loginVO);

		if (status) {
			loginVO.setUsername(this.baseMethods.getUsername());
			List<LoginVO> loginList = this.loginService.searchLoginID(loginVO);

			LoginVO loginVO2 = loginList.get(0);

			loginVO2.setPassword(bCryptPasswordEncoder.encode(request.getParameter("newpassword")));

			this.loginService.insertLogin(loginVO2);

			return new ModelAndView("redirect:/logout");
		} else {
			return new ModelAndView(UserPathEnum.USER_CHANGE_PASSWORD.getPath(), ConstantEnum.PASSWORD_ERROR.getValue(),
					MessageEnum.OLD_PASSWORD_NOT_VALID.getMessage());
		}

	}

	@PostMapping(value = "admin/fetchUser")
	public ResponseEntity<Page<RequiredFieldsDTO>> loadUsersDataTable(@RequestParam int page,
			@RequestParam Map<String, String> allRequestParams) {

		int size = Integer.parseInt(allRequestParams.get("size"));
		String sort = allRequestParams.get("sort");
		String query = allRequestParams.get("query");
		String sortBy = allRequestParams.get("sortBy");

		Pageable requestedPage = baseMethods.getRequestedPage(page, size, sort, sortBy);

		Page<RequiredFieldsDTO> data;

		if ((query != null) && (!query.trim().isEmpty())) {
			data = detailService.searchUserByQuery(query.trim(), requestedPage);
		} else {
			data = detailService.searchUser(requestedPage);
		}

		return new ResponseEntity<Page<RequiredFieldsDTO>>(data, HttpStatus.OK);
	}
}
