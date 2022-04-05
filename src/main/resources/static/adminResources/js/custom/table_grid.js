var columnHeader;
var headerRow;
var view='Grid-View';

var downloadButton = '<button type="button" onclick="downloadProject(projectId)" id="downloadButton" class="border-0 d-bloack mt-2 mx-auto btn btn-outline-secondary btn-rounded btn-icon ml-2 edit-delete-action" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Download"> <i class="fas fa-download action-icon"></i> </button>';
var projectGrid = '<div class="col-12 col-sm-6 col-md-6 col-xl-3 grid-margin stretch-card mb-4 px-3"> <div class="card card1 card-body text-center justify-content-center pt-5">';

$('body').tooltip({
    selector: '[data-toggle="tooltip"]',
    trigger: 'hover',
    container: 'body'
}).on('click', '[data-toggle="tooltip"]', function () {
    $('[data-toggle="tooltip"]').tooltip('dispose');
});

$(document).ready(function(){
	columns = [{
	    column: 'Id',
	    sortable: false,
	    width: '10%',
	    name: 'id'
	},
	{
	    column: 'Project Name',
	    sortable: true,
	    width: '30%',
	    icon: 'fas fa-sort',
	    name: 'projectName'
	},
	{
	    column: 'Project Description',
	    sortable: true,
	    width: '30%',
	    icon: 'fas fa-sort',
	    name: 'projectDescription'
	},
	{
	    column: 'Icon',
	    sortable: false,
	    width: '30%',
	},
	]
	
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

    if (view === 'Grid-View'){
    	 var a={
    	            size: 8,
    	            query: query_String,
    	            sort: sort_Dir,
    	            sortBy: sortByColumn,
    	            isArchive: false,
    	        }
    }
	else if(view === 'Table-View'){
   	 var a={
	            size: length,
	            query: query_String,
	            sort: sort_Dir,
	            sortBy: sortByColumn,
	            isArchive: false,
	        }
	}
   
    // Ajax Call To API
    $.ajax({
        type: "POST",
        url: "projects/" + page,
        data: a,


        success: function(response) {
        	if (view === 'Grid-View'){
        		$('#dataRow').empty();
        		gridView(response,page);
        	}
        	else if(view === 'Table-View'){
        		tableView(response,page)
        	}
        }
    });
}
function gridView(response,page){
	$('#dataRow').empty();
	$('#dataRow').addClass('px-2 py-3');
	
	if (response.numberOfElements == 0) {
		$('#show').html('showing 0 to 0 of ' + response.totalElements + ' entries');
		$('#dataRow').append('<span style="margin:auto;font-size:x-large">No Data Found </span>')
	}else{
		var start = (page * 8);
		$('#show').html('showing ' + (start + 1) + ' to ' + (start + response.numberOfElements) + ' of ' + response.totalElements + ' entries');
	}
	
	$('hr').hide();
	
	$('#order-listing_wrapper_length').hide();
	$('ul.pagination').empty();
	
	
	$.each(response.content,function(index,value){
		downloadButton = downloadButton.replace(/projectId/g, value.id);
		var newProject = projectGrid + '<input type="hidden" value='+value.id+'> <i class="'+value.projectIcon+' fa-3x"></i><h3 class="mt-3">'+value.projectName+'</h3>'+downloadButton;
		$('#dataRow').append(newProject);
	});
	
	if ($('ul.pagination li').length - 2 != response.totalPages) {
        $('ul.pagination').empty();
        buildGridPagination(response.number,response.totalPages,view);
    }
}
function tableView(response,page){
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
            bodyRow += '<tr>' +
                '<td class="py-3">' + ((page * length) + (index + 1)) + '</td>' +
                '<td> ' + value.projectName + '</td>' +
                '<td>' + value.projectDescription + '</td>' +
                '<td><i class="' + value.projectIcon + '"></i> ' + value.projectIcon + '</td>' +
                '</tr>';
        });

        createTableBody(bodyRow, page, response);
    }
}

$(document).on('click','#view-button',function(){
		if($('#view-button').text().trim() === 'Table-View'){
			view = 'Table-View';
			$('#dataRow').append('<table id="dataTable" class="table"></table>');
			buildTable();
			createTableHeader(headerRow);
			fetchData(0);
			
			$('#view-button').text('Grid-View');
			$('#view-button').prepend('<i class="fas fa-th mr-2"></i>');
			
		}else if($('#view-button').text().trim() === 'Grid-View'){
			view = 'Grid-View';
			fetchData(0);

			$('#view-button').text('Table-View');
			$('#view-button').prepend('<i class="fas fa-bars mr-2"></i>');
		}
});

//download project
function downloadProject(projectId) {
	$("#cover-spin").show();
	var ajaxResponse;
    $.ajax({
        type: "GET",
        url: "download-project?projectId=" + projectId,
        async: true,
        success: function(response) {
            ajaxResponse = JSON.parse(response);
            $("#cover-spin").hide();            	
            window.open(ajaxResponse.presignedURL,"_self")
        },
    });
    return ajaxResponse
}
