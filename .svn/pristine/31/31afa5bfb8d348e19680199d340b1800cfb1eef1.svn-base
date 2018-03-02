/*
 * 
 * 
 * 
 */
package com.nfet.controller.admin;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.http.client.ClientProtocolException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nfet.Message;
import com.nfet.entity.Product;
import com.nfet.entity.ProductCategory;
import com.nfet.service.OrderService;
import com.nfet.service.ProductCategoryService;
import com.nfet.service.ProductService;
import com.nfet.service.TemplateService;

/**
 * Controller - 商品分类
 * 
 * 
 * 
 */
@Controller("adminProductCategoryController")
@RequestMapping("/admin/product_category")
public class ProductCategoryController extends BaseController {

	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	@Resource(name = "templateServiceImpl")
	private TemplateService templateService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		model.addAttribute("templates", templateService.findAll());
		return "/admin/product_category/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(ProductCategory productCategory, Long parentId, Long templateId, RedirectAttributes redirectAttributes) {
		productCategory.setParent(productCategoryService.find(parentId));
		productCategory.setTemplate(templateService.find(templateId));
		if (!isValid(productCategory)) {
			return ERROR_VIEW;
		}
		productCategory.setTreePath(null);
		productCategory.setGrade(null);
		productCategory.setChildren(null);
		productCategory.setProducts(null);
		productCategoryService.save(productCategory);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.ehtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		ProductCategory productCategory = productCategoryService.find(id);
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		model.addAttribute("productCategory", productCategory);
		model.addAttribute("children", productCategoryService.findChildren(productCategory));
		model.addAttribute("templates", templateService.findAll());
		return "/admin/product_category/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(ProductCategory productCategory, Long parentId, Long templateId, RedirectAttributes redirectAttributes) {
		productCategory.setParent(productCategoryService.find(parentId));
		productCategory.setTemplate(templateService.find(templateId));
		if (!isValid(productCategory)) {
			return ERROR_VIEW;
		}
		if (productCategory.getParent() != null) {
			ProductCategory parent = productCategory.getParent();
			if (parent.equals(productCategory)) {
				return ERROR_VIEW;
			}
			List<ProductCategory> children = productCategoryService.findChildren(parent);
			if (children != null && children.contains(parent)) {
				return ERROR_VIEW;
			}
		}
		productCategoryService.update(productCategory, "treePath", "grade", "children", "products");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.ehtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		return "/admin/product_category/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long id) {
		ProductCategory productCategory = productCategoryService.find(id);
		if (productCategory == null) {
			return ERROR_MESSAGE;
		}
		Set<ProductCategory> children = productCategory.getChildren();
		if (children != null && !children.isEmpty()) {
			return Message.error("admin.productCategory.deleteExistChildrenNotAllowed");
		}
		Set<Product> products = productCategory.getProducts();
		if (products != null && !products.isEmpty()) {
			return Message.error("admin.productCategory.deleteExistProductNotAllowed");
		}
		productCategoryService.delete(id);
		return SUCCESS_MESSAGE;
	}

	/**
	 * 统计页
	 * 
	 * @throws IOException
	 * @throws MqttException
	 * @throws ClientProtocolException
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String index(Long id, ModelMap model) throws ClientProtocolException, MqttException, IOException {
		ProductCategory productCategory = productCategoryService.find(id);
		if (productCategory == null) {
			return ERROR_VIEW;
		}
		// Set<Product> products = productCategory.getProducts();
		// int orderCount = 0;
		// int printedOrderCount = 0;
		// int reveivedOrderCount = 0;
		// int sentOrderCount = 0;
		// int expiredOrderCount = 0;
		//
		// int productCount = 0;
		// int marketableProductCount = 0;
		// int notMarketableProductCount = 0;
		// int onlineProductCount = 0;
		// int offlineProductCount = 0;
		// while (products.iterator().hasNext()) {
		// Product product = products.iterator().next();
		// productCount++;
		//
		// }
		Long productCount = productService.count(productCategory, null, true, null, null);
		model.addAttribute("productCount", productCount);
		model.addAttribute("marketableProductCount", productService.count(productCategory, true, true, null, null));
		model.addAttribute("notMarketableProductCount", productService.count(productCategory, false, true, null, null));
		// int onlineProductCount = PushManager.getInstance().countProduct(null);
		model.addAttribute("onlineProductCount", productService.count(productCategory, null, true, true, null));
		model.addAttribute("offlineProductCount", productService.count(productCategory, null, true, false, null));

		Long normalProductCount = productService.count(productCategory, null, null, null, true);
		Long notNormalProductCount = productService.count(productCategory, null, null, null, false);
		Map<String, Long> productCountMap = new LinkedHashMap<String, Long>();
		productCountMap.put("normalProductCount", normalProductCount);
		productCountMap.put("notNormalProductCount", notNormalProductCount);
		model.addAttribute("productCountMap", productCountMap);
		return "/admin/product_category/view";
	}
}