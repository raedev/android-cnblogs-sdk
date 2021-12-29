package com.cnblogs.sdk.api;

import com.cnblogs.sdk.internal.ApiConstant;
import com.cnblogs.sdk.model.UserInfo;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * 账号接口
 * @author RAE
 * @date 2021/02/25
 */
public interface IAccountWebApi {



    /**
     * 获取登录用户信息
     * @return 用户信息
     */
    @GET(ApiConstant.API_ACCOUNT_INFO)
    @Headers(ApiConstant.HEADER_ACCEPT_JSON)
    Observable<UserInfo> getUserInfo();

}
