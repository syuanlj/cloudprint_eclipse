package com.nfet.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.nfet.util.PushManager;

public class IntervalCheckPersistOrder implements Runnable {

	public IntervalCheckPersistOrder() {
		super();
	}

	@Override
	public void run() {

		if (PushManager.getInstance().isPersisting()) {
			return;
		}
		long intervalPersistOrderTime = PushManager.getInstance().getIntervalPersistOrderTime();
		if (System.currentTimeMillis() - intervalPersistOrderTime > 600000) {
			Runnable runnable = new IntervalPersistOrder();
			ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
			service.scheduleAtFixedRate(runnable, 0, 120, TimeUnit.SECONDS);
		}
	}
}