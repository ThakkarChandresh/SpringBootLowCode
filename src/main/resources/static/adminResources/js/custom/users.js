var headerRow;
var columnHeader;

$(document).ready(function() {

    columns = [{
            column: 'Id',
            sortable: false,
            width: '4%',
        },
        {
            column: 'First Name',
            sortable: true,
            width: '12%',
            icon: 'fas fa-sort',
            name: 'firstName'
        },
        {
            column: 'Last Name',
            sortable: true,
            width: '12%',
            icon: 'fas fa-sort',
            name: 'lastName'
        }, {
            column: 'User Name',
            sortable: true,
            width: '12%',
            icon: 'fas fa-sort',
            name: 'loginVO.username'
        },{
            column: 'Password',
            sortable: false,
            width: '12%',
        },{
            column: 'Created Date',
            sortable: true,
            width: '12%',
            icon: 'fas fa-sort-down',
            name: 'createdDate'
        }, {
            column: 'Updated Date',
            sortable: true,
            width: '12%',
            icon: 'fas fa-sort',
            name: 'updatedDate'
        },
        {
            column: 'Email Verification',
            sortable: false,
            width: '12%',
        },
        {
            column: 'Action',
            sortable: false,
            width: '12%',
        }
    ]
    
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
        url: "fetchUser?page="+page,
        data: {
            size: length,
            query: query_String,
            sort: sort_Dir,
            sortBy: sortByColumn,
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
                	let statusButton;
                	let enabledButton;
                	
                	if(value.status){
                		statusButton = '<label class="badge badge-warning">Pending</label>';
                		enabledButton="";
                		
                	}else{
                		statusButton = '<label class="badge badge-success">Verified</label>';
                		
                		if(value.enabled == 1){
                			enabledButton = '<a class="btn btn-outline-danger btn-fw" href="deactiveUser?id='+value.loginId+'">Deactive</a>'
                		}else{
                			enabledButton = ' <a class="btn btn-outline-success btn-fw" href="activeUser?id='+value.loginId+'">Active</a>'
                		}
                	}
                	
                    bodyRow += '<tr>' +
                        '<td>' + ((page * length) + (index + 1)) + '</td>' +                       
                        '<td>' + value.firstName + '</td>' +
                        '<td>' + value.lastName + '</td>' +
                        '<td>' + value.username + '</td>' +
                        '<td>*******</td>' +
                        '<td>' + getDate(value.createdDate) + '</td>' +
                        '<td>' + getDate(value.updatedDate) + '</td>' +
                        '<td>' + statusButton + '</td>' +
                        '<td>' + enabledButton + '</td>' +
                        '</tr>';
                });

                createTableBody(bodyRow, page, response);
            }
        }
    });
}

function getDate(date){
	const d = new Date(date);
	return d.toDateString();
}	