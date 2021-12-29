package com.cnblogs.sdk.data.impl;

import com.cnblogs.sdk.CnblogsSdk;
import com.cnblogs.sdk.api.gateway.IUserApi;
import com.cnblogs.sdk.data.api.IAppTokenDataApi;
import com.cnblogs.sdk.internal.CnblogsSessionManager;
import com.cnblogs.sdk.model.TokenInfo;
import com.cnblogs.sdk.model.UserInfo;
import com.cnblogs.sdk.provider.CnblogsGatewayApiProvider;
import com.cnblogs.sdk.provider.ICnblogsSdkConfig;

import io.reactivex.rxjava3.core.Observable;

/**
 * Token 处理
 * @author RAE
 * @date 2021/07/24
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class AppTokenImpl implements IAppTokenDataApi {

    private final IUserApi mUserApi;
    private final ICnblogsSdkConfig mConfig;


    public AppTokenImpl() {
        CnblogsGatewayApiProvider provider = CnblogsSdk.getInstance().getGatewayApiProvider();
        mUserApi = provider.getUserApi();
        mConfig = CnblogsSdk.getConfig();
    }

    @Override
    public Observable<TokenInfo> requestAppToken() {
        return Observable.create(emitter -> {
            UserInfo userInfo = CnblogsSessionManager.getDefault().getUserInfo();
            final String guest = "guest";
            String blogApp = userInfo == null ? guest : userInfo.getBlogApp();
            String nickName = userInfo == null ? guest : userInfo.getDisplayName();
            // 请求Token
            TokenInfo tokenInfo = mUserApi.getToken(blogApp, nickName).blockingFirst();
            // 保存Token
            mConfig.setToken(tokenInfo.token);
            mConfig.setApiToken(tokenInfo.apiToken);
            emitter.onNext(tokenInfo);
            emitter.onComplete();
        });
    }
}
