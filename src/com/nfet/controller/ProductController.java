/*
 * 
 * 
 * 
 */
package com.nfet.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nfet.PrintProtocol;
import com.nfet.controller.admin.BaseController;
import com.nfet.entity.Area;
import com.nfet.entity.Product;
import com.nfet.entity.ProductCategory;
import com.nfet.service.AreaService;
import com.nfet.service.ProductCategoryService;
import com.nfet.service.ProductService;

/**
 * Controller - 商品
 * 
 * 
 * 
 */
@Controller("productController")
@RequestMapping("/product")
public class ProductController extends BaseController {

	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;

	/**
	 * 注册
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> save(String deviceid, String devicetype, String mqttname, String mqttpwd, String cloudname, String cloudpwd, String manutime) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (productService.findBySn(deviceid) != null) {
			result.put("resstate", "3");
			return result;
		}
		Product product = new Product();
		ProductCategory productCategory = productCategoryService.findByName(devicetype);
		product.setSn(deviceid);
		product.setProductCategory(productCategory);
		product.setUsername(cloudname);
		product.setPassword(cloudpwd);
		product.setMqttUsername(mqttname);
		product.setMqttPassword(mqttpwd);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			product.setManufacture(sdf.parse(manutime));
		} catch (ParseException e) {
			e.printStackTrace();
			result.put("resstate", "2");
			return result;
		}
		product.setIsList(true);
		product.setIsMarketable(false);
		product.setIsOnline(false);
		product.setStatus(PrintProtocol.PRINTER_STATUS_NORMAL);
		productService.save(product);

		result.put("resstate", "1");
		return result;
	}

	/**
	 * 授权
	 */
	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> authorize(String deviceid, String cloudname, String cloudpwd, String latitude, String longitude, String province, String city, String district, String time) {
		Map<String, Object> result = new HashMap<String, Object>();

		Product product = productService.findBySn(deviceid);
		if (product == null) {
			result.put("resstate", "2");
			return result;
		}
		if (cloudname == null || "".equals(cloudname) || !cloudname.equals(product.getUsername())) {
			result.put("resstate", "2");
			return result;
		}
		if (cloudpwd == null || "".equals(cloudpwd) || !cloudpwd.equals(product.getPassword())) {
			result.put("resstate", "2");
			return result;
		}

		String fullName = province + city + district;
		Area area = areaService.findByFullName(fullName.replaceFirst("null", ""));
		if (area == null) {
			result.put("resstate", "2");
			return result;
		}
		product.setLatitude(latitude);
		product.setLongitude(longitude);
		product.setArea(area);
		product.setIsMarketable(true);
		productService.update(product);

		result.put("resstate", "1");
		return result;
	}

	/**
	 * 获取云端用户名+密码
	 */
	@RequestMapping(value = "/getAccount", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> getAccount(String deviceid) {
		Map<String, Object> result = new HashMap<String, Object>();

		Product product = productService.findBySn(deviceid);
		if (product == null) {
			result.put("resstate", "2");
			return result;
		}

		result.put("resstate", "1");
		result.put("cloudname", product.getUsername());
		result.put("cloudpwd", product.getPassword());
		return result;
	}

	/**
	 * 获取打印机状态
	 * 
	 */
	@RequestMapping(value = "/checkProduct", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> checkProduct(String deviceid, String cloudname, String cloudpwd) {
		Map<String, Object> result = new HashMap<String, Object>();

		Product product = productService.findBySn(deviceid);
		if (product == null) {
			result.put("resstate", "4");
			return result;
		}
		if (cloudname == null || "".equals(cloudname) || !cloudname.equals(product.getUsername())) {
			result.put("resstate", "5");
			return result;
		}
		if (cloudpwd == null || "".equals(cloudpwd) || !cloudpwd.equals(product.getPassword())) {
			result.put("resstate", "5");
			return result;
		}
		if (!product.getIsOnline()) {
			result.put("resstate", "3");
			return result;			
		}
		if (product.getStatus() == PrintProtocol.PRINTER_STATUS_NORMAL || product.getStatus() == PrintProtocol.PRINTER_STATUS_BUSY) {
			result.put("resstate", "1");
			return result;			
		}
		if (product.getStatus() == PrintProtocol.PRINTER_STATUS_ABNORMAL) {
			result.put("resstate", "2");
			return result;			
		}
		result.put("resstate", "7");
		return result;			
	}
}