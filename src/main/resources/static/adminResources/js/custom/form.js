//bulding pagination
var columnHeader;
var headerRow;
var length;
var columns;
var deleteEditAction = '<button type="button" id="infoButton" onclick="formInfo(formId)" class="border-0 btn btn-outline-secondary btn-rounded btn-icon edit-delete-action" data-toggle="tooltip"  data-placement="bottom" title="" data-original-title="Form Details"><i class="fas fa-info-circle action-icon"></i></button><button type="button" id="archiveButton" onclick="archiveUnarchiveForm(formId,true)" class="border-0 btn btn-outline-secondary btn-rounded btn-icon ml-2 edit-delete-action" data-toggle="tooltip"  data-placement="bottom" title="" data-original-title="Archive"><i class="fas fa-archive action-icon"></i> </button> <button type="button" id="deleteButton" onclick="deleteCurrentForm(formId)" class="border-0 btn btn-outline-secondary btn-rounded btn-icon ml-2 edit-delete-action" data-toggle="tooltip"  data-placement="bottom" title="" data-original-title="Delete"> <i class="fas fa-trash-alt action-icon"></i> </button><a href="preview/add?formid=formId" target="_blank"><button type="button" id="previewButton" class="border-0 btn btn-outline-secondary btn-rounded btn-icon ml-2 edit-delete-action"  data-toggle="tooltip"  data-placement="bottom" title="" data-original-title="Preview"><i class="fas fa-eye action-icon"></i></button></a>';
var newdeleteEditAction;
var unarchiveAction = '<button type="button" id="archiveButton" onclick="archiveUnarchiveForm(formId,false)" class="border-0 btn btn-outline-secondary btn-rounded btn-icon ml-2 edit-delete-action" data-toggle="tooltip"  data-placement="bottom" title="" data-original-title="Un-Archive"><i class="fas fa-box-open"></i></button>';
var projectDropdownOptions= '<select id="project-module-dropdown" class="form-select border rounded w-25 float-right text-left"> ';
var moduleDropdownOptions = '<select id="module-dropdown" class="form-select border rounded ml-3 mr-2 w-25 float-right text-left"> ';
var projectId;
var moduleId;

$(document).ready(function() {

    columns = [{
            column: 'Id',
            sortable: false,
            width: '10%',
            name: 'id'
        },
        {
            column: 'Form Name',
            sortable: true,
            width: '15%',
            icon: 'fas fa-sort',
            name: 'formName'
        },
        {
            column: 'Module Name',
            sortable: true,
            width: '15%',
            icon: 'fas fa-sort',
            name: 'moduleVO.moduleName'
        },
        {
            column: 'Project Name',
            sortable: true,
            width: '15%',
            icon: 'fas fa-sort',
            name: 'moduleVO.projectVO.projectName'
        },
        {
            column: 'Created Date',
            sortable: true,
            width: '15%',
            icon: 'fas fa-sort-down',
            name: 'createdDate'
        },
        {
            column: 'Updated Date',
            sortable: true,
            width: '15%',
            icon: 'fas fa-sort',
            name: 'updatedDate'
        },
        {
            column: 'Action',
            sortable: false,
            width: '15%',
        }
    ]

  //creating projects and modules dropdown
    $.ajax({

        type: "GET",
        url: "getActiveUserProjects",
        async: false,
        success: function(response) {
            $.each(response, function(index, value) {
            	if(value.id == clickedProjectId){
            		projectDropdownOptions += '<option value="' + value.id + '" selected>' + value.projectName + '</option>'
            	}else{
            		projectDropdownOptions += '<option value="' + value.id + '">' + value.projectName + '</option>'
            	}
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
        		if(value.id == clickedModuleId){
        			moduleDropdownOptions+= '<option value="' + value.id + '" selected>' + value.moduleName + '</option>'
        		}else{
        			moduleDropdownOptions+= '<option value="' + value.id + '">' + value.moduleName + '</option>'
        		}
        	});
        	
        	}else{
        		moduleDropdownOptions+='<option selected="true" disabled="disabled">Select modules</option>'
        	}
        },
        
    });
    
    moduleDropdownOptions +='</select>';
    
    $('#project-module-dropdown').after(moduleDropdownOptions);
    
    //Initail Setting Module Id As Clicked Module Id
    if (clickedModuleId){
    	moduleId = clickedModuleId;
    	$('#projectId').val(clickedProjectId);
    	changeModalModuleDropdown();
    	$('#moduleId').val(clickedModuleId);
    }else{
    	moduleId = $('#module-dropdown').val();
    }
    
    
    //Initial Fetching
    fetchData(0);


    for (i = 0; i < columns.length; i++) {

        if (columns[i] && columns[i].sortable) {
            columnHeader = '<th style="cursor:pointer;width: ' + columns[i].width + ';" class="table-headers">';

            columnHeader = columnHeader + columns[i].column;

            columnHeader = columnHeader + '<i class="' + columns[i].icon + '"></i>';
        } else {
            columnHeader = '<th style="width: ' + columns[i].width + ';" class="table-headers">';

            columnHeader = columnHeader + columns[i].column;
        }

        columnHeader = columnHeader + '</th>';

        headerRow += columnHeader
    }

    createTableHeader(headerRow);

});

$(document).on("change", "#project-module-dropdown", function() {
	changePageModuleDropdown();
	$('#projectId').val($('#project-module-dropdown').val());
	changeModalModuleDropdown();
});

function changePageModuleDropdown(){
	$('#module-dropdown').empty();
	
	projectId = $('#project-module-dropdown').val();
	    
	    $.ajax({

	        type: "GET",
	        url: "getProjectModule?projectId="+projectId,
	        async: false,
	        success: function(response) {
	        	$('#module-dropdown').append('<option selected="true" disabled="disabled">Select Modules</option>')
		        	$.each(response, function(index, value) {
		        		$('#module-dropdown').append('<option value="'+value.id+'">'+value.moduleName+'</option>')
		        	});
	        },
	        
	    });
	    
	    moduleId = $('#module-dropdown').val();
}

$(document).on("change", "#module-dropdown",function(){
	moduleId = $('#module-dropdown').val();
	fetchData(0, sort, sortBy, query_String);
	$('#moduleId').val($('#module-dropdown').val());
});

$("#projectId").change(function() {
	$(document).find('#project-module-dropdown').val($('#projectId').val());
	changeModalModuleDropdown();
	changePageModuleDropdown();
});

function changeModalModuleDropdown(){
	$('#moduleId').empty();
		
		projectId = $('#projectId').val();
	    
	    $.ajax({
	
	        type: "GET",
	        url: "getProjectModule?projectId="+projectId,
	        async: false,
	        success: function(response) {
	        	$('#moduleId').append('<option selected="true" disabled="disabled">Select Modules</option>')
		        	$.each(response, function(index, value) {
		        		$('#moduleId').append('<option value="'+value.id+'">'+value.moduleName+'</option>')
		        	});
	        },
	        
	    });
}

$('#moduleId').change(function(){
	
	$(document).find('#module-dropdown').val($('#moduleId').val());
	moduleId = $('#module-dropdown').val();
	fetchData(0, sort, sortBy, query_String);
});

function fetchData(page, sort, sortBy, query_String) {

    length = $("#length_dropbox").val();
    
    //Replacing Column name with actual name and modifying icon in column array
    if (sort && sortBy) {
        for (i = 0; i < columns.length; i++) {
            if (columns[i] && columns[i].sortable) {
                if (sortBy == columns[i].column) {
                    sortBy = columns[i].name;
                }
            }
        }
    }

    let pageNumber = (typeof page !== 'undefined') ? page : 0;
    let sort_Dir = (typeof sort !== 'undefined') ? sort : 'DESC';
    let sortByColumn = (typeof sortBy !== 'undefined') ? sortBy : 'createdDate';

    // Ajax Call To API
    $.ajax({
        type: "POST",
        url: "form/"+page,
        data: {
            size: length,
            query: query_String,
            sort: sort_Dir,
            sortBy: sortByColumn,
            projectId: projectId,
            moduleId: moduleId,
        },

        success: function(response) {

            if (response.numberOfElements == 0) {
                var cols = $("#dataTable thead tr th").length;

                let bodyRow = '<tr style="height:100px; text-align:center" >' +
                    '<td colspan="' + cols + '" style="font-size:x-large">' + "No Data Found" + '</td>' +
                    +'</tr>'

                createTableBody(bodyRow, page, response);

            } else {
                let bodyRow;

                // Add Values To Table
                $.each(response.content, function(index, value) {

                    newdeleteEditAction = deleteEditAction.replace(/formId/g, value.formId);

                    bodyRow += '<tr>' +
                        '<td>' + ((page * length) + (index + 1)) + '</td>' +
                        '<td>' + value.formName+ '</td>' +
                        '<td>' + value.moduleVO.moduleName+ '</td>' +
                        '<td>' + value.moduleVO.projectVO.projectName + '</td>' +
                        '<td>' + getDate(value.createdDate) + '</td>' +
                        '<td>' + getDate(value.updatedDate) + '</td>' +
                        '<td>' + newdeleteEditAction + '</td>' +
                        '</tr>';
                });

                createTableBody(bodyRow, page, response);
            }
        }
    });
}

function deleteCurrentForm(formId) {
    swal({
            title: "Are you sure?",
            text: "Once deleted, you will not be able to recover this form!",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
        .then((willDelete) => {
            if (willDelete) {
                $.ajax({
                    type: "DELETE",
                    url: "form/" + formId,
                    async: false,
                    success: function(response) {
                        swal("Form deleted successfully!", {
                            icon: "success",
                        });
                        fetchData(0);
                    },
                });

            } else {
                fetchData(0);
            }
        });
}

$('#addFormsButton').click(function(){
	window.location.href="addForms";
});

function archiveUnarchiveForm(formId,status) {
    $.ajax({
        type: "GET",
        url: "archive-unarchive-form?formId="+formId+"&status="+status,
        async: false,
        success: function(response) {},
    });
    
    fetchData(0);
}

function getDate(date){
	const d = new Date(date);
	return d.toDateString();
}
