package com.nfet.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eleme.openapi.sdk.api.entity.order.OOrder;
import eleme.openapi.sdk.api.exception.ServiceException;
import eleme.openapi.sdk.api.service.OrderService;
import eleme.openapi.sdk.api.utils.CallbackValidationUtil;
import eleme.openapi.sdk.utils.JacksonUtils;

import com.nfet.controller.admin.BaseController;
import com.nfet.entity.OMessage;
import com.nfet.thirdpart.api.OAuthClientHelper;

@Controller("tpapiController")
@RequestMapping("/api")
public class ThirdPartApi extends BaseController {
	// 设置APP SECRET
    private static final String secret = "ab71ec54f87a5f8b1533eb1d644f7e6a6f6ddf2c";
    
    @RequestMapping(value = "/receivemsg", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> receivemsg(@RequestBody OMessage pushmsg){
		Map<String, Object> result = new HashMap<String, Object>();

		if(CallbackValidationUtil.isValidMessage(pushmsg, secret))
		{
			result.put("message", "ok");
		}
		else
		{
			result.put("message", "fail");
		}
		
		OAuthClientHelper oauthHelper=new OAuthClientHelper();
        //type=10的消息，调用确认订单接口完成接单流程
        if (null != pushmsg && pushmsg.getType() == 10) {
            OrderService orderService = new OrderService(OAuthClientHelper.getConfig(), oauthHelper.getTokenFromProperties());
            OMessage.Message msg = JacksonUtils.json2pojo(pushmsg.getMessage(), OMessage.Message.class);
            try {
                OOrder oOrder = orderService.confirmOrder(msg.getOrder_id());
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
		
		return result;
	}
    
    @RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> test(){
		Map<String, Object> result = new HashMap<String, Object>();

		result.put("message", "ok");
		
		return result;
	}
}
