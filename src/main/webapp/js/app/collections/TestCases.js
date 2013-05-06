define([ "app/models/TestCase" ], function(TestCase) {

	var TestCases = Backbone.Collection.extend({
		model : TestCase,
		id : 'id'
	});

	return TestCases;
});