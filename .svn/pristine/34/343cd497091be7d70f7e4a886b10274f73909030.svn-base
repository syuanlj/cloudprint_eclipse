package com.nfet.thread;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.nfet.util.PushManager;

public class StartServer implements Runnable {

	public StartServer() {
		super();
	}

	@Override
	public void run() {
		try {
	        PushManager.getInstance().buildServer();
        } catch (MqttException e) {
	        e.printStackTrace();
        }
	}
}
