package com.cnblogs.api;

import android.app.Application;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
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
import io.reactivex.disposables.Disposable;
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
        String cookie = "_ga=GA1.2.646038017.1597584943; .Cnblogs.AspNetCore.Cookies=CfDJ8K5MrGQfPjpFvRyctF-QEQcvOHeHQPSsTPWGyOPRyuLQUiRfOlXH_rRWXc_Tv2SUp0856ptMOJm5vAb0_DcpuA3mbDftDXMDsPMFmDQVqdwtPkqrejgnCzdtsr0CucxxGOucAiKbuSsU1BnHbWL0Y-Pbqw4xZDjBQtUr-xaul47zZlwgWTmelssyXGkFUA5DzsxzdZ1s02vbvXYKFcOkso5OdzthzHFeMoPCuEuIU3Y4Tpm_jTP0T-6eg_-lGzbV1aKL2Cg7ibyz2TWle90QpFL1ep1qbDr9B_GMwM7aHbHuvywgt2mbj_mBd5AcoX6c8Ss-qrZv7sBwry3IfiTGiMIXIIAoHxkABQtjELsvJ6u4DAbMTeOvOEr0rYpjJtaxze4WusNRV6BzeYzDJU3p3oRmPWWpQygJkpa5t1cO3HZjhwClVfFMyKoeOqCf2SHZJb0M8_3wmoOR9m1P0SvvQRMNdaHXFqkzBNVcvklfH8d6NbC1tt34TLtiUZPEiEAlxUecBK59AC8Xj-dZXReeYGayDSt2Hyk9oZP62KZ2NFDlDVg9XEzz3tmjNvji543CMQ; Hm_lvt_39b794a97f47c65b6b2e4e1741dcba38=1601037325,1601045935,1601261629,1601262126; _gid=GA1.2.1371163887.1601883227; _gat=1; SERVERID=6348d13ff393b8a263cfff7dae61eb75|1601883226|1601883226";
        setLoginCookie(cookie);
        Disposable subscribe = requestUserInfo().subscribe(userInfoBean -> Log.i("cnblogs", "Mock登录成功：" + userInfoBean.getBlogApp()), throwable -> Log.e("cnblogs", "Mock登录失败，" + throwable.getMessage(), throwable));
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
     * 请求用户信息，并保持在SessionManger中
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
