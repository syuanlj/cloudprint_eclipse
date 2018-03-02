package eleme.openapi.sdk.api.exception;


public class InvalidSignatureException extends ServiceException {
    public InvalidSignatureException() {
        super("INVALID_SIGNATURE", "无效的签名");
    }

    public InvalidSignatureException(String message) {
        super("INVALID_SIGNATURE", message);
    }
}
