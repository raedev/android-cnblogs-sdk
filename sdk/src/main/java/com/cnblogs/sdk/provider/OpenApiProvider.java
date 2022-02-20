package com.cnblogs.sdk.provider;

import android.content.Context;

import com.cnblogs.sdk.api.open.ITokenApi;
import com.cnblogs.sdk.internal.http.converter.JsonConverterFactory;

import retrofit2.Retrofit;

/**
 * 官网开放接口 For https://api.cnblogs.com
 * <p>调用该提供程序的接口需要申请ApiKey授权，<a href="https://api.cnblogs.com">相关文档请查看官网</a></p>
 * @author RAE
 * @date 2021/12/29
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class OpenApiProvider extends BaseApiProvider {

    public OpenApiProvider(Context context) {
        super(context);
    }

    @Override
    protected Retrofit.Builder newRetrofitBuilder(Context context) {
        return super
                .newRetrofitBuilder(context)
                // JSON 解析器
                .addConverterFactory(JsonConverterFactory.create(getClassLoader()));
    }

    /**
     * 获取Token授权接口
     * @return ITokenApi
     */
    public ITokenApi getTokenApi() {
        return super.create(ITokenApi.class);
    }

}
