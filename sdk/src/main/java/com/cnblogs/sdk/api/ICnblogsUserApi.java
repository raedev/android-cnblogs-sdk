package com.cnblogs.sdk.api;

import com.cnblogs.sdk.model.UserInfo;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * 博客园用户接口
 * @author RAE
 * @date 2021/02/10
 */
public interface ICnblogsUserApi {

    /**
     * 获取登录用户信息
     * @return 用户信息
     */
    @GET("https://account.cnblogs.com/user/userinfo")
    @Headers("accept: application/json, text/javascript, */*; q=0.01")
    Observable<UserInfo> getUserInfo();
}
