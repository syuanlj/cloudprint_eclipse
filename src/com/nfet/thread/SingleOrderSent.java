package com.nfet.thread;

import java.util.Date;

import com.nfet.entity.Order;
import com.nfet.util.PushManager;

public class SingleOrderSent implements Runnable {

	private String productSn = null;
	private String orderSn = null;

	public SingleOrderSent(String productSn, String orderSn) {
		super();
		this.productSn = productSn;
		this.orderSn = orderSn;
	}

	@Override
	public void run() {
		Order order = PushManager.getInstance().getOrderInfoMap(this.orderSn);
		if (order != null && !Order.OrderStatus.printed.equals(order.getOrderStatus())) {
			order.setOrderStatus(Order.OrderStatus.sent);
			order.setLastPush(new Date(System.currentTimeMillis()));
			order.setPushTimes(order.getPushTimes() + 1);
		}
	}
}
