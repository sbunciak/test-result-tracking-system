require([ "app/models/Product" , 
          "app/collections/Products", 
          "app/views/ProductsView",
          "app/views/CreateProductView",
          "app/views/HomeView", ],

function(Product, Products, ProductsView, CreateProductView, HomeView) {

	var AppRouter = Backbone.Router.extend({
		routes : {
			"" : "home", // index page
			"products" : "showProducts", // list of products
			"products/new" : "createProduct", // create product
			"products/:id" : "editProduct", // create product

		},

		editProduct : function(product_id) {
			var product =new Product({id : product_id}); 
			
			var view = new CreateProductView({
				el : $("#page_loader"),
				model : product
			});
			
			product.fetch();
		},
		
		createProduct : function() {
			
			var view = new CreateProductView({
				el : $("#page_loader"),
				model : new Product()
			});
			
			view.render();
		},
		
		showProducts : function() {
			var products = new Products(); 
			
			var view = new ProductsView({
				el : $("#page_loader"),
				model : products
			});
			
			products.fetch();
		},

		home : function() {
			var homeView = new HomeView({
				el : $("#page_loader")
			});
		}
	});

	// Instantiate the router
	var app_router = new AppRouter;

	// Start Backbone history a necessary step for bookmarkable URL's
	Backbone.history.start();
});