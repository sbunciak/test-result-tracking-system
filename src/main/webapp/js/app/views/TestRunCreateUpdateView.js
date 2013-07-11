define([ "lib/text!../../../templates/test_run_create_update_html" ], function(
		testTemplate) {

	var TestRunCreateUpdateView = Backbone.View.extend({
		events : {
			"click input[data-tm-role='create']" : "saveRun"
		},

		initialize : function() {
			this.model.bind("all", this.render, this);
		},

		render : function() {
			// Compile the template using underscore
			var template = _.template(testTemplate, {
				run : this.model
			});

			// Load the compiled HTML into the Backbone "el"
			this.$el.html(template);
		},

		saveRun : function(event) {
			// get attributes from the form
			var runAttributes = {
				name : $('#name').val(),
				type : $('#type').val(),
				rules : $('#rules').val(),
				description : $('#description').val()
			};

			if (this.model.isNew()) {
				// creating a new product
				this.model.set(runAttributes);
				this.model.save(null, {
					success : function(product) {
						addMessage("success", "Test Run successfully created.");
					}
				})
			} else {
				// updating existing product
				this.model.save(runAttributes, {
					success : function(product) {
						addMessage("success", "Test Run successfully saved.");
					}
				})
			}

		}
	});

	return TestRunCreateUpdateView;
});
