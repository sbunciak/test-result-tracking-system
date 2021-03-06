define([ "lib/text!../../../templates/test_case_list_html", "../../navigation" ], 
		function(testTemplate, navigation) {

	var TestCaseListView = Backbone.View.extend({
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
			
			navigation.highlight(['product','version','plan']);
		},

		deleteCase : function(event) {
			var id = $(event.currentTarget).data("tm-id");
			this.model.get(id).destroy({
				success : function() {
					addMessage("success", "Test case successfully removed.");
				}
			});
		}
	});

	return TestCaseListView;
});
