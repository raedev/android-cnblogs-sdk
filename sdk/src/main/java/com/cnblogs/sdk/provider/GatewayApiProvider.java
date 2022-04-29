package com.cnblogs.sdk.provider;

import android.content.Context;

import com.cnblogs.sdk.api.gateway.IAppApi;
import com.cnblogs.sdk.api.gateway.IFeedApi;
import com.cnblogs.sdk.internal.http.converter.JsonConverterFactory;
import com.cnblogs.sdk.internal.http.interceptor.CnblogsLoggingInterceptor;
import com.cnblogs.sdk.internal.http.interceptor.CnblogsRequestInterceptor;
import com.cnblogs.sdk.internal.http.interceptor.CnblogsResponseInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * 网关接口提供者 <a href="https://github.com/raedev/cnblogs-gateway-api">开源地址</a>
 * @author RAE
 * @date 2022/02/14
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class GatewayApiProvider extends BaseApiProvider {

    public GatewayApiProvider(Context context) {
        super(context);
    }

    @Override
    protected Retrofit.Builder newRetrofitBuilder(Context context) {
        return super
                .newRetrofitBuilder(context)
                .baseUrl("http://cnblogs.dev")
                // JSON 解析器
                .addConverterFactory(JsonConverterFactory.create(getClassLoader()));
    }

    @Override
    protected OkHttpClient makeHttpClient(Context context) {
        return new OkHttpClient.Builder()
                .connectTimeout(mConnectTimeout, TimeUnit.SECONDS)
                .readTimeout(mReadTimeout, TimeUnit.SECONDS)
                .writeTimeout(mWriteTimeout, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                // 请求、响应拦截
                .addInterceptor(CnblogsRequestInterceptor.create())
                .addInterceptor(CnblogsResponseInterceptor.create())
                // 网络层日志输出
                .addNetworkInterceptor(CnblogsLoggingInterceptor.create())
                .build();
    }

    /**
     * 获取App接口
     */
    public final IAppApi getAppApi() {
        return create(IAppApi.class);
    }

    /**
     * 获取信息流接口
     */
    public final IFeedApi getFeedApi() {
        return create(IFeedApi.class);
    }

}
