package com.cnblogs.sdk.data.impl;

import androidx.annotation.NonNull;

import com.cnblogs.sdk.CnblogsSdk;
import com.cnblogs.sdk.db.CnblogsDatabase;
import com.cnblogs.sdk.model.UserInfo;
import com.cnblogs.sdk.provider.CnblogsWebApiProvider;

/**
 * 数据接口基类
 * @author RAE
 * @date 2021/02/20
 */
public abstract class BaseDataApi {

    @NonNull
    protected CnblogsWebApiProvider getWebApiProvider() {
        return CnblogsSdk.getInstance().getWebApiProvider();
    }

    @NonNull
    protected CnblogsDatabase getDatabase() {
        return CnblogsSdk.getInstance().getDatabase();
    }

    protected UserInfo getUserInfo() {
        return CnblogsSdk.getSessionManager().getUserInfo();
    }

    protected boolean isLogin() {
        return getUserInfo() != null;
    }
}
