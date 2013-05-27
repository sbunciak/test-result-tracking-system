define([ "app/models/TestPlan" ], function(TestPlan) {

	var TestPlans = Backbone.Collection.extend({
		url : 'rest/testplans',
		model : TestPlan,
		id : 'id'
	});

	return TestPlans;
});