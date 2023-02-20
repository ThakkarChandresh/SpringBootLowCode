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
import com.project.model.FormsVO;
import com.project.model.LoginVO;
import com.project.model.ModuleVO;
import com.project.model.ProjectVO;
import com.project.service.CodeService;
import com.project.service.FormsService;
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
	private FormsService formService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private CodeService codeService;

	@GetMapping(value = "user/getActiveUserProjects")
	public ResponseEntity<List<ProjectVO>> getActiveUserProjects(@ModelAttribute LoginVO loginVO) {

		List<ProjectVO> allProjects;

		String activeUser = this.baseMethods.getUsername();

		allProjects = this.projectService.getActiveUserProjects(activeUser);

		return new ResponseEntity<List<ProjectVO>>(allProjects, HttpStatus.OK);
	}

	@GetMapping(value = "user/getProjectModule")
	public ResponseEntity<List<ModuleVO>> getProjectModule(@ModelAttribute ProjectVO projectVO,
			@RequestParam String projectId) {

		String username = this.baseMethods.getUsername();

		if (projectId != null) {
			projectVO.setId(Long.parseLong(projectId));
		}

		List<ModuleVO> modules = this.moduleService.getCurrentProjectModule(username, projectVO);

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
		String username = this.baseMethods.getUsername();

		List<ProjectVO> projectList = this.projectService.getActiveUserProjects(this.baseMethods.getUsername());
		ProjectVO projectVO = projectList.get(0);
		List<ModuleVO> moduleList = this.moduleService.getCurrentProjectModule(username, projectVO);

		return new ModelAndView(UserPathEnum.USER_FORMS.getPath())
				.addObject(ConstantEnum.MODULE_ID.getValue(), moduleId)
				.addObject(ConstantEnum.PROJECT_ID.getValue(), projectId)
				.addObject(ConstantEnum.PROJECT_LIST.getValue(), projectList)
				.addObject(ConstantEnum.MODULE_LIST.getValue(), moduleList);
	}

	@GetMapping(value = "user/addForms")
	public ModelAndView forms() {
		List<ProjectVO> projectList = this.projectService.getActiveUserProjects(this.baseMethods.getUsername());

		return new ModelAndView(UserPathEnum.USER_ADD_FORM.getPath(), ConstantEnum.PROJECT_LIST.getValue(),
				projectList);
	}

	@GetMapping(value = "user/archive-unarchive-project")
	public ResponseEntity<Object> archiveUnarchiveProject(@RequestParam Long projectId, @RequestParam boolean status) {

		this.projectService.archiveProject(projectId, status);
		this.moduleService.archiveProjectModule(projectId, status);

		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@GetMapping(value = "user/archive-unarchive-module")
	public ResponseEntity<Object> archiveUnarchiveModule(@RequestParam Long moduleId, @RequestParam boolean status) {

		this.moduleService.archiveModule(moduleId, status);

		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@GetMapping(value = "user/archive-unarchive-form")
	public ResponseEntity<Object> archiveUnarchiveForm(@RequestParam Long formId, @RequestParam boolean status) {
		this.formService.archiveForm(formId, status);

		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@GetMapping(value = "user/preview/getForm")
	public ResponseEntity<List<FormsVO>> getForm(@RequestParam Long formid) {
		List<FormsVO> form = this.formService.findForm(formid);

		return new ResponseEntity<List<FormsVO>>(form, HttpStatus.OK);
	}

	@GetMapping(value = "user/preview/add")
	public ModelAndView previewAdd(@RequestParam Long formid) {

		return new ModelAndView("user/preview/add", "formId", formid);
	}

	@GetMapping(value = "user/preview/view")
	public ModelAndView previewView(@RequestParam Long formid) {

		return new ModelAndView("user/preview/view", "formId", formid);
	}

	@GetMapping(value = "user/downloadProject")
	public ResponseEntity<Object> downloadMonolithicProject(@RequestParam Long projectId, @RequestParam String type) {
		String response = this.codeService.generateProject(projectId, type);
		System.out.println(response);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
}
