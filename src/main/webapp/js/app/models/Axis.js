define(['lib/backbone'], function() {

	var Axis = Backbone.Model.extend({

		urlRoot : 'rest/axis',
		
		initialize : function() {
			this.bind("error", function(model, error) {
				// We have received an error, log it, alert it or forget it :)
				addMessage("error", error);
			});
		}
	});

	return Axis;
});