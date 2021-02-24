package com.cnblogs.sdk.oauth;

import com.cnblogs.sdk.exception.CnblogsSdkException;
import com.cnblogs.sdk.model.UserInfo;

/**
 * 登录授权回调
 * @author RAE
 * @date 2021/02/24
 */
public interface AuthWebViewCallback {

    /**
     * 登录成功
     * @param userInfo 登录成功
     */
    void onLoginSuccess(UserInfo userInfo);

    /**
     * 登录失败
     * @param exception 错误信息
     */
    void onLoginFailed(CnblogsSdkException exception);
}
