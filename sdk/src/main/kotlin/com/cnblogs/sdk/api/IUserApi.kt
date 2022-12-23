package com.cnblogs.sdk.api

import com.cnblogs.sdk.internal.http.HttpHeader
import com.cnblogs.sdk.model.UserInfo
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * 博客园用户接口
 * @author RAE
 * @date 2022/09/18
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
internal interface IUserApi {

    /**
     * 获取用户信息
     */
    @GET("https://account.cnblogs.com/user/userinfo")
    @Headers(HttpHeader.ACCEPT_TYPE_JSON, HttpHeader.CONTENT_TYPE_JSON, HttpHeader.XHR)
    fun getUserInfo(): Observable<UserInfo>
}