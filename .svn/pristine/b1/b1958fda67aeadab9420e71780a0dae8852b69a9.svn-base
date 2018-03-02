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

public class AuthorizeProduct {
	public static void main(String[] main) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();

		String postUrl = "http://123.57.57.102:8081/cloudprint/product/authorize.ehtml";
		// String postUrl =
		// "http://localhost:8080/cloudprint/product/authorize.ehtml";
		HttpPost httpPost = new HttpPost(postUrl);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("deviceid", "VN2015002"));
		parameters.add(new BasicNameValuePair("cloudname", "HD62NBTN"));
		parameters.add(new BasicNameValuePair("cloudpwd", "444N4R64"));
		parameters.add(new BasicNameValuePair("latitude", "116.41667"));
		parameters.add(new BasicNameValuePair("longitude", "39.91667"));
		parameters.add(new BasicNameValuePair("province", ""));
		parameters.add(new BasicNameValuePair("city", "北京市"));
		parameters.add(new BasicNameValuePair("district", "海淀区"));

		try {
			UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(parameters, "UTF-8");
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
