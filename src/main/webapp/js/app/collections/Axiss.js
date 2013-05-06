define([ "app/models/Axis" ], function(Axis) {

	var Axiss = Backbone.Collection.extend({
		url : 'rest/axis',
		model : Axis,
		id : 'id'
	});

	return Axiss;
});