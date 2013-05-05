define([ "app/models/Product",
		"lib/text!../../../templates/create_product.html" ], function(Product,
		productsTemplate) {

	var CreateProductView = Backbone.View.extend({
		events : {
			"click input[data-tm-role='create']" : "saveProduct"
		},

		initialize : function() {
			this.model.bind("all", this.render, this);
		},

		render : function() {
			// Compile the template using underscore
			var template = _.template(productsTemplate, {
				product : this.model
			});

			// Load the compiled HTML into the Backbone "el"
			this.$el.html(template);
		},

		saveProduct : function(event) {
			// TODO: create/update collision
			// get attributes from the form
			var productAttributes = {
					name : $('#name').val(),
					description : $('#description').val()
			}; 

			//this.model.set(productAttributes);
						
			this.model.save(productAttributes, {
				success : function(product) {
					addMessage("success", "Product successfully saved.");
					console.log(product);
					//	app.navigate("showProducts", {trigger: true});
				}
			})
		}
	});

	return CreateProductView;
});
