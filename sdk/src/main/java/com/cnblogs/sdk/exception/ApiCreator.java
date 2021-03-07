package com.cnblogs.sdk.exception;

import com.cnblogs.sdk.internal.ObjectCacheHashMap;

import java.lang.reflect.Constructor;

import retrofit2.Retrofit;

/**
 * 接口创建者，负责Api的创建
 *
 * @author RAE
 * @date 2021/03/04
 */
@SuppressWarnings("unchecked")
public final class ApiCreator {

    /**
     * 缓存接口，因为调用频繁，避免过多的实例化操作
     */
    static final ObjectCacheHashMap<String, Object> mApiCacheMap = new ObjectCacheHashMap<>(10);

    private ApiCreator() {
    }

    /**
     * 创建接口实例
     *
     * @param retrofit Retrofit
     * @param cls 接口类
     * @param <T> 类型
     * @return 实例
     */
    public static <T> T create(Retrofit retrofit, Class<T> cls) {
        String key = cls.getName();
        Object api = mApiCacheMap.get(key);
        if (api == null) {
            T t = retrofit.create(cls);
            mApiCacheMap.put(key, t);
            return t;
        }
        return (T) api;
    }

    /**
     * 创建接口实例
     *
     * @param cls 接口类
     * @param <T> 类型
     * @return 实例
     */
    public static <T> T create(Class<T> cls) {
        String key = cls.getName();
        Object api = mApiCacheMap.get(key);
        if (api == null) {
            try {
                Constructor<T> constructor = cls.getConstructor();
                constructor.setAccessible(true);
                T t = constructor.newInstance();
                mApiCacheMap.put(key, t);
                return t;
            } catch (Throwable e) {
                throw new CnblogsSdkException("实例化接口异常！", e);
            }
        }
        return (T) api;
    }
}
