require([ "jquery", "lib/text", "lib/underscore", "lib/backbone",
		"lib/hideshow", "header", "app/routes" ], function($) {

	$(function() {
		// Close all message 'windows' on startup
		closeMessages();
	});
});