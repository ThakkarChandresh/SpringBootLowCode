package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.enums.ConstantEnum;
import com.project.enums.UserPathEnum;
import com.project.model.ProjectVO;
import com.project.service.ProjectService;
import com.project.util.BaseMethods;

@Controller
public class FormsController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private BaseMethods baseMethods;

	@GetMapping(value = "user/forms")
	public ModelAndView formsPage() {

		List<ProjectVO> projectList = projectService.getActiveUserProjects(this.baseMethods.getUsername());

		return new ModelAndView(UserPathEnum.USER_FORMS.getPath(), ConstantEnum.PROJECT_LIST.getValue(), projectList);
	}

}
