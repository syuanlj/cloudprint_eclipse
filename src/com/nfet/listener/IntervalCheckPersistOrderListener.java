package com.nfet.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.nfet.thread.IntervalCheckPersistOrder;
import com.nfet.util.PushManager;

public class IntervalCheckPersistOrderListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		PushManager.getInstance().disconnect();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Runnable runnable = new IntervalCheckPersistOrder();
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(runnable, 151, 150, TimeUnit.SECONDS);
	}
}
