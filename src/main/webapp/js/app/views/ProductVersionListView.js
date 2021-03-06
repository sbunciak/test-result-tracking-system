define([ "lib/text!../../../templates/product_version_list_html", "../../navigation" ], function(
		productsTemplate, navigation) {

	var ProductVersionListView = Backbone.View.extend({
		events : {
			"click input[data-tm-role='delete']" : "deleteVersion"
		},

		initialize : function() {
			this.model.bind("all", this.render, this);
		},

		render : function() {
			// Compile the template using underscore
			var template = _.template(productsTemplate, {
				versions : this.model.models
			});

			// Load the compiled HTML into the Backbone "el"
			this.$el.html(template);
			
			navigation.highlight(['product']);
		},

		deleteVersion : function(event) {
			var id = $(event.currentTarget).data("tm-id");
			this.model.get(id).destroy({
				success : function() {
					addMessage("success", "Product version successfully removed.");
					navigation.buildProductVersionOptions();
				}
			});
		}
	});

	return ProductVersionListView;
});