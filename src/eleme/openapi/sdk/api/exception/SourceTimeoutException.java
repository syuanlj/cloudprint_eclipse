package eleme.openapi.sdk.api.exception;

public class SourceTimeoutException extends ServiceException {
    public SourceTimeoutException() {
        super("SOURCE_TIMEOUT", "资源连接超时");
    }

    public SourceTimeoutException(String message) {
        super("SOURCE_TIMEOUT", message);
    }
}
