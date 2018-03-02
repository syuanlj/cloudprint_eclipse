<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.product.view")}</title>


<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.ehtml">${message("admin.path.index")}</a> &raquo; ${message("admin.product.view")}
	</div>
	<table class="input tabContent">
		<tr>
			<th>
				${message("Product.productCategory")}:
			</th>
			<td>
				${product.productCategory.name}
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
				${message("Product.bootVersion")}:
			</th>
			<td>
				${product.bootVersion}
			</td>
		</tr>
		<tr>
			<th>
				${message("Product.softwareVersion")}:
			</th>
			<td>
				${product.softwareVersion}
			</td>
		</tr>
		<tr>
			<th>
				${message("Product.hardwareVersion")}:
			</th>
			<td>
				${product.hardwareVersion}
			</td>
		</tr>
		<tr>
			<th>
				${message("Product.fontVersion")}:
			</th>
			<td>
				${product.fontVersion}
			</td>
		</tr>
		<tr>
			<th>
				${message("Product.oemSn")}:
			</th>
			<td>
				${product.oemSn}
			</td>
		</tr>
		<tr>
			<th>
				${message("Product.voltage")}:
			</th>
			<td>
				${product.voltage}
			</td>
		</tr>
		<tr>
			<th>
				${message("Product.manufacture")}:
			</th>
			<td>
				[#if product.manufacture??]${product.manufacture}[/#if]
			</td>
		</tr>
		<tr>
			<th>
				${message("Product.shop")}:
			</th>
			<td>
				[#if product.shop??]${product.shop.name}[/#if]
			</td>
		</tr>
	</table>
	<table class="input">
		<tr>
			<th>
				&nbsp;
			</th>
			<td>
				<input type="button" class="button" value="${message("admin.product.reset")}" onclick="location.href='reset_printer.ehtml?id=${product.id}'" />
				<input type="button" class="button" value="${message("admin.product.factory")}" onclick="location.href='reset_factory.ehtml?id=${product.id}'" />
				<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.ehtml'" />
			</td>
		</tr>
	</table>
</body>
</html>