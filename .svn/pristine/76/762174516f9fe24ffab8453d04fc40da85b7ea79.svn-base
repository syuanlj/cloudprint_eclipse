package eleme.openapi.sdk.oauth.parser;

import eleme.openapi.sdk.oauth.response.ErrorResponse;

public interface OAuthParser<T extends ErrorResponse> {

    /**
     * 把响应字符串解释成相应的领域对象。
     *
     * @param rsp 响应字符串
     * @return 领域对象
     */
    public T parse(String rsp) ;

    public Class<T> getResponseClass() ;

}
