package com.cnblogs.sdk.exception;

/**
 * 博客园接口运行时异常
 * @author RAE
 * @date 2021/02/20
 */
public class CnblogsRuntimeException extends RuntimeException {

    public CnblogsRuntimeException() {
    }

    public CnblogsRuntimeException(String message) {
        super(message);
    }

    public CnblogsRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CnblogsRuntimeException(Throwable cause) {
        super(cause);
    }
}
