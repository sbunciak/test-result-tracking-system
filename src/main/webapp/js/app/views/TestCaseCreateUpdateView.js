define([ "lib/text!../../../templates/test_case_create_update_html" ], function(
		testTemplate) {

	var TestCaseCreateUpdateView = Backbone.View.extend({
		events : {
			"click input[data-tm-role='create']" : "saveCase"
		},

		initialize : function() {
			this.model.bind("all", this.render, this);
		},

		render : function() {
			// Compile the template using underscore
			var template = _.template(testTemplate, {
				c : this.model
			});

			// Load the compiled HTML into the Backbone "el"
			this.$el.html(template);
		},

		saveCase : function(event) {
			// get attributes from the form
			var caseAttributes = {
				title : $('#title').val(),
				defaultTester : $('#defaultTester').val(),
				ciLink : $('#ciLink').val()
			};

			if (this.model.isNew()) {
				// creating a new product
				this.model.set(caseAttributes);
				this.model.save(null, {
					success : function(product) {
						addMessage("success", "Test case successfully created.");
					}
				});
			} else {
				// updating existing product
				this.model.save(caseAttributes, {
					success : function(product) {
						addMessage("success", "Test case successfully saved.");
					}
				});
			}

		}
	});

	return TestCaseCreateUpdateView;
});
