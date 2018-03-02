package com.nfet.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.nfet.PrintProtocol;
import com.nfet.PrintTopic;
import com.nfet.Setting;
import com.nfet.UpgradeProtocol;
import com.nfet.UpgradeTopic;
import com.nfet.entity.Order;
import com.nfet.entity.Product;
import com.nfet.thread.NextOrderPush;
import com.nfet.thread.SaveUpgradeLog;
import com.nfet.thread.SingleOrderPush;
import com.nfet.thread.SingleOrderSent;
import com.nfet.thread.StartServer;
import com.nfet.thread.UpdateProduct;

import sun.misc.BASE64Decoder;

public class PushManager {

	private static PushManager instance = null;

	private MqttClient server = null;
	private Map<String, MqttClient> productMap = null;
	private Map<String, Product> productInfoMap = null;
	private Map<String, Product.WorkStatus> productStatusMap = null;
	private Map<String, Long> productTimeMap = null;
	private Map<String, String> orderSnMap = null;
	private Map<String, Order> orderInfoMap = null;
	private Map<String, Order> orderInfoBackupMap = null;
	private Map<String, Order> orderPersistMap = null;
	private Map<String, String> settingMap = null;
	private boolean isPersisting = false;
	private long intervalPushOrderTime = 0l;
	private long intervalPersistOrderTime = 0l;

	private PushManager() throws MqttException, IOException {
		this.productMap = new HashMap<String, MqttClient>();
		this.productInfoMap = new HashMap<String, Product>();
		this.productStatusMap = new HashMap<String, Product.WorkStatus>();
		this.productTimeMap = new HashMap<String, Long>();
		this.orderSnMap = new HashMap<String, String>();
		this.orderInfoMap = new HashMap<String, Order>();
		this.orderInfoBackupMap = new HashMap<String, Order>();
		this.orderPersistMap = new HashMap<String, Order>();
		this.settingMap = new HashMap<String, String>();
		buildServer();
		buildProductMap(true);
		System.setOut(new PrintStream(new FileOutputStream(new File("D:\\cloudprint_" + System.currentTimeMillis() + ".log"))));
		System.out.println(new Date() + "----PushManager init end----");
	}

	public synchronized static PushManager getInstance() {
		if (instance == null) {
			try {
				instance = new PushManager();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (MqttException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	private MqttClient initServer(String serverHost, String serverUsername, String serverPassword) throws MqttException {
		MqttClient server = new MqttClient(serverHost, serverUsername, new MemoryPersistence());
		MqttConnectOptions options = new MqttConnectOptions();
		options.setCleanSession(true);
		options.setUserName(serverUsername);
		options.setPassword(serverPassword.toCharArray());
		options.setConnectionTimeout(60000);
		options.setKeepAliveInterval(60000);
		server.setCallback(new MqttCallback() {
			@Override
			public void connectionLost(Throwable cause) {
				System.out.println("connectionLost-----------");
				cause.printStackTrace();
				Runnable runnable = new StartServer();
				ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
				service.schedule(runnable, 0, TimeUnit.SECONDS);
				service.shutdown();
			}

			@Override
			public void deliveryComplete(IMqttDeliveryToken token) {
			}

			@Override
			public void messageArrived(String topic, MqttMessage mqttMessage) throws IOException {
				byte[] payload = mqttMessage.getPayload();

				if (topic.startsWith(PrintTopic.prefix_up)) {
					byte protocolType = payload[PrintProtocol.PROTOCOL_TYPE_OFFSET];

					if (PrintProtocol.PROTOCOL_TYPE_UP == protocolType) {
						short bodyLength = bytes2short(payload, PrintProtocol.BODY_LENGTH_OFFSET, PrintProtocol.BODY_LENGTH_LENGTH);

						if (bodyLength == payload.length - PrintProtocol.BODY_OFFSET) {
							byte checksum = payload[PrintProtocol.CHECKSUM_OFFSET];

							if (checksum == checksum(payload, PrintProtocol.BODY_OFFSET)) {
								byte commandId = payload[PrintProtocol.COMMAND_ID_OFFSET];
								String productSn = topic.replace(PrintTopic.prefix_up, "");
								// CluSTer
								String serverNo = PushManager.getInstance().getSettingMap("serverNo");
								if (serverNo == null) {
									Setting setting = SettingUtils.get();
									serverNo = setting.getServerNo();
									PushManager.getInstance().putSettingMap("serverNo", serverNo);
								}
								String serverCount = PushManager.getInstance().getSettingMap("serverCount");
								if (serverCount == null) {
									Setting setting = SettingUtils.get();
									serverCount = setting.getServerCount();
									PushManager.getInstance().putSettingMap("serverCount", serverCount);
								}

								int hashCode = Math.abs(productSn.hashCode());
								if (Integer.parseInt(serverNo) == hashCode % Integer.parseInt(serverCount)) {
									System.out.println(new Date(System.currentTimeMillis()) + "----------arrived command id----------" + commandId + "----------topic----------" + topic);
									if (PrintProtocol.COMMAND_ID_PUSH_ORDER_FEEDBACK == commandId) {
										String orderSn = bytes2string(payload, PrintProtocol.PUSH_ORDER_FEEDBACK_SN_OFFSET, PrintProtocol.PUSH_ORDER_FEEDBACK_SN_LENGTH).trim();
										byte status = payload[PrintProtocol.PUSH_ORDER_FEEDBACK_STATUS_OFFSET];

										if (PrintProtocol.PUSH_ORDER_FEEDBACK_STATUS_SUCCESS == status) {
											Runnable runnable = new SingleOrderSent(productSn, orderSn);
											ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
											service.schedule(runnable, 0, TimeUnit.SECONDS);
											service.shutdown();
										} else {
											System.out.println(new Date(System.currentTimeMillis()) + "----------arrived command id 1 product sn---------" + productSn + "----------status----------" + status);
										}
									} else if (PrintProtocol.COMMAND_ID_PRINT_ORDER_FEEDBACK == commandId) {
										String orderSn = bytes2string(payload, PrintProtocol.PRINT_ORDER_FEEDBACK_SN_OFFSET, PrintProtocol.PRINT_ORDER_FEEDBACK_SN_LENGTH).trim();
										byte status = payload[PrintProtocol.PRINT_ORDER_FEEDBACK_STATUS_OFFSET];
										String error = bytes2short(payload, PrintProtocol.PRINT_ORDER_FEEDBACK_ERROR_OFFSET, PrintProtocol.PRINT_ORDER_FEEDBACK_ERROR_LENGTH) + "";

										if (PrintProtocol.PRINT_ORDER_FEEDBACK_STATUS_SUCCESS == status) {
											Runnable runnable = new NextOrderPush(productSn, orderSn, true);
											ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
											service.schedule(runnable, 0, TimeUnit.SECONDS);
											service.shutdown();
										} else {
											Runnable runnable1 = new NextOrderPush(productSn, orderSn, false);
											ScheduledExecutorService service1 = Executors.newSingleThreadScheduledExecutor();
											service1.schedule(runnable1, 0, TimeUnit.SECONDS);
											service1.shutdown();

											PushManager.getInstance().putProductStatusMap(productSn, Product.WorkStatus.abnormal);
											Runnable runnable2 = new UpdateProduct(productSn, null, status, error);
											ScheduledExecutorService service2 = Executors.newSingleThreadScheduledExecutor();
											service2.schedule(runnable2, 0, TimeUnit.SECONDS);
											service2.shutdown();
										}
									} else if (PrintProtocol.COMMAND_ID_QUERY_PRINTER_STATUS_FEEDBACK == commandId) {
										String voltage = bytes2string(payload, PrintProtocol.QUERY_PRINTER_STATUS_FEEDBACK_VOLTAGE_OFFSET, PrintProtocol.QUERY_PRINTER_STATUS_FEEDBACK_VOLTAGE_LENGTH);
										byte status = payload[PrintProtocol.QUERY_PRINTER_STATUS_FEEDBACK_STATUS_OFFSET];
										String error = bytes2short(payload, PrintProtocol.QUERY_PRINTER_STATUS_FEEDBACK_ERROR_OFFSET, PrintProtocol.QUERY_PRINTER_STATUS_FEEDBACK_ERROR_LENGTH) + "";

										if (PrintProtocol.PRINTER_STATUS_NORMAL == status) {
											if (Product.WorkStatus.abnormal.equals(PushManager.getInstance().getProductStatusMap(productSn))) {
												PushManager.getInstance().putProductStatusMap(productSn, Product.WorkStatus.normal);
												Runnable runnable1 = new UpdateProduct(productSn, null, status, error);
												ScheduledExecutorService service1 = Executors.newSingleThreadScheduledExecutor();
												service1.schedule(runnable1, 0, TimeUnit.SECONDS);
												service1.shutdown();
											}
											Runnable runnable2 = new SingleOrderPush(productSn);
											ScheduledExecutorService service2 = Executors.newSingleThreadScheduledExecutor();
											service2.schedule(runnable2, 0, TimeUnit.SECONDS);
											service2.shutdown();
										} else if (PrintProtocol.PRINTER_STATUS_ABNORMAL == status) {
											System.out.println(new Date(System.currentTimeMillis()) + "----------arrived command id 7 product sn---------" + productSn + "----------status----------" + status);
											if (!Product.WorkStatus.abnormal.equals(PushManager.getInstance().getProductStatusMap(productSn))) {
												PushManager.getInstance().putProductStatusMap(productSn, Product.WorkStatus.abnormal);
												Runnable runnable1 = new UpdateProduct(productSn, voltage, status, error);
												ScheduledExecutorService service1 = Executors.newSingleThreadScheduledExecutor();
												service1.schedule(runnable1, 0, TimeUnit.SECONDS);
												service1.shutdown();
											}
										} else if (PrintProtocol.PRINTER_STATUS_BUSY == status) {
											System.out.println(new Date(System.currentTimeMillis()) + "----------arrived command id 7 product sn---------" + productSn + "----------status----------" + status);
											if (Product.WorkStatus.abnormal.equals(PushManager.getInstance().getProductStatusMap(productSn))) {
												Runnable runnable1 = new UpdateProduct(productSn, voltage, status, error);
												ScheduledExecutorService service1 = Executors.newSingleThreadScheduledExecutor();
												service1.schedule(runnable1, 0, TimeUnit.SECONDS);
												service1.shutdown();
											}
										} else {
											System.out.println("invalid printer status : " + status);
										}
									} else if (PrintProtocol.COMMAND_ID_UPLOAD_PRINTER_STATUS == commandId) {
										byte status = payload[PrintProtocol.UPLOAD_PRINTER_STATUS_STATUS_OFFSET];
										String error = bytes2short(payload, PrintProtocol.UPLOAD_PRINTER_STATUS_ERROR_OFFSET, PrintProtocol.UPLOAD_PRINTER_STATUS_ERROR_LENGTH) + "";

										System.out.println("----command id 8----product sn----" + productSn + "----status----" + status + "----error----" + error);
										if (PrintProtocol.PRINTER_STATUS_NORMAL == status) {
											if (Product.WorkStatus.abnormal.equals(PushManager.getInstance().getProductStatusMap(productSn))) {
												PushManager.getInstance().putProductStatusMap(productSn, Product.WorkStatus.normal);
												Runnable runnable1 = new UpdateProduct(productSn, null, status, error);
												ScheduledExecutorService service1 = Executors.newSingleThreadScheduledExecutor();
												service1.schedule(runnable1, 0, TimeUnit.SECONDS);
												service1.shutdown();
											} else {
												Long productTime = PushManager.getInstance().getProductTimeMap(productSn);
												if (productTime == null || System.currentTimeMillis() - productTime > 120 * 1000) {
													PushManager.getInstance().putProductStatusMap(productSn, Product.WorkStatus.normal);
												}
											}
										} else if (PrintProtocol.PRINTER_STATUS_ABNORMAL == status) {
											if (!Product.WorkStatus.abnormal.equals(PushManager.getInstance().getProductStatusMap(productSn))) {
												PushManager.getInstance().putProductStatusMap(productSn, Product.WorkStatus.abnormal);
												Runnable runnable = new UpdateProduct(productSn, null, status, error);
												ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
												service.schedule(runnable, 0, TimeUnit.SECONDS);
												service.shutdown();
											}
										} else if (PrintProtocol.PRINTER_STATUS_BUSY == status) {
											if (Product.WorkStatus.abnormal.equals(PushManager.getInstance().getProductStatusMap(productSn))) {
												Runnable runnable = new UpdateProduct(productSn, null, status, error);
												ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
												service.schedule(runnable, 0, TimeUnit.SECONDS);
												service.shutdown();
											}
										} else {
											System.out.println("invalid printer status : " + status);
										}
									} else if (PrintProtocol.COMMAND_ID_BOOT_PRINTER == commandId) {
										String productCategory = bytes2string(payload, PrintProtocol.BOOT_PRINTER_CATEGORY_OFFSET, PrintProtocol.BOOT_PRINTER_CATEGORY_LENGTH).trim();
										String bootVersion = bytes2string(payload, PrintProtocol.BOOT_VERSION_OFFSET, PrintProtocol.BOOT_VERSION_LENGTH);
										String softwareVersion = bytes2string(payload, PrintProtocol.BOOT_SOFTWARE_VERSION_OFFSET, PrintProtocol.BOOT_SOFTWARE_VERSION_LENGTH);
										String hardwareVersion = bytes2string(payload, PrintProtocol.BOOT_HARDWARE_VERSION_OFFSET, PrintProtocol.BOOT_HARDWARE_VERSION_LENGTH);
										String fontVersion = bytes2string(payload, PrintProtocol.BOOT_FONT_VERSION_OFFSET, PrintProtocol.BOOT_FONT_VERSION_LENGTH);
										String oemSn = bytes2string(payload, PrintProtocol.BOOT_OEM_SN_OFFSET, PrintProtocol.BOOT_OEM_SN_LENGTH);
										byte status = payload[PrintProtocol.BOOT_STATUS_OFFSET];
										String error = null;
										if (PrintProtocol.PRINTER_STATUS_ABNORMAL == status) {
											error = bytes2short(payload, PrintProtocol.BOOT_ERROR_OFFSET, PrintProtocol.BOOT_ERROR_LENGTH) + "";
										}
										putProductMap(productSn, getServer());
										if (PrintProtocol.PRINTER_STATUS_NORMAL == status) {
											PushManager.getInstance().putProductStatusMap(productSn, Product.WorkStatus.normal);
										} else if (PrintProtocol.PRINTER_STATUS_ABNORMAL == status) {
											PushManager.getInstance().putProductStatusMap(productSn, Product.WorkStatus.abnormal);
										} else if (PrintProtocol.PRINTER_STATUS_BUSY == status) {
											PushManager.getInstance().putProductStatusMap(productSn, Product.WorkStatus.busy);
										} else {
											System.out.println("invalid printer status : " + status);
										}

										Runnable runnable1 = new UpdateProduct(productSn, bootVersion, softwareVersion, hardwareVersion, fontVersion, oemSn, null, status, error);
										ScheduledExecutorService service1 = Executors.newSingleThreadScheduledExecutor();
										service1.schedule(runnable1, 0, TimeUnit.SECONDS);
										service1.shutdown();
									} else {
										System.out.println("invalid command id : " + commandId);
									}
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
				} else if (topic.startsWith(UpgradeTopic.prefix_up)) {
					short bodyLength = bytes2short(payload, UpgradeProtocol.BODY_LENGTH_OFFSET, UpgradeProtocol.BODY_LENGTH_LENGTH);

					if (bodyLength == payload.length - UpgradeProtocol.BODY_OFFSET) {
						byte checksum = payload[UpgradeProtocol.CHECKSUM_OFFSET];

						if (checksum == checksum(payload, UpgradeProtocol.BODY_OFFSET)) {
							String productSn = topic.replace(UpgradeTopic.prefix_up, "");
							byte code = payload[UpgradeProtocol.UPGRADE_CODE_OFFSET];
							System.out.println(new Date(System.currentTimeMillis()) + "----------upgrade code----------" + code + "----------topic----------" + topic);

							Runnable runnable = new SaveUpgradeLog(productSn, code);
							ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
							service.schedule(runnable, 0, TimeUnit.SECONDS);
							service.shutdown();
						} else {
							System.out.println("invalid checksum : " + checksum);
						}
					} else {
						System.out.println("invalid body length : " + bodyLength);
					}
				} else {
					System.out.println("invalid topic : " + topic);
				}
			}
		});
		server.connect(options);
		return server;
	}

	public void buildServer() throws MqttException {
		Setting setting = SettingUtils.get();
		String serverHost = setting.getServerHost();
		String serverUsername = setting.getServerUsername();
		String serverPassword = setting.getServerPassword();

		if (this.server == null || !this.server.isConnected()) {
			this.server = initServer(serverHost, serverUsername, serverPassword);
			try {
				this.server.subscribe(PrintTopic.prefix_up + "#");
				this.server.subscribe(UpgradeTopic.prefix_up + "#");
			} catch (MqttException e) {
				e.printStackTrace();
			}
		}
	}

	public void buildProductMap(boolean flag) {
		HashMap<String, MqttClient> tempMap = new HashMap<String, MqttClient>();
		JSONObject jo = getProductList(this.server.getServerURI());
		JSONArray ja = jo.getJSONArray("rows");
		for (int i = 0; i < ja.size(); i++) {
			String productSn = ja.getJSONObject(i).getString("protocol_session_id");
			if (this.server.getClientId().equals(productSn)) {
				continue;
			}
			tempMap.put(productSn, this.server);
			if (flag) {
				this.putProductStatusMap(productSn, Product.WorkStatus.normal);
			}
		}
		this.productMap = tempMap;
	}

	private JSONObject getProductList(String url) {
		Setting setting = SettingUtils.get();
		String adminUsername = setting.getAdminUsername();
		String adminPassword = setting.getAdminPassword();
		HttpClient httpClient = new DefaultHttpClient();
		JSONObject jsonResult = null;

		String postUrl = url.replace("tcp", "http").replace("61613", "61680") + "/api/json/session/signin";
		String getUrl = url.replace("tcp", "http").replace("61613", "61680") + "/broker/connections?ps=100000";
		HttpPost httpPost = new HttpPost(postUrl);
		HttpGet httpGet = new HttpGet(getUrl);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("username", adminUsername));
		parameters.add(new BasicNameValuePair("password", adminPassword));

		try {
			UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(parameters);
			httpPost.setEntity(formEntiry);
			HttpResponse postResult = httpClient.execute(httpPost);
			httpPost.releaseConnection();

			if (postResult.getStatusLine().getStatusCode() == 200) {
				HttpResponse getResult = httpClient.execute(httpGet);
				if (getResult.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String strResult = EntityUtils.toString(getResult.getEntity());
					jsonResult = JSONObject.fromObject(strResult);
				} else {
					System.out.println("get method STATUS_CODE:" + getResult.getStatusLine().getStatusCode());
				}
				return jsonResult;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonResult;
	}

	// public void buildProductMap(boolean flag) {
	// HashMap<String, MqttClient> tempMap = new HashMap<String, MqttClient>();
	// JSONObject jo = getProductList(this.server.getServerURI());
	// JSONArray ja = jo.getJSONArray("result");
	// for (int i = 0; i < ja.size(); i++) {
	// String productSn = ja.getJSONObject(i).getString("clientId");
	// if (this.server.getClientId().equals(productSn)) {
	// continue;
	// }
	// tempMap.put(productSn, this.server);
	// if (flag) {
	// this.putProductStatusMap(productSn, Product.WorkStatus.normal);
	// }
	// }
	// this.productMap = tempMap;
	// }
	//
	// private JSONObject getProductList(String url) {
	//
	// Setting setting = SettingUtils.get();
	// String adminUsername = setting.getAdminUsername();
	// String adminPassword = setting.getAdminPassword();
	// DefaultHttpClient httpClient = new DefaultHttpClient();
	// httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, new
	// UsernamePasswordCredentials(adminUsername, adminPassword));
	// JSONObject jsonResult = null;
	//
	// String postUrl = url.replace("tcp", "http").replace("1883", "18083") +
	// "/api/clients";
	// HttpPost httpPost = new HttpPost(postUrl);
	// httpPost.setHeader("Authorization", "Basic YWRtaW46cHVibGlj");
	// List<NameValuePair> parameters = new ArrayList<NameValuePair>();
	// parameters.add(new BasicNameValuePair("page_size", "1000000"));
	// parameters.add(new BasicNameValuePair("curr_page", "1"));
	// parameters.add(new BasicNameValuePair("client_key", ""));
	//
	// try {
	// UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(parameters,
	// "UTF-8");
	// httpPost.setEntity(formEntiry);
	// HttpResponse postResult = httpClient.execute(httpPost);
	// if (postResult.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	// String strResult = EntityUtils.toString(postResult.getEntity());
	// jsonResult = JSONObject.fromObject(strResult);
	// } else {
	// System.out.println("get method STATUS_CODE:" +
	// postResult.getStatusLine().getStatusCode());
	// }
	// return jsonResult;
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return jsonResult;
	// }

	private byte[] preparePrintPayload(byte commandId, String orderSn, String content) {

		byte[] message = null;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			short bodyLength = 0;
			if (PrintProtocol.COMMAND_ID_PUSH_ORDER == commandId) {
				byte[] contentBytes = decoder.decodeBuffer(content);
				bodyLength = (short) (PrintProtocol.PUSH_ORDER_SN_LENGTH + PrintProtocol.PUSH_ORDER_RESERVE_LENGTH + contentBytes.length);
			} else if (PrintProtocol.COMMAND_ID_RESET_PRINTER == commandId) {
				bodyLength = (short) (PrintProtocol.RESET_PRINTER_RESERVE_LENGTH);
			} else if (PrintProtocol.COMMAND_ID_QUERY_PRINTER_STATUS == commandId) {
				bodyLength = (short) (PrintProtocol.QUERY_PRINTER_STATUS_RESERVE_LENGTH);
			} else if (PrintProtocol.COMMAND_ID_RESET_FACTORY == commandId) {
				bodyLength = (short) (PrintProtocol.RESET_FACTORY_RESERVE_LENGTH);
			} else {
				System.out.println("invalid command id : " + commandId);
			}
			message = new byte[PrintProtocol.BODY_OFFSET + bodyLength];

			message[PrintProtocol.PROTOCOL_VERSION_OFFSET] = PrintProtocol.PROTOCOL_VERSION;
			message[PrintProtocol.PRODUCT_TYPE_OFFSET] = PrintProtocol.PRODUCT_TYPE_PRINTER;
			message[PrintProtocol.PROTOCOL_TYPE_OFFSET] = PrintProtocol.PROTOCOL_TYPE_DOWN;
			message[PrintProtocol.COMMAND_ID_OFFSET] = commandId;
			message[PrintProtocol.PROTOCOL_RESERVE_OFFSET] = PrintProtocol.RESERVE_CHARACTER;
			copyByte(message, PrintProtocol.BODY_LENGTH_OFFSET, short2byte(bodyLength));

			if (PrintProtocol.COMMAND_ID_PUSH_ORDER == commandId) {
				copyByte(message, PrintProtocol.PUSH_ORDER_SN_OFFSET, orderSn.getBytes("GB2312"));
				byte[] contentBytes = decoder.decodeBuffer(content);
				copyByte(message, PrintProtocol.PUSH_ORDER_CONTENT_OFFSET, contentBytes);
			} else if (PrintProtocol.COMMAND_ID_RESET_PRINTER == commandId) {
				message[PrintProtocol.RESET_PRINTER_RESERVE_OFFSET] = PrintProtocol.RESERVE_CHARACTER;
			} else if (PrintProtocol.COMMAND_ID_QUERY_PRINTER_STATUS == commandId) {
				message[PrintProtocol.QUERY_PRINTER_STATUS_RESERVE_OFFSET] = PrintProtocol.RESERVE_CHARACTER;
			} else if (PrintProtocol.COMMAND_ID_RESET_FACTORY == commandId) {
				message[PrintProtocol.RESET_FACTORY_RESERVE_OFFSET] = PrintProtocol.RESERVE_CHARACTER;
			} else {
				System.out.println("invalid command id : " + commandId);
			}

			message[PrintProtocol.CHECKSUM_OFFSET] = checksum(message, PrintProtocol.BODY_OFFSET);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return message;
	}

	private byte[] prepareUpgradePayload(String productCategoryName, String version, String length, String md5, String url, String username, String password) {

		byte[] message = null;
		try {
			short bodyLength = (short) (UpgradeProtocol.PRODUCT_CATEGORY_LENGTH + UpgradeProtocol.FIRMWARE_VERSION_LENGTH + UpgradeProtocol.FILE_LENGTH_LENGTH + UpgradeProtocol.MD5_LENGTH + UpgradeProtocol.URL_LENGTH_LENGTH + UpgradeProtocol.FTP_USERNAME_LENGTH + UpgradeProtocol.FTP_PASSWORD_LENGTH + url.getBytes("GB2312").length);
			message = new byte[UpgradeProtocol.BODY_OFFSET + bodyLength];

			message[UpgradeProtocol.PROTOCOL_VERSION_OFFSET] = UpgradeProtocol.PROTOCOL_VERSION;
			message[UpgradeProtocol.PRODUCT_TYPE_OFFSET] = UpgradeProtocol.PRODUCT_TYPE_PRINTER;
			copyByte(message, UpgradeProtocol.BODY_LENGTH_OFFSET, short2byte(bodyLength));
			copyByte(message, UpgradeProtocol.PRODUCT_CATEGORY_OFFSET, padRight(productCategoryName, UpgradeProtocol.RESERVE_CHARACTER, UpgradeProtocol.PRODUCT_CATEGORY_LENGTH));
			copyByte(message, UpgradeProtocol.FIRMWARE_VERSION_OFFSET, version.getBytes("GB2312"));
			copyByte(message, UpgradeProtocol.FILE_LENGTH_OFFSET, int2byte(new Integer(length).intValue()));
			copyByte(message, UpgradeProtocol.MD5_OFFSET, md5.getBytes("GB2312"));
			copyByte(message, UpgradeProtocol.URL_LENGTH_OFFSET, digit2byte(url.length()));
			copyByte(message, UpgradeProtocol.FTP_USERNAME_OFFSET, padRight(username, UpgradeProtocol.RESERVE_CHARACTER, UpgradeProtocol.FTP_USERNAME_LENGTH));
			copyByte(message, UpgradeProtocol.FTP_PASSWORD_OFFSET, padRight(password, UpgradeProtocol.RESERVE_CHARACTER, UpgradeProtocol.FTP_PASSWORD_LENGTH));
			copyByte(message, UpgradeProtocol.URL_OFFSET, url.getBytes("GB2312"));

			message[UpgradeProtocol.CHECKSUM_OFFSET] = checksum(message, UpgradeProtocol.BODY_OFFSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return message;
	}

	public void pushPrintMessage(String productSn, byte commandId, String orderSn, String content) {
		try {
			System.out.println(new Date(System.currentTimeMillis()) + ", productSn----" + productSn + ", commandId----" + commandId + ", orderSn----" + orderSn);
			byte[] payload = preparePrintPayload(commandId, orderSn, content);
			pushMessage(PrintTopic.prefix_down, productSn, payload);
		} catch (Exception e) {
			try {
				e.printStackTrace(new PrintStream(new File("D:\\error_" + System.currentTimeMillis() + ".log")));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}

	}

	public void pushUpgradeMessage(String productSn, String productCategoryName, String version, String length, String md5, String url, String username, String password) {
		byte[] payload = prepareUpgradePayload(productCategoryName, version, length, md5, url, username, password);
		pushMessage(UpgradeTopic.prefix_down, productSn, payload);
	}

	private void pushMessage(String prefix, String productSn, byte[] payload) {
		try {
			this.putProductTimeMap(productSn, System.currentTimeMillis());
			MqttTopic topic = this.server.getTopic(prefix + productSn);
			MqttMessage message = new MqttMessage();
			message.setQos(1);
			message.setRetained(false);
			message.setPayload(payload);
			MqttDeliveryToken token = topic.publish(message);
			token.waitForCompletion();
		} catch (MqttException e) {
			try {
				e.printStackTrace(new PrintStream(new File("D:\\error_" + System.currentTimeMillis() + ".log")));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void disconnect() {
		try {
			this.server.disconnect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	private MqttClient getServer() {
		return this.server;
	}

	public void putProductMap(String productSn, MqttClient server) {
		this.productMap.put(productSn, server);
	}

	public Map<String, MqttClient> getProductMap() {
		return this.productMap;
	}

	public void putProductInfoMap(String sn, Product product) {
		this.productInfoMap.put(sn, product);
	}

	public Product getProductInfoMap(String productSn) {
		return this.productInfoMap.get(productSn);
	}

	public int getProductInfoMapSize() {
		return this.productInfoMap.size();
	}

	public void putProductStatusMap(String productSn, Product.WorkStatus productStatus) {
		this.productStatusMap.put(productSn, productStatus);
	}

	public Product.WorkStatus getProductStatusMap(String productSn) {
		return this.productStatusMap.get(productSn);
	}

	public void putOrderSnMap(String productSn, String orderSn) {
		this.orderSnMap.put(productSn, orderSn);
	}

	public String getOrderSnMap(String productSn) {
		return this.orderSnMap.get(productSn);
	}

	public void removeOrderSnMap(String productSn) {
		this.orderSnMap.remove(productSn);
	}

	public Set<String> getOrderSnMapKey() {
		return this.orderSnMap.keySet();
	}

	public void putOrderInfoMap(String sn, Order order) {
		this.orderInfoMap.put(sn, order);
	}

	public Order getOrderInfoMap(String orderSn) {
		return this.orderInfoMap.get(orderSn);
	}

	public int getOrderInfoMapSize() {
		return this.orderInfoMap.size();
	}

	public void removeOrderInfoMap(String orderSn) {
		this.orderInfoMap.remove(orderSn);
	}

	public void putOrderInfoBackupMap(String sn, Order order) {
		this.orderInfoBackupMap.put(sn, order);
	}

	public Order getOrderInfoBackupMap(String orderSn) {
		return this.orderInfoBackupMap.get(orderSn);
	}

	public void removeOrderInfoBackupMap(String orderSn) {
		this.orderInfoBackupMap.remove(orderSn);
	}

	public void putOrderPersistMap(String orderSn, Order order) {
		this.orderPersistMap.put(orderSn, order);
	}

	public Order getOrderPersistMap(String orderSn) {
		return this.orderPersistMap.get(orderSn);
	}

	public void removeOrderPersistMap(String orderSn) {
		this.orderPersistMap.remove(orderSn);
	}

	public Set<String> getOrderPersistMapKey() {
		return this.orderPersistMap.keySet();
	}

	public void putProductTimeMap(String productSn, Long time) {
		this.productTimeMap.put(productSn, time);
	}

	public Long getProductTimeMap(String productSn) {
		return this.productTimeMap.get(productSn);
	}

	public void putSettingMap(String name, String value) {
		this.settingMap.put(name, value);
	}

	public String getSettingMap(String name) {
		return this.settingMap.get(name);
	}

	public boolean isPersisting() {
		return isPersisting;
	}

	public void setPersisting(boolean isPersisting) {
		this.isPersisting = isPersisting;
	}

	public long getIntervalPushOrderTime() {
		return intervalPushOrderTime;
	}

	public void setIntervalPushOrderTime(long intervalPushOrderTime) {
		this.intervalPushOrderTime = intervalPushOrderTime;
	}

	public long getIntervalPersistOrderTime() {
		return intervalPersistOrderTime;
	}

	public void setIntervalPersistOrderTime(long intervalPersistOrderTime) {
		this.intervalPersistOrderTime = intervalPersistOrderTime;
	}

	public int countProduct(String serverId) {
		if (serverId == null) {
			return this.productMap.size();
		} else {
			Iterator<MqttClient> serverIter = this.productMap.values().iterator();
			int count = 0;
			while (serverIter.hasNext()) {
				MqttClient server = serverIter.next();
				if (server.getClientId().indexOf(serverId) > -1) {
					count++;
				}
			}
			return count;
		}
	}

	public boolean isConnected(String productSn) {
		return this.productMap.keySet().contains(productSn);
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

	public void addProducts(List<Product> products) {
		String serverNo = PushManager.getInstance().getSettingMap("serverNo");
		if (serverNo == null) {
			Setting setting = SettingUtils.get();
			serverNo = setting.getServerNo();
			PushManager.getInstance().putSettingMap("serverNo", serverNo);
		}
		String serverCount = PushManager.getInstance().getSettingMap("serverCount");
		if (serverCount == null) {
			Setting setting = SettingUtils.get();
			serverCount = setting.getServerCount();
			PushManager.getInstance().putSettingMap("serverCount", serverCount);
		}
		for (Product product : products) {
			String productSn = product.getSn();
			int hashCode = Math.abs(productSn.hashCode());
			if (Integer.parseInt(serverNo) == hashCode % Integer.parseInt(serverCount)) {
				Product tempProduct = new Product();
				tempProduct.setSn(product.getSn());
				tempProduct.setUsername(product.getUsername());
				tempProduct.setPassword(product.getPassword());

				this.productInfoMap.put(productSn, tempProduct);
			}
		}
	}

	public synchronized void addOrder(Order order) {
		String orderSns = null;
		synchronized (this.orderSnMap) {
			orderSns = this.orderSnMap.get(order.getProduct().getSn());
			if (orderSns == null) {
				orderSns = "";
			}
			this.orderSnMap.put(order.getProduct().getSn(), orderSns.concat(order.getSn()));
		}
		this.orderInfoMap.put(order.getSn(), order);
	}

	public String persistOrder(String productSn, Order order) {
		String orderSns = null;
		synchronized (this.orderSnMap) {
			orderSns = this.orderSnMap.get(productSn);
			if (orderSns != null) {
				orderSns = orderSns.replaceFirst(order.getSn(), "");
				this.orderSnMap.put(productSn, orderSns);
			}
		}
		this.orderInfoMap.remove(order.getSn());
		this.orderPersistMap.put(order.getSn(), order);
		return orderSns;
	}

	public void removeOrder(String productSn, Order order) {
		String orderSns = null;
		synchronized (this.orderSnMap) {
			orderSns = this.orderSnMap.get(productSn);
			if (orderSns != null) {
				orderSns = orderSns.replaceFirst(order.getSn(), "");
				this.orderSnMap.put(productSn, orderSns);
			}
		}
		this.orderInfoMap.remove(order.getSn());
	}
}