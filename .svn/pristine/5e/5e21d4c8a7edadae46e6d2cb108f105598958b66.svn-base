package com.nfet.thread;

import com.nfet.entity.Order;
import com.nfet.entity.Sn.Type;
import com.nfet.service.OrderService;
import com.nfet.service.SnService;
import com.nfet.util.PushManager;
import com.nfet.util.SpringUtils;

public class IntervalPersistOrder implements Runnable {

	public IntervalPersistOrder() {
		super();
	}

	@Override
	public void run() {

		try {
			PushManager.getInstance().setIntervalPersistOrderTime(System.currentTimeMillis());
			if (!PushManager.getInstance().isPersisting()) {
				PushManager.getInstance().setPersisting(true);
				String[] orderSns = PushManager.getInstance().getOrderPersistMapKey().toString().replace("[", "").replace("]", "").split("\\, ");
				System.out.println("----orderPersistMap key size----" + orderSns.length);
				OrderService orderService = SpringUtils.getBean("orderServiceImpl", OrderService.class);
				SnService snService = SpringUtils.getBean("snServiceImpl", SnService.class);
				
				if(orderSns.length>0)
				{
					for (String orderSn : orderSns) {
						Order order = PushManager.getInstance().getOrderPersistMap(orderSn);
						if (order != null) {
							String sn = snService.generate(Type.order);
							order.setSn(sn.substring(sn.length() - 16));
							orderService.save(order);
						}
						PushManager.getInstance().removeOrderPersistMap(orderSn);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			PushManager.getInstance().setPersisting(false);
		}
	}
}