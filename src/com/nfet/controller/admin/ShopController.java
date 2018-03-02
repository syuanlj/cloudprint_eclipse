/*
 * 
 * 
 * 
 */
package com.nfet.controller.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nfet.Message;
import com.nfet.Pageable;
import com.nfet.entity.Shop;
import com.nfet.service.ShopService;

/**
 * Controller - 品牌
 * 
 * 
 * 
 */
@Controller("adminShopController")
@RequestMapping("/admin/shop")
public class ShopController extends BaseController {

	@Resource(name = "shopServiceImpl")
	private ShopService shopService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		return "/admin/shop/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Shop shop, Long areaId, RedirectAttributes redirectAttributes) {
		if (!isValid(shop)) {
			return ERROR_VIEW;
		}
		shop.setProduct(null);
		shopService.save(shop);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.ehtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("shop", shopService.find(id));
		return "/admin/shop/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Shop shop, Long areaId, RedirectAttributes redirectAttributes) {
		if (!isValid(shop)) {
			return ERROR_VIEW;
		}
		shopService.update(shop, "orders");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.ehtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", shopService.findPage(pageable));
		return "/admin/shop/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		shopService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}