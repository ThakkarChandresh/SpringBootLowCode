hotkeys('ctrl+d', function(event, handler) {
	event.preventDefault();
	window.location.href= "index";
});

hotkeys('ctrl+p', function(event, handler) {
	  event.preventDefault();
	  window.location.href= "projects";
});

hotkeys('ctrl+m', function(event, handler) {
	  event.preventDefault();
	  window.location.href= "modules";
});

hotkeys('ctrl+f', function(event, handler) {
	  event.preventDefault();
	  window.location.href= "forms";
});
	
hotkeys('ctrl+s', function(event, handler) {
	event.preventDefault();
	$("body").toggleClass("sidebar-icon-only");
});

hotkeys('ctrl+l', function(event, handler) {
	  event.preventDefault();
	  window.location.href= "/logout";
});

hotkeys('/', function(event, handler) {
	event.preventDefault();
	$("#txt_searchall").focus();
});
