package com.nfet.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class AddProduct {
	public static void main(String[] main) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();

		for (int i = 1001; i <= 2000; i++) {
			String postUrl = "http://139.129.27.116:8080/cloudprint/product/save.ehtml";
//			String postUrl = "http://192.168.1.21:8080/cloudprint/product/save.ehtml";
			HttpPost httpPost = new HttpPost(postUrl);
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("deviceid", "printer" + i));
			parameters.add(new BasicNameValuePair("devicetype", "CPrin 1"));
			parameters.add(new BasicNameValuePair("mqttname", "admin"));
			parameters.add(new BasicNameValuePair("mqttpwd", "password"));
			parameters.add(new BasicNameValuePair("cloudname", "admin"));
			parameters.add(new BasicNameValuePair("cloudpwd", "password"));
			parameters.add(new BasicNameValuePair("manutime", "2016-01-01 00:00:00"));

			try {
				UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(parameters);
				httpPost.setEntity(formEntiry);
				HttpResponse postResult = httpClient.execute(httpPost);
				System.out.println("STATUS_CODE:" + postResult.getStatusLine().getStatusCode());
				System.out.println(EntityUtils.toString(postResult.getEntity(), "utf-8"));
				httpPost.releaseConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
