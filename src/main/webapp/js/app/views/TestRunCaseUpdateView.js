define([ "lib/text!../../../templates/test_run_case_update_html","../../navigation" ], 
		function(testTemplate, navigation) {

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
			
			navigation.highlight(['product','version','build','plan','run']);
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
					addMessage("success", "Test Run Case successfully saved.");
					// auto redirect
					setTimeout(function() {
						  window.location.href = "#/test_run_cases";
					}, 2000);
				}
			});

		}
	});

	return TestRunCaseUpdateView;
});
