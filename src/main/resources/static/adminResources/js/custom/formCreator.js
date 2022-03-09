const child = '<div class="col-sm-3"><label class="form-label">Value<span class="required-field">*</span></label><input name="value" class="form-control" placeholder="value" id="input-value"></div><div class="col-sm-3"><label class="form-label">Label<span class="required-field">*</span></label> <input name="label" class="form-control" placeholder="label" id="input-label"></div><div class="col-sm-5"><button type="button" id="subAdd" class="btn btn-outline-primary btn-icon-text py-2 mt-1_8">Add</button><button type="button" id="clear" class="ml-2 btn btn-outline-success btn-icon-text py-2 mt-1_8">Done</button></div>';
var formDetails = [];
var field = {};
var options = [];

$(document).on('click',"#formAddButton", function() {
	field = {};
	field["fieldName"] = camelize($('#fieldName').val());
	field["fieldType"] = $('#fieldType').val();
	
	if($('#fieldType').val() === 'radio' || $('#fieldType').val() === 'checkbox' || $('#fieldType').val() === 'dropdown' ){
		$('#sub-menu').empty();
		$('#sub-menu').append(child);
	}else{
		$('#sub-menu').empty();
		$('#fieldName').val("");
		$('#fieldType').prop('selectedIndex',0);
	}
	
	formDetails.push(field);
	appendToTable(formDetails);
	console.log(formDetails);
});

$(document).on('click','#clear',function(){
	$('#fieldName').val("");
	$('#fieldType').prop('selectedIndex',0);
	$('#sub-menu').empty();
	options = [];
	field = {};
});

$(document).on('click',"#subAdd",function(){
	var subFields = {}
	subFields["value"] = $('#input-value').val();
	subFields["label"] = $('#input-label').val();
	
	options.push(subFields);
	
	field["options"] = options;
	
	$('#input-value').val("");
	$('#input-label').val("");
	
	appendToTable(formDetails);
	console.log(formDetails);
});


function appendToTable(formDetails){
	$('#table-data').empty();
	var row='';
	for (var i = 0 ; i< formDetails.length; i++){
		
		row+= '<tr class="text-center">';
		row+= '<td>'+(i+1)+'</td>';
		row+= '<td>'+(formDetails[i]['fieldName'])+'</td>';
		row+= '<td>'+(formDetails[i]['fieldType'])+'</td>';
			if(formDetails[i]['options']){
				for (var j=0 ; j<formDetails[i]['options'].length; j++){
					if(j == 0){
						row+= '<td>'+formDetails[i]['options'][j]['value'];
					}else{
						row+= '<br>'+formDetails[i]['options'][j]['value'];
					}
				}
				for (var j=0 ; j<formDetails[i]['options'].length; j++){
					if(j == 0){
						row+='<td>'+formDetails[i]['options'][j]['label'];
					}else{
						row+= '<br>'+formDetails[i]['options'][j]['label'];
					}
				}
			}else{
				row+= '<td>-</td>';
				row+= '<td>-</td>';
			}
		row+= '</tr>';
		
	}
		console.log(row);
	$('#table-data').append(row);
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
	
	$('#table-data').empty();
	$('#table-data').append('<td class="text-center" colspan="5">Add Fields From Above Section</td>');
});

function camelize(str) {
    return str.replace(/(?:^\w|[A-Z]|\b\w)/g, function(word, index) {
        return index === 0 ? word.toLowerCase() : word.toUpperCase();
    }).replace(/\s+/g, '');
}
