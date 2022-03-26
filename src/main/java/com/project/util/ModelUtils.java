package com.project.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.model.FormDetailsVO;
import com.project.model.FormsVO;

import io.github.ccodemvc.CClassVar;
import io.github.ccodemvc.CCodeClass;
import io.github.ccodemvc.CCodeMVC;
import io.github.ccodemvc.CCodeMethod;
import io.github.ccodemvc.CCodeMethodBlock;

@Component
public class ModelUtils {

	@Autowired
	private BaseMethods baseMethods;

	public String getModelContent(FormsVO formsVO, List<FormDetailsVO> formDetails) {

		try {
			List<String> ls = new ArrayList<String>();
			ls.add("java.io.Serializable");
			ls.add("javax.persistence.Column");
			ls.add("javax.persistence.Entity");
			ls.add("javax.persistence.GeneratedValue");
			ls.add("javax.persistence.GenerationType");
			ls.add("javax.persistence.Id");
			ls.add("javax.persistence.Table");

			CCodeMVC mvc = new CCodeMVC();

			CCodeClass classS = mvc.withPackageStatement("com.project.model").withRequiredImports(ls);

			classS.addAnnotation("Entity");
			classS.addAnnotation("Table(name=\"" + baseMethods.camelize(formsVO.getFormName()) + "_table\")");
			classS.create("class", baseMethods.allLetterCaps(formsVO.getFormName() + "VO")).withAM("public");
			classS.implementS("Serializable");

			CClassVar var = classS.classVar();
			var.createVariable("serialVersionUID").withAM("public", "static", "final").type("long");
			var.assign("1L");

			var.addAnnotation("Id");
			var.addAnnotation("Column");
			var.addAnnotation("GeneratedValue(strategy = GenerationType.IDENTITY)");
			var.createVariable(baseMethods.camelize(formsVO.getFormName()) + "Id").withAM("private").type("long");

			CCodeMethod method = classS.method();

			CCodeMethodBlock methodBlock = method
					.createMethod("get" + baseMethods.camelize(formsVO.getFormName()) + "Id").withAM("public")
					.withReturnType("long").withParameters("");
			methodBlock.addReturnStatement("this.id");
			method.closeMethod();

			methodBlock = method.createMethod("set" + baseMethods.camelize(formsVO.getFormName()) + "Id")
					.withAM("public").withReturnType("void").withParameters("int id");
			methodBlock.useVariable("this.fn");
			methodBlock.assign("id");
			method.closeMethod();

			for (int i = 0; i < formDetails.size(); i++) {
				var.addAnnotation("Column");
				var.createVariable(formDetails.get(i).getFieldName()).withAM("private").type("String");

				methodBlock = method.createMethod("get" + formDetails.get(i).getFieldName()).withAM("public")
						.withReturnType("String").withParameters("");
				methodBlock.addReturnStatement("this." + formDetails.get(i).getFieldName());
				method.closeMethod();

				methodBlock = method.createMethod("set" + formDetails.get(i).getFieldName()).withAM("public")
						.withReturnType("void").withParameters("String " + formDetails.get(i).getFieldName());
				methodBlock.useVariable("this." + formDetails.get(i).getFieldName());
				methodBlock.assign(formDetails.get(i).getFieldName());
				method.closeMethod();
			}

			classS.closeClass();

			return mvc.build();
		} catch (Exception ex) {
			ex.printStackTrace();

			return "";
		}

	}
}
