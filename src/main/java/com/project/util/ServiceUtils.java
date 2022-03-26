package com.project.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.model.FormsVO;

import io.github.ccodemvc.CCodeClass;
import io.github.ccodemvc.CCodeMVC;
import io.github.ccodemvc.CCodeMethod;

@Component
public class ServiceUtils {

	@Autowired
	private BaseMethods baseMethods;
	
	public String getServiceContent(FormsVO formsVO) {
		String formName = formsVO.getFormName();

		try {
			CCodeMVC cm = new CCodeMVC();

			List<String> ls = new ArrayList<String>();
			ls.add("java.util.List");
			ls.add("com.project.model.*");

			CCodeClass cl = cm.withPackageStatement("com.project.service").withRequiredImports(ls);
			cl.create("interface", baseMethods.allLetterCaps(formName) +"Service").withAM("public");
			CCodeMethod cmethod = cl.method();
			cmethod.createMethod("insert").withAM("public").withReturnType("void")
					.withParameters(baseMethods.allLetterCaps(formName)+"VO"+" "+baseMethods.camelize(formName)+"VO");
			cmethod.closeAbstractMethod();

			cmethod.createMethod("search").withAM("public").withReturnType("List<"+baseMethods.allLetterCaps(formName)+"VO>")
					.withParameters("");
			cmethod.closeAbstractMethod();

			cmethod.createMethod("delete").withAM("public").withReturnType("void")
					.withParameters(baseMethods.allLetterCaps(formName)+"VO"+" "+baseMethods.camelize(formName)+"VO");
			cmethod.closeAbstractMethod();

			cmethod.createMethod("edit").withAM("public").withReturnType("List<"+baseMethods.allLetterCaps(formName)+"VO>")
				.withParameters("long "+ baseMethods.camelize(formName)+"Id");
			cmethod.closeAbstractMethod();

			cl.closeClass();

			return cm.build();

		} catch (Exception ex) {
			ex.printStackTrace();
		
			return "";
		}
	}	
}
