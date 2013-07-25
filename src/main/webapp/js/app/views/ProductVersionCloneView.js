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
				version : this.model,
				cloning : true
			});

			// Load the compiled HTML into the Backbone "el"
			this.$el.html(template);
		},

		saveVersion : function(event) {
			var cloning_id = this.cloning_id;
			var model = this.model;
			
			var cloneOptions = $('input[name="clone"]:checked').serializeObject();
			if (cloneOptions.clone instanceof Array) {
				// do nothing
			} else {
				cloneOptions.clone = new Array(cloneOptions.clone); // json array hack
			}
			
			// get attributes from the form
			var productAttributes = {
				productVersion : $('#productVersion').val(),
				description : $('#description').val()
			};
			
			this.model.set(productAttributes);
			
			cloneOptions.clonedVersion = this.model.attributes;
			
			// ommit backbone save, leave cloning to REST
			$.ajax({
				url : model.urlRoot+cloning_id+'/clone',
				type : "POST",
				data : JSON.stringify(cloneOptions),
				contentType : "application/json",
				success : function() {
					addMessage("success", "Product version successfully cloned.");
					navigation.refresh();
				}
			});
		}
	});

	return ProductVersionCreateUpdateView;
});
