package com.cnblogs.sdk.data.api;

import androidx.annotation.Nullable;

import com.cnblogs.sdk.model.ProfileInfo;
import com.cnblogs.sdk.model.UserInfo;

import java.util.List;

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
    Observable<UserInfo> queryUserInfo();

    /**
     * 获取个人中心信息
     * @param blogApp 如果传Null查询当前用户
     * @return 个人中心信息
     */
    Observable<ProfileInfo> queryProfileInfo(@Nullable String blogApp);

    /**
     * 获取我的关注
     * @param blogApp blogApp
     * @param page 分页
     * @return 粉丝列表
     */
    Observable<List<ProfileInfo>> queryFollows(@Nullable String blogApp, int page);

    /**
     * 获取我的粉丝
     * @param blogApp blogApp
     * @param page 分页
     * @return 粉丝列表
     */
    Observable<List<ProfileInfo>> queryFans(@Nullable String blogApp, int page);

    /**
     * 退出登录
     * @return 退出成功返回当前登录的用户，退出失败走异常
     */
    Observable<UserInfo> logout();
}
