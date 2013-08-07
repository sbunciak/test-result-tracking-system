define([ "lib/text!../../../templates/test_plan_create_update_html", "../../navigation", 
         "../collections/Axiss", "../models/Axis" ], 
         function( testTemplate, navigation, Axiss, Axis, navigation) {

	var TestPlanCreateUpdateView = Backbone.View.extend({
		events : {
			"click input[data-tm-role='create']" : "savePlan"
		},

		initialize : function() {
			this.model.bind("all", this.render, this);
		},

		render : function() {
			var axiss = new Axiss();
			axiss.fetch({async:false});
			
			// Compile the template using underscore
			var template = _.template(testTemplate, {
				plan : this.model,
				axiss : axiss.models
			});

			// Load the compiled HTML into the Backbone "el"
			this.$el.html(template);
			
			navigation.highlight(['product','version']);
		},

		savePlan : function(event) {
			var selAxisId = $('select[name="axiss"]').val();
			
			var selectedAxiss = new Array();
			for ( var int = 0; int < selAxisId.length; int++) {
				var a = new Axis({id:selAxisId[int]});
				a.fetch({async:false});
				selectedAxiss[int] = a.toJSON();
			}
						
			// get attributes from the form
			var planAttributes = {
				name : $('#name').val(),
				type : $('#type').val(),
				rules : $('#rules').val(),
				description : $('#description').val(),
				axiss : selectedAxiss 
			};

			if (this.model.isNew()) {
				// creating a new product
				this.model.set(planAttributes);
				this.model.save(null, {
					success : function(product) {
						addMessage("success", "Test plan successfully created.");
						navigation.buildTestPlanOptions();
						// auto redirect
						setTimeout(function() {
							  window.location.href = "#/test_plans";
						}, 2000);
					}
				});
			} else {
				// updating existing product
				this.model.save(planAttributes, {
					success : function(product) {
						addMessage("success", "Test plan successfully saved.");
						navigation.buildTestPlanOptions();
						// auto redirect
						setTimeout(function() {
							  window.location.href = "#/test_plans";
						}, 2000);
					}
				});
			}

		}
	});

	return TestPlanCreateUpdateView;
});
