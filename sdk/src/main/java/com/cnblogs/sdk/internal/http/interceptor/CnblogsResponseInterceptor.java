package com.cnblogs.sdk.internal.http.interceptor;

import com.cnblogs.sdk.exception.CnblogsHttpException;
import com.cnblogs.sdk.internal.utils.CnblogsUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求拦截器
 * @author RAE
 * @date 2021/12/29
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class CnblogsResponseInterceptor implements Interceptor {

    private CnblogsResponseInterceptor() {
    }

    public static CnblogsResponseInterceptor create() {
        return new CnblogsResponseInterceptor();
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String body = CnblogsUtils.copyBufferBody(response.body());
        if (!response.isSuccessful()) {
            throw new CnblogsHttpException(response.code(), body);
        }
        return response;
    }
}
