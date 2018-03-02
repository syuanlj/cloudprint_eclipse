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
import com.nfet.Template;
import com.nfet.service.TemplateService;

/**
 * Controller - 模板
 * 
 * 
 * 
 */
@Controller("adminTemplateController")
@RequestMapping("/admin/template")
public class TemplateController extends BaseController {

	@Resource(name = "templateServiceImpl")
	private TemplateService templateService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		return "/admin/template/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Template template, RedirectAttributes redirectAttributes) {
		if (!isValid(template)) {
			return ERROR_VIEW;
		}
		templateService.save(template);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.ehtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("template", templateService.find(id));
		return "/admin/template/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Template template, RedirectAttributes redirectAttributes) {
		if (!isValid(template)) {
			return ERROR_VIEW;
		}
		templateService.update(template);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.ehtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", templateService.findPage(pageable));
		return "/admin/template/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		templateService.delete(ids);
		return SUCCESS_MESSAGE;
	}
}