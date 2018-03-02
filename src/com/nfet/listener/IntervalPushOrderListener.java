package com.nfet.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.nfet.thread.IntervalPushOrder;
import com.nfet.util.PushManager;

public class IntervalPushOrderListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		PushManager.getInstance().disconnect();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Runnable runnable = new IntervalPushOrder();
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(runnable, 10, 10, TimeUnit.SECONDS);
	}
}
