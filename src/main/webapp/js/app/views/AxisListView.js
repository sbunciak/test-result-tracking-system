define([ "lib/text!../../../templates/axis_list_html", "../../navigation" ], 
		function(axisTemplate, navigation) {

	var AxisListView = Backbone.View.extend({
		events : {
			"click input[data-tm-role='delete']" : "deleteAxis"
		},

		initialize : function() {
			this.model.bind("all", this.render, this);
		},

		render : function() {
			// Compile the template using underscore
			var template = _.template(axisTemplate, {
				axiss : this.model.models
			});

			// Load the compiled HTML into the Backbone "el"
			this.$el.html(template);
			
			navigation.highlight([]);
		},

		deleteAxis : function(event) {
			var id = $(event.currentTarget).data("tm-id");
			this.model.get(id).destroy({
				success : function() {
					addMessage("success", "Axis successfully removed.");
					navigation.buildAxisOptions();
				}
			});
		}
	});

	return AxisListView;
});
