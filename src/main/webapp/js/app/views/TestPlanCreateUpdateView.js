define([ "lib/text!../../../templates/test_plan_create_update_html", "../../navigation" ], function(
		testTemplate, navigation) {

	var TestPlanCreateUpdateView = Backbone.View.extend({
		events : {
			"click input[data-tm-role='create']" : "savePlan"
		},

		initialize : function() {
			this.model.bind("all", this.render, this);
		},

		render : function() {
			// Compile the template using underscore
			var template = _.template(testTemplate, {
				plan : this.model
			});

			// Load the compiled HTML into the Backbone "el"
			this.$el.html(template);
		},

		savePlan : function(event) {
			// get attributes from the form
			var planAttributes = {
				name : $('#name').val(),
				type : $('#type').val(),
				rules : $('#rules').val(),
				description : $('#description').val()
			};

			if (this.model.isNew()) {
				// creating a new product
				this.model.set(planAttributes);
				this.model.save(null, {
					success : function(product) {
						addMessage("success", "Test plan successfully created.");
						navigation.buildTestPlanOptions();
					}
				});
			} else {
				// updating existing product
				this.model.save(planAttributes, {
					success : function(product) {
						addMessage("success", "Test plan successfully saved.");
						navigation.buildTestPlanOptions();
					}
				});
			}

		}
	});

	return TestPlanCreateUpdateView;
});
