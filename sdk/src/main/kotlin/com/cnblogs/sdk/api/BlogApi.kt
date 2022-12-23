package com.cnblogs.sdk.api

import com.cnblogs.sdk.internal.annotation.HTML
import com.cnblogs.sdk.internal.http.HttpHeader
import com.cnblogs.sdk.internal.parser.blog.BlogListParser
import com.cnblogs.sdk.model.ArticleInfo
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * 博客接口
 * @author RAE
 * @date 2022/10/04
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
internal interface BlogApi {

    @POST("https://www.cnblogs.com/AggSite/AggSitePostList")
    @Headers(HttpHeader.CONTENT_TYPE_JSON)
    @HTML(BlogListParser::class)
    fun getHomeBlogs(): Observable<List<ArticleInfo>>
}