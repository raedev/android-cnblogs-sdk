package com.cnblogs.sdk.data.impl;

import com.cnblogs.sdk.CnblogsSdk;
import com.cnblogs.sdk.provider.CnblogsWebApiProvider;

/**
 * 数据接口基类
 * @author RAE
 * @date 2021/02/20
 */
public abstract class BaseDataApi {

    protected CnblogsWebApiProvider getWebApiProvider() {
        return CnblogsSdk.getInstance().getWebApiProvider();
    }
}
