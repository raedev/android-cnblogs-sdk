package com.cnblogs.sdk.oauth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cnblogs.sdk.R;
import com.cnblogs.sdk.exception.CnblogsRuntimeException;
import com.cnblogs.sdk.model.UserInfo;

/**
 * 网页授权登录WebView，
 * 获取登录状态方式
 * 1、继承该Fragment自定义回调{@link #onLoginSuccess(UserInfo)}方法
 * 2、该Fragment的Activity实现{@link AuthWebViewCallback} 即可
 * @author RAE
 * @date 2021/02/23
 */
public class WebAuthFragment extends Fragment implements AuthWebViewCallback {

    protected WebView mWebView;
    protected AuthWebViewHandler mWebViewHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mWebView = new WebView(requireContext().getApplicationContext());
        mWebViewHandler = new AuthWebViewHandler(mWebView, this);
        mWebViewHandler.setOnAuthWebViewCallback(this);
        mWebView.setId(R.id.webView);
        mWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return mWebView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mWebViewHandler.authorize();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebViewHandler.destroy();
    }

    @Override
    public void onLoginSuccess(UserInfo userInfo) {
        if (getActivity() instanceof AuthWebViewCallback) {
            ((AuthWebViewCallback) getActivity()).onLoginSuccess(userInfo);
        } else {
            requireActivity().finish();
        }
    }

    @Override
    public void onLoginFailed(CnblogsRuntimeException exception) {
        if (getActivity() instanceof AuthWebViewCallback) {
            ((AuthWebViewCallback) getActivity()).onLoginFailed(exception);
        }
    }
}
