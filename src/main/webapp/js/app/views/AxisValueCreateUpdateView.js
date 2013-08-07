define([ "lib/text!../../../templates/axis_value_create_update_html","../../navigation" ], 
		function(axisTemplate, navigation) {

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
			
			navigation.highlight(['axis']);
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
						// auto redirect
						setTimeout(function() {
							  window.location.href = "#/axis_values";
						}, 2000);
					}
				});
			} else {
				// updating existing Axis
				this.model.save(axisAttributes, {
					success : function(axis) {
						addMessage("success", "Axis value successfully saved.");
						// auto redirect
						setTimeout(function() {
							  window.location.href = "#/axis_values";
						}, 2000);
					}
				});
			}

		}
	});

	return AxisValueCreateUpdateView;
});
