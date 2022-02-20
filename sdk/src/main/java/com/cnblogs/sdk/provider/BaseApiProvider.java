package com.cnblogs.sdk.provider;

import android.content.Context;

import com.cnblogs.sdk.common.CookieSynchronizer;
import com.cnblogs.sdk.internal.http.interceptor.CnblogsCookieInterceptor;
import com.cnblogs.sdk.internal.http.interceptor.CnblogsLoggingInterceptor;
import com.cnblogs.sdk.internal.http.interceptor.CnblogsRequestInterceptor;
import com.cnblogs.sdk.internal.http.interceptor.CnblogsResponseInterceptor;
import com.cnblogs.sdk.internal.loader.CnblogsClassLoader;
import com.cnblogs.sdk.internal.utils.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

/**
 * 接口提供者基类，创建OkHttp和Retrofit对象。
 * @author RAE
 * @date 2022/02/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
abstract class BaseApiProvider {

    private final Retrofit mRetrofit;
    private final Context mContext;
    private final CnblogsClassLoader mClassLoader;
    protected long mConnectTimeout = 20;
    protected long mReadTimeout = 120;
    protected long mWriteTimeout = 120;

    public BaseApiProvider(Context context) {
        mContext = context;
        mClassLoader = new CnblogsClassLoader(context);
        this.onInit();
        mRetrofit = newRetrofitBuilder(context).build();
    }

    protected Context getContext() {
        return mContext;
    }

    public CnblogsClassLoader getClassLoader() {
        return mClassLoader;
    }

    protected void onInit() {
        // 父类无实现
    }

    protected Retrofit.Builder newRetrofitBuilder(Context context) {
        return new Retrofit.Builder()
                .client(makeHttpClient(context))
                .baseUrl(Constant.OPEN_API_URL)
                // RxJava
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create());
    }

    protected OkHttpClient makeHttpClient(Context context) {
        return new OkHttpClient.Builder()
                .connectTimeout(mConnectTimeout, TimeUnit.SECONDS)
                .readTimeout(mReadTimeout, TimeUnit.SECONDS)
                .writeTimeout(mWriteTimeout, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                // 请求Cookie及同步
                .cookieJar(CookieSynchronizer.getInstance().getCookieJar())
                .addInterceptor(CnblogsCookieInterceptor.create())
                // 请求、响应拦截
                .addInterceptor(CnblogsRequestInterceptor.create())
                .addInterceptor(CnblogsResponseInterceptor.create())
                // 网络层日志输出
                .addNetworkInterceptor(CnblogsLoggingInterceptor.create())
                .build();
    }

    /**
     * 创建接口
     */
    protected <T> T create(Class<T> cls) {
        return mRetrofit.create(cls);
    }
}
