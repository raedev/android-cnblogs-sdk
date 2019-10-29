package com.cnblogs.api.http;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rae on 2019-10-22.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class CnblogsRequestInterceptor implements Interceptor {


    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();

        // 组合标准的请求头
        builder.header("user-agent", CHeaders.UA);
        builder.header("accept-language", CHeaders.LANG);
        builder.header("cache-control", CHeaders.CACHE_CONTROL);
        builder.header("dnt", "1");

        return chain.proceed(builder.build());
    }
}
