/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 Core JavaScript functionality for the application.  Performs the required
 Restful calls, validates return values, and populates the member table.
 */

/* Builds the updated table for the member list */
function buildMemberRows(members) {
	return _.template($("#member-tmpl").html(), {
		"members" : members
	});
}

/* Uses JAX-RS GET to retrieve current member list */
function updateMemberTable() {
	$.ajax({
		url : "rest/members",
		cache : false,
		success : function(data) {
			$('#members').empty().append(buildMemberRows(data));
		},
		error : function(error) {
			// console.log("error updating table -" + error.status);
		}
	});
}

/*
 * Attempts to register a new member using a JAX-RS POST. The callbacks the
 * refresh the member table, or process JAX-RS response codes to update the
 * validation errors.
 */
function registerMember(memberData) {
	// clear existing msgs
	$('span.invalid').remove();
	$('span.success').remove();

	$
			.ajax({
				url : 'rest/members',
				contentType : "application/json",
				dataType : "json",
				type : "POST",
				data : JSON.stringify(memberData),
				success : function(data) {
					// console.log("Member registered");

					// clear input fields
					$('#reg')[0].reset();

					// mark success on the registration form
					$('#formMsgs')
							.append(
									$('<span class="success">Member Registered</span>'));

					updateMemberTable();
				},
				error : function(error) {
					if ((error.status == 409) || (error.status == 400)) {
						// console.log("Validation error registering user!");

						var errorMsg = $.parseJSON(error.responseText);

						$.each(errorMsg, function(index, val) {
							$('<span class="invalid">' + val + '</span>')
									.insertAfter($('#' + index));
						});
					} else {
						// console.log("error - unknown server issue");
						$('#formMsgs')
								.append(
										$('<span class="invalid">Unknown server error</span>'));
					}
				}
			});
}

function loadPage(page) {
	$('#page_loader').load(page);
}

function listProducts() {
	$.ajax({
		url : "rest/products",
		cache : false,
		success : function(data) {
			$('#products_content').empty().append(buildProductRows(data));
		},
		error : function(error) {
			addMessage("error", "error updating table -" + error.status);
		}
	});
}

function buildProductRows(products) {
	var productsBody = "<tr>";

	for ( var i = 0; i < products.length; i++) {
		productsBody += "<td>&nbsp;</td>";
		productsBody += "<td>" + products[i].name + "</td>";
		productsBody += "<td>" + products[i].description + "</td>";
		productsBody += "<td>" + products[i].id + "</td>";
		productsBody += '<td><input type="image" src="images/icn_edit.png" title="Edit">'
				+ '<input type="image"	src="images/icn_trash.png" title="Trash"></td>';
	}

	productsBody += "</tr>";
	return productsBody;
}

/**
 * @param type :
 *            warning | error | success | info
 * @param text
 *            to display
 */
function addMessage(type, text) {
	$(".alert_" + type).html(
			text + ' - <a href="#" onclick="closeMessages()">close</a>');
	$(".alert_" + type).fadeIn();
}

