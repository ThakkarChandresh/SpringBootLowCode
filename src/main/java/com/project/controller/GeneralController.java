package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.project.enums.ConstantEnum;
import com.project.enums.UserPathEnum;
import com.project.model.LoginVO;
import com.project.model.ModuleVO;
import com.project.model.ProjectVO;
import com.project.service.ModuleService;
import com.project.service.ProjectService;
import com.project.util.BaseMethods;

@Controller
public class GeneralController {

	@Autowired
	private BaseMethods baseMethods;

	@Autowired
	private ModuleService moduleService;

	@Autowired
	private ProjectService projectService;

	@GetMapping(value = "user/getActiveUserProjects")
	public ResponseEntity<List<ProjectVO>> getActiveUserProjects(@ModelAttribute LoginVO loginVO) {

		List<ProjectVO> allProjects;

		String activeUser = this.baseMethods.getUsername();

		allProjects = this.projectService.getActiveUserProjects(activeUser);

		return new ResponseEntity<List<ProjectVO>>(allProjects, HttpStatus.OK);
	}

	@GetMapping(value = "user/getProjectModule")
	public ResponseEntity<List<ModuleVO>> getProjectModule(@ModelAttribute LoginVO loginVO,
			@ModelAttribute ProjectVO projectVO, @RequestParam String projectId) {

		loginVO.setUsername(this.baseMethods.getUsername());

		if (projectId != null) {
			projectVO.setId(Long.parseLong(projectId));
		}

		List<ModuleVO> modules = this.moduleService.getCurrentProjectModule(projectVO);

		return new ResponseEntity<List<ModuleVO>>(modules, HttpStatus.OK);
	}

	@GetMapping(value = "user/clickedProjectModules")
	public ModelAndView clickedProjectModules(@RequestParam Long projectId) {

		List<ProjectVO> projectList = this.projectService.getActiveUserProjects(this.baseMethods.getUsername());

		return new ModelAndView(UserPathEnum.USER_MODULE.getPath(), "projectId", projectId)
				.addObject(ConstantEnum.MODULE_VO.getValue(), new ModuleVO())
				.addObject(ConstantEnum.PROJECT_LIST.getValue(), projectList);
	}

	@GetMapping(value = "user/clickedModuleForms")
	public ModelAndView clickedModuleForms(@RequestParam Long moduleId, @RequestParam Long projectId) {
		return new ModelAndView(UserPathEnum.USER_FORMS.getPath())
				.addObject(ConstantEnum.MODULE_ID.getValue(), moduleId)
				.addObject(ConstantEnum.PROJECT_ID.getValue(), projectId);
	}

	@GetMapping(value = "user/addForms")
	public ModelAndView forms() {
		List<ProjectVO> projectList = this.projectService.getActiveUserProjects(this.baseMethods.getUsername());

		return new ModelAndView(UserPathEnum.USER_ADD_FORM.getPath(), ConstantEnum.PROJECT_LIST.getValue(),
				projectList);
	}

	@GetMapping(value = "user/archive-unarchive-project")
	public ResponseEntity<Object> archiveUnarchiveProject(@RequestParam Long projectId,@RequestParam boolean status) {
		
		this.projectService.archiveProject(projectId, status);
		this.moduleService.archiveProjectModule(projectId, status);

		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@GetMapping(value = "user/archive-unarchive-module")
	public ResponseEntity<Object> archiveUnarchiveModule(@RequestParam Long moduleId,@RequestParam boolean status) {
		
		this.moduleService.archiveModule(moduleId, status);

		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}
