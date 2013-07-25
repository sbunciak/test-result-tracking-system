define([ "lib/text!../../../templates/test_report_html",
		"../collections/TestRuns", "../collections/TestRunCases", ], function(
		testTemplate, TestRuns, TestRunCases) {

	var TestReportView = Backbone.View.extend({

		render : function() {
			// Load test run cases with results first
			var prd_id = $('#nav_product_id').val();
			var ver_id = $('#nav_version_id').val();
			var bld_id = $('#nav_build_id').val();

			_.each(this.model.models, function(run) {
				var rCases = new TestRunCases();
				rCases.url = "rest/products/" + prd_id + "/versions/" + ver_id
						+ "/builds/" + bld_id + "/testplans/"
						+ run.attributes.testPlan.id + "/runs/" + run.get('id')
						+ "/cases";
				rCases.fetch({
					async : false
				});
				
				run.set('cases', rCases.models);
			});

			// Compile the template using underscore
			var template = _.template(testTemplate, {
				runs : this.model.models
			});

			// Load the compiled HTML into the Backbone "el"
			this.$el.html(template);
		}
	});

	return TestReportView;
});
