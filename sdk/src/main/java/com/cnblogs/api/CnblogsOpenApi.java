package com.cnblogs.api;

import android.app.Application;

import com.cnblogs.api.http.CnblogsCookieInterceptor;
import com.cnblogs.api.http.CnblogsRequestInterceptor;
import com.cnblogs.api.http.converter.gson.GsonConverterFactory;
import com.cnblogs.api.http.converter.html.HtmlConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxAndroidCallAdapterFactory;

/**
 * 博客园开放接口
 * Created by rae on 2019-10-19.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@SuppressWarnings("WeakerAccess")
public final class CnblogsOpenApi {

    // 单例
    private final static CnblogsOpenApi sOpenApi = new CnblogsOpenApi();
    private final Retrofit mRetrofit;

    public static CnblogsOpenApi getInstance() {
        return sOpenApi;
    }

    public static void init(Application application) {
        CnblogsUserManager.init(application);
    }

    private CnblogsOpenApi() {

        // HTTP日志输出
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(CLog.HTTP_LOGGER);
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        // Cookie状态维持
        CnblogsCookieInterceptor cookie = new CnblogsCookieInterceptor();

        OkHttpClient client = new OkHttpClient.Builder()
                // Cookie，当HTTP请求返回Set-Cookie的时候会自动保存到本地的CookieManager中去
                .cookieJar(cookie.getCookieJar())
                //  连接超时
                .connectTimeout(30, TimeUnit.SECONDS)
                // 流读取超时
                .readTimeout(120, TimeUnit.SECONDS)
                // 流写入超时
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(new CnblogsRequestInterceptor())
                .addInterceptor(logging)
                .addInterceptor(cookie)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://www.cnblogs.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(HtmlConverterFactory.create())
                .addCallAdapterFactory(RxAndroidCallAdapterFactory.create())
                .build();
    }

    /**
     * 博客接口
     */
    public IBlogApi getBlogApi() {
        return mRetrofit.create(IBlogApi.class);
    }


    /**
     * 博客园用户接口
     */
    public IUserApi getUserApi() {
        return mRetrofit.create(IUserApi.class);
    }

}
