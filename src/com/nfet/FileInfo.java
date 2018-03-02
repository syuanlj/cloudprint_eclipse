/*
 * 
 * 
 * 
 */
package com.nfet;

import java.util.ArrayList;
import java.util.List;

import com.nfet.entity.Firmware;
import com.nfet.entity.Product;

/**
 * 文件信息
 * 
 * 
 * 
 */
public class FileInfo {

	/**
	 * 文件类型
	 */
	public enum FileType {

		product, firmware
	}

	/** 商品 */
	private List<Product> products = new ArrayList<Product>();

	/** 固件 */
	private List<Firmware> firmwares = new ArrayList<Firmware>();

	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	public List<Product> getProducts() {
		return products;
	}

	/**
	 * 设置商品
	 * 
	 * @param products
	 *            商品
	 */
	public void setProducts(List<Product> products) {
		this.products = products;
	}

	/**
	 * 获取固件
	 * 
	 * @return 固件
	 */
	public List<Firmware> getFirmwares() {
		return firmwares;
	}

	/**
	 * 设置固件
	 * 
	 * @param firmwares
	 *            固件
	 */
	public void setFirmwares(List<Firmware> firmwares) {
		this.firmwares = firmwares;
	}
}