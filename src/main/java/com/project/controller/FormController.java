package com.project.controller;

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

import com.project.enums.UserPathEnum;
import com.project.model.FormsVO;
import com.project.model.LoginVO;
import com.project.service.FormsService;
import com.project.util.BaseMethods;

@Controller
@RequestMapping(value = "user/form")
public class FormController {

	@Autowired
	private BaseMethods baseMethods;

	@Autowired
	private FormsService formsService;

	@GetMapping
	public ModelAndView viewForms() {
		return new ModelAndView(UserPathEnum.USER_FORMS.getPath());
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

		data = formsService.findAllForms(activeModuleId, requestedPage);

		if ((query != null) && (!query.trim().isEmpty())) {
			data = formsService.searchInCurrentModule(activeModuleId, query.trim(), query.trim(), query.trim(),
					requestedPage);

		}
		return new ResponseEntity<Page<FormsVO>>(data, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteProject(@ModelAttribute FormsVO formsVO, @PathVariable Long id) {
		formsVO.setId(id);
		this.formsService.deleteForm(formsVO);

		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}
