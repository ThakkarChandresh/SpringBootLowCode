package com.project.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.transaction.Transactional;

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
import com.project.ccode.util.ApplicationUtils;
import com.project.ccode.util.BaseControllerUtil;
import com.project.ccode.util.ControllerUtils;
import com.project.ccode.util.MenuUtil;
import com.project.ccode.util.ModelUtils;
import com.project.ccode.util.RepositoryUtils;
import com.project.ccode.util.ServiceImplUtils;
import com.project.ccode.util.ServiceUtils;
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
	private BaseControllerUtil baseControllerUtil;

	@Autowired
	private ApplicationUtils applicationUtils;

	@Autowired
	private FormTagService formTagService;

	@Autowired
	private MenuUtil menuUtil;

	@Autowired
	private ObjectUtil objectUtil;

	@Autowired
	private LambdaUtils lambdaUtils;

	private static final Regions CLIENT_REGION = Regions.US_EAST_1;
	private static final String BUCKET_NAME = "userprojects";
	private static final AmazonS3 S3_CLIENT = AmazonS3ClientBuilder.standard().withRegion(CLIENT_REGION).build();
	private static final String SOURCE_BUCKET_NAME = "baseassets";

	public String generateProject(Long id) {
		ProjectVO projectVO = new ProjectVO();
		projectVO.setId(id);

		List<ModuleVO> moduleList = this.moduleService.getCurrentProjectModule(baseMethods.getUsername(), projectVO);

		this.createApplication(moduleList);
		this.createRepository(moduleList);
		this.createController(moduleList);
		this.createModel(moduleList);
		this.createService(moduleList);
		this.createServiceImpl(moduleList);
		this.createJsCSS(moduleList);
		this.createPom(moduleList);
		this.createJSP(moduleList);
		this.createHeaderFooter(moduleList);
		this.createMenuAndXML(moduleList);

		String prefix = baseMethods.getUsername() + "/" + moduleList.get(0).getProjectVO().getProjectName();
		List<String> ls = objectUtil.getObject(prefix);
		return lambdaUtils.invokeLmabda(ls, baseMethods.getUsername(),
				moduleList.get(0).getProjectVO().getProjectName());
	}

	private void createApplication(List<ModuleVO> moduleList) {
		ModuleVO moduleVO = moduleList.get(0);

		String stringObjKeyName = baseMethods.getUsername() + "/" + moduleVO.getProjectVO().getProjectName()
				+ "/src/main/java/com/project/" + "Application.java";

		String content = this.applicationUtils.getApplicationContent();

		// Upload a text string as a new object.
		S3_CLIENT.putObject(BUCKET_NAME, stringObjKeyName, content);
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
				String stringObjKeyName;
				String content;

				for (FormsVO formsVO : formList) {

					stringObjKeyName = baseMethods.getUsername() + "/" + moduleVO.getProjectVO().getProjectName()
							+ "/src/main/java/com/project/controller/"
							+ baseMethods.allLetterCaps(formsVO.getFormName()) + "Controller.java";
					content = this.controllerUtils.getControllerContent(formsVO);

					// Upload a text string as a new object.
					S3_CLIENT.putObject(BUCKET_NAME, stringObjKeyName, content);
				}

				stringObjKeyName = baseMethods.getUsername() + "/" + moduleVO.getProjectVO().getProjectName()
						+ "/src/main/java/com/project/controller/BaseController.java";
				content = this.baseControllerUtil.getBaseControllerContent();

				S3_CLIENT.putObject(BUCKET_NAME, stringObjKeyName, content);
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

	private void createJsCSS(List<ModuleVO> moduleList) {
		String[] cssFiles = new String[] { "all.css", "bootstrap.min.css", "bread.css", "dataTables.bootstrap4.css",
				"style.css" };
		String[] jsFiles = new String[] { "action.js", "data-table.js", "dataTables.bootstrap4.js",
				"jquery.dataTables.js", "jquery.min.js" };
		String[] webFontsFiles = new String[] { "fa-brands-400.eot", "fa-brands-400.svg", "fa-brands-400.ttf",
				"fa-brands-400.woff", "fa-brands-400.woff2", "fa-regular-400.eot", "fa-regular-400.svg",
				"fa-regular-400.ttf", "fa-regular-400.woff", "fa-regular-400.woff2", "fa-solid-900.eot",
				"fa-solid-900.svg", "fa-solid-900.ttf", "fa-solid-900.woff", "fa-solid-900.woff2" };

		ModuleVO moduleVO = moduleList.get(0);

		try {
			String destObjKeyName = baseMethods.getUsername() + "/" + moduleVO.getProjectVO().getProjectName()
					+ "/src/main/resources/";

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

			S3_CLIENT.copyObject(SOURCE_BUCKET_NAME, "properties/".concat("application.properties"), BUCKET_NAME,
					destObjKeyName.concat("application.properties"));

			S3_CLIENT.copyObject(SOURCE_BUCKET_NAME, "profile/".concat("profile.png"), BUCKET_NAME,
					destObjKeyName.concat("static/adminResources/images/profile.png"));

			S3Object object = S3_CLIENT
					.getObject(new GetObjectRequest(SOURCE_BUCKET_NAME, "css/".concat("general.css")));
			InputStream objectData = object.getObjectContent();
			String generalCss = IOUtils.toString(objectData);
			objectData.close();

			generalCss = generalCss.replace("[HEADER-BACKGROUND]", moduleVO.getProjectVO().getHeaderColor());
			generalCss = generalCss.replace("[FOOTER-BACKGROUND]", moduleVO.getProjectVO().getFooterColor());
			generalCss = generalCss.replace("[SIDEBAR-BACKGROUND]", moduleVO.getProjectVO().getMenuColor());

			S3_CLIENT.putObject(BUCKET_NAME, destObjKeyName.concat("static/adminResources/css/").concat("general.css"),
					generalCss);
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createPom(List<ModuleVO> moduleList) {

		ModuleVO moduleVO = moduleList.get(0);

		try {
			String destObjKeyName = baseMethods.getUsername() + "/" + moduleVO.getProjectVO().getProjectName() + "/";

			S3Object object = S3_CLIENT.getObject(new GetObjectRequest(SOURCE_BUCKET_NAME, "pom/".concat("pom.xml")));
			InputStream objectData = object.getObjectContent();
			String pomFile = IOUtils.toString(objectData);
			objectData.close();

			pomFile = pomFile.replace("[PROJECT-NAME]",
					baseMethods.allLetterCaps(moduleVO.getProjectVO().getProjectName()));

			S3_CLIENT.putObject(BUCKET_NAME, destObjKeyName.concat("pom.xml"), pomFile);
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createHeaderFooter(List<ModuleVO> moduleList) {

		ModuleVO moduleVO = moduleList.get(0);

		try {
			String destObjKeyName = baseMethods.getUsername() + "/" + moduleVO.getProjectVO().getProjectName()
					+ "/src/main/webapp/WEB-INF/view/";

			S3Object object = S3_CLIENT
					.getObject(new GetObjectRequest(SOURCE_BUCKET_NAME, "jsp/".concat("header.jsp")));
			InputStream objectData = object.getObjectContent();
			String headerFile = IOUtils.toString(objectData);
			objectData.close();

			headerFile = headerFile.replace("[PROJECT-NAME-HEADER]", moduleVO.getProjectVO().getProjectName());

			S3_CLIENT.putObject(BUCKET_NAME, destObjKeyName.concat("header.jsp"), headerFile);

			S3Object object1 = S3_CLIENT
					.getObject(new GetObjectRequest(SOURCE_BUCKET_NAME, "jsp/".concat("footer.jsp")));
			InputStream objectData1 = object1.getObjectContent();
			String footerFile = IOUtils.toString(objectData1);
			objectData1.close();

			footerFile = footerFile.replace("[FOOTER-CONTENT]",
					"Copyright@" + moduleVO.getProjectVO().getProjectName());

			S3_CLIENT.putObject(BUCKET_NAME, destObjKeyName.concat("footer.jsp"), footerFile);

		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createJSP(List<ModuleVO> moduleList) {
		LoginVO loginVO = new LoginVO();
		loginVO.setUsername(baseMethods.getUsername());

		String destObjKeyName = baseMethods.getUsername() + "/" + moduleList.get(0).getProjectVO().getProjectName()
				+ "/src/main/webapp/WEB-INF/view/";

		try {

			S3Object object = S3_CLIENT.getObject(new GetObjectRequest(SOURCE_BUCKET_NAME, "jsp/".concat("add.jsp")));
			InputStream objectData = object.getObjectContent();
			String rawFormJSP = IOUtils.toString(objectData);
			objectData.close();

			for (ModuleVO moduleVO : moduleList) {
				List<FormsVO> formList = this.formService.getCurrentModuleForms(loginVO, moduleVO);
				for (FormsVO formsVO : formList) {
					String addJsp = rawFormJSP.replace("[FORM-TAG]", this.formTagService.getFormTag(formsVO))
							.replace("[FORM-NAME]", formsVO.getFormName());

					S3_CLIENT.putObject(BUCKET_NAME, destObjKeyName.concat(
							baseMethods.allLetterCaps(formsVO.getModuleVO().getModuleName()).toLowerCase() + "/add"
									+ baseMethods.camelize(formsVO.getFormName()).toLowerCase() + ".jsp"),
							addJsp);
				}
			}

			object = S3_CLIENT.getObject(new GetObjectRequest(SOURCE_BUCKET_NAME, "jsp/".concat("view.jsp")));
			objectData = object.getObjectContent();
			String rawViewJSP = IOUtils.toString(objectData);

			objectData.close();
			for (ModuleVO moduleVO : moduleList) {
				List<FormsVO> formList = this.formService.getCurrentModuleForms(loginVO, moduleVO);

				for (FormsVO formsVO : formList) {
					List<FormDetailsVO> formDetailsVOList = this.formService.findFormDetails(formsVO.getFormId());

					StringBuilder fieldThList = new StringBuilder();
					StringBuilder fieldTdList = new StringBuilder();
					for (FormDetailsVO formDetailsVO : formDetailsVOList) {
						fieldThList.append("<th>" + formDetailsVO.getFieldName() + "</th>");
						fieldTdList.append("<td>${list." + formDetailsVO.getFieldName() + "}</td>");
					}
					String viewJsp = rawViewJSP.replace("[FORM-NAME]", formsVO.getFormName())
							.replace("[FIELD-TH-LIST]", fieldThList).replace("[FIELD-TD-LIST]", fieldTdList)
							.replace("[LIST-NAME]", baseMethods.camelize(formsVO.getFormName()) + "List")
							.replace("[EDIT-URL]",
									"/" + baseMethods.allLetterCaps(formsVO.getModuleVO().getModuleName()).toLowerCase()
											+ "/edit" + baseMethods.allLetterCaps(formsVO.getFormName()) + "?"
											+ baseMethods.camelize(formsVO.getFormName()) + "Id=${list."
											+ baseMethods.camelize(formsVO.getFormName()) + "Id}")
							.replace("[DELETE-URL]",
									"/" + baseMethods.allLetterCaps(formsVO.getModuleVO().getModuleName()).toLowerCase()
											+ "/delete" + baseMethods.allLetterCaps(formsVO.getFormName()) + "?"
											+ baseMethods.camelize(formsVO.getFormName()) + "Id=${list."
											+ baseMethods.camelize(formsVO.getFormName()) + "Id}");

					S3_CLIENT.putObject(BUCKET_NAME, destObjKeyName.concat(
							baseMethods.allLetterCaps(formsVO.getModuleVO().getModuleName()).toLowerCase() + "/view"
									+ baseMethods.camelize(formsVO.getFormName()).toLowerCase() + ".jsp"),
							viewJsp);
				}
			}

			S3_CLIENT.copyObject(SOURCE_BUCKET_NAME, "jsp/".concat("index.jsp"), BUCKET_NAME,
					destObjKeyName.concat("index.jsp"));

		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void createMenuAndXML(List<ModuleVO> moduleList) {
		ModuleVO moduleVO = moduleList.get(0);

		String destObjKeyName = baseMethods.getUsername() + "/" + moduleVO.getProjectVO().getProjectName()
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
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}