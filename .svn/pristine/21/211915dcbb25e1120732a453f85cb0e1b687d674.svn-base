package com.nfet.thread;

import com.nfet.entity.Product;
import com.nfet.entity.UpgradeLog;
import com.nfet.service.ProductService;
import com.nfet.service.UpgradeLogService;
import com.nfet.util.SpringUtils;

public class SaveUpgradeLog implements Runnable {

	private String productSn = null;
	private Byte code = null;

	public SaveUpgradeLog(String productSn, Byte code) {
		super();
		this.productSn = productSn;
		this.code = code;
	}

	@Override
	public void run() {
		ProductService productService = SpringUtils.getBean("productServiceImpl", ProductService.class);
		UpgradeLogService upgradeLogService = SpringUtils.getBean("upgradeLogServiceImpl", UpgradeLogService.class);
		Product product = productService.findBySn(this.productSn);
		UpgradeLog upgradeLog = new UpgradeLog();
		upgradeLog.setProduct(product);
		upgradeLog.setCode(this.code);
		// product.getUpgradeLogs().add(upgradeLog);
		upgradeLogService.save(upgradeLog);
		product.setIsLatest(true);
		productService.update(product);
	}
}
