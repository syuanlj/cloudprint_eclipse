package com.nfet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.nfet.util.PushManager;

public class IntervalPushFirmwareListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		PushManager.getInstance().disconnect();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
//		Runnable runnable = new IntervalPushFirmware();
//		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
//		service.scheduleAtFixedRate(runnable, 10, 30, TimeUnit.SECONDS);
	}
}
