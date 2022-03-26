package com.project.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.model.FormsVO;

import io.github.ccodemvc.CCodeClass;
import io.github.ccodemvc.CCodeMVC;
import io.github.ccodemvc.CCodeMethod;

@Component
public class RepositoryUtils {

	@Autowired
	private BaseMethods baseMethods;

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
			classS.extendS("JPARepository<" + formName + "VO, Long>");

			CCodeMethod method = classS.method();
			method.createMethod("findBy" + formName + "Id").withAM("public").withReturnType("List<" + formName + "VO>")
					.withParameters("long " + baseMethods.camelize(formName) + "Id");
			method.closeAbstractMethod();
			classS.closeClass();

			return mvc.build();
		} catch (IOException e) {
			e.printStackTrace();
	
			return "";
		}
	}

}
