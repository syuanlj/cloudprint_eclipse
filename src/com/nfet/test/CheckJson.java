package com.nfet.test;

import java.io.IOException;

import net.sf.json.JSONObject;

public class CheckJson {
	public static void main(String[] main) throws IOException {
		String content = "[{'orders': [{'num': '2', 'oid': 'oid1', 'discount_fee': '3121.98', 'payment': '3121.98', 'title': 'DPK750', 'buyer_messages': '请及时发货', 'sku_unique_code': 'skucode12', 'sku_properties_name': '颜色:黑白', 'total_fee': '3121.98', 'outer_item_id': 'G1893848', 'type': 'FIXED', 'item_type': '0', 'price': '3123.98', 'sku_id': 'sku1'}, {'num': '2', 'oid': 'oid2', 'discount_fee': '3121.98', 'payment': '3121.98', 'title': 'DPK200', 'buyer_messages': '请及时发货', 'sku_unique_code': 'skucode34', 'sku_properties_name': '颜色:黑白', 'total_fee': '3121.98', 'outer_item_id': 'G1893872', 'type': 'FIXED', 'item_type': '0', 'price': '3123.98', 'sku_id': 'sku2'}], 'num': '2', 'pay_time': '2016-02-02 15:26:57', 'seller_nick': 'test经销商', 'discount_fee': '1234.56', 'refunded_fee': '1234.56', 'trade_memo': '这是一次成功的交易', 'update_time': '2016-02-02 15:26:57', 'total_fee': '1234.56', 'seller_flag': '2', 'fetch_detail': [{'shop_mobile': '12345678901', 'shop_district': '栖霞区8', 'shop_name': 'XX科技有限公司', 'shop_state': '江苏', 'shop_address': 'XX路33号', 'fetcher_mobile': '1865165111', 'fetch_time': '2016/8/20 12:08:00', 'shop_city': '南京', 'fetcher_name': '领取人姓名'}], 'shipping_type': 'express', 'buyer_nick': '卖家昵称', 'pay_type': 'WEIXIN', 'created': '2016-02-02 15:26:57', 'seller_id': 'testseller', 'receiver_zip': '210032', 'buyer_id': '微信粉丝', 'receiver_state': '江苏', 'buyer_type': '1', 'payment': '1234.56', 'receiver_address': '江苏省南京市栖霞区XX路33号', 'post_fee': '108.2', 'receiver_city': '南京', 'buyer_message': '请准时发货', 'receiver_district': '栖霞区', 'receiver_mobile': '18651653333', 'weixin_user_id': '微信粉丝', 'tid': 'tidvalue', 'buyer_area': '南京', 'receiver_name': '测试姓名'}]";
		JSONObject jo = JSONObject.fromObject(content);

	}
}
