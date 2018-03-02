/*
 * 
 * 
 * 
 */
package com.nfet.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import com.nfet.entity.Area;
import com.nfet.entity.Order;
import com.nfet.service.AreaService;
import com.nfet.service.OrderService;
import com.nfet.service.ProductService;
import com.nfet.thirdpart.api.OAuthClientHelper;

/**
 * Controller - 共用
 * 
 * 
 * 
 */
@Controller("adminCommonController")
@RequestMapping("/admin/common")
public class CommonController implements ServletContextAware {

	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;

	/** servletContext */
	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * 主页
	 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main() {
		return "/admin/common/main";
	}
	
	/**
	 * 主页
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		return "/admin/common/main";
	}

	/**
	 * 首页
	 * 
	 * @throws IOException
	 * @throws MqttException
	 * @throws ClientProtocolException
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap model) throws ClientProtocolException, MqttException, IOException {
		model.addAttribute("orderCount", orderService.count(null, null));
		model.addAttribute("printedOrderCount", orderService.count(Order.OrderStatus.printed, false));
		model.addAttribute("reveivedOrderCount", orderService.count(Order.OrderStatus.received, false));
		model.addAttribute("sentOrderCount", orderService.count(Order.OrderStatus.sent, false));
		model.addAttribute("expiredOrderCount", orderService.count(null, true));

		Long productCount = productService.count(null, null, true, null, null);
		model.addAttribute("productCount", productCount);
		model.addAttribute("marketableProductCount", productService.count(null, true, true, null, null));
		model.addAttribute("notMarketableProductCount", productService.count(null, false, true, null, null));
		// int onlineProductCount = PushManager.getInstance().countProduct(null);
		model.addAttribute("onlineProductCount", productService.count(null, null, true, true, null));
		model.addAttribute("offlineProductCount", productService.count(null, null, true, false, null));

		Long normalProductCount = productService.count(null, null, null, null, true);
		Long notNormalProductCount = productService.count(null, null, null, null, false);
		Map<String, Long> productCountMap = new LinkedHashMap<String, Long>();
		productCountMap.put("normalProductCount", normalProductCount);
		productCountMap.put("notNormalProductCount", notNormalProductCount);
		model.addAttribute("productCountMap", productCountMap);
		return "/admin/common/index";
	}

	/**
	 * 地区
	 */
	@RequestMapping(value = "/area", method = RequestMethod.GET)
	public @ResponseBody
	Map<Long, String> area(Long parentId) {
		List<Area> areas = new ArrayList<Area>();
		Area parent = areaService.find(parentId);
		if (parent != null) {
			areas = new ArrayList<Area>(parent.getChildren());
		} else {
			areas = areaService.findRoots();
		}
		Map<Long, String> options = new HashMap<Long, String>();
		for (Area area : areas) {
			options.put(area.getId(), area.getName());
		}
		return options;
	}

	/**
	 * 错误提示
	 */
	@RequestMapping("/error")
	public String error() {
		return "/admin/common/error";
	}
	
	@RequestMapping("/notfound404")
	public String notfound404() {
		return "/admin/common/notfound404";
	}

	/**
	 * 权限错误
	 */
	@RequestMapping("/unauthorized")
	public String unauthorized(HttpServletRequest request, HttpServletResponse response) {
		String requestType = request.getHeader("X-Requested-With");
		if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
			response.addHeader("loginStatus", "unauthorized");
			try {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		return "/admin/common/unauthorized";
	}

}