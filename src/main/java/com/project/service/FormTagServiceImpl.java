package com.project.service;

public class FormTagServiceImpl implements FormTagService {
	private String divTag = "<div class=\"form-group\">";
	private String endDiv = "</div>";
	@Override
	public String getFormTag() {
		StringBuilder form = new StringBuilder();
		form.append("<f:form class=\"forms-sample\" action=\"#\" method=\"POST\" modelAttribute=\"vo\">\n");
			form.append(divTag.concat("\n"));
				
				form.append("<label for=\"roomNo\">roomNo</label>\n");
				form.append("<f:input type=\"number\" class=\"form-control\" path=\"roomNo\" id=\"roomNo\">");
				form.append("\n");
			
			form.append(endDiv.concat("\n"));
		form.append("</f:form>");
		
		return form.toString();
	}
	
	public static void main(String [] args){
		System.out.println(new FormTagServiceImpl().getFormTag());
	}

}
