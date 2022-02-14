package com.cnblogs.sdk.provider;

import com.cnblogs.sdk.api.open.ITokenApi;
import com.cnblogs.sdk.internal.Constant;
import com.cnblogs.sdk.internal.http.CnblogsConverterFactory;
import com.cnblogs.sdk.internal.http.CnblogsCookieInterceptor;
import com.cnblogs.sdk.internal.http.CnblogsLoggingInterceptor;
import com.cnblogs.sdk.internal.http.CnblogsRequestInterceptor;
import com.cnblogs.sdk.internal.http.CnblogsResponseInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

/**
 * 官网开放接口（api.cnblogs.com）
 * <p>调用该提供程序的接口需要申请ApiKey授权，相关文档请查看官网https://api.cnblogs.com</p>
 * @author RAE
 * @date 2021/12/29
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class OpenApiProvider {

    private final Retrofit mRetrofit;

    public OpenApiProvider() {
        mRetrofit = new Retrofit.Builder()
                .client(newHttpClient())
                .baseUrl(Constant.OPEN_API_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(CnblogsConverterFactory.create())
                .build();
    }

    /**
     * 初始化HTTP Client
     * @return
     */
    private OkHttpClient newHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(CnblogsCookieInterceptor.create())
                .addInterceptor(CnblogsRequestInterceptor.create())
                .addInterceptor(CnblogsResponseInterceptor.create())
                .addInterceptor(CnblogsLoggingInterceptor.create())
                .build();
    }

    /**
     * 获取Token授权接口
     * @return ITokenApi
     */
    public ITokenApi getTokenApi() {
        return mRetrofit.create(ITokenApi.class);
    }

}
