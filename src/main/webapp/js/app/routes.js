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
          "app/views/AxisConfigCreateView",
          "app/views/AxisConfigUpdateView",
          "app/views/TestPlanListView",
          "app/views/TestPlanCreateUpdateView",
          "app/views/TestCaseListView",
          "app/views/TestCaseCreateUpdateView",
          "app/views/TestRunListView",
          "app/views/TestRunCreateUpdateView",
          "app/views/TestRunCaseListView",
          "app/views/TestRunCaseUpdateView",
          "app/views/HomeView" ],

function(
		Product, ProductVersion, ProductBuild, Axis, AxisValue, AxisConfig,	TestPlan, TestCase, TestRun, TestRunCase, 
		Products, ProductVersions, ProductBuilds, Axiss, AxisValues, AxisConfigs, TestPlans, TestCases, TestRuns, TestRunCases, 
		ProductListView, ProductCreateUpdateView, 
		ProductVersionListView,	ProductVersionCreateUpdateView, 
		ProductBuildListView, ProductBuildCreateUpdateView, 
		AxisListView, AxisCreateUpdateView, 
		AxisValueListView, AxisValueCreateUpdateView, 
		AxisConfigListView,	AxisConfigCreateView, AxisConfigUpdateView,
		TestPlanListView, TestPlanCreateUpdateView, 
		TestCaseListView, TestCaseCreateUpdateView, 
		TestRunListView, TestRunCreateUpdateView, 
		TestRunCaseListView, TestRunCaseUpdateView,
		HomeView, AdminView) {

	var AppRouter = Backbone.Router.extend({
		routes : {
			// Application routing: 
			"" : "home", // index page
			// Products
			"products" : "listProducts", // list of products
			"products/new" : "createProduct", // create product
			"products/:id" : "editProduct", // edit product
			"products/clone/:id" : "cloneProduct", // clone product
			// Product version
			"product_versions" : "listProductVersions", // list of product versions
			"product_versions/new" : "createProductVersion", // create product version
			"product_versions/:id" : "editProductVersion", // edit product version
			"product_versions/clone/:id" : "cloneProductVersion", // clone product version
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
			
			currentView.close();
			currentView = new TestRunCaseListView({
				el : $("#page_loader"),
				model : testRunCases
			});
			
			testRunCases.fetch();
		},
		
		editTestRunCase : function(case_id) {
			var prd_id = $('#nav_product_id').val();
			var ver_id = $('#nav_version_id').val();
			var bld_id = $('#nav_build_id').val();
			var tpl_id = $('#nav_plan_id').val();
			var run_id = $('#nav_run_id').val();

			var testRunCase = new TestRunCase({id:case_id}); 
			testRunCase.urlRoot = "rest/products/" + prd_id + "/versions/" + ver_id + "/builds/" + bld_id + "/testplans/" + tpl_id + "/runs/" + run_id + "/cases";
			
			currentView.close();
			currentView = new TestRunCaseUpdateView({
				el : $("#page_loader"),
				model : testRunCase
			});
			
			testRunCase.fetch();
		},
		
		// ====== TEST RUN ====== //
		
		createTestRun : function() {
			var prd_id = $('#nav_product_id').val();
			var ver_id = $('#nav_version_id').val();
			var bld_id = $('#nav_build_id').val();
			var tpl_id = $('#nav_plan_id').val();
			
			var testRun = new TestRun(); 
			testRun.urlRoot = "rest/products/" + prd_id + "/versions/" + ver_id + "/builds/" + bld_id + "/testplans/" + tpl_id + "/runs";
			
			currentView.close();
			currentView = new TestRunCreateUpdateView({
				el : $("#page_loader"),
				model : testRun
			});
			
			currentView.render();
		},
		
		editTestRun : function(run_id) {
			var prd_id = $('#nav_product_id').val();
			var ver_id = $('#nav_version_id').val();
			var bld_id = $('#nav_build_id').val();
			var tpl_id = $('#nav_plan_id').val();
			
			var testRun = new TestRun({id : run_id}); 
			testRun.urlRoot = "rest/products/" + prd_id + "/versions/" + ver_id + "/builds/" + bld_id + "/testplans/" + tpl_id + "/runs";
			
			currentView.close();
			currentView = new TestRunCreateUpdateView({
				el : $("#page_loader"),
				model : testRun
			});
			
			testRun.fetch();
		},
		
		listTestRuns : function() {
			var prd_id = $('#nav_product_id').val();
			var ver_id = $('#nav_version_id').val();
			var bld_id = $('#nav_build_id').val();
			var tpl_id = $('#nav_plan_id').val();
			
			var testRuns = new TestRuns(); 
			testRuns.url = "rest/products/" + prd_id + "/versions/" + ver_id + "/builds/" + bld_id + "/testplans/" + tpl_id + "/runs";
			
			currentView.close();
			currentView = new TestRunListView({
				el : $("#page_loader"),
				model : testRuns
			});
			
			testRuns.fetch();
		},
		
		// ====== TEST CASE ====== //
		
		editTestCase : function(case_id) {
			var prd_id = $('#nav_product_id').val();
			var ver_id = $('#nav_version_id').val();
			var tpl_id = $('#nav_plan_id').val();
			
			var testCase = new TestCase({id : case_id}); 
			testCase.urlRoot = "rest/products/" + prd_id + "/versions/" + ver_id + "/testplans/" + tpl_id + "/cases";
			
			currentView.close();
			currentView = new TestCaseCreateUpdateView({
				el : $("#page_loader"),
				model : testCase
			});
			
			testCase.fetch();
		},
		
		createTestCase : function() {
			var prd_id = $('#nav_product_id').val();
			var ver_id = $('#nav_version_id').val();
			var tpl_id = $('#nav_plan_id').val();
			
			var testCase = new TestCase(); 
			testCase.urlRoot = "rest/products/" + prd_id + "/versions/" + ver_id + "/testplans/" + tpl_id + "/cases";
			
			currentView.close();
			currentView = new TestCaseCreateUpdateView({
				el : $("#page_loader"),
				model : testCase
			});
			
			currentView.render();
		},
		
		listTestCases : function() {
			var prd_id = $('#nav_product_id').val();
			var ver_id = $('#nav_version_id').val();
			var tpl_id = $('#nav_plan_id').val();
			
			var testCases = new TestCases(); 
			testCases.url = "rest/products/" + prd_id + "/versions/" + ver_id + "/testplans/" + tpl_id + "/cases";
			
			currentView.close();
			currentView = new TestCaseListView({
				el : $("#page_loader"),
				model : testCases
			});
			
			testCases.fetch();
		},
		
		// ====== TEST PLAN ====== //
		
		editTestPlan : function(plan_id) {
			var prd_id = $('#nav_product_id').val();
			var ver_id = $('#nav_version_id').val();
			
			var testPlan = new TestPlan({id : plan_id}); 
			testPlan.urlRoot = "rest/products/" + prd_id + "/versions/" + ver_id + "/testplans";
			
			currentView.close();
			currentView = new TestPlanCreateUpdateView({
				el : $("#page_loader"),
				model : testPlan
			});
			
			testPlan.fetch();
		},
		
		createTestPlan : function() {
			var prd_id = $('#nav_product_id').val();
			var ver_id = $('#nav_version_id').val();
			
			var testPlan = new TestPlan(); 
			testPlan.urlRoot = "rest/products/" + prd_id + "/versions/" + ver_id + "/testplans";
			
			currentView.close();
			currentView = new TestPlanCreateUpdateView({
				el : $("#page_loader"),
				model : testPlan
			});
			
			currentView.render();
		},
		
		listTestPlans : function() {
			var prd_id = $('#nav_product_id').val();
			var ver_id = $('#nav_version_id').val();
			
			var testPlans = new TestPlans(); 
			testPlans.url = "rest/products/" + prd_id + "/versions/" + ver_id + "/testplans";
			
			currentView.close();
			currentView = new TestPlanListView({
				el : $("#page_loader"),
				model : testPlans
			});
			
			testPlans.fetch();
		},
		
		// ====== AXIS CONFIG ====== //

		createAxisConfig : function() {
			var aid = $("#nav_axis_id").val();
			var ver_id = $('#nav_version_id').val();
			
			var axisConfigs = new AxisConfigs(); 
			axisConfigs.url = 'rest/axis/' + aid + '/version/' + ver_id + '/configurations';
			
			currentView.close();
			currentView = new AxisConfigCreateView({
				el : $("#page_loader"),
				model : axisConfigs,
			});
			
			axisConfigs.fetch();
		},
		
		editAxisConfig : function(config_id) {
			var aid = $("#nav_axis_id").val();
			var ver_id = $('#nav_version_id').val();
			
			var axisConfig = new AxisConfig( {id: config_id} ); 
			axisConfig.urlRoot = 'rest/axis/' + aid + '/version/' + ver_id + '/configurations';
			
			currentView.close();
			currentView = new AxisConfigUpdateView({
				el : $("#page_loader"),
				model : axisConfig
			});
			
			axisConfig.fetch();
		},
		
		listAxisConfigs : function() {
			var aid = $("#nav_axis_id").val();
			var ver_id = $('#nav_version_id').val();
			
			var axisConfigs = new AxisConfigs(); 
			axisConfigs.url = 'rest/axis/' + aid + '/version/' + ver_id + '/configurations';
			
			currentView.close();
			currentView = new AxisConfigListView({
				el : $("#page_loader"),
				model : axisConfigs
			});
			
			axisConfigs.fetch();
		},
		
		// ====== AXIS VALUE ====== //
		
		editAxisValue : function(value_id) {
			var aid = $("#nav_axis_id").val();
			
			var axisValue = new AxisValue( {id: value_id} ); 
			axisValue.urlRoot = 'rest/axis/' + aid + '/values';
			
			currentView.close();
			currentView = new AxisValueCreateUpdateView({
				el : $("#page_loader"),
				model : axisValue
			});
			
			axisValue.fetch();
		},
		
		createAxisValue : function() {
			var aid = $("#nav_axis_id").val();
			
			var axisValue = new AxisValue(); 
			axisValue.urlRoot = 'rest/axis/' + aid + '/values';
			
			currentView.close();
			currentView = new AxisValueCreateUpdateView({
				el : $("#page_loader"),
				model : axisValue
			});
			
			currentView.render();
		},
		
		listAxisValues : function() {
			var aid = $("#nav_axis_id").val();
			
			var axisValues = new AxisValues(); 
			axisValues.url = 'rest/axis/' + aid + '/values';
			
			currentView.close();
			currentView = new AxisValueListView({
				el : $("#page_loader"),
				model : axisValues
			});
			
			axisValues.fetch();
		},
		
		// ====== AXIS ====== //
		
		editAxis : function(axis_id) {
			var axis = new Axis({id : axis_id}); 
			
			currentView.close();
			currentView = new AxisCreateUpdateView({
				el : $("#page_loader"),
				model : axis
			});
			
			axis.fetch();
		},
		
		createAxis : function() {
			var axis = new Axis(); 
			
			currentView.close();
			currentView = new AxisCreateUpdateView({
				el : $("#page_loader"),
				model : axis
			});
			
			currentView.render();
		},
		
		listAxis : function() {
			var axiss = new Axiss(); 
			
			currentView.close();
			currentView = new AxisListView({
				el : $("#page_loader"),
				model : axiss
			});
			
			axiss.fetch();
		},
		
		// ====== PRODUCT BUILD ====== //
		
		editProductBuild : function(build_id) {
			var pid = $("#nav_product_id").val();
			var vid = $("#nav_version_id").val();
			var productBuild = new ProductBuild({id : build_id});
			productBuild.urlRoot = 'rest/products/' + pid + '/versions/' + vid + '/builds';
			
			currentView.close();
			currentView = new ProductBuildCreateUpdateView({
				el : $("#page_loader"),
				model : productBuild
			});
			
			productBuild.fetch();
		},
		
		createProductBuild : function() {
			var pid = $("#nav_product_id").val();
			var vid = $("#nav_version_id").val();
			var productBuild = new ProductBuild();
			productBuild.urlRoot = 'rest/products/' + pid + '/versions/' + vid + '/builds';
			
			currentView.close();
			currentView = new ProductBuildCreateUpdateView({
				el : $("#page_loader"),
				model : productBuild
			});
			
			currentView.render();
		},
		
		listProductBuilds : function() {
			var pid = $("#nav_product_id").val();
			var vid = $("#nav_version_id").val();
			var productBuilds = new ProductBuilds();
			productBuilds.url = 'rest/products/' + pid + '/versions/' + vid + '/builds';
			
			currentView.close();
			currentView = new ProductBuildListView({
				el : $("#page_loader"),
				model : productBuilds
			});
			
			productBuilds.fetch();
		},
		
		// ====== PRODUCT VERSION ====== //
		cloneProductVersion : function(version_id) {
			var pid = $("#nav_product_id").val();
			var productVersion = new ProductVersion({id : version_id});
			
			productVersion.urlRoot = "rest/products/"+pid+"/versions/";
			productVersion.fetch({
				success: function() {
				    var clone = productVersion.clone();
				    clone.unset('id', {silent:true});
				    clone.urlRoot = "rest/products/"+pid+"/versions/";
				    delete clone.id;
				    
					currentView.close();
					currentView = new ProductVersionCreateUpdateView({
						el : $("#page_loader"),
						model : clone
					});
					
					currentView.render();
				}
			});
		},
		
		editProductVersion : function(version_id) {
			var pid = $("#nav_product_id").val();
			var productVersion = new ProductVersion({id : version_id});
			productVersion.urlRoot = "rest/products/"+pid+"/versions/";
			
			currentView.close();
			currentView = new ProductVersionCreateUpdateView({
				el : $("#page_loader"),
				model : productVersion
			});
			
			productVersion.fetch();
		},
		
		createProductVersion : function() {
			var pid = $("#nav_product_id").val();
			var productVersion = new ProductVersion();
			productVersion.urlRoot = "rest/products/"+pid+"/versions/";
			
			currentView.close();
			currentView = new ProductVersionCreateUpdateView({
				el : $("#page_loader"),
				model : productVersion
			});
			
			currentView.render();
		},
		
		listProductVersions : function() {
			var pid = $("#nav_product_id").val();
			var productVersions = new ProductVersions();
			productVersions.url = 'rest/products/' + pid + '/versions';
			
			currentView.close();
			currentView = new ProductVersionListView({
				el : $("#page_loader"),
				model : productVersions
			});
			
			productVersions.fetch();
		},
		// ====== PRODUCT ====== //		
		cloneProduct : function(product_id) {
			var product = new Product({id : product_id});
			product.fetch({
				success: function() {
				    var clone = product.clone();
				    clone.unset('id', {silent:true});
				    delete clone.id;
				    
					currentView.close();
					currentView = new ProductCreateUpdateView({
						el : $("#page_loader"),
						model : clone
					});
					
					currentView.render();
				}
			});
		},
		
		editProduct : function(product_id) {
			var product = new Product({id : product_id}); 
			
			currentView.close();
			currentView = new ProductCreateUpdateView({
				el : $("#page_loader"),
				model : product
			});
			
			product.fetch();
		},
		
		createProduct : function() {
			currentView.close();
			currentView = new ProductCreateUpdateView({
				el : $("#page_loader"),
				model : new Product()
			});
			
			currentView.render();
		},
		
		listProducts : function() {
			currentView.close();
			var products = new Products(); 
			
			currentView = new ProductListView({
				el : $("#page_loader"),
				model : products
			});
			
			products.fetch();
		},
		// ====== HOME ====== //
		home : function() {
			currentView.close();
			currentView = new HomeView({
				el : $("#page_loader")
			});
		}
	});
	
	// just not to be null
	var currentView = new HomeView();
	
	Backbone.View.prototype.close = function() {
		$(this.el).empty();
		this.undelegateEvents();
		
		if (this.onClose) {
			this.onClose();
		}
	};

	// Instantiate the router
	var app_router = new AppRouter;

	// Start Backbone history a necessary step for bookmarkable URL's
	Backbone.history.start();
	
	$('#nav_refresh').click(function() {
		currentView.render();
	});
});