/*
 * 
 * 
 * 
 */
package com.nfet.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nfet.controller.admin.BaseController;
import com.nfet.entity.Order;
import com.nfet.entity.Product;
import com.nfet.entity.Sn;
import com.nfet.entity.Order.OrderStatus;
import com.nfet.service.FileService;
import com.nfet.service.OrderService;
import com.nfet.service.ProductService;
import com.nfet.service.SnService;
import com.nfet.util.CurrencyUtils;
import com.nfet.util.PushManager;
import com.nfet.util.SpringUtils;

import sun.misc.BASE64Encoder;

/**
 * Controller - 订单
 * 
 * 
 * 
 */
@Controller("orderController")
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Resource(name = "fileServiceImpl")
    private FileService fileService;
    @Resource(name = "orderServiceImpl")
    private OrderService orderService;
    @Resource(name = "productServiceImpl")
    private ProductService productService;
    @Resource(name = "snServiceImpl")
    private SnService snService;

    /**
     * @throws UnsupportedEncodingException
     * 
     */
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> receive(String deviceid, String cloudname, String cloudpwd, String template, String taskid, String time, String content) throws UnsupportedEncodingException {

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("taskid", taskid);

        Product product = PushManager.getInstance().getProductInfoMap(deviceid);
        if (product == null) {
            product = productService.findBySn(deviceid);
            if (product == null) {
                result.put("resstate", "2");
                return result;
            }
            Product tempProduct = new Product();
            tempProduct.setSn(product.getSn());
            tempProduct.setUsername(product.getUsername());
            tempProduct.setPassword(product.getPassword());
            PushManager.getInstance().putProductInfoMap(deviceid, tempProduct);
            product = tempProduct;
        }

        if (cloudname == null || "".equals(cloudname) || !cloudname.equals(product.getUsername())) {
            result.put("resstate", "3");
            return result;
        }
        if (cloudpwd == null || "".equals(cloudpwd) || !cloudpwd.equals(product.getPassword())) {
            result.put("resstate", "3");
            return result;
        }

        Order order = new Order();
        if ("0".equals(template)) {
            order.setContent(content);
        } else {
            byte[] contentBytes = null;
            List<Byte> bytes = new ArrayList<Byte>();
            JSONObject jo = JSONObject.fromObject(content);
            System.out.println("----content----" + content);

            // 初始化打印机参数
            addArray(bytes, new byte[] { 0x1b, 0x40 });
            // 放大字体并居中打印
            addArray(bytes, new byte[] { 0x1d, 0x21, 0x11, 0x1b, 0x61, 0x01 });
            // 居中放置较大字体的表单名称
            addString(bytes, "交易清单" + "\r\n");
            // 后续文字恢复左对齐，正常自提
            addArray(bytes, new byte[] { 0x1d, 0x21, 0x00, 0x1b, 0x61, 0x00 });
            // 走纸4mm
            addArray(bytes, new byte[] { 0x1b, 0x4a, 0x20 });
            // 商家名称
            addString(bytes, "商家名称：" + jo.getString("seller_nick") + "\r\n");
            // 销售员
            addString(bytes, "销售人员：" + jo.getString("seller_id") + "\r\n");
            // 订单日期
            addString(bytes, "订单时间：" + jo.getString("created") + "\r\n");
            // 空行
            addString(bytes, "\r\n");
            // 表头
            addArray(bytes, new byte[] { 0x1b, 0x24, 0x00, 0x00 });
            addString(bytes, "品名");
            addString(bytes, "单价");
            addString(bytes, "数量");
            addString(bytes, "金额\r\n");
            // 为了页面美观的分隔符
            addString(bytes, "--------------------------------\r\n");

            JSONArray ja_orders = jo.getJSONArray("orders");
            for (int i = 0; i < ja_orders.size(); i++) {
                JSONObject ja_order = ja_orders.getJSONObject(i);
                // 1. title长度过长时需要截取其中部分数据打印机，多余数据舍弃
                addArray(bytes, new byte[] { 0x1b, 0x24, 0x00, 0x00 });
                addString(bytes, ja_order.getString("title"));
                addArray(bytes, new byte[] { 0x1b, 0x24, (byte) 0x80, 0x00 });
                addString(bytes, ja_order.getString("price"));
                addArray(bytes, new byte[] { 0x1b, 0x24, (byte) 0xf0, 0x00 });
                addString(bytes, ja_order.getString("num"));
                addArray(bytes, new byte[] { 0x1b, 0x24, 0x20, 0x01 });
                addString(bytes, ja_order.getString("payment") + "\r\n");
            }
            // 对于发票类的票据，为保证格式正确，此处会添加适当数量的换行，此处需要计算得到
            addString(bytes, "\r\n");
            addString(bytes, "\r\n");
            addString(bytes, "\r\n");
            addString(bytes, "\r\n");

            // 合计金额小写（注释：此处可能会对金额进行简单运算）
            addString(bytes, "合计金额：" + jo.getString("payment") + "元\r\n");
            // 合计金额大写
            addString(bytes, "大写：" + CurrencyUtils.price2rmb(new BigDecimal(jo.getString("payment"))) + "\r\n");
            // 为了页面美观的分隔符
            addString(bytes, "--------------------------------\r\n");

            // 支付方式
            if ("ALIPAY".equals(jo.getString("pay_type"))) {
                addString(bytes, "付款方式：支付宝支付\r\n");
                addString(bytes, "买家昵称：" + jo.getString("buyer_nick") + "\r\n");
            } else if ("CARD".equals(jo.getString("pay_type"))) {
                addString(bytes, "付款方式：刷卡支付\r\n");
                addString(bytes, "交易银行：" + jo.getString("card_bank") + "\r\n");
                addString(bytes, "交易卡号：" + jo.getString("card_no") + "\r\n");
                addString(bytes, "银行流水号：" + jo.getString("card_bank_sn") + "\r\n");
            } else if ("CASH".equals(jo.getString("pay_type"))) {
                addString(bytes, "付款方式：现金\r\n");
            }

            // 空行
            addString(bytes, "\r\n");

            // 提货方式
            if ("express".equals(jo.getString("shipping_type"))) {
                addString(bytes, "提货方式：快递\r\n");
                addString(bytes, "收件人地址：" + jo.getString("receiver_state") + jo.getString("receiver_city") + jo.getString("receiver_district") + jo.getString("receiver_address") + "\r\n");
                addString(bytes, "收件人姓名：" + jo.getString("receiver_name") + "\r\n");
                addString(bytes, "收件人电话：" + jo.getString("receiver_mobile") + "\r\n");
                addString(bytes, "收件人邮编：" + jo.getString("receiver_zip") + "\r\n");
            } else if ("appointment".equals(jo.getString("shipping_type"))) {
                addString(bytes, "提货方式：预约提货\r\n");
                addString(bytes, "预约时间：" + jo.getString("appoint_time") + "\r\n");
            } else if ("take".equals(jo.getString("shipping_type"))) {
                addString(bytes, "提货方式：已自提\r\n");
            }

            // 空行
            addString(bytes, "\r\n");
            // 为了页面美观的分隔符
            addString(bytes, "--------------------------------\r\n");

            // // 促销广告
            // addString(bytes,
            // "    扫描一下二维码，下载APP\r\n         可享受98折优惠\r\n\r\n");
            // // 居中打印
            // addArray(bytes, new byte[] { 0x1b, 0x61, 0x01 });
            // // 打印二维码
            // String qr = "http:\\www.fujitsu-nfcp.com/download/serier.app";
            // addString(bytes, qr);
            // addArray(bytes, new byte[] { 0x1d, 0x6c, 0x07, 0x03, (byte)
            // (qr.getBytes("GB2312").length % 256), (byte)
            // (qr.getBytes("GB2312").length / 256) });
            // addString(bytes, qr);

            // 空行
            addString(bytes, "\r\n");

            // NFCP广告
            // 居右侧打印
            addArray(bytes, new byte[] { 0x1b, 0x61, 0x02 });
            addString(bytes, "nfcp-晟拓云打印\r\n");

            // 根据需要，此处可能存在对以上总共打印的行数进行统计，对总纸长进行计算
            addArray(bytes, new byte[] { 0x1b, 0x4a, 0x67 });
            // 初始化打印机参数
            addArray(bytes, new byte[] { 0x1b, 0x40 });

            contentBytes = new byte[bytes.size()];
            for (int i = 0; i < bytes.size(); i++) {
                contentBytes[i] = bytes.get(i);
            }
            BASE64Encoder encoder = new BASE64Encoder();
            order.setContent(encoder.encode(contentBytes));
        }
        order.setTaskId(taskid);
        order.setOrderStatus(Order.OrderStatus.received);
        order.setProduct(product);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            order.setReceive(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
            result.put("resstate", "4");
            return result;
        }
        order.setPushTimes(0);
        order.setCreateDate(new Date(System.currentTimeMillis()));
        String sn = snService.generate(Sn.Type.order);
        order.setSn(sn.substring(sn.length() - 16));
        PushManager.getInstance().addOrder(order);

        result.put("resstate", "1");
        result.put("id", order.getSn());
        return result;
    }

    private void addString(List<Byte> bytes, String str) throws UnsupportedEncodingException {
        for (byte b : str.getBytes("GB2312")) {
            bytes.add(b);
        }
    }

    private void addArray(List<Byte> bytes, byte[] byteArray) {
        for (int i = 0; i < byteArray.length; i++) {
            bytes.add(byteArray[i]);
        }
    }

    /**
     * 检查
     * 
     */
    @RequestMapping(value = "/checkOrder", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> checkOrder(String deviceid, String cloudname, String cloudpwd, String id) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("id", id);

        Product product = productService.findBySn(deviceid);
        if (product == null) {
            result.put("resstate", "3");
            return result;
        }
        if (cloudname == null || "".equals(cloudname) || !cloudname.equals(product.getUsername())) {
            result.put("resstate", "4");
            return result;
        }
        if (cloudpwd == null || "".equals(cloudpwd) || !cloudpwd.equals(product.getPassword())) {
            result.put("resstate", "4");
            return result;
        }
        Order order = orderService.findBySn(id);
        if (order == null) {
            result.put("resstate", "5");
            return result;
        } else if (!product.equals(order.getProduct())) {
            result.put("resstate", "7");
            return result;
        } else {
            OrderStatus orderStatus = order.getOrderStatus();
            if (Order.OrderStatus.printed.equals(orderStatus)) {
                result.put("resstate", "1");
                return result;
            } else {
                result.put("resstate", "2");
                return result;
            }
        }
    }

    /**
     * 检查
     * 
     */
    @RequestMapping(value = "/checkOrders", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> checkOrders(String deviceid, String cloudname, String cloudpwd, String timestart, String timeend) {
        Map<String, Object> result = new HashMap<String, Object>();

        Product product = productService.findBySn(deviceid);
        if (product == null) {
            result.put("resstate", "3");
            return result;
        }
        if (cloudname == null || "".equals(cloudname) || !cloudname.equals(product.getUsername())) {
            result.put("resstate", "4");
            return result;
        }
        if (cloudpwd == null || "".equals(cloudpwd) || !cloudpwd.equals(product.getPassword())) {
            result.put("resstate", "4");
            return result;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;
        try {
            beginDate = sdf.parse(timestart);
            endDate = sdf.parse(timeend);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("resstate", "5");
            return result;
        }
        List<Order> orders = orderService.findList(product, null, beginDate, endDate, null, null);
        if (orders == null || orders.size() == 0) {
            result.put("resstate", "2");
            return result;
        }
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        for (Order order : orders) {
            Map<String, String> map = new HashMap<String, String>();
            mapList.add(map);
            String sn = order.getSn();
            map.put("id", sn);
            OrderStatus orderStatus = order.getOrderStatus();
            if (Order.OrderStatus.printed.equals(orderStatus)) {
                map.put("status", "1");
            } else {
                map.put("status", "2");
            }
        }
        result.put("resstate", "1");
        result.put("count", orders.size());
        result.put("rows", mapList);
        return result;
    }

    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public @ResponseBody
    void stop() {
        ProductService productService = SpringUtils.getBean("productServiceImpl", ProductService.class);
        List<Product> products = productService.findAll();
        for (Product product : products) {
            PushManager.getInstance().putProductStatusMap(product.getSn(), Product.WorkStatus.busy);
        }
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public @ResponseBody
    void start() {
        ProductService productService = SpringUtils.getBean("productServiceImpl", ProductService.class);
        List<Product> products = productService.findAll();
        for (Product product : products) {
            PushManager.getInstance().putProductStatusMap(product.getSn(), Product.WorkStatus.normal);
        }
    }
}