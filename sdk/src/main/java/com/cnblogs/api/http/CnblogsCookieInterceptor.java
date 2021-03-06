package com.cnblogs.api.http;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.IOException;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Cookie 请求拦截器
 * Created by rae on 2019-10-20.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class CnblogsCookieInterceptor implements Interceptor {

    private final String url = "https://cnblogs.com";
    private final JavaNetCookieJar cookieJar;

    public CnblogsCookieInterceptor() {
        CookieManager.setDefault(new CookieManager()); // 初始化默认的CookieManager
        cookieJar = new JavaNetCookieJar(CookieManager.getDefault()); // 初始化CookieJar
    }

    public JavaNetCookieJar getCookieJar() {
        return cookieJar;
    }

    @Override
    @NonNull
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        // 请求之前同步浏览器Cookie
        this.setJavaCookie(android.webkit.CookieManager.getInstance().getCookie(this.url));
        // 自定义Cookie
        String cookie = request.header("Cookie");
        if (!TextUtils.isEmpty(cookie)) {
            this.setJavaCookie(cookie);
        }

        return chain.proceed(request);
    }

    /**
     * 同步网页的cookie到HTTP请求cookie中去
     */
    private void setJavaCookie(String webCookies) {
        if (TextUtils.isEmpty(webCookies)) return;
        List<Cookie> cookies = new ArrayList<>();
        String[] texts = webCookies.split(";");
        HttpUrl httpUrl = HttpUrl.parse(this.url);
        if (httpUrl == null) return;
        // 解析字符串
        for (String text : texts) {
            if (TextUtils.isEmpty(text)) continue;
            text = text.trim(); // 去掉多余的空格
            if (!text.endsWith(";")) {
                text += ";";
            }
            text += " domain=.cnblogs.com; path=/; HttpOnly";
            Cookie cookie = Cookie.parse(httpUrl, text);
            cookies.add(cookie);
        }

        // 保存cookie
        cookieJar.saveFromResponse(httpUrl, cookies);
    }

}
