<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.order.view")}</title>


<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.ehtml">${message("admin.path.index")}</a> &raquo; ${message("admin.order.view")}
	</div>
	<table class="input tabContent">
		<tr>
			<th>
				${message("Order.sn")}:
			</th>
			<td>
				${order.sn}
			</td>
		</tr>
		<tr>
			<th>
				${message("Order.content.encode")}:
			</th>
			<td>
				<textarea style="width: 100%; height: 200px; padding: 0px;" readonly>${order.content}</textarea>
			</td>
		</tr>
		<tr>
			<th>
				${message("Order.content.decode")}:
			</th>
			<td>
				<textarea style="width: 100%; height: 200px; padding: 0px;" readonly>${content}</textarea>
			</td>
		</tr>
	</table>
	<table class="input">
		<tr>
			<th>
				&nbsp;
			</th>
			<td>
				<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.ehtml'" />
			</td>
		</tr>
	</table>
</body>
</html>