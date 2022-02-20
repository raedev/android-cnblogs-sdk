package com.cnblogs.sdk.provider;

import android.content.Context;

import com.cnblogs.sdk.internal.http.converter.JsonConverterFactory;

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
                // JSON 解析器
                .addConverterFactory(JsonConverterFactory.create(getClassLoader()));
    }
}
