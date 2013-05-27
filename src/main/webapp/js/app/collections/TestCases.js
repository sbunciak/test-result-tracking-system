define([ "app/models/TestCase" ], function(TestCase) {

	var TestCases = Backbone.Collection.extend({
		url : 'rest/testplans',
		model : TestCase,
		id : 'id'
	});

	return TestCases;
});