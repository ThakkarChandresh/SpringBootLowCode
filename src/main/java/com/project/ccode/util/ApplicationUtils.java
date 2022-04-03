package com.project.ccode.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import io.github.ccodemvc.CCodeClass;
import io.github.ccodemvc.CCodeMVC;
import io.github.ccodemvc.CCodeMethod;
import io.github.ccodemvc.CCodeMethodBlock;
import io.github.ccodemvc.CCodeMethodCall;

@Component
public class ApplicationUtils {
	public String getApplicationContent() {
		try {
			List<String> importList = new ArrayList<String>();

			importList.add("org.springframework.boot.SpringApplication");
			importList.add("org.springframework.boot.autoconfigure.SpringBootApplication");
			importList.add("org.springframework.boot.builder.SpringApplicationBuilder");
			importList.add("org.springframework.boot.web.support.SpringBootServletInitializer");

			CCodeMVC mvc = new CCodeMVC();

			CCodeClass classS = mvc.withPackageStatement("com.project").withRequiredImports(importList);
			classS.addAnnotation("SpringBootApplication");
			classS.create("class", "Application").withAM("public");
			classS.extendS("SpringBootServletInitializer");

			CCodeMethod cmethod = classS.method();
			cmethod.addAnnotation("Override");
			CCodeMethodBlock methodblock = cmethod.createMethod("configure").withAM("protected")
					.withReturnType("SpringApplicationBuilder").withParameters("SpringApplicationBuilder application");
			methodblock.addReturnStatement(
					CCodeMethodCall.callMethodUsingObject("application", "sources", "Application.class"));
			cmethod.closeMethod();

			cmethod.createMethod("main").withAM("public", "static").withReturnType("void")
					.withParameters("String [] args");
			methodblock.addStatement(
					CCodeMethodCall.callMethodUsingObject("SpringApplication", "run", "Application.class", "args"));
			cmethod.closeMethod();

			classS.closeClass();

			return mvc.build();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}