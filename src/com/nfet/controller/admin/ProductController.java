/*
 * 
 * 
 * 
 */
package com.nfet.controller.admin;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.nfet.PrintProtocol;
import com.nfet.FileInfo.FileType;
import com.nfet.entity.User;
import com.nfet.entity.Area;
import com.nfet.entity.Product;
import com.nfet.entity.ProductCategory;
import com.nfet.entity.Role;
import com.nfet.service.AdminService;
import com.nfet.service.AreaService;
import com.nfet.service.FileService;
import com.nfet.service.ProductCategoryService;
import com.nfet.service.ProductService;
import com.nfet.service.RoleService;
import com.nfet.service.ShopService;
import com.nfet.thread.ResetFactory;
import com.nfet.thread.ResetProduct;

/**
 * Controller - 商品
 * 
 * 
 * 
 */
@Controller("adminProductController")
@RequestMapping("/admin/product")
public class ProductController extends BaseController {

	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	@Resource(name = "shopServiceImpl")
	private ShopService shopService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "roleServiceImpl")
	private RoleService roleService;

	/**
	 * 检查编号是否唯一
	 */
	@RequestMapping(value = "/check_sn", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkSn(String previousSn, String sn) {
		if (StringUtils.isEmpty(sn)) {
			return false;
		}
		if (productService.snUnique(previousSn, sn)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, ModelMap model) {
		model.addAttribute("product", productService.find(id));
		return "/admin/product/view";
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add() {
		return "/admin/product/add";
	}
	
	/**
	 * 添加设备
	 */
	@RequestMapping(value = "/addproduct", method = RequestMethod.GET)
	public String addprodut() {
		return "/admin/product/addproduct";
	}
	
	/**
	 * 添加设备
	 * 
	 */
	@RequestMapping(value = "/listproduct", method = RequestMethod.GET)
	public String listproduct(ModelMap model) {
		List<Product> productlist=new ArrayList<>();
		Product product=new Product();
		product.setSn("zheshisn");
		product.setCreateDate(new Date(1412654676572L));
		product.setSoftwareVersion("1.0.0");
		product.setStatus((byte) 0);
		product.setIsOnline(true);
		productlist.add(product);
		productlist.add(product);
		productlist.add(product);
		productlist.add(product);
		productlist.add(product);
		productlist.add(product);
		productlist.add(product);
		productlist.add(product);
		productlist.add(product);
		productlist.add(product);
		productlist.add(product);
		productlist.add(product);
		
		model.addAttribute("productlist",productlist);
		
		return "/admin/product/listproduct";
	}
	
	/**
	 * 添加设备
	 */
	@RequestMapping(value = "/addproduct", method = RequestMethod.POST)
	public String addproduct(Product product, ModelMap model, RedirectAttributes redirectAttributes) {
		
		if(!StringUtils.isNotEmpty(product.getSn())){
			model.addAttribute("checkResult", Message.error("无效的产品序列号"));
			return "/admin/product/addproduct";
		}
		
		Product mProduct = productService.findBySn(product.getSn());
		
		if(mProduct == null){
			model.addAttribute("checkResult", Message.error("无效的产品序列号"));
			return "/admin/product/addproduct";
		}
		
		if(!StringUtils.equals(mProduct.getPassword(), product.getPassword())){
			model.addAttribute("checkResult", Message.error("产品序列号和密钥不匹配"));
			return "/admin/product/addproduct";
		}
		
		if(mProduct.getIsMarketable()){
			model.addAttribute("checkResult", Message.error("设备已经添加"));
			return "/admin/product/addproduct";
		}
		
		if(StringUtils.isNotEmpty(product.getProductName())){
			mProduct.setProductName(product.getProductName());
		}
		
		if(StringUtils.isNotEmpty(product.getProductCardno())){
			mProduct.setProductName(product.getProductCardno());
		}
		
		mProduct.setIsList(true);
		mProduct.setIsMarketable(true);
		mProduct.setIsOnline(false);
		mProduct.setStatus(PrintProtocol.PRINTER_STATUS_NORMAL);
		
		if (!isValid(mProduct)) {
			model.addAttribute("checkResult", Message.error("admin.product.checkResult.invalid", mProduct.getSn()));
			return "/admin/product/addproduct";
		}
		
		productService.update(mProduct);

		model.addAttribute("checkResult", SUCCESS_MESSAGE);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.ehtml";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/preview", method = RequestMethod.POST)
	public String preview(MultipartFile productFile, ModelMap model) {
		List<Product> products = new ArrayList<Product>();

		if (productFile != null && !productFile.isEmpty()) {
			if (!fileService.isValid(FileType.product, productFile)) {
				return "/admin/product/add";
			}
			try {
				HSSFWorkbook hssfWorkbook = new HSSFWorkbook(productFile.getInputStream());
				for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
					HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
					if (hssfSheet == null) {
						continue;
					}
					for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
						HSSFRow hssfRow = hssfSheet.getRow(rowNum);
						if (hssfRow == null) {
							continue;
						}

						Product product = new Product();
						HSSFCell productCategoryName = hssfRow.getCell(0);
						if (productCategoryName == null) {
							continue;
						}
						ProductCategory productCategory = productCategoryService.findByName(productCategoryName.getStringCellValue());
						product.setProductCategory(productCategory);

						HSSFCell productSn = hssfRow.getCell(1);
						if (productSn == null) {
							continue;
						}
						product.setSn(productSn.getStringCellValue());

						HSSFCell manufacture = hssfRow.getCell(6);
						if (manufacture == null) {
							continue;
						}
						product.setManufacture(manufacture.getDateCellValue());
						product.setIsList(true);
						product.setIsMarketable(true);
						product.setIsOnline(false);
						if (!isValid(product)) {
							model.addAttribute("checkResult", Message.error("admin.product.checkResult.invalid", product.getSn()));
							return "/admin/product/add";
						}
						if (StringUtils.isNotEmpty(product.getSn()) && productService.snExists(product.getSn())) {
							model.addAttribute("checkResult", Message.error("admin.product.checkResult.duplicate", product.getSn()));
							return "/admin/product/add";
						}
						products.add(product);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("checkResult", SUCCESS_MESSAGE);
		model.addAttribute("products", products);
		return "/admin/product/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(FileInfo fileInfo, RedirectAttributes redirectAttributes) {
		List<Product> products = fileInfo.getProducts();
		for (Product product : products) {
			String productCategoryName = product.getProductCategory().getName();
			ProductCategory productCategory = productCategoryService.findByName(productCategoryName);
			product.setProductCategory(productCategory);
			product.setIsList(true);
			product.setIsMarketable(true);
			product.setIsOnline(false);
			product.setStatus(PrintProtocol.PRINTER_STATUS_NORMAL);
			productService.save(product);
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.ehtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		model.addAttribute("shops", shopService.findAll());
		model.addAttribute("product", productService.find(id));
		return "/admin/product/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Product product, Long productCategoryId, Long shopId, RedirectAttributes redirectAttributes) {
		product.setProductCategory(productCategoryService.find(productCategoryId));
		product.setShop(shopService.find(shopId));
		Product pProduct = productService.find(product.getId());
		if (pProduct == null) {
			return ERROR_VIEW;
		}
		if (StringUtils.isNotEmpty(product.getSn()) && !productService.snUnique(pProduct.getSn(), product.getSn())) {
			return ERROR_VIEW;
		}
		pProduct.setSn(product.getSn());
		pProduct.setProductCategory(product.getProductCategory());
		pProduct.setShop(product.getShop());
		pProduct.setIsList(product.getIsList());
		pProduct.setIsMarketable(product.getIsMarketable());

		productService.update(pProduct);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.ehtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Long productCategoryId, Boolean isMarketable, Boolean isList, Boolean isOnline, Boolean isNormal, Long areaId, Pageable pageable, ModelMap model) {
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		model.addAttribute("productCategoryId", productCategoryId);
		model.addAttribute("isMarketable", isMarketable);
		model.addAttribute("isList", isList);
		model.addAttribute("isOnline", isOnline);
		model.addAttribute("isNormal", isNormal);
		Role role1 = roleService.find(1L);
		Role role2 = roleService.find(2L);
		User admin = adminService.getCurrent();
		model.addAttribute("isAdmin", admin.getRoles().contains(role1) || admin.getRoles().contains(role2));
		Area area = areaService.find(areaId);
		model.addAttribute("area", area);
		Byte status = null;
		if (isNormal != null) {
			if (isNormal) {
				status = PrintProtocol.PRINTER_STATUS_NORMAL;
			} else {
				status = PrintProtocol.PRINTER_STATUS_ABNORMAL;
			}
		}
		model.addAttribute("page", productService.findPage(productCategory, null, isMarketable, isList, isOnline, status, area, pageable));
		return "/admin/product/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		List<String> mqttUsernames = new ArrayList<String>();
		for (int i = 0; i < ids.length; i++) {
			Long id = ids[i];
			Product product = productService.find(id);
			String mqttUsername = product.getMqttUsername();
			mqttUsernames.add(mqttUsername);
		}

		productService.delete(ids);

		return SUCCESS_MESSAGE;
	}

	/**
	 * 分布
	 */
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public String list(Long parentId, ModelMap model) {
		Area parent = areaService.find(parentId);
		if (parent != null) {
			model.addAttribute("parent", parent);
			model.addAttribute("areas", new ArrayList<Area>(parent.getChildren()));
		} else {
			model.addAttribute("areas", areaService.findRoots());
		}
		return "/admin/product/count";
	}

	/**
	 * 重启
	 */
	@RequestMapping(value = "/reset_printer", method = RequestMethod.GET)
	public String resetPrinter(Long id, RedirectAttributes redirectAttributes) {
		Runnable runnable = new ResetProduct(id);
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.schedule(runnable, 0, TimeUnit.SECONDS);
		service.shutdown();

		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.ehtml";
	}

	/**
	 * 重启
	 */
	@RequestMapping(value = "/reset_factory", method = RequestMethod.GET)
	public String resetFactory(Long id, RedirectAttributes redirectAttributes) {
		Runnable runnable = new ResetFactory(id);
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.schedule(runnable, 0, TimeUnit.SECONDS);
		service.shutdown();

		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.ehtml";
	}
}