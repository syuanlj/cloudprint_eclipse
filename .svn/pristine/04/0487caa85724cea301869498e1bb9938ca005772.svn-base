package com.nfet.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.nfet.thread.InitProduct;
import com.nfet.util.PushManager;

public class InitProductListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		PushManager.getInstance().disconnect();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Runnable runnable = new InitProduct();
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.schedule(runnable, 10, TimeUnit.SECONDS);
		service.shutdown();
	}
}
