
$('#firstName,#lastName').keypress(function(evt) {
	var char = String.fromCharCode(evt.which);

	if (!(/[a-zA-Z]/.test(char))) {
		evt.preventDefault();
	}
});