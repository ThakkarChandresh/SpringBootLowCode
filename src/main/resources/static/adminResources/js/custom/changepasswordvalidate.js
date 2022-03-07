var password = /^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9])(?=.*[a-z]).{8,}$/;
var key = "";
var value = "";

$("#updatebutton").prop('disabled', true);
$('#updatebutton').css('cursor', 'not-allowed');
$("#updatebutton").prop('title', 'Please fulfil all requirments');

function validatePassword(){
	var pswd = $('#newpassword').val();
	
	//validate the length
	if ( pswd.length < 8 ) {
		$('#length').removeClass('text-success').addClass('text-danger');
	} else {
		$('#length').removeClass('text-danger').addClass('text-success');
	}
	
	//validate letter
	if ( pswd.match(/[A-z]/) ) {
		$('#letter').removeClass('text-danger').addClass('text-success');
	} else {
		$('#letter').removeClass('text-success').addClass('text-danger');
		
	}

	//validate capital letter
	if ( pswd.match(/[A-Z]/) ) {
		$('#capital').removeClass('text-danger').addClass('text-success');
	} else {
		$('#capital').removeClass('text-success').addClass('text-danger');
	}

	//validate number
	if ( pswd.match(/\d/) ) {
		$('#number').removeClass('text-danger').addClass('text-success');
	} else {
		$('#number').removeClass('text-success').addClass('text-danger');
	}
	
	//validate special Character
	if ( pswd.match(/[!@#$&*]/)) {
		$('#space').removeClass('text-danger').addClass('text-success');
	} else {
		$('#space').removeClass('text-success').addClass('text-danger');
	}
	
	if(pswd.match(password)){
		$("#updatebutton").prop('disabled', false);
		$('#updatebutton').css('cursor', 'pointer');
		$("#updatebutton").prop('title', '');
	}else{
		$("#updatebutton").prop('disabled', true);
		$('#updatebutton').css('cursor', 'not-allowed');
		$("#updatebutton").prop('title', 'Please fulfil all requirements');
	}
}

$('#changePasswordForm').submit(function(){
	var oldpassword = $('#oldpassword').val();
	var newpassword = $('#newpassword').val();
	var confirmpassword = $('#confirmpassword').val();

	$('#oldpassworderror').html("");
	$('#newpassworderror').html("");
	$('#confirmpassworderror').html("");

	if (oldpassword == "") {
		key = "oldpassworderror";
		value = "Can't be empty";
	} else if (newpassword == "") {
		key = "newpassworderror";
		value = "Can't be empty";
	} else if (confirmpassword !== newpassword) {
		key = "confirmpassworderror";
		value = "Password is not matched";
	} else if (oldpassword == newpassword) {
		key = "newpassworderror"
		value = "New password can't be same as old password"
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
