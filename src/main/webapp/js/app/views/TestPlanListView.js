define([ "lib/text!../../../templates/test_plan_list_html", "../../navigation" ], function(
		testTemplate, navigation) {

	var TestPlanListView = Backbone.View.extend({
		events : {
			"click input[data-tm-role='delete']" : "deletePlan"
		},

		initialize : function() {
			this.model.bind("all", this.render, this);
		},

		render : function() {
			// Compile the template using underscore
			var template = _.template(testTemplate, {
				plans : this.model.models
			});

			// Load the compiled HTML into the Backbone "el"
			this.$el.html(template);
		},

		deletePlan : function(event) {
			var id = $(event.currentTarget).data("tm-id");
			this.model.get(id).destroy({
				success : function() {
					addMessage("success", "Test plan successfully removed.");
					navigation.refresh();
				}
			});
		}
	});

	return TestPlanListView;
});
