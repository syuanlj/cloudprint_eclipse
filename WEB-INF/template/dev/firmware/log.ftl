<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.firmware.log")}</title>


<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<style type="text/css">
.moreTable th {
	width: 80px;
	line-height: 25px;
	padding: 5px 10px 5px 0px;
	text-align: right;
	font-weight: normal;
	color: #333333;
	background-color: #f8fbff;
}

.moreTable td {
	line-height: 25px;
	padding: 5px;
	color: #666666;
}
</style>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.ehtml">${message("admin.path.index")}</a> &raquo; ${message("admin.firmware.log")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<div class="bar">
		<div class="buttonWrap">
			<a href="javascript:;" id="refreshButton" class="iconButton">
				<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
			</a>
		</div>
	</div>
	<table id="listTable" class="list">
		<tr>
			<th class="check">
				&nbsp;
			</th>
			<th>
				<a href="javascript:;" class="sort" name="code">${message("admin.firmware.log.code")}</a>
			</th>
			<th>
				<a href="javascript:;" class="sort" name="createDate">${message("admin.common.createDate")}</a>
			</th>
		</tr>
		[#list page.content as log]
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					${message("admin.firmware.log.code${log.code}")}
				</td>
				<td>
					<span title="${log.createDate?string("yyyy-MM-dd HH:mm:ss")}">${log.createDate?string("yyyy-MM-dd HH:mm:ss")}</span>
				</td>
			</tr>
		[/#list]
	</table>
	[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
		[#include "/admin/include/pagination.ftl"]
	[/@pagination]
</body>
</html>