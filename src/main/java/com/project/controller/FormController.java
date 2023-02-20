package com.project.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.project.dto.FormDataDTO;
import com.project.dto.FormDetailsDTO;
import com.project.dto.OptionsDTO;
import com.project.enums.ConstantEnum;
import com.project.enums.UserPathEnum;
import com.project.model.FormDetailsVO;
import com.project.model.FormsVO;
import com.project.model.LoginVO;
import com.project.model.ModuleVO;
import com.project.model.ProjectVO;
import com.project.service.FormsService;
import com.project.service.LoginService;
import com.project.service.ModuleService;
import com.project.service.ProjectService;
import com.project.util.BaseMethods;

@Controller
@RequestMapping(value = "user/form")
public class FormController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ModuleService moduleService;

	@Autowired
	private BaseMethods baseMethods;

	@Autowired
	private FormsService formsService;

	@GetMapping
	public ModelAndView viewForms() {
		String username = this.baseMethods.getUsername();

		List<ProjectVO> projectList = this.projectService.getActiveUserProjects(this.baseMethods.getUsername());
		ProjectVO projectVO = projectList.get(0);
		List<ModuleVO> moduleList = this.moduleService.getCurrentProjectModule(username, projectVO);

		return new ModelAndView(UserPathEnum.USER_FORMS.getPath(), ConstantEnum.PROJECT_LIST.getValue(), projectList)
				.addObject(ConstantEnum.MODULE_LIST.getValue(), moduleList);
	}

	@PostMapping
	public ResponseEntity<Object> insertForm(@ModelAttribute ProjectVO projectVO, @ModelAttribute ModuleVO moduleVO,
			@ModelAttribute FormsVO formsVO, @ModelAttribute LoginVO loginVO, @RequestBody FormDataDTO formData) {
		loginVO.setUsername(this.baseMethods.getUsername());
		List<LoginVO> loginList = this.loginService.searchLoginID(loginVO);
		formsVO.setLoginVO(loginList.get(0));

		projectVO.setId(formData.getProjectId());
		formsVO.setProjectVO(projectVO);

		moduleVO.setId(formData.getModuleId());
		formsVO.setModuleVO(moduleVO);

		formsVO.setFormName(formData.getFormName());
		formsVO.setForDescription(formData.getFormDescription());

		this.formsService.insertForm(formsVO);

		List<FormDetailsDTO> formDetails = formData.getFormDetails();

		for (FormDetailsDTO formDetail : formDetails) {
			FormDetailsVO formDetailVO = new FormDetailsVO();

			formDetailVO.setFormsVO(formsVO);

			formDetailVO.setFieldName(formDetail.getFieldName());
			formDetailVO.setFieldType(formDetail.getFieldType());

			List<OptionsDTO> options = formDetail.getOptions();
			if (options != null && !options.isEmpty()) {
				StringBuilder values = new StringBuilder();
				StringBuilder labels = new StringBuilder();

				for (OptionsDTO option : options) {
					values.append(option.getValue());
					values.append(",");
					labels.append(option.getLabel());
					labels.append(",");
				}

				formDetailVO.setLabel(labels.deleteCharAt(labels.length() - 1).toString());
				formDetailVO.setValue(values.deleteCharAt(values.length() - 1).toString());
			}

			this.formsService.insertFormDetails(formDetailVO);
		}

		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<List<FormDetailsDTO>> getFormDetails(@PathVariable Long id) {

		List<FormDetailsVO> formDetailsVOList = this.formsService.findFormDetails(id);
		List<FormDetailsDTO> formDetailsDTOList = new ArrayList<FormDetailsDTO>();

		for (FormDetailsVO formDetail : formDetailsVOList) {
			FormDetailsDTO formDetailDTO = new FormDetailsDTO();
			formDetailDTO.setFieldName(formDetail.getFieldName());
			formDetailDTO.setFieldType(formDetail.getFieldType());

			if (formDetail.getValue() != null && formDetail.getLabel() != null) {
				List<String> values = new ArrayList<String>(Arrays.asList(formDetail.getValue().split(",")));
				List<String> labels = new ArrayList<String>(Arrays.asList(formDetail.getLabel().split(",")));

				List<OptionsDTO> options = new ArrayList<OptionsDTO>();

				for (int i = 0; i < values.size(); i++) {
					OptionsDTO optionDTO = new OptionsDTO();
					optionDTO.setValue(values.get(i));
					optionDTO.setLabel(labels.get(i));

					options.add(optionDTO);
				}

				formDetailDTO.setOptions(options);
			}

			formDetailsDTOList.add(formDetailDTO);
		}
		return new ResponseEntity<List<FormDetailsDTO>>(formDetailsDTOList, HttpStatus.OK);
	}

	@PostMapping(value = "/{page}")
	public ResponseEntity<Page<FormsVO>> projectTable(@PathVariable int page,
			@RequestParam Map<String, String> allRequestParams, @ModelAttribute LoginVO loginVO) {

		int size = Integer.parseInt(allRequestParams.get("size"));
		String query = allRequestParams.get("query");
		String sort = allRequestParams.get("sort");
		String sortBy = allRequestParams.get("sortBy");
		String moduleId = allRequestParams.get("moduleId");

		Pageable requestedPage = baseMethods.getRequestedPage(page, size, sort, sortBy);
		Page<FormsVO> data;

		loginVO.setUsername(this.baseMethods.getUsername());

		Long activeModuleId;

		activeModuleId = Long.parseLong(moduleId);

		data = formsService.findAllForms(loginVO.getUsername(), activeModuleId, requestedPage);

		if ((query != null) && (!query.trim().isEmpty())) {
			data = formsService.searchInCurrentModule(activeModuleId, query.trim(), query.trim(), query.trim(),
					requestedPage);

		}
		return new ResponseEntity<Page<FormsVO>>(data, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteProject(@ModelAttribute FormsVO formsVO, @PathVariable Long id) {
		formsVO.setFormId(id);
		this.formsService.deleteForm(formsVO);

		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@GetMapping(value = "/{moduleId}/{projectId}/{formName}")
	public ResponseEntity<Boolean> checkFormName(@ModelAttribute FormsVO formVO, @ModelAttribute ModuleVO moduleVO,
			@ModelAttribute ProjectVO projectVO, @PathVariable Long moduleId, @PathVariable Long projectId,
			@PathVariable String formName) {

		String username = this.baseMethods.getUsername();

		projectVO.setId(projectId);

		formVO.setProjectVO(projectVO);

		moduleVO.setId(moduleId);
		formVO.setModuleVO(moduleVO);
		formVO.setFormName(formName);

		boolean status = this.formsService.checkFormName(formVO, username);

		return new ResponseEntity<Boolean>(status, HttpStatus.OK);
	}
}
