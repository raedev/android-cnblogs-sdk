package com.cnblogs.api;

import com.cnblogs.api.http.HtmlParser;
import com.cnblogs.api.model.AccountInfoBean;
import com.cnblogs.api.model.CnblogsJsonResult;
import com.cnblogs.api.model.MessageBean;
import com.cnblogs.api.model.SimpleUserInfoBean;
import com.cnblogs.api.model.UploadAvatarResult;
import com.cnblogs.api.model.UserInfoBean;
import com.cnblogs.api.param.ReplyMessageParam;
import com.cnblogs.api.param.ResetPasswordParam;
import com.cnblogs.api.param.UpdateAccountParam;
import com.cnblogs.api.param.UserNicknameParam;
import com.cnblogs.api.parser.user.FriendsParser;
import com.cnblogs.api.parser.user.InboxMessageParser;
import com.cnblogs.api.parser.user.MessageDetailParser;
import com.cnblogs.api.parser.user.UserInfoParser;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.adapter.rxjava2.AndroidObservable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 博客园用户接口
 * Created by rae on 2020-01-01.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface IUserApi {

    /**
     * 获取个人资料信息
     */
    @GET("https://account.cnblogs.com/api/account")
    @JsonParser
    AndroidObservable<AccountInfoBean> getAccountInfo();


    /**
     * 获取个人资料信息
     */
    @GET("https://account.cnblogs.com/api/user/info")
    @JsonParser
    AndroidObservable<SimpleUserInfoBean> getCurrentUserInfo();

    /**
     * 获取用户信息
     */
    @GET("https://home.cnblogs.com/u/{blogApp}")
    @HtmlParser(UserInfoParser.class)
    AndroidObservable<UserInfoBean> getUserInfo(@Path("blogApp") String blogApp);


    /**
     * 上传头像图片
     *
     * @param fileName       文件名称
     * @param headerFileName 文件名称
     * @param file           文件
     */
    @POST("https://upload.cnblogs.com/ImageUploader/TemporaryAvatarUpload")
    @Headers({
            "X-Mime-Type: image/jpeg",
            "Content-Type: application/octet-stream",
            "Accept: */*",
            "Referer: https://www.cnblogs.com",
            "Origin: https://upload.cnblogs.com",
            "X-Requested-With: XMLHttpRequest"
    })
    @JsonParser
    AndroidObservable<UploadAvatarResult> uploadAvatarImage(@Query("qqfile") String fileName, @Header("X-File-Name") String headerFileName, @Body RequestBody file);


    /**
     * 更新头像
     */
    @POST("https://upload.cnblogs.com/avatar/crop")
    @FormUrlEncoded
    @JsonParser
    AndroidObservable<UploadAvatarResult> updateAvatar(@Field("x") int x,
                                                       @Field("y") int y,
                                                       @Field("w") int w,
                                                       @Field("h") int h,
                                                       @Field("imgsrc") String url);


    /**
     * 更新昵称
     */
    @PUT("https://account.cnblogs.com/api/account/display-name")
    @JsonParser
    AndroidObservable<CnblogsJsonResult> updateNickName(@Body UserNicknameParam param);

    /**
     * 更新账号
     */
    @PUT("https://account.cnblogs.com/api/account/login-name")
    @JsonParser
    AndroidObservable<CnblogsJsonResult> updateLoginAccount(@Body UpdateAccountParam param);

    /**
     * 更新密码
     */
    @PUT("https://account.cnblogs.com/api/account/password")
    @JsonParser
    AndroidObservable<CnblogsJsonResult> resetPassword(@Body ResetPasswordParam param);


    /**
     * 获取粉丝列表
     */
    @GET("https://home.cnblogs.com/u/{blogApp}/relation/followers")
    @HtmlParser(FriendsParser.class)
    AndroidObservable<List<UserInfoBean>> getFans(@Path("blogApp") String blogApp, @Query("page") int page);

    /**
     * 获取关注列表
     */
    @GET("https://home.cnblogs.com/u/{blogApp}/relation/following")
    @HtmlParser(FriendsParser.class)
    AndroidObservable<List<UserInfoBean>> getFollows(@Path("blogApp") String blogApp, @Query("page") int page);

    /**
     * 系统消息列表
     *
     * @param type 类型：inbox 收件箱， unread 未读消息
     */
    @GET("https://msg.cnblogs.com/{type}/{page}")
    @HtmlParser(InboxMessageParser.class)
    AndroidObservable<List<MessageBean>> getMessages(@Path("type") String type, @Path("page") int page);


    /**
     * 消息详情
     *
     * @param id 消息Id
     */
    @GET("https://msg.cnblogs.com/item/{id}")
    @HtmlParser(MessageDetailParser.class)
    AndroidObservable<List<MessageBean>> getMessageDetail(@Path("id") String id);


    /**
     * 回复消息
     */
    @POST("https://msg.cnblogs.com/api/message/reply")
    @JsonParser(source = true)
    AndroidObservable<String> replyMessage(@Body ReplyMessageParam param);

}
