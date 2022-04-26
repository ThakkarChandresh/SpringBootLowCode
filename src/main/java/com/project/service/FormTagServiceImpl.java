package com.project.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.enums.CCodeEnum;
import com.project.model.FormDetailsVO;
import com.project.model.FormsVO;
import com.project.util.BaseMethods;

@Service
public class FormTagServiceImpl implements FormTagService {

	@Autowired
	private FormsService formsService;

	@Autowired
	private BaseMethods baseMethods;

	private String divTag = "<div class=\"form-group\">";
	private String endDiv = "</div>";

	@Override
	public String getFormTag(FormsVO formsVO) {

		List<FormDetailsVO> formDetailsVOList = this.formsService.findFormDetails(formsVO.getFormId());

		StringBuilder form = new StringBuilder();
		form.append("<f:form class=\"forms-sample\" id=\"" + formsVO.getFormName() + "Form" + "\" action=\"/"
				+ baseMethods.allLetterCaps(formsVO.getModuleVO().getModuleName()).toLowerCase() + "/insert"
				+ baseMethods.allLetterCaps(formsVO.getFormName()) + "\" method=\"POST\" modelAttribute=\""
				+ baseMethods.camelize(formsVO.getFormName()) + "VO" + "\">\n");
		form.append("<div class=\"modal-body\">");

		for (FormDetailsVO formDetail : formDetailsVOList) {
			form.append(divTag.concat("\n"));
			form.append("<label for=\"" + formDetail.getFieldName() + "\">"
					+ baseMethods.reverseCamelize(formDetail.getFieldName()) + "</label>\n");

			if (formDetail.getFieldType().equals("text-area")) {
				form.append("<f:textarea class=\"form-control\" path=\"" + formDetail.getFieldName()
						+ CCodeEnum.ID.getValue() + formDetail.getFieldName() + "\" rows=\"4\" />");
			} else if (formDetail.getFieldType().equals("radiobutton")
					|| formDetail.getFieldType().equals("checkbox")) {
				List<String> values = new ArrayList<String>(Arrays.asList(formDetail.getValue().split(",")));
				List<String> labels = new ArrayList<String>(Arrays.asList(formDetail.getLabel().split(",")));

				for (int i = 0; i < values.size(); i++) {
					form.append("<div class=\"form-check\">");
					form.append("<label for=\"" + labels.get(i) + "\">" + baseMethods.reverseCamelize(labels.get(i))
							+ "</label>\n");
					form.append("<f:" + formDetail.getFieldType() + " class=\"form-check-input ml-2\" path=\""
							+ formDetail.getFieldName() + CCodeEnum.ID.getValue() + labels.get(i) + "\" value=\""
							+ values.get(i) + "\"/>");
					form.append("</div>\n");
				}
			} else if (formDetail.getFieldType().equals("dropdown")) {
				form.append("<f:select path=\"" + formDetail.getFieldName() + "\" class=\"ml-2\" id=\""
						+ formDetail.getFieldName() + "\">");
				List<String> values = new ArrayList<String>(Arrays.asList(formDetail.getValue().split(",")));
				List<String> labels = new ArrayList<String>(Arrays.asList(formDetail.getLabel().split(",")));

				for (int i = 0; i < values.size(); i++) {
					form.append("<f:option value=\"" + values.get(i) + "\" >"
							+ baseMethods.reverseCamelize(labels.get(i)) + "</f:option>\n");
				}

				form.append("</f:select>");
			} else {
				form.append("<f:input type=\"" + formDetail.getFieldType() + "\" class=\"form-control\" path=\""
						+ formDetail.getFieldName() + CCodeEnum.ID.getValue() + formDetail.getFieldName() + "\" />");
			}
			form.append("\n");
			form.append(endDiv.concat("\n\n"));
		}
		form.append("<f:input type=\"hidden\" path=\"" + baseMethods.camelize(formsVO.getFormName()) + "Id" + "\" />");
		form.append(endDiv);
		form.append("<div class=\"modal-footer\">");
		form.append(
				"<button class=\"btn btn-light mr-2\" type=\"reset\" data-dismiss=\"modal\">Cancel</button><button type=\"submit\" id=\"submit\" class=\"btn btn-primary\">Submit</button>");
		form.append(endDiv);
		form.append("</f:form>");

		return form.toString();
	}
}
