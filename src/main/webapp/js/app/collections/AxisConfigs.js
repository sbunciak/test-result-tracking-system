define([ "app/models/AxisConfig" ], function(AxisConfig) {

	var AxisConfigs = Backbone.Collection.extend({
		model : AxisConfig,
		id : 'id'
	});

	return AxisConfigs;
});