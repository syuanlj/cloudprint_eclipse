package eleme.openapi.sdk.oauth.parser;

import eleme.openapi.sdk.oauth.response.ErrorResponse;

public interface Converter {
    /**
     * 把字符串转换为响应对象。
     *
     * @param <T>   领域泛型
     * @param rsp   响应字符串
     * @param clazz 领域类型
     * @return 响应对象
     */
    public <T extends ErrorResponse> T toResponse(String rsp, Class<T> clazz);

}
