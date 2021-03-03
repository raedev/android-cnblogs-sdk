package com.cnblogs.sdk.data.impl;

import com.cnblogs.sdk.data.api.IBlogDataApi;
import com.cnblogs.sdk.model.ArticleInfo;
import com.cnblogs.sdk.model.CategoryInfo;
import com.cnblogs.sdk.param.BlogParam;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * 博客数据接口实现
 * @author RAE
 * @date 2021/03/01
 */
class BlogDataApiImpl extends ArticleDataApi implements IBlogDataApi {

    @Override
    public Observable<List<ArticleInfo>> queryBlogs(CategoryInfo categoryInfo, int page) {
        return mBlogApi.getBlogs(new BlogParam(categoryInfo, page)).flatMap(data -> cacheToLocal(categoryInfo, data));
    }

}
