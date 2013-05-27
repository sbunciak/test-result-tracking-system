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

function closeMessages() {
	$(".alert_info").hide(); // Hide info message
	$(".alert_warning").hide(); // Hide warning message
	$(".alert_error").hide(); // Hide error message
	$(".alert_success").hide(); // Hide success message
}

function buildTestRunOptions(testRuns) {
	var body = "";

	for ( var i = 0; i < testRuns.length; i++) {
		body += '<option value="' + testRuns[i].id + '">' + testRuns[i].name + '</option>';
	}

	return body;
}

function buildTestPlanOptions(testPlans) {
	var body = "";

	for ( var i = 0; i < testPlans.length; i++) {
		body += '<option value="' + testPlans[i].id + '">' + testPlans[i].name + '</option>';
	}

	return body;
}

function buildAxisOptions(axis) {
	var body = "";
	
	for ( var i = 0; i < axis.length; i++) {
		body += '<option value="' + axis[i].id + '">' + axis[i].category + '</option>';
	}

	return body;
}

function buildProductBuildOptions(productBuilds) {
	var body = "";

	for ( var i = 0; i < productBuilds.length; i++) {
		body += '<option value="' + productBuilds[i].id + '">' + productBuilds[i].label + '</option>';
	}

	return body;
}

function buildProductVersionOptions(versions) {
	var body = "";

	for ( var i = 0; i < versions.length; i++) {
		body += '<option value="' + versions[i].id + '">' + versions[i].productVersion + '</option>';
	}

	return body;
}

function buildProductOptions(products) {
	var body = "";

	for ( var i = 0; i < products.length; i++) {
		body += '<option value="' + products[i].id + '">' + products[i].name + '</option>';
	}

	return body;
}

function loadProductVersions() {
	var prd_id = $('#nav_product_id').val();

	$.ajax({
		url : "rest/products/" + prd_id + "/versions",
		success : function(data) {
			$('#nav_version_id').empty().append(buildProductVersionOptions(data));
		}
	});
}

function loadProductBuilds() {
	var prd_id = $('#nav_product_id').val();
	var ver_id = $('#nav_version_id').val();

	$.ajax({
		url : "rest/products/" + prd_id + "/versions/" + ver_id + "/builds",
		success : function(data) {
			$('#nav_build_id').empty().append(buildProductBuildOptions(data));
		}
	});
}

function loadProducts() {
	$.ajax({
		url : "rest/products",
		success : function(data) {
			$('#nav_product_id').empty().append(buildProductOptions(data));
		}
	});
}

function loadAxis() {
	$.ajax({
		url : "rest/axis",
		success : function(data) {
			$('#nav_axis_id').empty().append(buildAxisOptions(data));
		}
	});
}

function loadTestRuns() {
	var prd_id = $('#nav_product_id').val();
	var ver_id = $('#nav_version_id').val();
	var bld_id = $('#nav_build_id').val();
	var tpl_id = $('#nav_plan_id').val();

	$.ajax({
		url : "rest/products/" + prd_id + "/versions/" + ver_id + "/builds/"
				+ bld_id + "/testplans/" + tpl_id + "/runs",
		success : function(data) {
			$('#nav_run_id').empty().append(buildTestRunOptions(data));
		}
	});
}

function loadTestPlans() {
	var prd_id = $('#nav_product_id').val();
	var ver_id = $('#nav_version_id').val();

	$.ajax({
		url : "rest/products/" + prd_id + "/versions/" + ver_id + "/testplans",
		success : function(data) {
			$('#nav_plan_id').empty().append(buildTestPlanOptions(data));
		}
	});
}

// auxiliary function to serialize html forms to json objects
$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};