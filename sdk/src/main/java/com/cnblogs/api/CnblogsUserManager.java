package com.cnblogs.api;

import android.app.Application;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cnblogs.api.model.AccountInfoBean;
import com.cnblogs.api.model.SimpleUserInfoBean;
import com.cnblogs.api.model.UserInfoBean;
import com.rae.cnblogs.sdk.CnblogsSDK;
import com.rae.session.SessionManager;
import com.rae.session.SessionUserInfo;

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
//        setLoginCookie("_ga=GA1.2.578499859.1564977874; .AspNetCore.Antiforgery.b8-pDmTq1XM=CfDJ8Nf-Z6tqUPlNrwu2nvfTJEirIt99za7kLPUw0s0JPcrH73SUoStRCvkzWuIs5a5xJ5-7DO1j4g54kBtPtgukY_7pIsbsAHl-H8YvD97p0_istSrpo5cuBAlTenmMWlDc_x0jDtTaz6gWLN1us5a8FhY; _gid=GA1.2.760874555.1580535764; .Cnblogs.AspNetCore.Cookies=CfDJ8Nf-Z6tqUPlNrwu2nvfTJEjf_wW5J4dWSaOgsH-W4aH84shgSPZSdEePpncookOl2lx8gBHp_Rrz4-1VKVILT4HnHtYOoqflerpNlk0P4derszGqfW_WEKhNRjvCt9-eW1jFXo2AA3Nl8kaw0CU1C9lnOfg2ftAL_Hh83MSwOqppCcXmsHOqChcCX4bxWyZU_7LytJAUMJPm1zqqLFoamuIUF1fJRJyUcP_na5aDyiuvJjUM5yDXoXAAPiuYK9PDEnmax3egPLxGf7q1Gto6o5eqHsw7qJIlmOQOxtv1ueq8nGj6IDL0gfaeWKkzRDKkL9M5sM2s12r7QRtvf9_qEjjwJzi3KzreVADhgaSrkB0iaDclH0sTWnoUUgBdAS7aq1kgJM7on4LpLWwBrKskvKAcovdCIP0YcFxzg475GfR8sUQjhm3xaGIzEGW7yp_jL3OPRERyt5YwnRnJtwn2YJUN9IlmJAL3L7GYXpNAjZiPbUJZh-hQz8QYqesDb1N_TmhW0aujBRityaupbgvs6tpEKzFKb7K1qvQJfom2Mna4; .CNBlogsCookie=FCF1FEF6BE12FD9DB9158E2991511A55DBFB1EFB1A7561B185180CC5E4AA1DE33EE2F57464055E50286D5F24815A9231919C7BBB7ED8702E6F961CA4F65E3A8EC8C22A0ED62BC1BE182D1C2FCE9245DEB69A084A");
        setLoginCookie("_ga=GA1.2.578499859.1564977874; _dx_uzZo5y=eccfe38c11ed246f7d63598361efdc1fe0c38635290b843026dfe7ff6b31c9a5dae0bfa5; _gid=GA1.2.290866735.1582380945; _gat=1; .Cnblogs.AspNetCore.Cookies=CfDJ8Nf-Z6tqUPlNrwu2nvfTJEiHQSN6Tx5EGDbS_eaNPmdVnuWaHy8coY7tmi45pK2Qq0vdP5dCKQ2UbjyRyfToCgTW7v17T3x-L9qPKpJp8R0G96F8mAY4Lj6COJI3S1wyu5hrnpgKaDAkEvE2eFds0tWfhZg9liNovLPTXZ_ZTX42bu0TAWtjOIdiS6SrZYue_QM1DwnCbnqSsyu_VV_vpR1FC787x59J--k-bkRfulch0qY6Xp49YxiGM592DVlIuwQ9wtK-Y1ZSB-Z4Fkh-VMlifJ4WFRxrqWyl_nq_gaqaPswa3iHQ91Ap6ASlJPlZLvupF748pqBc2Jq2e52vqEN6eNbJ17WPu_BU7WiB-WXqLUGb16hdhvNrhJuLqavKQjlfTyOwhPDd79LFdhghW7tPOjTvmJsUZPmZ8c_RjHeXanlkb-gTTNfYGBNCVXdMRdMcApvajNuZ7_b40B0tGoInVJyALDgiRM8pSHyAL7TDrIm0bPF92zEGIVjdaoy8uPvwCx-q4ty4IKKYsai1vCSs23rCpqXHfsMFolDpnv-zgZDhSSOeEfAyJYsEVKDCcA; .CNBlogsCookie=2C097F148E1A9CACEF8665F642BC241F160F1E86957046CEFCAE748F16C8BE78CC3841743FD5F9774291C24C4F3E76A0EEB8A55C55416D5743AA90790CA7C454DF2AB08826F68F226C7A1C07A03CE06B43239E4B");
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
        SessionUserInfo gatewayUser = new SessionUserInfo();
        gatewayUser.setName(user.getDisplayName());
        gatewayUser.setUserId(user.getBlogApp());
        CnblogsSDK.getInstance().setUser(gatewayUser);

        // 通知
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
        // 关联网关接口状态
        CnblogsSDK.getInstance().getSessionManager().clear();
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
                .flatMap(new Function<SimpleUserInfoBean, ObservableSource<UserInfoBean>>() {
                    @Override
                    public ObservableSource<UserInfoBean> apply(final SimpleUserInfoBean simpleUserInfo) {
                        String blogApp = TextUtils.isEmpty(simpleUserInfo.getBlogApp()) ? simpleUserInfo.getUserId() : simpleUserInfo.getBlogApp();
                        return userApi.getUserInfo(blogApp)
                                .map(new Function<UserInfoBean, UserInfoBean>() {
                                    @Override
                                    public UserInfoBean apply(UserInfoBean userInfoBean) {
                                        userInfoBean.setUserId(simpleUserInfo.getUserId());
                                        userInfoBean.setDisplayName(simpleUserInfo.getDisplayName());
                                        return userInfoBean;
                                    }
                                });
                    }
                })
                // 获取登录账号信息
                .flatMap(new Function<UserInfoBean, ObservableSource<? extends UserInfoBean>>() {
                    @Override
                    public ObservableSource<? extends UserInfoBean> apply(final UserInfoBean userInfoBean) {
                        return userApi.getAccountInfo().map(new Function<AccountInfoBean, UserInfoBean>() {
                            @Override
                            public UserInfoBean apply(AccountInfoBean accountInfoBean) {
                                userInfoBean.setLoginAccount(accountInfoBean.getLoginName());
                                return userInfoBean;
                            }
                        });
                    }
                })
                // 设置用户信息
                .map(new Function<UserInfoBean, UserInfoBean>() {
                    @Override
                    public UserInfoBean apply(UserInfoBean userInfoBean) {
                        setUser(userInfoBean);
                        return userInfoBean;
                    }
                });
    }
}
