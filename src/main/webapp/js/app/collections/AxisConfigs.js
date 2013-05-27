define([ "app/models/AxisConfig" ], function(AxisConfig) {

	var AxisConfigs = Backbone.Collection.extend({
		url : 'rest/axis',
		model : AxisConfig,
		id : 'id'
	});

	return AxisConfigs;
});