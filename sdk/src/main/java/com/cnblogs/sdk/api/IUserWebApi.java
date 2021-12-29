package com.cnblogs.sdk.api;

import com.cnblogs.sdk.internal.ApiConstant;
import com.cnblogs.sdk.parser.HtmlParser;
import com.cnblogs.sdk.model.ProfileInfo;
import com.cnblogs.sdk.model.ResponseInfo;
import com.cnblogs.sdk.parser.user.ProfileInfoParser;
import com.cnblogs.sdk.parser.user.RelationParser;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 博客园用户接口
 * @author RAE
 * @date 2021/02/10
 */
public interface IUserWebApi {

    /**
     * 获取个人中心信息
     * @param blogApp blogApp
     * @return 个人中心信息
     */
    @GET(ApiConstant.API_USER_PROFILE_INFO)
    @HtmlParser(ProfileInfoParser.class)
    Observable<ProfileInfo> getProfileInfo(@Path("blogApp") String blogApp);

    /**
     * 关注列表
     * @param blogApp blogApp
     * @param page 页码
     * @return 关注
     */
    @GET(ApiConstant.API_USER_FOLLOW)
    @HtmlParser(RelationParser.class)
    Observable<List<ProfileInfo>> getFollows(@Path("blogApp") String blogApp, @Query("page") int page);

    /**
     * 粉丝列表
     * @param blogApp blogApp
     * @param page 页码
     * @return 粉丝
     */
    @GET(ApiConstant.API_USER_FANS)
    @HtmlParser(RelationParser.class)
    Observable<List<ProfileInfo>> getFans(@Path("blogApp") String blogApp, @Query("page") int page);

    /**
     * 关注博主
     * @param userId 用户ID，注意不是blogApp
     * @param remark 显示的备注名称
     * @return 公共实体
     */
    Observable<ResponseInfo> followUser(@Field("userId") String userId, @Field("remark") String remark);

}
