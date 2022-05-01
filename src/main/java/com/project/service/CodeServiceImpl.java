package com.project.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.project.ccode.util.AjaxUtil;
import com.project.ccode.util.ApplicationUtils;
import com.project.ccode.util.BaseControllerUtil;
import com.project.ccode.util.ControllerUtils;
import com.project.ccode.util.MenuUtil;
import com.project.ccode.util.MicroControllerUtils;
import com.project.ccode.util.ModelUtils;
import com.project.ccode.util.RepositoryUtils;
import com.project.ccode.util.ServiceImplUtils;
import com.project.ccode.util.ServiceUtils;
import com.project.enums.TypeEnum;
import com.project.lambda.util.LambdaUtils;
import com.project.model.FormDetailsVO;
import com.project.model.FormsVO;
import com.project.model.LoginVO;
import com.project.model.ModuleVO;
import com.project.model.ProjectVO;
import com.project.s3.util.ObjectUtil;
import com.project.util.BaseMethods;

@Service
@Transactional
public class CodeServiceImpl implements CodeService {

	@Autowired
	private BaseMethods baseMethods;

	@Autowired
	private ProjectService projectService;

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

	@Autowired
	private MicroControllerUtils microControllerUtils;

	@Autowired
	private BaseControllerUtil baseControllerUtil;

	@Autowired
	private ApplicationUtils applicationUtils;

	@Autowired
	private FormTagService formTagService;

	@Autowired
	private AjaxUtil ajaxUtil;

	@Autowired
	private MenuUtil menuUtil;

	@Autowired
	private ObjectUtil objectUtil;

	@Autowired
	private LambdaUtils lambdaUtils;

	private static final Logger LOGGER = LogManager.getLogger(CodeServiceImpl.class);

	private static final Regions CLIENT_REGION = Regions.US_EAST_1;
	private static final String BUCKET_NAME = "userprojects";
	private static final AmazonS3 S3_CLIENT = AmazonS3ClientBuilder.standard().withRegion(CLIENT_REGION).build();
	private static final String SOURCE_BUCKET_NAME = "baseassets";

	public String generateProject(Long id, String type) {
		try {
			ProjectVO projectVO = new ProjectVO();
			projectVO.setId(id);

			List<ModuleVO> moduleList = this.moduleService.getCurrentProjectModule(baseMethods.getUsername(),
					projectVO);

			type = type.trim();
			projectVO = moduleList.get(0).getProjectVO();

			if (type.equals(TypeEnum.MONOLITHIC.getValue()) && !projectVO.isGeneratedMonolithic()) {
				this.createApplication(moduleList, type);
				this.createRepository(moduleList, type);
				this.createController(moduleList, type);
				this.createModel(moduleList, type);
				this.createService(moduleList, type);
				this.createServiceImpl(moduleList, type);
				this.createPom(moduleList, type);
				this.createJsCSS(moduleList, type);
				this.createJSP(moduleList, type);
				this.createHeaderFooter(moduleList, type);
				this.createMenuAndXML(moduleList, type);
				this.createProperties(moduleList, type);
			} else if (type.equals(TypeEnum.MICROSERVICE.getValue()) && !projectVO.isGeneratedMicroservice()) {
				this.createApplication(moduleList, type);
				this.createRepository(moduleList, type);
				this.createController(moduleList, type);
				this.createModel(moduleList, type);
				this.createService(moduleList, type);
				this.createServiceImpl(moduleList, type);
				this.createPom(moduleList, type);
				this.createProperties(moduleList, type);
				this.createSwaggerConfig(moduleList, type);
			}

			String prefix = type + "/" + baseMethods.getUsername() + "/" + projectVO.getProjectName();
			List<String> ls = objectUtil.getObject(prefix);

			String invokeLambda = lambdaUtils.invokeLmabda(ls, baseMethods.getUsername(), projectVO.getProjectName(),
					type);

			if (type.equals(TypeEnum.MONOLITHIC.getValue()) && !projectVO.isGeneratedMonolithic()
					&& invokeLambda != null) {
				projectService.setMonolithicStatus(id, true);
			} else if (type.equals(TypeEnum.MICROSERVICE.getValue()) && !projectVO.isGeneratedMicroservice()
					&& invokeLambda != null) {
				projectService.setMicroserviceStatus(id, true);
			}

			return invokeLambda;
		} catch (Exception e) {
			LOGGER.error("Exception In Generate Project", e);
			return "Some error occurred";
		}
	}

	private void createApplication(List<ModuleVO> moduleList, String type) {
		try {
			ModuleVO moduleVO = moduleList.get(0);

			String stringObjKeyName = type + "/" + baseMethods.getUsername() + "/"
					+ moduleVO.getProjectVO().getProjectName() + "/src/main/java/com/project/" + "Application.java";

			String content = this.applicationUtils.getApplicationContent(type);

			// Upload a text string as a new object.
			S3_CLIENT.putObject(BUCKET_NAME, stringObjKeyName, content);
		} catch (AmazonServiceException e) {
			LOGGER.error("Exception in creating Application", e);
		} catch (SdkClientException e) {
			LOGGER.error("Exception in creating Application", e);
		}
	}

	private void createRepository(List<ModuleVO> moduleList, String type) {
		try {
			LoginVO loginVO = new LoginVO();
			loginVO.setUsername(baseMethods.getUsername());
			for (ModuleVO moduleVO : moduleList) {
				List<FormsVO> formList = this.formService.getCurrentModuleForms(loginVO, moduleVO);

				for (FormsVO formsVO : formList) {
					String stringObjKeyName = type + "/" + baseMethods.getUsername() + "/"
							+ moduleVO.getProjectVO().getProjectName() + "/src/main/java/com/project/dao/"
							+ baseMethods.allLetterCaps(formsVO.getFormName()) + "DAO.java";

					String content = this.repositoryUtils.getRepositoryContent(formsVO);

					// Upload a text string as a new object.
					S3_CLIENT.putObject(BUCKET_NAME, stringObjKeyName, content);
				}
			}
		} catch (AmazonServiceException e) {
			LOGGER.error("Exception in creating Repository", e);
		} catch (SdkClientException e) {
			LOGGER.error("Exception in creating Repository", e);
		}
	}

	private void createController(List<ModuleVO> moduleList, String type) {
		try {
			LoginVO loginVO = new LoginVO();
			loginVO.setUsername(baseMethods.getUsername());
			for (ModuleVO moduleVO : moduleList) {

				List<FormsVO> formList = this.formService.getCurrentModuleForms(loginVO, moduleVO);
				String stringObjKeyName;
				String content;

				for (FormsVO formsVO : formList) {

					stringObjKeyName = type + "/" + baseMethods.getUsername() + "/"
							+ moduleVO.getProjectVO().getProjectName() + "/src/main/java/com/project/controller/"
							+ baseMethods.allLetterCaps(formsVO.getFormName()) + "Controller.java";

					if (type.equals(TypeEnum.MICROSERVICE.getValue())) {
						content = this.microControllerUtils.getMicroControllerContent(formsVO);
					} else if (type.equals(TypeEnum.MONOLITHIC.getValue())) {
						content = this.controllerUtils.getControllerContent(formsVO);
					} else {
						content = "";
					}
					// Upload a text string as a new object.
					S3_CLIENT.putObject(BUCKET_NAME, stringObjKeyName, content);
				}

				if (type.equals(TypeEnum.MONOLITHIC.getValue())) {
					stringObjKeyName = type + "/" + baseMethods.getUsername() + "/"
							+ moduleVO.getProjectVO().getProjectName()
							+ "/src/main/java/com/project/controller/BaseController.java";
					content = this.baseControllerUtil.getBaseControllerContent();

					S3_CLIENT.putObject(BUCKET_NAME, stringObjKeyName, content);
				}
			}
		} catch (AmazonServiceException e) {
			LOGGER.error("Exception in creating Controller", e);
		} catch (SdkClientException e) {
			LOGGER.error("Exception in creating Controller", e);
		}
	}

	private void createModel(List<ModuleVO> moduleList, String type) {
		try {
			LoginVO loginVO = new LoginVO();
			loginVO.setUsername(baseMethods.getUsername());
			for (ModuleVO moduleVO : moduleList) {
				List<FormsVO> formList = this.formService.getCurrentModuleForms(loginVO, moduleVO);

				for (FormsVO formsVO : formList) {
					String stringObjKeyName = type + "/" + baseMethods.getUsername() + "/"
							+ moduleVO.getProjectVO().getProjectName() + "/src/main/java/com/project/model/"
							+ baseMethods.allLetterCaps(formsVO.getFormName()) + "VO.java";

					List<FormDetailsVO> formDetails = this.formService.findFormDetails(formsVO.getFormId());

					String content = this.modelUtils.getModelContent(formsVO, formDetails);

					// Upload a text string as a new object.
					S3_CLIENT.putObject(BUCKET_NAME, stringObjKeyName, content);
				}

			}
		} catch (AmazonServiceException e) {
			LOGGER.error("Exception in creating Model", e);
		} catch (SdkClientException e) {
			LOGGER.error("Exception in creating Model", e);
		}
	}

	private void createService(List<ModuleVO> moduleList, String type) {
		try {
			LoginVO loginVO = new LoginVO();
			loginVO.setUsername(baseMethods.getUsername());
			for (ModuleVO moduleVO : moduleList) {

				List<FormsVO> formList = this.formService.getCurrentModuleForms(loginVO, moduleVO);
				for (FormsVO formsVO : formList) {
					String stringObjKeyName = type + "/" + baseMethods.getUsername() + "/"
							+ moduleVO.getProjectVO().getProjectName() + "/src/main/java/com/project/service/"
							+ baseMethods.allLetterCaps(formsVO.getFormName()) + "Service.java";

					String content = this.serviceUtils.getServiceContent(formsVO);

					// Upload a text string as a new object.
					S3_CLIENT.putObject(BUCKET_NAME, stringObjKeyName, content);
				}

			}
		} catch (AmazonServiceException e) {
			LOGGER.error("Exception in creating Service", e);
		} catch (SdkClientException e) {
			LOGGER.error("Exception in creating Service", e);
		}
	}

	private void createServiceImpl(List<ModuleVO> moduleList, String type) {
		try {
			LoginVO loginVO = new LoginVO();
			loginVO.setUsername(baseMethods.getUsername());
			for (ModuleVO moduleVO : moduleList) {
				List<FormsVO> formList = this.formService.getCurrentModuleForms(loginVO, moduleVO);

				for (FormsVO formsVO : formList) {
					String stringObjKeyName = type + "/" + baseMethods.getUsername() + "/"
							+ moduleVO.getProjectVO().getProjectName() + "/src/main/java/com/project/service/"
							+ baseMethods.allLetterCaps(formsVO.getFormName()) + "ServiceImpl.java";

					String content = this.serviceImplUtils.getServiceImplContent(formsVO);

					// Upload a text string as a new object.
					S3_CLIENT.putObject(BUCKET_NAME, stringObjKeyName, content);
				}

			}
		} catch (AmazonServiceException e) {
			LOGGER.error("Exception in creating ServiceImpl", e);
		} catch (SdkClientException e) {
			LOGGER.error("Exception in creating ServiceImpl", e);
		}
	}

	private void createJsCSS(List<ModuleVO> moduleList, String type) {
		String[] cssFiles = new String[] { "all.css", "bootstrap.min.css", "dataTables.bootstrap4.css", "style.css",
				"general.css" };
		String[] jsFiles = new String[] { "action.js", "data-table.js", "dataTables.bootstrap4.js",
				"jquery.dataTables.js", "jquery.min.js", "bootstrap.min.js" };
		String[] webFontsFiles = new String[] { "fa-brands-400.eot", "fa-brands-400.svg", "fa-brands-400.ttf",
				"fa-brands-400.woff", "fa-brands-400.woff2", "fa-regular-400.eot", "fa-regular-400.svg",
				"fa-regular-400.ttf", "fa-regular-400.woff", "fa-regular-400.woff2", "fa-solid-900.eot",
				"fa-solid-900.svg", "fa-solid-900.ttf", "fa-solid-900.woff", "fa-solid-900.woff2" };

		ModuleVO moduleVO = moduleList.get(0);

		try {
			String destObjKeyName = type + "/" + baseMethods.getUsername() + "/"
					+ moduleVO.getProjectVO().getProjectName() + "/src/main/resources/";

			for (String cssFile : cssFiles) {
				S3_CLIENT.copyObject(SOURCE_BUCKET_NAME, "css/".concat(cssFile), BUCKET_NAME,
						destObjKeyName.concat("static/adminResources/css/").concat(cssFile));
			}
			for (String jsFile : jsFiles) {
				S3_CLIENT.copyObject(SOURCE_BUCKET_NAME, "js/".concat(jsFile), BUCKET_NAME,
						destObjKeyName.concat("static/adminResources/js/").concat(jsFile));
			}
			for (String webFonts : webFontsFiles) {
				S3_CLIENT.copyObject(SOURCE_BUCKET_NAME, "webfonts/".concat(webFonts), BUCKET_NAME,
						destObjKeyName.concat("static/adminResources/webfonts/").concat(webFonts));
			}
			LoginVO loginVO = new LoginVO();
			loginVO.setUsername(baseMethods.getUsername());

			for (ModuleVO currentModule : moduleList) {

				List<FormsVO> formList = this.formService.getCurrentModuleForms(loginVO, currentModule);
				for (FormsVO formsVO : formList) {
					String content = this.ajaxUtil.getAjaxContent(formsVO);

					S3_CLIENT.putObject(BUCKET_NAME, destObjKeyName.concat("static/adminResources/js/custom/edit"
							+ baseMethods.allLetterCaps(formsVO.getFormName()) + ".js"), content);
				}
			}

			S3_CLIENT.copyObject(SOURCE_BUCKET_NAME, "profile/".concat("profile.png"), BUCKET_NAME,
					destObjKeyName.concat("static/adminResources/images/profile.png"));

		} catch (AmazonServiceException e) {
			LOGGER.error("Exception in creating static files", e);
		} catch (SdkClientException e) {
			LOGGER.error("Exception in creating static files", e);
		}
	}

	private void createProperties(List<ModuleVO> moduleList, String type) {

		ModuleVO moduleVO = moduleList.get(0);

		try {
			String destObjKeyName = type + "/" + baseMethods.getUsername() + "/"
					+ moduleVO.getProjectVO().getProjectName() + "/src/main/resources/";

			S3_CLIENT.copyObject(SOURCE_BUCKET_NAME, "properties/".concat("application.properties"), BUCKET_NAME,
					destObjKeyName.concat("application.properties"));

		} catch (AmazonServiceException e) {
			LOGGER.error("Exception in creating properties files", e);
		} catch (SdkClientException e) {
			LOGGER.error("Exception in creating properties files", e);
		}
	}

	private void createPom(List<ModuleVO> moduleList, String type) {

		ModuleVO moduleVO = moduleList.get(0);

		try {
			String destObjKeyName = type + "/" + baseMethods.getUsername() + "/"
					+ moduleVO.getProjectVO().getProjectName() + "/";

			S3Object object = S3_CLIENT.getObject(new GetObjectRequest(SOURCE_BUCKET_NAME, "pom/".concat("pom.xml")));
			InputStream objectData = object.getObjectContent();
			String pomFile = IOUtils.toString(objectData);

			if (type.equals(TypeEnum.MONOLITHIC.getValue())) {
				objectData.close();
				object.close();
				object = S3_CLIENT
						.getObject(new GetObjectRequest(SOURCE_BUCKET_NAME, "pom/".concat("monolothicaddon.txt")));
				objectData = object.getObjectContent();
				String addonMonolithicdependecy = IOUtils.toString(objectData);
				pomFile = pomFile.replace("[ADD-ON-DEPENDENCY]", addonMonolithicdependecy).replace("[PACKAGING]",
						"war");

			} else if (type.equals(TypeEnum.MICROSERVICE.getValue())) {
				objectData.close();
				object.close();
				object = S3_CLIENT
						.getObject(new GetObjectRequest(SOURCE_BUCKET_NAME, "pom/".concat("microserviceaddon.txt")));
				objectData = object.getObjectContent();
				String addonMicroServicedependecy = IOUtils.toString(objectData);
				pomFile = pomFile.replace("[ADD-ON-DEPENDENCY]", addonMicroServicedependecy).replace("[PACKAGING]",
						"jar");
			}
			objectData.close();
			object.close();

			pomFile = pomFile.replace("[PROJECT-NAME]",
					baseMethods.allLetterCaps(moduleVO.getProjectVO().getProjectName()));
			S3_CLIENT.putObject(BUCKET_NAME, destObjKeyName.concat("pom.xml"), pomFile);
		} catch (AmazonServiceException e) {
			LOGGER.error("Exception in creating pom", e);
		} catch (SdkClientException e) {
			LOGGER.error("Exception in creating pom", e);
		} catch (IOException e) {
			LOGGER.error("Exception in pom IO", e);
		}
	}

	private void createHeaderFooter(List<ModuleVO> moduleList, String type) {

		ModuleVO moduleVO = moduleList.get(0);

		try {
			String destObjKeyName = type + "/" + baseMethods.getUsername() + "/"
					+ moduleVO.getProjectVO().getProjectName() + "/src/main/webapp/WEB-INF/view/";

			S3Object object = S3_CLIENT
					.getObject(new GetObjectRequest(SOURCE_BUCKET_NAME, "jsp/".concat("header.jsp")));
			InputStream objectData = object.getObjectContent();
			String headerFile = IOUtils.toString(objectData);
			objectData.close();

			headerFile = headerFile.replace("[PROJECT-NAME-HEADER]",
					baseMethods.reverseCamelize(moduleVO.getProjectVO().getProjectName()));

			S3_CLIENT.putObject(BUCKET_NAME, destObjKeyName.concat("header.jsp"), headerFile);

			S3Object object1 = S3_CLIENT
					.getObject(new GetObjectRequest(SOURCE_BUCKET_NAME, "jsp/".concat("footer.jsp")));
			InputStream objectData1 = object1.getObjectContent();
			String footerFile = IOUtils.toString(objectData1);
			objectData1.close();

			footerFile = footerFile.replace("[FOOTER-CONTENT]",
					"Copyright@" + baseMethods.reverseCamelize(moduleVO.getProjectVO().getProjectName()));

			S3_CLIENT.putObject(BUCKET_NAME, destObjKeyName.concat("footer.jsp"), footerFile);

		} catch (AmazonServiceException e) {
			LOGGER.error("Exception in creating header-footer", e);
		} catch (SdkClientException e) {
			LOGGER.error("Exception in creating header-footer", e);
		} catch (IOException e) {
			LOGGER.error("Exception in header-footer IO", e);
		}
	}

	private void createJSP(List<ModuleVO> moduleList, String type) {
		LoginVO loginVO = new LoginVO();
		loginVO.setUsername(baseMethods.getUsername());

		String destObjKeyName = type + "/" + baseMethods.getUsername() + "/"
				+ moduleList.get(0).getProjectVO().getProjectName() + "/src/main/webapp/WEB-INF/view/";

		try {
			S3Object object = S3_CLIENT.getObject(new GetObjectRequest(SOURCE_BUCKET_NAME, "jsp/".concat("view.jsp")));
			InputStream objectData = object.getObjectContent();
			String rawViewJSP = IOUtils.toString(objectData);

			objectData.close();
			for (ModuleVO moduleVO : moduleList) {
				List<FormsVO> formList = this.formService.getCurrentModuleForms(loginVO, moduleVO);

				for (FormsVO formsVO : formList) {
					List<FormDetailsVO> formDetailsVOList = this.formService.findFormDetails(formsVO.getFormId());

					StringBuilder fieldThList = new StringBuilder();
					StringBuilder fieldTdList = new StringBuilder();
					for (FormDetailsVO formDetailsVO : formDetailsVOList) {
						fieldThList
								.append("<th>" + baseMethods.reverseCamelize(formDetailsVO.getFieldName()) + "</th>");
						fieldTdList.append("<td>${list." + formDetailsVO.getFieldName() + "}</td>");
					}
					String viewJsp = rawViewJSP
							.replace("[FORM-NAME]", baseMethods.reverseCamelize(formsVO.getFormName()))
							.replace("[FIELD-TH-LIST]", fieldThList).replace("[FIELD-TD-LIST]", fieldTdList)
							.replace("[LIST-NAME]", baseMethods.camelize(formsVO.getFormName()) + "List")
							.replace("[PRIMARY-KEY]", "${list." + baseMethods.camelize(formsVO.getFormName()) + "Id}")
							.replace("[DELETE-URL]",
									"/" + baseMethods.allLetterCaps(formsVO.getModuleVO().getModuleName()).toLowerCase()
											+ "/delete" + baseMethods.allLetterCaps(formsVO.getFormName()) + "?"
											+ baseMethods.camelize(formsVO.getFormName()) + "Id=${list."
											+ baseMethods.camelize(formsVO.getFormName()) + "Id}")
							.replace("[FORM-TAG]", this.formTagService.getFormTag(formsVO))
							.replace("[CAP-FORM-NAME]", baseMethods.allLetterCaps(formsVO.getFormName()))
							.replace("[MODULE-ICON]", moduleVO.getModuleIcon());

					S3_CLIENT.putObject(BUCKET_NAME,
							destObjKeyName.concat(
									baseMethods.allLetterCaps(formsVO.getModuleVO().getModuleName()).toLowerCase() + "/"
											+ baseMethods.camelize(formsVO.getFormName()).toLowerCase() + ".jsp"),
							viewJsp);
				}
			}

			S3_CLIENT.copyObject(SOURCE_BUCKET_NAME, "jsp/".concat("index.jsp"), BUCKET_NAME,
					destObjKeyName.concat("index.jsp"));

		} catch (AmazonServiceException e) {
			LOGGER.error("Exception in creating Jsp", e);
		} catch (SdkClientException e) {
			LOGGER.error("Exception in creating Jsp", e);
		} catch (IOException e) {
			LOGGER.error("Exception in Jsp IO", e);
		}

	}

	private void createMenuAndXML(List<ModuleVO> moduleList, String type) {
		ModuleVO moduleVO = moduleList.get(0);

		String destObjKeyName = type + "/" + baseMethods.getUsername() + "/" + moduleVO.getProjectVO().getProjectName()
				+ "/src/main/webapp/WEB-INF/";
		try {

			LoginVO loginVO = new LoginVO();
			loginVO.setUsername(baseMethods.getUsername());

			S3_CLIENT.putObject(BUCKET_NAME, destObjKeyName.concat("view/menu.jsp"),
					this.menuUtil.getMenuContent(moduleList));

			S3Object object = S3_CLIENT.getObject(new GetObjectRequest(SOURCE_BUCKET_NAME, "xml/".concat("web.xml")));
			InputStream objectData = object.getObjectContent();
			String xmlFile = IOUtils.toString(objectData);
			xmlFile = xmlFile.replace("[PROJECT-NAME]",
					baseMethods.allLetterCaps(moduleVO.getProjectVO().getProjectName()));
			objectData.close();

			S3_CLIENT.putObject(BUCKET_NAME, destObjKeyName.concat("web.xml"), xmlFile);

		} catch (AmazonServiceException e) {
			LOGGER.error("Exception in creating Menu-xml", e);
		} catch (SdkClientException e) {
			LOGGER.error("Exception in creating Menu-xml", e);
		} catch (IOException e) {
			LOGGER.error("Exception in Menu-xml IO", e);
		}
	}

	private void createSwaggerConfig(List<ModuleVO> moduleList, String type) {

		ModuleVO moduleVO = moduleList.get(0);

		try {
			String destObjKeyName = type + "/" + baseMethods.getUsername() + "/"
					+ moduleVO.getProjectVO().getProjectName() + "/src/main/java/com/project/config/";

			S3Object object = S3_CLIENT
					.getObject(new GetObjectRequest(SOURCE_BUCKET_NAME, "config/".concat("SwaggerConfig.java")));
			InputStream objectData = object.getObjectContent();
			String swaggerConfig = IOUtils.toString(objectData);
			swaggerConfig = swaggerConfig.replace("[PROJECT-NAME]",
					baseMethods.allLetterCaps(moduleVO.getProjectVO().getProjectName()));
			objectData.close();

			S3_CLIENT.putObject(BUCKET_NAME, destObjKeyName.concat("SwaggerConfig.java"), swaggerConfig);

		} catch (AmazonServiceException e) {
			LOGGER.error("Exception in creating Swagger Congif", e);
		} catch (SdkClientException e) {
			LOGGER.error("Exception in creating Swagger Congif", e);
		} catch (IOException e) {
			LOGGER.error("Exception in header-footer IO", e);
		}
	}

}