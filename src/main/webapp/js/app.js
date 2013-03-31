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

/**
 * Attempts to register a new member using a JAX-RS POST. The callbacks the
 * refresh the member table, or process JAX-RS response codes to update the
 * validation errors.
 */
function addProduct(productData) {
	$.ajax({
		url : 'rest/products',
		contentType : "application/json",
		dataType : "json",
		type : "PUT",
		data : JSON.stringify(productData),
		success : function(data) {
			// clear input fields
			$('form')[0].reset();

			// mark success on the registration form
			addMessage("success", "New Product sucessfully created.");

			listProducts();
		},
		error : function(error) {
			addMessage("error", "error creating new Product -" + error.status);
		}
	});
}

function addProductVersion(productVersionData) {
	var prd_id = $('#product_id').val();

	$.ajax({
		url : "rest/products/" + prd_id + "/versions",
		contentType : "application/json",
		dataType : "json",
		type : "PUT",
		data : JSON.stringify(productVersionData),
		success : function(data) {
			// clear input fields
			$('form')[0].reset();

			// mark success on the registration form
			addMessage("success", "New Product version sucessfully created.");

			listProductVersions();
		},
		error : function(error) {
			addMessage("error", "error creating new Product version -" + error.status);
		}
	});
}

function addProductBuild(productBuildData) {
	var prd_id = $('#product_id').val();
	var ver_id = $('#product_version_id').val();
	
	$.ajax({
		url : "rest/products/" + prd_id + "/versions/"+ver_id+"/builds",
		contentType : "application/json",
		dataType : "json",
		type : "PUT",
		data : JSON.stringify(productBuildData),
		success : function(data) {
			// clear input fields
			$('form')[0].reset();

			// mark success on the registration form
			addMessage("success", "New Product build sucessfully created.");

			listProductBuilds();
		},
		error : function(error) {
			addMessage("error", "error creating new Product build -" + error.status);
		}
	});
}

function addAxis(axisData) {
	$.ajax({
		url : 'rest/axis',
		contentType : "application/json",
		dataType : "json",
		type : "PUT",
		data : JSON.stringify(axisData),
		success : function(data) {
			// clear input fields
			$('form')[0].reset();

			// mark success on the registration form
			addMessage("success", "New Axis sucessfully created.");

			listAxis();
		},
		error : function(error) {
			addMessage("error", "error creating new Axis -" + error.status);
		}
	});
}

function addAxisValue(axisValueData) {
	var prd_id = $('#axis_id').val();

	$.ajax({
		url : "rest/axis/" + prd_id + "/values",
		contentType : "application/json",
		dataType : "json",
		type : "PUT",
		data : JSON.stringify(axisValueData),
		success : function(data) {
			// clear input fields
			$('form')[0].reset();

			// mark success on the registration form
			addMessage("success", "New Axis value sucessfully created.");

			listAxisValues();
		},
		error : function(error) {
			addMessage("error", "error creating new Axis value -" + error.status);
		}
	});
}
/**
 * 
 * @param page
 */
function loadPage(page) {
	$('#page_loader').load(page);
}

/**
 * 
 */
function listProducts() {
	$.ajax({
		url : "rest/products",
		cache : false,
		success : function(data) {
			$('#products_content').empty().append(buildProductRows(data));
		},
		error : function(error) {
			addMessage("error", "error updating list -" + error.status);
		}
	});
}

function listProductVersions() {
	var prd_id = $('#product_id').val();

	$.ajax({
		url : "rest/products/" + prd_id + "/versions",
		cache : false,
		success : function(data) {
			$('#product_versions_content').empty()
					.append(buildProductVersionRows(data));
		},
		error : function(error) {
			addMessage("error", "error updating list -" + error.status);
		}
	});
}

function listProductBuilds() {
	var prd_id = $('#product_id').val();
	var ver_id = $('#product_version_id').val();
	
	$.ajax({
		url : "rest/products/" + prd_id + "/versions/"+ver_id+"/builds",
		cache : false,
		success : function(data) {
			$('#product_builds_content').empty()
					.append(buildProductBuildRows(data));
		},
		error : function(error) {
			addMessage("error", "error updating list -" + error.status);
		}
	});
}

function listAxis() {
	$.ajax({
		url : "rest/axis",
		cache : false,
		success : function(data) {
			$('#axis_content').empty().append(buildAxisRows(data));
		},
		error : function(error) {
			addMessage("error", "error updating list -" + error.status);
		}
	});
}

function listAxisValues() {
	var prd_id = $('#axis_id').val();

	$.ajax({
		url : "rest/axis/" + prd_id + "/values",
		cache : false,
		success : function(data) {
			$('#axis_values_content').empty()
					.append(buildAxisValueRows(data));
		},
		error : function(error) {
			addMessage("error", "error updating list -" + error.status);
		}
	});
}

/**
 * 
 * @param products
 * @returns {String}
 */
function buildProductRows(products) {
	var body = "";

	for ( var i = 0; i < products.length; i++) {
		body += "<tr>";
		body += "<td>" + products[i].name + "</td>";
		body += "<td>" + products[i].description + "</td>";
		body += "<td>" + products[i].id + "</td>";
		body += '<td><input type="image" src="images/icn_edit.png" title="Edit">'
				+ '<input type="image"	src="images/icn_trash.png" title="Trash"></td>';
		body += "</tr>";
	}

	return body;
}

function buildProductOptions(products) {
	var body = "";

	for ( var i = 0; i < products.length; i++) {
		body += '<option value="' + products[i].id + '">' + products[i].name
				+ '</option>';
	}

	return body;
}

function buildProductVersionOptions(versions) {
	var body = "";

	for ( var i = 0; i < versions.length; i++) {
		body += '<option value="' + versions[i].id + '">' + versions[i].productVersion
				+ '</option>';
	}

	return body;
}

function buildProductVersionRows(productVersions) {
	var body = "";

	for ( var i = 0; i < productVersions.length; i++) {
		body += "<tr>";
		body += "<td>" + productVersions[i].productVersion + "</td>";
		body += "<td>" + productVersions[i].description + "</td>";
		body += "<td>" + productVersions[i].id + "</td>";
		body += '<td><input type="image" src="images/icn_edit.png" title="Edit">'
				+ '<input type="image"	src="images/icn_trash.png" title="Trash"></td>';
		body += "</tr>";
	}

	return body;
}

function buildProductBuildRows(productBuilds) {
	var body = "";

	for ( var i = 0; i < productBuilds.length; i++) {
		body += "<tr>";
		body += "<td>" + productBuilds[i].label + "</td>";
		body += "<td>" + productBuilds[i].id + "</td>";
		body += '<td><input type="image" src="images/icn_edit.png" title="Edit">'
				+ '<input type="image"	src="images/icn_trash.png" title="Trash"></td>';
		body += "</tr>";
	}

	return body;
}

function buildAxisRows(axis) {
	var body = "";

	for ( var i = 0; i < axis.length; i++) {
		body += "<tr>";
		body += "<td>" + axis[i].category + "</td>";
		body += "<td>" + axis[i].description + "</td>";
		body += "<td>" + axis[i].id + "</td>";
		body += '<td><input type="image" src="images/icn_edit.png" title="Edit">'
				+ '<input type="image"	src="images/icn_trash.png" title="Trash"></td>';
		body += "</tr>";
	}

	return body;
}

function buildAxisOptions(axis) {
	var body = "";

	for ( var i = 0; i < axis.length; i++) {
		body += '<option value="' + axis[i].id + '">' + axis[i].category
				+ '</option>';
	}

	return body;
}

function buildAxisValueRows(axisValues) {
	var body = "";

	for ( var i = 0; i < axisValues.length; i++) {
		body += "<tr>";
		body += "<td>" + axisValues[i].value + "</td>";
		body += "<td>" + axisValues[i].id + "</td>";
		body += '<td><input type="image" src="images/icn_edit.png" title="Edit">'
				+ '<input type="image"	src="images/icn_trash.png" title="Trash"></td>';
		body += "</tr>";
	}

	return body;
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
	$(".alert_" + type).delay(5000).fadeOut('slow');
}
