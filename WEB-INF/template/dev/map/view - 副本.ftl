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
	var roots = new Object();  
	roots.put = function (key,value){  
		var s = "roots." + key + ' = "' + value + '";';  
		eval(s);  
	}  
	roots.get = function(key){  
		var v = eval("roots." + key + ";");  
		return v;  
	} 
	[#list roots as root]
		roots.put("${root.name}", "${root.count}");
	[/#list]
	var map = new BMap.Map("allmap", {enableMapClick:false});
	var myStyleJson=[{
		"featureType": "label",
		"elementType": "all",
		"stylers": {
			"visibility": "off"
		}
	}];
	map.setMapStyle({styleJson: myStyleJson });
	map.centerAndZoom(new BMap.Point(108, 38), 5);
	map.disableScrollWheelZoom();

	var prevProvince = null;
	//地图单击事件
	map.addEventListener("click", function(e) {
		var myGeo = new BMap.Geocoder();
		myGeo.getLocation(e.point, function(result) {
			var province = result.addressComponents.province;
			if (province == "" || prevProvince == province) {
				return;
			}
			map.clearOverlays();
			prevProvince = result.addressComponents.province;
			var latlng = e.point;
			var info = new BMap.InfoWindow(prevProvince + " " + roots.get(prevProvince) + "台", {width:220});
			map.openInfoWindow(info, latlng);
			//console.log(prevProvince);
			var bdary = new BMap.Boundary();
			bdary.get(province, function(rs) {
				for (var i = 0; i < rs.boundaries.length; i++) {
					var polygon = new BMap.Polygon(rs.boundaries[i], {
						strokeWeight : 2,
						strokeColor : "green"
					});
					//map.addOverlay(polygon);
				}
			});
		});
	});
</script>