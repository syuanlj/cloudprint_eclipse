/*
 * 
 * 
 * 
 */
package com.nfet.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nfet.entity.Area;
import com.nfet.service.AreaService;

/**
 * Controller - 模板
 * 
 * 
 * 
 */
@Controller("adminMapController")
@RequestMapping("/admin/map")
public class MapController extends BaseController {

	@Resource(name = "areaServiceImpl")
	private AreaService areaService;

	/**
	 * 地图
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(ModelMap model) {

		List<Area> roots = areaService.findRoots();
		Map<String, String> points = new HashMap<String, String>();
		points.put("北京市", "116.39737,39.939502");
		points.put("天津市", "117.133262,39.256321");
		points.put("上海市", "121.36464,31.303465");
		points.put("重庆市", "106.32485,29.895013");
		points.put("河北省", "114.336873,38.21885");
		points.put("山西省", "112.349964,38.044464");
		points.put("辽宁省", "123.241164,41.948112");
		points.put("吉林省", "125.228072,43.894927");
		points.put("黑龙江省", "126.479088,45.985284");
		points.put("江苏省", "118.715429,32.246466");
		points.put("浙江省", "120.040035,30.350837");
		points.put("安徽省", "117.170056,31.99595");
		points.put("福建省", "119.156964,26.182279");
		points.put("江西省", "115.808656,28.774611");
		points.put("山东省", "116.912494,36.812038");
		points.put("河南省", "113.453802,34.895028");
		points.put("湖北省", "114.116105,30.764814");
		points.put("湖南省", "112.800698,28.474291");
		points.put("广东省", "113.233035,23.224606");
		points.put("海南省", "110.179083,19.921006");
		points.put("四川省", "103.924003,30.796585");
		points.put("贵州省", "106.499624,26.844365");
		points.put("云南省", "102.599397,25.248948");
		points.put("陕西省", "108.780889,34.408508");
		points.put("甘肃省", "103.66644,36.218003");
		points.put("青海省", "101.605943,36.752842");
		points.put("西藏自治区", "90.972306,29.838888");
		points.put("广西壮族自治区", "108.265765,23.020403");
		points.put("内蒙古自治区", "111.614073,40.951504");
		points.put("宁夏回族自治区", "106.094884,38.624116");
		points.put("新疆维吾尔自治区", "87.476819,43.894927");
		// points.put("香港特别行政区", "114.1529,22.542716");
		// points.put("澳门特别行政区", "113.417008,22.337477");
		// points.put("台湾省", "121.36464,25.248948");
		Map<String, String> positions = new HashMap<String, String>();
		positions.put("北京市", "-20,-20");
		positions.put("天津市", "20,-20");
		positions.put("上海市", "-20,-20");
		positions.put("重庆市", "-20,-20");
		positions.put("河北省", "-20,-20");
		positions.put("山西省", "-60,-10");
		positions.put("辽宁省", "-20,-20");
		positions.put("吉林省", "-20,-20");
		positions.put("黑龙江省", "-25,-20");
		positions.put("江苏省", "-20,-20");
		positions.put("浙江省", "20,20");
		positions.put("安徽省", "-60,-20");
		positions.put("福建省", "-20,-20");
		positions.put("江西省", "-20,-20");
		positions.put("山东省", "-20,-20");
		positions.put("河南省", "-20,-20");
		positions.put("湖北省", "-20,-20");
		positions.put("湖南省", "-40,-20");
		positions.put("广东省", "20,-20");
		positions.put("海南省", "-20,-20");
		positions.put("四川省", "-60,-20");
		positions.put("贵州省", "-20,-20");
		positions.put("云南省", "-20,-20");
		positions.put("陕西省", "-20,-20");
		positions.put("甘肃省", "-20,-20");
		positions.put("青海省", "-60,-20");
		positions.put("西藏自治区", "-30,-20");
		positions.put("广西壮族自治区", "-40,-20");
		positions.put("内蒙古自治区", "-35,-20");
		positions.put("宁夏回族自治区", "-40,-20");
		positions.put("新疆维吾尔自治区", "-50,-20");
		model.addAttribute("roots", roots);
		model.addAttribute("points", points);
		model.addAttribute("positions", positions);

		return "/admin/map/view";
	}

	/**
	 * 地图
	 */
	@RequestMapping(value = "/sub_view", method = RequestMethod.GET)
	public String subView(String province, ModelMap model) {

		Area root = areaService.findByFullName(province);
		Map<String, String> points = new HashMap<String, String>();
		String center = null;
		if ("安徽省".equals(province)) {
			center = "117.17,31.52";
			points.put("合肥市", "117.17,31.52");
			points.put("安庆市", "117.02,30.31");
			points.put("蚌埠市", "117.21,32.56");
			points.put("亳州市", "115.47,33.52");
			points.put("巢湖市", "117.52,31.36");
			points.put("滁州市", "118.18,32.18");
			points.put("阜阳市", "115.48,32.54");
			points.put("贵池市", "117.28,30.39");
			points.put("淮北市", "116.47,33.57");
			points.put("淮南市", "116.58,32.37");
			points.put("黄山市", "118.18,29.43");
			points.put("界首市", "115.21,33.15");
			points.put("六安市", "116.28,31.44");
			points.put("马鞍山市", "118.28,31.43");
			points.put("明光市", "117.58,32.47");
			points.put("宿州市", "116.58,33.38");
			points.put("天长市", "118.59,32.41");
			points.put("铜陵市", "117.48,30.56");
			points.put("芜湖市", "118.22,31.19");
			points.put("宣州市", "118.44,30.57");
		} else if ("福建省".equals(province)) {
			center = "119.18,26.05";
			points.put("福州市", "119.18,26.05");
			points.put("长乐市", "119.31,25.58");
			points.put("福安市", "119.39,27.06");
			points.put("福清市", "119.23,25.42");
			points.put("建瓯市", "118.20,27.03");
			points.put("建阳市", "118.07,27.21");
			points.put("晋江市", "118.35,24.49");
			points.put("龙海市", "117.48,24.26");
			points.put("龙岩市", "117.01,25.06");
			points.put("南安市", "118.23,24.57");
			points.put("南平市", "118.10,26.38");
			points.put("宁德市", "119.31,26.39");
			points.put("莆田市", "119.01,24.26");
			points.put("泉州市", "118.36,24.56");
			points.put("三明市", "117.36,26.13");
			points.put("邵武市", "117.29,27.20");
			points.put("石狮市", "118.38,24.44");
			points.put("武夷山市", "118.02,27.46");
			points.put("厦门市", "118.06,24.27");
			points.put("永安市", "117.23,25.58");
			points.put("漳平市", "117.24,25.17");
			points.put("漳州市", "117.39,24.31");
		} else if ("甘肃省".equals(province)) {
			center = "103.51,36.04";
			points.put("兰州市", "103.51,36.04");
			points.put("白银市", "104.12,36.33");
			points.put("敦煌市", "94.41,40.08");
			points.put("嘉峪关市", "98.14,39.48");
			points.put("金昌市", "102.10,38.28");
			points.put("酒泉市", "98.31,39.44");
			points.put("临夏市", "103.12,35.37");
			points.put("平凉市", "106.40,35.32");
			points.put("天水市", "105.42,34.37");
			points.put("武威市", "102.39,37.56");
			points.put("西峰市", "107.40,35.45");
			points.put("玉门市", "97.35,39.49");
			points.put("张掖市", "100.26,38.56");
		} else if ("广东省".equals(province)) {
			center = "113.14,23.08";
			points.put("广州市", "113.14,23.08");
			points.put("潮阳市", "116.36,23.16");
			points.put("潮州市", "116.38,23.40");
			points.put("澄海市", "116.46,23.28");
			points.put("从化市", "113.33,23.33");
			points.put("东莞市", "113.45,23.02");
			points.put("恩平市", "112.19,22.12");
			points.put("佛山市", "113.06,23.02");
			points.put("高明市", "112.50,22.53");
			points.put("高要市", "112.26,23.02");
			points.put("高州市", "110.50,21.54");
			points.put("鹤山市", "112.57,22.46");
			points.put("河源市", "114.41,23.43");
			points.put("花都市", "113.12,23.23");
			points.put("化州市", "110.37,21.39");
			points.put("惠阳市", "114.28,22.48");
			points.put("惠州市", "114.22,23.05");
			points.put("江门市", "113.04,22.35");
			points.put("揭阳市", "116.21,22.32");
			points.put("开平市", "112.40,22.22");
			points.put("乐昌市", "113.21,25.09");
			points.put("雷州市", "110.04,20.54");
			points.put("廉江市", "110.17,21.37");
			points.put("连州市", "112.23,24.48");
			points.put("罗定市", "111.33,22.46");
			points.put("茂名市", "110.53,21.40");
			points.put("梅州市", "116.07,24.19");
			points.put("南海市", "113.09,23.01");
			points.put("番禺市", "113.22,22.57");
			points.put("普宁市", "116.10,23.18");
			points.put("清远市", "113.01,23.42");
			points.put("三水市", "112.52,23.10");
			points.put("汕头市", "116.41,23.22");
			points.put("汕尾市", "115.21,22.47");
			points.put("韶关市", "113.37,24.48");
			points.put("深圳市", "114.07,22.33");
			points.put("顺德市", "113.15,22.50");
			points.put("四会市", "112.41,23.21");
			points.put("台山市", "112.48,22.15");
			points.put("吴川市", "110.47,21.26");
			points.put("新会市", "113.01,22.32");
			points.put("兴宁市", "115.43,24.09");
			points.put("阳春市", "111.48,22.10");
			points.put("阳江市", "111.58,21.50");
			points.put("英德市", "113.22,24.10");
			points.put("云浮市", "112.02,22.57");
			points.put("增城市", "113.49,23.18");
			points.put("湛江市", "110.24,21.11");
			points.put("肇庆市", "112.27,23.03");
			points.put("中山市", "113.22,22.31");
			points.put("珠海市", "113.34,22.17");
		} else if ("广西壮族自治区".equals(province)) {
			center = "108.19,22.48";
			points.put("南宁市", "108.19,22.48");
			points.put("北海市", "109.07,21.28");
			points.put("北流市", "110.21,22.42");
			points.put("百色市", "106.36,23.54");
			points.put("防城港市", "108.20,21.37");
			points.put("贵港市", "109.36,23.06");
			points.put("桂林市", "110.17,25.17");
			points.put("桂平市", "110.04,23.22");
			points.put("河池市", "108.03,24.42");
			points.put("合山市", "108.52,23.47");
			points.put("柳州市", "109.24,23.19");
			points.put("赁祥市", "106.44,22.07");
			points.put("钦州市", "108.37,21.57");
			points.put("梧州市", "111.20,23.29");
			points.put("玉林市", "110.09,22.38");
			points.put("宜州市", "108.40,24.28");
		} else if ("贵州省".equals(province)) {
			center = "106.42,26.35";
			points.put("贵阳市", "106.42,26.35");
			points.put("安顺市", "105.55,26.14");
			points.put("毕节市", "105.18,27.18");
			points.put("赤水市", "105.42,28.34");
			points.put("都匀市", "107.31,26.15");
			points.put("凯里市", "107.58,26.35");
			points.put("六盘水市", "104.50,26.35");
			points.put("清镇市", "106.27,26.33");
			points.put("铜仁市", "109.12,27.43");
			points.put("兴义市", "104.53,25.05");
			points.put("遵义市", "106.55,27.42");
		} else if ("海南省".equals(province)) {
			center = "110.20,20.02";
			points.put("海口市", "110.20,20.02");
			points.put("儋州市", "109.34,19.31");
			points.put("琼海市", "110.28,19.14");
			points.put("琼山市", "110.21,19.59");
			points.put("三亚市", "109.31,18.14");
			points.put("通什市", "109.31,18.46");
		} else if ("河北省".equals(province)) {
			center = "114.30,38.02";
			points.put("石家庄市", "114.30,38.02");
			points.put("安国市", "115.20,38.24");
			points.put("保定市", "115.30,38.51");
			points.put("霸州市", "116.24,39.06");
			points.put("泊头市", "116.34,38.04");
			points.put("沧州市", "116.52,38.18");
			points.put("承德市", "117.57,40.59");
			points.put("定州市", "115.00,38.30");
			points.put("丰南市", "118.06,39.34");
			points.put("高碑店市", "115.51,39.20");
			points.put("蒿城市", "114.50,38.02");
			points.put("邯郸市", "114.28,36.36");
			points.put("河间市", "116.05,38.26");
			points.put("衡水市", "115.42,37.44");
			points.put("黄骅市", "117.21,38.21");
			points.put("晋州市", "115.02,38.02");
			points.put("冀州市", "115.33,37.34");
			points.put("廓坊市", "116.42,39.31");
			points.put("鹿泉市", "114.19,38.04");
			points.put("南宫市", "115.23,37.22");
			points.put("秦皇岛市", "119.35,39.55");
			points.put("任丘市", "116.07,38.42");
			points.put("三河市", "117.04,39.58");
			points.put("沙河市", "114.30,36.51");
			points.put("深州市", "115.32,38.01");
			points.put("唐山市", "118.11,39.36");
			points.put("武安市", "114.11,36.42");
			points.put("邢台市", "114.30,37.04");
			points.put("辛集市", "115.12,37.54");
			points.put("新乐市", "114.41,38.20");
			points.put("张家口市", "114.53,40.48");
			points.put("涿州市", "115.59,39.29");
			points.put("遵化市", "117.58,40.11");
		} else if ("河南省".equals(province)) {
			center = "113.40,34.46";
			points.put("郑州市", "113.40,34.46");
			points.put("安阳市", "114.21,36.06");
			points.put("长葛市", "113.47,34.12");
			points.put("登封市", "113.02,34.27");
			points.put("邓州市", "112.05,32.42");
			points.put("巩义市", "112.58,34.46");
			points.put("鹤壁市", "114.11,35.54");
			points.put("辉县市", "113.47,35.27");
			points.put("焦作市", "113.12,35.14");
			points.put("济源市", "112.35,35.04");
			points.put("开封市", "114.21,34.47");
			points.put("灵宝市", "110.52,34.31");
			points.put("林州市", "113.49,36.03");
			points.put("漯河市", "114.02,33.33");
			points.put("洛阳市", "112.27,34.41");
			points.put("南阳市", "112.32,33.00");
			points.put("平顶山市", "113.17,33.44");
			points.put("濮阳市", "115.01,35.44");
			points.put("沁阳市", "112.57,35.05");
			points.put("汝州市", "112.50,34.09");
			points.put("三门峡市", "111.12,34.47");
			points.put("商丘市", "115.38,34.26");
			points.put("卫辉市", "114.03,35.24");
			points.put("舞钢市", "113.30,33.17");
			points.put("项城市", "114.54,33.26");
			points.put("荥阳市", "113.21,34.46");
			points.put("新密市", "113.22,34.31");
			points.put("新乡市", "113.52,35.18");
			points.put("信阳市", "114.04,32.07");
			points.put("新郑市", "113.43,34.24");
			points.put("许昌市", "113.49,34.01");
			points.put("偃师市", "112.47,34.43");
			points.put("义马市", "111.55,34.43");
			points.put("禹州市", "113.28,34.09");
			points.put("周口市", "114.38,33.37");
			points.put("驻马店市", "114.01,32.58");
		} else if ("黑龙江省".equals(province)) {
			center = "126.36,45.44";
			points.put("哈尔滨市", "126.36,45.44");
			points.put("阿城市", "126.58,45.32");
			points.put("安达市", "125.18,46.24");
			points.put("北安市", "126.31,48.15");
			points.put("大庆市", "125.01,46.36");
			points.put("富锦市", "132.02,47.15");
			points.put("海林市", "129.21,44.35");
			points.put("海伦市", "126.57,47.28");
			points.put("鹤岗市", "130.16,47.20");
			points.put("黑河市", "127.29,50.14");
			points.put("佳木斯市", "130.22,46.47");
			points.put("鸡西市", "130.57,45.17");
			points.put("密山市", "131.50,45.32");
			points.put("牡丹江市", "129.36,44.35");
			points.put("讷河市", "124.51,48.29");
			points.put("宁安市", "129.28,44.21");
			points.put("齐齐哈尔市", "123.57,47.20");
			points.put("七台河市", "130.49,45.48");
			points.put("双城市", "126.15,45.22");
			points.put("尚志市", "127.55,45.14");
			points.put("双鸭山市", "131.11,46.38");
			points.put("绥芬河市", "131.11,44.25");
			points.put("绥化市", "126.59,46.38");
			points.put("铁力市", "128.01,46.59");
			points.put("同江市", "132.30,47.39");
			points.put("五常市", "127.11,44.55");
			points.put("五大连池市", "126.07,48.38");
			points.put("伊春市", "128.56,47.42");
			points.put("肇东市", "125.58,46.04");
		} else if ("湖北省".equals(province)) {
			center = "114.17,30.35";
			points.put("武汉市", "114.17,30.35");
			points.put("安陆市", "113.41,31.15");
			points.put("当阳市", "111.47,30.50");
			points.put("丹江口市", "108.30,32.33");
			points.put("大冶市", "114.58,30.06");
			points.put("恩施市", "109.29,30.16");
			points.put("鄂州市", "114.52,30.23");
			points.put("广水市", "113.48,31.37");
			points.put("洪湖市", "113.27,29.48");
			points.put("黄石市", "115.06,30.12");
			points.put("黄州市", "114.52,30.27");
			points.put("荆门市", "112.12,31.02");
			points.put("荆沙市", "112.16,30.18");
			points.put("老河口市", "111.40,32.23");
			points.put("利川市", "108.56,30.18");
			points.put("麻城市", "115.01,31.10");
			points.put("浦圻市", "113.51,29.42");
			points.put("潜江市", "112.53,30.26");
			points.put("石首市", "112.24,29.43");
			points.put("十堰市", "110.47,32.40");
			points.put("随州市", "113.22,31.42");
			points.put("天门市", "113.10,60.39");
			points.put("武穴市", "115.33,29.51");
			points.put("襄樊市", "112.08,32.02");
			points.put("咸宁市", "114.17,29.53");
			points.put("仙桃市", "113.27,30.22");
			points.put("孝感市", "113.54,30.56");
			points.put("宜昌市", "111.17,30.42");
			points.put("宜城市", "112.15,31.42");
			points.put("应城市", "113.33,30.57");
			points.put("枣阳市", "112.44,32.07");
			points.put("枝城市", "111.27,30.23");
			points.put("钟祥市", "112.34,31.10");
		} else if ("湖南省".equals(province)) {
			center = "112.59,28.12";
			points.put("长沙市", "112.59,28.12");
			points.put("常德市", "111.51,29.02");
			points.put("郴州市", "113.02,25.46");
			points.put("衡阳市", "112.37,26.53");
			points.put("洪江市", "109.59,27.07");
			points.put("怀化市", "109.58,27.33");
			points.put("津市市", "111.52,29.38");
			points.put("吉首市", "109.43,28.18");
			points.put("耒阳市", "112.51,26.24");
			points.put("冷水江市", "111.26,27.42");
			points.put("冷水滩市", "111.35,26.26");
			points.put("涟源市", "111.41,27.41");
			points.put("醴陵市", "113.30,27.40");
			points.put("临湘市", "113.27,29.29");
			points.put("浏阳市", "113.37,28.09");
			points.put("娄底市", "111.59,27.44");
			points.put("汨罗市", "113.03,28.49");
			points.put("韶山市", "112.29,27.54");
			points.put("邵阳市", "111.28,27.14");
			points.put("武冈市", "110.37,26.43");
			points.put("湘潭市", "112.53,27.52");
			points.put("湘乡市", "112.31,27.44");
			points.put("益阳市", "112.20,28.36");
			points.put("永州市", "111.37,26.13");
			points.put("沅江市", "112.22,28.50");
			points.put("岳阳市", "113.06,29.22");
			points.put("张家界市", "110.29,29.08");
			points.put("株洲市", "113.09,27.51");
			points.put("资兴市", "113.13,25.58");
		} else if ("吉林省".equals(province)) {
			center = "125.19,43.54";
			points.put("长春市", "125.19,43.54");
			points.put("白城市", "122.50,45.38");
			points.put("白山市", "126.26,41.56");
			points.put("大安市", "124.18,45.30");
			points.put("德惠市", "125.42,44.32");
			points.put("敦化市", "128.13,43.22");
			points.put("公主岭市", "124.49,43.31");
			points.put("和龙市", "129.00,42.32");
			points.put("桦甸市", "126.44,42.58");
			points.put("珲春市", "130.22,42.52");
			points.put("集安市", "126.11,41.08");
			points.put("蛟河市", "127.21,43.42");
			points.put("吉林市", "126.33,43.52");
			points.put("九台市", "125.51,44.09");
			points.put("辽源市", "125.09,42.54");
			points.put("临江市", "126.53,41.49");
			points.put("龙井市", "129.26,42.46");
			points.put("梅河口市", "125.40,42.32");
			points.put("舒兰市", "126.57,44.24");
			points.put("四平市", "124.22,43.10");
			points.put("松原市", "124.49,45.11");
			points.put("洮南市", "122.47,45.20");
			points.put("通化市", "125.56,41.43");
			points.put("图们市", "129.51,42.57");
			points.put("延吉市", "129.30,42.54");
			points.put("愉树市", "126.32,44.49");
		} else if ("江苏省".equals(province)) {
			center = "118.46,32.03";
			points.put("南京市", "118.46,32.03");
			points.put("常熟市", "120.43,31.39");
			points.put("常州市", "119.58,31.47");
			points.put("丹阳市", "119.32,32.00");
			points.put("东台市", "120.19,32.51");
			points.put("高邮市", "119.27,32.47");
			points.put("海门市", "121.09,31.53");
			points.put("淮安市", "119.09,33.30");
			points.put("淮阴市", "119.02,33.36");
			points.put("江都市", "119.32,32.26");
			points.put("姜堰市", "120.08,32.34");
			points.put("江阴市", "120.17,31.54");
			points.put("靖江市", "120.17,32.02");
			points.put("金坛市", "119.33,31.46");
			points.put("昆山市", "120.57,31.23");
			points.put("连去港市", "119.10,34.36");
			points.put("溧阳市", "119.29,31.26");
			points.put("南通市", "120.51,32.01");
			points.put("邳州市", "117.59,34.19");
			points.put("启乐市", "121.39,31.48");
			points.put("如皋市", "120.33,32.23");
			points.put("宿迁市", "118.18,33.58");
			points.put("苏州市", "120.37,31.19");
			points.put("太仓市", "121.06,31.27");
			points.put("泰兴市", "120.01,32.10");
			points.put("泰州市", "119.54,32.30");
			points.put("通州市", "121.03,32.05");
			points.put("吴江市", "120.39,31.10");
			points.put("无锡市", "120.18,31.34");
			points.put("兴化市", "119.50,32.56");
			points.put("新沂市", "118.20,34.22");
			points.put("徐州市", "117.11,34.15");
			points.put("盐在市", "120.08,33.22");
			points.put("扬中市", "119.49,32.14");
			points.put("扬州市", "119.26,32.23");
			points.put("宜兴市", "119.49,31.21");
			points.put("仪征市", "119.10,32.16");
			points.put("张家港市", "120.32,31.52");
			points.put("镇江市", "119.27,32.11");
		} else if ("江西省".equals(province)) {
			center = "115.55,28.40";
			points.put("南昌市", "115.55,28.40");
			points.put("德兴市", "117.35,28.57");
			points.put("丰城市", "115.48,28.12");
			points.put("赣州市", "114.56,28.52");
			points.put("高安市", "115.22,28.25");
			points.put("吉安市", "114.58,27.07");
			points.put("景德镇市", "117.13,29.17");
			points.put("井冈山市", "114.10,26.34");
			points.put("九江市", "115.58,29.43");
			points.put("乐平市", "117.08,28.58");
			points.put("临川市", "116.21,27.59");
			points.put("萍乡市", "113.50,27.37");
			points.put("瑞昌市", "115.38,29.40");
			points.put("瑞金市", "116.01,25.53");
			points.put("上饶市", "117.58,25.27");
			points.put("新余市", "114.56,27.48");
			points.put("宜春市", "114.23,27.47");
			points.put("鹰潭市", "117.03,28.14");
			points.put("樟树市", "115.32,28.03");
		} else if ("辽宁省".equals(province)) {
			center = "123.25,41.48";
			points.put("沈阳市", "123.25,41.48");
			points.put("鞍山市", "123.00,41.07");
			points.put("北票市", "120.47,41.48");
			points.put("本溪市", "123.46,41.18");
			points.put("朝阳市", "120.27,41.34");
			points.put("大连市", "121.36,38.55");
			points.put("丹东市", "124.22,40.08");
			points.put("大石桥市", "122.31,40.37");
			points.put("东港市", "124.08,39.53");
			points.put("凤城市", "124.02,40.28");
			points.put("抚顺市", "123.54,41.51");
			points.put("阜新市", "121.39,42.01");
			points.put("盖州市", "122.21,40.24");
			points.put("海城市", "122.43,40.51");
			points.put("葫芦岛市", "120.51,40.45");
			points.put("锦州市", "121.09,41.07");
			points.put("开原市", "124.02,42.32");
			points.put("辽阳市", "123.12,41.16");
			points.put("凌海市", "121.21,41.10");
			points.put("凌源市", "119.22,41.14");
			points.put("盘锦市", "122.03,41.07");
			points.put("普兰店市", "121.58,39.23");
			points.put("铁法市", "123.32,42.28");
			points.put("铁岭市", "123.51,42.18");
			points.put("瓦房店市", "122.00,39.37");
			points.put("兴城市", "120.41,40.37");
			points.put("新民市", "122.49,41.59");
			points.put("营口市", "122.13,40.39");
			points.put("庄河市", "122.58,39.41");
		} else if ("内蒙古自治区".equals(province)) {
			center = "111.41,40.48";
			points.put("呼和浩特市", "111.41,40.48");
			points.put("包头市", "109.49,40.39");
			points.put("赤峰市", "118.58,42.17");
			points.put("东胜市", "109.59,39.48");
			points.put("二连浩特市", "111.58,43.38");
			points.put("额尔古纳市", "120.11,50.13");
			points.put("丰镇市", "113.09,40.27");
			points.put("根河市", "121.29,50.48");
			points.put("海拉尔市", "119.39,49.12");
			points.put("霍林郭勒市", "119.38,45.32");
			points.put("集宁市", "113.06,41.02");
			points.put("临河市", "107.22,40.46");
			points.put("满洲里市", "117.23,49.35");
			points.put("通辽市", "122.16,43.37");
			points.put("乌兰浩特市", "122.03,46.03");
			points.put("乌海市", "106.48,39.40");
			points.put("锡林浩特市", "116.03,43.57");
			points.put("牙克石市", "120.40,49.17");
			points.put("扎兰屯市", "122.47,48.00");
		} else if ("宁夏回族自治区".equals(province)) {
			center = "106.16,38.27";
			points.put("银川市", "106.16,38.27");
			points.put("青铜峡市", "105.59,37.56");
			points.put("石嘴山市", "106.22,39.02");
			points.put("吴忠市", "106.11,37.59");
		} else if ("青海省".equals(province)) {
			center = "101.48,36.38";
			points.put("西宁市", "101.48,36.38");
			points.put("德令哈市", "97.23,37.22");
			points.put("格尔木市", "94.55,36.26");
		} else if ("山东省".equals(province)) {
			center = "117.00,36.40";
			points.put("济南市", "117.00,36.40");
			points.put("安丘市", "119.12,36.25");
			points.put("滨州市", "118.02,37.22");
			points.put("昌邑市", "119.24,39.52");
			points.put("德州市", "116.17,37.26");
			points.put("东营市", "118.30,37.27");
			points.put("肥城市", "116.46,36.14");
			points.put("高密市", "119.44,36.22");
			points.put("菏泽市", "115.26,35.14");
			points.put("胶南市", "119.58,35.53");
			points.put("胶州市", "120.00,36.17");
			points.put("即墨市", "120.28,36.22");
			points.put("济宁市", "116.33,35.23");
			points.put("莱芜市", "117.40,36.12");
			points.put("莱西市", "120.31,36.52");
			points.put("莱阳市", "120.42,36.58");
			points.put("莱州市", "119.57,37.10");
			points.put("乐陵市", "117.12,37.44");
			points.put("聊城市", "115.57,36.26");
			points.put("临清市", "115.42,36.51");
			points.put("临沂市", "118.20,35.03");
			points.put("龙口市", "120.21,37.39");
			points.put("蓬莱市", "120.45,37.48");
			points.put("平度市", "119.58,36.47");
			points.put("青岛市", "120.18,36.03");
			points.put("青州市", "118.28,36.42");
			points.put("曲阜市", "116.58,35.36");
			points.put("日照市", "119.32,35.23");
			points.put("荣成市", "122.25,37.10");
			points.put("乳山市", "121.31,36.54");
			points.put("寿光市", "118.44,36.53");
			points.put("泰安市", "117.08,36.11");
			points.put("滕州市", "117.09,35.06");
			points.put("潍坊市", "119.06,36.43");
			points.put("威海市", "122.07,37.31");
			points.put("文登市", "122.03,37.12");
			points.put("新泰市", "117.45,35.54");
			points.put("烟台市", "121.24,37.32");
			points.put("兖州市", "116.49,35.32");
			points.put("禹城市", "116.39,36.56");
			points.put("枣庄市", "117.33,34.52");
			points.put("章丘市", "117.32,36.43");
			points.put("招远市", "120.23,37.21");
			points.put("诸城市", "119.24,35.59");
			points.put("淄博市", "118.03,36.48");
			points.put("邹城市", "116.58,35.24");			
		} else if ("山西省".equals(province)) {
			center = "112.33,37.54";
			points.put("太原市", "112.33,37.54");
			points.put("长治市", "113.06,36.11");
			points.put("大同市", "113.17,40.06");
			points.put("高平市", "112.55,35.48");
			points.put("古交市", "112.09,37.54");
			points.put("河津市", "110.41,35.35");
			points.put("侯马市", "111.21,35.37");
			points.put("霍州市", "111.42,36.34");
			points.put("介休市", "111.55,37.02");
			points.put("晋城市", "112.51,35.30");
			points.put("临汾市", "111.31,36.05");
			points.put("潞城市", "113.14,36.21");
			points.put("朔州市", "112.26,39.19");
			points.put("孝义市", "111.48,37.08");
			points.put("忻州市", "112.43,38.24");
			points.put("阳泉市", "113.34,37.51");
			points.put("永济市", "110.27,34.52");
			points.put("原平市", "112.42,38.43");
			points.put("榆次市", "112.43,37.41");
			points.put("运城市", "110.59,35.02");			
		} else if ("陕西省".equals(province)) {
			center = "108.57,34.17";
			points.put("西安市", "108.57,34.17");
			points.put("安康市", "109.01,32.41");
			points.put("宝鸡市", "107.09,34.22");
			points.put("韩城市", "110.27,35.28");
			points.put("汉中市", "107.01,33.04");
			points.put("华阴市", "110.05,34.34");
			points.put("商州市", "109.57,33.52");
			points.put("铜川市", "109.07,35.06");
			points.put("渭南市", "109.30,34.30");
			points.put("咸阳市", "108.43,34.20");
			points.put("兴平市", "108.29,34.18");
			points.put("延安市", "109.28,36.35");
			points.put("榆林市", "109.47,38.18");			
		} else if ("四川省".equals(province)) {
			center = "104.04,30.40";
			points.put("成都市", "104.04,30.40");
			points.put("巴中市", "106.43,31.51");
			points.put("崇州市", "103.40,30.39");
			points.put("达川市", "107.29,31.14");
			points.put("德阳市", "104.22,31.09");
			points.put("都江堰市", "103.37,31.01");
			points.put("峨眉山市", "103.29,29.36");
			points.put("涪陵市", "107.22,29.42");
			points.put("广汉市", "104.15,30.58");
			points.put("广元市", "105.51,32.28");
			points.put("华蓥市", "106.44,30.26");
			points.put("简阳市", "104.32,30.24");
			points.put("江油市", "104.42,31.48");
			points.put("阆中市", "105.58,31.36");
			points.put("乐山市", "103.44,29.36");
			points.put("泸州市", "105.24,28.54");
			points.put("绵阳市", "104.42,31.30");
			points.put("南充市", "106.04,30.49");
			points.put("内江市", "105.02,29.36");
			points.put("攀枝花市", "101.43,26.34");
			points.put("彭州市", "103.57,30.59");
			points.put("邛崃市", "103.28,30.26");
			points.put("遂宁市", "105.33,30.31");
			points.put("万县市", "108.21,30.50");
			points.put("万源市", "108.03,32.03");
			points.put("西昌市", "102.16,27.54");
			points.put("雅安市", "102.59,29.59");
			points.put("宜宾市", "104.34,28.47");
			points.put("自贡市", "104.46,29.23");
			points.put("资阳市", "104.38,30.09");
		} else if ("西藏自治区".equals(province)) {
			center = "91.08,29.39";
			points.put("拉萨市", "91.08,29.39");
			points.put("日喀则市", "88.51,29.16");
		} else if ("新疆维吾尔自治区".equals(province)) {
			center = "87.36,43.45";
			points.put("乌鲁木齐市", "87.36,43.45");
			points.put("阿克苏市", "80.19,41.09");
			points.put("阿勒泰市", "88.12,47.50");
			points.put("阿图什市", "76.08,39.42");
			points.put("博乐市", "82.08,44.57");
			points.put("昌吉市", "87.18,44.02");
			points.put("阜康市", "87.58,44.09");
			points.put("哈密市", "93.28,42.50");
			points.put("和田市", "79.55,37.09");
			points.put("克拉玛依市", "84.51,45.36");
			points.put("喀什市", "75.59,39.30");
			points.put("库尔勒市", "86.07,41.46");
			points.put("奎屯市", "84.56,44.27");
			points.put("石河子市", "86.00,44.18");
			points.put("塔城市", "82.59,46.46");
			points.put("吐鲁番市", "89.11,42.54");
			points.put("伊宁市", "81.20,43.55");
		} else if ("云南省".equals(province)) {
			center = "102.42,25.04";			
			points.put("昆明市", "102.42,25.04");
			points.put("保山市", "99.10,25.08");
			points.put("楚雄市", "101.32,25.01");
			points.put("大理市", "100.13,25.34");
			points.put("东川市", "103.12,26.06");
			points.put("个旧市", "103.09,23.21");
			points.put("景洪市", "100.48,22.01");
			points.put("开远市", "103.13,23.43");
			points.put("曲靖市", "103.48,25.30");
			points.put("瑞丽市", "97.50,24.00");
			points.put("思茅市", "100.58,22.48");
			points.put("畹町市", "98.04,24.06");
			points.put("宣威市", "104.06,26.13");
			points.put("玉溪市", "102.32,24.22");
			points.put("昭通市", "103.42,27.20");			
		} else if ("浙江省".equals(province)) {
			center = "120.10,30.16";			
			points.put("杭州市", "120.10,30.16");
			points.put("慈溪市", "121.15,30.11");
			points.put("东阳市", "120.14,29.16");
			points.put("奉化市", "121.24,29.39");
			points.put("富阳市", "119.57,30.03");
			points.put("海宁市", "120.42,30.32");
			points.put("湖州市", "120.06,30.52");
			points.put("建德市", "119.16,29.29");
			points.put("江山市", "118.37,28.45");
			points.put("嘉兴市", "120.45,30.46");
			points.put("金华市", "119.39,29.07");
			points.put("兰溪市", "119.28,29.12");
			points.put("临海市", "121.08,28.51");
			points.put("丽水市", "119.54,28.27");
			points.put("龙泉市", "119.08,28.04");
			points.put("宁波市", "121.33,29.52");
			points.put("平湖市", "121.01,30.42");
			points.put("衢州市", "118.52,28.58");
			points.put("瑞安市", "120.38,27.48");
			points.put("上虞市", "120.52,30.01");
			points.put("绍兴市", "120.34,30.00");
			points.put("台州市", "121.27,28.41");
			points.put("桐乡市", "120.32,30.38");
			points.put("温岭市", "121.21,28.22");
			points.put("温州市", "120.39,28.01");
			points.put("萧山市", "120.16,30.09");
			points.put("义乌市", "120.04,29.18");
			points.put("乐清市", "120.58,28.08");
			points.put("余杭市", "120.18,30.26");
			points.put("余姚市", "121.10,30.02");
			points.put("永康市", "120.01,29.54");
			points.put("舟山市", "122.06,30.01");
			points.put("诸暨市", "120.14,29.43");
		}
		model.addAttribute("roots", root.getChildren());
		model.addAttribute("points", points);
		model.addAttribute("center", center);

		return "/admin/map/sub_view";
	}
}