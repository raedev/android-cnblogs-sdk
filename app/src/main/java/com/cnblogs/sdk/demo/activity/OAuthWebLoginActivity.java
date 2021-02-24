package com.cnblogs.sdk.demo.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.cnblogs.sdk.demo.R;
import com.cnblogs.sdk.exception.CnblogsSdkException;
import com.cnblogs.sdk.internal.CnblogsLogger;
import com.cnblogs.sdk.model.UserInfo;
import com.cnblogs.sdk.oauth.AuthWebViewCallback;
import com.cnblogs.sdk.ui.WebAuthFragment;

/**
 * 官网登录接口
 * @author RAE
 * @date 2021/02/22
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class OAuthWebLoginActivity extends BaseActivity implements AuthWebViewCallback {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth_web);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.ll_content, new WebAuthFragment())
                .commitNow();
    }

    @Override
    public void onLoginSuccess(UserInfo userInfo) {
        CnblogsLogger.i("登录成功：" + userInfo.getDisplayName());
        showToast("登录成功：" + userInfo.getDisplayName());
    }

    @Override
    public void onLoginFailed(CnblogsSdkException exception) {
        showAlert("登录失败：" + exception.getMessage());
    }
}
