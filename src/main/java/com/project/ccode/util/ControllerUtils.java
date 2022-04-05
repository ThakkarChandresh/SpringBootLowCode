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
import io.github.ccodemvc.CCodeCreateObject;
import io.github.ccodemvc.CCodeMVC;
import io.github.ccodemvc.CCodeMethod;
import io.github.ccodemvc.CCodeMethodBlock;
import io.github.ccodemvc.CCodeMethodCall;

@Component
public class ControllerUtils {

	@Autowired
	private BaseMethods baseMethods;

	private static final Logger LOGGER = LogManager.getLogger(ControllerUtils.class);
	
	public String getControllerContent(FormsVO formsVO) {
		String formName = formsVO.getFormName();

		try {
			List<String> importList = new ArrayList<String>();
			importList.add("java.util.List");
			importList.add("org.springframework.stereotype.Controller");
			importList.add("org.springframework.beans.factory.annotation.Autowired");
			importList.add("org.springframework.web.bind.annotation.GetMapping");
			importList.add("org.springframework.web.bind.annotation.PostMapping");
			importList.add("org.springframework.web.servlet.ModelAndView");
			importList.add("org.springframework.web.bind.annotation.ModelAttribute");
			importList.add("org.springframework.web.bind.annotation.RequestParam");

			importList.add("com.project.model.*");
			importList.add("com.project.service.*");

			CCodeMVC mvc = new CCodeMVC();

			CCodeClass classS = mvc.withPackageStatement("com.project.controller").withRequiredImports(importList);
			classS.addAnnotation("Controller");
			classS.create("class", baseMethods.allLetterCaps(formName) + "Controller")
					.withAM(CCodeEnum.PUBLIC.getValue());

			CClassVar classVar = classS.classVar();
			classVar.addAnnotation("Autowired");
			classVar.createVariable(baseMethods.camelize(formName) + CCodeEnum.SERVICE.getValue()).withAM("private")
					.type(baseMethods.allLetterCaps(formName) + CCodeEnum.SERVICE.getValue());

			CCodeMethod cmethod = classS.method();

			cmethod.addAnnotation(CCodeEnum.GET_MAPPING.getValue()
					+ baseMethods.allLetterCaps(formsVO.getModuleVO().getModuleName()).toLowerCase() + "/add"
					+ baseMethods.allLetterCaps(formName) + "\")");
			CCodeMethodBlock methodblock = cmethod.createMethod("add" + baseMethods.allLetterCaps(formName))
					.withAM("public").withReturnType(CCodeEnum.MODELANDVIEW.getValue()).withParameters("");
			methodblock.addReturnStatement(CCodeCreateObject.createObject(CCodeEnum.MODELANDVIEW.getValue(),
					"\"" + formsVO.getModuleVO().getModuleName().toLowerCase() + "/add"
							+ baseMethods.camelize(formName).toLowerCase() + "\"",
					"\"" + baseMethods.camelize(formName) + "VO" + "\"",
					CCodeCreateObject.createObject(baseMethods.allLetterCaps(formName) + "VO")));
			cmethod.closeMethod();

			cmethod.addAnnotation("PostMapping(value=\""
					+ baseMethods.allLetterCaps(formsVO.getModuleVO().getModuleName()).toLowerCase() + "/insert"
					+ baseMethods.allLetterCaps(formName) + "\")");
			cmethod.createMethod("insert" + baseMethods.allLetterCaps(formName)).withAM(CCodeEnum.PUBLIC.getValue())
					.withReturnType(CCodeEnum.MODELANDVIEW.getValue()).withParameters("@ModelAttribute "
							+ baseMethods.allLetterCaps(formName) + "VO" + " " + baseMethods.camelize(formName) + "VO");
			methodblock.addStatement(CCodeMethodCall.callMethodUsingObject(
					CCodeEnum.THIS.getValue() + baseMethods.camelize(formName) + CCodeEnum.SERVICE.getValue(), "insert",
					baseMethods.camelize(formName) + "VO"));
			methodblock.addReturnStatement(CCodeCreateObject.createObject(CCodeEnum.MODELANDVIEW.getValue(),
					"\"redirect:/" + formsVO.getModuleVO().getModuleName().toLowerCase() + CCodeEnum.VIEW.getValue()
							+ baseMethods.allLetterCaps(formName) + "\""));
			cmethod.closeMethod();

			cmethod.addAnnotation(CCodeEnum.GET_MAPPING.getValue()
					+ baseMethods.allLetterCaps(formsVO.getModuleVO().getModuleName()).toLowerCase() + "/delete"
					+ baseMethods.allLetterCaps(formName) + "\")");
			cmethod.createMethod("delete" + baseMethods.allLetterCaps(formName)).withAM(CCodeEnum.PUBLIC.getValue())
					.withReturnType(CCodeEnum.MODELANDVIEW.getValue())
					.withParameters("@ModelAttribute " + baseMethods.allLetterCaps(formName) + "VO" + " "
							+ baseMethods.camelize(formName) + "VO" + "," + "@RequestParam " + "Long "
							+ baseMethods.camelize(formName) + "Id");
			methodblock.addStatement(CCodeMethodCall.callMethodUsingObject(baseMethods.camelize(formName) + "VO",
					"set" + baseMethods.allLetterCaps(formName) + "Id", baseMethods.camelize(formName) + "Id"));
			methodblock.addStatement(CCodeMethodCall.callMethodUsingObject(
					CCodeEnum.THIS.getValue() + baseMethods.camelize(formName) + CCodeEnum.SERVICE.getValue(), "delete",
					baseMethods.camelize(formName) + "VO"));
			methodblock.addReturnStatement(CCodeCreateObject.createObject(CCodeEnum.MODELANDVIEW.getValue(),
					"\"redirect:/" + formsVO.getModuleVO().getModuleName().toLowerCase() + CCodeEnum.VIEW.getValue()
							+ baseMethods.allLetterCaps(formName) + "\""));
			cmethod.closeMethod();

			cmethod.addAnnotation(CCodeEnum.GET_MAPPING.getValue()
					+ baseMethods.allLetterCaps(formsVO.getModuleVO().getModuleName()).toLowerCase()
					+ CCodeEnum.VIEW.getValue() + baseMethods.allLetterCaps(formName) + "\")");
			cmethod.createMethod("view" + baseMethods.allLetterCaps(formName)).withAM(CCodeEnum.PUBLIC.getValue())
					.withReturnType(CCodeEnum.MODELANDVIEW.getValue()).withParameters("");
			methodblock.createVariable(baseMethods.camelize(formName) + "List").withAM("")
					.type("List<" + baseMethods.allLetterCaps(formName) + "VO>");
			methodblock.assign(CCodeMethodCall.callMethodUsingObject(
					CCodeEnum.THIS.getValue() + baseMethods.camelize(formName) + CCodeEnum.SERVICE.getValue(),
					"search"));
			methodblock.addReturnStatement(CCodeCreateObject.createObject(CCodeEnum.MODELANDVIEW.getValue(),
					"\"" + formsVO.getModuleVO().getModuleName().toLowerCase() + CCodeEnum.VIEW.getValue()
							+ baseMethods.camelize(formName).toLowerCase() + "\"",
					"\"" + baseMethods.camelize(formName) + "List" + "\"", baseMethods.camelize(formName) + "List"));
			cmethod.closeMethod();

			cmethod.addAnnotation(CCodeEnum.GET_MAPPING.getValue()
					+ baseMethods.allLetterCaps(formsVO.getModuleVO().getModuleName()).toLowerCase() + "/edit"
					+ baseMethods.allLetterCaps(formName) + "\")");
			cmethod.createMethod("edit" + baseMethods.allLetterCaps(formName)).withAM(CCodeEnum.PUBLIC.getValue())
					.withReturnType(CCodeEnum.MODELANDVIEW.getValue())
					.withParameters("@RequestParam " + "Long " + baseMethods.camelize(formName) + "Id");
			methodblock.createVariable(baseMethods.camelize(formName) + "List").withAM("")
					.type("List<" + baseMethods.allLetterCaps(formName) + "VO>");
			methodblock.assign(CCodeMethodCall.callMethodUsingObject(
					CCodeEnum.THIS.getValue() + baseMethods.camelize(formName) + CCodeEnum.SERVICE.getValue(), "edit",
					baseMethods.camelize(formName) + "Id"));
			methodblock.addReturnStatement(CCodeCreateObject.createObject(CCodeEnum.MODELANDVIEW.getValue(),
					"\"" + formsVO.getModuleVO().getModuleName().toLowerCase() + "/add"
							+ baseMethods.camelize(formName).toLowerCase() + "\"",
					"\"" + baseMethods.camelize(formName) + "VO" + "\"",
					CCodeMethodCall.callMethodUsingObject(baseMethods.camelize(formName) + "List", "get", "0")));
			cmethod.closeMethod();

			classS.closeClass();
			return mvc.build();

		} catch (Exception e) {
			LOGGER.error("Exception in ControllerUtil", e);
			return "";
		}
	}
}
