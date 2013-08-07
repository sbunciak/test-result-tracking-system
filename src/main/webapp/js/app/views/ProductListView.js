define([ "lib/text!../../../templates/product_list_html", "../../navigation" ], function(
		productsTemplate, navigation) {

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
			
			navigation.highlight([]);
		},

		deleteProduct : function(event) {
			var id = $(event.currentTarget).data("tm-id");
			this.model.get(id).destroy({
				success : function() {
					addMessage("success", "Product successfully removed.");
					navigation.buildProductOptions();
				}
			});
		}
	});

	return ProductListView;
});
