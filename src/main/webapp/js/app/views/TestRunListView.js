define([ "lib/text!../../../templates/test_run_list_html", "../../navigation" ], 
		function(testTemplate, navigation) {

	var TestRunListView = Backbone.View.extend({
		events : {
			"click input[data-tm-role='delete']" : "deleteRun"
		},

		initialize : function() {
			this.model.bind("all", this.render, this);
		},

		render : function() {
			// Compile the template using underscore
			var template = _.template(testTemplate, {
				runs : this.model.models
			});

			// Load the compiled HTML into the Backbone "el"
			this.$el.html(template);
			
			navigation.highlight(['product','version','build','plan']);
		},

		deleteRun : function(event) {
			var id = $(event.currentTarget).data("tm-id");
			this.model.get(id).destroy({
				success : function() {
					addMessage("success", "Test run successfully removed.");
					navigation.buildTestRunOptions();
				}
			});
		}
	});

	return TestRunListView;
});
