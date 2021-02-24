package com.cnblogs.sdk.exception;

import java.io.IOException;

/**
 * 博客园接口请求IO异常
 * @author RAE
 * @date 2021/02/20
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class CnblogsSdkIOException extends IOException {

    public CnblogsSdkIOException() {
    }

    public CnblogsSdkIOException(String message) {
        super(message);
    }

    public CnblogsSdkIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public CnblogsSdkIOException(Throwable cause) {
        super(cause);
    }
}
