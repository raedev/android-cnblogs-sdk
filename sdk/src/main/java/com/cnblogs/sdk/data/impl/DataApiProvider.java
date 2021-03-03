package com.cnblogs.sdk.data.impl;

import com.cnblogs.sdk.data.api.IBlogDataApi;
import com.cnblogs.sdk.data.api.ICategoryDataApi;
import com.cnblogs.sdk.data.api.IUserDataApi;
import com.cnblogs.sdk.exception.CnblogsSdkException;
import com.cnblogs.sdk.provider.CnblogsBaseApiProvider;

/**
 * 数据接口提供者
 * @author RAE
 * @date 2021/02/25
 */
public class DataApiProvider extends CnblogsBaseApiProvider {

    @SuppressWarnings("unchecked")
    protected <T> T createDataApi(Class<T> cls) {
        String key = cls.getName();
        Object api = mApiCacheMap.get(key);
        if (api == null) {
            try {
                T t = cls.newInstance();
                mApiCacheMap.put(key, t);
                return t;
            } catch (Throwable e) {
                throw new CnblogsSdkException("实例化接口异常！", e);
            }
        }
        return (T) api;
    }

    /**
     * 获取用户数据接口
     */
    public IUserDataApi getUserDataApi() {
        return createDataApi(UserDataApiImpl.class);
    }

    /**
     * 获取分类数据接口
     */
    public ICategoryDataApi getCategoryDataApi() {
        return createDataApi(CategoryDataApiImpl.class);
    }

    /**
     * 获取博客数据接口
     */
    public IBlogDataApi getBlogDataApi() {
        return createDataApi(BlogDataApiImpl.class);
    }
}
