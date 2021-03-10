package com.cnblogs.sdk.data.impl;

import com.cnblogs.sdk.data.api.BlogDataApi;
import com.cnblogs.sdk.data.api.CategoryDataApi;
import com.cnblogs.sdk.data.api.UserDataApi;
import com.cnblogs.sdk.internal.ApiCreator;

/**
 * 数据接口提供者
 *
 * @author RAE
 * @date 2021/02/25
 */
public abstract class DataApiProvider {

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
    public BlogDataApi getBlogDataApi() {
        return ApiCreator.create(BlogDataImpl.class);
    }
}
