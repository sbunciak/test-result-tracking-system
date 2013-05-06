define([ "app/models/Product" ], function(Product) {

	var Products = Backbone.Collection.extend({
		model : Product,
		id : 'id'
	});

	return Products;

});