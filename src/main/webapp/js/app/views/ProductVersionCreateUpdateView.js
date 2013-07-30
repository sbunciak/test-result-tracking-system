define([ "lib/text!../../../templates/product_version_create_update_html", "../../navigation", "../collections/TestPlans","../models/TestPlan" ], function(
		productsTemplate, navigation, TestPlans, TestPlan) {

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
				productVersion : $('#productVersion').val(),
				description : $('#description').val()
			};
			
			if (this.model.isNew()) {
				// creating a new product
				this.model.set(productAttributes);
				this.model.save(null, {
					success : function(productVersion) {
						addMessage("success", "Product version successfully created.");
						navigation.buildProductVersionOptions();
					}
				});
			} else {
				// updating existing product
				this.model.save(productAttributes, {
					success : function(productVersion) {
						addMessage("success", "Product version successfully saved.");
						navigation.buildProductVersionOptions();
					}
				});
			}

		}
	});

	return ProductVersionCreateUpdateView;
});
