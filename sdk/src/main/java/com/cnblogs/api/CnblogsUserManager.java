package com.cnblogs.api;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cnblogs.api.model.UserInfoBean;
import com.rae.session.SessionManager;

/**
 * 用户管理
 */
public final class CnblogsUserManager {

    static void init(Application application) {
        // SessionManager
        SessionManager.init(new SessionManager.Builder()
                .name("CnblogsUserManager")
                .withContext(application)
                .userClass(UserInfoBean.class).build());
    }


    @Nullable
    public static UserInfoBean getUser() {
        return SessionManager.getDefault().getUser();
    }

    public static void setUser(@NonNull UserInfoBean user) {
        SessionManager.getDefault().setUser(user);
    }

    public static void logout() {
        SessionManager.getDefault().clear();
    }

    public static boolean isLogin() {
        return SessionManager.getDefault().isLogin();
    }
}
