package com.nfet.thread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;

import com.nfet.PrintProtocol;
import com.nfet.Setting;
import com.nfet.entity.Order;
import com.nfet.entity.Product;
import com.nfet.util.PushManager;
import com.nfet.util.SettingUtils;

public class NextOrderPush implements Runnable {

	private String productSn = null;
	private String orderSn = null;
	private boolean flag = false;

	public NextOrderPush(String productSn, String orderSn, boolean flag) {
		super();
		this.productSn = productSn;
		this.orderSn = orderSn;
		this.flag = flag;
	}

	@Override
	public void run() {

		if (flag) {
			try {
				Order preOrder = PushManager.getInstance().getOrderInfoMap(this.orderSn);
				if (preOrder == null) {
					PushManager.getInstance().putProductStatusMap(this.productSn, Product.WorkStatus.normal);
				}

				if (Order.OrderStatus.sent.equals(preOrder.getOrderStatus())) {
					preOrder.setFinish(new Date(System.currentTimeMillis()));
					preOrder.setExpire(null);
				} else if (Order.OrderStatus.received.equals(preOrder.getOrderStatus())) {
					preOrder.setLastPush(new Date(System.currentTimeMillis()));
					preOrder.setPushTimes(preOrder.getPushTimes() + 1);
					preOrder.setFinish(new Date(System.currentTimeMillis()));
					preOrder.setExpire(null);
				}
				preOrder.setOrderStatus(Order.OrderStatus.printed);
				String orderSns = PushManager.getInstance().persistOrder(this.productSn, preOrder);

				if (orderSns == null) {
					PushManager.getInstance().putProductStatusMap(this.productSn, Product.WorkStatus.normal);
					return;
				} else if ("".equals(orderSns)) {
					PushManager.getInstance().putProductStatusMap(this.productSn, Product.WorkStatus.normal);
					PushManager.getInstance().removeOrderSnMap(this.productSn);
					return;
				}
				Thread.sleep(1000);
				String nextOrderSn = orderSns.substring(0, PrintProtocol.PUSH_ORDER_SN_LENGTH);
				Order nextOrder = PushManager.getInstance().getOrderInfoMap(nextOrderSn);
				if (nextOrder == null) {
					PushManager.getInstance().putProductStatusMap(this.productSn, Product.WorkStatus.normal);
				}

				String expireInterval = PushManager.getInstance().getSettingMap("expireInterval");
				if (expireInterval == null) {
					Setting setting = SettingUtils.get();
					expireInterval = setting.getExpireInterval();
					PushManager.getInstance().putSettingMap("expireInterval", expireInterval);
				}
				Date lastPush = nextOrder.getLastPush();
				Integer pushTimes = nextOrder.getPushTimes();
				String pushInterval = PushManager.getInstance().getSettingMap("pushInterval");
				if (pushInterval == null) {
					Setting setting = SettingUtils.get();
					pushInterval = setting.getPushInterval();
					PushManager.getInstance().putSettingMap("pushInterval", pushInterval);
				}
				String[] pushIntervalArray = pushInterval.split(",");
				long now = System.currentTimeMillis();
				if (new Long(expireInterval) < now - nextOrder.getCreateDate().getTime()) {
					PushManager.getInstance().removeOrder(productSn, nextOrder);
					PushManager.getInstance().putProductStatusMap(this.productSn, Product.WorkStatus.normal);
				} else if (pushTimes >= pushIntervalArray.length) {
					nextOrder.setExpire(new Date(System.currentTimeMillis()));
					PushManager.getInstance().persistOrder(this.productSn, nextOrder);
					PushManager.getInstance().putProductStatusMap(this.productSn, Product.WorkStatus.normal);
				} else if (lastPush == null || new Long(pushIntervalArray[pushTimes]) < now - lastPush.getTime()) {
					PushManager.getInstance().pushPrintMessage(this.productSn, PrintProtocol.COMMAND_ID_QUERY_PRINTER_STATUS, null, null);
				} else {
					PushManager.getInstance().putProductStatusMap(this.productSn, Product.WorkStatus.normal);
				}
			} catch (Exception e) {
				try {
					e.printStackTrace(new PrintStream(new File("D:\\error_" + System.currentTimeMillis() + ".log")));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else {
			Order preOrder = PushManager.getInstance().getOrderInfoMap(this.orderSn);
			preOrder.setOrderStatus(Order.OrderStatus.received);
			PushManager.getInstance().putProductStatusMap(this.productSn, Product.WorkStatus.normal);

			System.out.println("product " + this.productSn + " orderSn " + orderSn + " is reprint.");
		}
	}
}