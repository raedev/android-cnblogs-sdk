package com.cnblogs.sdk.provider;

import com.cnblogs.sdk.api.IAccountApi;
import com.cnblogs.sdk.api.IBlogApi;
import com.cnblogs.sdk.api.ICategoryApi;
import com.cnblogs.sdk.api.IUserApi;

/**
 * 博客园网页接口提供者
 * @author RAE
 * @date 2021/02/10
 */
public class CnblogsWebApiProvider extends BaseApiProvider {

    @Override
    protected String getName() {
        return "CnblogsWebApiProvider";
    }

    /**
     * 创建接口实例
     * @param cls 接口类
     * @param <T> 类型
     * @return 实例
     */
    @SuppressWarnings("unchecked")
    protected <T> T createApi(Class<T> cls) {
        String key = cls.getName();
        Object api = mApiCacheMap.get(key);
        if (api == null) {
            T t = mRetrofit.create(cls);
            mApiCacheMap.put(key, t);
            return t;
        }
        return (T) api;
    }

    /**
     * 获取账号接口
     */
    public IAccountApi getAccountApi() {
        return createApi(IAccountApi.class);
    }

    /**
     * 获取用户接口
     */
    public IUserApi getUserApi() {
        return createApi(IUserApi.class);
    }

    /**
     * 分类接口
     */
    public ICategoryApi getCategoryApi() {
        return createApi(ICategoryApi.class);
    }

    /**
     * 博客接口
     */
    public IBlogApi getBlogApi() {
        return createApi(IBlogApi.class);
    }
}
