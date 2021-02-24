package com.cnblogs.sdk.provider;

import android.util.LruCache;

import androidx.annotation.NonNull;

import com.cnblogs.sdk.CnblogsSdk;
import com.cnblogs.sdk.api.ApiConstant;
import com.cnblogs.sdk.http.CnblogsCookieInterceptor;
import com.cnblogs.sdk.http.CnblogsRequestInterceptor;
import com.cnblogs.sdk.http.CnblogsResponseInterceptor;
import com.cnblogs.sdk.internal.CnblogsConverterFactory;
import com.cnblogs.sdk.internal.CnblogsLogger;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

/**
 * 博客园接口提供者
 * @author RAE
 * @date 2021/02/10
 */
public abstract class BaseApiProvider {

    protected final LruCache<String, Object> mLruCache = new LruCache<>(8);
    protected Retrofit mRetrofit;
    protected OkHttpClient mHttpClient;

    public BaseApiProvider() {
        mHttpClient = makeHttpClient();
        mRetrofit = makeRetrofitBuilder(mHttpClient).build();
    }

    private OkHttpClient makeHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(makeLogger());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .cookieJar(CnblogsSdk.getSessionManager().getCookieJar())
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new CnblogsCookieInterceptor())
                .addInterceptor(new CnblogsRequestInterceptor())
                .addInterceptor(new CnblogsResponseInterceptor())
                .addInterceptor(loggingInterceptor)
                .build();
    }

    private HttpLoggingInterceptor.Logger makeLogger() {
        return CnblogsLogger::d;
    }

    protected Retrofit.Builder makeRetrofitBuilder(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(ApiConstant.BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(CnblogsConverterFactory.create());
    }

    protected String getName() {
        return "CnblogsApiProvider";
    }

    protected int getVersion() {
        return 1;
    }


    @NonNull
    @Override
    public String toString() {
        return getName() + "-v" + getVersion();
    }
}