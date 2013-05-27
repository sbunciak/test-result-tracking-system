define([ "app/models/TestRun" ], function(TestRun) {

	var TestRuns = Backbone.Collection.extend({
		url : 'rest/testplans',
		model : TestRun,
		id : 'id'
	});

	return TestRuns;
});