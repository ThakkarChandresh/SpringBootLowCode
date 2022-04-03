const child = '<div class="col-sm-3"><label class="form-label">Value<span class="required-field">*</span></label><input name="value" class="form-control" placeholder="value" id="input-value"><span id="valueError" class="text-danger font-weight-bold"></span></div><div class="col-sm-3"><label class="form-label">Label<span class="required-field">*</span></label> <input name="label" class="form-control" placeholder="label" id="input-label"><span id="labelError" class="text-danger font-weight-bold"></span></div><div class="col-sm-5"><button type="button" id="subAdd" class="btn btn-outline-primary btn-icon-text py-2 mt-1_8">Add</button><button type="button" id="clear" class="ml-2 btn btn-outline-success btn-icon-text py-2 mt-1_8">Done</button></div>';
const note = '<label class="font-weight-bold text-danger" id="note">Note: Please add values and lables for types:"Radio Button, Checkbox & Dropdown"</label>'
var formDetails = [];
var field = {};
var options = [];

var key = "";
var value = "";

function validateField(){
	var fieldName = $('#fieldName').val();
	
	$('#fieldNameError').html("");
	
	if (fieldName == "") {
        key = "fieldNameError";
        value = "Field name is required";
    }else {
        key = "";
        value = "";
    }

    if (key && key !== "" && value && value !== "") {
        $('#' + key).html(value);
        return false;
    } else {
        return true;
    }
}
$('#fieldName').keypress(function(){
	 $('#fieldNameError').html("");
});

function checkFormName(projectId, moduleId, formName) {
    var ajaxResponse;

    $.ajax({

        type: "GET",
        url: "form/" + moduleId + "/" + projectId + "/" + formName,
        async: false,
        success: function(response) {
            ajaxResponse = response;
        },
    });

    return ajaxResponse;
}

function validateForm(){
var formName = $('#name').val();
var formDescription = $('#description').val();

var projectId = $('#projectId').val();
var moduleId = $('#moduleId').val();

	$('#formNameError').html("");
	$('#formDescError').html("");

	
	if (formName == "") {
        key = "formNameError";
        value = "Form name is required";
    }else if(formDescription == ""){
    	key = "formDescError";
        value = "Form description is required";
    }else if(formDetails.length == 0){
    	key = "fieldNameError";
        value = "Add at least one field";
    }else if (!checkFormName(projectId, moduleId, formName)) {
        key = "formNameError";
        value = "Form name already exists"
    }else {
        key = "";
        value = "";
    }

    if (key && key !== "" && value && value !== "") {
        $('#' + key).html(value);
        return false;
    } else {
        return true;
    }
}

function validateSubFields(){
		
	var inputVal = $(document).find('#input-value').val();
	var inputLabel = $(document).find('#input-label').val();
	
	
	$(document).find('#valueError').html("");
	$(document).find('#labelError').html("");

	if (inputVal == "") {
        key = "valueError";
        value = "Value is required";
    }else if(inputLabel == ""){
    	key = "labelError";
        value = "Label is required";
    }else {
        key = "";
        value = "";
    }
	
    if (key && key !== "" && value && value !== "") {
        $(document).find('#' + key).html(value);
        return false;
    } else {
        return true;
    }
}

$('#name').keypress(function(){
	 $('#formNameError').html("");
});

$('#description').keypress(function(){
	 $('#formDescError').html("");
});


$(document).on('click',"#fieldAddButton", function() {
	if(validateField()){
		options = [];
		field = {};
		field["fieldName"] = camelize($('#fieldName').val());
		field["fieldType"] = $('#fieldType').val();
		
		if($('#fieldType').val() === 'radiobutton' || $('#fieldType').val() === 'checkbox' || $('#fieldType').val() === 'dropdown' ){
			$('#sub-menu').empty();
			$('#sub-menu').append(child);
			$(note).insertBefore('#sub-menu');
		}else{
			$('#sub-menu').empty();
			$('#fieldName').val("");
			$('#fieldType').prop('selectedIndex',0);
		}
		
		formDetails.push(field);
		appendToTable("table-data",formDetails);
	}
});

$(document).on('click','#clear',function(){
	if(options.length == 0){
		$(document).find("#note").remove();
		$(note).insertBefore('#sub-menu');
	}else{
		$('#fieldName').val("");
		$('#fieldType').prop('selectedIndex',0);
		$('#sub-menu').empty();
		options = [];
		field = {};
	}
});

$(document).on('keypress' , '#input-value , #input-label',function(){
	 $(document).find("#note").remove();
});


$(document).on('click',"#subAdd",function(){
	if(validateSubFields()){
		var subFields = {}
		subFields["value"] = $('#input-value').val();
		subFields["label"] = $('#input-label').val();
		
		options.push(subFields);
		
		field["options"] = options;
		
		$('#input-value').val("");
		$('#input-label').val("");
		
		appendToTable("table-data",formDetails);
	}
});


function appendToTable(targetId,formDetails){
	$('#'+targetId).empty();
	var row='';
	for (var i = 0 ; i< formDetails.length; i++){
		var value='';
		var label='';

		row+= '<tr class="text-center">';
		row+= '<td class="border-right">'+(i+1)+'</td>';
		row+= '<td class="border-right">'+(formDetails[i]['fieldName'])+'</td>';
		row+= '<td class="border-right">'+(formDetails[i]['fieldType'])+'</td>';
			if(formDetails[i]['options']){
				for (var j=0 ; j<formDetails[i]['options'].length; j++){
					if(j == 0){
						value+= '<td class="border-right"><ul><li>'+formDetails[i]['options'][j]['value']+'</li>';
						label+='<td><ul><li>'+formDetails[i]['options'][j]['label']+'</li>'
					}else{
						value+= '<li>'+formDetails[i]['options'][j]['value']+'</li>';
						label+= '<li>'+formDetails[i]['options'][j]['label']+'</li>';
					}
				}
				row+= value+'</ul>';
				row+=label+'</ul>';
			}else{
				row+= '<td class="border-right">-</td>';
				row+= '<td>-</td>';
			}
		row+= '</tr>';
		
	}
	$('#'+targetId).append(row);
}

$('#formModal').on('hidden.bs.modal', function(e) {
	options = [];
	field = {};
	formDetails = [];
	
	$('#name').val("");
	$('#description').val("");
	
	$('#fieldName').val("");
	$('#fieldType').prop('selectedIndex',0);
	$('#sub-menu').empty();
	
	$('#formNameError').html("");
	$('#formDescError').html("");
	$('#fieldNameError').html("");
	$(document).find('#valueError').html("");
	$(document).find('#labelError').html("");
//	$('#projectId').prop('selectedIndex',0);
//	changeModelModuleDropdown();
	
	$('#table-data').empty();
	$('#table-data').append('<td class="text-center" colspan="5">Add Fields From Above Section</td>');
});

$('#formModal').on('shown.bs.modal', function(e) {
	options = [];
	field = {};
	formDetails = [];
	
	$('#name').val("");
	$('#description').val("");
	
	$('#fieldName').val("");
	$('#fieldType').prop('selectedIndex',0);
	$('#sub-menu').empty();
	
	$('#table-data').empty();
	$('#table-data').append('<td class="text-center" colspan="5">Add Fields From Above Section</td>');
});


$('#submit').click(function(){
	if(validateForm()){
		const formData = {
	        	'formDetails' :formDetails,
	        	'projectId':$('#projectId').val(),
	        	'moduleId':$('#moduleId').val(),
	        	'formName': $('#name').val(),
	        	'formDescription': $('#description').val(),
	        };
		
		 $.ajax({
		        type: "POST",
		        url: "form",
		        contentType : 'application/json',
		        data: JSON.stringify(formData),
		        success: function(response){
		        	$("#formModal").modal('hide');
		        	fetchData(0, sort, sortBy, query_String);
		        }
		 });
	}
});

function formInfo(formId){
	   $.ajax({
	        type: "GET",
	        url: "form/"+formId,
	        async: false,
	        success: function(response) {
	        	formViewDetails = response;
	        },
	    });
	   $('#formDetailModal').modal('show');
	   appendToTable("form-details-data",formViewDetails);
}

$('#formDetailModal').on('hidden.bs.modal', function(e) {
	$('#form-details-data').empty();
});


function camelize(str) {
    return str.replace(/(?:^\w|[A-Z]|\b\w)/g, function(word, index) {
        return index === 0 ? word.toLowerCase() : word.toUpperCase();
    }).replace(/\s+/g, '');
}