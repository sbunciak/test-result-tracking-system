define ([ "lib/text!../../../templates/main.html" ], function(
		mainTemplate) {

	var HomeView = Backbone.View.extend({
		initialize : function() {
			this.render();
		},
		render : function() {

			// Compile the template using underscore
			var template = _.template(mainTemplate, {} );

			// Load the compiled HTML into the Backbone "el"
			this.$el.html(template);
		}
	});

	return HomeView;

});