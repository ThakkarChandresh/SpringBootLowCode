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
			importList.add("org.springframework.http.HttpStatus");
			importList.add("org.springframework.http.ResponseEntity");
			importList.add("org.springframework.web.bind.annotation.RequestMapping");
			importList.add("org.springframework.web.bind.annotation.GetMapping");
			importList.add("org.springframework.web.bind.annotation.PostMapping");
			importList.add("org.springframework.web.servlet.ModelAndView");
			importList.add("org.springframework.web.bind.annotation.ModelAttribute");
			importList.add("org.springframework.web.bind.annotation.RequestParam");
			importList.add("org.springframework.web.bind.annotation.PathVariable");

			importList.add("com.project.model.*");
			importList.add("com.project.service.*");

			CCodeMVC mvc = new CCodeMVC();

			CCodeClass classS = mvc.withPackageStatement("com.project.controller").withRequiredImports(importList);
			classS.addAnnotation("Controller");
			classS.addAnnotation("RequestMapping(\""
					+ baseMethods.allLetterCaps(formsVO.getModuleVO().getModuleName()).toLowerCase() + "\")");
			classS.create("class", baseMethods.allLetterCaps(formName) + "Controller")
					.withAM(CCodeEnum.PUBLIC.getValue());

			CClassVar classVar = classS.classVar();
			classVar.addAnnotation("Autowired");
			classVar.createVariable(baseMethods.camelize(formName) + CCodeEnum.SERVICE.getValue()).withAM("private")
					.type(baseMethods.allLetterCaps(formName) + CCodeEnum.SERVICE.getValue());

			CCodeMethod cmethod = classS.method();

			cmethod.addAnnotation("PostMapping(value=\"" + "/insert" + baseMethods.allLetterCaps(formName) + "\")");
			CCodeMethodBlock methodblock = cmethod.createMethod("insert" + baseMethods.allLetterCaps(formName))
					.withAM(CCodeEnum.PUBLIC.getValue()).withReturnType(CCodeEnum.MODELANDVIEW.getValue())
					.withParameters("@ModelAttribute " + baseMethods.allLetterCaps(formName) + "VO" + " "
							+ baseMethods.camelize(formName) + "VO");
			methodblock.addStatement(CCodeMethodCall.callMethodUsingObject(
					CCodeEnum.THIS.getValue() + baseMethods.camelize(formName) + CCodeEnum.SERVICE.getValue(), "insert",
					baseMethods.camelize(formName) + "VO"));
			methodblock.addReturnStatement(CCodeCreateObject.createObject(CCodeEnum.MODELANDVIEW.getValue(),
					"\"redirect:/" + baseMethods.allLetterCaps(formsVO.getModuleVO().getModuleName()).toLowerCase()
							+ CCodeEnum.SLASH.getValue() + baseMethods.camelize(formName) + "\""));
			cmethod.closeMethod();

			cmethod.addAnnotation(CCodeEnum.GET_MAPPING.getValue() + CCodeEnum.VALUE.getValue() + "/delete"
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
					"\"redirect:/" + baseMethods.allLetterCaps(formsVO.getModuleVO().getModuleName()).toLowerCase()
							+ CCodeEnum.SLASH.getValue() + baseMethods.camelize(formName) + "\""));
			cmethod.closeMethod();

			cmethod.addAnnotation(CCodeEnum.GET_MAPPING.getValue() + CCodeEnum.VALUE.getValue()
					+ CCodeEnum.SLASH.getValue() + baseMethods.camelize(formName) + "\")");
			cmethod.createMethod("view" + baseMethods.allLetterCaps(formName)).withAM(CCodeEnum.PUBLIC.getValue())
					.withReturnType(CCodeEnum.MODELANDVIEW.getValue()).withParameters("");
			methodblock.createVariable(baseMethods.camelize(formName) + "List").withAM("")
					.type("List<" + baseMethods.allLetterCaps(formName) + "VO>");
			methodblock.assign(CCodeMethodCall.callMethodUsingObject(
					CCodeEnum.THIS.getValue() + baseMethods.camelize(formName) + CCodeEnum.SERVICE.getValue(),
					"search"));
			methodblock
					.addReturnStatement(
							CCodeMethodCall
									.callMethodUsingObject(
											CCodeCreateObject
													.createObject(
															CCodeEnum.MODELANDVIEW
																	.getValue(),
															"\"" + baseMethods
																	.allLetterCaps(
																			formsVO.getModuleVO().getModuleName())
																	.toLowerCase() + CCodeEnum.SLASH.getValue()
																	+ baseMethods.camelize(formName).toLowerCase()
																	+ "\"",
															"\"" + baseMethods.camelize(formName) + "List" + "\"",
															baseMethods.camelize(formName) + "List"),
											"addObject", "\"" + baseMethods.camelize(formName) + "VO" + "\"",
											CCodeCreateObject
													.createObject(baseMethods.allLetterCaps(formName) + "VO")));
			cmethod.closeMethod();

			cmethod.addAnnotation(CCodeEnum.GET_MAPPING.getValue() + CCodeEnum.VALUE.getValue() + "/edit"
					+ baseMethods.allLetterCaps(formName) + "/" + "{" + baseMethods.camelize(formName) + "Id" + "}"
					+ "\")");
			cmethod.createMethod("edit" + baseMethods.allLetterCaps(formName)).withAM(CCodeEnum.PUBLIC.getValue())
					.withReturnType(CCodeEnum.RESPONSEENTITY.getValue() + baseMethods.allLetterCaps(formName) + "VO>")
					.withParameters("@PathVariable " + "Long " + baseMethods.camelize(formName) + "Id");
			methodblock.createVariable(baseMethods.camelize(formName) + "List").withAM("")
					.type("List<" + baseMethods.allLetterCaps(formName) + "VO>");
			methodblock.assign(CCodeMethodCall.callMethodUsingObject(
					CCodeEnum.THIS.getValue() + baseMethods.camelize(formName) + CCodeEnum.SERVICE.getValue(), "edit",
					baseMethods.camelize(formName) + "Id"));
			methodblock.addReturnStatement(CCodeCreateObject.createObject(
					CCodeEnum.RESPONSEENTITY.getValue() + baseMethods.allLetterCaps(formName) + "VO>",
					CCodeMethodCall.callMethodUsingObject(baseMethods.camelize(formName) + "List", "get", "0"),
					CCodeEnum.HTTPSTATUS.getValue()));
			cmethod.closeMethod();

			classS.closeClass();
			return mvc.build();

		} catch (Exception e) {
			LOGGER.error("Exception in ControllerUtil", e);
			return "";
		}
	}
}
