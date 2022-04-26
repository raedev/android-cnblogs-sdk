package com.cnblogs.sdk.internal.data;

import android.content.Context;

import com.cnblogs.sdk.provider.WebApiProvider;

/**
 * 数据接口基类
 * @author RAE
 * @date 2022/03/13
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public abstract class BaseDataApi {

    private final Context mContext;
    private final WebApiProvider mWebApiProvider;

    public BaseDataApi(Context context, WebApiProvider webApiProvider) {
        mContext = context;
        mWebApiProvider = webApiProvider;
    }

    protected Context getContext() {
        return mContext;
    }

    protected WebApiProvider getWebApiProvider() {
        return mWebApiProvider;
    }
}
