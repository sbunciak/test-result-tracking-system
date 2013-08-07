define([ "lib/text!../../../templates/axis_config_list_html", "../../navigation" ], 
		function(axisTemplate, navigation) {

	var AxisConfigListView = Backbone.View.extend({
		events : {
			"click input[data-tm-role='delete']" : "deleteAxisConfig"
		},

		initialize : function() {
			this.model.bind("all", this.render, this);
		},

		render : function() {
			// Compile the template using underscore
			var template = _.template(axisTemplate, {
				configs : this.model.models
			});

			// Load the compiled HTML into the Backbone "el"
			this.$el.html(template);
			
			navigation.highlight(['product','version','axis']);
		},

		deleteAxisConfig : function(event) {
			var id = $(event.currentTarget).data("tm-id");
			this.model.get(id).destroy({
				success : function() {
					addMessage("success", "Axis configuration successfully removed.");
				}
			});
		}
	});

	return AxisConfigListView;
});
