package com.nfet.thread;

import com.nfet.entity.Product;
import com.nfet.service.ProductService;
import com.nfet.util.SpringUtils;

public class UpdateProduct implements Runnable {

	private String productSn = null;
	private String bootVersion = null;
	private String softwareVersion = null;
	private String hardwareVersion = null;
	private String fontVersion = null;
	private String oemSn = null;
	private String voltage = null;
	private Byte status = null;
	private String error = null;

	public UpdateProduct(String productSn, String voltage, Byte status, String error) {
		super();
		this.productSn = productSn;
		this.voltage = voltage;
		this.status = status;
		this.error = error;
	}

	public UpdateProduct(String productSn, String bootVersion, String softwareVersion, String hardwareVersion, String fontVersion, String oemSn, String voltage, Byte status, String error) {
		super();
		this.productSn = productSn;
		this.bootVersion = bootVersion;
		this.softwareVersion = softwareVersion;
		this.hardwareVersion = hardwareVersion;
		this.fontVersion = fontVersion;
		this.oemSn = oemSn;
		this.voltage = voltage;
		this.status = status;
		this.error = error;
	}

	@Override
	public void run() {
		ProductService productService = SpringUtils.getBean("productServiceImpl", ProductService.class);
		Product product = productService.findBySnForUpdate(this.productSn);
		if (bootVersion != null) {
			product.setBootVersion(bootVersion);
		}
		if (softwareVersion != null) {
			product.setSoftwareVersion(softwareVersion);
		}
		if (hardwareVersion != null) {
			product.setHardwareVersion(hardwareVersion);
		}
		if (fontVersion != null) {
			product.setFontVersion(fontVersion);
		}
		if (oemSn != null) {
			product.setOemSn(oemSn);
		}
		if (voltage != null) {
			product.setVoltage(voltage);
		}
		if (status != null) {
			product.setStatus(status);
		}
		if (error != null) {
			product.setError(error);
		}
		product.setIsOnline(true);
		productService.update(product);
	}
}
