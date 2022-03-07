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
import com.project.model.ProjectVO;
import com.project.model.RegisterVO;
import com.project.service.ProjectService;
import com.project.service.UserDetailService;
import com.project.util.BaseMethods;

@Controller
@RequestMapping(value = "user/projects")
public class ProjectController {

	@Autowired
	private BaseMethods baseMethods;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private UserDetailService detailService;

	@GetMapping
	public ModelAndView projects() {

		return new ModelAndView(UserPathEnum.USER_PROJECT.getPath()).addObject(ConstantEnum.PROJECT_VO.getValue(),
				new ProjectVO());
	}

	@PostMapping
	public ModelAndView addProject(@ModelAttribute ProjectVO projectVO, @ModelAttribute LoginVO loginVO) {

		loginVO.setUsername(this.baseMethods.getUsername());

		List<RegisterVO> registerVO = this.detailService.getCurrentUser(loginVO);
		projectVO.setLoginVO(registerVO.get(0).getLoginVO());

		this.projectService.insertProject(projectVO);

		return new ModelAndView("redirect:/user/projects");
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteProject(@ModelAttribute ProjectVO projectVO, @PathVariable Long id) {
		projectVO.setId(id);
		this.projectService.deleteProject(projectVO);

		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@GetMapping(value = "/{projectId}")
	public ResponseEntity<ProjectVO> getCurrentProjectData(@ModelAttribute ProjectVO projectVO,
			@PathVariable Long projectId) {

		projectVO.setId(projectId);

		List<ProjectVO> projectData = this.projectService.searchCurrentProject(projectVO);

		return new ResponseEntity<ProjectVO>(projectData.get(0), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/{projectName}")
	public ResponseEntity<Boolean> checkProjectName(@PathVariable Long id, @PathVariable String projectName,
			@ModelAttribute LoginVO loginVO, ProjectVO projectVO) {

		loginVO.setUsername(this.baseMethods.getUsername());

		projectVO.setLoginVO(loginVO);
		projectVO.setId(id);
		projectVO.setProjectName(projectName);

		boolean status = this.projectService.checkProjectName(projectVO);

		return new ResponseEntity<Boolean>(status, HttpStatus.OK);
	}

	@PostMapping(value = "/{page}")
	public ResponseEntity<Page<ProjectVO>> loadProjectDataTable(@PathVariable int page,
			@RequestParam Map<String, String> allRequestParams) {

		int size = Integer.parseInt(allRequestParams.get("size"));
		String sort = allRequestParams.get("sort");
		String query = allRequestParams.get("query");
		String sortBy = allRequestParams.get("sortBy");
		boolean isArchive = Boolean.parseBoolean((allRequestParams.get("isArchive")));

		Pageable requestedPage = baseMethods.getRequestedPage(page, size, sort, sortBy);

		Page<ProjectVO> data;

		String username = this.baseMethods.getUsername();

		if ((query != null) && (!query.trim().isEmpty())) {
			data = projectService.searchProject(username, query.trim(), query.trim(), isArchive, requestedPage);

		} else {
			data = projectService.getAllCurrentUserProjects(username, isArchive, requestedPage);
		}
		
		return new ResponseEntity<Page<ProjectVO>>(data, HttpStatus.OK);
	}
}
