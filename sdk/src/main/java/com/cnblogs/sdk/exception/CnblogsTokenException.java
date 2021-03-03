package com.cnblogs.sdk.exception;

/**
 * 用户相关异常
 * @author RAE
 * @date 2021/02/25
 */
public class CnblogsTokenException extends CnblogsSdkIOException {

    public CnblogsTokenException() {
        super("登录过期，请重新登录");
    }

    public CnblogsTokenException(String message) {
        super(message);
    }

    public CnblogsTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public CnblogsTokenException(Throwable cause) {
        super(cause);
    }
}
