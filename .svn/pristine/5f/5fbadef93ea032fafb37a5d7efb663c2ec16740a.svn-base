package com.nfet.thread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;

import com.nfet.entity.Product;
import com.nfet.service.ProductService;
import com.nfet.util.PushManager;
import com.nfet.util.SpringUtils;

public class InitProduct implements Runnable {

	public InitProduct() {
		super();
	}

	@Override
	public void run() {

		try {
			ProductService productService = SpringUtils.getBean("productServiceImpl", ProductService.class);
			List<Product> products = productService.findAll();
			PushManager.getInstance().addProducts(products);
			System.out.println(new Date() + "----InitProduct size----" + PushManager.getInstance().getProductInfoMapSize());
		} catch (Exception e) {
			try {
				e.printStackTrace(new PrintStream(new File("D:\\error_" + System.currentTimeMillis() + ".log")));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
}
