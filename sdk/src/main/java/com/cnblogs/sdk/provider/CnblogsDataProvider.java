package com.cnblogs.sdk.provider;

import com.cnblogs.sdk.data.api.IBlogDataApi;
import com.cnblogs.sdk.data.api.CategoryDataApi;
import com.cnblogs.sdk.data.api.IAppTokenDataApi;
import com.cnblogs.sdk.data.api.IFeedDataApi;
import com.cnblogs.sdk.data.api.UserDataApi;
import com.cnblogs.sdk.data.impl.AppTokenImpl;
import com.cnblogs.sdk.data.impl.BlogDataImpl;
import com.cnblogs.sdk.data.impl.CategoryDataImpl;
import com.cnblogs.sdk.data.impl.FeedDataApiImpl;
import com.cnblogs.sdk.data.impl.UserDataImpl;
import com.cnblogs.sdk.internal.ApiCreator;

/**
 * 博客园数据提供者
 * 融合官网接口、网页接口、本地数据库保存于一体的数据提供者，基本场景下你只需要知道这一个接口即可
 * 如果这个接口还是无法满足需求则调用其他Provider进行业务处理
 * @author RAE
 * @date 2021/02/20
 */
public final class CnblogsDataProvider {

    /**
     * 获取AppToken数据接口
     */
    public IAppTokenDataApi getAppTokenDataApi() {
        return ApiCreator.create(AppTokenImpl.class);
    }

    /**
     * 信息流接口
     */
    public IFeedDataApi getFeedDataApi() {
        return ApiCreator.create(FeedDataApiImpl.class);
    }

    /**
     * 获取用户数据接口
     */
    public UserDataApi getUserDataApi() {
        return ApiCreator.create(UserDataImpl.class);
    }

    /**
     * 获取分类数据接口
     */
    public CategoryDataApi getCategoryDataApi() {
        return ApiCreator.create(CategoryDataImpl.class);
    }

    /**
     * 获取博客数据接口
     */
    public IBlogDataApi getBlogDataApi() {
        return ApiCreator.create(BlogDataImpl.class);
    }
}
