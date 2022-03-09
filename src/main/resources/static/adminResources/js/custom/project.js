//bulding pagination
var columnHeader;
var headerRow;
var length;
var columns;
var deleteEditAction = '<button type="button" id="editButton" onclick="currentProjectData(projectId)" class="border-0 btn btn-outline-secondary btn-rounded btn-icon edit-delete-action" data-toggle="tooltip"  data-placement="bottom" title="" data-original-title="Edit"><i class="fas fa-edit action-icon"></i></button><button type="button" id="archiveButton" onclick="archiveUnarchiveProject(projectId,true)" class="border-0 btn btn-outline-secondary btn-rounded btn-icon ml-2 edit-delete-action" data-toggle="tooltip"  data-placement="bottom" title="" data-original-title="Archive"><i class="fas fa-archive action-icon"></i> </button> <button type="button" id="deleteButton" onclick="deleteCurrentProject(projectId)" class="border-0 btn btn-outline-secondary btn-rounded btn-icon ml-2 edit-delete-action" data-toggle="tooltip"  data-placement="bottom" title="" data-original-title="Delete"> <i class="fas fa-trash-alt action-icon"></i> </button><button type="button" id="downloadButton" class="border-0 btn btn-outline-secondary btn-rounded btn-icon ml-2 edit-delete-action"  data-toggle="tooltip"  data-placement="bottom" title="" data-original-title="Download"> <i class="fas fa-download action-icon"></i> </button>';
var toogleOptions = '<div class="col-md-auto"> <label class="toggler" id="archived">Archived Projects</label> <div class="toggle"> <input type="checkbox" id="isArchive" class="check"> <b class="b switch"></b> </div></div>';
var unarchiveAction = '<button type="button" id="archiveButton" onclick="archiveUnarchiveProject(projectId,false)" class="border-0 btn btn-outline-secondary btn-rounded btn-icon ml-2 edit-delete-action" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Un-Archive"><i class="fas fa-box-open"></i></button>';
var newdeleteEditAction;

Coloris({
	el : '.coloris',
	swatches : [ '#264653', '#2a9d8f', '#e9c46a', '#f4a261', '#e76f51',
			'#d62828', '#023e8a', '#0077b6', '#0096c7', '#00b4d8',
			'#48cae4' ]
});

$('#projectName').keypress(function(){
	 $('#nameError').html("");
});

$('#projectDescription').keypress(function(){
	 $('#descError').html("");
});

$('#projectIcon').keypress(function(){
	 $('#iconError').html("");
});

$(document).ready(function() {
    
	columns = [{
            column: 'Id',
            sortable: false,
            width: '7%',
            name: 'id'
        },
        {
            column: 'Project Name',
            sortable: true,
            width: '15%',
            icon: 'fas fa-sort',
            name: 'projectName'
        },
        {
            column: 'Project Description',
            sortable: true,
            width: '15%',
            icon: 'fas fa-sort',
            name: 'projectDescription'
        },
        {
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

    $('#order-listing_wrapper_filter').parent()
        .prepend(toogleOptions);

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

    var isArchive = $('#isArchive').prop('checked');
    // Ajax Call To API
    $.ajax({
        type: "POST",
        url: "projects/" + page,
        data: {
            size: length,
            query: query_String,
            sort: sort_Dir,
            sortBy: sortByColumn,
            isArchive: isArchive,
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
                    if (!value.archiveStatus) {
                        newAction = deleteEditAction.replace(/projectId/g, value.id);
                    } else {
                        newAction = unarchiveAction.replace(/projectId/g, value.id);
                    }
                    bodyRow += '<tr>' +
                        '<td>' + ((page * length) + (index + 1)) + '</td>' +
                        '<td> <a href="clickedProjectModules?projectId=' + value.id + '">' + value.projectName + '</a> </td>' +
                        '<td>' + value.projectDescription + '</td>' +
                        '<td><i class="' + value.projectIcon + '"></i> ' + value.projectIcon + '</td>' +
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

function getDate(date) {
    const d = new Date(date);
    return d.toDateString();
}

function archiveUnarchiveProject(projectId, status) {
    $.ajax({
        type: "GET",
        url: "archive-unarchive-project?projectId=" + projectId + "&status=" + status,
        async: false,
        success: function(response) {
        },
    });

    fetchData(0);
}

//validation part
var key = "";
var value = "";

function currentProjectData(projectId) {
    var ajaxResponse;

    $.ajax({

        type: "GET",
        url: "projects/" + projectId,
        async: false,
        success: function(response) {
            ajaxResponse = response;
        },
    });



    $('#projectName').val(ajaxResponse.projectName);
    $('#projectDescription').val(ajaxResponse.projectDescription);
    $('#projectIcon').val(ajaxResponse.projectIcon)
    $('#projectId').val(ajaxResponse.id);
    $('#headerColor').val(ajaxResponse.headerColor);
    $('#menuColor').val(ajaxResponse.menuColor);
    $('#footerColor').val(ajaxResponse.footerColor);
    
    $('.clr-field').eq(0).prop('style','color: '+ajaxResponse.headerColor+';');
    $('.clr-field').eq(1).prop('style','color: '+ajaxResponse.menuColor+';');
    $('.clr-field').eq(2).prop('style','color: '+ajaxResponse.footerColor+';');
    
    $('#model-title').html("Update Project");
    $('#submitButton').val("Update");

    $("#addProjectModel").modal('show');

}

$('#addProjectModel').on('hidden.bs.modal', function(e) {

    $('#model-title').html("Add Project");
    $('#submitButton').val("Add");

    $('#nameError').html("");
    $('#descError').html("");
    $('#iconError').html("");

    $('#projectId').val(0);
    
    $('.clr-field').prop('style','color: rgb(0, 0, 0);');

    $("#projectForm").get(0).reset();
});

//delete project
function deleteCurrentProject(projectId) {
    swal({
            title: "Are you sure?",
            text: "Once deleted, you will not be able to recover this project!",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
        .then((willDelete) => {
            if (willDelete) {
                $.ajax({
                    type: "DELETE",
                    url: "projects/" + projectId,
                    async: false,
                    success: function(response) {
                        swal("Project deleted successfully!", {
                            icon: "success",
                        });
                        fetchData(0)
                    },
                });

            } else {
                fetchData(0)
            }
        });
}

//check project name already exits or not
function checkName(id, projectName) {
    var ajaxResponse;

    $.ajax({

        type: "GET",
        url: "projects/" + id + "/" + projectName,
        async: false,
        success: function(response) {
            ajaxResponse = response;
        },
    });
    return ajaxResponse
}

//validation on form submit
$('#projectForm').submit(function(e) {
    var projectName = $('#projectName').val();
    var projectDesc = $('#projectDescription').val();
    var projectIcon = $('#projectIcon').val();

    var id = $('#projectId').val();

    $('#nameError').html("");
    $('#descError').html("");

    if (projectName == "") {
        key = "nameError";
        value = "Project name is required";
    } else if (projectDesc == "") {
        key = "descError";
        value = "Project description is required";
    } else if (projectIcon == "") {
        key = "iconError";
        value = "Project icon is required";
    } else if (!checkName(id, projectName)) {
        key = "nameError";
        value = "Project name already exist";
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