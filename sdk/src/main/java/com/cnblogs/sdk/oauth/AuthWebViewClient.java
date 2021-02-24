package com.cnblogs.sdk.oauth;

import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cnblogs.sdk.internal.CnblogsLogger;

/**
 * 网页登录状态处理客户端
 * 根据请求中是否有登录的Cookie来判断是否登录成功
 * @author RAE
 * @date 2021/02/22
 */
@SuppressWarnings("SpellCheckingInspection")
public class AuthWebViewClient extends WebViewClient {

    private OnAuthSuccessListener mOnAuthSuccessListener;
    /**
     * 回调方法只触发一次
     */
    private boolean mIsCallback;

    public void setIsCallback(boolean callback) {
        mIsCallback = callback;
    }

    public void setOnAuthSuccessListener(OnAuthSuccessListener onAuthSuccessListener) {
        mOnAuthSuccessListener = onAuthSuccessListener;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        // 判断是否有Cookie
        String cookie = CookieManager.getInstance().getCookie(url);
        String cookieKey = ".CNBlogsCookie";
        String netCookieKey = ".Cnblogs.AspNetCore.Cookies";
        if (!TextUtils.isEmpty(cookie) && cookie.contains(cookieKey) && cookie.contains(netCookieKey)) {
            // 回调通知登录成功状态
            CnblogsLogger.d("网页登录Cookie：" + cookie);
            if (mOnAuthSuccessListener != null && !mIsCallback) {
                mOnAuthSuccessListener.onAuthSuccess();
                mIsCallback = true;
            }
        }
    }

    public interface OnAuthSuccessListener {
        /**
         * 登录授权成功
         */
        void onAuthSuccess();
    }
}
