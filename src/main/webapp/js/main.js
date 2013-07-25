/*
    JBoss, Home of Professional Open Source
    Copyright 2012, Red Hat, Inc., and individual contributors
    by the @authors tag. See the copyright.txt in the distribution for a
    full listing of individual contributors.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

// TODO: shim backbone (underscore), routes (util)
require([ "jquery", "lib/underscore", "lib/text", "lib/backbone", "lib/hideshow",
		"util", "navigation", "security", "app/routes" ], function($, _, text, backbone,
		hideshow, util, navigation, security, routes) {

	$(function() {
		// Close all message 'windows' on startup
		// closeMessages();

		// load navigations
		navigation.load();
		
		// display selected menus according to user's role
		security.load();
	});
});