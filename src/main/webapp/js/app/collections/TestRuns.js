define([ "app/models/TestRun" ], function(TestRun) {

	var TestRuns = Backbone.Collection.extend({
		model : TestRun,
		id : 'id'
	});

	return TestRuns;
});