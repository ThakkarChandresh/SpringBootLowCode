hotkeys('ctrl+d', function(event, handler) {
	event.preventDefault();
	window.location.href= "index";
});

hotkeys('ctrl+u', function(event, handler) {
	event.preventDefault();
	window.location.href= "users";
});

hotkeys('ctrl+l', function(event, handler) {
	  event.preventDefault();
	  window.location.href= "/logout";
});

hotkeys('ctrl+s', function(event, handler) {
	event.preventDefault();
	$("body").toggleClass("sidebar-icon-only");
});


hotkeys('/', function(event, handler) {
	event.preventDefault();
	$("#txt_searchall").focus();
});