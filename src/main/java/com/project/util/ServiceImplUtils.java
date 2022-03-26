package com.project.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.model.FormsVO;

import io.github.ccodemvc.CClassVar;
import io.github.ccodemvc.CCodeClass;
import io.github.ccodemvc.CCodeMVC;
import io.github.ccodemvc.CCodeMethod;
import io.github.ccodemvc.CCodeMethodBlock;
import io.github.ccodemvc.CCodeMethodCall;

@Component
public class ServiceImplUtils {

	@Autowired
	private BaseMethods baseMethods;

	public String getServiceImplContent(FormsVO formsVO) {
		String formName = formsVO.getFormName();

		try {

			List<String> importList = new ArrayList<String>();
			importList.add("java.util.List");
			importList.add("javax.transaction.Transactional");
			importList.add("org.springframework.beans.factory.annotation.Autowired");
			importList.add("org.springframework.stereotype.Service");
			importList.add("com.project.dao.*");
			importList.add("com.project.model.*");

			CCodeMVC mvc = new CCodeMVC();
			CCodeClass classS = mvc.withPackageStatement("com.base.service").withRequiredImports(importList);

			classS.addAnnotation("Service");
			classS.addAnnotation("Transactional");
			classS.create("class", baseMethods.allLetterCaps(formName) + "ServiceImpl").withAM("public");
			classS.implementS(baseMethods.allLetterCaps(formName) + "Service");

			CClassVar classVar = classS.classVar();
			classVar.addAnnotation("Autowired");
			classVar.createVariable(baseMethods.camelize(formName + "DAO")).withAM("private")
					.type(baseMethods.allLetterCaps(formName) + "DAO");

			CCodeMethod cmethod = classS.method();
			cmethod.addAnnotation("Override");
			CCodeMethodBlock methodblock = cmethod.createMethod("insert").withAM("public").withReturnType("void")
					.withParameters(
							baseMethods.allLetterCaps(formName) + "VO" + " " + baseMethods.camelize(formName) + "VO");
			methodblock.addStatement(
					CCodeMethodCall.callMethodUsingObject("this." + baseMethods.camelize(formName) + "DAO", "save"));
			cmethod.closeMethod();

			cmethod.addAnnotation("Override");
			cmethod.createMethod("search").withAM("public").withReturnType("List<" + formName + "VO>").withParameters("");

			methodblock.addReturnStatement(
					CCodeMethodCall.callMethodUsingObject("this." + baseMethods.camelize(formName) + "DAO", "findAll"));
			cmethod.closeMethod();

			cmethod.addAnnotation("Override");
			cmethod.createMethod("delete").withAM("public").withReturnType("void").withParameters(
					baseMethods.allLetterCaps(formName) + "VO" + " " + baseMethods.camelize(formName) + "VO");
			methodblock.addStatement(
					CCodeMethodCall.callMethodUsingObject("this." + baseMethods.camelize(formName) + "DAO", "delete"));
			cmethod.closeMethod();

			cmethod.addAnnotation("Override");
			cmethod.createMethod("edit").withAM("public")
					.withReturnType("List<" + baseMethods.allLetterCaps(formName) + "VO>")
					.withParameters("long " + baseMethods.camelize(formName) + "Id");
			methodblock.addReturnStatement(
					CCodeMethodCall.callMethodUsingObject("this." + baseMethods.camelize(formName) + "DAO",
							"findBy" + baseMethods.allLetterCaps(formName) + "Id",
							"long " + baseMethods.camelize(formName) + "Id"));
			cmethod.closeMethod();

			classS.closeClass();
			return mvc.build();

		} catch (Exception ex) {
			ex.printStackTrace();

			return "";
		}
	}
}
