package com.cnblogs.sdk.internal.http;

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
public class CnblogsRequestInterceptor implements Interceptor {

    private CnblogsRequestInterceptor() {
    }

    public static CnblogsRequestInterceptor create() {
        return new CnblogsRequestInterceptor();
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        return chain.proceed(request);
    }
}
