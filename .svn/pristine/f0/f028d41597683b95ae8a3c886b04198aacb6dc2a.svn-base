<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.firmware.list")}</title>


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
		<a href="${base}/admin/common/index.ehtml">${message("admin.path.index")}</a> &raquo; ${message("admin.firmware.list")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="list.ehtml" method="get">
		<div class="bar">
			<a href="add.ehtml" class="iconButton">
				<span class="addIcon">&nbsp;</span>${message("admin.common.add")}
			</a>
			<div class="buttonWrap">
				<a href="javascript:;" id="deleteButton" class="iconButton disabled">
					<span class="deleteIcon">&nbsp;</span>${message("admin.common.delete")}
				</a>
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check">
					<input type="checkbox" id="selectAll" />
				</th>
				<th>
					<a href="javascript:;" class="sort" name="productCategory">${message("admin.firmware.productCategory")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="version">${message("admin.firmware.version")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="length">${message("admin.firmware.length")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="date">${message("admin.firmware.date")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="enddate">${message("admin.firmware.enddate")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="isCompleted">${message("admin.firmware.isCompleted")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="createDate">${message("admin.common.createDate")}</a>
				</th>
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list page.content as firmware]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${firmware.id}" />
					</td>
					<td>
						${firmware.productCategory.name}
					</td>
					<td>
						${firmware.version}
					</td>
					<td>
						${firmware.length}
					</td>
					<td>
						${firmware.date}
					</td>
					<td>
						${firmware.enddate}
					</td>
					<td>
						<span class="${firmware.isCompleted?string("true", "false")}Icon">&nbsp;</span>
					</td>
					<td>
						<span title="${firmware.createDate?string("yyyy-MM-dd HH:mm:ss")}">${firmware.createDate?string("yyyy-MM-dd HH:mm:ss")}</span>
					</td>
					<td>
						<a href="view.ehtml?firmwareId=${firmware.id}">[${message("admin.common.view")}]</a>
						<a href="result.ehtml?firmwareId=${firmware.id}">[${message("admin.firmware.result")}]</a>
					</td>
				</tr>
			[/#list]
		</table>
		[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
			[#include "/admin/include/pagination.ftl"]
		[/@pagination]
	</form>
</body>
</html>