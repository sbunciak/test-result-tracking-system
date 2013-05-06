require([ "app/models/Product" , 
          "app/collections/Products", 
          "app/views/ProductListView",
          "app/views/ProductCreateUpdateView",
          "app/views/HomeView", ],

function(Product, Products, ProductListView, ProductCreateUpdateView, HomeView) {

	var AppRouter = Backbone.Router.extend({
		routes : {
			"" : "home", // index page
			"products" : "listProducts", // list of products
			"products/new" : "createProduct", // create product
			"products/:id" : "editProduct", // create product
			"axis" : "listAxis", // list of axis
			"products/:id/versions" : "listProductVersions", // list of product versions
		},

		listProductVersions : function(pid) {
			var productVersions = new ProductVersions({
				urlRoot : "rest/products/"+pid+"versions/"
			}); 
			
			var view = new ProductVersionListView({
				el : $("#page_loader"),
				model : productVersions
			});
			
			productVersions.fetch();
		},
		
		editProduct : function(product_id) {
			var product = new Product({id : product_id}); 
			
			var view = new ProductCreateUpdateView({
				el : $("#page_loader"),
				model : product
			});
			
			product.fetch();
		},
		
		createProduct : function() {
			
			var view = new ProductCreateUpdateView({
				el : $("#page_loader"),
				model : new Product()
			});
			
			view.render();
		},
		
		listProducts : function() {
			var products = new Products(); 
			
			var view = new ProductListView({
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