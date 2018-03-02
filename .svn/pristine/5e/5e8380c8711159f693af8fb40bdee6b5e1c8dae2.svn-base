package com.nfet.thread;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttClient;

import com.nfet.entity.Product;
import com.nfet.service.ProductService;
import com.nfet.util.PushManager;
import com.nfet.util.SpringUtils;

public class IntervalCheckConnection implements Runnable {

	public IntervalCheckConnection() {
		super();
	}

	@Override
	public void run() {
		// System.out.println(new Date() +
		// "----IntervalCheckConnection start----");
		PushManager.getInstance().buildProductMap(false);
		// System.out.println(new Date() +
		// "----IntervalCheckConnection middle0----");
		Map<String, MqttClient> productMap = PushManager.getInstance().getProductMap();
		// System.out.println(new Date() +
		// "----IntervalCheckConnection middle1----");

		ProductService productService = SpringUtils.getBean("productServiceImpl", ProductService.class);
		List<Product> products = productService.findAll();
		for (Product product : products) {
			if (productMap.keySet().contains(product.getSn())) {
				if (!product.getIsOnline()) {
					product.setIsOnline(true);
					productService.update(product);
				}
			} else {
				if (product.getIsOnline()) {
					product.setIsOnline(false);
					productService.update(product);
				}
			}
		}
		System.out.println(new Date() + "----IntervalCheckConnection end----");
	}
}
