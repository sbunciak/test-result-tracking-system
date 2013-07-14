/**
 * @param type :
 *            warning | error | success | info
 * @param text
 *            to display
 */
function addMessage(type, text) {
	$(".alert_" + type).html(text);
	$(".alert_" + type).fadeIn();
	$(".alert_" + type).delay(5000).fadeOut('slow');
}

function closeMessages() {
	$(".alert_info").hide(); // Hide info message
	$(".alert_warning").hide(); // Hide warning message
	$(".alert_error").hide(); // Hide error message
	$(".alert_success").hide(); // Hide success message
}

function addLoggedUserInfo() {
	$.ajax({
		url : "rest/user",
		success : function(data) {
			$('#nav_user_id').empty().append("Logged as: " + data);
		}
	});
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