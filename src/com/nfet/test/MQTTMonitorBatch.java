package com.nfet.test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.MqttException;

public class MQTTMonitorBatch {

	public static void main(String[] args) throws InterruptedException, MqttException, UnsupportedEncodingException, FileNotFoundException {

		Runnable runnable = new MQTTMonitor();
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.schedule(runnable, 0, TimeUnit.SECONDS);
		service.shutdown();
	}
}
