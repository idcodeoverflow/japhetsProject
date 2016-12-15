/**
 * Utils to prevent security issues on ajax.
 */
function csrfValidation() {
	var CONTENT = "content";
	var token = $("meta[name='_csrf']").attr(CONTENT);
	var header = $("meta[name='_csrf_header']").attr(CONTENT);
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
}