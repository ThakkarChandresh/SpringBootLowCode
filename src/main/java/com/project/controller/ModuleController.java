package com.project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping(value = "user/modules")
public class ModuleController {

	@Autowired
	private BaseMethods baseMethods;

	@Autowired
	private ModuleService moduleService;

	@Autowired
	private ProjectService projectService;

	// READ
	@GetMapping
	public ModelAndView getModules() {

		List<ProjectVO> projectList = this.projectService.getActiveUserProjects(this.baseMethods.getUsername());

		return new ModelAndView(UserPathEnum.USER_MODULE.getPath())
				.addObject(ConstantEnum.MODULE_VO.getValue(), new ModuleVO())
				.addObject(ConstantEnum.PROJECT_LIST.getValue(), projectList);
	}

	// CREATE AND UPDATE
	@PostMapping
	public ModelAndView addModule(@ModelAttribute ModuleVO moduleVO) {

		this.moduleService.addModule(moduleVO);

		return new ModelAndView("redirect:/user/clickedProjectModules?projectId=" + moduleVO.getProjectVO().getId());
	}

	// DELETE
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteModule(@ModelAttribute ModuleVO moduleVO, @PathVariable Long id) {
		moduleVO.setId(id);

		this.moduleService.deleteModule(moduleVO);

		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	// READ MODULE BASED ON MODULEID
	@GetMapping(value = "/{moduleId}")
	public ResponseEntity<ModuleVO> getCurrentModuleData(@ModelAttribute ModuleVO moduleVO,
			@PathVariable Long moduleId) {

		moduleVO.setId(moduleId);

		List<ModuleVO> currentModule = this.moduleService.getCurrentModuleData(moduleVO);

		return new ResponseEntity<ModuleVO>(currentModule.get(0), HttpStatus.OK);
	}

	// READ MODULE FOR CHECKING SAME MODULE NAME EXISTS IN THAT PROJECT OR NOT
	@GetMapping(value = "/{moduleId}/{projectId}/{moduleName}")
	public ResponseEntity<Boolean> checkModuleName(@ModelAttribute ModuleVO moduleVO,
			@ModelAttribute ProjectVO projectVO, @PathVariable Long moduleId, @PathVariable Long projectId,
			@PathVariable String moduleName) {

		projectVO.setId(projectId);

		moduleVO.setProjectVO(projectVO);

		moduleVO.setId(moduleId);
		moduleVO.setModuleName(moduleName);

		boolean status = this.moduleService.checkModuleName(moduleVO);

		return new ResponseEntity<Boolean>(status, HttpStatus.OK);
	}

	// READ AND MODULE PAGE LOADING
	@PostMapping(value = "/{page}")
	public ResponseEntity<Page<ModuleVO>> loadProjectDataTable(@PathVariable int page,
			@RequestParam Map<String, String> allRequestParams, @ModelAttribute LoginVO loginVO) {

		int size = Integer.parseInt(allRequestParams.get("size"));
		String query = allRequestParams.get("query");
		String sort = allRequestParams.get("sort");
		String sortBy = allRequestParams.get("sortBy");
		String projectId = allRequestParams.get("projectId");
		boolean isArchive = Boolean.parseBoolean((allRequestParams.get("isArchive")));

		loginVO.setUsername(this.baseMethods.getUsername());

		Long activeProjectId;
		List<ProjectVO> projectList;

		if (projectId != null) {
			activeProjectId = Long.parseLong(projectId);
		} else {
			projectList = this.projectService.getActiveUserProjects(this.baseMethods.getUsername());

			if (!projectList.isEmpty()) {
				activeProjectId = projectList.get(0).getId();
			} else {
				activeProjectId = 0L;
			}
		}

		Pageable requestedPage = baseMethods.getRequestedPage(page, size, sort, sortBy);
		Page<ModuleVO> data;

		if ((query != null) && (!query.trim().isEmpty())) {
			data = moduleService.searchCurrentProjectModules(activeProjectId, query.trim(), query.trim(), query.trim(),isArchive,
					requestedPage);
		} else {
			data = moduleService.searchByProjectId(activeProjectId, isArchive, requestedPage);
		}

		return new ResponseEntity<Page<ModuleVO>>(data, HttpStatus.OK);
	}
}
