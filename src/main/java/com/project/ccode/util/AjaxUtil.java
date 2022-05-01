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
import com.project.model.FormDetailsVO;
import com.project.model.FormsVO;
import com.project.service.FormsService;
import com.project.util.BaseMethods;

@Component
public class AjaxUtil {
	private static final Logger LOGGER = LogManager.getLogger(AjaxUtil.class);

	@Autowired
	private BaseMethods baseMethods;

	@Autowired
	private FormsService formService;

	private static final String JQUERY_STRING = "$('#[FIELD-NAME]').val(ajaxResponse.[FIELD-NAME]);";
	private static final Regions CLIENT_REGION = Regions.US_EAST_1;
	private static final String SOURCE_BUCKET_NAME = "baseassets";
	private static final String OBJECT_KEY_NAME = "js/";
	private String rawEdit;

	public String getAjaxContent(FormsVO formsVO) {
		try {
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(CLIENT_REGION).build();

			S3Object object = s3Client
					.getObject(new GetObjectRequest(SOURCE_BUCKET_NAME, OBJECT_KEY_NAME.concat("edit.js")));
			InputStream objectData = object.getObjectContent();
			rawEdit = IOUtils.toString(objectData);

			rawEdit = rawEdit.replace("[PRIMARY-KEY]", baseMethods.camelize(formsVO.getFormName()) + "Id")
					.replace("[MODULE-NAME]", baseMethods.camelize(formsVO.getModuleVO().getModuleName()).toLowerCase())
					.replace("[CAP-FORM-NAME]", baseMethods.allLetterCaps(formsVO.getFormName()))
					.replace("[REV-CAM-FORM-NAME]", baseMethods.reverseCamelize(formsVO.getFormName()));

			List<FormDetailsVO> formDetailsVOList = this.formService.findFormDetails(formsVO.getFormId());

			StringBuilder formElements = new StringBuilder();

			for (FormDetailsVO formDetail : formDetailsVOList) {
				formElements.append(JQUERY_STRING.replace("[FIELD-NAME]", formDetail.getFieldName()) + "\n");
			}
			formElements
					.append(JQUERY_STRING.replace("[FIELD-NAME]", baseMethods.camelize(formsVO.getFormName()) + "Id"));
			rawEdit = rawEdit.replace("[FORM-ELEMENTS]", formElements.toString());

		} catch (IOException e) {
			LOGGER.error("Exception in MenuSublist IO", e);
		} catch (AmazonServiceException e) {
			LOGGER.error("Exception in MenuSublist", e);
		} catch (SdkClientException e) {
			LOGGER.error("Exception in MenuSublist", e);
		}

		return rawEdit;
	}

}
