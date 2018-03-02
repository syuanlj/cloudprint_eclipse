/*
 * 
 * 
 * 
 */
package com.nfet.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nfet.FileInfo;
import com.nfet.Message;
import com.nfet.Pageable;
import com.nfet.FileInfo.FileType;
import com.nfet.entity.Firmware;
import com.nfet.entity.Product;
import com.nfet.entity.ProductCategory;
import com.nfet.service.AreaService;
import com.nfet.service.FileService;
import com.nfet.service.FirmwareService;
import com.nfet.service.ProductCategoryService;
import com.nfet.service.ProductService;
import com.nfet.service.UpgradeLogService;
import com.nfet.thread.SingleFirmwarePush;

/**
 * Controller - 固件
 * 
 * 
 * 
 */
@Controller("adminFirmwareController")
@RequestMapping("/admin/firmware")
public class FirmwareController extends BaseController {

	@Resource(name = "firmwareServiceImpl")
	private FirmwareService firmwareService;
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "upgradeLogServiceImpl")
	private UpgradeLogService upgrageLogService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;

	/**
	 * 检查编号是否存在
	 */
	@RequestMapping(value = "/check_sn", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkSn(String productSn) {
		if (StringUtils.isEmpty(productSn)) {
			return true;
		}
		String[] productSns = productSn.split(",");
		for (int i = 0; i < productSns.length; i++) {
			if (productService.findBySn(productSns[i]) == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		return "/admin/firmware/add";
	}

	/**
	 * 预览
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/preview", method = RequestMethod.POST)
	public String preview(MultipartFile firmwareFile, ModelMap model) {
		List<Firmware> firmwares = new ArrayList<Firmware>();

		if (firmwareFile != null && !firmwareFile.isEmpty()) {
			if (!fileService.isValid(FileType.firmware, firmwareFile)) {
				model.addAttribute("checkResult", ERROR_MESSAGE);
				return "/admin/firmware/add";
			}

			try {
				Document document = new SAXReader().read(firmwareFile.getInputStream());
				List<Element> elements = document.selectNodes("/update/target");
				for (Element element : elements) {
					String productCategoryName = element.selectSingleNode("name").getText();
					String url = element.selectSingleNode("url").getText();
					String version = element.selectSingleNode("version").getText();
					String length = element.selectSingleNode("filelen").getText();
					String md5 = element.selectSingleNode("md5").getText();
					String date = element.selectSingleNode("date").getText();
					String enddate = element.selectSingleNode("enddate").getText();
					String ftpname = element.selectSingleNode("ftpname").getText();
					String ftppwd = element.selectSingleNode("ftppwd").getText();
					Firmware firmware = new Firmware();
					ProductCategory productCategory = productCategoryService.findByName(productCategoryName);
					if (productCategory != null) {
						firmware.setProductCategory(productCategory);
						firmware.setUrl(url);
						firmware.setVersion(version);
						firmware.setLength(length);
						firmware.setMd5(md5);
						firmware.setDate(date);
						firmware.setEnddate(enddate);
						firmware.setFtpname(ftpname);
						firmware.setFtppwd(ftppwd);
					} else {
						model.addAttribute("checkResult", ERROR_MESSAGE);
						return "/admin/firmware/add";
					}
					firmwares.add(firmware);
				}
			} catch (DocumentException e) {
				e.printStackTrace();
				model.addAttribute("checkResult", ERROR_MESSAGE);
				return "/admin/firmware/add";
			} catch (IOException e) {
				e.printStackTrace();
				model.addAttribute("checkResult", ERROR_MESSAGE);
				return "/admin/firmware/add";
			}
		}
		model.addAttribute("checkResult", SUCCESS_MESSAGE);
		model.addAttribute("firmwares", firmwares);
		List<String> hours = new ArrayList<String>();
		for (int i = 0; i < 24; i++) {
			hours.add(i + "");
		}
		model.addAttribute("types", Firmware.Type.values());
		model.addAttribute("hours", hours);
		return "/admin/firmware/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(FileInfo fileInfo, String areaIds, RedirectAttributes redirectAttributes) {
		List<Firmware> firmwares = fileInfo.getFirmwares();

		if (areaIds == null) {
			areaIds = "";
		}
		areaIds = "|" + areaIds.replaceAll("\\,", "|") + "|";
		while (areaIds.indexOf("||") > -1) {
			areaIds = areaIds.replaceFirst("\\|\\|", "|0|");
		}

		areaIds = areaIds.substring(1, areaIds.length() - 1);
		String[] areaIdArray = areaIds.split("\\|");

		for (int i = 0; i < firmwares.size(); i++) {
			Firmware firmware = firmwares.get(i);
			String productCategoryName = firmware.getProductCategory().getName();
			ProductCategory productCategory = productCategoryService.findByName(productCategoryName);
			firmware.setProductCategory(productCategory);
			firmware.setIsCompleted(false);
			if (Firmware.Type.current.equals(firmware.getType())) {
				firmware.setHour(null);
			}
			String areaId = areaIdArray[i];
			if (!"0".equals(areaId)) {
				firmware.setArea(areaService.find(new Long(areaId)));
			}
			firmwareService.save(firmware);
			List<Product> products = productService.findList(productCategory, null, null, null, null, null, null);
			if (StringUtils.isEmpty(firmware.getProductSn())) {
				for (Product product : products) {
					product.setIsLatest(false);
					productService.update(product);
				}
			} else if (Firmware.ProductType.include.equals(firmware.getProductType())) {
				String[] productSns = firmware.getProductSn().split(",");
				for (int j = 0; j < productSns.length; j++) {
					String productSn = productSns[j];
					Product product = productService.findBySn(productSn);
					product.setIsLatest(false);
					productService.update(product);
				}
			} else if (Firmware.ProductType.exclude.equals(firmware.getProductType())) {
				List<String> productSns = Arrays.asList(firmware.getProductSn().split(","));
				for (Product product : products) {
					String productSn = product.getSn();
					if (productSns.contains(productSn)) {
						continue;
					}
					product.setIsLatest(false);
					productService.update(product);
				}
			}
			if (Firmware.Type.current.equals(firmware.getType())) {
				Runnable runnable = new SingleFirmwarePush(firmware.getId());
				ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
				service.schedule(runnable, 0, TimeUnit.SECONDS);
				service.shutdown();
			} else if (Firmware.Type.schedule.equals(firmware.getType())) {
				Calendar calendar = Calendar.getInstance();
				Integer hour = firmware.getHour();
				if (hour <= calendar.get(Calendar.HOUR_OF_DAY)) {
					calendar.add(Calendar.DATE, 1);
				}
				calendar.set(Calendar.HOUR_OF_DAY, hour);
				calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
				calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
				long interval = calendar.getTimeInMillis() - System.currentTimeMillis();
				Runnable runnable = new SingleFirmwarePush(firmware.getId());
				ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
				service.schedule(runnable, interval / 1000, TimeUnit.SECONDS);
				service.shutdown();
			}
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.ehtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", firmwareService.findPage(pageable));
		return "/admin/firmware/list";
	}

	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long firmwareId, ModelMap model) {
		model.addAttribute("firmware", firmwareService.find(firmwareId));
		return "/admin/firmware/view";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		firmwareService.delete(ids);
		return SUCCESS_MESSAGE;
	}

	/**
	 * 结果
	 */
	@RequestMapping(value = "/result", method = RequestMethod.GET)
	public String result(Long firmwareId, Boolean isLatest, Pageable pageable, ModelMap model) {
		Firmware firmware = firmwareService.find(firmwareId);
		List<Product> products = new ArrayList<Product>();
		if (firmware.getProductSn() != null && !"".equals(firmware.getProductSn())) {
			String[] productSns = firmware.getProductSn().split(",");
			for (int i = 0; i < productSns.length; i++) {
				String productSn = productSns[i];
				Product product = productService.findBySn(productSn);
				products.add(product);
			}
		}
		ProductCategory productCategory = firmware.getProductCategory();
		if (StringUtils.isEmpty(firmware.getProductSn())) {
			model.addAttribute("page", productService.findPage(productCategory, null, null, isLatest, pageable));
		} else if (Firmware.ProductType.include.equals(firmware.getProductType())) {
			model.addAttribute("page", productService.findPage(productCategory, products, null, isLatest, pageable));
		} else if (Firmware.ProductType.exclude.equals(firmware.getProductType())) {
			model.addAttribute("page", productService.findPage(productCategory, null, products, isLatest, pageable));
		}
		model.addAttribute("firmwareId", firmwareId);
		model.addAttribute("isLatest", isLatest);

		return "/admin/firmware/result";
	}

	/**
	 * 日志
	 */
	@RequestMapping(value = "/log", method = RequestMethod.GET)
	public String log(Long productId, Pageable pageable, ModelMap model) {
		Product product = productService.find(productId);
		model.addAttribute("page", upgrageLogService.findPage(product, pageable));

		return "/admin/firmware/log";
	}
}