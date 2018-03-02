<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.firmware.view")}</title>


<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>

</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.ehtml">${message("admin.path.index")}</a> &raquo; ${message("admin.firmware.view")}
	</div>

	<table class="input tabContent">
		<tr>
			<th>
				${message("admin.firmware.productCategory")}:
			</th>
			<td>
				${firmware.productCategory.name}
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.firmware.url")}:
			</th>
			<td>
				${firmware.url}
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.firmware.ftpname")}:
			</th>
			<td>
				${firmware.ftpname}
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.firmware.ftppwd")}:
			</th>
			<td>
				${firmware.ftppwd}
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.firmware.version")}:
			</th>
			<td>
				${firmware.version}
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.firmware.length")}:
			</th>
			<td>
				${firmware.length}
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.firmware.md5")}:
			</th>
			<td>
				${firmware.md5}
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.firmware.date")}:
			</th>
			<td>
				${firmware.date}
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.firmware.enddate")}:
			</th>
			<td>
				${firmware.enddate}
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.firmware.type")}:
			</th>
			<td>
				${message("admin.firmware.type." + firmware.type)}
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.firmware.hour")}:
			</th>
			<td>
				${firmware.hour}
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.firmware.targetVersion")}:
			</th>
			<td>
				${firmware.targetVersion}
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.firmware.area")}:
			</th>
			<td>
				[#if firmware.area??]${firmware.area.fullName}[/#if]
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.firmware.product.type")}:
			</th>
			<td>
				${message("admin.firmware.product.type.${firmware.productType}")}
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.firmware.productSn")}:
			</th>
			<td>
				${firmware.productSn}
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.common.createDate")}:
			</th>
			<td>
				${firmware.createDate?string("yyyy-MM-dd HH:mm:ss")}
			</td>
		</tr>
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