/**
 * Author: DVT HDH
 */


let dhMain;
if (typeof dhMain === "undefined") {
	dhMain = {};
}


/**
 * Form validation using jQuery validation plugin.
 */
dhMain.formValidation = {
	/**
	 * Default validation options.
	 */
	defaultOptions: {
		errorClass: "invalid-feedback",
		validClass: "valid-feedback",
		errorElement: "div",
		highlight: function (element) {
			$(element).parent().addClass("was-validated");
			$(element).addClass("invalid").removeClass("valid");
		},
		unhighlight: function (element) {
			$(element).removeClass("invalid").addClass("valid");
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
 * Executes when the DOM is fully loaded.
 */
$(function () {
	dhMain.formValidation.init();
});
