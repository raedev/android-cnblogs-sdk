package com.cnblogs.api;

import com.cnblogs.api.http.HtmlParser;
import com.cnblogs.api.model.BlogBean;
import com.cnblogs.api.model.CategoryBean;
import com.cnblogs.api.param.BlogListParam;
import com.cnblogs.api.parser.blog.HomeBlogParser;

import java.util.List;

import retrofit2.adapter.rxjava2.AndroidObservable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 博客接口
 * Created by rae on 2019-10-19.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface IBlogApi {

    /**
     * 获取首页博客列表
     */
    @POST("https://www.cnblogs.com/AggSite/AggSitePostList")
    @HtmlParser(HomeBlogParser.class)
    AndroidObservable<List<BlogBean>> getHomeBlogs(@Body BlogListParam param);

    /**
     * 获取技术分类
     */
    @GET("https://cnblogs.raeblog.com/category")
    @JsonParser
    AndroidObservable<List<CategoryBean>> getHomeCategory();

}
