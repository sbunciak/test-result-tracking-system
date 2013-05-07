define([], function() {

	var TestRun = Backbone.Model.extend({

		// will be overwritten
		urlRoot : '',
		
		initialize : function() {
			this.bind("error", function(model, error) {
				// We have received an error, log it, alert it or forget it :)
				addMessage("error", error);
			});
		}
	});

	return TestRun;
});