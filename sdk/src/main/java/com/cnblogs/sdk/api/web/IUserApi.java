package com.cnblogs.sdk.api.web;

import com.cnblogs.sdk.api.param.AvatarUploadParam;
import com.cnblogs.sdk.internal.http.Empty;
import com.cnblogs.sdk.internal.http.HttpHeader;
import com.cnblogs.sdk.internal.http.annotation.HTML;
import com.cnblogs.sdk.internal.http.annotation.JSON;
import com.cnblogs.sdk.internal.parser.user.CheckDisplayNameParser;
import com.cnblogs.sdk.internal.parser.user.FollowerParser;
import com.cnblogs.sdk.internal.parser.user.ProfileParser;
import com.cnblogs.sdk.model.AccountInfo;
import com.cnblogs.sdk.model.BaseResponse;
import com.cnblogs.sdk.model.PersonalInfo;
import com.cnblogs.sdk.model.UploadAvatarBean;
import com.cnblogs.sdk.model.UserInfo;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 博客园用户接口
 * @author RAE
 * @date 2022/02/14
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface IUserApi {

    // region 个人信息相关

    /**
     * 用户信息
     * @return Observable
     */
    @GET("https://account.cnblogs.com/user/userinfo")
    @Headers({HttpHeader.ACCEPT_TYPE_JSON, HttpHeader.XHR})
    Observable<UserInfo> userInfo();


    /**
     * 个人信息
     * @param blogApp 用户
     * @return Observable
     */
    @GET("https://home.cnblogs.com/u/{blogApp}")
    @HTML(ProfileParser.class)
    Observable<PersonalInfo> profile(@Path("blogApp") String blogApp);

    // endregion

    // region 账号相关

    /**
     * 账号信息
     * @return Observable
     */
    @GET("https://account.cnblogs.com/api/account")
    @Headers({HttpHeader.ACCEPT_TYPE_JSON, HttpHeader.XHR})
    Observable<AccountInfo> accountInfo();

    /**
     * 更新头像
     * @param body 头像
     * @return Observable
     */
    @JSON
    @POST("https://upload.cnblogs.com/avatar/upload")
    @Headers({HttpHeader.ACCEPT_TYPE_JSON, HttpHeader.XHR})
    Observable<UploadAvatarBean> uploadAvatar(@Body AvatarUploadParam body);

    /**
     * 检查昵称是否可用
     * @param displayName 昵称
     * @return Observable
     */
    @POST("https://account.cnblogs.com/account/checkDisplayName")
    @FormUrlEncoded
    @Headers(HttpHeader.XHR)
    @HTML(CheckDisplayNameParser.class)
    Observable<Boolean> checkDisplayName(@Field("displayName") String displayName);

    /**
     * 更新昵称，一个月内只能更新3次。调用接口前使用{@link #checkDisplayName(String)}来检查昵称是否有用。
     * <p>前置条件：Cookie中[.Cnblogs.Account.Antiforgery]、[XSRF-TOKEN]必须携带值。</p>
     * <p>以上Cookie值可通过请求{@link #accountInfo()}接口获取得到。</p>
     * @param displayName 昵称
     * @return Observable
     */
    @JSON(transitive = true)
    @PUT("https://account.cnblogs.com/api/account/display-name")
    @FormUrlEncoded
    @Headers({HttpHeader.ACCEPT_TYPE_JSON, HttpHeader.JSON_PARAM, HttpHeader.XHR})
    Observable<BaseResponse> updateDisplayName(@Field("displayName") String displayName);

    // endregion

    // region 关注/粉丝 相关

    /**
     * 我关注的人
     * @param blogApp blogApp
     * @param page 页码
     * @return Observable
     */
    @GET("https://home.cnblogs.com/u/{blogApp}/relation/following")
    @HTML(FollowerParser.class)
    Observable<PersonalInfo> followers(@Path("blogApp") String blogApp, @Query("page") int page);

    /**
     * 我的粉丝
     * @param blogApp blogApp
     * @param page 页码
     * @return Observable
     */
    @GET("https://home.cnblogs.com/u/{blogApp}/relation/followers")
    @HTML(FollowerParser.class)
    Observable<PersonalInfo> fans(@Path("blogApp") String blogApp, @Query("page") int page);

    /**
     * 加关注
     * @param userId 用户ID
     * @param remark 备注
     * @return Observable
     */
    @JSON(transitive = true)
    @POST("https://home.cnblogs.com/ajax/follow/followUser")
    @FormUrlEncoded
    @Headers({HttpHeader.ACCEPT_TYPE_JSON, HttpHeader.XHR})
    Observable<Empty> follow(@Field("userId") String userId, @Query("remark") String remark);

    /**
     * 取消关注
     * @param userId 用户ID
     * @param isRemoveGroup 默认传false
     * @return Observable
     */
    @JSON(transitive = true)
    @POST("https://home.cnblogs.com/ajax/follow/RemoveFollow")
    @FormUrlEncoded
    @Headers({HttpHeader.ACCEPT_TYPE_JSON, HttpHeader.XHR})
    Observable<Empty> unfollow(@Field("userId") String userId, @Field("isRemoveGroup") Boolean isRemoveGroup);

    // endregion

}
