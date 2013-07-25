/*
 * Security module - provides functionality to retrieve currently logged user and his permissions from REST. 
 */
define([ "jquery" ], function($) {

	var security = {
		
		addLoggedUserInfo : function() {
			$.ajax({
				url : "rest/user",
				success : function(data) {
					$('#nav_user_id').empty().append("Logged as: " + data);
				}
			});
		},	
			
		enableMenus : function() {
			$.ajax({
				url : "rest/user/role",
				type : "POST",
				data : JSON.stringify({role : "Tester"}),
				dataType : "text",
				contentType : "application/json",
				success : function(inRole) {
					if (inRole == "true") {
						$('#menu_builds').show();
						$('#menu_runcases').show();
						$('#menu_testrep').show();
					} 
				}
			});
			
			$.ajax({
				url : "rest/user/role",
				type : "POST",
				data : JSON.stringify({role : "Manager"}),
				dataType : "text",
				contentType : "application/json",
				success : function(inRole) {
					if (inRole == "true") {
						$('#menu_products').show();
						$('#menu_versions').show();
						$('#menu_builds').show();
						$('#menu_axis').show();
						$('#menu_values').show();
						$('#menu_configs').show();
						$('#menu_plans').show();
						$('#menu_cases').show();
						$('#menu_runs').show();
						$('#menu_runcases').show();
					} 
				}
			});
		},
	
		// main entry point
		load : function() {

			// Add logged user info
			this.addLoggedUserInfo();
			
			// Enable only appropriate menus
			this.enableMenus();
		}
	};

	return security;
});