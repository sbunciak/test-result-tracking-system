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
			addMessage("error", "error creating new Product version -"
					+ error.status);
		}
	});
}

function addProductBuild(productBuildData) {
	var prd_id = $('#product_id').val();
	var ver_id = $('#product_version_id').val();

	$.ajax({
		url : "rest/products/" + prd_id + "/versions/" + ver_id + "/builds",
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
			addMessage("error", "error creating new Product build -"
					+ error.status);
		}
	});
}

function addTestPlan(testPlanData) {
	var prd_id = $('#product_id').val();
	var ver_id = $('#product_version_id').val();

	$
			.ajax({
				url : "rest/products/" + prd_id + "/versions/" + ver_id
						+ "/testplans",
				contentType : "application/json",
				dataType : "json",
				type : "PUT",
				data : JSON.stringify(testPlanData),
				success : function(data) {
					// clear input fields
					$('form')[0].reset();

					// mark success on the registration form
					addMessage("success", "New Test plan sucessfully created.");

					listTestPlans();
				},
				error : function(error) {
					addMessage("error", "error creating new Test plan -"
							+ error.status);
				}
			});
}

function addTestCase(testCaseData) {
	var prd_id = $('#product_id').val();
	var ver_id = $('#product_version_id').val();
	var tpl_id = $('#test_plan_id').val();

	$
			.ajax({
				url : "rest/products/" + prd_id + "/versions/" + ver_id
						+ "/testplans/" + tpl_id + "/cases",
				contentType : "application/json",
				dataType : "json",
				type : "PUT",
				data : JSON.stringify(testCaseData),
				success : function(data) {
					// clear input fields
					$('form')[0].reset();

					// mark success on the registration form
					addMessage("success", "New Test case sucessfully created.");

					listTestCases();
				},
				error : function(error) {
					addMessage("error", "error creating new Test case -"
							+ error.status);
				}
			});
}

function addTestRun(testRunData) {
	var prd_id = $('#product_id').val();
	var ver_id = $('#product_version_id').val();
	var bld_id = $('#product_build_id').val();
	var tpl_id = $('#test_plan_id').val();

	$
			.ajax({
				url : "rest/products/" + prd_id + "/versions/" + ver_id
						+ "/builds/" + bld_id + "/testplans/" + tpl_id
						+ "/runs",
				contentType : "application/json",
				dataType : "json",
				type : "PUT",
				data : JSON.stringify(testRunData),
				success : function(data) {
					// clear input fields
					$('form')[0].reset();

					// mark success on the registration form
					addMessage("success", "New Test run sucessfully created.");

					listTestRuns();
				},
				error : function(error) {
					addMessage("error", "error creating new Test run -"
							+ error.status);
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
			addMessage("error", "error creating new Axis value -"
					+ error.status);
		}
	});
}

function addAxisValueConfiguration(configurationData) {
	var a_id = $('#axis_id').val();
	var pv_id = $('#product_version_id').val();

	var jsonData = "[";
	$.each(configurationData, function(index, element) {
		jsonData += '{' + '"productVersionId":' + pv_id + ','
				+ '"axisValueId":' + element.name + ',' + '"priority":"'
				+ element.value + '"}';

		jsonData += (index != configurationData.length - 1) ? ',' : ']';
	});

	$.ajax({
		url : "rest/axis/" + a_id + "/configurations",
		contentType : "application/json",
		dataType : "json",
		type : "PUT",
		data : jsonData,
		success : function(data) {
			// clear input fields
			$('form')[0].reset();

			// mark success on the registration form
			addMessage("success",
					"New Axis configurations sucessfully created.");

			listAxisValuesPriorities();
		},
		error : function(error) {
			addMessage("error", "error creating new Axis configurations -"
					+ error.status);
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
			$('#product_versions_content').empty().append(
					buildProductVersionRows(data));
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
		url : "rest/products/" + prd_id + "/versions/" + ver_id + "/builds",
		cache : false,
		success : function(data) {
			$('#product_builds_content').empty().append(
					buildProductBuildRows(data));
		},
		error : function(error) {
			addMessage("error", "error updating list -" + error.status);
		}
	});
}

function listTestPlans() {
	var prd_id = $('#product_id').val();
	var ver_id = $('#product_version_id').val();

	$.ajax({
		url : "rest/products/" + prd_id + "/versions/" + ver_id + "/testplans",
		cache : false,
		success : function(data) {
			$('#test_plans_content').empty().append(buildTestPlanRows(data));
		},
		error : function(error) {
			addMessage("error", "error updating list -" + error.status);
		}
	});
}

function listTestRuns() {
	var prd_id = $('#product_id').val();
	var ver_id = $('#product_version_id').val();
	var bld_id = $('#product_build_id').val();
	var tpl_id = $('#test_plan_id').val();

	$.ajax({
		url : "rest/products/" + prd_id + "/versions/" + ver_id + "/builds/"
				+ bld_id + "/testplans/" + tpl_id + "/runs",
		cache : false,
		success : function(data) {
			$('#test_runs_content').empty().append(buildTestRunRows(data));
		},
		error : function(error) {
			addMessage("error", "error updating list -" + error.status);
		}
	});
}

function listTestRunCases() {
	var prd_id = $('#product_id').val();
	var ver_id = $('#product_version_id').val();
	var bld_id = $('#product_build_id').val();
	var tpl_id = $('#test_plan_id').val();
	var run_id = $('#test_run_id').val();

	$.ajax({
		url : "rest/products/" + prd_id + "/versions/" + ver_id + "/builds/"
				+ bld_id + "/testplans/" + tpl_id + "/runs/" + run_id
				+ "/cases",
		cache : false,
		success : function(data) {
			$('#test_run_cases_content').empty().append(
					buildTestRunCaseRows(data));
		},
		error : function(error) {
			addMessage("error", "error fetching test run cases -"
					+ error.status);
		}
	});
}

function listTestCases() {
	var prd_id = $('#product_id').val();
	var ver_id = $('#product_version_id').val();
	var tpl_id = $('#test_plan_id').val();

	$.ajax({
		url : "rest/products/" + prd_id + "/versions/" + ver_id + "/testplans/"
				+ tpl_id + "/cases",
		cache : false,
		success : function(data) {
			$('#test_cases_content').empty().append(buildTestCaseRows(data));
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
			$('#axis_values_content').empty().append(buildAxisValueRows(data));
		},
		error : function(error) {
			addMessage("error", "error updating list -" + error.status);
		}
	});
}

function listAxisValuesPriorities() {
	var prd_id = $('#axis_id').val();

	$.ajax({
		url : "rest/axis/" + prd_id + "/values",
		cache : false,
		success : function(data) {
			$('#axis_conf_content').empty().append(
					buildAxisValuePrioritiesRows(data));
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
		body += '<option value="' + versions[i].id + '">'
				+ versions[i].productVersion + '</option>';
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

function buildProductBuildOptions(productBuilds) {
	var body = "";

	for ( var i = 0; i < productBuilds.length; i++) {
		body += '<option value="' + productBuilds[i].id + '">'
				+ productBuilds[i].label + '</option>';
	}

	return body;
}

function buildTestPlanRows(testPlans) {
	var body = "";

	for ( var i = 0; i < testPlans.length; i++) {
		body += "<tr>";
		body += "<td>" + testPlans[i].name + "</td>";
		body += "<td>" + testPlans[i].type + "</td>";
		body += "<td><pre>" + testPlans[i].rules + "</pre></td>";
		body += "<td>" + testPlans[i].description + "</td>";
		body += "<td>" + testPlans[i].id + "</td>";
		body += '<td><input type="image" src="images/icn_edit.png" title="Edit">'
				+ '<input type="image"	src="images/icn_trash.png" title="Trash"></td>';
		body += "</tr>";
	}

	return body;
}

function buildTestRunRows(testRuns) {
	var body = "";

	for ( var i = 0; i < testRuns.length; i++) {
		body += "<tr>";
		body += "<td>" + testRuns[i].name + "</td>";
		body += "<td>" + testRuns[i].id + "</td>";
		body += '<td><input type="image" src="images/icn_edit.png" title="Edit">'
				+ '<input type="image"	src="images/icn_trash.png" title="Trash"></td>';
		body += "</tr>";
	}

	return body;
}

function buildTestRunCaseRows(testRunCases) {
	var body = "";

	for ( var i = 0; i < testRunCases.length; i++) {
		body += "<tr>";
		body += "<td>" + testRunCases[i].title + "</td>";
		body += "<td>" + testRunCases[i].criterias + "</td>";
		body += "<td>" + testRunCases[i].assignee + "</td>";
		body += "<td>" + testRunCases[i].ciLink + "</td>";
		body += "<td>" + testRunCases[i].bugTrLink + "</td>";
		body += "<td>" + testRunCases[i].result + "</td>";
		body += "<td>" + testRunCases[i].id + "</td>";
		body += '<td><input type="image" src="images/icn_edit.png" title="Edit">'
				+ '<input type="image"	src="images/icn_trash.png" title="Trash"></td>';
		body += "</tr>";
	}

	return body;
}

function buildTestRunOptions(testRuns) {
	var body = "";

	for ( var i = 0; i < testRuns.length; i++) {
		body += '<option value="' + testRuns[i].id + '">' + testRuns[i].name
				+ '</option>';
	}

	return body;
}

function buildTestCaseRows(testCases) {
	var body = "";

	for ( var i = 0; i < testCases.length; i++) {
		body += "<tr>";
		body += "<td>" + testCases[i].title + "</td>";
		body += "<td>" + testCases[i].defaultTester + "</td>";
		body += "<td>" + testCases[i].ciLink + "</td>";
		body += "<td>" + testCases[i].id + "</td>";
		body += '<td><input type="image" src="images/icn_edit.png" title="Edit">'
				+ '<input type="image"	src="images/icn_trash.png" title="Trash"></td>';
		body += "</tr>";
	}

	return body;
}

function buildTestPlanOptions(testPlans) {
	var body = "";

	for ( var i = 0; i < testPlans.length; i++) {
		body += '<option value="' + testPlans[i].id + '">' + testPlans[i].name
				+ '</option>';
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

function buildAxisValuePrioritiesRows(axisValues) {
	var body = "";

	for ( var i = 0; i < axisValues.length; i++) {
		body += "<tr>";
		body += "<td>" + axisValues[i].value + "</td>";
		body += "<td>" + '<input type="radio" name="' + axisValues[i].id
				+ '" value="P1" />' + "</td>";
		body += "<td>" + '<input type="radio" name="' + axisValues[i].id
				+ '" value="P2" />' + "</td>";
		body += "<td>" + '<input type="radio" name="' + axisValues[i].id
				+ '" value="P3" />' + "</td>";
		body += "<td>" + '<input type="radio" name="' + axisValues[i].id
				+ '" value="DISABLED" checked="checked" />' + "</td>";
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
