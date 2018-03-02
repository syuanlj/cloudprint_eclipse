package com.nfet.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Calc {

	public static void main(String[] args) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("admin", "public"));
		String postUrl = "http://139.129.27.116:18083/api/clients";
		HttpPost httpPost = new HttpPost(postUrl);
		httpPost.setHeader("Authorization", "Basic YWRtaW46cHVibGlj");
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("page_size", "10000"));
		parameters.add(new BasicNameValuePair("curr_page", "1"));
		parameters.add(new BasicNameValuePair("client_key", ""));

		try {
			UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(parameters, "UTF-8");
			httpPost.setEntity(formEntiry);
			HttpResponse postResult = httpClient.execute(httpPost);
			String result = EntityUtils.toString(postResult.getEntity());
			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpPost.releaseConnection();
		}
	}

}
