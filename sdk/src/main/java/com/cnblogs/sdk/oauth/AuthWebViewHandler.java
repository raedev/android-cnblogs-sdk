package com.cnblogs.sdk.oauth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.cnblogs.sdk.CnblogsSdk;
import com.cnblogs.sdk.api.ApiConstant;
import com.cnblogs.sdk.data.api.IUserDataApi;
import com.cnblogs.sdk.exception.CnblogsSdkException;
import com.cnblogs.sdk.http.CnblogsObserver;
import com.cnblogs.sdk.http.Composer;
import com.cnblogs.sdk.internal.CnblogsLogger;
import com.cnblogs.sdk.model.UserInfo;
import com.github.raedev.swift.utils.JsonUtils;

import java.lang.ref.WeakReference;
import java.net.URLEncoder;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

/**
 * 官网授权登录处理
 * @author RAE
 * @date 2021/02/22
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class AuthWebViewHandler {

    private final WeakReference<WebView> mWeakReference;
    private final LifecycleOwner mOwner;
    private final IUserDataApi mUserApi;
    AuthWebViewClient mWebViewClient;
    private AuthWebViewCallback mOnAuthWebViewCallback;


    @SuppressLint("SetJavaScriptEnabled")
    public AuthWebViewHandler(@NonNull WebView webView, LifecycleOwner owner) {
        mWeakReference = new WeakReference<>(webView);
        mOwner = owner;
        mUserApi = CnblogsSdk.getInstance().getDataProvider().getUserDataApi();
        WebSettings settings = webView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        webView.addJavascriptInterface(this, "cnblogs");
        webView.setWebChromeClient(new WebChromeClient());
        mWebViewClient = new AuthWebViewClient();
        mWebViewClient.setOnAuthSuccessListener(this::onAuthSuccess);
        webView.setWebViewClient(mWebViewClient);
    }


    @JavascriptInterface
    public void back() {
        if (mOwner instanceof Fragment) {
            ((Fragment) mOwner).getActivity().finish();
        } else if (mOwner instanceof Activity) {
            ((Activity) mOwner).finish();
        }
    }

    @JavascriptInterface
    public void reload() {
        // 返回主线程
        Observable.create(emitter -> {
            WebView webView = mWeakReference.get();
            if (webView == null) {
                return;
            }
            mWebViewClient.setIsCallback(false);
            authorize();
        }).subscribeOn(AndroidSchedulers.mainThread()).subscribe();
    }

    @JavascriptInterface
    public void clearCache() {
        // 返回主线程
        Observable.create(emitter -> {
            mWebViewClient.setIsCallback(false);
            // 清除浏览器缓存
            WebView webView = mWeakReference.get();
            if (webView != null) {
                webView.clearHistory();
                webView.clearCache(true);
            }
            // 清除Cookie缓存
            CnblogsSdk.getSessionManager().forgot();
            authorize();
        }).subscribeOn(AndroidSchedulers.mainThread()).subscribe();
    }

    public void setOnAuthWebViewCallback(AuthWebViewCallback onAuthWebViewCallback) {
        mOnAuthWebViewCallback = onAuthWebViewCallback;
    }

    /**
     * 通过WebView 请求授权
     */
    public void authorize() {
        WebView webView = mWeakReference.get();
        if (webView == null) {
            return;
        }
        webView.loadUrl(ApiConstant.WEB_AUTHORIZE_URL);
    }

    /**
     * 授权成功
     */
    private void onAuthSuccess() {
        // 同步Cookie
        CnblogsLogger.d("网页登录成功，准备校验登录状态...");
        CnblogsSdk.getSessionManager().syncWebCookie();
        WebView webView = mWeakReference.get();
        if (webView != null) {
            webView.stopLoading();
        }
        loadLoadingPage("正在获取用户信息");
        // 获取用户信息
        mUserApi.queryUserInfo()
                .compose(Composer.uiThread(mOwner))
                .subscribe(new CnblogsObserver<UserInfo>() {
                    @Override
                    public void onAccept(@NonNull UserInfo userInfo) {
                        // 登录成功
                        loadSuccessPage(JsonUtils.toJson(userInfo));
                        if (mOnAuthWebViewCallback != null) {
                            mOnAuthWebViewCallback.onLoginSuccess(userInfo);
                        }
                    }

                    @Override
                    public void onError(@NonNull CnblogsSdkException exception) {
                        if (mOnAuthWebViewCallback != null) {
                            mOnAuthWebViewCallback.onLoginFailed(exception);
                        }
                        loadFailedPage(exception.getMessage());
                    }
                });
    }

    /**
     * 加载登录中转页
     */
    private void loadTransferPage(int code, String msg) {
        WebView webView = mWeakReference.get();
        if (webView != null) {
            String url = "file:///android_asset/loginTransfer.html?code=" + code + "&msg=" + URLEncoder.encode(msg);
            webView.loadUrl(url);
        }
    }

    /**
     * 加载登录中转页
     */
    private void loadLoadingPage(String msg) {
        this.loadTransferPage(0, msg);
    }

    /**
     * 加载登录中转页
     */
    private void loadSuccessPage(String msg) {
        this.loadTransferPage(200, msg);
    }

    /**
     * 加载登录中转页
     */
    private void loadFailedPage(String msg) {
        this.loadTransferPage(500, msg);
    }


    /**
     * 释放资源
     */
    public void destroy() {
        mOnAuthWebViewCallback = null;
        WebView webView = mWeakReference.get();
        if (webView != null) {
            webView.removeAllViews();
            webView.destroy();
        }
        mWeakReference.clear();
    }
}
