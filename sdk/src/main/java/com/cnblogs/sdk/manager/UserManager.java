package com.cnblogs.sdk.manager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cnblogs.sdk.model.UserInfo;
import com.github.raedev.swift.session.SessionManager;
import com.github.raedev.swift.session.SessionStateListener;

import java.util.Objects;

/**
 * 用户管理器
 * @author RAE
 * @date 2021/12/29
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class UserManager {

    private static UserManager sUserManager;
    private final SessionManager mManager;

    public UserManager(Context context) {
        mManager = new SessionManager.Builder(context)
                .setSessionName("CnblogsUserManager")
                .setUserClass(UserInfo.class)
                .build();
    }

    public static void initManager(Context context) {
        if (sUserManager == null) {
            sUserManager = new UserManager(context);
        }
    }

    @NonNull
    public static UserManager getInstance() {
        return Objects.requireNonNull(sUserManager, "请先初始化接口工厂ApiFactory");
    }

    /**
     * 获取当前登录的用户信息
     * @return 用户信息
     */
    @Nullable
    public UserInfo getUser() {
        return mManager.getUserInfo();
    }

    /**
     * 设置用户信息，实现登录
     * @param user 用户信息
     */
    public void setUser(UserInfo user) {
        mManager.setUserInfo(user);
    }

    /**
     * @return 是否登录
     */
    public boolean isUnauthorized() {
        return getUser() == null;
    }

    /**
     * 退出登录
     */
    public void clear() {
        mManager.forgot();
    }

    /**
     * 监听用户信息改变
     * @param listener listener
     */
    public void addSessionStateListener(SessionStateListener listener) {
        mManager.addSessionStateListener(listener);
    }

    /**
     * 移除监听
     * @param listener listener
     */
    public void removeSessionStateListener(SessionStateListener listener) {
        mManager.removeSessionStateListener(listener);
    }

}
