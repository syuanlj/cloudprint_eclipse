package com.nfet.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.MqttException;

public class MQTTClientBatch2 {

	public static void main(String[] args) throws InterruptedException, MqttException, UnsupportedEncodingException, FileNotFoundException {

		System.setOut(new PrintStream(new FileOutputStream(new File("d:\\mqttclient_" + System.currentTimeMillis() + ".log"))));

		String[] printers = { "VN5Z259497", "VN5Z259496", "VN5Z259495", "VN5Z259494", "VN5Z259493", "VN5Z259491", "VN5Z259490", "VN5Z259489", "VN5Z259488", "VN5Z259487", "VN5Z259484", "VN5Z259482", "VN5Z259481", "VN5Z259480", "VN5Z259479", "VN5Z259475", "VN5Z259474", "VN5Z259471", "VN5Z259469", "VN5Z259465", "VN5Z259463", "VN5Z259462", "VN5Z259459", "VN5Z259458", "VN5Z259457", "VN5Z259454", "VN5Z259453", "VN5Z259452", "VN5Z259451", "VN5Z259450" };
		for (int i = 0; i < printers.length; i++) {
			Runnable runnable = new MQTTClient2(printers[i]);
			ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
			service.schedule(runnable, 0, TimeUnit.SECONDS);
			service.shutdown();
		}
	}
}
