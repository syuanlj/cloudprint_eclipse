package com.nfet.thirdpart.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import eleme.openapi.sdk.config.Config;
import eleme.openapi.sdk.oauth.OAuthClient;
import eleme.openapi.sdk.oauth.response.Token;
import eleme.openapi.sdk.utils.PropertiesUtils;

public class OAuthClientHelper implements IOAuthClientHelper {

    // 设置是否沙箱环境
    private static final boolean isSandbox = true;
    // 设置APPKEY
    private static final String key = "H8yF3IhFl1";
    // 设置APPSECRET
    private static final String secret = "ab71ec54f87a5f8b1533eb1d644f7e6a6f6ddf2c";
    // 初始化OAuthClient
    private static OAuthClient client = null;
    private static Map<String, String> tokenMap = new HashMap<String, String>();
    private static Config config = null;
    
    static{
    	 // 初始化全局配置工具
        config = new Config(isSandbox, key, secret);
        client = new OAuthClient(config);
    }
    
    public OAuthClientHelper(){

    }
    
    public static Config getConfig(){
    	return config;
    }
    
    public static String getSecret(){
    	return secret;
    }
    
    
	@Override
	public String getOAuthUrl(String redirectUrl) {
        String scope = "all";
        String state = "xyz";
        String authUrl = client.getAuthUrl(redirectUrl, scope, state);
        return authUrl;
	}

	@Override
	public Token getToken(String redirectUrl, String autoCode) {
        Token token = client.getTokenByCode(autoCode, redirectUrl);
        if (token.isSuccess()) {
            setTokenInfo(token);
            return token;
        } else {
            System.out.println("error_code: " + token.getError());
            System.out.println("error_desc: " + token.getError_description());
            return null;
        }
	}

	@Override
	public Token refreshToken() {
        Token token = client.getTokenByRefreshToken(getTokenFromProperties().getRefreshToken());
        if (token.isSuccess()) {
            setTokenInfo(token);
            return token;
        } 
        
        return token;
	}
	
	/**
     * 已获取Token信息后使用
     *
     * @return
     */
    @SuppressWarnings("deprecation")
	public Token getTokenFromProperties() {
        String access_token = PropertiesUtils.getPropValueByKey("access_token");
        String token_type = PropertiesUtils.getPropValueByKey("token_type");
        String expires_in = PropertiesUtils.getPropValueByKey("expires_in");
        String refresh_token = PropertiesUtils.getPropValueByKey("refresh_token");
        if (access_token.isEmpty()) {
            System.out.println("access_token is null");
            return null;
        }
        
        Long expires=Long.valueOf(expires_in);
        Token token = new Token();
        token.setAccessToken(access_token);
        token.setTokenType(token_type);
        token.setExpires(expires);
        token.setRefreshToken(refresh_token);
        
        Date date = new Date();
        Date expireDate = new Date(expires-600);
        if(date.after(expireDate)){
        	return refreshToken();
        }
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
