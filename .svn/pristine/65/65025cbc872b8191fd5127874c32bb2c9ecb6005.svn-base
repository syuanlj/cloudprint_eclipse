[#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.main.title")}</title>


<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/admin/css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<style type="text/css">
*{
	font: 12px tahoma, Arial, Verdana, sans-serif;
}
html, body {
	width: 100%;
	height: 100%;
	overflow: hidden;
}
</style>
<script type="text/javascript">
$().ready(function() {

	var $menuItem = $("#menu a");
	
	$menuItem.click(function() {
		var $this = $(this);
		$menuItem.removeClass("current");
		$this.addClass("current");
	});

	var $productMenu = $(".productMenu");
	var $subProductMenu = $(".subProductMenu");
	$productMenu.click(function() {
		var $this = $(this);
		$this.toggleClass("plus");
		$this.toggleClass("minus");
		$subProductMenu.toggleClass("retract");
	});

	var $printMenu = $(".printMenu");
	var $subPrintMenu = $(".subPrintMenu");
	$printMenu.click(function() {
		var $this = $(this);
		$this.toggleClass("plus");
		$this.toggleClass("minus");
		$subPrintMenu.toggleClass("retract");
	});

	var $receiveMenu = $(".receiveMenu");
	var $subReceiveMenu = $(".subReceiveMenu");
	$receiveMenu.click(function() {
		var $this = $(this);
		$this.toggleClass("plus");
		$this.toggleClass("minus");
		$subReceiveMenu.toggleClass("retract");
	});

});
</script>
</head>
<body>
	<script type="text/javascript">
		if (self != top) {
			top.location = self.location;
		};
	</script>
	<table class="main">
		<tr>
			<th class="logo">
				<a href="main.ehtml">
					<img src="${base}/resources/admin/images/header_logo.png" />
				</a>
			</th>
			<th class="title">
				<img src="${base}/resources/admin/images/title.png" />
			</th>
			<th>
				<div class="link">
					<strong>[@shiro.principal /]</strong>
					${message("admin.main.hello")}!
					<a href="../logout.jsp" target="_top">[${message("admin.main.logout")}]</a>
				</div>
			</th>
		</tr>
		<tr>
			<td id="menu" class="menu">
				<dl class="default">
					<dd>&nbsp;</dd>
					[#list ["admin:product", "admin:productCategory", "admin:shop", "admin:productCount"] as permission]
						[@shiro.hasPermission name = permission]
							<dt>${message("admin.main.productGroup")}</dt>
							[#break /]
						[/@shiro.hasPermission]
					[/#list]
					[@shiro.hasPermission name="admin:product"]
						<dd>
							<a href="javascript:void(0)" class="productMenu plus">${message("admin.main.product")}</a>
						</dd>
						<dd style="text-indent: 1em" class="subProductMenu retract">
							<a href="../product/list.ehtml?isMarketable=true" target="iframe">${message("admin.main.product.isMarketable")}</a>
						</dd>
						<dd style="text-indent: 1em" class="subProductMenu retract">
							<a href="../product/list.ehtml?isMarketable=false" target="iframe">${message("admin.main.product.notMarketable")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:productCategory"]
						<dd>
							<a href="../product_category/list.ehtml" target="iframe">${message("admin.main.productCategory")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:shop"]
						<dd>
							<a href="../shop/list.ehtml" target="iframe">${message("admin.main.shop")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:productCount"]
						<dd>
							<a href="../product/count.ehtml" target="iframe">${message("admin.main.productCount")}</a>
						</dd>
					[/@shiro.hasPermission]

					<dd>&nbsp;</dd>
					[#list ["admin:order"] as permission]
						[@shiro.hasPermission name = permission]
							<dt>${message("admin.main.orderGroup")}</dt>
							[#break /]
						[/@shiro.hasPermission]
					[/#list]
					[@shiro.hasPermission name="admin:order"]
						<dd>
							<a href="../order/list.ehtml" target="iframe">${message("admin.main.order")}</a>
						</dd>
					[/@shiro.hasPermission]

					<dd>&nbsp;</dd>
					[#list ["admin:template", "admin:firmware"] as permission]
						[@shiro.hasPermission name = permission]
							<dt>${message("admin.main.contentGroup")}</dt>
							[#break /]
						[/@shiro.hasPermission]
					[/#list]
					[@shiro.hasPermission name="admin:template"]
						<dd>
							<a href="../template/list.ehtml" target="iframe">${message("admin.main.template")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:firmware"]
						<dd>
							<a href="../firmware/list.ehtml" target="iframe">${message("admin.main.firmware")}</a>
						</dd>
					[/@shiro.hasPermission]

					<dd>&nbsp;</dd>
					[#list ["admin:statistic"] as permission]
						[@shiro.hasPermission name = permission]
							<dt>${message("admin.main.statisticGroup")}</dt>
							[#break /]
						[/@shiro.hasPermission]
					[/#list]
					[@shiro.hasPermission name="admin:statistic"]
						<dd>
							<a href="../map/view.ehtml" target="iframe">${message("admin.statistic.map.view")}</a>
						</dd>
						<dd>
							<a href="javascript:void(0)" class="printMenu plus">${message("admin.statistic.print")}</a>
						</dd>
						<dd class="subPrintMenu retract" style="text-indent: 1em">
							<a href="../statistic/print_hour.ehtml" target="iframe">${message("admin.statistic.print.hour")}</a>
						</dd>
						<dd class="subPrintMenu retract" style="text-indent: 1em">
							<a href="../statistic/print_date.ehtml" target="iframe">${message("admin.statistic.print.date")}</a>
						</dd>
						<dd class="subPrintMenu retract" style="text-indent: 1em">
							<a href="../statistic/print_month.ehtml" target="iframe">${message("admin.statistic.print.month")}</a>
						</dd>
						<dd>
							<a href="javascript:void(0)" class="receiveMenu plus">${message("admin.statistic.receive")}</a>
						</dd>
						<dd class="subReceiveMenu retract" style="text-indent: 1em">
							<a href="../statistic/receive_hour.ehtml" target="iframe">${message("admin.statistic.receive.hour")}</a>
						</dd>
						<dd class="subReceiveMenu retract" style="text-indent: 1em">
							<a href="../statistic/receive_date.ehtml" target="iframe">${message("admin.statistic.receive.date")}</a>
						</dd>
						<dd class="subReceiveMenu retract" style="text-indent: 1em">
							<a href="../statistic/receive_month.ehtml" target="iframe">${message("admin.statistic.receive.month")}</a>
						</dd>
					[/@shiro.hasPermission]

					<dd>&nbsp;</dd>
					[#list ["admin:area", "admin:admin", "admin:role"] as permission]
						[@shiro.hasPermission name = permission]
							<dt>${message("admin.main.systemGroup")}</dt>
							[#break /]
						[/@shiro.hasPermission]
					[/#list]
					[@shiro.hasPermission name="admin:area"]
						<dd>
							<a href="../area/list.ehtml" target="iframe">${message("admin.main.area")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:admin"]
						<dd>
							<a href="../admin/list.ehtml" target="iframe">${message("admin.main.admin")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:role"]
						<dd>
							<a href="../role/list.ehtml" target="iframe">${message("admin.main.role")}</a>
						</dd>
					[/@shiro.hasPermission]
				</dl>
			</td>
			<td colspan="2">
				<iframe id="iframe" name="iframe" src="index.ehtml" frameborder="0"></iframe>
			</td>
		</tr>
	</table>
</body>
</html>