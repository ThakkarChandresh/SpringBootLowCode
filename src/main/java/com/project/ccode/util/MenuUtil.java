package com.project.ccode.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.project.model.FormsVO;
import com.project.model.LoginVO;
import com.project.model.ModuleVO;
import com.project.service.FormsService;
import com.project.util.BaseMethods;

@Component
public class MenuUtil {
	@Autowired
	private BaseMethods baseMethods;

	@Autowired
	private FormsService formService;

	private static final Logger LOGGER = LogManager.getLogger(MenuUtil.class);

	private static final Regions CLIENT_REGION = Regions.US_EAST_1;
	private static final String SOURCE_BUCKET_NAME = "baseassets";
	private static final String OBJECT_KEY_NAME = "jsp/";

	public String getSubListContent(List<FormsVO> formList) {

		StringBuilder inputStreamOutput = new StringBuilder();

		try {
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(CLIENT_REGION).build();

			S3Object object = s3Client
					.getObject(new GetObjectRequest(SOURCE_BUCKET_NAME, OBJECT_KEY_NAME.concat("menusublist.jsp")));
			InputStream objectData = object.getObjectContent();
			String rawModuleSubList = IOUtils.toString(objectData);
			String formName = "";

			for (FormsVO formsVO : formList) {
				formName = formsVO.getFormName();

				inputStreamOutput
						.append(rawModuleSubList
								.replace("[VIEW-MODULE-URL]",
										"/" + baseMethods.allLetterCaps(formsVO.getModuleVO().getModuleName())
												.toLowerCase() + "/" + baseMethods.camelize(formName))
								.replace("[VIEW-MODULE-NAME]",
										baseMethods.reverseCamelize(baseMethods.allLetterCaps(formName))));
			}
			objectData.close();

		} catch (IOException e) {
			LOGGER.error("Exception in MenuSublist IO", e);
		} catch (AmazonServiceException e) {
			LOGGER.error("Exception in MenuSublist", e);
		} catch (SdkClientException e) {
			LOGGER.error("Exception in MenuSublist", e);
		}
		return inputStreamOutput.toString();

	}

	public String getMenuListContent(List<ModuleVO> moduleList) {
		StringBuilder inputStreamOutput = new StringBuilder();

		try {
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(CLIENT_REGION).build();

			S3Object object = s3Client
					.getObject(new GetObjectRequest(SOURCE_BUCKET_NAME, OBJECT_KEY_NAME.concat("menulist.jsp")));
			InputStream objectData = object.getObjectContent();

			LoginVO loginVO = new LoginVO();
			loginVO.setUsername(baseMethods.getUsername());
			String rawModuleList = IOUtils.toString(objectData);

			for (ModuleVO currentModule : moduleList) {
				List<FormsVO> formList = this.formService.getCurrentModuleForms(loginVO, currentModule);
				inputStreamOutput.append(rawModuleList.replace("[SUB-MENU-LIST]", getSubListContent(formList))
						.replace("[MODULE-NAME]", baseMethods.reverseCamelize(currentModule.getModuleName()))
						.replace("[MODULE-ICON]", currentModule.getModuleIcon()));
			}

			objectData.close();

		} catch (IOException e) {
			LOGGER.error("Exception in Menulist IO", e);
		} catch (AmazonServiceException e) {
			LOGGER.error("Exception in Menulist", e);
		} catch (SdkClientException e) {
			LOGGER.error("Exception in Menulist", e);
		}
		return inputStreamOutput.toString();

	}

	public String getMenuContent(List<ModuleVO> moduleList) {
		String inputStreamOutput = "";

		try {
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(CLIENT_REGION).build();

			S3Object object = s3Client
					.getObject(new GetObjectRequest(SOURCE_BUCKET_NAME, OBJECT_KEY_NAME.concat("menu.jsp")));
			InputStream objectData = object.getObjectContent();

			inputStreamOutput = IOUtils.toString(objectData).replace("[MENU-LIST]", getMenuListContent(moduleList))
					.replace("[LOGO-NAME]",
							baseMethods.reverseCamelize(moduleList.get(0).getProjectVO().getProjectName()))
					.replace("[ICON-CLASS]", moduleList.get(0).getProjectVO().getProjectIcon());

			objectData.close();

		} catch (IOException e) {
			LOGGER.error("Exception in MenuUtil", e);
		} catch (AmazonServiceException e) {
			LOGGER.error("Exception in MenuUtil", e);
		} catch (SdkClientException e) {
			LOGGER.error("Exception in MenuUtil IO", e);
		}

		return inputStreamOutput;
	}
}
