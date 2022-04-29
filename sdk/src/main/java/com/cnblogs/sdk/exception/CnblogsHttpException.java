package com.cnblogs.sdk.exception;

import androidx.annotation.Nullable;

import com.cnblogs.sdk.internal.utils.CnblogsUtils;

/**
 * 博客园HTTP类型异常
 * @author RAE
 * @date 2021/12/29
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@SuppressWarnings("unused")
public class CnblogsHttpException extends CnblogsIOException {

    /**
     * Http状态码
     */
    private final int mStatusCode;
    @Nullable
    private final String mContent;

    public CnblogsHttpException(int code, String message) {
        this(code, message, null);
    }

    public CnblogsHttpException(int code, String message, @Nullable String content) {
        super(CnblogsUtils.emptyOrDefault(message, "网络请求发生" + code + "错误"));
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
}
