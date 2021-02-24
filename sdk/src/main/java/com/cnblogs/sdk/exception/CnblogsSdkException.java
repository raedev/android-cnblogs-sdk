package com.cnblogs.sdk.exception;

/**
 * 博客园接口运行时异常
 * @author RAE
 * @date 2021/02/20
 */
public class CnblogsSdkException extends RuntimeException {

    public CnblogsSdkException() {
    }

    public CnblogsSdkException(String message) {
        super(message);
    }

    public CnblogsSdkException(String message, Throwable cause) {
        super(message, cause);
    }

    public CnblogsSdkException(Throwable cause) {
        super(cause);
    }
}
