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

public class SingleOrderPush implements Runnable {

	private String productSn = null;

	public SingleOrderPush(String productSn) {
		super();
		this.productSn = productSn;
	}

	@Override
	public void run() {

		try {

			String orderSns = PushManager.getInstance().getOrderSnMap(this.productSn);
			if (orderSns == null || "".equals(orderSns)) {
				PushManager.getInstance().putProductStatusMap(this.productSn, Product.WorkStatus.normal);
				PushManager.getInstance().removeOrderSnMap(this.productSn);
				return;
			}

			String orderSn = orderSns.substring(0, PrintProtocol.PUSH_ORDER_SN_LENGTH);
			Order order = PushManager.getInstance().getOrderInfoMap(orderSn);
			if (order == null) {
				PushManager.getInstance().putProductStatusMap(this.productSn, Product.WorkStatus.normal);
			}
			String expireInterval = PushManager.getInstance().getSettingMap("expireInterval");
			if (expireInterval == null) {
				Setting setting = SettingUtils.get();
				expireInterval = setting.getExpireInterval();
				PushManager.getInstance().putSettingMap("expireInterval", expireInterval);
			}
			Date lastPush = order.getLastPush();
			Integer pushTimes = order.getPushTimes();
			String pushInterval = PushManager.getInstance().getSettingMap("pushInterval");
			if (pushInterval == null) {
				Setting setting = SettingUtils.get();
				pushInterval = setting.getPushInterval();
				PushManager.getInstance().putSettingMap("pushInterval", pushInterval);
			}
			String[] pushIntervalArray = pushInterval.split(",");
			long now = System.currentTimeMillis();
			if (new Long(expireInterval) < now - order.getCreateDate().getTime()) {
				PushManager.getInstance().removeOrder(productSn, order);
				PushManager.getInstance().putProductStatusMap(this.productSn, Product.WorkStatus.normal);
			} else if (pushTimes >= pushIntervalArray.length) {
				order.setExpire(new Date(System.currentTimeMillis()));
				PushManager.getInstance().persistOrder(productSn, order);
				PushManager.getInstance().putProductStatusMap(this.productSn, Product.WorkStatus.normal);
			} else if (lastPush == null || new Long(pushIntervalArray[pushTimes]) < now - lastPush.getTime()) {
				PushManager.getInstance().pushPrintMessage(this.productSn, PrintProtocol.COMMAND_ID_PUSH_ORDER, order.getSn(), order.getContent());
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
	}
}