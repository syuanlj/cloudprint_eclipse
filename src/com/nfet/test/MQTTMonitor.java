package com.nfet.test;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.nfet.PrintProtocol;
import com.nfet.PrintTopic;

public class MQTTMonitor implements Runnable {

	@Override
	public void run() {

		String host = "tcp://139.129.27.116:61613";
		String userName = "admin";
		String passWord = "password";

		try {
			final MqttClient client = new MqttClient(host, "monitor");

			MqttConnectOptions options = new MqttConnectOptions();
			options.setCleanSession(true);
			options.setUserName(userName);
			options.setPassword((passWord).toCharArray());
			options.setConnectionTimeout(60000);
			options.setKeepAliveInterval(60000);
			client.setCallback(new MqttCallback() {
				@Override
				public void connectionLost(Throwable cause) {
					System.out.println("connectionLost-----------");
					cause.printStackTrace();
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
				}

				@Override
				public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
					byte[] payload = mqttMessage.getPayload();

					if (topic.startsWith(PrintTopic.prefix_down)) {
						byte protocolType = payload[PrintProtocol.PROTOCOL_TYPE_OFFSET];

						if (PrintProtocol.PROTOCOL_TYPE_DOWN == protocolType) {
							short bodyLength = bytes2short(payload, PrintProtocol.BODY_LENGTH_OFFSET, PrintProtocol.BODY_LENGTH_LENGTH);

							if (bodyLength == payload.length - PrintProtocol.BODY_OFFSET) {
								byte checksum = payload[PrintProtocol.CHECKSUM_OFFSET];

								if (checksum == checksum(payload, PrintProtocol.BODY_OFFSET)) {
									byte commandId = payload[PrintProtocol.COMMAND_ID_OFFSET];
									String productSn = topic.replace(PrintTopic.prefix_down, "");
									System.out.println(new Date(System.currentTimeMillis()) + "----------command id----------" + commandId + "----------product sn----------" + productSn);

								} else {
									System.out.println("invalid checksum : " + checksum);
								}
							} else {
								System.out.println("invalid body length : " + bodyLength);
							}
						} else {
							System.out.println("invalid protocol type : " + protocolType);
						}
					}
				}
			});
			client.connect(options);
			client.subscribe(PrintTopic.prefix_down + "#");
			System.out.println("monitor" + " connected.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private byte[] padRight(String str, byte padding, int length) {
		byte[] bytes = new byte[length];
		try {
			byte[] strArray = str.getBytes("GB2312");
			copyByte(bytes, 0, strArray);
			for (int i = strArray.length; i < length; i++) {
				bytes[i] = padding;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return bytes;
	}

	private String bytes2string(byte[] bytes, int offset, int length) {
		byte[] stringBytes = new byte[length];
		for (int i = 0; i < length; i++) {
			stringBytes[i] = bytes[offset + i];
		}
		return new String(stringBytes);
	}

	private String bytes2string(byte[] bytes, int offset) {
		int length = bytes.length - offset;
		return bytes2string(bytes, offset, length);
	}

	private int byte2digit(byte b) {
		return 0xff & b;
	}

	private short bytes2short(byte[] bytes, int offset, int length) {
		byte[] shortBytes = new byte[length];
		for (int i = 0; i < length; i++) {
			shortBytes[i] = bytes[offset + i];
		}
		return (short) ((0xff & shortBytes[0]) | (0xff00 & (shortBytes[1] << 8)));
	}

	private int bytes2int(byte[] bytes, int offset, int length) {
		byte[] intBytes = new byte[length];
		for (int i = 0; i < length; i++) {
			intBytes[i] = bytes[offset + i];
		}
		return (0xff & intBytes[0]) | (0xff00 & (intBytes[1] << 8)) | (0xff0000 & (intBytes[2] << 16)) | (0xff000000 & (intBytes[3] << 24));
	}

	private byte[] digit2byte(int data) {
		byte[] bytes = new byte[1];
		bytes[0] = (byte) (data & 0xff);
		return bytes;
	}

	private byte[] short2byte(short data) {
		byte[] bytes = new byte[2];
		bytes[0] = (byte) (data & 0xff);
		bytes[1] = (byte) ((data & 0xff00) >> 8);
		return bytes;
	}

	private byte[] int2byte(int data) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) (data & 0xff);
		bytes[1] = (byte) ((data & 0xff00) >> 8);
		bytes[2] = (byte) ((data & 0xff0000) >> 16);
		bytes[3] = (byte) ((data & 0xff000000) >> 24);
		return bytes;
	}

	private void copyByte(byte[] toArray, int offset, byte[] fromArray) {
		for (int i = 0; i < fromArray.length; i++) {
			byte from = fromArray[i];
			toArray[offset + i] = from;
		}
	}

	private byte checksum(byte[] bytes, int offset) {
		int sum = 0;
		for (int i = offset; i < bytes.length; i++) {
			byte b = bytes[i];
			sum += b;
		}
		return (byte) (sum & 0xff);
	}

	private byte[] hex2byte(String hex) {
		char[] data = hex.toCharArray();
		int len = data.length;
		if ((len & 0x01) != 0) {
			throw new RuntimeException("Odd number of characters.");
		}
		byte[] out = new byte[len >> 1];
		// two characters form the hex value.
		for (int i = 0, j = 0; j < len; i++) {
			int f = toDigit(data[j], j) << 4;
			j++;
			f = f | toDigit(data[j], j);
			j++;
			out[i] = (byte) (f & 0xFF);
		}
		return out;
	}

	private int toDigit(char ch, int index) {
		int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
		}
		return digit;
	}
}