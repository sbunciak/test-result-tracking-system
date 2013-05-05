define([], function() {

	var Product = Backbone.Model.extend({

		urlRoot : 'rest/products',

		// If you return a string from the validate function,
		// Backbone will throw an error
		/*
		validate : function(attributes) {
			if (attributes.name != "") {
				return "Name can not be empty!";
			}
		}, */
		
		initialize : function() {
			this.bind("error", function(model, error) {
				// We have received an error, log it, alert it or forget it :)
				addMessage("error", error);
			});
		}
	});

	return Product;
	
});