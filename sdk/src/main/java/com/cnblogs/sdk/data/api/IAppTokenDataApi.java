package com.cnblogs.sdk.data.api;

import com.cnblogs.sdk.model.TokenInfo;

import io.reactivex.rxjava3.core.Observable;

/**
 * App Token 接口
 * @author RAE
 * @date 2021/07/24
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface IAppTokenDataApi {

    /**
     * 请求AppToken
     * @param userInfo 当前用户信息，可空
     * @return 观察者
     */
    Observable<TokenInfo> requestAppToken();
}
