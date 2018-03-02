<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.product.edit")}</title>


<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $productCategoryId = $("#productCategoryId");
	
	[@flash_message /]
	
	// 表单验证
	$inputForm.validate({
		rules: {
			productCategoryId: "required",
			sn: {
				pattern: /^[0-9a-zA-Z_-]+$/,
				remote: {
					url: "check_sn.ehtml?previousSn=${product.sn}",
					cache: false
				}
			}
		},
		messages: {
			sn: {
				pattern: "${message("admin.validate.illegal")}",
				remote: "${message("admin.validate.exist")}"
			}
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.ehtml">${message("admin.path.index")}</a> &raquo; ${message("admin.product.edit")}
	</div>
	<form id="inputForm" action="update.ehtml" method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${product.id}" />
		<table class="input tabContent">
			<tr>
				<th>
					${message("Product.productCategory")}:
				</th>
				<td>
					<select id="productCategoryId" name="productCategoryId">
						[#list productCategoryTree as productCategory]
							<option value="${productCategory.id}"[#if productCategory == product.productCategory] selected="selected"[/#if]>
								[#if productCategory.grade != 0]
									[#list 1..productCategory.grade as i]
										&nbsp;&nbsp;
									[/#list]
								[/#if]
								${productCategory.name}
							</option>
						[/#list]
					</select>
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.sn")}:
				</th>
				<td>
					${product.sn}
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.shop")}:
				</th>
				<td>
					<select name="shopId">
						<option value="">${message("admin.common.choose")}</option>
						[#list shops as shop]
							<option value="${shop.id}"[#if shop == product.shop] selected="selected"[/#if]>
								${shop.name}
							</option>
						[/#list]
					</select>
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.common.setting")}:
				</th>
				<td>
					<label>
						<input type="checkbox" name="isList" value="true"[#if product.isList] checked="checked"[/#if] />${message("Product.isList")}
						<input type="hidden" name="_isList" value="false" />
					</label>
					<label>
						<input type="checkbox" name="isMarketable" value="true"[#if product.isMarketable] checked="checked"[/#if] />${message("Product.isMarketable")}
						<input type="hidden" name="_isMarketable" value="false" />
					</label>
				</td>
			</tr>
		</table>
		<table class="input">
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