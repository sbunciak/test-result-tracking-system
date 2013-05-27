define([], function() {

	var ProductBuild = Backbone.Model.extend({

		// will be overwritten
		urlRoot : 'rest/',
		
		initialize : function() {
			this.bind("error", function(model, error) {
				// We have received an error, log it, alert it or forget it :)
				addMessage("error", error);
			});
		}
	});

	return ProductBuild;
});