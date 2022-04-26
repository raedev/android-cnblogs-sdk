package com.cnblogs.sdk.provider;

import android.content.Context;

import com.cnblogs.sdk.api.data.IBlogDataApi;
import com.cnblogs.sdk.internal.data.BlogDataApiImpl;

/**
 * 数据提供者，一般来说调用者只要知道这个接口即可
 * 对Api接口和本地接口进行统一封装调用
 * @author RAE
 * @date 2022/02/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class DataApiProvider {

    private final Context mContext;
    private final WebApiProvider mWebApiProvider;

    public DataApiProvider(Context context, WebApiProvider webApiProvider) {
        mContext = context;
        mWebApiProvider = webApiProvider;
    }

    public IBlogDataApi getBlogDataApi() {
        return new BlogDataApiImpl(mContext, mWebApiProvider);
    }
}
