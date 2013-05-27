require([ "app/models/Product",
          "app/models/ProductVersion",
          "app/models/ProductBuild",
          "app/models/Axis",
          "app/models/AxisValue",
          "app/models/AxisConfig",
          "app/models/TestPlan",
          "app/models/TestCase",
          "app/models/TestRun",
          "app/models/TestRunCase",
          "app/collections/Products",
          "app/collections/ProductVersions",
          "app/collections/ProductBuilds",
          "app/collections/Axiss",
          "app/collections/AxisValues",
          "app/collections/AxisConfigs",
          "app/collections/TestPlans",
          "app/collections/TestCases",
          "app/collections/TestRuns",
          "app/collections/TestRunCases",
          "app/views/ProductListView",
          "app/views/ProductCreateUpdateView",
          "app/views/ProductVersionListView",
          "app/views/ProductVersionCreateUpdateView",
          "app/views/ProductBuildListView",
          "app/views/ProductBuildCreateUpdateView",
          "app/views/AxisListView",
          "app/views/AxisCreateUpdateView",
          "app/views/AxisValueListView",
          "app/views/AxisValueCreateUpdateView",
          "app/views/AxisConfigListView",
          "app/views/AxisConfigCreateUpdateView",
          "app/views/TestPlanListView",
          "app/views/TestPlanCreateUpdateView",
          "app/views/TestCaseListView",
          "app/views/TestCaseCreateUpdateView",
          "app/views/TestRunListView",
          "app/views/TestRunCreateUpdateView",
          "app/views/TestRunCaseListView",
          "app/views/TestRunCaseUpdateView",
          "app/views/HomeView", ],

function(
		Product, ProductVersion, ProductBuild, Axis, AxisValue, AxisConfig,	TestPlan, TestCase, TestRun, TestRunCase, 
		Products, ProductVersions, ProductBuilds, Axiss, AxisValues, AxisConfigs, TestPlans, TestCases, TestRuns, TestRunCases, 
		ProductListView, ProductCreateUpdateView, 
		ProductVersionListView,	ProductVersionCreateUpdateView, 
		ProductBuildListView, ProductBuildCreateUpdateView, 
		AxisListView, AxisCreateUpdateView, 
		AxisValueListView, AxisValueCreateUpdateView, 
		AxisConfigListView,	AxisConfigCreateUpdateView, 
		TestPlanListView, TestPlanCreateUpdateView, 
		TestCaseListView, TestCaseCreateUpdateView, 
		TestRunListView, TestRunCreateUpdateView, 
		TestRunCaseListView, TestRunCaseUpdateView,
		HomeView) {

	var AppRouter = Backbone.Router.extend({
		routes : {
			// Application routing: 
			"" : "home", // index page
			// Products
			"products" : "listProducts", // list of products
			"products/new" : "createProduct", // create product
			"products/:id" : "editProduct", // edit product
			// Product version
			"product_versions" : "listProductVersions", // list of product versions
			"product_versions/new" : "createProductVersion", // create product version
			"product_versions/:id" : "editProductVersion", // edit product version
			// Product build
			"product_builds" : "listProductBuilds", // list of product build
			"product_builds/new" : "createProductBuild", // create product build
			"product_builds/:id" : "editProductBuild", // edit product build
			// Axis
			"axis" : "listAxis", // list of axis
			"axis/new" : "createAxis", // create axis
			"axis/:id" : "editAxis", // edit axis
			// Axis value
			"axis_values" : "listAxisValues", // list of axis values
			"axis_values/new" : "createAxisValue", // create axis value
			"axis_values/:id" : "editAxisValue", // edit axis value
			// Axis configuration
			"axis_configs" : "listAxisConfigs", // list of axis configurations
			"axis_configs/new" : "createAxisConfig", // create axis configuration
			"axis_configs/:id" : "editAxisConfig", // edit axis configuration
			// Test plan
			"test_plans" : "listTestPlans", // list of test plans
			"test_plans/new" : "createTestPlan", // create test plan
			"test_plans/:id" : "editTestPlan", // edit test plan
			// Test case
			"test_cases" : "listTestCases", // list of test cases
			"test_cases/new" : "createTestCase", // create test case
			"test_cases/:id" : "editTestCase", // edit test case
			// Test run
			"test_runs" : "listTestRuns", // list of test runs
			"test_runs/new" : "createTestRun", // create test run
			"test_runs/:id" : "editTestRun", // edit test run 
			// Test run case
			"test_run_cases" : "listTestRunCases", // list of test run cases
			"test_run_cases/:id" : "editTestRunCase", // edit test run case
		},

		// ====== TEST RUN CASE ====== //
		
		listTestRunCases : function() {
			var prd_id = $('#nav_product_id').val();
			var ver_id = $('#nav_version_id').val();
			var bld_id = $('#nav_build_id').val();
			var tpl_id = $('#nav_plan_id').val();
			var run_id = $('#nav_run_id').val();

			var testRunCases = new TestRunCases(); 
			testRunCases.url = "rest/products/" + prd_id + "/versions/" + ver_id + "/builds/" + bld_id + "/testplans/" + tpl_id + "/runs/" + run_id + "/cases";
			
			var view = new TestRunCaseListView({
				el : $("#page_loader"),
				model : testRunCases
			});
			
			testRunCases.fetch();
		},
		
		// ====== TEST RUN ====== //
		
		listTestRuns : function() {
			var prd_id = $('#nav_product_id').val();
			var ver_id = $('#nav_version_id').val();
			var bld_id = $('#nav_build_id').val();
			var tpl_id = $('#nav_plan_id').val();
			
			var testRuns = new TestRuns(); 
			testRuns.url = "rest/products/" + prd_id + "/versions/" + ver_id + "/builds/" + bld_id + "/testplans/" + tpl_id + "/runs";
			
			var view = new TestRunListView({
				el : $("#page_loader"),
				model : testRuns
			});
			
			testRuns.fetch();
		},
		
		// ====== TEST CASE ====== //
		
		listTestCases : function() {
			var prd_id = $('#nav_product_id').val();
			var ver_id = $('#nav_version_id').val();
			var tpl_id = $('#nav_plan_id').val();
			
			var testCases = new TestCases(); 
			testCases.url = "rest/products/" + prd_id + "/versions/" + ver_id + "/testplans/" + tpl_id + "/cases";
			
			var view = new TestCaseListView({
				el : $("#page_loader"),
				model : testCases
			});
			
			testCases.fetch();
		},
		
		// ====== TEST PLAN ====== //
		
		listTestPlans : function() {
			var prd_id = $('#nav_product_id').val();
			var ver_id = $('#nav_version_id').val();
			
			var testPlans = new TestPlans(); 
			testPlans.url = "rest/products/" + prd_id + "/versions/" + ver_id + "/testplans";
			
			var view = new TestPlanListView({
				el : $("#page_loader"),
				model : testPlans
			});
			
			testPlans.fetch();
		},
		
		// ====== AXIS CONFIG ====== //

		listAxisConfigs : function() {
			var aid = $("#nav_axis_id").val();
			var ver_id = $('#nav_version_id').val();
			
			var axisConfigs = new AxisConfigs(); 
			axisConfigs.url = 'rest/axis/' + aid + '/version/' + ver_id + '/configurations';
			
			var view = new AxisConfigListView({
				el : $("#page_loader"),
				model : axisConfigs
			});
			
			axisConfigs.fetch();
		},
		
		// ====== AXIS VALUE ====== //
		
		listAxisValues : function() {
			var aid = $("#nav_axis_id").val();
			
			var axisValues = new AxisValues(); 
			axisValues.url = 'rest/axis/' + aid + '/values';
			
			var view = new AxisValueListView({
				el : $("#page_loader"),
				model : axisValues
			});
			
			axisValues.fetch();
		},
		
		// ====== AXIS ====== //
		
		listAxis : function() {
			var axiss = new Axiss(); 
			
			var view = new AxisListView({
				el : $("#page_loader"),
				model : axiss
			});
			
			axiss.fetch();
		},
		
		// ====== PRODUCT BUILD ====== //
		
		listProductBuilds : function() {
			var pid = $("#nav_product_id").val();
			var vid = $("#nav_version_id").val();
			var productBuilds = new ProductBuilds();
			productBuilds.url = 'rest/products/' + pid + '/versions/' + vid + '/builds';
			
			var view = new ProductBuildListView({
				el : $("#page_loader"),
				model : productBuilds
			});
			
			productBuilds.fetch();
		},
		
		// ====== PRODUCT VERSION ====== //
		
		editProductVersion : function(version_id) {
			var pid = $("#nav_product_id").val();
			var productVersion = new ProductVersion({id : version_id});
			productVersion.urlRoot = "rest/products/"+pid+"/versions/";
			
			var view = new ProductVersionCreateUpdateView({
				el : $("#page_loader"),
				model : productVersion
			});
			
			productVersion.fetch();
		},
		
		createProductVersion : function() {
			var pid = $("#nav_product_id").val();
			var productVersion = new ProductVersion();
			productVersion.urlRoot = "rest/products/"+pid+"/versions/";
			
			var view = new ProductVersionCreateUpdateView({
				el : $("#page_loader"),
				model : productVersion
			});
			
			view.render();
		},
		
		listProductVersions : function() {
			var pid = $("#nav_product_id").val();
			var productVersions = new ProductVersions();
			productVersions.url = 'rest/products/' + pid + '/versions';
			
			var view = new ProductVersionListView({
				el : $("#page_loader"),
				model : productVersions
			});
			
			productVersions.fetch();
		},
		// ====== PRODUCT ====== //		
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
		// ====== HOME ====== //
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