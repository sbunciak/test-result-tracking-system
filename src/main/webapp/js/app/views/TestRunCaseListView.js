define([ "lib/text!../../../templates/test_run_case_list_html", "../../navigation" ], 
		function(testTemplate, navigation) {

	var TestRunCaseListView = Backbone.View.extend({
		events : {
			"click input[data-tm-role='delete']" : "deleteCase",
			"click input[data-tm-role='status']" : "changeStatus",
			"click input[data-tm-role='statusMulti']" : "changeStatusMulti"
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
		},
		
		changeStatusMulti : function(event) {
			var ids = $('input[name="multi_rcases"]');
			var status = $('#result').val();
			
			var attributes = {
				result : status 
			};
			
			for ( var int = 0; int < ids.length; int++) {
				var id = ids[int].value;	
				// updating existing product
				this.model.get(id).save(attributes, {
					success : function() {
						addMessage("success", "Test Run Case result updated.");
					}
				});
			}
						
		}
		
	});

	return TestRunCaseListView;
});
