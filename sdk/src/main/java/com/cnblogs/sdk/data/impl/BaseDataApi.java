package com.cnblogs.sdk.data.impl;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.cnblogs.sdk.CnblogsSdk;
import com.cnblogs.sdk.data.dao.CnblogsDatabase;
import com.cnblogs.sdk.model.UserInfo;
import com.cnblogs.sdk.provider.CnblogsGatewayApiProvider;
import com.cnblogs.sdk.provider.CnblogsWebApiProvider;

import java.util.List;

/**
 * 数据接口基类
 *
 * @author RAE
 * @date 2021/02/20
 */
public abstract class BaseDataApi {

    @NonNull
    protected CnblogsWebApiProvider getWebApiProvider() {
        return CnblogsSdk.getInstance().getWebApiProvider();
    }

    protected CnblogsGatewayApiProvider getGatewayApiProvider(){
        return CnblogsSdk.getInstance().getGatewayApiProvider();
    }

    @NonNull
    protected CnblogsDatabase getDatabase() {
        return CnblogsSdk.getInstance().getDatabase();
    }

    protected UserInfo getUserInfo() {
        return CnblogsSdk.getSessionManager().getUserInfo();
    }

    /**
     * 获取用户ID，用户没有登录返回游客账号
     * 该方法不用作判断是否登录
     * @return 用户ID
     */
    @NonNull
    protected String getUserId() {
        UserInfo userInfo = getUserInfo();
        if (userInfo != null) {
            return userInfo.getBlogApp();
        }
        return "Guest";
    }

    protected boolean isLogin() {
        return getUserInfo() != null;
    }

    protected boolean isEmpty(String text) {
        return TextUtils.isEmpty(text);
    }

    protected <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }
}
