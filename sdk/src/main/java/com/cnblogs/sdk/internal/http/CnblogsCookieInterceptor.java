package com.cnblogs.sdk.internal.http;


import android.text.TextUtils;

import com.cnblogs.sdk.internal.Constant;
import com.cnblogs.sdk.manager.CookieSynchronizer;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Cookie拦截器，处理Cookie双向同步。
 * @author RAE
 * @date 2021/02/15
 */
public class CnblogsCookieInterceptor implements Interceptor {

    /**
     * Cookie同步器
     */
    private final CookieSynchronizer mCookieSynchronizer = new CookieSynchronizer();

    private CnblogsCookieInterceptor() {
    }

    public static CnblogsCookieInterceptor create() {
        return new CnblogsCookieInterceptor();
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        // 官网开放接口无需处理Cookie问题
        if (TextUtils.equals(request.url().host(), Constant.OPEN_API_HOST)) {
            return chain.proceed(request);
        }
        // 请求前： WebCookie 同步到 JavaCookie
        mCookieSynchronizer.syncWebCookie();
        Response response = chain.proceed(request);
        // 请求后：JavaCookie 同步到 WebCookie
        mCookieSynchronizer.syncJavaNetCookie();
        return response;
    }
}
