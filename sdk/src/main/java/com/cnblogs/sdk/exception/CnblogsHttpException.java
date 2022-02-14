package com.cnblogs.sdk.exception;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;

/**
 * 博客园HTTP类型异常
 * @author RAE
 * @date 2021/12/29
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@SuppressWarnings("unused")
public class CnblogsHttpException extends IOException {

    /**
     * Http状态码
     */
    private final int mStatusCode;
    @Nullable
    private final String mContent;


    public CnblogsHttpException(int code, @Nullable String content) {
        super("HTTP请求错误，状态码：" + code);
        mStatusCode = code;
        mContent = content;
    }

    public CnblogsHttpException(int code, @Nullable String content, String message) {
        super(message);
        mStatusCode = code;
        mContent = content;
    }

    /**
     * HTTP状态码
     */
    public int getStatusCode() {
        return mStatusCode;
    }

    /**
     * HTTP 响应内容
     */
    @Nullable
    public String getContent() {
        return mContent;
    }

    @NonNull
    @Override
    public String toString() {
        return "HTTP返回内容：\r\n" + mContent + "\r\n" + super.toString();
    }
}
