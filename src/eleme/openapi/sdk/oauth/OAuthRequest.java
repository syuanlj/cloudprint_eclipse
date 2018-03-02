package eleme.openapi.sdk.oauth;

import eleme.openapi.sdk.oauth.response.ErrorResponse;

import java.util.Map;

/**
 * 请求接口
 */
public interface OAuthRequest<T extends ErrorResponse> {

    public Class<T> getResponseClass() ;

    public Map<String, String> getHeaderMap();

    public Map<String, String> getBodyMap();

}
