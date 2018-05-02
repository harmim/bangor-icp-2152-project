/**
 * Author: DVT HDH
 */


let dvtHdhMain;
if (typeof dvtHdhMain === "undefined") {
	dvtHdhMain = {};
}


/**
 * Form validation using jQuery validation plugin.
 */
dvtHdhMain.formValidation = {
	/**
	 * Default validation options.
	 */
	defaultOptions: {
		errorClass: "invalid-feedback",
		validClass: "valid-feedback",
		errorElement: "div",
		highlight: function (element) {
			for (let $parent = $(element).parent(); $parent.prop("nodeName") !== "form"; $parent = $parent.parent()) {
				if ($parent.hasClass("form-group")) {
					$parent.addClass("was-validated");
					break;
				}
			}
			$(element).addClass("invalid").removeClass("valid");
		},
		unhighlight: function (element) {
			$(element).removeClass("invalid").addClass("valid");
		},
		errorPlacement: function (error, element) {
			for (let $parent = $(element).parent(); $parent.prop("nodeName") !== "form"; $parent = $parent.parent()) {
				if ($parent.hasClass("form-group")) {
					$parent.append(error);
					break;
				}
			}
		}
	},


	/**
	 * Initialization.
	 */
	init: function () {
		$.validator.setDefaults(this.defaultOptions);
		this.default();
	},


	/**
	 * Default form validation all form with class needs-validation.
	 */
	default: function () {
		$("form.needs-validation").validate();
	}
};


/**
 * DataTables initialization.
 */
dvtHdhMain.dataTables = {
	/**
	 * Initialization.
	 */
	init: function () {
		if (typeof $().DataTable === "function") {
			$(".data-table").DataTable({
				"pageLength": 25
			});
		}
	}
};


/**
 * Executes when the DOM is fully loaded.
 */
$(function () {
	dvtHdhMain.formValidation.init();
	dvtHdhMain.dataTables.init();
});
