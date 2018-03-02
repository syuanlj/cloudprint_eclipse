package eleme.openapi.sdk.oauth.request;

import eleme.openapi.sdk.config.Config;
import eleme.openapi.sdk.oauth.BaseOAuthRequest;
import eleme.openapi.sdk.oauth.response.Token;

import java.util.Map;

public class ServerRefreshTokenRequest extends BaseOAuthRequest<Token> {
    private Config context;
    public ServerRefreshTokenRequest(Config context) {
        this.context = context;
    }
    private String refreshToken;

    public Class<Token> getResponseClass() {
        return Token.class;
    }

    public Map<String, String> getHeaderMap()  {
        setAuthorization(context.getApp_key(), context.getApp_secret());
        return super.headerMap;
    }

    public Map<String, String> getBodyMap()  {
        putBodyParam("grant_type", "refresh_token");
        putBodyParam("refresh_token", this.refreshToken);
        return super.bodyMap;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
