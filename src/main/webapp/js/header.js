$(document).ready(function() {

	// When page loads...
	closeMessages();

	// On Click Event
	$("#sidebar .toggle a").click(function() {

		var page = $(this).attr("href"); // Find the href
		page = page.replace('#','');
		loadPage("templates/"+page+".html");
		
		return false;
	});

	
});

function closeMessages() {
	$(".alert_info").hide(); // Hide info message
	$(".alert_warning").hide(); // Hide warning message
	$(".alert_error").hide(); // Hide error message
	$(".alert_success").hide(); // Hide success message
}

// auxiliary function to serialize html forms to json objects
$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};