define([ "lib/text!../../../templates/test_run_case_list_html", "../../navigation" ], 
		function(testTemplate, navigation) {

	var TestRunCaseListView = Backbone.View.extend({
		events : {
			"click input[data-tm-role='delete']" : "deleteCase",
			"click input[data-tm-role='status']" : "changeStatus"
		},

		initialize : function() {
			this.model.bind("all", this.render, this);
		},

		render : function() {
			// Compile the template using underscore
			var template = _.template(testTemplate, {
				cases : this.model.models
			});

			// Load the compiled HTML into the Backbone "el"
			this.$el.html(template);
			
			navigation.highlight(['product','version','build','plan','run']);
		},

		deleteCase : function(event) {
			var id = $(event.currentTarget).data("tm-id");
			this.model.get(id).destroy({
				success : function() {
					addMessage("success", "Test run case successfully removed.");
				}
			});
		},
		
		changeStatus : function(event) {
			var id = $(event.currentTarget).data("tm-id");
			var status = $(event.currentTarget).data("tm-status");
			var attributes = {
					result : status 
			};
			
			// updating existing product
			this.model.get(id).save(attributes, {
				success : function() {
					addMessage("success", "Test Run Case result updated.");
					$('#'+id+'_status_img').attr('src', 'images/ico_'+status.toLowerCase()+'.png');
				}
			});
		}
		
		
	});

	return TestRunCaseListView;
});
