define([ "lib/text!../../../templates/axis_value_list.html" ], function(
		axisTemplate) {

	var AxisValueListView = Backbone.View.extend({
		events : {
			"click input[data-tm-role='delete']" : "deleteAxisValue"
		},

		initialize : function() {
			this.model.bind("all", this.render, this);
		},

		render : function() {
			// Compile the template using underscore
			var template = _.template(axisTemplate, {
				values : this.model.models
			});

			// Load the compiled HTML into the Backbone "el"
			this.$el.html(template);
		},

		deleteAxis : function(event) {
			var id = $(event.currentTarget).data("tm-id");
			this.model.get(id).destroy({
				success : function() {
					addMessage("success", "Axis Value successfully removed.");
				}
			});
		}
	});

	return AxisValueListView;
});
