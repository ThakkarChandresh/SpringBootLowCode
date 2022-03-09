var searchAndLength = '<div class="row"> <div class="d-flex align-items-center col-md-2"> <div class="dataTables_length" id="order-listing_wrapper_length"> <label>Show <select name="length" id="length_dropbox"> <option value="5">5</option> <option value="10" selected>10</option> <option value="15">15</option> </select> entries </label> </div> </div> <div class="d-flex justify-content-end align-items-center col-sm-auto col-md-10"> <div id="order-listing_wrapper_filter" class="dataTables_filter"> <label class="mb-0"><input type="search" class="form-control mr-3" id="txt_searchall" placeholder="Enter search text" /> </label> </div> </div> </div> <hr class="mb-0">'
var ordeListingWrapper = '<div id="order-listing_wrapper" class="dataTables_wrapper dt-bootstrap4 no-footer"></div>'
var middelRow = '<div class="row" id="dataRow"><div class="col-sm-12" id="table">'
var paginationRow = '<div class="row"> <div class="col-sm-12 col-md-5"> <div class="dataTables_info" id="show"></div> </div> <div class="col-sm-12 col-md-7"> <ul class="pagination justify-content-end"> </ul> </div> </div>'
var query_String;
var sort;
var sortBy;
var view_options;

$('body').tooltip({
    selector: '[data-toggle="tooltip"]',
    trigger: 'hover',
    container: 'body'
}).on('click', '[data-toggle="tooltip"]', function () {
    $('[data-toggle="tooltip"]').tooltip('dispose');
});

//creating basic table components
$(document).ready(function() {
	buildTable();
});
function buildTable(){
	table = $('#dataTable');

    $('.table-responsive').empty();

    $('.table-responsive').append(ordeListingWrapper);


    //Appending Top Row Of Search Bar And Length options
    $('#order-listing_wrapper').append(searchAndLength);
    	
    //Appending Middle Row Of Table Data
    $('#order-listing_wrapper').append(middelRow)
    $(table).appendTo('#table');


    //Modifynig Table Class And Data
    $('.table').addClass('dataTable no-footer');
    $('.table').attr({
        role: 'grid',
        ariadescribedby: "order-listing_wrapper_info"
    });
    $('.table').append('<thead><tr></tr></thead>')
    $('.table').append('<tbody></tbody>')
    $('.table thead tr').attr('role', 'row');


    //Appending Last Row Of Show Query And Pagination
    $('#order-listing_wrapper').append(paginationRow);
}


//create table header
function createTableHeader(headerRow) {
    $('#dataTable thead tr').empty();

    //Appending header
    $('#dataTable thead tr').append(headerRow);
}


//creating table body
function createTableBody(bodyRows, page, response) {
    $('#dataTable tbody').empty();

    var start = (page * length);
    
    //Appending Body
    $(document).find('#dataTable tbody').append(bodyRows);

    // Showing from...
    if(response.numberOfElements != 0)
    	$('#show').html('showing ' + (start + 1) + ' to ' + (start + response.numberOfElements) + ' of ' + response.totalElements + ' entries');
    else
    	$('#show').html('showing ' + start + ' to ' + (start + response.numberOfElements) + ' of ' + response.totalElements + ' entries');
   
    //Re Building Paginiation In Case Of Page Number Got Changed
    if ($('ul.pagination li').length - 2 != response.totalPages) {
        $('ul.pagination').empty();
        buildPagination(response.totalPages);
    }
}

// Change Of Pagination According To Button
function buildPagination(totalPages) {

    let pageIndex = '<li class="page-item"><a class="page-link">Previous</a></li>';
    $("ul.pagination").append(pageIndex);

    for (let i = 1; i <= totalPages; i++) {
        if (i == 1) {
            pageIndex = "<li class='page-item active'><a class='page-link'>" +
                i + "</a></li>"
        } else {
            pageIndex = "<li class='page-item'><a class='page-link'>" +
                i + "</a></li>"
        }
        $("ul.pagination").append(pageIndex);
    }

    pageIndex = '<li class="page-item"><a class="page-link">Next</a></li>';
    $("ul.pagination").append(pageIndex);
}


//Changing Color Of Pagination Bar
$(document).on("click", "ul.pagination li a", function() {
    var val = $(this).text();

    if (val.toUpperCase() === "NEXT") {
        let activeValue = parseInt($("ul.pagination li.active").text());
        let totalPages = $("ul.pagination li").length - 2;

        if (activeValue < totalPages) {
            let currentActive = $("li.active");
            fetchData(activeValue, sort, sortBy, query_String);
            $("li.active").removeClass("active");
            currentActive.next().addClass("active");
        }
    } else if (val.toUpperCase() === "PREVIOUS") {
        let activeValue = parseInt($("ul.pagination li.active").text());
        if (activeValue > 1) {
            fetchData(activeValue - 2, sort, sortBy, query_String);
            let currentActive = $("li.active");
            currentActive.removeClass("active");
            currentActive.prev().addClass("active");
        }
    } else {
        fetchData(parseInt(val) - 1, sort, sortBy, query_String);
        $("li.active").removeClass("active");
        $(this).parent().addClass("active");
    }
});


//On Click Of Column Name
$(document).on("click", ".table-headers", function() {

    columnName = $(this).text();

    if ($(this).find('i').attr('class') == "fas fa-sort-up") {

        sort = "DESC"
        sortBy = columnName;
        fetchData(0, sort, sortBy, query_String);
        $('ul.pagination').empty();
        $(this).find('i').attr('class', 'fas fa-sort-down');

        $('.table-headers').not(this).find('i').attr('class', 'fas fa-sort');
    } else if ($(this).find('i').attr('class') == "fas fa-sort-down") {
        sort = "ASC"
        sortBy = columnName;
        fetchData(0, sort, sortBy, query_String);
        $('ul.pagination').empty();
        $(this).find('i').attr('class', 'fas fa-sort-up');

        $('.table-headers').not(this).find('i').attr('class', 'fas fa-sort');
    } else if ($(this).find('i').attr('class') == "fas fa-sort") {
        sort = "ASC"
        sortBy = columnName;
        fetchData(0, sort, sortBy, query_String);
        $('ul.pagination').empty();

        $(this).find('i').attr('class', 'fas fa-sort-up');

        $('.table-headers').not(this).find('i').attr('class', 'fas fa-sort');
    }
});


// Search Bar
$(document).on("search", "#txt_searchall", function() {
    if ($('#txt_searchall').val() == "" || $('#txt_searchall').val() == undefined) {
        if (query_String != undefined) {
            query_String = undefined;
            fetchData(0, sort, sortBy, query_String);
            $('ul.pagination').empty();
        }
    } else {
        query_String = $('#txt_searchall').val();
        fetchData(0, sort, sortBy, query_String);
    }
});

//On Length Dropbox Change
$(document).on("change", "#length_dropbox", function() {
  if (query_String == undefined) {
      fetchData(0, sort, sortBy, query_String);
      $('ul.pagination').empty();
      $('#txt_searchall').val('');
  } else {
      fetchData(0, sort, sortBy, query_String);
      $('ul.pagination').empty();
  }
});


//for grid view

function buildGridPagination(activePage,totalPages) {
	currentPage = activePage;
	totalPage = totalPages;
	
	if(currentPage == 0)
		previousButton = '<button value="PREVIOUS" style="cursor: not-allowed;" type="button" id="previousButton" class="btn btn-outline-secondary btn-rounded btn-icon ml-2" disabled="true"><i class="fas fa-chevron-left pt-1"></i></button>';
	else
		previousButton = '<button value="PREVIOUS" type="button" id="previousButton" class="btn btn-outline-secondary btn-rounded btn-icon ml-2" data-toggle="tooltip"  data-placement="bottom" title="" data-original-title="Previous"><i class="fas fa-chevron-left pt-1"></i></button>';
	
	if(currentPage == totalPages-1)
		nextButton = '<button value="NEXT" style="cursor: not-allowed;" type="button" id="nextButton" class="btn btn-outline-secondary btn-rounded btn-icon ml-2" disabled="true" data-toggle="tooltip"  data-placement="bottom" title="" data-original-title="Next"><i class="fas fa-chevron-right pt-1"></i></button>';
	else
		nextButton = '<button value="NEXT" type="button" id="nextButton" class="btn btn-outline-secondary btn-rounded btn-icon ml-2" data-toggle="tooltip"  data-placement="bottom" title="" data-original-title="Next"><i class="fas fa-chevron-right pt-1"></i></button>';
	
	$("ul.pagination").append(previousButton);
    
    $("ul.pagination").append(nextButton);
    
}
$(document).on("click", "ul.pagination button", function() {
    var val = $(this).val();
    
    if (val.toUpperCase() === "NEXT") {
        if (currentPage+1 < totalPage) {
            $('#dataRow').empty();
            $("ul.pagination").empty();
            fetchData(currentPage+1,query_String);
        }
    } else if (val.toUpperCase() === "PREVIOUS") {
        if (currentPage+1 > 1) {
        	$('#dataRow').empty();
        	$("ul.pagination").empty();
        	fetchData(currentPage - 1, query_String);
        }
    }
});

