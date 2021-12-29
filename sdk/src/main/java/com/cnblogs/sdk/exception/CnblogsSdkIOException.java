package com.cnblogs.sdk.exception;

import java.io.IOException;
import java.util.Locale;

/**
 * 博客园接口请求IO异常
 * @author RAE
 * @date 2021/02/20
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class CnblogsSdkIOException extends IOException {

    /**
     * 无数据
     */
    public static int ERROR_NONE_DATA = 10;

    private int httpCode;
    private int errorCode;

    public CnblogsSdkIOException(String message) {
        super(message);
    }

    public CnblogsSdkIOException(String message, int errorCode, int httpCode) {
        super(String.format(Locale.CHINESE, "%s，错误代码%s%s", message, httpCode, errorCode));
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
