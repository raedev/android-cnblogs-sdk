package com.cnblogs.sdk.internal.http.interceptor;

import androidx.annotation.NonNull;

import com.cnblogs.sdk.exception.CnblogsHttpException;
import com.cnblogs.sdk.internal.utils.Constant;
import com.cnblogs.sdk.internal.utils.Logger;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 网络层拦截器：日志拦截、登录拦截
 * @author RAE
 * @date 2022/02/09
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class CnblogsNetworkInterceptor implements Interceptor {

    final HttpLoggingInterceptor mHttpLoggingInterceptor;

    private CnblogsNetworkInterceptor() {
        HttpLoggingInterceptor.Logger logger = msg -> Logger.d("Cnblogs.API", msg);
        mHttpLoggingInterceptor = new HttpLoggingInterceptor(logger);
        mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    public static CnblogsNetworkInterceptor create() {
        return new CnblogsNetworkInterceptor();
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response;
        // 调试模式下日志拦截器处理请求
        if (Constant.DEBUG) {
            response = mHttpLoggingInterceptor.intercept(chain);
        } else {
            response = chain.proceed(request);
        }

        // 重定向到登录的请求视为登录失效
        if (response.code() == 302) {
            String location = response.header("location");
            HttpUrl url = location == null ? null : HttpUrl.parse(location);
            if (url != null && url.encodedPath().contains("signin")) {
                throw new CnblogsHttpException(403, "登录失效，请重新登录");
            }
        }

        return response;
    }
}
