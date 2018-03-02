package com.nfet.test;

import java.io.FileNotFoundException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MQTTClientBatch {

	public static void main(String[] args) throws FileNotFoundException {

		// System.setOut(new PrintStream(new FileOutputStream(new
		// File("d:\\mqttclient_" + System.currentTimeMillis() + ".log"))));
		if (args == null || args.length == 0) {
			args = new String[] { "1", "10000" };
		}
		String startStr = args[0];
		String endStr = args[1];

		int startNum = Integer.parseInt(startStr);
		int endNum = Integer.parseInt(endStr);
		for (int i = startNum; i <= endNum; i++) {
			Runnable runnable = new MQTTClient(i);
			ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
			service.schedule(runnable, 0, TimeUnit.SECONDS);
			service.shutdown();
		}
	}
}
