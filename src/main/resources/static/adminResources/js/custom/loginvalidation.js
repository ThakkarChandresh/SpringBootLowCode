key = "";
value = "";
/*function successMessage(message) {
	if (message == 'E-mail verified successfully') {
		$.toast({
			heading : 'success',
			text : message,
			showHideTransition : 'fade',
			icon : 'success',
			loaderBg : '#00ff00',
			position : 'top-right'
		})
	} else if (message && message !== '') {
		$.toast({
			heading : 'Warning!',
			text : message,
			showHideTransition : 'fade',
			icon : 'error',
			loaderBg : '#f2a654',
			position : 'top-right'
		})
	}
}*/


$( "#username,#password" ).on( "copy paste drop", function() {
    return false;
});


$("#loginForm").submit(function(){

	var username = $('#username').val();
	var password = $('#password').val();

	$('#usererror').html("");
	$('#passworderror').html("");

	if (username == "") {
		value = "  *Username should not be empty";
		key = "usererror";
	} else if (password == "") {
		value = "  *Please Enter Valid Password";
		key = "passworderror";
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
