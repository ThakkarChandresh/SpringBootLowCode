package com.project.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.project.enums.AdminPathEnum;
import com.project.enums.ConstantEnum;
import com.project.enums.GeneralPathEnum;
import com.project.enums.MessageEnum;
import com.project.enums.UserPathEnum;
import com.project.model.ForgotPasswordVO;
import com.project.model.LoginVO;
import com.project.service.ForgotPasswordService;
import com.project.service.LoginService;
import com.project.util.BaseMethods;

@Controller
public class LoginController {
	@Autowired
	private BaseMethods baseMethods;

	@Autowired
	private ForgotPasswordService forgotPasswordService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping(value = "/")
	public ModelAndView loadLogin() {
		return new ModelAndView("redirect:/user/index");
	}

	@GetMapping(value = "/admin/index")
	public ModelAndView adminIndex(LoginVO loginVO) {
		return new ModelAndView(AdminPathEnum.ADMIN_INDEX.getPath());
	}

	@GetMapping(value = "/user/index")
	public ModelAndView userIndex() {
		return new ModelAndView(UserPathEnum.USER_INDEX.getPath());
	}

	@RequestMapping(value = "/logout", method = { RequestMethod.POST, RequestMethod.GET })
	public String viewUserDetails(ModelMap model, HttpServletResponse response, HttpServletRequest request) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
			request.getSession().invalidate();
			request.getSession().setAttribute(ConstantEnum.TEMP_STATUS.getValue(), MessageEnum.SUCCESS.getMessage());
			request.getSession().setAttribute(ConstantEnum.STATUS_TEXT.getValue(), MessageEnum.LOGOUT.getMessage());
		}
		return GeneralPathEnum.LOGIN.getPath();
	}

	@GetMapping(value = "/login")
	public ModelAndView load() {

		return new ModelAndView(GeneralPathEnum.LOGIN.getPath());
	}

	@GetMapping(value = "/403")
	public ModelAndView load403() {
		return new ModelAndView(GeneralPathEnum.LOGIN.getPath());
	}

	@GetMapping(value = "/error")
	public ModelAndView error() {
		return new ModelAndView(GeneralPathEnum.LOGIN.getPath());
	}

	@GetMapping(value = "/404")
	public ModelAndView handle404() {
		return new ModelAndView(GeneralPathEnum.HANDLE_404.getPath());
	}

	@GetMapping(value = "/forgot-password")
	public ModelAndView forgotPassword() {
		return new ModelAndView(GeneralPathEnum.FORGOT_PASSWORD.getPath(), ConstantEnum.FORGOT_PASSWORD_VO.getValue(),
				new ForgotPasswordVO());
	}

	@PostMapping(value = "/get-forgot-password-otp")
	public ResponseEntity<String> setToken(@RequestParam String email,
			@ModelAttribute ForgotPasswordVO forgotPasswordVO) {

		List<ForgotPasswordVO> list = this.forgotPasswordService.checkEmail(email);

		String otp = this.baseMethods.generateotp();
		String token = this.baseMethods.generateuuid();

		if (!list.isEmpty()) {
			forgotPasswordVO = list.get(0);

			forgotPasswordVO.setOtp(otp);
			forgotPasswordVO.setToken(token);
			forgotPasswordVO.setAttempt(0);
			forgotPasswordVO.setStatus("NOT_VERIFIED");
		} else {
			forgotPasswordVO.setEmail(email);
			forgotPasswordVO.setOtp(otp);
			forgotPasswordVO.setToken(token);
		}

		this.forgotPasswordService.setTokenAndOtp(forgotPasswordVO);

		this.baseMethods.sendOTPMail(email, otp);

		return new ResponseEntity<String>(token, HttpStatus.OK);
	}

	@PostMapping(value = "/check-otp-token")
	public ModelAndView checkOtpAndToken(@ModelAttribute ForgotPasswordVO forgotPasswordVO) {

		List<ForgotPasswordVO> forgotPasswordDetails = this.forgotPasswordService.checkTokenAndOtp(forgotPasswordVO);

		List<ForgotPasswordVO> list = this.forgotPasswordService.checkEmail(forgotPasswordVO.getEmail());
		ForgotPasswordVO forgotPasswordVO1 = list.get(0);

		int attempt = forgotPasswordVO1.getAttempt() + 1;

		if (!forgotPasswordDetails.isEmpty() && !forgotPasswordDetails.get(0).getStatus().equals("EXPIRED")) {
			forgotPasswordVO1.setAttempt(attempt);
			forgotPasswordVO1.setStatus("VERIFIED");

			this.forgotPasswordService.setTokenAndOtp(forgotPasswordVO1);

			return new ModelAndView(GeneralPathEnum.RESET_PASSWORD.getPath(), ConstantEnum.EMAIL.getValue(),
					forgotPasswordVO.getEmail());
		} else if (!forgotPasswordDetails.isEmpty() && forgotPasswordDetails.get(0).getStatus().equals("EXPIRED")) {

			String message = "Please try again";

			return new ModelAndView(GeneralPathEnum.FORGOT_PASSWORD.getPath(),
					ConstantEnum.FORGOT_PASSWORD_VO.getValue(), new ForgotPasswordVO())
							.addObject(ConstantEnum.STATUS.getValue(), "OTP has expired")
							.addObject(ConstantEnum.MESSAGE.getValue(), message);
		} else if (attempt < 3) {
			forgotPasswordVO1.setAttempt(attempt);

			this.forgotPasswordService.setTokenAndOtp(forgotPasswordVO1);

			String message = "Please try again " + (3 - attempt) + "/3 attemps left";

			return new ModelAndView(GeneralPathEnum.FORGOT_PASSWORD.getPath(),
					ConstantEnum.FORGOT_PASSWORD_VO.getValue(), forgotPasswordVO)
							.addObject(ConstantEnum.STATUS.getValue(), "Incorrect OTP")
							.addObject(ConstantEnum.MESSAGE.getValue(), message);
		} else {
			return new ModelAndView("redirect:/login");
		}
	}

	@PostMapping(value = "/reset-password")
	public ModelAndView resetPassword(@ModelAttribute LoginVO loginVO, HttpServletRequest request) {
		String username = request.getParameter("username");
		String newpassword = request.getParameter("newpassword");

		loginVO.setUsername(username);
		List<LoginVO> loginList = this.loginService.searchLoginID(loginVO);

		LoginVO loginVO2 = loginList.get(0);

		loginVO2.setPassword(bCryptPasswordEncoder.encode(newpassword));

		this.loginService.insertLogin(loginVO2);

		return new ModelAndView("redirect:/login");
	}

	@GetMapping(value = "user/test")
	public ModelAndView test() {

		return new ModelAndView("user/test");
	}
}