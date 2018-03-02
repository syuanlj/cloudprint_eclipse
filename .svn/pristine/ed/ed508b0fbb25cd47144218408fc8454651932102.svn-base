package com.nfet.test;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class GetAccount {
	public static void main(String[] main) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();

		String getUrl = "http://139.129.27.116:8080/cloudprint/product/getAccount.ehtml?deviceid=VN5Z259468";
		HttpGet httpGet = new HttpGet(getUrl);

		try {
			HttpResponse getResult = httpClient.execute(httpGet);
			System.out.println("STATUS_CODE:" + getResult.getStatusLine().getStatusCode());
			System.out.println(EntityUtils.toString(getResult.getEntity(), "utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
