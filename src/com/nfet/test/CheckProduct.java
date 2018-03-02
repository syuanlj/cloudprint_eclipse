package com.nfet.test;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class CheckProduct {
	public static void main(String[] main) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();

		StringBuffer getUrl = new StringBuffer("http://123.57.57.102:8081/cloudprint/product/checkProduct.ehtml");
		getUrl.append("?");
		getUrl.append("deviceid");
		getUrl.append("=");
		getUrl.append("VN2015002");
		getUrl.append("&");
		getUrl.append("cloudname");
		getUrl.append("=");
		getUrl.append("HD62NBTN");
		getUrl.append("&");
		getUrl.append("cloudpwd");
		getUrl.append("=");
		getUrl.append("444N4R64");
		HttpGet httpGet = new HttpGet(getUrl.toString());

		try {
			HttpResponse getResult = httpClient.execute(httpGet);
			System.out.println(EntityUtils.toString(getResult.getEntity(), "utf-8"));
			System.out.println("STATUS_CODE:" + getResult.getStatusLine().getStatusCode());
			httpGet.releaseConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
