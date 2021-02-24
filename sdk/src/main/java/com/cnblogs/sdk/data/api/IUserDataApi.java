package com.cnblogs.sdk.data.api;

import com.cnblogs.sdk.model.UserInfo;

import io.reactivex.rxjava3.core.Observable;

/**
 * 用户接口
 * @author RAE
 * @date 2021/02/20
 */
public interface IUserDataApi {

    /**
     * 获取用户信息
     * @return 用户信息
     */
    Observable<UserInfo> handleUserInfo();
}
