define([ "app/models/ProductBuild" ], function(ProductBuild) {

	var ProductBuilds = Backbone.Collection.extend({
		model : ProductBuild,
		id : 'id'
	});

	return ProductBuilds;
});