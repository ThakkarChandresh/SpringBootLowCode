package com.project.ccode.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.enums.CCodeEnum;
import com.project.model.FormDetailsVO;
import com.project.model.FormsVO;
import com.project.util.BaseMethods;

import io.github.ccodemvc.CClassVar;
import io.github.ccodemvc.CCodeClass;
import io.github.ccodemvc.CCodeMVC;
import io.github.ccodemvc.CCodeMethod;
import io.github.ccodemvc.CCodeMethodBlock;

@Component
public class ModelUtils {

	@Autowired
	private BaseMethods baseMethods;

	private static final Logger LOGGER = LogManager.getLogger(ModelUtils.class);

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
			classS.create("class", baseMethods.allLetterCaps(formsVO.getFormName() + "VO"))
					.withAM(CCodeEnum.PUBLIC.getValue());
			classS.implementS("Serializable");

			CClassVar variable = classS.classVar();
			variable.createVariable("serialVersionUID").withAM(CCodeEnum.PUBLIC.getValue(), "static", "final")
					.type("long");
			variable.assign("1L");

			variable.addAnnotation("Id");
			variable.addAnnotation("Column");
			variable.addAnnotation("GeneratedValue(strategy = GenerationType.IDENTITY)");
			variable.createVariable(baseMethods.camelize(formsVO.getFormName()) + "Id").withAM("private").type("long");

			CCodeMethod method = classS.method();

			CCodeMethodBlock methodBlock = method
					.createMethod("get" + baseMethods.allLetterCaps(formsVO.getFormName()) + "Id")
					.withAM(CCodeEnum.PUBLIC.getValue()).withReturnType("long").withParameters("");
			methodBlock
					.addReturnStatement(CCodeEnum.THIS.getValue() + baseMethods.camelize(formsVO.getFormName()) + "Id");
			method.closeMethod();

			methodBlock = method.createMethod("set" + baseMethods.allLetterCaps(formsVO.getFormName()) + "Id")
					.withAM(CCodeEnum.PUBLIC.getValue()).withReturnType("void")
					.withParameters("Long " + baseMethods.camelize(formsVO.getFormName()) + "Id");
			methodBlock.useVariable(CCodeEnum.THIS.getValue() + baseMethods.camelize(formsVO.getFormName()) + "Id");
			methodBlock.assign(baseMethods.camelize(formsVO.getFormName()) + "Id");
			method.closeMethod();

			for (int i = 0; i < formDetails.size(); i++) {
				variable.addAnnotation("Column");
				variable.createVariable(formDetails.get(i).getFieldName()).withAM("private").type("String");

				methodBlock = method.createMethod("get" + baseMethods.allLetterCaps(formDetails.get(i).getFieldName()))
						.withAM(CCodeEnum.PUBLIC.getValue()).withReturnType("String").withParameters("");
				methodBlock.addReturnStatement(CCodeEnum.THIS.getValue() + formDetails.get(i).getFieldName());
				method.closeMethod();

				methodBlock = method.createMethod("set" + baseMethods.allLetterCaps(formDetails.get(i).getFieldName()))
						.withAM(CCodeEnum.PUBLIC.getValue()).withReturnType("void")
						.withParameters("String " + formDetails.get(i).getFieldName());
				methodBlock.useVariable(CCodeEnum.THIS.getValue() + formDetails.get(i).getFieldName());
				methodBlock.assign(formDetails.get(i).getFieldName());
				method.closeMethod();
			}

			classS.closeClass();

			return mvc.build();
		} catch (Exception e) {
			LOGGER.error("Exception in ModelUtil", e);
			return "";
		}

	}
}
