define([ "app/models/TestPlan" ], function(TestPlan) {

	var TestPlans = Backbone.Collection.extend({
		model : TestPlan,
		id : 'id'
	});

	return TestPlans;
});