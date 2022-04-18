package com.project.ccode.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.project.enums.TypeEnum;

import io.github.ccodemvc.CCodeClass;
import io.github.ccodemvc.CCodeMVC;
import io.github.ccodemvc.CCodeMethod;
import io.github.ccodemvc.CCodeMethodBlock;
import io.github.ccodemvc.CCodeMethodCall;

@Component
public class ApplicationUtils {
	private static final Logger LOGGER = LogManager.getLogger(ApplicationUtils.class);

	public String getApplicationContent(String type) {
		try {
			List<String> importList = new ArrayList<String>();

			importList.add("org.springframework.boot.SpringApplication");
			importList.add("org.springframework.boot.autoconfigure.SpringBootApplication");

			if (type.equals(TypeEnum.MONOLITHIC.getValue())) {
				importList.add("org.springframework.boot.builder.SpringApplicationBuilder");
				importList.add("org.springframework.boot.web.support.SpringBootServletInitializer");
			}

			CCodeMVC mvc = new CCodeMVC();

			CCodeClass classS = mvc.withPackageStatement("com.project").withRequiredImports(importList);
			classS.addAnnotation("SpringBootApplication");
			classS.create("class", "Application").withAM("public");

			if (type.equals(TypeEnum.MONOLITHIC.getValue()))
				classS.extendS("SpringBootServletInitializer");

			CCodeMethod cmethod = classS.method();
			CCodeMethodBlock methodblock;

			if (type.equals(TypeEnum.MONOLITHIC.getValue())) {
				cmethod.addAnnotation("Override");
				methodblock = cmethod.createMethod("configure").withAM("protected")
						.withReturnType("SpringApplicationBuilder")
						.withParameters("SpringApplicationBuilder application");
				methodblock.addReturnStatement(
						CCodeMethodCall.callMethodUsingObject("application", "sources", "Application.class"));
				cmethod.closeMethod();
			}

			methodblock = cmethod.createMethod("main").withAM("public", "static").withReturnType("void")
					.withParameters("String [] args");
			methodblock.addStatement(
					CCodeMethodCall.callMethodUsingObject("SpringApplication", "run", "Application.class", "args"));
			cmethod.closeMethod();

			classS.closeClass();

			return mvc.build();
		} catch (Exception e) {
			LOGGER.error("Exception in ApplicationUtil", e);
			return "";
		}
	}
}
