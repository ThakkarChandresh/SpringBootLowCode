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

	private static final Logger LOGGER = LogManager.getLogger(ServiceImplUtils.class);
	
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
			CCodeClass classS = mvc.withPackageStatement("com.project.service").withRequiredImports(importList);

			classS.addAnnotation("Service");
			classS.addAnnotation("Transactional");
			classS.create("class", baseMethods.allLetterCaps(formName) + "ServiceImpl")
					.withAM(CCodeEnum.PUBLIC.getValue());
			classS.implementS(baseMethods.allLetterCaps(formName) + "Service");

			CClassVar classVar = classS.classVar();
			classVar.addAnnotation("Autowired");
			classVar.createVariable(baseMethods.camelize(formName + "DAO")).withAM("private")
					.type(baseMethods.allLetterCaps(formName) + "DAO");

			CCodeMethod cmethod = classS.method();
			cmethod.addAnnotation(CCodeEnum.OVERRIDE.getValue());
			CCodeMethodBlock methodblock = cmethod.createMethod("insert").withAM(CCodeEnum.PUBLIC.getValue())
					.withReturnType("void").withParameters(
							baseMethods.allLetterCaps(formName) + "VO" + " " + baseMethods.camelize(formName) + "VO");
			methodblock.addStatement(CCodeMethodCall.callMethodUsingObject(
					CCodeEnum.THIS.getValue() + baseMethods.camelize(formName) + "DAO", "save",
					baseMethods.camelize(formName) + "VO"));
			cmethod.closeMethod();

			cmethod.addAnnotation(CCodeEnum.OVERRIDE.getValue());
			cmethod.createMethod("search").withAM(CCodeEnum.PUBLIC.getValue())
					.withReturnType("List<" + baseMethods.allLetterCaps(formName) + "VO>").withParameters("");

			methodblock.addReturnStatement(CCodeMethodCall.callMethodUsingObject(
					CCodeEnum.THIS.getValue() + baseMethods.camelize(formName) + "DAO", "findAll"));
			cmethod.closeMethod();

			cmethod.addAnnotation(CCodeEnum.OVERRIDE.getValue());
			cmethod.createMethod("delete").withAM(CCodeEnum.PUBLIC.getValue()).withReturnType("void").withParameters(
					baseMethods.allLetterCaps(formName) + "VO" + " " + baseMethods.camelize(formName) + "VO");
			methodblock.addStatement(CCodeMethodCall.callMethodUsingObject(
					CCodeEnum.THIS.getValue() + baseMethods.camelize(formName) + "DAO", "delete",
					baseMethods.camelize(formName) + "VO"));
			cmethod.closeMethod();

			cmethod.addAnnotation(CCodeEnum.OVERRIDE.getValue());
			cmethod.createMethod("edit").withAM(CCodeEnum.PUBLIC.getValue())
					.withReturnType("List<" + baseMethods.allLetterCaps(formName) + "VO>")
					.withParameters("Long " + baseMethods.camelize(formName) + "Id");
			methodblock.addReturnStatement(CCodeMethodCall.callMethodUsingObject(
					CCodeEnum.THIS.getValue() + baseMethods.camelize(formName) + "DAO",
					"findBy" + baseMethods.allLetterCaps(formName) + "Id", baseMethods.camelize(formName) + "Id"));
			cmethod.closeMethod();

			classS.closeClass();
			return mvc.build();

		} catch (Exception e) {
			LOGGER.error("Exception in ServiceImplUtil", e);
			return "";
		}
	}
}
