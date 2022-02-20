package com.cnblogs.sdk.api.web;

import com.cnblogs.sdk.api.param.BlogListParam;
import com.cnblogs.sdk.internal.http.HttpHeader;
import com.cnblogs.sdk.internal.http.annotation.HTML;
import com.cnblogs.sdk.internal.parser.blog.BlogContentParser;
import com.cnblogs.sdk.internal.parser.blog.BlogListParser;
import com.cnblogs.sdk.model.BlogInfo;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * 博客园博客接口
 * @author RAE
 * @date 2022/02/14
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface IBlogApi {

    /**
     * 获取分类博客列表
     * @param param 查询参数
     * @return Observable
     */
    @POST("https://www.cnblogs.com/AggSite/AggSitePostList")
    @Headers(HttpHeader.CONTENT_TYPE_JSON)
    @HTML(BlogListParser.class)
    Observable<List<BlogInfo>> blogList(@Body BlogListParam param);


    /**
     * 获取博文内容
     * @param url 博客原文路径
     * @return Observable
     */
    @GET
    @HTML(BlogContentParser.class)
    Observable<String> content(@Url String url);
}
