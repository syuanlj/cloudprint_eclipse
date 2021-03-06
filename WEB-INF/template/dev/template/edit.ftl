<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.template.edit")}</title>


<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");

	[@flash_message /]

	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			order: "digits",
			content: "required"
		}
	});

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.ehtml">${message("admin.path.index")}</a> &raquo; ${message("admin.template.edit")} [${template.name}]
	</div>
	<form id="inputForm" action="update.ehtml" method="post">
		<input type="hidden" name="id" class="text" value="${template.id}" />
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Template.name")}:
				</th>
				<td>
					<input type="text" name="name" class="text" value="${template.name}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.common.order")}:
				</th>
				<td>
					<input type="text" name="order" class="text" value="${template.order}" maxlength="9" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Template.content")}:
				</th>
				<td>
					<textarea name="content" style="width: 100%; height: 500px; padding: 0px;">${template.content}</textarea>
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.ehtml'" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>