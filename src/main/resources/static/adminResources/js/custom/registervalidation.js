var emailcheck = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
var passwordcheck = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
var key = "";
var value = "";

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

	return ajaxResponse;
}


$( "#username,#password,#confirmpassword,#firstName,#lastName " ).on( "paste drop", function() {
    return false;
});

$("#registerForm").submit(function(){
	
	var firstname = $('#firstName').val();
	var lastname = $('#lastName').val();
	var email = $('#username').val();
	var password = $('#password').val();
	var confirmpassword = $('#confirmpassword').val();

	$('#firsterror').html("");
	$('#lasterror').html("");
	$('#emailerror').html("");
	$('#passworderror').html("");
	$('#confirmpassworderror').html("");

	if (firstname == "") {
		value = " *Firstname should not be empty";
		key = "firsterror";
	}

	else if (lastname == "") {
		value = " *Lastname should not be empty";
		key = "lasterror";
	}

	else if (!(emailcheck.test(email))) {
		value = " *There must be @ and . character";
		key = "emailerror";
	}

	else if (!(passwordcheck.test(password))) {
		value = " *Keep strong password one capital is required";
		key = "passworderror";
	}

	else if (confirmpassword != password) {
		value = " *Password is not matched";
		key = "confirmpassworderror";
	} else if (checkEmail(email)) {
		value = " E-mail already exists";
		key = "emailerror";

	}

	else {
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

function verifyEmail(key, email, port) {

	if (key && key !== '' && email && email !== '') {
		$(".loader").show();
		$('#email-text').html("Please wait while we verify your email...");

		$.ajax({

			type : "GET",
			url : "verifyemail?key=" + key + "&email=" + email,
			async : false,

			success : function(response) {
				setTimeout(function() {
					$(".loader").hide();
					setTimeout(function() {

						$('#email-text').html("Redirecting to login page")
								.addClass('loading');
					}, 1000);
					if (response.status) {
						$('#email-text').html(response.message);
						$('#icon-id').html('<i class="fas fa-check-circle">');
					} else {
						$('#email-text').html(response.message);
						$('#icon-id').html(
								'<i class="fas fa-exclamation-triangle"></i>');
					}
				}, 3000)
				setTimeout(function() {
					window.location.href = "http://localhost:" + port;
				}, 7000)

			},
		});

	} else {
		$('#email-text').html('Something went wrong');
		$('#icon-id').html('<i class="fas fa-times-circle">');
	}
}