var projectDropdownOptions= '<select id="project-module-dropdown" class="form-select border rounded w-25 float-right text-left"> ';
var moduleDropdownOptions = '<select id="module-dropdown" class="form-select border rounded ml-3 mr-2 w-25 float-right text-left"> ';
var projectId;
var moduleId;

$(document).ready(function() {
    $.ajax({

        type: "GET",
        url: "getActiveUserProjects",
        async: false,
        success: function(response) {
            $.each(response, function(index, value) {
                projectDropdownOptions+= '<option value="' + value.id + '">' + value.projectName + '</option>'
            });
        },
    });

    projectDropdownOptions+= '</select>';

    $('#order-listing_wrapper_filter').parent().prepend(projectDropdownOptions);

    projectId = $('#project-module-dropdown').val();
    
    $.ajax({

        type: "GET",
        url: "getProjectModule?projectId="+projectId,
        async: false,
        success: function(response) {
        	if(response.length != 0){
        	$.each(response, function(index, value) {
        	moduleDropdownOptions+= '<option value="' + value.id + '">' + value.moduleName + '</option>'
        	});
        	}else{
        		moduleDropdownOptions+='<option value="0">Select modules</option>'
        	}
        },
        
    });
    
    moduleDropdownOptions +='</select>';
    
    $('#project-module-dropdown').after(moduleDropdownOptions);
});

$(document).on("change", "#project-module-dropdown", function() {
	$('#module-dropdown').empty();
	
	
projectId = $('#project-module-dropdown').val();
    
    $.ajax({

        type: "GET",
        url: "getProjectModule?projectId="+projectId,
        async: false,
        success: function(response) {
        	if(response.length != 0){
	        	$.each(response, function(index, value) {
	        		$('#module-dropdown').append('<option value="'+value.id+'">'+value.moduleName+'</option>')
	        	});
        	}else{
        		$('#module-dropdown').append('<option value="0">Select Modules</option>')
        	}
        },
        
    });
    
    moduleId = $('#module-dropdown').val();
    fetchData(0, projectId,moduleId);
    
});

$(document).on("change", "#module-dropdown",function(){
	moduleId = $('#module-dropdown').val();
	fetchData(0, projectId,moduleId);
});