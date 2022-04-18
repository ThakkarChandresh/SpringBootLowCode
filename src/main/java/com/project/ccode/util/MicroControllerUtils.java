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
public class MicroControllerUtils {

	@Autowired
	private BaseMethods baseMethods;

	private static final Logger LOGGER = LogManager.getLogger(MicroControllerUtils.class);

	public String getMicroControllerContent(FormsVO formsVO) {
		String formName = formsVO.getFormName();

		try {
			List<String> importList = new ArrayList<String>();
			importList.add("java.util.List");
			importList.add("org.springframework.web.bind.annotation.RestController");
			importList.add("org.springframework.beans.factory.annotation.Autowired");
			importList.add("org.springframework.web.bind.annotation.RequestMapping");
			importList.add("org.springframework.web.bind.annotation.GetMapping");
			importList.add("org.springframework.web.bind.annotation.PostMapping");
			importList.add("org.springframework.web.bind.annotation.PutMapping");
			importList.add("org.springframework.web.bind.annotation.PathVariable");
			importList.add("org.springframework.web.bind.annotation.DeleteMapping");
			importList.add("org.springframework.web.bind.annotation.CrossOrigin");
			importList.add("org.springframework.web.bind.annotation.ModelAttribute");
			importList.add("org.springframework.http.HttpStatus");
			importList.add("org.springframework.http.ResponseEntity");
			importList.add("org.springframework.web.bind.annotation.RequestBody");

			importList.add("com.project.model.*");
			importList.add("com.project.service.*");

			CCodeMVC mvc = new CCodeMVC();

			CCodeClass classS = mvc.withPackageStatement("com.project.controller").withRequiredImports(importList);
			classS.addAnnotation("CrossOrigin");
			classS.addAnnotation("RestController");
			classS.addAnnotation("RequestMapping" + CCodeEnum.VALUE.getValue()
					+ baseMethods.camelize(formsVO.getModuleVO().getModuleName()) + "/" + baseMethods.camelize(formName)
					+ "\")");

			classS.create("class", baseMethods.allLetterCaps(formName) + "Controller")
					.withAM(CCodeEnum.PUBLIC.getValue());

			CClassVar classVar = classS.classVar();
			classVar.addAnnotation("Autowired");
			classVar.createVariable(baseMethods.camelize(formName) + CCodeEnum.SERVICE.getValue()).withAM("private")
					.type(baseMethods.allLetterCaps(formName) + CCodeEnum.SERVICE.getValue());

			CCodeMethod cmethod = classS.method();

			cmethod.addAnnotation(CCodeEnum.GET_MAPPING.getValue());
			CCodeMethodBlock methodblock = cmethod.createMethod("view" + baseMethods.allLetterCaps(formName))
					.withAM(CCodeEnum.PUBLIC.getValue()).withReturnType(CCodeEnum.RESPONSEENTITY.getValue()
							+ CCodeEnum.LIST.getValue() + baseMethods.allLetterCaps(formName) + "VO>>")
					.withParameters("");
			methodblock.createVariable(baseMethods.camelize(formName) + "List").withAM("")
					.type(CCodeEnum.LIST.getValue() + baseMethods.allLetterCaps(formName) + "VO>");
			methodblock.assign(CCodeMethodCall.callMethodUsingObject(
					CCodeEnum.THIS.getValue() + baseMethods.camelize(formName) + CCodeEnum.SERVICE.getValue(),
					"search"));
			methodblock.addReturnStatement(CCodeCreateObject.createObject(
					CCodeEnum.RESPONSEENTITY.getValue() + CCodeEnum.LIST.getValue()
							+ baseMethods.allLetterCaps(formName) + "VO>>",
					baseMethods.camelize(formName) + "List", CCodeEnum.HTTPSTATUS.getValue()));
			cmethod.closeMethod();

			cmethod.addAnnotation(CCodeEnum.GET_MAPPING.getValue() + CCodeEnum.VALUE.getValue() + "/" + "{"
					+ baseMethods.camelize(formName) + "Id" + "}" + "\")");
			cmethod.createMethod("edit" + baseMethods.allLetterCaps(formName)).withAM(CCodeEnum.PUBLIC.getValue())
					.withReturnType(CCodeEnum.RESPONSEENTITY.getValue() + baseMethods.allLetterCaps(formName) + "VO>")
					.withParameters("@PathVariable " + "Long " + baseMethods.camelize(formName) + "Id");
			methodblock.createVariable(baseMethods.camelize(formName) + "List").withAM("")
					.type(CCodeEnum.LIST.getValue() + baseMethods.allLetterCaps(formName) + "VO>");
			methodblock.assign(CCodeMethodCall.callMethodUsingObject(
					CCodeEnum.THIS.getValue() + baseMethods.camelize(formName) + CCodeEnum.SERVICE.getValue(), "edit",
					baseMethods.camelize(formName) + "Id"));
			methodblock.addReturnStatement(CCodeCreateObject.createObject(
					CCodeEnum.RESPONSEENTITY.getValue() + baseMethods.allLetterCaps(formName) + "VO>",
					CCodeMethodCall.callMethodUsingObject(baseMethods.camelize(formName) + "List", "get", "0"),
					CCodeEnum.HTTPSTATUS.getValue()));
			cmethod.closeMethod();

			cmethod.addAnnotation("PutMapping");
			cmethod.createMethod("update" + baseMethods.allLetterCaps(formName)).withAM(CCodeEnum.PUBLIC.getValue())
					.withReturnType(CCodeEnum.RESPONSEENTITY.getValue() + CCodeEnum.OBJECT.getValue())
					.withParameters("@RequestBody " + baseMethods.allLetterCaps(formName) + "VO "
							+ baseMethods.camelize(formName) + "VO");
			methodblock.addStatement(CCodeMethodCall.callMethodUsingObject(
					CCodeEnum.THIS.getValue() + baseMethods.camelize(formName) + CCodeEnum.SERVICE.getValue(),
					CCodeEnum.INSERT.getValue(), baseMethods.camelize(formName) + "VO"));
			methodblock.addReturnStatement(
					CCodeCreateObject.createObject(CCodeEnum.RESPONSEENTITY.getValue() + CCodeEnum.OBJECT.getValue(),
							CCodeEnum.HTTPSTATUS.getValue()));
			cmethod.closeMethod();

			cmethod.addAnnotation("DeleteMapping" + CCodeEnum.VALUE.getValue() + "/" + "{"
					+ baseMethods.camelize(formName) + "Id" + "}" + "\")");
			cmethod.createMethod("delete" + baseMethods.allLetterCaps(formName)).withAM(CCodeEnum.PUBLIC.getValue())
					.withReturnType(CCodeEnum.RESPONSEENTITY.getValue() + CCodeEnum.OBJECT.getValue()).withParameters(
							"@ModelAttribute " + baseMethods.allLetterCaps(formName) + "VO" + " "
									+ baseMethods.camelize(formName) + "VO",
							"@PathVariable " + "Long " + baseMethods.camelize(formName) + "Id");
			methodblock.addStatement(CCodeMethodCall.callMethodUsingObject(baseMethods.camelize(formName) + "VO",
					"set" + baseMethods.allLetterCaps(formName) + "Id", baseMethods.camelize(formName) + "Id"));
			methodblock.addStatement(CCodeMethodCall.callMethodUsingObject(
					CCodeEnum.THIS.getValue() + baseMethods.camelize(formName) + CCodeEnum.SERVICE.getValue(), "delete",
					baseMethods.camelize(formName) + "VO"));
			methodblock.addReturnStatement(
					CCodeCreateObject.createObject(CCodeEnum.RESPONSEENTITY.getValue() + CCodeEnum.OBJECT.getValue(),
							CCodeEnum.HTTPSTATUS.getValue()));
			cmethod.closeMethod();

			cmethod.addAnnotation("PostMapping");
			cmethod.createMethod(CCodeEnum.INSERT.getValue() + baseMethods.allLetterCaps(formName))
					.withAM(CCodeEnum.PUBLIC.getValue())
					.withReturnType(CCodeEnum.RESPONSEENTITY.getValue() + CCodeEnum.OBJECT.getValue())
					.withParameters("@RequestBody " + baseMethods.allLetterCaps(formName) + "VO "
							+ baseMethods.camelize(formName) + "VO");
			methodblock.addStatement(CCodeMethodCall.callMethodUsingObject(
					CCodeEnum.THIS.getValue() + baseMethods.camelize(formName) + CCodeEnum.SERVICE.getValue(),
					CCodeEnum.INSERT.getValue(), baseMethods.camelize(formName) + "VO"));
			methodblock.addReturnStatement(
					CCodeCreateObject.createObject(CCodeEnum.RESPONSEENTITY.getValue() + CCodeEnum.OBJECT.getValue(),
							CCodeEnum.HTTPSTATUS.getValue()));
			cmethod.closeMethod();

			classS.closeClass();
			return mvc.build();

		} catch (Exception e) {
			LOGGER.error("Exception in RestControllerUtil", e);
			return "";
		}
	}

}
