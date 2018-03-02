<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.index.title")}</title>


<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.input .powered {
	font-size: 11px;
	text-align: right;
	color: #cccccc;
}
</style>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/fusionCharts/FusionCharts.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {

	[@compress single_line = true]
		var productCountData = '<graph caption="${message("admin.statistic.product.normal")}" subcaption="" decimalPrecision="0" formatNumberScale="0" chartRightMargin="30" showValues="1" baseFontSize="12">
		[#list productCountMap.keySet() as key]
			<set name="${message("admin.statistic.product.${key}")}" value="${productCountMap.get(key)}" hoverText="${message("admin.statistic.product.${key}")}" \/>
		[/#list]
		<\/graph>';
	[/@compress]

	var productCountChart = new FusionCharts("${base}/resources/admin/fusionCharts/Charts/Pie2D.swf", "productCountChart", "1177", "360");
	productCountChart.addParam("wmode", "Opaque");
	productCountChart.setDataXML(productCountData);
	productCountChart.render("productCountChart");
});
</script></head>
<body>
	<div class="path">
		${message("admin.index.title")}
	</div>
	<table class="input">
		<tr>
			<td colspan="4">
				&nbsp;
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.index.orderCount")}:
			</th>
			<td>
				<a href="${base}/admin/order/list.ehtml">${orderCount}</a>
			</td>
			<th>
				&nbsp;
			</th>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.index.printedOrderCount")}:
			</th>
			<td>
				<a href="${base}/admin/order/list.ehtml?orderStatus=printed">${printedOrderCount}</a>
			</td>
			<th>
				${message("admin.index.receivedOrderCount")}:
			</th>
			<td>
				<a href="${base}/admin/order/list.ehtml?orderStatus=received">${reveivedOrderCount}</a>
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.index.sentOrderCount")}:
			</th>
			<td>
				<a href="${base}/admin/order/list.ehtml?orderStatus=sent">${sentOrderCount}</a>
			</td>
			<th>
				${message("admin.index.expiredOrderCount")}:
			</th>
			<td>
				<a href="${base}/admin/order/list.ehtml?hasExpired=true">${expiredOrderCount}</a>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				&nbsp;
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.index.productCount")}:
			</th>
			<td>
				<a href="${base}/admin/product/list.ehtml">${productCount}</a>
			</td>
			<th>
				&nbsp;
			</th>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.index.marketableProductCount")}:
			</th>
			<td>
				<a href="${base}/admin/product/list.ehtml?isMarketable=true">${marketableProductCount}</a>
			</td>
			<th>
				${message("admin.index.notMarketableProductCount")}:
			</th>
			<td>
				<a href="${base}/admin/product/list.ehtml?isMarketable=false">${notMarketableProductCount}</a>
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.index.onlineProductCount")}:
			</th>
			<td>
				<a href="${base}/admin/product/list.ehtml?isOnline=true">${onlineProductCount}</a>
			</td>
			<th>
				${message("admin.index.offlineProductCount")}:
			</th>
			<td>
				<a href="${base}/admin/product/list.ehtml?isOnline=false">${offlineProductCount}</a>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<div id="productCountChart" style="height: 100px;"></div>
			</td>
		</tr>
	</table>
</body>
</html>