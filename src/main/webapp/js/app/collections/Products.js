define([ "app/models/Product" ], function(Product) {

	var Products = Backbone.Collection.extend({
		url : 'rest/products',
		model : Product,
		id : 'id'
	});

	return Products;

});