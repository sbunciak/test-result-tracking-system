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

require.config({
    shim: {
        'lib/backbone': {
            //These script dependencies should be loaded before loading
            //backbone.js
            deps: ['lib/underscore', 'lib/jquery-1.9.1.min'],
            exports: 'Backbone'
        },
        'app/routes': {
            deps: ['util', 'lib/backbone']
        },
        'util': {
            deps: ['lib/backbone']
        }
    }
});

/*
 * Application main entry point. Load all the modules, calls entry point for navigation and security module.
 */
require([ "jquery", "lib/underscore", "lib/text", "lib/backbone", "lib/hideshow",
		"util", "navigation", "security", "app/routes" ], function($, _, text, Backbone,
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