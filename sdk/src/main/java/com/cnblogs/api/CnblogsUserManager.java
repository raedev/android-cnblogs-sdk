package com.cnblogs.api;

import android.app.Application;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.cnblogs.api.model.UserInfoBean;
import com.rae.session.SessionManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 用户管理
 */
public final class CnblogsUserManager {

    /**
     * 初始化用户管理器
     */
    static void init(Application application) {
        // SessionManager
        SessionManager.init(new SessionManager.Builder()
                .name("CnblogsUserManager")
                .withContext(application)
                .userClass(UserInfoBean.class).build());
    }


    /**
     * 获取当前用户
     */
    @Nullable
    public static UserInfoBean getUser() {
        return SessionManager.getDefault().getUser();
    }

    /**
     * 设置当前用户,并且发出通知，通知用户信息已更新
     *
     * @param user 用户信息
     */
    public static void setUser(@NonNull UserInfoBean user) {
        SessionManager.getDefault().setUser(user);
        EventBus.getDefault().post(user);
    }

    /**
     * 退出登录
     */
    public static void logout() {
        // 清除用户状态
        SessionManager.getDefault().clear();
        // 清除Cookie
        CookieManager cookieManager = CookieManager.getInstance();
        if (cookieManager != null) {
            cookieManager.removeAllCookie();
        }
        // 通知用户已退出
        EventBus.getDefault().post(new UserInfoEvent(UserInfoEvent.TYPE_LOGOUT));
    }

    /**
     * 是否登录
     */
    public static boolean isLogin() {
        return SessionManager.getDefault().isLogin();
    }

    /**
     * 设置登录Cookie
     */
    public static void setLoginCookie(String cookie) {
        CookieManager cookieManager = CookieManager.getInstance();
        if (cookieManager == null || TextUtils.isEmpty(cookie)) return;
        cookieManager.removeAllCookie(); // 移除所有Cookie
        String[] cookies = cookie.split(";");
        String url = "cnblogs.com";
        for (String item : cookies) {
            String[] itemCookie = item.split("=");
            if (itemCookie.length != 2) continue;
            String name = itemCookie[0];
            String value = itemCookie[1];
            String cookieValue = String.format("%s=%s; domain=.cnblogs.com; path=/; HttpOnly", name, value);
            cookieManager.setCookie(url, cookieValue);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
        } else {
            CookieSyncManager.getInstance().sync();
        }
    }

    /**
     * 通知更新用户信息
     */
    public static void notifyUpdateUserInfo() {
        // 通知用户已退出
        EventBus.getDefault().post(new UserInfoEvent(UserInfoEvent.TYPE_UPDATE_INFO));
    }
}
