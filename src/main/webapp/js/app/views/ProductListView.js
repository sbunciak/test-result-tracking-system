define([ "lib/text!../../../templates/product_list.html" ], function(
		productsTemplate) {

	var ProductListView = Backbone.View.extend({
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
			this.model.get(id).destroy({
				success : function() {
					addMessage("success", "Product successfully removed.");
				}
			});
		}
	});

	return ProductListView;
});