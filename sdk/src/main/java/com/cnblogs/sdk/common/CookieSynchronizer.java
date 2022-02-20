package com.cnblogs.sdk.common;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.cnblogs.sdk.internal.utils.Constant;
import com.cnblogs.sdk.internal.utils.Logger;

import java.lang.ref.WeakReference;
import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    private static WeakReference<CookieSynchronizer> sReference;
    private final JavaNetCookieJar mCookieJar;
    private final HttpUrl mCookieHttpUrl;

    private CookieSynchronizer() {
        CookieHandler cookieHandler = java.net.CookieManager.getDefault();
        if (cookieHandler == null) {
            cookieHandler = new java.net.CookieManager();
            java.net.CookieManager.setDefault(cookieHandler);
        }
        mCookieJar = new JavaNetCookieJar(cookieHandler);
        mCookieHttpUrl = HttpUrl.parse(Constant.URL);
    }

    public static CookieSynchronizer getInstance() {
        if (sReference == null || sReference.get() == null) {
            sReference = new WeakReference<>(new CookieSynchronizer());
        }
        return sReference.get();
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

    /**
     * 设置双向同步 Cookie
     * @param cookie Cookie
     */
    public void setCookie(Cookie cookie) {
        // 设置Cookie
        List<Cookie> cookies = Collections.singletonList(cookie);
        mCookieJar.saveFromResponse(mCookieHttpUrl, cookies);
        // 同步Cookie
        syncJavaNetCookie();
    }

    /**
     * 设置双向同步 Cookie
     * @param name 名称
     * @param value 值
     */
    public void setCookie(String name, String value) {
        Cookie cookie = new Cookie.Builder()
                .name(name)
                .path("/")
                .domain("cnblogs.com")
                .value(value)
                .httpOnly()
                .build();
        this.setCookie(cookie);
    }

    @Nullable
    public Cookie getCookie(String url, String name) {
        List<Cookie> cookies = getCookieJar().loadForRequest(Objects.requireNonNull(HttpUrl.parse(url)));
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.name())) {
                return cookie;
            }
        }
        return null;
    }
}
