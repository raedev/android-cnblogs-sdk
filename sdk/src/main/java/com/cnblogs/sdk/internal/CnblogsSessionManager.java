package com.cnblogs.sdk.internal;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;

import com.cnblogs.sdk.model.UserInfo;
import com.github.raedev.swift.session.SharedPreferencesSessionManager;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.JavaNetCookieJar;

/**
 * 博客园会话管理，维护登录信息、Token信息、Cookie同步。
 * @author RAE
 * @date 2021/02/20
 */
public final class CnblogsSessionManager extends SharedPreferencesSessionManager {


    private final JavaNetCookieJar mCookieJar;

    public CnblogsSessionManager(Context context) {
        super(context, "CnblogsSessionManager", UserInfo.class);
        CookieHandler cookieHandler = java.net.CookieManager.getDefault();
        if (cookieHandler == null) {
            cookieHandler = new java.net.CookieManager();
            java.net.CookieManager.setDefault(cookieHandler);
        }
        mCookieJar = new JavaNetCookieJar(cookieHandler);
    }

    public JavaNetCookieJar getCookieJar() {
        return mCookieJar;
    }


    /**
     * 将WebView的Cookie同步到JavaNetCookieJar中去
     * 【 WebCookie > JavaNetCookie 】
     */
    public void syncWebCookie() {
        final String url = "https://cnblogs.com";
        CookieManager cookieManager = android.webkit.CookieManager.getInstance();
        String webCookies = cookieManager.getCookie(url.replace("https", "http"));
        String sslCookies = cookieManager.getCookie(url);
        if (TextUtils.isEmpty(webCookies)) {
            webCookies = sslCookies;
        }
        if (TextUtils.isEmpty(webCookies)) {
            return;
        }
        // Cookie转换
        HttpUrl httpUrl = HttpUrl.parse(url);
        if (httpUrl == null) {
            return;
        }
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
            Cookie cookie = Cookie.parse(httpUrl, text);
            CnblogsLogger.d("同步WebCookie：" + text);
            cookies.add(cookie);
        }
        // 保存Cookie
        mCookieJar.saveFromResponse(httpUrl, cookies);
    }

    /**
     * 将JavaNetCookieJar同步到WebView的Cookie中去
     * 【 JavaNetCookie > WebCookie 】
     */
    public void syncJavaNetCookie() {
        String url = "https://cnblogs.com";
        HttpUrl uri = HttpUrl.parse(url);
        if (uri == null) {
            return;
        }
        List<Cookie> cookies = mCookieJar.loadForRequest(uri);
        // 同步接口的cookie达到同步web登陆
        CookieManager cookieManager = CookieManager.getInstance();
        for (Cookie cookie : cookies) {
            cookieManager.setCookie(url, cookie.toString());
        }
        cookieManager.flush();
    }

    @Override
    public void forgot() {
        super.forgot();
        // 清除Cookie
        CookieManager.getInstance().removeAllCookies(new ValueCallback<Boolean>() {
            @Override
            public void onReceiveValue(Boolean value) {
                CnblogsLogger.d("清除Cookie缓存结果：" + value);
            }
        });
    }
}
