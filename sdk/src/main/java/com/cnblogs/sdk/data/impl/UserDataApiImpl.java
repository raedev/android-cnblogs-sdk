package com.cnblogs.sdk.data.impl;

import com.cnblogs.sdk.CnblogsSdk;
import com.cnblogs.sdk.api.ICnblogsUserApi;
import com.cnblogs.sdk.data.api.IUserDataApi;
import com.cnblogs.sdk.model.UserInfo;

import io.reactivex.rxjava3.core.Observable;

/**
 * 用户接口
 * @author RAE
 * @date 2021/02/20
 */
public class UserDataApiImpl extends BaseDataApi implements IUserDataApi {

    private final ICnblogsUserApi mUserApi;

    public UserDataApiImpl() {
        mUserApi = getWebApiProvider().getUserApi();
    }

    @Override
    public Observable<UserInfo> handleUserInfo() {
        return mUserApi.getUserInfo().doOnNext(userInfo -> {
            // 保存到Session
            CnblogsSdk.getSessionManager().setUserInfo(userInfo);
        });
    }
}
