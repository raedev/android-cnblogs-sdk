package com.cnblogs.sdk.internal.data;

import android.content.Context;

import androidx.annotation.NonNull;

import com.cnblogs.sdk.api.data.IBlogDataApi;
import com.cnblogs.sdk.api.param.BlogListParam;
import com.cnblogs.sdk.api.web.IBlogApi;
import com.cnblogs.sdk.model.BlogInfo;
import com.cnblogs.sdk.model.Category;
import com.cnblogs.sdk.provider.WebApiProvider;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * 博客接口实现
 * @author RAE
 * @date 2022/03/13
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class BlogDataApiImpl extends BaseDataApi implements IBlogDataApi {

    private final IBlogApi mBlogApi;

    public BlogDataApiImpl(Context context, WebApiProvider webApiProvider) {
        super(context, webApiProvider);
        mBlogApi = webApiProvider.getBlogApi();
    }

    @Override
    public Observable<List<BlogInfo>> getBlogs(@NonNull Category category, int page) {
        BlogListParam param = new BlogListParam(category.getId(), page);
        Observable<List<BlogInfo>> observable = mBlogApi.blogList(param);
        return observable.map(data -> {
            for (BlogInfo item : data) {
                // 本地内容满足条件：本地缓存存在，博客更新日期相同

                Observable<String> content = mBlogApi.content(item.getUrl());
            }
            return data;
        });
    }


}
