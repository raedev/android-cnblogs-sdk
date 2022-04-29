package com.cnblogs.sdk.api.data;

import androidx.annotation.NonNull;

import com.cnblogs.sdk.model.BlogInfo;
import com.cnblogs.sdk.model.Category;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * 博客接口
 * @author RAE
 * @date 2022/03/13
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface IBlogDataApi {

    /**
     * 加载博客列表
     * @param category 所属分类
     * @param page 页码
     * @return 博客列表
     */
    Observable<List<BlogInfo>> getBlogs(@NonNull Category category, int page);
}
