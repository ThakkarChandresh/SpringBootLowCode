package com.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.project.enums.ConstantEnum;
import com.project.enums.GeneralPathEnum;
import com.project.enums.MessageEnum;
import com.project.model.EmailVerificationVO;
import com.project.model.LoginVO;
import com.project.model.RegisterVO;
import com.project.service.EmailVerificationService;
import com.project.service.LoginService;
import com.project.util.BaseMethods;

@RestController
public class RegisterController {

	@Value("${server.port}")
	private int portnumber;

	@Autowired
	private LoginService loginService;

	@Autowired
	private BaseMethods baseservice;

	@Autowired
	private EmailVerificationService emailverificationservice;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping(value = "/register")
	public ModelAndView loadRegister() {
		return new ModelAndView(GeneralPathEnum.REGISTER.getPath(), ConstantEnum.REGISTER_VO.getValue(),
				new RegisterVO());
	}

	@GetMapping(value = "/checkEmail")
	public ResponseEntity<Boolean> checkEmail(@RequestParam String email, LoginVO loginVO) {
		loginVO.setUsername(email);
		boolean status = this.loginService.checkEmail(loginVO);
		return new ResponseEntity<Boolean>(status, HttpStatus.OK);
	}

	@PostMapping(value = "/insert")
	public ModelAndView goToHomePage(@ModelAttribute RegisterVO registerVO, LoginVO loginVO,
			EmailVerificationVO emailverificationVO) {

		String emailId = registerVO.getLoginVO().getUsername();
		emailId = emailId.trim();

		loginVO.setUsername(emailId);
		loginVO.setPassword(bCryptPasswordEncoder.encode(registerVO.getLoginVO().getPassword()));
		loginVO.setEnabled(0);
		loginVO.setRole("ROLE_USER");

		loginService.insertLogin(loginVO);

		String uuid = baseservice.generateuuid();
		String link = "http://localhost:" + portnumber + "/verify-email?key=" + uuid + "&email="
				+ registerVO.getLoginVO().getUsername();

		emailverificationVO.setEmail(registerVO.getLoginVO().getUsername());
		emailverificationVO.setUuid(uuid);
		emailverificationVO.setStatus(true);

		emailverificationservice.verifyemailinsert(emailverificationVO);

		registerVO.setLoginVO(loginVO);
		registerVO.setEmailVerificationVO(emailverificationVO);
		loginService.insertRegister(registerVO);

		baseservice.sendVerifyMail(registerVO.getLoginVO().getUsername(), registerVO.getFirstName(), registerVO.getLastName(),
				link);

		return new ModelAndView("redirect:/");
	}

	@GetMapping(value = "/verify-email")
	public ModelAndView verifyemail(ModelMap map, @RequestParam String key, @RequestParam String email) {

		map.put(ConstantEnum.KEY.getValue(), key);
		map.put(ConstantEnum.EMAIL.getValue(), email);
		map.put(ConstantEnum.PORT_NUMBER.getValue(), portnumber);

		return new ModelAndView(GeneralPathEnum.VERIFY_EMAIL.getPath());
	}

	@GetMapping(value = "/verifyemail")
	public ResponseEntity<Map<String, Object>> verifyemailParameters(@RequestParam String key,
			@RequestParam String email, @ModelAttribute EmailVerificationVO emailverificationVO, LoginVO loginVO) {

		Map<String, Object> map = new HashMap<String, Object>();

		emailverificationVO.setEmail(email);
		emailverificationVO.setUuid(key);

		List<EmailVerificationVO> ls = emailverificationservice.searchUser(emailverificationVO);

		EmailVerificationVO emailVerificationVO2 = ls.get(0);

		String message = null;
		boolean status = false;

		if (!ls.isEmpty()) {
			if (emailVerificationVO2.isStatus()) {

				loginVO.setUsername(email);
				List<LoginVO> loginList = loginService.searchLoginID(loginVO);
				LoginVO loginVO2 = loginList.get(0);

				emailVerificationVO2.setStatus(false);
				emailverificationservice.verifyemailinsert(emailVerificationVO2);

				loginVO2.setEnabled(1);
				loginService.insertLogin(loginVO2);

				status = true;
				message = MessageEnum.EMAIL_VERIFIED.getMessage();
			} else {
				status = false;
				message = MessageEnum.EMAIL_ALREADY_VERIFIED.getMessage();
			}
		} else {
			status = false;
			message = MessageEnum.SOMETHING_WRONG.getMessage();
		}

		map.put(ConstantEnum.STATUS.getValue(), status);
		map.put(ConstantEnum.MESSAGE.getValue(), message);

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
}
