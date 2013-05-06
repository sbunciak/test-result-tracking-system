define([ "app/models/ProductVersion" ], function(ProductVersion) {

	var ProductVersions = Backbone.Collection.extend({
		model : ProductVersion,
		id : 'id'
	});

	return ProductVersions;
});