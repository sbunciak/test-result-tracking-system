define(['lib/backbone'], function() {

	var ProductBuild = Backbone.Model.extend({

		// will be overwritten
		urlRoot : '',
		
		initialize : function() {
			this.bind("error", function(model, error) {
				// We have received an error, log it, alert it or forget it :)
				addMessage("error", "Unexpected error has occured: " + error.statusText);
			});
		}
	});

	return ProductBuild;
});