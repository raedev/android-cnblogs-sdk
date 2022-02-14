package com.cnblogs.sdk.manager;

import android.text.TextUtils;

import com.cnblogs.sdk.internal.Constant;
import com.cnblogs.sdk.internal.Logger;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.JavaNetCookieJar;

/**
 * Cookie 同步器，用于同步WebCookie和JavaCookie，实现双向同步。
 * @author RAE
 * @date 2021/12/29
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class CookieSynchronizer {

    private final JavaNetCookieJar mCookieJar;
    private final HttpUrl mCookieHttpUrl;

    public CookieSynchronizer() {
        CookieHandler cookieHandler = java.net.CookieManager.getDefault();
        if (cookieHandler == null) {
            cookieHandler = new java.net.CookieManager();
            java.net.CookieManager.setDefault(cookieHandler);
        }
        mCookieJar = new JavaNetCookieJar(cookieHandler);
        mCookieHttpUrl = HttpUrl.parse(Constant.URL);
    }

    public JavaNetCookieJar getCookieJar() {
        return mCookieJar;
    }

    /**
     * 将WebView的Cookie同步到JavaNetCookieJar中去
     * 【 WebCookie > JavaNetCookie 】
     */
    public void syncWebCookie() {
        android.webkit.CookieManager cookieManager = android.webkit.CookieManager.getInstance();
        String webCookies = cookieManager.getCookie(Constant.URL.replace("https", "http"));
        String sslCookies = cookieManager.getCookie(Constant.URL);
        if (TextUtils.isEmpty(webCookies)) {
            webCookies = sslCookies;
        }
        if (TextUtils.isEmpty(webCookies)) {
            return;
        }
        // Cookie转换
        List<Cookie> cookies = new ArrayList<>();
        String[] texts = webCookies.split(";");
        // 解析字符串
        for (String text : texts) {
            if (TextUtils.isEmpty(text)) {
                continue;
            }
            // 去掉多余的空格
            text = text.trim();
            if (!text.endsWith(";")) {
                text += ";";
            }
            text += " domain=.cnblogs.com; path=/; HttpOnly";
            Cookie cookie = Cookie.parse(mCookieHttpUrl, text);
            Logger.d("同步WebCookie：" + text);
            cookies.add(cookie);
        }
        // 保存Cookie
        mCookieJar.saveFromResponse(mCookieHttpUrl, cookies);
    }

    /**
     * 将JavaNetCookieJar同步到WebView的Cookie中去
     * 【 JavaNetCookie > WebCookie 】
     */
    public void syncJavaNetCookie() {
        List<Cookie> cookies = mCookieJar.loadForRequest(mCookieHttpUrl);
        // 同步接口的cookie达到同步web登陆
        android.webkit.CookieManager cookieManager = android.webkit.CookieManager.getInstance();
        for (Cookie cookie : cookies) {
            cookieManager.setCookie(Constant.URL, cookie.toString());
        }
        cookieManager.flush();
    }

    /**
     * 清除双向Cookie
     */
    public void clear() {
        // 清除WebCookie
        android.webkit.CookieManager.getInstance().removeAllCookies(null);
        // 清除JavaNetCookie
        List<Cookie> cookies = mCookieJar.loadForRequest(mCookieHttpUrl);
        List<Cookie> emptyCookies = new ArrayList<>(cookies.size());
        for (Cookie cookie : cookies) {
            // 使Cookie过期
            emptyCookies.add(new Cookie
                    .Builder()
                    .name(cookie.name())
                    .value("")
                    .httpOnly()
                    .domain(cookie.domain())
                    .path(cookie.path())
                    .expiresAt(0)
                    .build());
        }
        this.mCookieJar.saveFromResponse(mCookieHttpUrl, emptyCookies);
    }
}
