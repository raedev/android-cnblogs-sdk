package com.cnblogs.sdk.exception;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cnblogs.sdk.internal.utils.Constant;

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
    private String mMessage;

    public CnblogsHttpException(int code, @Nullable String content) {
        this(code, content, null);
    }

    public CnblogsHttpException(int code, @Nullable String content, String message) {
        mMessage = "网络请求发生错误，错误代码:" + code;
        if (message != null) {
            mMessage += ": ";
            mMessage += message;
        }
        if (Constant.DEBUG) {
            mMessage += "返回数据为：";
            mMessage += content;
        }
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

    @Nullable
    @Override
    public String getMessage() {
        return mMessage;
    }

    @NonNull
    @Override
    public String toString() {
        return mMessage;
    }
}
