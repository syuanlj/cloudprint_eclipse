<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
	body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=dIU3Z5EeUrV4z6mNgzVgMZvQ"></script>
	<title>地图展示</title>
</head>
<body>
	<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
	var map = new BMap.Map("allmap", {enableMapClick:false});
	var myStyleJson=[{
		"featureType": "label",
		"elementType": "all",
		"stylers": {
			"visibility": "off"
		}
	}];
	map.setMapStyle({styleJson: myStyleJson });
	map.centerAndZoom(new BMap.Point(108.780889,34.408508), 5);
	map.disableScrollWheelZoom();

	[#list roots as root]
	[#if points.get(root.name)??]
	var point${root_index} = new BMap.Point(${points.get(root.name)});
	var marker${root_index} = new BMap.Marker(point${root_index});
	map.addOverlay(marker${root_index});
	marker${root_index}.addEventListener("click", zoom${root_index});
	var label${root_index} = new BMap.Label("[#if root.count > 0]<a href='${base}/admin/product/list.ehtml?areaId=${root.id}' style='text-decoration: none; color:#000'>${root.name}${root.count}台</a>[#else]${root.name}${root.count}台[/#if]", {offset:new BMap.Size(${positions.get(root.name)})});
	marker${root_index}.setLabel(label${root_index});

	function zoom${root_index}(){
		[#if root.name == "北京市" || root.name == "天津市" || root.name == "上海市" || root.name == "重庆市"]return false;[/#if]
		window.location.href="sub_view.ehtml?province=${root.name}"
	}
	[/#if]
	[/#list]
</script>