/*
 * Navigation module - provides back-end functionality and rendering for the navigation bar. 
 */
define([ "jquery", "app/collections/Products",
		"app/collections/ProductVersions", "app/collections/ProductBuilds",
		"app/collections/Axiss", "app/collections/TestPlans",
		"app/collections/TestRuns" ], function($, Products, ProductVersions,
		ProductBuilds, Axiss, TestPlans, TestRuns) {

	var navigation = {
		// objects
		products : new Products(),
		productVersions : new ProductVersions(),
		productBuilds : new ProductBuilds(),
		axiss : new Axiss(),
		testPlans : new TestPlans(),
		testRuns : new TestRuns(),

		// rendering
		buildTestRunOptions : function() {
			var body = "";
			var testRuns = this.testRuns.models;

			this.testRuns.url = "rest/products/" + $('#nav_product_id').val()
					+ "/versions/" + $('#nav_version_id').val() + "/builds/"
					+ $('#nav_build_id').val() + "/testplans/"
					+ $('#nav_plan_id').val() + "/runs";
			this.testRuns.fetch({
				success : function() {
					for ( var i = 0; i < testRuns.length; i++) {
						body += '<option value="' + testRuns[i].get('id')
								+ '">' + testRuns[i].get('name') + '</option>';
					}
					$('#nav_run_id').empty().append(body);
				}
			});
		},

		buildTestPlanOptions : function() {
			var body = "";
			var testPlans = this.testPlans.models;

			this.testPlans.url = "rest/products/" + $('#nav_product_id').val()
					+ "/versions/" + $('#nav_version_id').val() + "/testplans";
			this.testPlans
					.fetch({
						async : false,
						success : function() {
							for ( var i = 0; i < testPlans.length; i++) {
								body += '<option value="'
										+ testPlans[i].get('id') + '">'
										+ testPlans[i].get('name')
										+ '</option>';
							}
							$('#nav_plan_id').empty().append(body);
						}
					});

		},

		buildAxisOptions : function() {
			var body = "";
			var axis = this.axiss.models;

			this.axiss.fetch({
				success : function() {
					for ( var i = 0; i < axis.length; i++) {
						body += '<option value="' + axis[i].get('id') + '">'
								+ axis[i].get('category') + '</option>';
					}
					$('#nav_axis_id').empty().append(body);
				}
			});

		},

		buildProductBuildOptions : function() {
			var body = "";
			var productBuilds = this.productBuilds.models;

			this.productBuilds.url = 'rest/products/'
					+ $("#nav_product_id").val() + '/versions/'
					+ $("#nav_version_id").val() + '/builds';
			this.productBuilds.fetch({
				async : false,
				success : function() {
					for ( var i = 0; i < productBuilds.length; i++) {
						body += '<option value="' + productBuilds[i].get('id')
								+ '">' + productBuilds[i].get('label')
								+ '</option>';

					}
					$('#nav_build_id').empty().append(body);
				}
			});

		},

		buildProductVersionOptions : function() {
			var body = "";
			var versions = this.productVersions.models;

			this.productVersions.url = 'rest/products/'
					+ $("#nav_product_id").val() + '/versions';
			this.productVersions.fetch({
				async : false,
				success : function() {
					for ( var i = 0; i < versions.length; i++) {
						body += '<option value="' + versions[i].get('id')
								+ '">' + versions[i].get('productVersion')
								+ '</option>';

					}
					$('#nav_version_id').empty().append(body);
				}
			});

		},

		buildProductOptions : function() {
			var body = "";
			var products = this.products.models;

			this.products.fetch({
				async : false,
				success : function() {
					for ( var i = 0; i < products.length; i++) {
						body += '<option value="' + products[i].get('id')
								+ '">' + products[i].get('name') + '</option>';
					}
					$('#nav_product_id').empty().append(body);
				}
			});
		},

		// main entry point
		load : function() {
			this.products.bind("all", this.buildProductOptions(), this);
			this.productVersions.bind("all", this.buildProductVersionOptions(),
					this);
			this.productBuilds.bind("all", this.buildProductBuildOptions(),
					this);
			this.axiss.bind("all", this.buildAxisOptions(), this);
			this.testPlans.bind("all", this.buildTestPlanOptions(), this);
			this.testRuns.bind("all", this.buildTestRunOptions(), this);

			$('#nav_product_id').change(function() {
				navigation.buildProductVersionOptions();
			});

			$('#nav_version_id').change(function() {
				navigation.buildTestPlanOptions();
				navigation.buildProductBuildOptions();
			});

			$('#nav_build_id').change(function() {
				navigation.buildTestRunOptions();
			});

			$('#nav_plan_id').change(function() {
				navigation.buildTestRunOptions();
			});

			$('#nav_refresh').click(function() {
				navigation.refresh();
			});
		},
		// refresh current view
		refresh : function() {
			Backbone.history.fragment = null;
			Backbone.history.navigate(document.location.hash, true);
		}
	};

	return navigation;
});