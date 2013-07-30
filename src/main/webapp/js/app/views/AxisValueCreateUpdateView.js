define([ "lib/text!../../../templates/axis_value_create_update_html" ], function(
		axisTemplate) {

	var AxisValueCreateUpdateView = Backbone.View.extend({
		events : {
			"click input[data-tm-role='create']" : "saveAxis"
		},

		initialize : function() {
			this.model.bind("all", this.render, this);
		},

		render : function() {
			// Compile the template using underscore
			var template = _.template(axisTemplate, {
				axis : this.model
			});

			// Load the compiled HTML into the Backbone "el"
			this.$el.html(template);
		},

		saveAxis : function(event) {
			// get attributes from the form
			var axisAttributes = {
				value : $('#value').val()
			};

			if (this.model.isNew()) {
				// creating a new Axis
				this.model.set(axisAttributes);
				this.model.save(null, {
					success : function(axis) {
						addMessage("success", "Axis value successfully created.");
					}
				});
			} else {
				// updating existing Axis
				this.model.save(axisAttributes, {
					success : function(axis) {
						addMessage("success", "Axis value successfully saved.");
					}
				});
			}

		}
	});

	return AxisValueCreateUpdateView;
});
