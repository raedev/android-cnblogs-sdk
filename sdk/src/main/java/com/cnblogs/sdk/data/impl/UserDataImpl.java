package com.cnblogs.sdk.data.impl;

import androidx.annotation.Nullable;

import com.cnblogs.sdk.CnblogsSdk;
import com.cnblogs.sdk.api.IAccountApi;
import com.cnblogs.sdk.api.IUserApi;
import com.cnblogs.sdk.data.api.UserDataApi;
import com.cnblogs.sdk.exception.CnblogsSdkException;
import com.cnblogs.sdk.exception.CnblogsTokenException;
import com.cnblogs.sdk.http.Composer;
import com.cnblogs.sdk.model.ProfileInfo;
import com.cnblogs.sdk.model.UserInfo;
import com.cnblogs.sdk.parser.HtmlParserUtils;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;

/**
 * 用户接口
 * @author RAE
 * @date 2021/02/20
 */
public class UserDataImpl extends BaseDataApi implements UserDataApi {

    private final IAccountApi mAccountApi;
    private final IUserApi mUserApi;

    public UserDataImpl() {
        mAccountApi = getWebApiProvider().getAccountApi();
        mUserApi = getWebApiProvider().getUserApi();
    }

    @Override
    public Observable<UserInfo> queryUserInfo() {
        return mAccountApi.getUserInfo()
                .map(userInfo -> {
                    // 修复字段信息
                    userInfo.setAvatarName(HtmlParserUtils.fixUrl(userInfo.getAvatarName()));
                    userInfo.setIconName(HtmlParserUtils.fixUrl(userInfo.getIconName()));
                    userInfo.setHomeLink(HtmlParserUtils.fixUrl(userInfo.getHomeLink()));
                    userInfo.setBlogLink(HtmlParserUtils.fixUrl(userInfo.getBlogLink()));
                    return userInfo;
                })
                .doOnNext(userInfo -> {
                    // 保存到Session
                    CnblogsSdk.getSessionManager().setUserInfo(userInfo);
                });
    }

    @Override
    public Observable<ProfileInfo> queryProfileInfo(@Nullable String blogApp) {
        return Observable.create((ObservableOnSubscribe<String>) emitter -> {
            if (blogApp != null) {
                emitter.onNext(blogApp);
                emitter.onComplete();
                return;
            }
            UserInfo userInfo = CnblogsSdk.getSessionManager().getUserInfo();
            if (userInfo == null) {
                throw new CnblogsTokenException();
            }
            emitter.onNext(userInfo.getBlogApp());
            emitter.onComplete();
        }).flatMap((Function<String, ObservableSource<ProfileInfo>>) mUserApi::getProfileInfo);
    }

    @Override
    public Observable<List<ProfileInfo>> queryFollows(@Nullable String blogApp, int page) {
        if (blogApp == null && isLogin()) {
            blogApp = getUserInfo().getBlogApp();
        }
        return mUserApi.getFollows(blogApp, page);
    }

    @Override
    public Observable<List<ProfileInfo>> queryFans(@Nullable String blogApp, int page) {
        if (blogApp == null && isLogin()) {
            blogApp = getUserInfo().getBlogApp();
        }
        return mUserApi.getFans(blogApp, page);
    }

    @Override
    public Observable<UserInfo> logout() {
        return Observable.create((ObservableOnSubscribe<UserInfo>) emitter -> {
            // 退出会话
            UserInfo userInfo = CnblogsSdk.getSessionManager().getUserInfo();
            if (userInfo == null) {
                throw new CnblogsSdkException("没有登录，无需退出");
            }
            CnblogsSdk.getSessionManager().forgot();
            emitter.onNext(userInfo);
            emitter.onComplete();
        }).compose(Composer.mainThread());
    }
}
