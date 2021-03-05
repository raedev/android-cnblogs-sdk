package com.cnblogs.sdk.provider;

import com.cnblogs.sdk.CnblogsSdk;
import com.cnblogs.sdk.api.ApiConstant;
import com.cnblogs.sdk.api.IAccountApi;
import com.cnblogs.sdk.api.IBlogApi;
import com.cnblogs.sdk.api.ICategoryApi;
import com.cnblogs.sdk.api.IUserApi;
import com.cnblogs.sdk.exception.ApiCreator;
import com.cnblogs.sdk.http.converter.CnblogsConverterFactory;
import com.cnblogs.sdk.http.interceptor.CnblogsCookieInterceptor;
import com.cnblogs.sdk.http.interceptor.CnblogsRequestInterceptor;
import com.cnblogs.sdk.http.interceptor.CnblogsResponseInterceptor;
import com.cnblogs.sdk.internal.CnblogsLogger;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

/**
 * 博客园Web网页接口提供者
 *
 * @author RAE
 * @date 2021/02/10
 */
public final class CnblogsWebApiProvider {
    final Retrofit mRetrofit;
    final OkHttpClient mHttpClient;

    public CnblogsWebApiProvider() {
        mHttpClient = makeHttpClient();
        mRetrofit = makeRetrofitBuilder(mHttpClient).build();
    }

    private OkHttpClient makeHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(makeLogger());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .cookieJar(CnblogsSdk.getSessionManager().getCookieJar())
                .connectTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new CnblogsCookieInterceptor())
                .addInterceptor(new CnblogsRequestInterceptor())
                .addInterceptor(new CnblogsResponseInterceptor())
                .addInterceptor(loggingInterceptor)
                .build();
    }

    private HttpLoggingInterceptor.Logger makeLogger() {
        return CnblogsLogger::d;
    }

    private Retrofit.Builder makeRetrofitBuilder(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(ApiConstant.BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(CnblogsConverterFactory.create());
    }

    /**
     * 获取账号接口
     */
    public IAccountApi getAccountApi() {
        return ApiCreator.create(mRetrofit, IAccountApi.class);
    }

    /**
     * 获取用户接口
     */
    public IUserApi getUserApi() {
        return ApiCreator.create(mRetrofit, IUserApi.class);
    }

    /**
     * 分类接口
     */
    public ICategoryApi getCategoryApi() {
        return ApiCreator.create(mRetrofit, ICategoryApi.class);
    }

    /**
     * 博客接口
     */
    public IBlogApi getBlogApi() {
        return ApiCreator.create(mRetrofit, IBlogApi.class);
    }
}
