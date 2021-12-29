package com.cnblogs.sdk.api.gateway;

import com.cnblogs.sdk.model.CategoryInfo;
import com.cnblogs.sdk.model.TokenInfo;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * 用户相关接口
 * @author RAE
 * @date 2021/07/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface IUserApi {

    /**
     * 获取接口访问Token
     * <p>这里调用服务器返回，不开放个人的申请的AppKey信息，如需要请自行前往: https://api.cnblogs.com申请</p>
     * <p>用户信息隐私提示：网关需要根据唯一用户Id来提供后续的服务，如：用户分类配置、搜索等</p>
     * @param blogApp 用户博客App
     * @param nickname 昵称
     * @return 观察者
     */
    @POST("token")
    @FormUrlEncoded
    Observable<TokenInfo> getToken(@Field("blogApp") String blogApp, @Field("nickname") String nickname);

    /**
     * 获取用户首页技术分类
     * @return 观察者
     */
    @GET("user/category")
    Observable<List<CategoryInfo>> getUserCategory();

    /**
     * 更新用户首页技术分类，当编辑分类时候触发更新
     * @param json {@link CategoryInfo}[] 数组Json
     * @return 观察者
     */
    @PUT("user/category")
    @FormUrlEncoded
    Observable<List<CategoryInfo>> updateUserCategory(@Field("json") String json);

}
