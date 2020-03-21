package com.cnblogs.api.http;


import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rae on 2019-10-22.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class CnblogsRequestInterceptor implements Interceptor {


    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();

        // 组合标准的请求头
        builder.header("user-agent", HttpHeaders.UA);
        builder.header("accept-language", HttpHeaders.LANG);
        builder.header("cache-control", HttpHeaders.CACHE_CONTROL);
        builder.header("dnt", "1");

        return chain.proceed(builder.build());
    }
}
