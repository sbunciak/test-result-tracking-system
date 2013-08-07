define([ "lib/text!../../../templates/axis_config_update_html", "../../navigation" ], 
		function(axisTemplate, navigation) {

	var AxisConfigUpdateView = Backbone.View
			.extend({

				events : {
					"click input[data-tm-role='create']" : "saveAxis"
				},

				initialize : function() {
					this.model.bind("all", this.render, this);
				},

				render : function() {
					// Compile the template using underscore
					var template = _.template(axisTemplate, {
						c : this.model
					});

					// Load the compiled HTML into the Backbone "el"
					this.$el.html(template);
					
					navigation.highlight(['product','version','axis']);
				},

				saveAxis : function(event) {

					// updating Axis Config
					var axisAttributes = {
						priority : $('input[name=' + $('input[name=id]').val() + ']:checked').val()
					};

					this.model.save(axisAttributes, {
						success : function(axis) {
							addMessage("success",
									"Axis config successfully saved.");
							// auto redirect
							setTimeout(function() {
								window.location.href = "#/axis_configs";
							}, 2000);

						}
					});

				}
			});

	return AxisConfigUpdateView;
});
