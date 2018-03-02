package com.nfet.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class AddOrder {
    public static void main(String[] main) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();

        String postUrl = "http://139.129.27.116:8080/cloudprint/order/receive.ehtml";
        HttpPost httpPost = new HttpPost(postUrl);

        // BASE64Encoder encoder = new BASE64Encoder();
        // for (int i = 2; i <= 2; i++) {
        // for (int j = 1; j <= 10; j++) {
        // List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        // String content = "content" + i + j;
        // parameters.add(new BasicNameValuePair("content",
        // encoder.encode(content.getBytes("GB2312"))));
        // parameters.add(new BasicNameValuePair("deviceid", "printer" + i));
        // parameters.add(new BasicNameValuePair("cloudname", "admin"));
        // parameters.add(new BasicNameValuePair("cloudpwd", "password"));
        // parameters.add(new BasicNameValuePair("template", "0"));
        // parameters.add(new BasicNameValuePair("taskid", "cloudprint2000"));
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // parameters.add(new BasicNameValuePair("time", sdf.format(new
        // Date(System.currentTimeMillis()))));
        //
        // try {
        // UrlEncodedFormEntity formEntiry = new
        // UrlEncodedFormEntity(parameters, "UTF-8");
        // httpPost.setEntity(formEntiry);
        // HttpResponse postResult = httpClient.execute(httpPost);
        // System.out.println(EntityUtils.toString(postResult.getEntity(),
        // "utf-8"));
        // System.out.println("STATUS_CODE:" +
        // postResult.getStatusLine().getStatusCode());
        // httpPost.releaseConnection();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        // }

        JSONObject jsonParam = new JSONObject();
        jsonParam.put("seller_nick", "富士通");
        jsonParam.put("seller_id", "李四");
        jsonParam.put("created", "2017-01-01");
        jsonParam.put("payment", "50000");
        jsonParam.put("pay_type", "ALIPAY");
        jsonParam.put("buyer_nick", "woshizhifubao");
        jsonParam.put("shipping_type", "express");
        jsonParam.put("receiver_state", "江苏省");
        jsonParam.put("receiver_city", "南京市");
        jsonParam.put("receiver_district", "秦淮区");
        jsonParam.put("receiver_address", "中山东路1号");
        jsonParam.put("receiver_name", "张三");
        jsonParam.put("receiver_mobile", "13813888888");
        jsonParam.put("receiver_zip", "210002");

        JSONArray orders = new JSONArray();
        JSONObject order1 = new JSONObject();
        order1.put("title", "打印机");
        order1.put("price", "1000");
        order1.put("num", "10");
        order1.put("payment", "10000");
        orders.add(order1);
        JSONObject order2 = new JSONObject();
        order2.put("title", "扫描仪");
        order2.put("price", "2000");
        order2.put("num", "20");
        order2.put("payment", "40000");
        orders.add(order2);
        jsonParam.put("orders", orders);

        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("deviceid", "printer2"));
        parameters.add(new BasicNameValuePair("cloudname", "admin"));
        parameters.add(new BasicNameValuePair("cloudpwd", "password"));
        parameters.add(new BasicNameValuePair("template", "1"));
        parameters.add(new BasicNameValuePair("taskid", "cloudprint2000"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        parameters.add(new BasicNameValuePair("time", sdf.format(new Date(System.currentTimeMillis()))));
        parameters.add(new BasicNameValuePair("content", jsonParam.toString()));

        try {
            UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(parameters, "UTF-8");
            httpPost.setEntity(formEntiry);
            HttpResponse postResult = httpClient.execute(httpPost);
            System.out.println(EntityUtils.toString(postResult.getEntity(), "UTF-8"));
            System.out.println("STATUS_CODE:" + postResult.getStatusLine().getStatusCode());
            httpPost.releaseConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
