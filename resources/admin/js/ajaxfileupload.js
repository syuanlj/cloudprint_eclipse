jQuery
		.extend( {

			createUploadIframe : function(id, uri) {
				// create frame
			var frameId = 'jUploadFrame' + id;
			var iframeHtml = '<iframe id="' + frameId + '" name="' + frameId
					+ '" style="position:absolute; top:-9999px; left:-9999px"';
			if (window.ActiveXObject) {F
				if (typeof uri == 'boolean') {
					iframeHtml += ' src="' + 'javascript:false' + '"';

				} else if (typeof uri == 'string') {
					iframeHtml += ' src="' + uri + '"';

				}
			}
			iframeHtml += ' />';
			jQuery(iframeHtml).appendTo(document.body);

			return jQuery('#' + frameId).get(0);
		},
		createUploadForm : function(id, inputFormId) {
			var formId = 'jUploadForm' + id;

			// 把form改掉。
			var form = $("#" + inputFormId)[0];
			jQuery(form).attr('id', formId);
			// jQuery('<form action="" method="POST" name="' + formId + '" id="'
			// + formId + '" enctype="multipart/form-data"></form>');

			return form;
		},

		ajaxFileUpload : function(s) {
			// TODO introduce global settings, allowing the client to modify
			// them for all requests, not only timeout
			s = jQuery.extend( {}, jQuery.ajaxSettings, s);
			var id = new Date().getTime();
			var formId = 'jUploadForm' + id;

			// 把form改掉即可，需要传递formId过来
			var form = jQuery.createUploadForm(id, s.formId);

			var io = jQuery.createUploadIframe(id, s.secureuri);
			var frameId = 'jUploadFrame' + id;

			// Watch for a new set of requests
			if (s.global && !jQuery.active++) {
				jQuery.event.trigger("ajaxStart");
			}
			var requestDone = false;
			// Create the request object
			var xml = {}
			if (s.global)
				jQuery.event.trigger("ajaxSend", [ xml, s ]);

			// Wait for a response to come back

			var uploadCallback = function(isTimeout) {
				var io = document.getElementById(frameId);
				try {
					if (io.contentWindow) {
						xml.responseText = io.contentWindow.document.body ? io.contentWindow.document.body.innerHTML
								: null;
						xml.responseXML = io.contentWindow.document.XMLDocument ? io.contentWindow.document.XMLDocument
								: io.contentWindow.document;

					} else if (io.contentDocument) {
						xml.responseText = io.contentDocument.document.body ? io.contentDocument.document.body.innerHTML
								: null;
						xml.responseXML = io.contentDocument.document.XMLDocument ? io.contentDocument.document.XMLDocument
								: io.contentDocument.document;
					}
				} catch (e) {
					// jQuery.handleError(s, xml, null, e);
				}
				if (xml || isTimeout == "timeout") {
					requestDone = true;
					var status;
					try 
					{
						status = isTimeout != "timeout" ? "success" : "error";
						
						// Make sure that the request was successful or
						// notmodified
						if (status != "error") 
						{
							// process the data (runs the xml through httpData
							// regardless of callback)
							var data = jQuery.uploadHttpData(xml, s.dataType);
							// If a local callback was specified, fire it and
							// pass it the data
							if (s.success)
								s.success(data, status);

							// Fire the global callback
							if (s.global)
								jQuery.event.trigger("ajaxSuccess", [ xml, s ]);
						}
						// jQuery.handleError(s, xml, status);
					} catch (e) 
					{
						status = "error";
						// jQuery.handleError(s, xml, status, e);
					}

					// The request was completed
					if (s.global)
						jQuery.event.trigger("ajaxComplete", [ xml, s ]);

					// Handle the global AJAX counter
					if (s.global && !--jQuery.active)
						jQuery.event.trigger("ajaxStop");

					// Process result
					if (s.complete)
						s.complete(xml, status);

					jQuery(io).unbind()

					setTimeout(function() {
						try {
							jQuery(io).remove();
							jQuery(form).remove();

						} catch (e) {
							// jQuery.handleError(s, xml, null, e);
						}

					}, 100)

					xml = null

				}
			}

			// Timeout checker
			if (s.timeout > 0) {
				setTimeout(function() {
					// Check to see if the request is still happening
						if (!requestDone)
							uploadCallback("timeout");
					}, s.timeout);
			}
			try {

				var form = jQuery('#' + formId);

				jQuery(form).attr('action', s.url);
				jQuery(form).attr('method', 'POST');
				jQuery(form).attr('target', frameId);
				if (form.encoding) {
					jQuery(form).attr('encoding', 'multipart/form-data');
				} else {
					jQuery(form).attr('enctype', 'multipart/form-data');
				}

				jQuery(form).submit();

			} catch (e) {
				// jQuery.handleError(s, xml, null, e);
			}

			jQuery('#' + frameId).load(uploadCallback);
			return {
				abort : function() {
				}
			};
		},

		uploadHttpData : function(xml, type) {
			
			//googlecrome上有问题，比如我要返回{a:b}，到这儿却变成了<pre>...{a:b}...</pre>
			//var data = type == "xml"? xml.responseXML : xml.responseText;
			
			//if (type == "json") {data = jQuery.parseJSON(data);}

			return {};
		}
		
})
