<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.product.list")}</title>


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
	var $moreButton = $("#moreButton");
	var $filterSelect = $("#filterSelect");
	var $filterOption = $("#filterOption a");
	var $statistic = $("select[name='statistic']");
	
	[@flash_message /]
	
	// 更多选项
	$moreButton.click(function() {
		$.dialog({
			title: "${message("admin.product.moreOption")}",
			[@compress single_line = true]
				content: '
				<table id="moreTable" class="moreTable">
					<tr>
						<th>
							${message("admin.product.productCategory")}:
						<\/th>
						<td>
							<select name="productCategoryId">
								<option value="">${message("admin.common.choose")}<\/option>
								[#list productCategoryTree as productCategory]
									<option value="${productCategory.id}"[#if productCategory.id == productCategoryId] selected="selected"[/#if]>
										[#if productCategory.grade != 0]
											[#list 1..productCategory.grade as i]
												&nbsp;&nbsp;
											[/#list]
										[/#if]
										${productCategory.name}
									<\/option>
								[/#list]
							<\/select>
						<\/td>
					<\/tr>
					<tr>
						<th>
							${message("admin.product.area")}:
						<\/th>
						<td>
							<span class="fieldSet">
								<input type="hidden" id="areaSelect" name="areaId" [#if area??]value="${area.id}" treePath="${(area.treePath)!}"[/#if] />
							</span>
						<\/td>
					<\/tr>
				<\/table>',
			[/@compress]
			width: 600,
			modal: true,
			ok: "${message("admin.dialog.ok")}",
			cancel: "${message("admin.dialog.cancel")}",
			onShow: function() {
				// 地区选择
				$("#areaSelect").lSelect({
					url: "${base}/admin/common/area.ehtml"
				});
			},
			onOk: function() {
				$("#moreTable :input").each(function() {
					var $this = $(this);
					$("#" + $this.attr("name")).val($this.val());
				});
				$listForm.submit();
			}
		});
	});
	
	// 商品筛选
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

	// 打印选择
	$statistic.change(function() {
		var $this = $(this);
		if ($this.val() != "") {
			//window.open($this.val());
			window.location.href = $this.val();
		}
	});
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.ehtml">${message("admin.path.index")}</a> &raquo; ${message("admin.product.list")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="list.ehtml" method="get">
		<input type="hidden" id="productCategoryId" name="productCategoryId" value="${productCategoryId}" />
		<input type="hidden" id="isList" name="isList" value="[#if isList??]${isList?string("true", "false")}[/#if]" />
		<input type="hidden" id="isMarketable" name="isMarketable" value="[#if isMarketable??]${isMarketable?string("true", "false")}[/#if]" />
		<input type="hidden" id="isOnline" name="isOnline" value="[#if isOnline??]${isOnline?string("true", "false")}[/#if]" />
		<input type="hidden" id="isNormal" name="isNormal" value="[#if isNormal??]${isNormal?string("true", "false")}[/#if]" />
		<input type="hidden" id="areaId" name="areaId"/>
		<div class="bar">
			<!--
			<a href="add.ehtml" class="iconButton">
				<span class="addIcon">&nbsp;</span>${message("admin.common.add")}
			</a>-->
			<div class="buttonWrap">
				[#if isAdmin == true]
				<a href="javascript:;" id="deleteButton" class="iconButton disabled">
					<span class="deleteIcon">&nbsp;</span>${message("admin.common.delete")}
				</a>
				[/#if]
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
								<a href="javascript:;" name="isOnline" val="true"[#if isOnline?? && isOnline] class="checked"[/#if]>${message("admin.product.isOnline")}</a>
							</li>
							<li>
								<a href="javascript:;" name="isOnline" val="false"[#if isOnline?? && !isOnline] class="checked"[/#if]>${message("admin.product.notOnline")}</a>
							</li>
							<li class="separator">
								<a href="javascript:;" name="isNormal" val="true"[#if isNormal?? && isNormal] class="checked"[/#if]>${message("admin.product.isNormal")}</a>
							</li>
							<li>
								<a href="javascript:;" name="isNormal" val="false"[#if  isNormal?? && !isNormal] class="checked"[/#if]>${message("admin.product.notNormal")}</a>
							</li>
						</ul>
					</div>
				</div>
				<a href="javascript:;" id="moreButton" class="button">${message("admin.product.moreOption")}</a>
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
					<input type="checkbox" id="selectAll" />
				</th>
				<th>
					<a href="javascript:;" class="sort" name="sn">${message("Product.sn")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="productCategory">${message("Product.productCategory")}</a>
				</th>
				<th>
					<a href="javascript:;">${message("Product.isList")}</a>
				</th>
				<th>
					<a href="javascript:;">${message("Product.isMarketable")}</a>
				</th>
				<th>
					<a href="javascript:;">${message("Product.isOnline")}</a>
				</th>
				<th>
					<a href="javascript:;">${message("Product.isNormal")}</a>
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
					<a href="javascript:;" class="sort" name="manufacture">${message("Product.manufacture")}</a>
				</th>
				<th>
					<span>${message("admin.order.statistic")}</span>
				</th>
				<th>
					<span>${message("admin.product.area")}</span>
				</th>
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list page.content as product]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${product.id}" />
					</td>
					<td>
						${product.sn}
					</td>
					<td>
						${product.productCategory.name}
					</td>
					<td>
						<span class="${product.isList?string("true", "false")}Icon">&nbsp;</span>
					</td>
					<td>
						<span class="${product.isMarketable?string("true", "false")}Icon">&nbsp;</span>
					</td>
					<td>
						<span class="${product.isOnline?string("true", "false")}Icon">&nbsp;</span>
					</td>
					<td>
						[#if product.status == 0]<span class="trueIcon">&nbsp;</span>[#else]${message("admin.product.error${product.error}")}[/#if]
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
						[#if product.manufacture??]<span title="${product.manufacture?string("yyyy-MM-dd HH:mm:ss")}">${product.manufacture}</span>[/#if]
					</td>
					<td>
						<select name="statistic">
							<option value="">${message("admin.common.choose")}</option>
							<option value="../statistic/product_hour.ehtml?id=${product.id}">${message("admin.statistic.product.hour")}</option>
							<option value="../statistic/product_date.ehtml?id=${product.id}">${message("admin.statistic.product.date")}</option>
							<option value="../statistic/product_month.ehtml?id=${product.id}">${message("admin.statistic.product.month")}</option>
						</select>
					</td>
					<td>
						[#if product.area??]${product.area.fullName}[#else]-[/#if]
					</td>
					<td>
						<a href="view.ehtml?id=${product.id}">[${message("admin.common.view")}]</a>
						<a href="edit.ehtml?id=${product.id}">[${message("admin.common.edit")}]</a>
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