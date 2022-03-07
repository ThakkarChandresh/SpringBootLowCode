package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.enums.AdminPathEnum;

@Controller
public class AdminController {

	@GetMapping(value = "admin/manageTemplate")
	public ModelAndView manageTemplate() {
		return new ModelAndView(AdminPathEnum.ADMIN_MANAGE_TEMPLETE.getPath());
	}

}
