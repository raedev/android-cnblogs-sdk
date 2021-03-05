package com.cnblogs.sdk.http.interceptor;

import com.cnblogs.sdk.api.ApiConstant;

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

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.removeHeader("user-agent")
                .addHeader("user-agent", ApiConstant.USER_AGENT);
        return chain.proceed(builder.build());
    }
}
