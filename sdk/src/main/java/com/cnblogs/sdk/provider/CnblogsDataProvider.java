package com.cnblogs.sdk.provider;

import com.cnblogs.sdk.data.api.IUserDataApi;
import com.cnblogs.sdk.data.impl.UserDataApiImpl;
import com.cnblogs.sdk.exception.CnblogsSdkException;

/**
 * 博客园数据提供者
 * 融合官网接口、网页接口、本地数据库保存于一体的数据提供者，基本场景下你只需要知道这一个接口即可
 * 如果这个接口还是无法满足需求则调用其他Provider进行业务处理
 * @author RAE
 * @date 2021/02/20
 */
public class CnblogsDataProvider extends BaseApiProvider {

    public CnblogsDataProvider() {
    }

    @SuppressWarnings("unchecked")
    protected <T> T createDataApi(Class<T> cls) {
        String key = cls.getName();
        Object api = mLruCache.get(key);
        if (api == null) {
            try {
                T t = cls.newInstance();
                mLruCache.put(key, t);
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
    public final IUserDataApi getUserDataApi() {
        return createDataApi(UserDataApiImpl.class);
    }

}
