package com.cnblogs.sdk.exception;

/**
 * 博客园SDK异常
 * @author RAE
 * @date 2021/12/29
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class CnblogsException extends RuntimeException {

    public CnblogsException() {
    }

    public CnblogsException(String message) {
        super(message);
    }

    public CnblogsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CnblogsException(Throwable cause) {
        super(cause);
    }
}
