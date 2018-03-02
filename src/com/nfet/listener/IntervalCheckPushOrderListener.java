package com.nfet.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.nfet.thread.IntervalCheckPushOrder;
import com.nfet.util.PushManager;

public class IntervalCheckPushOrderListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		PushManager.getInstance().disconnect();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Runnable runnable = new IntervalCheckPushOrder();
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(runnable, 15, 10, TimeUnit.SECONDS);
	}
}
