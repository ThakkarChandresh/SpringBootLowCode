//bulding pagination
var columnHeader;
var headerRow;
var length;
var columns;
var deleteEditAction = '<button type="button" id="editButton" onclick="currentModuleData(moduleId)" class="border-0 btn btn-outline-secondary btn-rounded btn-icon edit-delete-action" data-toggle="tooltip"  data-placement="bottom" title="" data-original-title="Edit"><i class="fas fa-edit action-icon"></i></button> <button type="button" id="archiveButton" onclick="archiveUnarchiveModule(moduleId,true)" class="border-0 btn btn-outline-secondary btn-rounded btn-icon ml-2 edit-delete-action" data-toggle="tooltip"  data-placement="bottom" title="" data-original-title="Archive"> <i class="fas fa-archive action-icon"></i> </button> <button type="button" id="deleteButton" onclick="deleteModule(moduleId)" class="border-0 btn btn-outline-secondary btn-rounded btn-icon ml-2 edit-delete-action" data-toggle="tooltip"  data-placement="bottom" title="" data-original-title="Delete"> <i class="fas fa-trash-alt action-icon"></i> </button>';
var toogleOptions = '<div class="col-md-auto"> <label class="toggler" id="archived">Archived Modules</label> <div class="toggle"> <input type="checkbox" id="isArchive" class="check"> <b class="b switch"></b> </div></div>';
var newdeleteEditAction;
var unarchiveAction = '<button type="button" id="archiveButton" onclick="archiveUnarchiveModule(moduleId,false)" class="border-0 btn btn-outline-secondary btn-rounded btn-icon ml-2 edit-delete-action" data-toggle="tooltip"  data-placement="bottom" title="" data-original-title="Un-Archive"><i class="fas fa-box-open"></i></button>';

var dropdownOptions = '<select id="project-module-dropdown" class="form-select border rounded w-25 col-2 float-right text-left"> ';
var projectId;

$(document).ready(
    function() {

        columns = [{
            column: 'Id',
            sortable: false,
            width: '7%',
            name: 'id'
        }, {
            column: 'Module Name',
            sortable: true,
            width: '15%',
            icon: 'fas fa-sort',
            name: 'moduleName'
        }, {
            column: 'Module Description',
            sortable: true,
            width: '15%',
            icon: 'fas fa-sort',
            name: 'moduleDescription'
        },{
            column: 'Icon',
            sortable: false,
            width: '18%',
        }, 
        {
            column: 'Created Date',
            sortable: true,
            width: '15%',
            icon: 'fas fa-sort-down',
            name: 'createdDate'
        }, {
            column: 'Updated Date',
            sortable: true,
            width: '15%',
            icon: 'fas fa-sort',
            name: 'updatedDate'
        }, {
            column: 'Action',
            sortable: false,
            width: '15%',
        }]

        // Creating Project Dropdown
        $.ajax({

            type: "GET",
            url: "getActiveUserProjects",
            async: false,
            success: function(response) {
                $.each(response, function(index, value) {
                    if (value.id == clickedProjectId) {
                        dropdownOptions += '<option value="' + value.id +'" selected>' + value.projectName + '</option>'
                        projectId = clickedProjectId;
                    } else {
                        dropdownOptions += '<option value="' + value.id +'">' + value.projectName + '</option>'
                    }
                });
            },
        });

        dropdownOptions += '</select>';

        $('#order-listing_wrapper_filter').parent()
            .prepend(dropdownOptions);
        $('#order-listing_wrapper_filter').parent()
            .prepend(toogleOptions);

        // Initial Fetching
        fetchData(0);

        for (i = 0; i < columns.length; i++) {

            if (columns[i] && columns[i].sortable) {
                columnHeader = '<th style="cursor:pointer;width: ' + columns[i].width + ';" class="table-headers py-3">';

                columnHeader = columnHeader + columns[i].column;

                columnHeader = columnHeader + '<i class="' + columns[i].icon + '"></i>';
            } else {
                columnHeader = '<th style="width: ' + columns[i].width + ';" class="table-headers py-3">';

                columnHeader = columnHeader + columns[i].column;
            }

            columnHeader = columnHeader + '</th>';

            headerRow += columnHeader
        }

        createTableHeader(headerRow);

    });
$(document).on('click', '#isArchive', function() {
	 $('#archived').toggleClass("toggler--is-active");
	    $('.toggle').toggleClass("bg-primary");
	    
	    $('#txt_searchall').val("");

	    fetchData(0);
});

$('#moduleName').keypress(function(){
	 $('#nameError').html("");
});

$('#moduleDescription').keypress(function(){
	 $('#descError').html("");
});

$('#moduleIcon').keypress(function(){
	 $('#iconError').html("");
});

// Delete Module
function deleteModule(moduleId) {
    swal({
            title: "Are you sure?",
            text: "Once deleted, you will not be able to recover this module!",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
        .then((willDelete) => {
            if (willDelete) {
                $.ajax({
                    type: "DELETE",
                    url: "modules/" + moduleId,
                    async: false,
                    success: function(response) {
                        swal("Module deleted successfully!", {
                            icon: "success",
                        });
                        fetchData(0, sort, sortBy, query_String)
                    },
                });
            } else {
                fetchData(0, sort, sortBy, query_String)
            }
        });
}

// on project dropbox change
projectId = $(document).find(":selected", '#project-module-dropdown').val();

$(document).on("change", "#project-module-dropdown", function() {
    projectId = $(this).find(":selected").val();

    $('#projectDropdownId').val(projectId);

    fetchData(0, sort, sortBy, query_String);
});

function fetchData(page, sort, sortBy, query_String) {

    length = $("#length_dropbox").val();

    // Replacing Column name with actual name and modifying icon in column array
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
    
    var isArchive = $('#isArchive').prop('checked');

    // Ajax Call To API
    $.ajax({
        type: "POST",
        url: "modules/" + page,
        data: {
            size: length,
            query: query_String,
            sort: sort_Dir,
            sortBy: sortByColumn,
            projectId: projectId,
            isArchive: isArchive,
        },

        success: function(response) {
            if (response.numberOfElements == 0) {
                var cols = $("#dataTable thead tr th").length;

                let
                    bodyRow = '<tr style="height:100px; text-align:center" >' +
                    '<td colspan="' + cols +
                    '" style="font-size:x-large">' + "No Data Found" +
                    '</td>' + +'</tr>'

                createTableBody(bodyRow, page, response);

            } else {
                let
                    bodyRow;

                // Add Values To Table
                $.each(response.content, function(index, value) {
                	 if (!value.archiveStatus) {
                		 newAction = deleteEditAction.replace(/moduleId/g,value.id);
                     } else {
                         newAction = unarchiveAction.replace(/moduleId/g, value.id);
                     }

                    

                    bodyRow += '<tr>' + '<td>' + ((page * length) + (index + 1)) + '</td>' +
                        '<td>  <a href="clickedModuleForms?projectId=' + value.projectVO.id + '&moduleId=' + value.id +'">' + value.moduleName + '</a> </td>' +
                        '<td>' + value.moduleDescription + '</td>' + 
                        '<td><i class="' + value.moduleIcon + '"></i> ' + value.moduleIcon + '</td>' +
                        '<td>' + getDate(value.createdDate) + '</td>' + 
                        '<td>' + getDate(value.updatedDate) + '</td>' + 
                        '<td>' + newAction + '</td>' + 
                        '</tr>';
                });

                createTableBody(bodyRow, page, response);
            }
        }
    });
}

function archiveUnarchiveModule(moduleId,status) {
    $.ajax({
        type: "GET",
        url: "archive-unarchive-module?moduleId="+moduleId+"&status="+status,
        async: false,
        success: function(response) {},
    });

    fetchData(0);
}

function getDate(date) {
    const
        d = new Date(date);
    return d.toDateString();
}

// validation part
var key = "";
var value = "";

$('#currentProjectDropBox').prop("disabled", true);

function currentModuleData(moduleId) {
    var ajaxResponse;

    $.ajax({

        type: "GET",
        url: "modules/" + moduleId,
        async: false,
        success: function(response) {
            ajaxResponse = response;
        },
    });

    $('#moduleName').val(ajaxResponse.moduleName);
    $('#moduleDescription').val(ajaxResponse.moduleDescription);
    $('#moduleId').val(ajaxResponse.id);
    $('#moduleIcon').val(ajaxResponse.moduleIcon);
    $('#projectDropdownId').val(ajaxResponse.projectVO.id);

    $('#model-title').html("Update Module");
    $('#submitButton').val("Update");

    $("#addModuleModel").modal('show');

}

$('#addModuleModel').on('hidden.bs.modal', function(e) {
    $('#model-title').html("Add Module");
    $('#submitButton').val("Add");

    $('#moduleId').val(0);

    $('#nameError').html("");
    $('#descError').html("");

    $('#moduleName').val("");
    $('#moduleDescription').val("");
});

function checkModuleName(projectId, moduleId, moduleName) {
    var ajaxResponse;

    $.ajax({

        type: "GET",
        url: "modules/" + moduleId + "/" + projectId + "/" + moduleName,
        async: false,
        success: function(response) {
            ajaxResponse = response;
        },
    });

    return ajaxResponse;
}

$('#moduleForm').submit(function(e) {
    var moduleName = $('#moduleName').val();
    var moduleDesc = $('#moduleDescription').val();
    var moduleIcon = $('#moduleIcon').val();
    
    var projectId = $('#projectDropdownId').val();
    var moduleName = $('#moduleName').val();
    var moduleId = $('#moduleId').val();
    
    
    $('#nameError').html("");
    $('#descError').html("");
    $('#iconError').html("");
    
    if (moduleName == "") {
        key = "nameError";
        value = "Module name is required";
    } else if (moduleDesc == "") {
        key = "descError";
        value = "Module description is required";
    }else if (moduleIcon == "") {
        key = "iconError";
        value = "Module icon is required";
    }
    else if (!checkModuleName(projectId, moduleId, moduleName)) {
        key = "nameError";
        value = "Module name already exists";
    } else {
        key = "";
        value = "";
    }

    if (key && key !== "" && value && value !== "") {
        $('#' + key).html(value);
        return false;
    } else {
        return true;
    }

});