package com.cnblogs.sdk.http.interceptor;

import com.cnblogs.sdk.CnblogsSdk;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Cookie拦截器，处理Cookie双向同步。
 * @author RAE
 * @date 2021/02/15
 */
public class CnblogsCookieInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        // WebCookie 同步到 JavaCookie
        CnblogsSdk.getSessionManager().syncWebCookie();
        Response response = chain.proceed(chain.request());
        // JavaCookie 同步到 WebCookie
        CnblogsSdk.getSessionManager().syncJavaNetCookie();
        return response;
    }
}
