define(['lib/backbone'], function() {

	var TestRunCase = Backbone.Model.extend({

		// will be overwritten
		urlRoot : '',
		
		initialize : function() {
			this.bind("error", function(model, error) {
				// We have received an error, log it, alert it or forget it :)
				addMessage("error", "Unexpected error has occured: " + error.statusText);
			});
		},
		
		listCriterias : function() {
			var body = "";
			
			_.each(this.get('criterias'), function(c) {
				body += '[';
				body += c.axisValue.axis.category;
				body += ' ';
				body += c.axisValue.value;
				body += ' = ';
				body += c.priority;
				body += '] ';
			});
			
			return body;
		}
	});

	return TestRunCase;
});