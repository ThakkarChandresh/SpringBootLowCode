package com.project.ccode.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.model.FormsVO;
import com.project.util.BaseMethods;

import io.github.ccodemvc.CCodeClass;
import io.github.ccodemvc.CCodeMVC;
import io.github.ccodemvc.CCodeMethod;

@Component
public class RepositoryUtils {

	@Autowired
	private BaseMethods baseMethods;

	private static final Logger LOGGER = LogManager.getLogger(RepositoryUtils.class);
	
	public String getRepositoryContent(FormsVO formsVO) {
		String formName = baseMethods.allLetterCaps(formsVO.getFormName());

		try {
			List<String> importList = new ArrayList<String>();
			importList.add("org.springframework.stereotype.Repository");
			importList.add("org.springframework.data.jpa.repository.JpaRepository");
			importList.add("java.util.List");
			importList.add("com.project.model.*");

			CCodeMVC mvc = new CCodeMVC();

			CCodeClass classS = mvc.withPackageStatement("com.project.dao").withRequiredImports(importList);
			classS.addAnnotation("Repository");
			classS.create("interface", formName + "DAO").withAM("public");
			classS.extendS("JpaRepository<" + formName + "VO, Long>");

			CCodeMethod method = classS.method();
			method.createMethod("findBy" + formName + "Id").withAM("public").withReturnType("List<" + formName + "VO>")
					.withParameters("Long " + baseMethods.camelize(formName) + "Id");
			method.closeAbstractMethod();
			classS.closeClass();

			return mvc.build();
		} catch (IOException e) {
			LOGGER.error("Exception in RepositoryUtil", e);
			return "";
		}
	}

}
