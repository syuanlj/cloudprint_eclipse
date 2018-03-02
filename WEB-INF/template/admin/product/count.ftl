<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.area.list")}</title>


<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {
	
	[@flash_message /]
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.ehtml">${message("admin.path.index")}</a> &raquo; ${message("admin.product.productCount")}
	</div>
	<div class="bar">
		[#if parent??]
			<div class="pageBar">
				[#if parent.parent??]
					<a href="count.ehtml?parentId=${parent.parent.id}" class="iconButton">
						<span class="upIcon">&nbsp;</span>${message("admin.area.parent")}
					</a>
				[#else]
					<a href="count.ehtml" class="iconButton">
						<span class="upIcon">&nbsp;</span>${message("admin.area.parent")}
					</a>
				[/#if]
			</div>
		[/#if]
	</div>
	<table id="listTable" class="list">
		<tr>
			<th colspan="10" class="green" style="text-align: center;">
				[#if parent??]${message("admin.area.parent")} - ${parent.name}[#else]${message("admin.area.root")}[/#if]
			</th>
		</tr>
		[#list areas?chunk(5, "") as row]
			<tr>
				[#list row as area]
					[#if area?has_content]
						<td>
							<a href="count.ehtml?parentId=${area.id}" title="${message("admin.common.view")}">${area.name}</a>
						</td>
						<td style="text-align:right; padding-right:20px; ">
							${area.productCount}
						</td>
					[#else]
						<td>
							&nbsp;
						</td>
						<td>
							&nbsp;
						</td>
					[/#if]
				[/#list]
			</tr>
		[/#list]
		[#if !areas?has_content]
			<tr>
				<td style="text-align: center; color: red;">
					${message("admin.area.emptyChildren")}
				</td>
			</tr>
		[/#if]
	</table>
</body>
</html>