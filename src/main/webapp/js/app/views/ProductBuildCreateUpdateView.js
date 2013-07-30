define([ "lib/text!../../../templates/product_build_create_update_html", "../../navigation" ], function(
		productsTemplate, navigation) {

	var ProductBuildCreateUpdateView = Backbone.View.extend({
		events : {
			"click input[data-tm-role='create']" : "saveBuild"
		},

		initialize : function() {
			this.model.bind("all", this.render, this);
		},

		render : function() {
			// Compile the template using underscore
			var template = _.template(productsTemplate, {
				build : this.model
			});

			// Load the compiled HTML into the Backbone "el"
			this.$el.html(template);
		},

		saveBuild : function(event) {
			// get attributes from the form
			var productAttributes = {
				label : $('#label').val(),
			};

			if (this.model.isNew()) {
				// creating a new product
				this.model.set(productAttributes);
				this.model.save(null, {
					success : function(product) {
						addMessage("success", "Product Build successfully created.");
						navigation.buildProductBuildOptions();
					}
				});
			} else {
				// updating existing product
				this.model.save(productAttributes, {
					success : function(product) {
						addMessage("success", "Product Build successfully saved.");
						navigation.buildProductBuildOptions();
					}
				});
			}

		}
	});

	return ProductBuildCreateUpdateView;
});
