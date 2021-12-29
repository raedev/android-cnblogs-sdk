package com.cnblogs.sdk.provider;

import com.cnblogs.sdk.BuildConfig;
import com.cnblogs.sdk.CnblogsSdk;
import com.cnblogs.sdk.api.gateway.IFeedApi;
import com.cnblogs.sdk.api.gateway.ISearchApi;
import com.cnblogs.sdk.api.gateway.IUserApi;
import com.cnblogs.sdk.http.interceptor.GatewayInterceptor;
import com.cnblogs.sdk.internal.ApiCreator;
import com.cnblogs.sdk.internal.CnblogsLogger;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <p>博客园APP秉承着开源精神，故将网关接口也公开出来，让大家能知道整个APP的流程</p>
 * <p>聚合网关接口，博客园提供的接口是不够使用的，为了更好的用户体验APP需要有自己的接口。</p>
 * <p>该接口服务端部署在作者服务器，服务器没有做限制，资源有限，请勿私自频繁调用！</p>
 * @author RAE
 * @date 2021/02/10
 */
public final class CnblogsGatewayApiProvider {

    private final Retrofit mRetrofit;

    public CnblogsGatewayApiProvider(ICnblogsSdkConfig config) {
        OkHttpClient httpClient = makeHttpClient(config);
        mRetrofit = makeRetrofitBuilder(httpClient).build();
    }

    private OkHttpClient makeHttpClient(ICnblogsSdkConfig config) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(makeLogger());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .cookieJar(CnblogsSdk.getSessionManager().getCookieJar())
                .connectTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(GatewayInterceptor.create(config))
                .addInterceptor(loggingInterceptor)
                .build();
    }

    private HttpLoggingInterceptor.Logger makeLogger() {
        return msg -> CnblogsLogger.d("Gateway", msg);
    }

    private Retrofit.Builder makeRetrofitBuilder(OkHttpClient client) {
        String url = "https://cnblogs.raeblog.com/api/";
        if (BuildConfig.DEBUG) {
            url = "http://192.168.2.12:8083/api/";
//            url = "http://192.168.0.70:8083/api/";
        }
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
    }

    /**
     * 获取用户接口
     */
    public IUserApi getUserApi() {
        return ApiCreator.create(mRetrofit, IUserApi.class);
    }

    /**
     * 信息流接口
     */
    public IFeedApi getFeedApi() {
        return ApiCreator.create(mRetrofit, IFeedApi.class);
    }

    /**
     * 搜索接口
     */
    public ISearchApi getSearchApi() {
        return ApiCreator.create(mRetrofit, ISearchApi.class);
    }

}
