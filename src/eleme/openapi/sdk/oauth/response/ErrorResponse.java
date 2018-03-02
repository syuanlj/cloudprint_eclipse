package eleme.openapi.sdk.oauth.response;

import eleme.openapi.sdk.oauth.mapping.TokenField;
import eleme.openapi.sdk.utils.StringUtils;

import java.io.Serializable;

public class ErrorResponse implements Serializable {

    @TokenField("error")
    private String error;

    @TokenField("error_description")
    private String error_description;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public boolean isSuccess() {
        return StringUtils.isEmpty(error);
    }


}
