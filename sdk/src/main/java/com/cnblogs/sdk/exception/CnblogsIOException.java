package com.cnblogs.sdk.exception;

import java.io.IOException;

/**
 * IO异常
 * @author RAE
 * @date 2022/02/19
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class CnblogsIOException extends IOException {

    public CnblogsIOException() {
    }

    public CnblogsIOException(String message) {
        super(message);
    }

    public CnblogsIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public CnblogsIOException(Throwable cause) {
        super(cause);
    }
}
