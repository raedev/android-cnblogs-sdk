package com.cnblogs.sdk.internal.http;

import androidx.annotation.NonNull;

import com.cnblogs.sdk.internal.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 日志拦截器
 * @author RAE
 * @date 2022/02/09
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class CnblogsLoggingInterceptor implements Interceptor {

    final HttpLoggingInterceptor mHttpLoggingInterceptor;

    private CnblogsLoggingInterceptor() {
        HttpLoggingInterceptor.Logger logger = msg -> Logger.d("Cnblogs.API", msg);
        mHttpLoggingInterceptor = new HttpLoggingInterceptor(logger);
        mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    public static CnblogsLoggingInterceptor create() {
        return new CnblogsLoggingInterceptor();
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        return mHttpLoggingInterceptor.intercept(chain);
    }
}
