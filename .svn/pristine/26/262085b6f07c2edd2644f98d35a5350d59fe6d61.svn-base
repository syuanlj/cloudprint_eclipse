package com.nfet.thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.util.StringUtils;

import com.nfet.entity.Firmware;
import com.nfet.entity.Product;
import com.nfet.entity.ProductCategory;
import com.nfet.service.FirmwareService;
import com.nfet.service.ProductService;
import com.nfet.util.PushManager;
import com.nfet.util.SpringUtils;

public class IntervalPushFirmware implements Runnable {

	public IntervalPushFirmware() {
		super();
	}

	@Override
	public void run() {
		FirmwareService firmwareService = SpringUtils.getBean("firmwareServiceImpl", FirmwareService.class);
		List<com.nfet.Order> orderBys = new ArrayList<com.nfet.Order>();
		orderBys.add(com.nfet.Order.desc("createDate"));
		List<Firmware> firmwares = firmwareService.findList(Firmware.Type.schedule, false, orderBys);
		ProductService productService = SpringUtils.getBean("productServiceImpl", ProductService.class);

		for (Firmware firmware : firmwares) {
			String version = firmware.getVersion();
			String url = firmware.getUrl();
			String length = firmware.getLength();
			String md5 = firmware.getMd5();
			ProductCategory productCategory = firmware.getProductCategory();
			String productCategoryName = productCategory.getName();
			boolean isCompleted = true;

			if (StringUtils.isEmpty(firmware.getProductSn())) {
				Iterator<Product> productIter = productCategory.getProducts().iterator();

				while (productIter.hasNext()) {
					Product product = productIter.next();
					String productSn = product.getSn();
					if (!version.equals(product.getHardwareVersion())) {
						isCompleted = false;
						PushManager.getInstance().pushUpgradeMessage(productSn, productCategoryName, version, length, md5, url, "username", "password");
					}
				}
			} else if (Firmware.ProductType.include.equals(firmware.getProductType())) {
				String[] productSns = firmware.getProductSn().split(",");

				for (int i = 0; i < productSns.length; i++) {
					String productSn = productSns[i];
					Product product = productService.findBySn(productSn);
					if (!version.equals(product.getHardwareVersion())) {
						isCompleted = false;
						PushManager.getInstance().pushUpgradeMessage(productSn, productCategoryName, version, length, md5, url, "username", "password");
					}
				}
			} else if (Firmware.ProductType.exclude.equals(firmware.getProductType())) {
				Iterator<Product> productIter = productCategory.getProducts().iterator();
				List<String> productSns = Arrays.asList(firmware.getProductSn().split(","));

				while (productIter.hasNext()) {
					Product product = productIter.next();
					String productSn = product.getSn();
					if (productSns.contains(productSn)) {
						continue;
					}
					if (!version.equals(product.getHardwareVersion())) {
						isCompleted = false;
						PushManager.getInstance().pushUpgradeMessage(productSn, productCategoryName, version, length, md5, url, "username", "password");
					}
				}
			}

			if (isCompleted) {
				firmware.setIsCompleted(true);
				firmwareService.update(firmware);
			}
		}
	}
}
