require([ "jquery", "lib/text", "lib/underscore", "lib/backbone",
		"lib/hideshow", "header", "app/routes" ], function($) {

	$(function() {
		// Close all message 'windows' on startup
		closeMessages();

		// load navigations
		loadProducts();

		loadAxis();

		$('#nav_product_id').change(function() {
			loadProductVersions();
		});

		$('#nav_version_id').change(function() {
			loadProductBuilds();
			loadTestPlans();
		});

		$('#nav_build_id').change(function() {
			loadTestRuns();
		});

		$('#nav_plan_id').change(function() {
			loadTestRuns();
		});
	});

});