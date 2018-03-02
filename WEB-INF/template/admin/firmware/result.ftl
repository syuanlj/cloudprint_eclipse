<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.firmware.result")}</title>


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
<script type="text/javascript">
$().ready(function() {

	var $listForm = $("#listForm");
	var $filterSelect = $("#filterSelect");
	var $filterOption = $("#filterOption a");
	
	[@flash_message /]

	$filterSelect.mouseover(function() {
		var $this = $(this);
		var offset = $this.offset();
		var $menuWrap = $this.closest("div.menuWrap");
		var $popupMenu = $menuWrap.children("div.popupMenu");
		$popupMenu.css({left: offset.left, top: offset.top + $this.height() + 2}).show();
		$menuWrap.mouseleave(function() {
			$popupMenu.hide();
		});
	});
	
	// 筛选选项
	$filterOption.click(function() {
		var $this = $(this);
		var $dest = $("#" + $this.attr("name"));
		if ($this.hasClass("checked")) {
			$dest.val("");
		} else {
			$dest.val($this.attr("val"));
		}
		$listForm.submit();
		return false;
	});
});
</script></head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.ehtml">${message("admin.path.index")}</a> &raquo; ${message("admin.firmware.result")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="result.ehtml" method="get">
		<input type="hidden" id="firmwareId" name="firmwareId" value="${firmwareId}" />
		<input type="hidden" id="isLatest" name="isLatest" value="[#if isLatest??]${isLatest?string("true", "false")}[/#if]" />
		<div class="bar">
			<div class="buttonWrap">
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
				<div class="menuWrap">
					<a href="javascript:;" id="filterSelect" class="button">
						${message("admin.product.filter")}<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="filterOption" class="check">
							<li>
								<a href="javascript:;" name="isLatest" val="true"[#if isLatest?? && isLatest] class="checked"[/#if]>${message("admin.product.isLatest")}</a>
							</li>
							<li>
								<a href="javascript:;" name="isLatest" val="false"[#if isLatest?? && !isLatest] class="checked"[/#if]>${message("admin.product.notLatest")}</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="menuWrap">
				<div class="search">
					<span id="searchPropertySelect" class="arrow">&nbsp;</span>
					<input type="text" id="searchValue" name="searchValue" value="${page.searchValue}" maxlength="200" />
					<button type="submit">&nbsp;</button>
				</div>
				<div class="popupMenu">
					<ul id="searchPropertyOption">
						<li>
							<a href="javascript:;"[#if page.searchProperty == "sn"] class="current"[/#if] val="sn">${message("Product.sn")}</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check">
					&nbsp;
				</th>
				<th>
					<a href="javascript:;" class="sort" name="sn">${message("Product.sn")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="productCategory">${message("Product.productCategory")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="isLatest">${message("Product.isLatest")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="bootVersion">${message("Product.bootVersion")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="softwareVersion">${message("Product.softwareVersion")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="hardwareVersion">${message("Product.hardwareVersion")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="fontVersion">${message("Product.fontVersion")}</a>
				</th>
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list page.content as product]
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						${product.sn}
					</td>
					<td>
						${product.productCategory.name}
					</td>
					<td>
						<span class="${product.isLatest?string("true", "false")}Icon">&nbsp;</span>
					</td>
					<td>
						${product.bootVersion}
					</td>
					<td>
						${product.softwareVersion}
					</td>
					<td>
						${product.hardwareVersion}
					</td>
					<td>
						${product.fontVersion}
					</td>
					<td>
						<a href="log.ehtml?productId=${product.id}">[${message("admin.firmware.log")}]</a>
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