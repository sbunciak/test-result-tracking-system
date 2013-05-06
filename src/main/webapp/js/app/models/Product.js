define([], function() {

	var Product = Backbone.Model.extend({

		urlRoot : 'rest/products',
		
		initialize : function() {
			this.bind("error", function(model, error) {
				// We have received an error, log it, alert it or forget it :)
				addMessage("error", error);
			});
		}
	});

	return Product;
});