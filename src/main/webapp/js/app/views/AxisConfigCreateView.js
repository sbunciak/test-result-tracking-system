define([ "lib/text!../../../templates/axis_config_create_html",
		"app/collections/AxisConfigs", "app/collections/AxisValues", ], function(
		axisTemplate, AxisConfigs, AxisValues) {

	var AxisConfigCreateView = Backbone.View.extend({

		axisValues : new AxisValues(),

		events : {
			"click input[data-tm-role='create']" : "saveAxis"
		},

		initialize : function() {
			//this.model.bind("add", this.render, this);
			this.axisValues.bind("add", this.render, this);
			// TODO: cannot be 'static'
			this.axisValues.url = 'rest/axis/' + $("#nav_axis_id").val()
					+ '/values';
			this.axisValues.fetch();
			this.model.fetch({wait: true});
			
		},

		render : function() {
			// Compile the template using underscore
			var template = _.template(axisTemplate, {
				configs : this.model.models,
				values : this.axisValues.models
			});

			// Load the compiled HTML into the Backbone "el"
			this.$el.html(template);
		},

		saveAxis : function(event) {
			
			for ( var int = 0; int < $('input[name=id]').length; int++) {
				var axisValId = $('input[name=id]')[int].value;
				var axisVal = this.axisValues.get(axisValId);
				
				this.model.create({
					axisValue : axisVal,
					priority : $('input[name=' + axisValId + ']:checked').val()
				}, {
					success : function(axis) {
						addMessage("success",
								"Axis config successfully created.");
					}
				});
			}

		}
	});

	return AxisConfigCreateView;
});
