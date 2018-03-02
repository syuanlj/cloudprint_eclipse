package com.nfet.thread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;

import com.nfet.PrintProtocol;
import com.nfet.entity.Product;
import com.nfet.util.PushManager;

public class IntervalPushOrder implements Runnable {

	public IntervalPushOrder() {
		super();
	}

	@Override
	public void run() {

		try {
			PushManager.getInstance().setIntervalPushOrderTime(System.currentTimeMillis());
			String productSnString = PushManager.getInstance().getOrderSnMapKey().toString();
			String[] productSns = productSnString.replace("[", "").replace("]", "").split("\\, ");
			System.out.println(new Date() + "----IntervalPushOrder product size----" + productSns.length);
			for (String productSn : productSns) {
				String orderSns = PushManager.getInstance().getOrderSnMap(productSn);
				if (orderSns == null || "".equals(orderSns)) {
					continue;
				}
				Product.WorkStatus productStatus = PushManager.getInstance().getProductStatusMap(productSn);
				if (Product.WorkStatus.normal.equals(productStatus)) {
					PushManager.getInstance().putProductStatusMap(productSn, Product.WorkStatus.busy);
					PushManager.getInstance().pushPrintMessage(productSn, PrintProtocol.COMMAND_ID_QUERY_PRINTER_STATUS, null, null);
				} else if (Product.WorkStatus.busy.equals(productStatus)) {
					Long productTime = PushManager.getInstance().getProductTimeMap(productSn);
					if (productTime == null || System.currentTimeMillis() - productTime > 600 * 1000) {
						PushManager.getInstance().putProductStatusMap(productSn, Product.WorkStatus.normal);
					}
				}
			}
		} catch (Exception e) {
			try {
				e.printStackTrace(new PrintStream(new File("D:\\error_" + System.currentTimeMillis() + ".log")));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
}