/**
 * @param type :
 *            warning | error | success | info
 * @param text
 *            to display
 */
function addMessage(type, text) {
	$(".alert_" + type).html(text);
	$(".alert_" + type).fadeIn();
	$(".alert_" + type).delay(3000).fadeOut('slow');
}

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

// extend Backbone view with close() function
Backbone.View.prototype.close = function() {
	$(this.el).empty();
	this.undelegateEvents();

	if (this.onClose) {
		this.onClose();
	}
};

//extend Backbone model with cloneWithoutId() function
Backbone.Model.prototype.cloneWithoutId = function() {
	var clone = this.clone();
	clone.unset('id', {silent : true});
	delete clone.id;
	return clone;
};