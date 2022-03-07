var key = "";
var value = "";
var username;

function checkEmail(email) {
	$(".loader").show();

	var ajaxResponse;

	$.ajax({

		type : "GET",
		url : "checkEmail?email=" + email,
		async : false,
		success : function(response) {
			$(".loader").hide();
			ajaxResponse = response;
		},
	});

	return !ajaxResponse;
}
function showMessage(message,status){
	if(status === 'Incorrect OTP'){
			$.toast({
				heading : status,
				text :message,
				showHideTransition : 'fade',
				icon : 'warning',
				loaderBg : '#57c7d4',
				position : 'top-left'
			});
			 $('#username').val($('#email-id').val());	
			 $("#verify-otp").modal('show');
	}else if(status === 'OTP has expired'){
		$.toast({
			heading : status,
			text :message,
			showHideTransition : 'fade',
			icon : 'error',
			loaderBg : '#f2a654',
			position : 'top-left'
		});
	}
}

$("#forgot-password-form").submit(function(){
	return sendOTP();
});
$('#resend').click(function(){
	return sendOTP();
});

function sendOTP(){
	$("#verify-otp").modal('hide');
	username = $('#username').val();
	$('#usererror').html("");

	if (username == "") {
		value = "Username should not be empty";
		key = "usererror";
	}else if(checkEmail(username)){
		value = "Invalid username";
		key = "usererror";
	}else {
		key = "";
		value = "";
	}
	
	if (key && key !== "" && value && value !== "") {
		$('#' + key).html(value);
		return false;
	} else {
		$(".loader").show();
		$.ajax({
	        type: "POST",
	        url: "get-forgot-password-otp",
	        data: {
	        	email: username,
	        },
	        success: function(response) {
	        	 $(".loader").hide();
	    		 $("#verify-otp").modal('show');
	        	 var token = response;
	        	 $('#token-id').val(response);
	        	 $('#email-id').val(username);
	        }	
		});
		return false;
	}
}
$("#username, #OTP").on( "copy paste drop", function() {
    return false;
});

$('#checkOtpForm').submit(function(){
	var otp = $('#OTP').val();
	
	if (otp == "") {
		value = "OTP should not be empty";
		key = "otperror";
	}else{
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

$('#verify-otp').on('hidden.bs.modal', function() {
	$('#OTP').val('');
});
