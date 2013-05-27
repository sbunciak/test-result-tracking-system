define([ "lib/text!../../../templates/product_version_create_update.html" ], function(
		productsTemplate) {

	var ProductVersionCreateUpdateView = Backbone.View.extend({
		events : {
			"click input[data-tm-role='create']" : "saveVersion"
		},

		initialize : function() {
			this.model.bind("all", this.render, this);
		},

		render : function() {
			// Compile the template using underscore
			var template = _.template(productsTemplate, {
				version : this.model
			});

			// Load the compiled HTML into the Backbone "el"
			this.$el.html(template);
		},

		saveVersion : function(event) {
			// get attributes from the form
			var productAttributes = {
				name : $('#productVersion').val(),
				description : $('#description').val()
			};

			if (this.model.isNew()) {
				// creating a new product
				this.model.set(productAttributes);
				this.model.save(null, {
					success : function(product) {
						addMessage("success", "Product version successfully created.");
					}
				})
			} else {
				// updating existing product
				this.model.save(productAttributes, {
					success : function(product) {
						addMessage("success", "Product version successfully saved.");
					}
				})
			}

		}
	});

	return ProductVersionCreateUpdateView;
});
