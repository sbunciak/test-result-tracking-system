define([ "lib/text!../../../templates/test_run_case_list_html" ], function(
		testTemplate) {

	var TestRunCaseListView = Backbone.View.extend({
		events : {
			"click input[data-tm-role='delete']" : "deleteCase"
		},

		initialize : function() {
			this.model.bind("all", this.render, this);
		},

		render : function() {
			// Compile the template using underscore
			var template = _.template(testTemplate, {
				cases : this.model.models
			});

			// Load the compiled HTML into the Backbone "el"
			this.$el.html(template);
		},

		deleteCase : function(event) {
			var id = $(event.currentTarget).data("tm-id");
			this.model.get(id).destroy({
				success : function() {
					addMessage("success", "Test run case successfully removed.");
				}
			});
		}
	});

	return TestRunCaseListView;
});
