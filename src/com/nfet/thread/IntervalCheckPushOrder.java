package com.nfet.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.nfet.util.PushManager;

public class IntervalCheckPushOrder implements Runnable {

	public IntervalCheckPushOrder() {
		super();
	}

	@Override
	public void run() {

		long intervalPushOrderTime = PushManager.getInstance().getIntervalPushOrderTime();
		if (System.currentTimeMillis() - intervalPushOrderTime > 120000) {
			Runnable runnable = new IntervalPushOrder();
			ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
			service.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.SECONDS);
		}
	}
}