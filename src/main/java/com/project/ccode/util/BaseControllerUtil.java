package com.project.ccode.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import io.github.ccodemvc.CCodeClass;
import io.github.ccodemvc.CCodeCreateObject;
import io.github.ccodemvc.CCodeMVC;
import io.github.ccodemvc.CCodeMethod;
import io.github.ccodemvc.CCodeMethodBlock;

@Component
public class BaseControllerUtil {
	private static final Logger LOGGER = LogManager.getLogger(BaseControllerUtil.class);

	public String getBaseControllerContent() {

		try {
			List<String> importList = new ArrayList<String>();
			importList.add("org.springframework.stereotype.Controller");
			importList.add("org.springframework.web.bind.annotation.GetMapping");
			importList.add("org.springframework.web.servlet.ModelAndView");

			CCodeMVC mvc = new CCodeMVC();

			CCodeClass classS = mvc.withPackageStatement("com.project.controller").withRequiredImports(importList);
			classS.addAnnotation("Controller");
			classS.create("class", "BaseController").withAM("public");

			CCodeMethod cmethod = classS.method();

			cmethod.addAnnotation("GetMapping(value=\"" + "/" + "\")");
			CCodeMethodBlock methodblock = cmethod.createMethod("load").withAM("public").withReturnType("ModelAndView")
					.withParameters("");
			methodblock.addReturnStatement(CCodeCreateObject.createObject("ModelAndView", "\"index\" "));
			cmethod.closeMethod();

			classS.closeClass();
			return mvc.build();

		} catch (Exception e) {
			LOGGER.error("Exception in BaseControllerUtil", e);
			return "";
		}
	}
}
