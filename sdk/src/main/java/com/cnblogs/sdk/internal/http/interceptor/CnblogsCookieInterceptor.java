package com.cnblogs.sdk.internal.http.interceptor;


import android.text.TextUtils;

import com.cnblogs.sdk.common.CookieSynchronizer;
import com.cnblogs.sdk.internal.utils.Constant;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Cookie;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Cookie拦截器
 * <ul>
 *     <li>处理Cookie双向同步</li>
 *     <li>处理XSRF-TOKEN同步</li>
 * </ul>
 * @author RAE
 * @date 2021/02/15
 */
public class CnblogsCookieInterceptor implements Interceptor {

    /**
     * Cookie同步器
     */
    private final CookieSynchronizer mCookieSynchronizer;

    private CnblogsCookieInterceptor() {
        mCookieSynchronizer = CookieSynchronizer.getInstance();
    }

    public static CnblogsCookieInterceptor create() {
        return new CnblogsCookieInterceptor();
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        // 官网开放接口无需处理Cookie问题
        if (TextUtils.equals(request.url().host(), Constant.OPEN_API_DOMAIN)) {
            return chain.proceed(request);
        }
        // 请求前： WebCookie 同步到 JavaCookie
        mCookieSynchronizer.syncWebCookie();
        // XSRF-TOKEN 处理
        Cookie xsrfTokenCookie = mCookieSynchronizer.getCookie(request.url().toString(), "XSRF-TOKEN");
        if (xsrfTokenCookie != null) {
            request = request.newBuilder().header("x-xsrf-token", xsrfTokenCookie.value()).build();
        }
        Response response = chain.proceed(request);
        // 请求后：JavaCookie 同步到 WebCookie
        mCookieSynchronizer.syncJavaNetCookie();
        return response;
    }
}
