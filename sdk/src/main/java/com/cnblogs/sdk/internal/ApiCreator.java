package com.cnblogs.sdk.internal;

import com.cnblogs.sdk.exception.CnblogsSdkException;

import retrofit2.Retrofit;

/**
 * 接口创建者，负责Api的创建
 * @author RAE
 * @date 2021/03/04
 */
@SuppressWarnings("unchecked")
public final class ApiCreator {

    /**
     * 缓存接口，因为调用频繁，避免过多的实例化操作
     */
    private static final ObjectCacheHashMap<String, Object> API_CACHE_MAP = new ObjectCacheHashMap<>(12);

    private ApiCreator() {
        throw new IllegalStateException("hey guys!");
    }


    /**
     * 创建接口实例
     * @param retrofit Retrofit
     * @param cls 接口类
     * @param <T> 类型
     * @return 实例
     */
    public static <T> T create(Retrofit retrofit, Class<T> cls) {
        String key = cls.getName();
        Object api = API_CACHE_MAP.get(key);
        if (api == null) {
            T t = retrofit.create(cls);
            API_CACHE_MAP.put(key, t);
            return t;
        }
        return (T) api;
    }

    /**
     * 创建接口实例
     * @param cls 接口类
     * @param <T> 类型
     * @return 实例
     */
    public static <T> T create(Class<T> cls) {
        String key = cls.getName();
        Object api = API_CACHE_MAP.get(key);
        if (api == null) {
            try {
                T t = cls.newInstance();
                API_CACHE_MAP.put(key, t);
                return t;
            } catch (Throwable e) {
                throw new CnblogsSdkException("实例化接口异常！", e);
            }
        }
        return (T) api;
    }
}
