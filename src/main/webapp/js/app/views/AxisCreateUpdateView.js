define([ "lib/text!../../../templates/axis_create_update_html", "../../navigation"], function(
		axisTemplate, navigation) {

	var AxisCreateUpdateView = Backbone.View.extend({
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
			
			navigation.highlight([]);
		},

		saveAxis : function(event) {
			// get attributes from the form
			var axisAttributes = {
				category : $('#category').val(),
				description : $('#description').val()
			};

			if (this.model.isNew()) {
				// creating a new Axis
				this.model.set(axisAttributes);
				this.model.save(null, {
					success : function(axis) {
						addMessage("success", "Axis successfully created.");
						navigation.buildAxisOptions();
						
						// auto redirect
						setTimeout(function() {
							  window.location.href = "#/axis";
						}, 2000);
					}
				});
			} else {
				// updating existing Axis
				this.model.save(axisAttributes, {
					success : function(axis) {
						addMessage("success", "Axis successfully saved.");
						navigation.buildAxisOptions();
						
						// auto redirect
						setTimeout(function() {
							  window.location.href = "#/axis";
						}, 2000);
					}
				});
			}

		}
	});

	return AxisCreateUpdateView;
});
