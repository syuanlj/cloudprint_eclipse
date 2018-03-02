<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.product.add")}</title>


<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>

<script type="text/javascript">
$().ready(function() {

	var $fileForm = $("#fileForm");
	var $dataForm = $("#dataForm");
	var $previewButton = $("#previewButton");
	var $saveButton = $("#saveButton");
	
	[@flash_message /]

	$previewButton.click(function() {
		$fileForm.submit();
	});

	$saveButton.click(function() {
		$dataForm.submit();
	});

	// 表单验证
	$fileForm.validate({
		rules: {
			productFile: {
				required: true,
				extension: "${setting.productFileExtension}"
			},
		},
		submitHandler: function(form) {
			form.submit();
		}
	});
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.ehtml">${message("admin.path.index")}</a> &raquo; ${message("admin.product.add")}
	</div>
	<form id="fileForm" action="preview.ehtml" method="post" enctype="multipart/form-data">
		<table class="input">
			<tr>
				<th>
					${message("admin.product.xml")}:
				</th>
				<td>
					<span class="fieldSet">
						<input type="file" id="productFile" name="productFile"/>
					</span>
				</td>
			</tr>
			[#if checkResult??]
				<tr>
					<th>
						${message("admin.product.checkResult")}:
					</th>
					<td>
						${checkResult.content}
					</td>
				</tr>
			[/#if]
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" id="previewButton" class="button" value="${message("admin.common.preview")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.ehtml'" />
				</td>
			</tr>
		</table>
	</form>
	[#if products?has_content]
	<form id="dataForm" action="save.ehtml" method="post">
		<table class="input">
			<tr>
				<th>
					${message("admin.product.list")}:
				</th>
				<td>
					<table>
						[#list products as product]
							<tr>
								<td>
									<input type="text" name="products[${product_index}].productCategory.name"  value="${product.productCategory.name}" maxlength="200" style="width: 100px;" readonly/>
								</td>
								<td>
									<input type="text" name="products[${product_index}].sn"  value="${product.sn}" maxlength="200" style="width: 100px;" readonly/>
								</td>
								<td>
									<input type="text" name="products[${product_index}].username"  value="${product.username}" maxlength="200" style="width: 100px;" readonly/>
								</td>
								<td>
									<input type="text" name="products[${product_index}].password"  value="${product.password}" maxlength="200" style="width: 100px;" readonly/>
								</td>
								<td>
									<input type="text" name="products[${product_index}].manufacture"  value="${product.manufacture?string("yyyy-MM-dd")}" maxlength="200" style="width: 100px;" readonly/>
								</td>
							</tr>
						[/#list]
					</table>
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" id="saveButton" class="button" value="${message("admin.common.submit")}" />
				</td>
			</tr>
		</table>
	</form>
	[/#if]
</body>
</html>