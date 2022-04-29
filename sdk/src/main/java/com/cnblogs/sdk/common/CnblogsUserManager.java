package com.cnblogs.sdk.common;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.cnblogs.sdk.CnblogsFactory;
import com.cnblogs.sdk.model.UserInfo;
import com.github.raedev.swift.session.SharedPreferencesSessionManager;

import java.util.Objects;

/**
 * 用户管理器
 * <p>使用前需先初始化接口工厂{@link CnblogsFactory#initFactory(Application)}</p>
 * @author RAE
 * @date 2021/12/29
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class CnblogsUserManager extends SharedPreferencesSessionManager {

    private static CnblogsUserManager sUserManager;

    protected CnblogsUserManager(Context context) {
        super(context, "CnblogsUserManager", UserInfo.class);
    }

    @NonNull
    public static CnblogsUserManager getInstance() {
        if (sUserManager == null) {
            sUserManager = new CnblogsUserManager(CnblogsFactory.getContext());
        }
        return Objects.requireNonNull(sUserManager, "请先初始化接口工厂ApiFactory");
    }

    /**
     * @return 是否登录
     */
    public boolean isUnauthorized() {
        return !isLogin();
    }

    /**
     * 退出登录
     */
    public void clear() {
        super.forgot();
    }

    /**
     * 设置登录Cookie
     * @param cookieValue 登录Cookie值，key = .Cnblogs.AspNetCore.Cookies
     */
    public void setLoginCookie(String cookieValue) {
        CookieSynchronizer.getInstance().setCookie(".Cnblogs.AspNetCore.Cookies", cookieValue);
    }
}
