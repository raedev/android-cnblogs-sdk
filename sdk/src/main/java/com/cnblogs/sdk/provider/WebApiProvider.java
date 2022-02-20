package com.cnblogs.sdk.provider;

import android.content.Context;

import com.cnblogs.sdk.api.web.IBlogApi;
import com.cnblogs.sdk.api.web.ICommonApi;
import com.cnblogs.sdk.api.web.IRankingApi;
import com.cnblogs.sdk.api.web.ISearchApi;
import com.cnblogs.sdk.api.web.IUserApi;
import com.cnblogs.sdk.internal.http.converter.HtmlConverterFactory;
import com.cnblogs.sdk.internal.http.converter.JsonConverterFactory;

import retrofit2.Retrofit;

/**
 * 网页接口提供者，基于HTML解析获取数据，无需官方授权，完全模拟网页请求实现。
 * @author RAE
 * @date 2021/12/29
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class WebApiProvider extends BaseApiProvider {

    public WebApiProvider(Context context) {
        super(context);
    }

    @Override
    protected Retrofit.Builder newRetrofitBuilder(Context context) {
        return super
                .newRetrofitBuilder(context)
                // Html 解析器
                .addConverterFactory(HtmlConverterFactory.create(getClassLoader()))
                // JSON 解析器
                .addConverterFactory(JsonConverterFactory.create(getClassLoader()));
    }

    /**
     * 公共接口
     */
    public ICommonApi getCommonApi() {
        return super.create(ICommonApi.class);
    }

    /**
     * 用户接口
     */
    public IUserApi getUserApi() {
        return super.create(IUserApi.class);
    }

    /**
     * 博客接口
     */
    public IBlogApi getBlogApi() {
        return super.create(IBlogApi.class);
    }

    /**
     * 排行榜接口
     */
    public IRankingApi getRankingApi() {
        return super.create(IRankingApi.class);
    }

    /**
     * 搜索接口
     */
    public ISearchApi getSearchApi() {
        return super.create(ISearchApi.class);
    }
}
