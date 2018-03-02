/*
 * 
 * 
 * 
 */
package com.nfet.controller.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nfet.Page;
import com.nfet.Pageable;
import com.nfet.Setting;
import com.nfet.entity.Order;
import com.nfet.entity.Product;
import com.nfet.entity.Order.OrderStatus;
import com.nfet.service.OrderService;
import com.nfet.service.ProductService;
import com.nfet.util.SettingUtils;

import sun.misc.BASE64Decoder;

/**
 * Controller - 订单
 * 
 * 
 * 
 */
@Controller("adminOrderController")
@RequestMapping("/admin/order")
public class OrderController extends BaseController {

	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;

	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, ModelMap model) {
		Order order = orderService.find(id);
		model.addAttribute("order", order);
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			String content = new String(decoder.decodeBuffer(order.getContent()));
			model.addAttribute("content", content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/admin/order/view";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(String productSn, OrderStatus orderStatus, Boolean hasExpired, Pageable pageable, ModelMap model) {
		List<Product> products = productService.findList(productSn);
		model.addAttribute("productSn", productSn);
		model.addAttribute("orderStatus", orderStatus);
		model.addAttribute("hasExpired", hasExpired);
		if (productSn != null && products.isEmpty()) {
			model.addAttribute("page", new Page<Order>(new ArrayList<Order>(), 0, pageable));
		} else {
			model.addAttribute("page", orderService.findPage(products, orderStatus, hasExpired, pageable));
		}
		return "/admin/order/list";
	}

	/**
	 * 推送
	 */
	@RequestMapping(value = "/push", method = RequestMethod.GET)
	public String push(Long id, RedirectAttributes redirectAttributes) {
		Order order = orderService.find(id);

		Setting setting = SettingUtils.get();
		String siteUrl = setting.getSiteUrl();

		HttpClient httpClient = new DefaultHttpClient();
		String postUrl = siteUrl + "/order/receive.ehtml";
		System.out.println("----push order----" + postUrl);
		HttpPost httpPost = new HttpPost(postUrl);

		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("content", order.getContent()));
		parameters.add(new BasicNameValuePair("deviceid", order.getProduct().getSn()));
		parameters.add(new BasicNameValuePair("cloudname", order.getProduct().getUsername()));
		parameters.add(new BasicNameValuePair("cloudpwd", order.getProduct().getPassword()));
		parameters.add(new BasicNameValuePair("template", "0"));
		parameters.add(new BasicNameValuePair("taskid", order.getTaskId()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		parameters.add(new BasicNameValuePair("time", sdf.format(order.getReceive())));

		try {
			UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(parameters, "UTF-8");
			httpPost.setEntity(formEntiry);
			HttpResponse postResult = httpClient.execute(httpPost);
			System.out.println("STATUS_CODE:" + postResult.getStatusLine().getStatusCode());
			httpPost.releaseConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.ehtml";
	}

}