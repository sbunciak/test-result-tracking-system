define([ "app/models/TestRunCase" ], function(TestRunCase) {

	var TestRunCases = Backbone.Collection.extend({
		model : TestRunCase,
		id : 'id'
	});

	return TestRunCases;
});