define([ "app/models/AxisValue" ], function(AxisValue) {

	var AxisValues = Backbone.Collection.extend({
		model : AxisValue,
		id : 'id'
	});

	return AxisValues;
});