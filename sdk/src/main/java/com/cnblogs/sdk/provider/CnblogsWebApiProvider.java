package com.cnblogs.sdk.provider;

import com.cnblogs.sdk.api.ICnblogsUserApi;

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
        Object api = mLruCache.get(key);
        if (api == null) {
            T t = mRetrofit.create(cls);
            mLruCache.put(key, t);
            return t;
        }
        return (T) api;
    }

    /**
     * 获取用户接口
     */
    public ICnblogsUserApi getUserApi() {
        return createApi(ICnblogsUserApi.class);
    }
}
