package com.nfet.thirdpart.api;

//import eleme.openapi.sdk.api.entity.product.OCategory;
import eleme.openapi.sdk.api.exception.ServiceException;
//import eleme.openapi.sdk.api.service.ProductService;
//import eleme.openapi.sdk.api.service.UserService;
import eleme.openapi.sdk.config.Config;
import eleme.openapi.sdk.oauth.OAuthClient;
import eleme.openapi.sdk.oauth.response.Token;
import eleme.openapi.sdk.utils.PropertiesUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OAuthClientDemo {

    // 设置是否沙箱环境
    private static final boolean isSandbox = true;
    // 设置APPKEY
    private static final String key = "app key";
    // 设置APPSECRET
    private static final String secret = "app secret";
    // 初始化OAuthClient
    private static OAuthClient client = null;
    private static Map<String, String> tokenMap = new HashMap<String, String>();
    private static Config config = null;

    static {
        // 初始化全局配置工具
        config = new Config(isSandbox, key, secret);
        client = new OAuthClient(config);
    }

    public static void main(String[] args) throws ServiceException {
        OAuthClientDemo demo = new OAuthClientDemo();
        //demo.clientTokenTest();
//        demo.serverOAuthCodeTest();
//        demo.serverTokenTest();
//        demo.serverRefreshTokenTest();
        demo.testService();
    }

    /**
     * 客户端(个人)获取Token
     */
    private void clientTokenTest() {
        Token token = client.getTokenInClientCredentials();
        if (token.isSuccess()) {
            setTokenInfo(token);
            System.out.println(token);
        } else {
            System.out.println("error_code: " + token.getError());
            System.out.println("error_desc: " + token.getError_description());
        }
    }

    /**
     * 服务端(企业)获取授权URL
     */
    private void serverOAuthCodeTest() {
        String redirect_uri = "https://localhost:8899";
        String scope = "all";
        String state = "xyz";
        String authUrl = client.getAuthUrl(redirect_uri, scope, state);
        System.out.println(authUrl);
    }

    /**
     * 授权码(企业)模式获取Token
     */
    private void serverTokenTest() {
        String autoCode = "XXXXXXXXXXXX";
        String redirect_uri = "https://localhost:8899/demo";
        Token token = client.getTokenByCode(autoCode, redirect_uri);
        if (token.isSuccess()) {
            setTokenInfo(token);
            System.out.println(token);
        } else {
            System.out.println("error_code: " + token.getError());
            System.out.println("error_desc: " + token.getError_description());
        }
    }

    /**
     * 授权码(企业)模式刷新Token
     */
    private void serverRefreshTokenTest() {
        String refreshTokenStr = "XXXXXXXXXXX";
        Token token = client.getTokenByRefreshToken(getToken().getRefreshToken());
        if (token.isSuccess()) {
            setTokenInfo(token);
            System.out.println(token);
        } else {
            System.out.println("error_code: " + token.getError());
            System.out.println("error_desc: " + token.getError_description());
        }
    }

    private void testService() throws ServiceException {
        //UserService userService = new UserService(config, getToken());
        //long userId = userService.getUser().getUserId();
        //System.out.println("userId: " + userId);

//        ProductService productService = new ProductService(config, getToken());
//        OCategory category = productService.createCategory(987771L, "蛋炒饭", "一道美味的炒饭");
//
//        System.out.println(category.getId());

    }

    /**
     * 已获取Token信息后使用
     *
     * @return
     */
    private static Token getToken() {
        String access_token = PropertiesUtils.getPropValueByKey("access_token");
        String token_type = PropertiesUtils.getPropValueByKey("token_type");
        String expires_in = PropertiesUtils.getPropValueByKey("expires_in");
        String refresh_token = PropertiesUtils.getPropValueByKey("refresh_token");
        if (access_token.isEmpty()) {
            System.out.println("access_token is null");
            return null;
        }
        Token token = new Token();
        token.setAccessToken(access_token);
        token.setTokenType(token_type);
        token.setExpires(Long.valueOf(expires_in));
        token.setRefreshToken(refresh_token);
        return token;
    }

    private static void setTokenInfo(Token token) {
        if (null != token && token.isSuccess()) {
            tokenMap.put("access_token", token.getAccessToken());
            tokenMap.put("token_type", token.getTokenType());
            tokenMap.put("expires_in", String.valueOf(token.getExpires()));
            tokenMap.put("refresh_token", token.getRefreshToken());
            PropertiesUtils.setProps(tokenMap);
        }
    }
}
