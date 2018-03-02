<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.statistic.print")}</title>


<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/fusionCharts/FusionCharts.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $beginDate = $("#beginDate");
	var $endDate = $("#endDate");

	[#assign dateFormat = "yyyy-MM" /]
	[@compress single_line = true]
		var printedCountData = '<graph caption="${product.sn}${message("admin.statistic.product.month")}" subcaption="${beginDate?string(dateFormat)} ~ ${endDate?string(dateFormat)}" decimalPrecision="0" formatNumberScale="0" chartRightMargin="30" showValues="1" yAxisMaxValue="100" baseFontSize="12">
		[#list printedCountMap.keySet() as key]
			<set name="${key?string(dateFormat)}" value="${printedCountMap.get(key)}" hoverText="${key?string(dateFormat)}" \/>
		[/#list]
		<\/graph>';
	[/@compress]

	var printedCountChart = new FusionCharts("${base}/resources/admin/fusionCharts/Charts/Column2D.swf", "printedCountChart", "1177", "360");
	printedCountChart.addParam("wmode", "Opaque");
	printedCountChart.setDataXML(printedCountData);
	printedCountChart.render("printedCountChart");
	
	// 表单验证
	$inputForm.validate({
		rules: {
			beginDate: "required",
			endDate: "required"
		}
	});
});
</script>
</head>
<body>
	<div class="path">
		${message("admin.statistic.print")}
	</div>

	<form id="inputForm" action="product_month.ehtml" method="get">
		<input type="hidden" name="id" value="${product.id}" />
		<table class="input tabContent">
			<tr>
				<th>
					${message("admin.statistic.order.printed")}:
				</th>
				<td>
					${printedOrderCount}
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.statistic.order.sent")}:
				</th>
				<td>
					${sentOrderCount}
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.statistic.order.received")}:
				</th>
				<td>
					<a href="${base}/admin/order/list.ehtml?orderStatus=received&productSn=${product.sn}">${receivedOrderCount}</a>
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.statistic.order.expired")}:
				</th>
				<td>
					${expiredOrderCount}
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.statistic.orderStatus")}:
				</th>
				<td>
					<select id="orderStatus" name="orderStatus">
						[#list orderStatuses as status]
							<option value="${status}"[#if orderStatus == status] selected="selected"[/#if]>${message("admin.order.status." + status)}</option>
						[/#list]
					</select>
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.statistic.beginDate")}:
				</th>
				<td>
					<input type="text" id="beginDate" name="beginDate" class="text Wdate" value="${beginDate?string(dateFormat)}" autocomplete="off" onfocus="WdatePicker({dateFmt: 'yyyy-MM', maxDate: '#F{$dp.$D(\'endDate\')}'});" />${message("admin.statistic.month.notice")}
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.statistic.endDate")}:
				</th>
				<td>
					<input type="text" id="endDate" name="endDate" class="text Wdate" value="${endDate?string(dateFormat)}" autocomplete="off" onfocus="WdatePicker({dateFmt: 'yyyy-MM', minDate: '#F{$dp.$D(\'beginDate\')}'});" />
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div id="printedCountChart" style="height: 380px;"></div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>