define([ "lib/text!../../../templates/product_build_list.html" ], function(
		productsTemplate) {

	var ProductBuildListView = Backbone.View.extend({
		events : {
			"click input[data-tm-role='delete']" : "deleteBuild"
		},

		initialize : function() {
			this.model.bind("all", this.render, this);
		},

		render : function() {
			// Compile the template using underscore
			var template = _.template(productsTemplate, {
				builds : this.model.models,
				// temporary solution
				// product_id : $("#nav_product_id").val()
			});

			// Load the compiled HTML into the Backbone "el"
			this.$el.html(template);
		},

		deleteBuild : function(event) {
			var id = $(event.currentTarget).data("tm-id");
			this.model.get(id).destroy({
				success : function() {
					addMessage("success", "Product build successfully removed.");
				}
			});
		}
	});

	return ProductBuildListView;
});