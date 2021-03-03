package com.cnblogs.sdk.exception;

import java.io.IOException;

/**
 * 博客园接口请求IO异常
 * @author RAE
 * @date 2021/02/20
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class CnblogsSdkIOException extends IOException {


    private int httpCode;
    private int errorCode;

    public CnblogsSdkIOException() {
    }

    public CnblogsSdkIOException(String message) {
        super(message);
    }

    public CnblogsSdkIOException(String message, int errorCode, int httpCode) {
        super(message);
        this.errorCode = errorCode;
        this.httpCode = httpCode;
    }

    public CnblogsSdkIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public CnblogsSdkIOException(Throwable cause) {
        super(cause);
    }

    public int getHttpCode() {
        return httpCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
