package com.cnblogs.sdk.provider;

import com.cnblogs.sdk.CnblogsSdk;
import com.cnblogs.sdk.api.IAccountWebApi;
import com.cnblogs.sdk.api.IBlogWebApi;
import com.cnblogs.sdk.api.IUserWebApi;
import com.cnblogs.sdk.http.converter.CnblogsConverterFactory;
import com.cnblogs.sdk.http.interceptor.CnblogsCookieInterceptor;
import com.cnblogs.sdk.http.interceptor.CnblogsRequestInterceptor;
import com.cnblogs.sdk.http.interceptor.CnblogsResponseInterceptor;
import com.cnblogs.sdk.internal.ApiConstant;
import com.cnblogs.sdk.internal.ApiCreator;
import com.cnblogs.sdk.internal.CnblogsLogger;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

/**
 * 博客园Web网页接口提供者
 * @author RAE
 * @date 2021/02/10
 */
public final class CnblogsWebApiProvider {
    private final Retrofit mRetrofit;
    private final ICnblogsSdkConfig mConfig;

    public CnblogsWebApiProvider(ICnblogsSdkConfig config) {
        mConfig = config;
        OkHttpClient httpClient = makeHttpClient();
        mRetrofit = makeRetrofitBuilder(httpClient).build();
    }

    private OkHttpClient makeHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(makeLogger());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return new OkHttpClient.Builder()
                .cookieJar(CnblogsSdk.getSessionManager().getCookieJar())
                .connectTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new CnblogsCookieInterceptor())
                .addInterceptor(CnblogsRequestInterceptor.create(mConfig))
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
    public IAccountWebApi getAccountApi() {
        return ApiCreator.create(mRetrofit, IAccountWebApi.class);
    }

    /**
     * 获取用户接口
     */
    public IUserWebApi getUserApi() {
        return ApiCreator.create(mRetrofit, IUserWebApi.class);
    }

    /**
     * 博客接口
     */
    public IBlogWebApi getBlogApi() {
        return ApiCreator.create(mRetrofit, IBlogWebApi.class);
    }

}
