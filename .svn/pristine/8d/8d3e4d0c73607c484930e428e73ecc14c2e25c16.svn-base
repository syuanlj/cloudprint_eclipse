[#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.order.list")}</title>


<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<style type="text/css">
.filterTable th {
	width: 80px;
	line-height: 25px;
	padding: 5px 10px 5px 0px;
	text-align: right;
	font-weight: normal;
	color: #333333;
	background-color: #f8fbff;
}

.filterTable td {
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
	var $filterButton = $("#filterButton");

	[@flash_message /]
	
	// 订单筛选
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

	$filterButton.click(function() {
		$.dialog({
			title: "${message("admin.product.filter")}",
			[@compress single_line = true]
				content: '
				<table id="filterTable" class="filterTable">
					<tr>
						<th>
							${message("Product.sn")}:
						<\/th>
						<td>
							<input type="text" id="productSn" name="productSn" maxlength="200" />
						<\/td>
					<\/tr>
				<\/table>',
			[/@compress]
			width: 300,
			modal: true,
			ok: "${message("admin.dialog.ok")}",
			cancel: "${message("admin.dialog.cancel")}",
			onOk: function() {
				$("#filterTable :input").each(function() {
					var $this = $(this);
					$("#" + $this.attr("name")).val($this.val());
				});
				$listForm.submit();
			}
		});
	});});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.ehtml">${message("admin.path.index")}</a> &raquo; ${message("admin.order.list")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="list.ehtml" method="get">
		<input type="hidden" id="productSn" name="productSn" value="${productSn}" />
		<input type="hidden" id="orderStatus" name="orderStatus" value="${orderStatus}" />
		<input type="hidden" id="hasExpired" name="hasExpired" value="[#if hasExpired??]${hasExpired?string("true", "false")}[/#if]" />
		<div class="bar">
			<div class="buttonWrap">
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
				<div class="menuWrap">
					<a href="javascript:;" id="filterSelect" class="button">
						${message("admin.order.filter")}<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="filterOption" class="check">
							<li>
								<a href="javascript:;" name="orderStatus" val="received"[#if orderStatus == "received"] class="checked"[/#if]>${message("Order.OrderStatus.received")}</a>
							</li>
							<li>
								<a href="javascript:;" name="orderStatus" val="sent"[#if orderStatus == "sent"] class="checked"[/#if]>${message("Order.OrderStatus.sent")}</a>
							</li>
							<li>
								<a href="javascript:;" name="orderStatus" val="printed"[#if orderStatus == "printed"] class="checked"[/#if]>${message("Order.OrderStatus.printed")}</a>
							</li>
							<li class="separator">
								<a href="javascript:;" name="hasExpired" val="true"[#if hasExpired?? && hasExpired] class="checked"[/#if]>${message("admin.order.hasExpired")}</a>
							</li>
							<li>
								<a href="javascript:;" name="hasExpired" val="false"[#if hasExpired?? && !hasExpired] class="checked"[/#if]>${message("admin.order.unexpired")}</a>
							</li>
						</ul>
					</div>
				</div>
				<a href="javascript:;" id="filterButton" class="button">${message("admin.product.filter")}</a>
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
							<a href="javascript:;"[#if page.searchProperty == "sn"] class="current"[/#if] val="sn">${message("Order.sn")}</a>
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
					<a href="javascript:;" class="sort" name="sn">${message("Order.sn")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="product">${message("Order.product")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="orderStatus">${message("Order.orderStatus")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="pushTimes">${message("Order.pushTimes")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="lastPush">${message("Order.lastPush")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="finish">${message("Order.finish")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="expire">${message("Order.expire")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="createDate">${message("admin.common.createDate")}</a>
				</th>
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list page.content as order]
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						${order.sn}
					</td>
					<td>
						[#if order.product??]${order.product.sn}[/#if]
					</td>
					<td>
						[#if order.orderStatus == "received"]<span class="blue">[#elseif order.orderStatus == "sent"]<span class="red">[#elseif order.orderStatus == "printed"]<span class="green">[/#if]${message("Order.OrderStatus." + order.orderStatus)}</span>
						[#if order.expired]<span class="gray">(${message("admin.order.hasExpired")})</span>[/#if]
					</td>
					<td>
						${order.pushTimes}
					</td>
					<td>
						[#if order.lastPush??]<span title="${order.lastPush?string("yyyy-MM-dd HH:mm:ss")}">${order.lastPush?string("yyyy-MM-dd HH:mm:ss")}</span>[/#if]
					</td>
					<td>
						[#if order.finish??]<span title="${order.finish?string("yyyy-MM-dd HH:mm:ss")}">${order.finish?string("yyyy-MM-dd HH:mm:ss")}</span>[/#if]
					</td>
					<td>
						[#if order.expire??]<span title="${order.expire?string("yyyy-MM-dd HH:mm:ss")}">${order.expire?string("yyyy-MM-dd HH:mm:ss")}</span>[/#if]
					</td>
					<td>
						<span title="${order.createDate?string("yyyy-MM-dd HH:mm:ss")}">${order.createDate?string("yyyy-MM-dd HH:mm:ss")}</span>
					</td>
					<td>
						<a href="view.ehtml?id=${order.id}">[${message("admin.common.view")}]</a>
						<a href="push.ehtml?id=${order.id}">[${message("admin.product.push")}]</a>
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