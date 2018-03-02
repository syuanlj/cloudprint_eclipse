package eleme.openapi.sdk.oauth.impl;


import eleme.openapi.sdk.config.Constants;
import eleme.openapi.sdk.config.Config;
import eleme.openapi.sdk.oauth.IOAuthClient;
import eleme.openapi.sdk.oauth.OAuthRequest;
import eleme.openapi.sdk.oauth.parser.OAuthParser;
import eleme.openapi.sdk.oauth.parser.ObjectJsonParser;
import eleme.openapi.sdk.oauth.response.ErrorResponse;
import eleme.openapi.sdk.utils.WebUtils;

import java.io.IOException;

/**
 * 客户端模式获取Token
 */
public class DefaultIOAuthClient implements IOAuthClient {
    private int connectTimeout = 15000; // 默认连接超时时间为15秒
    private int readTimeout = 30000; // 默认响应超时时间为30秒
    private boolean useSimplifyJson = false; // 是否采用精简化的JSON返回
    private Config context;

    public DefaultIOAuthClient(Config context) {
        this.context = context;
    }

    public <T extends ErrorResponse> T execute(OAuthRequest<T> request) {
        try {
            String respJson = WebUtils.doPost(context, context.getOauthTokenUrl(),
                    request.getBodyMap(),
                    Constants.CHARSET_UTF8,
                    WebUtils.generateUUID(),
                    connectTimeout,
                    readTimeout,
                    request.getHeaderMap()
            );
            // 构建响应解释器
            OAuthParser<T> parser = new ObjectJsonParser<T>(request.getResponseClass(), this.useSimplifyJson);
            return parser.parse(respJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
