define([ "lib/text!../../../templates/product_create_update_html", "../../navigation" ], function(
		productsTemplate, navigation) {

	var ProductCreateUpdateView = Backbone.View.extend({
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
			
			navigation.highlight([]);
		},

		saveProduct : function(event) {
			// get attributes from the form
			var productAttributes = {
				name : $('#name').val(),
				description : $('#description').val()
			};

			if (this.model.isNew()) {
				// creating a new product
				this.model.set(productAttributes);
				this.model.save(null, {
					success : function(product) {
						addMessage("success", "Product successfully created.");
						navigation.buildProductOptions();
						// auto redirect
						setTimeout(function() {
							  window.location.href = "#/products";
						}, 2000);
					}
				});
			} else {
				// updating existing product
				this.model.save(productAttributes, {
					success : function(product) {
						addMessage("success", "Product successfully saved.");
						navigation.buildProductOptions();
						// auto redirect
						setTimeout(function() {
							  window.location.href = "#/products";
						}, 2000);
					}
				});
			}

		}
	});

	return ProductCreateUpdateView;
});
