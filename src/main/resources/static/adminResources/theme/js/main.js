var form;

$(document).ready(function(){
var formList;

	$.ajax({
	    type: "GET",
	    url: "getForm?formid="+formId,
	    async: false,
	    success: function(response) {
	    	formList = response[0];
	    	buildPage(formList);
	    	
	    	if(pageName === 'add'){
	    		addForm(form);
	    	}else if(pageName === 'view'){
	    		addTable(form);
	    	}
	    },
	});
});


function buildPage(formList){
	$('#add-url').prop('href','add?formid='+formList.formId);
	$('#view-url').prop('href','view?formid='+formList.formId);
	
	$('.head-text').html(formList.projectVO.projectName);
	$('#projectLogo').prop('class',formList.projectVO.projectIcon);
	$('#logoName').html(formList.projectVO.projectName);

	$('.card-title').html(formList.formName);
 	
	$.ajax({
        type: "GET",
        url: "../form/"+formList.formId,
        async: false,
        success: function(response) {
        	form = response;	
        },
    });
	
	$('.foot-text').html('Copyright@'+formList.projectVO.projectName);
}

function addForm(form){
	console.log(generateForm(form));
	$('#form-card').append(generateForm(form));	
}


function addTable(form){
	for(var i=0 ; i<form.length;i++){
		
		$('#order-listing > thead >tr').append('<th>'+form[i].fieldName+'</th>');
	}
}