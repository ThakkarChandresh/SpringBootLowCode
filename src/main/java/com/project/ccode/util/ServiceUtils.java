package com.project.ccode.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.enums.CCodeEnum;
import com.project.model.FormsVO;
import com.project.util.BaseMethods;

import io.github.ccodemvc.CCodeClass;
import io.github.ccodemvc.CCodeMVC;
import io.github.ccodemvc.CCodeMethod;

@Component
public class ServiceUtils {

	@Autowired
	private BaseMethods baseMethods;
	
	private static final Logger LOGGER = LogManager.getLogger(ServiceUtils.class);

	public String getServiceContent(FormsVO formsVO) {
		String formName = formsVO.getFormName();

		try {
			CCodeMVC cm = new CCodeMVC();

			List<String> ls = new ArrayList<String>();
			ls.add("java.util.List");
			ls.add("com.project.model.*");

			CCodeClass cl = cm.withPackageStatement("com.project.service").withRequiredImports(ls);
			cl.create("interface", baseMethods.allLetterCaps(formName) + "Service").withAM(CCodeEnum.PUBLIC.getValue());
			CCodeMethod cmethod = cl.method();
			cmethod.createMethod("insert").withAM(CCodeEnum.PUBLIC.getValue()).withReturnType("void").withParameters(
					baseMethods.allLetterCaps(formName) + "VO" + " " + baseMethods.camelize(formName) + "VO");
			cmethod.closeAbstractMethod();

			cmethod.createMethod("search").withAM(CCodeEnum.PUBLIC.getValue())
					.withReturnType("List<" + baseMethods.allLetterCaps(formName) + "VO>").withParameters("");
			cmethod.closeAbstractMethod();

			cmethod.createMethod("delete").withAM(CCodeEnum.PUBLIC.getValue()).withReturnType("void").withParameters(
					baseMethods.allLetterCaps(formName) + "VO" + " " + baseMethods.camelize(formName) + "VO");
			cmethod.closeAbstractMethod();

			cmethod.createMethod("edit").withAM(CCodeEnum.PUBLIC.getValue())
					.withReturnType("List<" + baseMethods.allLetterCaps(formName) + "VO>")
					.withParameters("Long " + baseMethods.camelize(formName) + "Id");
			cmethod.closeAbstractMethod();

			cl.closeClass();

			return cm.build();

		} catch (Exception e) {
			LOGGER.error("Exception in ServiceUtil", e);
			return "";
		}
	}
}
