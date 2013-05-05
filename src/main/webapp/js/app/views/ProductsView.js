define([ "app/collections/Products",
		"lib/text!../../../templates/products.html" ], function(Products,
		productsTemplate) {

	var ProductsView = Backbone.View.extend({
		events : {
			"click input[data-tm-role='delete']" : "deleteProduct"
		},

		initialize : function() {
			this.model.bind("all", this.render, this);
		},

		render : function() {
			// Compile the template using underscore
			var template = _.template(productsTemplate, {
				products : this.model.models
			});

			// Load the compiled HTML into the Backbone "el"
			this.$el.html(template);
		},

		deleteProduct : function(event) {
			var id = $(event.currentTarget).data("tm-id");
			this.model.models[id].destroy({
				success : function() {
					addMessage("success", "Product successfully removed.");
				}
			});
		}
	});

	return ProductsView;

});
