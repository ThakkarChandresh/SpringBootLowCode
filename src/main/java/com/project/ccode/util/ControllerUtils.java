package com.project.ccode.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
			classS.create("class", baseMethods.allLetterCaps(formName) + "Controller").withAM("public");

			CClassVar classVar = classS.classVar();
			classVar.addAnnotation("Autowired");
			classVar.createVariable(baseMethods.camelize(formName) + "Service").withAM("private")
					.type(baseMethods.allLetterCaps(formName) + "Service");

			CCodeMethod cmethod = classS.method();

			cmethod.addAnnotation("GetMapping(value=\""
					+ baseMethods.allLetterCaps(formsVO.getModuleVO().getModuleName()).toLowerCase() + "/add"
					+ baseMethods.allLetterCaps(formName) + "\")");
			CCodeMethodBlock methodblock = cmethod.createMethod("add" + baseMethods.allLetterCaps(formName))
					.withAM("public").withReturnType("ModelAndView").withParameters("");
			methodblock.addReturnStatement(CCodeCreateObject.createObject("ModelAndView",
					"\""+formsVO.getModuleVO().getModuleName().toLowerCase()+"/add" + baseMethods.camelize(formName).toLowerCase() + "\"",
					"\"" + baseMethods.camelize(formName) + "VO" + "\"",
					CCodeCreateObject.createObject(baseMethods.allLetterCaps(formName) + "VO")));
			cmethod.closeMethod();

			cmethod.addAnnotation("PostMapping(value=\""
					+ baseMethods.allLetterCaps(formsVO.getModuleVO().getModuleName()).toLowerCase() + "/insert"
					+ baseMethods.allLetterCaps(formName) + "\")");
			cmethod.createMethod("insert" + baseMethods.allLetterCaps(formName)).withAM("public")
					.withReturnType("ModelAndView").withParameters("@ModelAttribute "
							+ baseMethods.allLetterCaps(formName) + "VO" + " " + baseMethods.camelize(formName) + "VO");
			methodblock.addStatement(
					CCodeMethodCall.callMethodUsingObject("this." + baseMethods.camelize(formName) + "Service",
							"insert", baseMethods.camelize(formName) + "VO"));
			methodblock.addReturnStatement(CCodeCreateObject.createObject("ModelAndView",
					"\"redirect:/"+formsVO.getModuleVO().getModuleName().toLowerCase()+"/view" + baseMethods.allLetterCaps(formName) + "\""));
			cmethod.closeMethod();

			cmethod.addAnnotation("GetMapping(value=\""
					+ baseMethods.allLetterCaps(formsVO.getModuleVO().getModuleName()).toLowerCase() + "/delete"
					+ baseMethods.allLetterCaps(formName) + "\")");
			cmethod.createMethod("delete" + baseMethods.allLetterCaps(formName)).withAM("public")
					.withReturnType("ModelAndView")
					.withParameters("@ModelAttribute " + baseMethods.allLetterCaps(formName) + "VO" + " "
							+ baseMethods.camelize(formName) + "VO" + "," + "@RequestParam " + "Long "
							+ baseMethods.camelize(formName) + "Id");
			methodblock.addStatement(CCodeMethodCall.callMethodUsingObject(baseMethods.camelize(formName) + "VO",
					"set" + baseMethods.allLetterCaps(formName) + "Id", baseMethods.camelize(formName) + "Id"));
			methodblock.addStatement(
					CCodeMethodCall.callMethodUsingObject("this." + baseMethods.camelize(formName) + "Service",
							"delete", baseMethods.camelize(formName) + "VO"));
			methodblock.addReturnStatement(CCodeCreateObject.createObject("ModelAndView",
					"\"redirect:/"+formsVO.getModuleVO().getModuleName().toLowerCase()+"/view" + baseMethods.allLetterCaps(formName) + "\""));
			cmethod.closeMethod();

			cmethod.addAnnotation("GetMapping(value=\""
					+ baseMethods.allLetterCaps(formsVO.getModuleVO().getModuleName()).toLowerCase() + "/view"
					+ baseMethods.allLetterCaps(formName) + "\")");
			cmethod.createMethod("view" + baseMethods.allLetterCaps(formName)).withAM("public")
					.withReturnType("ModelAndView").withParameters("");
			methodblock.createVariable(baseMethods.camelize(formName) + "List").withAM("")
					.type("List<" + baseMethods.allLetterCaps(formName) + "VO>");
			methodblock.assign(CCodeMethodCall
					.callMethodUsingObject("this." + baseMethods.camelize(formName) + "Service", "search"));
			methodblock.addReturnStatement(CCodeCreateObject.createObject("ModelAndView",
					"\""+formsVO.getModuleVO().getModuleName().toLowerCase()+"/view" + baseMethods.camelize(formName).toLowerCase() + "\"",
					"\"" + baseMethods.camelize(formName) + "List" + "\"", baseMethods.camelize(formName) + "List"));
			cmethod.closeMethod();

			cmethod.addAnnotation("GetMapping(value=\""
					+ baseMethods.allLetterCaps(formsVO.getModuleVO().getModuleName()).toLowerCase() + "/edit"
					+ baseMethods.allLetterCaps(formName) + "\")");
			cmethod.createMethod("edit" + baseMethods.allLetterCaps(formName)).withAM("public")
					.withReturnType("ModelAndView")
					.withParameters("@RequestParam " + "Long " + baseMethods.camelize(formName) + "Id");
			methodblock.createVariable(baseMethods.camelize(formName) + "List").withAM("")
					.type("List<" + baseMethods.allLetterCaps(formName) + "VO>");
			methodblock
					.assign(CCodeMethodCall.callMethodUsingObject("this." + baseMethods.camelize(formName) + "Service",
							"edit", baseMethods.camelize(formName) + "Id"));
			methodblock.addReturnStatement(CCodeCreateObject.createObject("ModelAndView",
					"\""+formsVO.getModuleVO().getModuleName().toLowerCase()+"/add" + baseMethods.camelize(formName).toLowerCase() + "\"",
					"\"" + baseMethods.camelize(formName) + "VO" + "\"",
					CCodeMethodCall.callMethodUsingObject(baseMethods.camelize(formName) + "List", "get", "0")));
			cmethod.closeMethod();

			classS.closeClass();
			return mvc.build();

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
