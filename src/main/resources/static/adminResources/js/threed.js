$('.projectDiv').on('click',function(){
	
		var $box = $(this).find('input:checkbox');
		$box.prop("checked", true);
		
		if ($box.is(":checked")) {
			var group = "input:checkbox[name='" + $box.attr("name") + "']";
			$(group).prop("checked", false);
			$box.prop("checked", true);
		} else {
			$box.prop("checked", false);
		}

});

$('a[href$="#next"]').on('click',function(){
	
	$('#moduleGrid').empty();
	if ($("input:checkbox:checked").length > 0)
	{
		
		var projectId = $("input:checkbox:checked").val();
		var ajaxResponse;
		
		
		$.ajax({

			type : "GET",
			url : "getProjectModule?projectId=" + projectId,
			async : false,
			success : function(response) {
				ajaxResponse = response;
			},
		});
		
		$.each(ajaxResponse, function( index, value ){
			$('#moduleGrid').append('<div class="card d-flex justify-content-center moduleDiv" id="moduleCard"> <div class="card_body"> <p>'+value.moduleName+'</p> <input type="checkbox" class="radio" value="'+value.id+'" name="moduleId" /> </div> </div>')
	    });
		
		$('.moduleDiv').on('click',function(){
			
			var $box = $(this).find('input:checkbox');
			$box.prop("checked", true);
			
			if ($box.is(":checked")) {
				var group = "input:checkbox[name='" + $box.attr("name") + "']";
				$(group).prop("checked", false);
				$box.prop("checked", true);
			} else {
				$box.prop("checked", false);
			}

	});
		
		
	}
	else
	{
	}
});