<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.productCategory.add")}</title>


<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/cloudprint/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="/cloudprint/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="/cloudprint/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="/cloudprint/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="/cloudprint/resources/admin/js/common.js"></script>
<script type="text/javascript" src="/cloudprint/resources/admin/js/input.js"></script>
<style type="text/css">
.brands label {
	width: 150px;
	display: block;
	float: left;
	padding-right: 6px;
}
</style>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	
	[@flash_message /]

	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			order: "digits"
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.ehtml">${message("admin.path.index")}</a> &raquo; ${message("admin.productCategory.add")}
	</div>
	<form id="inputForm" action="save.ehtml" method="post">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("ProductCategory.name")}:
				</th>
				<td>
					<input type="text" id="name" name="name" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("ProductCategory.parent")}:
				</th>
				<td>
					<select name="parentId">
						<option value="">${message("admin.productCategory.root")}</option>
						[#list productCategoryTree as category]
							<option value="${category.id}">
								[#if category.grade != 0]
									[#list 1..category.grade as i]
										&nbsp;&nbsp;
									[/#list]
								[/#if]
								${category.name}
							</option>
						[/#list]
					</select>
				</td>
			</tr>
<!--
			<tr>
				<th>
					${message("ProductCategory.template")}:
				</th>
				<td>
					<select name="templateId">
						[#list templates as template]
							<option value="${template.id}">
								${template.name}
							</option>
						[/#list]
					</select>
				</td>
			</tr>
-->
			<tr>
				<th>
					${message("admin.common.order")}:
				</th>
				<td>
					<input type="text" name="order" class="text" maxlength="9" title="${message("admin.productCategory.order")}" />
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