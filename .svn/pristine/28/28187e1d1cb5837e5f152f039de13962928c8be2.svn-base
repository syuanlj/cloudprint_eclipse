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

public class MQTTClient2 implements Runnable {

	private String sn = null;

	public MQTTClient2(String sn) {
		this.sn = sn;
	}

	@Override
	public void run() {

		 String host = "tcp://139.129.27.116:61613";
		// String host = "tcp://192.168.1.17:61613";
//		String host = "tcp://123.57.57.102:61613";
		String userName = "admin";
		String passWord = "password";

		try {
			final MqttClient client = new MqttClient(host, sn);

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

									if (PrintProtocol.COMMAND_ID_PUSH_ORDER == commandId) {
										String orderSn = bytes2string(payload, PrintProtocol.PUSH_ORDER_SN_OFFSET, PrintProtocol.PUSH_ORDER_SN_LENGTH);
										String content = bytes2string(payload, PrintProtocol.PUSH_ORDER_CONTENT_OFFSET);
										System.out.println(new Date(System.currentTimeMillis()) + "----product----" + productSn + "----order----" + orderSn + "----content----" + content);

										byte[] orderSent = new byte[PrintProtocol.BODY_OFFSET + PrintProtocol.PUSH_ORDER_FEEDBACK_SN_LENGTH + 4 + PrintProtocol.PUSH_ORDER_FEEDBACK_STATUS_LENGTH];
										// 协议版本号
										orderSent[PrintProtocol.PROTOCOL_VERSION_OFFSET] = PrintProtocol.PROTOCOL_VERSION;
										// 设备类型
										orderSent[PrintProtocol.PRODUCT_TYPE_OFFSET] = PrintProtocol.PRODUCT_TYPE_PRINTER;
										// 协议类型
										orderSent[PrintProtocol.PROTOCOL_TYPE_OFFSET] = PrintProtocol.PROTOCOL_TYPE_UP;
										// 命令ID
										orderSent[PrintProtocol.COMMAND_ID_OFFSET] = PrintProtocol.COMMAND_ID_PUSH_ORDER_FEEDBACK;
										// 保留字
										orderSent[PrintProtocol.PROTOCOL_RESERVE_OFFSET] = PrintProtocol.RESERVE_CHARACTER;
										// 数据长度
										copyByte(orderSent, PrintProtocol.BODY_LENGTH_OFFSET, short2byte((short) (PrintProtocol.PUSH_ORDER_FEEDBACK_SN_LENGTH + 4 + PrintProtocol.PUSH_ORDER_FEEDBACK_STATUS_LENGTH)));

										// 任务ID
										copyByte(orderSent, PrintProtocol.PUSH_ORDER_FEEDBACK_SN_OFFSET, orderSn.getBytes("GB2312"));
										// 0x00:已接收OK
										orderSent[PrintProtocol.PUSH_ORDER_FEEDBACK_STATUS_OFFSET] = PrintProtocol.PUSH_ORDER_FEEDBACK_STATUS_SUCCESS;
										// TODO:和校验
										orderSent[PrintProtocol.CHECKSUM_OFFSET] = checksum(orderSent, PrintProtocol.BODY_OFFSET);

										MqttMessage message = new MqttMessage();
										message.setQos(2);
										message.setRetained(false);
										message.setPayload(orderSent);
										client.getTopic(PrintTopic.prefix_up + sn).publish(message);
										System.out.println(new Date(System.currentTimeMillis()) + "----------command id----------" + PrintProtocol.COMMAND_ID_PUSH_ORDER_FEEDBACK + "----------product sn----------" + productSn);

										Thread.sleep(2000);

										byte[] orderPrinted = new byte[PrintProtocol.BODY_OFFSET + PrintProtocol.PRINT_ORDER_FEEDBACK_SN_LENGTH + 4 + PrintProtocol.PRINT_ORDER_FEEDBACK_STATUS_LENGTH + PrintProtocol.PRINT_ORDER_FEEDBACK_ERROR_LENGTH];
										// 协议版本号
										orderPrinted[PrintProtocol.PROTOCOL_VERSION_OFFSET] = PrintProtocol.PROTOCOL_VERSION;
										// 设备类型
										orderPrinted[PrintProtocol.PRODUCT_TYPE_OFFSET] = PrintProtocol.PRODUCT_TYPE_PRINTER;
										// 协议类型
										orderPrinted[PrintProtocol.PROTOCOL_TYPE_OFFSET] = PrintProtocol.PROTOCOL_TYPE_UP;
										// 命令ID
										orderPrinted[PrintProtocol.COMMAND_ID_OFFSET] = PrintProtocol.COMMAND_ID_PRINT_ORDER_FEEDBACK;
										// 保留字
										orderPrinted[PrintProtocol.PROTOCOL_RESERVE_OFFSET] = PrintProtocol.RESERVE_CHARACTER;
										// 数据长度
										copyByte(orderPrinted, PrintProtocol.BODY_LENGTH_OFFSET, short2byte((short) (PrintProtocol.PRINT_ORDER_FEEDBACK_SN_LENGTH + 4 + PrintProtocol.PRINT_ORDER_FEEDBACK_STATUS_LENGTH + PrintProtocol.PRINT_ORDER_FEEDBACK_ERROR_LENGTH)));

										// 任务ID
										copyByte(orderPrinted, PrintProtocol.PRINT_ORDER_FEEDBACK_SN_OFFSET, orderSn.getBytes("GB2312"));
										// 0x00:已接收OK
										orderPrinted[PrintProtocol.PRINT_ORDER_FEEDBACK_STATUS_OFFSET] = PrintProtocol.PRINT_ORDER_FEEDBACK_STATUS_SUCCESS;
										// 异常码
										copyByte(orderPrinted, PrintProtocol.PRINT_ORDER_FEEDBACK_ERROR_OFFSET, padRight("0", PrintProtocol.RESERVE_CHARACTER, PrintProtocol.PRINT_ORDER_FEEDBACK_ERROR_LENGTH));
										// TODO:和校验
										orderPrinted[PrintProtocol.CHECKSUM_OFFSET] = checksum(orderPrinted, PrintProtocol.BODY_OFFSET);

										message.setPayload(orderPrinted);
										client.getTopic(PrintTopic.prefix_up + sn).publish(message);
										System.out.println(new Date(System.currentTimeMillis()) + "----------command id----------" + PrintProtocol.COMMAND_ID_PRINT_ORDER_FEEDBACK + "----------product sn----------" + productSn);
									} else if (PrintProtocol.COMMAND_ID_QUERY_PRINTER_STATUS == commandId) {
										MqttMessage message = new MqttMessage();
										message.setQos(2);
										message.setRetained(false);
										byte[] printerStatus = new byte[PrintProtocol.BODY_OFFSET + PrintProtocol.QUERY_PRINTER_STATUS_FEEDBACK_VOLTAGE_LENGTH + PrintProtocol.QUERY_PRINTER_STATUS_FEEDBACK_STATUS_LENGTH + PrintProtocol.QUERY_PRINTER_STATUS_FEEDBACK_ERROR_LENGTH];
										// 协议版本号
										printerStatus[PrintProtocol.PROTOCOL_VERSION_OFFSET] = PrintProtocol.PROTOCOL_VERSION;
										// 设备类型
										printerStatus[PrintProtocol.PRODUCT_TYPE_OFFSET] = PrintProtocol.PRODUCT_TYPE_PRINTER;
										// 协议类型
										printerStatus[PrintProtocol.PROTOCOL_TYPE_OFFSET] = PrintProtocol.PROTOCOL_TYPE_UP;
										// 命令ID
										printerStatus[PrintProtocol.COMMAND_ID_OFFSET] = PrintProtocol.COMMAND_ID_QUERY_PRINTER_STATUS_FEEDBACK;
										// 保留字
										printerStatus[PrintProtocol.PROTOCOL_RESERVE_OFFSET] = PrintProtocol.RESERVE_CHARACTER;
										// 数据长度
										copyByte(printerStatus, PrintProtocol.BODY_LENGTH_OFFSET, short2byte((short) (PrintProtocol.QUERY_PRINTER_STATUS_FEEDBACK_VOLTAGE_LENGTH + PrintProtocol.QUERY_PRINTER_STATUS_FEEDBACK_STATUS_LENGTH + PrintProtocol.QUERY_PRINTER_STATUS_FEEDBACK_ERROR_LENGTH)));

										// 电压（单位0.1V）
										copyByte(printerStatus, PrintProtocol.QUERY_PRINTER_STATUS_FEEDBACK_VOLTAGE_OFFSET, "13".getBytes("GB2312"));
										// 已接收OK
										printerStatus[PrintProtocol.QUERY_PRINTER_STATUS_FEEDBACK_STATUS_OFFSET] = PrintProtocol.PRINTER_STATUS_NORMAL;
										// 异常码
										copyByte(printerStatus, PrintProtocol.QUERY_PRINTER_STATUS_FEEDBACK_ERROR_OFFSET, padRight("0", PrintProtocol.RESERVE_CHARACTER, PrintProtocol.QUERY_PRINTER_STATUS_FEEDBACK_ERROR_LENGTH));
										// 和校验
										printerStatus[PrintProtocol.CHECKSUM_OFFSET] = checksum(printerStatus, PrintProtocol.BODY_OFFSET);

										message.setPayload(printerStatus);
										client.getTopic(PrintTopic.prefix_up + sn).publish(message);
										System.out.println(new Date(System.currentTimeMillis()) + "----------command id----------" + PrintProtocol.COMMAND_ID_QUERY_PRINTER_STATUS_FEEDBACK + "----------product sn----------" + productSn);
									} else {
										System.out.println("invalid command id : " + commandId);
									}
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
			client.subscribe(PrintTopic.prefix_down + sn);
			System.out.println(sn + " connected.");

			MqttMessage message = new MqttMessage();
			message.setQos(2);
			message.setRetained(false);
			byte[] printerBoot = new byte[PrintProtocol.BODY_OFFSET + PrintProtocol.BOOT_PRINTER_CATEGORY_LENGTH + PrintProtocol.BOOT_VERSION_LENGTH + PrintProtocol.BOOT_SOFTWARE_VERSION_LENGTH + PrintProtocol.BOOT_HARDWARE_VERSION_LENGTH + PrintProtocol.BOOT_FONT_VERSION_LENGTH + PrintProtocol.BOOT_OEM_SN_LENGTH + PrintProtocol.BOOT_STATUS_LENGTH + PrintProtocol.BOOT_ERROR_LENGTH];
			// 协议版本号
			printerBoot[PrintProtocol.PROTOCOL_VERSION_OFFSET] = PrintProtocol.PROTOCOL_VERSION;
			// 设备类型
			printerBoot[PrintProtocol.PRODUCT_TYPE_OFFSET] = PrintProtocol.PRODUCT_TYPE_PRINTER;
			// 协议类型
			printerBoot[PrintProtocol.PROTOCOL_TYPE_OFFSET] = PrintProtocol.PROTOCOL_TYPE_UP;
			// 命令ID
			printerBoot[PrintProtocol.COMMAND_ID_OFFSET] = PrintProtocol.COMMAND_ID_BOOT_PRINTER;
			// 保留字
			printerBoot[PrintProtocol.PROTOCOL_RESERVE_OFFSET] = PrintProtocol.RESERVE_CHARACTER;
			// 数据长度
			copyByte(printerBoot, PrintProtocol.BODY_LENGTH_OFFSET, short2byte((short) (PrintProtocol.BOOT_PRINTER_CATEGORY_LENGTH + PrintProtocol.BOOT_VERSION_LENGTH + PrintProtocol.BOOT_SOFTWARE_VERSION_LENGTH + PrintProtocol.BOOT_HARDWARE_VERSION_LENGTH + PrintProtocol.BOOT_FONT_VERSION_LENGTH + PrintProtocol.BOOT_OEM_SN_LENGTH + PrintProtocol.BOOT_STATUS_LENGTH + PrintProtocol.BOOT_ERROR_LENGTH)));

			// 打印机型号
			copyByte(printerBoot, PrintProtocol.BOOT_PRINTER_CATEGORY_OFFSET, padRight("Cprint1", PrintProtocol.RESERVE_CHARACTER, PrintProtocol.BOOT_PRINTER_CATEGORY_LENGTH));
			// Boot版本
			copyByte(printerBoot, PrintProtocol.BOOT_VERSION_OFFSET, padRight("1", PrintProtocol.RESERVE_CHARACTER, PrintProtocol.BOOT_VERSION_LENGTH));
			// 软件版本
			copyByte(printerBoot, PrintProtocol.BOOT_SOFTWARE_VERSION_OFFSET, padRight("2", PrintProtocol.RESERVE_CHARACTER, PrintProtocol.BOOT_SOFTWARE_VERSION_LENGTH));
			// 硬件版本
			copyByte(printerBoot, PrintProtocol.BOOT_HARDWARE_VERSION_OFFSET, padRight("3", PrintProtocol.RESERVE_CHARACTER, PrintProtocol.BOOT_HARDWARE_VERSION_LENGTH));
			// 字库版本
			copyByte(printerBoot, PrintProtocol.BOOT_FONT_VERSION_OFFSET, padRight("4", PrintProtocol.RESERVE_CHARACTER, PrintProtocol.BOOT_FONT_VERSION_LENGTH));
			// OEM编号
			copyByte(printerBoot, PrintProtocol.BOOT_OEM_SN_OFFSET, padRight("5", PrintProtocol.RESERVE_CHARACTER, PrintProtocol.BOOT_OEM_SN_LENGTH));
			// 状态
			printerBoot[PrintProtocol.BOOT_STATUS_OFFSET] = PrintProtocol.PRINTER_STATUS_NORMAL;
			// 异常码
			copyByte(printerBoot, PrintProtocol.BOOT_ERROR_OFFSET, padRight("0", PrintProtocol.RESERVE_CHARACTER, PrintProtocol.BOOT_ERROR_LENGTH));
			// 和校验
			printerBoot[PrintProtocol.CHECKSUM_OFFSET] = checksum(printerBoot, PrintProtocol.BODY_OFFSET);

			message.setPayload(printerBoot);
			client.getTopic(PrintTopic.prefix_up + sn).publish(message);
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