define([ "app/models/TestRunCase" ], function(TestRunCase) {

	var TestRunCases = Backbone.Collection.extend({
		url : 'rest/testplans',
		model : TestRunCase,
		id : 'id'
	});

	return TestRunCases;
});