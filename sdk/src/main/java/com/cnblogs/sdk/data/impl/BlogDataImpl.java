package com.cnblogs.sdk.data.impl;

import com.cnblogs.sdk.data.api.BlogDataApi;
import com.cnblogs.sdk.model.ArticleInfo;
import com.cnblogs.sdk.model.CategoryInfo;
import com.cnblogs.sdk.http.body.BlogRequestBody;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * 博客数据接口实现
 * @author RAE
 * @date 2021/03/01
 */
class BlogDataImpl extends ArticleDataImpl implements BlogDataApi {

    @Override
    public Observable<List<ArticleInfo>> queryBlogs(CategoryInfo categoryInfo, int page) {
        return mBlogApi.getBlogs(new BlogRequestBody(categoryInfo, page)).flatMap(data -> cacheToLocal(categoryInfo, data));
    }

}
