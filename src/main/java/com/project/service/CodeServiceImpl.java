package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.project.model.FormDetailsVO;
import com.project.model.FormsVO;
import com.project.model.LoginVO;
import com.project.model.ModuleVO;
import com.project.model.ProjectVO;
import com.project.util.BaseMethods;
import com.project.util.ControllerUtils;
import com.project.util.ModelUtils;
import com.project.util.RepositoryUtils;
import com.project.util.ServiceImplUtils;
import com.project.util.ServiceUtils;

@Service
@Transactional
public class CodeServiceImpl implements CodeService {

	@Autowired
	private BaseMethods baseMethods;

	@Autowired
	private ModuleService moduleService;

	@Autowired
	private FormsService formService;

	@Autowired
	private RepositoryUtils repositoryUtils;

	@Autowired
	private ServiceImplUtils serviceImplUtils;

	@Autowired
	private ServiceUtils serviceUtils;

	@Autowired
	private ModelUtils modelUtils;

	@Autowired
	private ControllerUtils controllerUtils;

	private static final Regions CLIENT_REGION = Regions.US_EAST_1;
	private static final String BUCKET_NAME = "userprojects";
	private static final AmazonS3 S3_CLIENT = AmazonS3ClientBuilder.standard().withRegion(CLIENT_REGION).build();

	public void generateProject(Long id) {

		ProjectVO projectVO = new ProjectVO();
		projectVO.setId(id);

		List<ModuleVO> moduleList = this.moduleService.getCurrentProjectModule(baseMethods.getUsername(), projectVO);

		this.createRepository(moduleList);
		this.createController(moduleList);
		this.createModel(moduleList);
		this.createService(moduleList);
		this.createServiceImpl(moduleList);
	}

	private void createRepository(List<ModuleVO> moduleList) {
		try {
			LoginVO loginVO = new LoginVO();
			loginVO.setUsername(baseMethods.getUsername());
			for (ModuleVO moduleVO : moduleList) {
				List<FormsVO> formList = this.formService.getCurrentModuleForms(loginVO, moduleVO);

				for (FormsVO formsVO : formList) {
					String stringObjKeyName = baseMethods.getUsername() + "/" + moduleVO.getProjectVO().getProjectName()
							+ "/src/main/java/com/project/dao/" + baseMethods.allLetterCaps(formsVO.getFormName())
							+ "DAO.java";

					String content = this.repositoryUtils.getRepositoryContent(formsVO);

					// Upload a text string as a new object.
					S3_CLIENT.putObject(BUCKET_NAME, stringObjKeyName, content);
				}
			}
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		}
	}

	private void createController(List<ModuleVO> moduleList) {
		try {
			LoginVO loginVO = new LoginVO();
			loginVO.setUsername(baseMethods.getUsername());
			for (ModuleVO moduleVO : moduleList) {

				List<FormsVO> formList = this.formService.getCurrentModuleForms(loginVO, moduleVO);

				for (FormsVO formsVO : formList) {
					String stringObjKeyName = baseMethods.getUsername() + "/" + moduleVO.getProjectVO().getProjectName()
							+ "/src/main/java/com/project/controller/" + baseMethods.allLetterCaps(formsVO.getFormName())
							+ "Controller.java";

					String content = this.controllerUtils.getControllerContent(formsVO);

					// Upload a text string as a new object.
					S3_CLIENT.putObject(BUCKET_NAME, stringObjKeyName, content);
				}

			}
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		}
	}

	private void createModel(List<ModuleVO> moduleList) {
		try {
			LoginVO loginVO = new LoginVO();
			loginVO.setUsername(baseMethods.getUsername());
			for (ModuleVO moduleVO : moduleList) {
				List<FormsVO> formList = this.formService.getCurrentModuleForms(loginVO, moduleVO);

				for (FormsVO formsVO : formList) {
					String stringObjKeyName = baseMethods.getUsername() + "/" + moduleVO.getProjectVO().getProjectName()
							+ "/src/main/java/com/project/model/" + baseMethods.allLetterCaps(formsVO.getFormName())
							+ "VO.java";

					List<FormDetailsVO> formDetails = this.formService.findFormDetails(formsVO.getFormId());

					String content = this.modelUtils.getModelContent(formsVO, formDetails);

					// Upload a text string as a new object.
					S3_CLIENT.putObject(BUCKET_NAME, stringObjKeyName, content);
				}

			}
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		}
	}

	private void createService(List<ModuleVO> moduleList) {
		try {
			LoginVO loginVO = new LoginVO();
			loginVO.setUsername(baseMethods.getUsername());
			for (ModuleVO moduleVO : moduleList) {

				List<FormsVO> formList = this.formService.getCurrentModuleForms(loginVO, moduleVO);
				for (FormsVO formsVO : formList) {
					String stringObjKeyName = baseMethods.getUsername() + "/" + moduleVO.getProjectVO().getProjectName()
							+ "/src/main/java/com/project/service/" + baseMethods.allLetterCaps(formsVO.getFormName())
							+ "Service.java";

					String content = this.serviceUtils.getServiceContent(formsVO);

					// Upload a text string as a new object.
					S3_CLIENT.putObject(BUCKET_NAME, stringObjKeyName, content);
				}

			}
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		}
	}

	private void createServiceImpl(List<ModuleVO> moduleList) {
		try {
			LoginVO loginVO = new LoginVO();
			loginVO.setUsername(baseMethods.getUsername());
			for (ModuleVO moduleVO : moduleList) {
				List<FormsVO> formList = this.formService.getCurrentModuleForms(loginVO, moduleVO);

				for (FormsVO formsVO : formList) {
					String stringObjKeyName = baseMethods.getUsername() + "/" + moduleVO.getProjectVO().getProjectName()
							+ "/src/main/java/com/project/service/" + baseMethods.allLetterCaps(formsVO.getFormName())
							+ "ServiceImpl.java";

					String content = this.serviceImplUtils.getServiceImplContent(formsVO);

					// Upload a text string as a new object.
					S3_CLIENT.putObject(BUCKET_NAME, stringObjKeyName, content);
				}

			}
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		}
	}
}