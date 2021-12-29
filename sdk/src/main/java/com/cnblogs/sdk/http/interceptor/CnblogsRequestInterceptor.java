package com.cnblogs.sdk.http.interceptor;

import com.cnblogs.sdk.internal.ApiConstant;
import com.cnblogs.sdk.provider.ICnblogsSdkConfig;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求拦截器
 * @author RAE
 * @date 2021/02/15
 */
public class CnblogsRequestInterceptor implements Interceptor {

    private static final String URL_OPEN_API = "api.cnblogs.com";
    private final ICnblogsSdkConfig mConfig;

    public static Interceptor create(ICnblogsSdkConfig config) {
        return new CnblogsRequestInterceptor(config);
    }

    private CnblogsRequestInterceptor(ICnblogsSdkConfig config) {
        mConfig = config;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.removeHeader("user-agent").addHeader("user-agent", ApiConstant.USER_AGENT);

        // 请求的是官方接口要带上授权信息
        if (request.url().host().contains(URL_OPEN_API)) {
            String token = mConfig.getApiToken();
            builder.addHeader("Authorization", "Bearer " + token);
        }
        return chain.proceed(builder.build());
    }
}
