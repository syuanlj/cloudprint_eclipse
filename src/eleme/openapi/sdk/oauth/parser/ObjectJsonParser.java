package eleme.openapi.sdk.oauth.parser;

import eleme.openapi.sdk.oauth.response.ErrorResponse;

public class ObjectJsonParser<T extends ErrorResponse> implements OAuthParser<T> {

    private Class<T> clazz;
    private boolean simplify;

    public ObjectJsonParser(Class<T> clazz) {
        this.clazz = clazz;
    }

    public ObjectJsonParser(Class<T> clazz, boolean simplify) {
        this.clazz = clazz;
        this.simplify = simplify;
    }

    public T parse(String rsp)  {
        Converter converter;
        if (this.simplify) {
            converter = new SimplifyJsonConverter();
        } else {
            converter = new JsonConverter();
        }
        return converter.toResponse(rsp, clazz);
    }

    public Class<T> getResponseClass() {
        return clazz;
    }
}
