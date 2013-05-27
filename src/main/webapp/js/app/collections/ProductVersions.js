define([ "app/models/ProductVersion" ], function(ProductVersion) {

	var ProductVersions = Backbone.Collection.extend({
		url : '', // needs to be set dynamically 
		model : ProductVersion,
		id : 'id'
	});

	return ProductVersions;
});