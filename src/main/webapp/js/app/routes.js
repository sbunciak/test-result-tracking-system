require([ "app/models/Product" , 
          "app/collections/Products", 
          "app/views/ProductListView",
          "app/views/ProductCreateUpdateView",
          "app/views/HomeView", ],

function(Product, Products, ProductListView, ProductCreateUpdateView, HomeView) {

	var AppRouter = Backbone.Router.extend({
		routes : {
			// Application routing: 
			"" : "home", // index page
			// Products
			"products" : "listProducts", // list of products
			"products/new" : "createProduct", // create product
			"products/:id" : "editProduct", // edit product
			// Product version
			"products/:pid/versions" : "listProductVersions", // list of product versions
			"products/:pid/versions/new" : "createProductVersion", // create product version
			"products/:pid/versions/:id" : "editProductVersion", // edit product version
			// Product build
			"products/:pid/versions/:vid/builds" : "listProductVersions", // list of product build
			"products/:pid/versions/:vid/builds/new" : "createProductVersion", // create product build
			"products/:pid/versions/:vid/builds/:id" : "editProductVersion", // edit product build
			// Axis
			"axis" : "listAxis", // list of axis
			"axis/new" : "createAxis", // create axis
			"axis/:id" : "editAxis", // edit axis
			// Axis value
			"axis/:aid/values" : "listAxisValues", // list of axis values
			"axis/:aid/values/new" : "createAxisValue", // create axis value
			"axis/:aid/values/:id" : "editAxisValue", // edit axis value
			// Axis configuration
			"axis/:aid/configurations" : "listAxisConfigs", // list of axis configurations
			"axis/:aid/configurations/new" : "createAxisConfig", // create axis configuration
			"axis/:aid/configurations/:id" : "editAxisConfig", // edit axis configuration
			// Test plan
			"products/:pid/versions/:vid/testplans" : "listTestPlan", // list of test plans
			"products/:pid/versions/:vid/testplans/new" : "createTestPlan", // create test plan
			"products/:pid/versions/:vid/testplans/:id" : "editTestPlan", // edit test plan
			// Test case
			"products/:pid/versions/:vid/testplans/:tid/cases" : "listTestCases", // list of test cases
			"products/:pid/versions/:vid/testplans/:tid/cases/new" : "createTestCase", // create test case
			"products/:pid/versions/:vid/testplans/:tid/cases/:id" : "editTestCase", // edit test case
			// Test run
			"products/:pid/versions/:vid/testplans/:tid/runs" : "listTestRuns", // list of test runs
			"products/:pid/versions/:vid/testplans/:tid/runs/new" : "createTestRun", // create test run
			"products/:pid/versions/:vid/testplans/:tid/runs/:id" : "editTestRun", // edit test run 
			// Test run case
			"products/:pid/versions/:vid/testplans/:tid/runs/:rid/cases" : "listTestRunCases", // list of test run cases
			"products/:pid/versions/:vid/testplans/:tid/runs/:rid/cases/:id" : "editTestRunCase", // edit test run case
		},

		editProductVersion : function(product_id) {
			var product = new Product({id : product_id}); 
			
			var view = new ProductCreateUpdateView({
				el : $("#page_loader"),
				model : product
			});
			
			product.fetch();
		},
		
		createProductVersion : function() {
			var view = new ProductCreateUpdateView({
				el : $("#page_loader"),
				model : new ProductVersion()
			});
			
			view.render();
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