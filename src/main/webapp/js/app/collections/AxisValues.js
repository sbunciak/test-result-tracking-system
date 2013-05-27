define([ "app/models/AxisValue" ], function(AxisValue) {

	var AxisValues = Backbone.Collection.extend({
		url : 'rest/axis',
		model : AxisValue,
		id : 'id'
	});

	return AxisValues;
});