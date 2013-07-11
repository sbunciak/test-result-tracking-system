define([ "lib/text!../../../templates/test_run_case_update_html" ], function(
		testTemplate) {

	var TestRunCaseUpdateView = Backbone.View.extend({
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
				assignee : $('#assignee').val(),
				ciLink : $('#ciLink').val(),
				bugTrLink : $('#bugTrLink').val(),
				result : $('#result').val()
			};

			// updating existing product
			this.model.save(caseAttributes, {
				success : function(product) {
					addMessage("success", "Test Run successfully saved.");
				}
			})

		}
	});

	return TestRunCaseUpdateView;
});
