package com.cnblogs.api;

import android.app.Application;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cnblogs.api.model.SimpleUserInfoBean;
import com.cnblogs.api.model.UserInfoBean;
import com.rae.cnblogs.sdk.CnblogsSDK;
import com.rae.cnblogs.sdk.model.CnblogsUserInfo;
import com.rae.session.SessionManager;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

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
     * 模拟登录状态，仅仅用于调试
     */
    public static void mockLogin() {
        setLoginCookie("_ga=GA1.2.578499859.1564977874; _dx_uzZo5y=eccfe38c11ed246f7d63598361efdc1fe0c38635290b843026dfe7ff6b31c9a5dae0bfa5; _gid=GA1.2.732311221.1587634491; _gat=1; .Cnblogs.AspNetCore.Cookies=CfDJ8B9DwO68dQFBg9xIizKsC6QwWMgkHX56fQx6lks3XFX30E7dthizE9L1LxeqIKnAFc-MKa688YJ3r_WLaItmQLUwYmu6Og39BIqV_VncP_tXIaCkw2xSYOShwACWx8tu_wCutdAUle2R3tdw_dvQPz8UvPF5Pz1ihlscpcc6duHjp1LzjU0WHau--25vFF8Evfy0QJBDed6HwWFu8ZjjT0K2YLczzKEluq_kUyvDGcjuZbtVnkYFITs7ennps0Bct_X6UYHbZV3CkbKRyBAgIqw8Iwh0bnlfkN0fk8xofrNkki38UBRJWzMSXSFSlGiDXkhtwGz3Tm4zZENhts4_DerDLG3HG2m1nCpNq6l4zh0eLkCHCMjB15qohMMpvboxlQnKkbYbWsFyacskUOkreO_5s7fSJUnQzzamSH0EjNwa1Pn2nO2kETwgh6LqXeNYppB2We-n4ldVHmPbFZ1XhnrQSGPJcYYDgYfU1UZB43GQGynX6gOTRcdO7mMTqlliT4HDZl3j_zwJKt3u7BPEl5b38u0k6v12c5l-00a0uu9XGHKY67QlnCZfai3GQCzzRw; .CNBlogsCookie=77839CAF05272BA3CA77E00B574C4422841C3F4DBB3C4E91B603298443CF581AAF52D03BA079BA41B8176E98101999626FDD09C6A4EDB7FC1D93804D52782018B9D8CF170C9BC43BE3483AA8AE7ACD76915C6FC5");
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
        // 设置用户
        SessionManager.getDefault().setUser(user);

        // 关联网关接口用户
        CnblogsUserInfo gatewayUser = new CnblogsUserInfo();
        gatewayUser.setName(user.getDisplayName());
        gatewayUser.setUserId(user.getBlogApp());
        CnblogsSDK.getInstance().getSessionManager().setUser(gatewayUser);

        // 通知
        EventBus.getDefault().post(user);
    }

    /**
     * 退出登录
     */
    public static void logout() {
        clear();
        // 通知用户已退出
        EventBus.getDefault().post(new UserInfoEvent(UserInfoEvent.TYPE_LOGOUT));
    }

    /**
     * 清除用户登录信息，不会发出通知
     */
    public static void clear() {
        // 清除用户状态
        SessionManager.getDefault().clear();
        // 清除Cookie
        CookieManager cookieManager = CookieManager.getInstance();
        if (cookieManager != null) {
            cookieManager.removeAllCookie();
        }
        // 关联网关接口状态
        CnblogsSDK.getInstance().getSessionManager().clear();
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
    private static void setLoginCookie(String cookie) {
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
        EventBus.getDefault().post(new UserInfoEvent(UserInfoEvent.TYPE_UPDATE_INFO));
    }


    /**
     * 请求用户信息
     */
    public static Observable<UserInfoBean> requestUserInfo() {
        final IUserApi userApi = CnblogsOpenApi.getInstance().getUserApi();
        // 获取当前用户信息
        return userApi.getCurrentUserInfo()
                // 根据blogApp获取更加详细的用户信息
                .flatMap((Function<SimpleUserInfoBean, ObservableSource<UserInfoBean>>) simpleUserInfo -> {
                    String blogApp = TextUtils.isEmpty(simpleUserInfo.getBlogApp()) ? simpleUserInfo.getUserId() : simpleUserInfo.getBlogApp();
                    return userApi.getUserInfo(blogApp)
                            .map(userInfoBean -> {
                                userInfoBean.setUserId(simpleUserInfo.getUserId());
                                userInfoBean.setDisplayName(simpleUserInfo.getDisplayName());
                                return userInfoBean;
                            });
                })
                // 获取登录账号信息
                .flatMap(userInfoBean -> userApi.getAccountInfo().map(accountInfoBean -> {
                    userInfoBean.setLoginAccount(accountInfoBean.getLoginName());
                    return userInfoBean;
                }))
                // 设置用户信息
                .map(userInfoBean -> {
                    setUser(userInfoBean);
                    return userInfoBean;
                });
    }
}
