package com.nfet.thread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.util.StringUtils;

import com.nfet.entity.Area;
import com.nfet.entity.Firmware;
import com.nfet.entity.Product;
import com.nfet.entity.ProductCategory;
import com.nfet.service.FirmwareService;
import com.nfet.service.ProductService;
import com.nfet.util.PushManager;
import com.nfet.util.SpringUtils;

public class SingleFirmwarePush implements Runnable {

	private Long firmwareId = null;

	public SingleFirmwarePush(Long firmwareId) {
		super();
		this.firmwareId = firmwareId;
	}

	@Override
	public void run() {
		FirmwareService firmwareService = SpringUtils.getBean("firmwareServiceImpl", FirmwareService.class);
		ProductService productService = SpringUtils.getBean("productServiceImpl", ProductService.class);
		Firmware firmware = firmwareService.find(this.firmwareId);

		if (!firmware.getIsCompleted()) {
			String version = firmware.getVersion();
			String url = firmware.getUrl();
			String length = firmware.getLength();
			String md5 = firmware.getMd5();
			ProductCategory productCategory = firmware.getProductCategory();
			String productCategoryName = productCategory.getName();
			String ftpname = firmware.getFtpname();
			String ftppwd = firmware.getFtppwd();
			String enddate = firmware.getEnddate();
			String targetVersion = firmware.getTargetVersion();
			Area area = firmware.getArea();
			boolean isCompleted = true;
			int count = 0;

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			try {
				Date endDate = sdf.parse(enddate);
				if (System.currentTimeMillis() > endDate.getTime()) {
					firmware.setIsCompleted(true);
					firmwareService.update(firmware);
					return;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}

			if (StringUtils.isEmpty(firmware.getProductSn())) {
				Iterator<Product> productIter = productCategory.getProducts().iterator();

				while (productIter.hasNext()) {
					if (count > 100) {
						return;
					}
					Product product = productIter.next();
					if (targetVersion != null && !targetVersion.equals(product.getSoftwareVersion())) {
						continue;
					}
					if (area != null && !area.equals(product.getArea())) {
						continue;
					}
					String productSn = product.getSn();
					if (!product.getIsLatest()) {
						isCompleted = false;
						PushManager.getInstance().pushUpgradeMessage(productSn, productCategoryName, version, length, md5, url, ftpname, ftppwd);
						count++;
					}
				}
			} else if (Firmware.ProductType.include.equals(firmware.getProductType())) {
				String[] productSns = firmware.getProductSn().split(",");

				for (int i = 0; i < productSns.length; i++) {
					if (count > 100) {
						return;
					}
					String productSn = productSns[i];
					Product product = productService.findBySn(productSn);
					if (targetVersion != null && !targetVersion.equals(product.getSoftwareVersion())) {
						continue;
					}
					if (area != null && !area.equals(product.getArea())) {
						continue;
					}
					if (!product.getIsLatest()) {
						isCompleted = false;
						PushManager.getInstance().pushUpgradeMessage(productSn, productCategoryName, version, length, md5, url, ftpname, ftppwd);
						count++;
					}
				}
			} else if (Firmware.ProductType.exclude.equals(firmware.getProductType())) {
				Iterator<Product> productIter = productCategory.getProducts().iterator();
				List<String> productSns = Arrays.asList(firmware.getProductSn().split(","));

				while (productIter.hasNext()) {
					if (count > 100) {
						return;
					}
					Product product = productIter.next();
					String productSn = product.getSn();
					if (productSns.contains(productSn)) {
						continue;
					}
					if (targetVersion != null && !targetVersion.equals(product.getSoftwareVersion())) {
						continue;
					}
					if (area != null && !area.equals(product.getArea())) {
						continue;
					}
					if (!product.getIsLatest()) {
						isCompleted = false;
						PushManager.getInstance().pushUpgradeMessage(productSn, productCategoryName, version, length, md5, url, ftpname, ftppwd);
						count++;
					}
				}
			}

			if (isCompleted) {
				firmware.setIsCompleted(true);
				firmwareService.update(firmware);
			} else {
				Runnable runnable = new SingleFirmwarePush(this.firmwareId);
				ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
				service.schedule(runnable, 180, TimeUnit.SECONDS);
				service.shutdown();
			}
		}
	}
}