define(['lib/backbone'], function() {

	var ProductVersion = Backbone.Model.extend({

		urlRoot : '', // needs to be set dynamically

		initialize : function() {
			this.bind("error", function(model, error) {
				// We have received an error, log it, alert it or forget it :)
				addMessage("error", "Unexpected error has occured: " + error.statusText);
			});
		},

	});

	return ProductVersion;
});