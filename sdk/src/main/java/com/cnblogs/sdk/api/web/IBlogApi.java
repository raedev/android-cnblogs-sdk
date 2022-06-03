package com.cnblogs.sdk.api.web;

import com.cnblogs.sdk.api.param.BlogListParam;
import com.cnblogs.sdk.internal.http.HttpHeader;
import com.cnblogs.sdk.internal.http.annotation.HTML;
import com.cnblogs.sdk.internal.http.annotation.JSON;
import com.cnblogs.sdk.internal.http.body.JsonRequestBody;
import com.cnblogs.sdk.internal.parser.blog.AuthorTopListParser;
import com.cnblogs.sdk.internal.parser.blog.BlogContentParser;
import com.cnblogs.sdk.internal.parser.blog.BlogListParser;
import com.cnblogs.sdk.internal.parser.blog.CommentListParser;
import com.cnblogs.sdk.internal.parser.blog.NextArticleParser;
import com.cnblogs.sdk.model.ArticleInfo;
import com.cnblogs.sdk.model.BlogInfo;
import com.cnblogs.sdk.model.CommentInfo;
import com.cnblogs.sdk.model.PostStatInfo;
import com.cnblogs.sdk.model.RecommendInfo;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * 博客园博客接口
 * @author RAE
 * @date 2022/02/14
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface IBlogApi {

    // region 文章相关

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


    /**
     * 获取当前文章的上下篇文章
     * @param blogApp 博客APP
     * @param postId 文章Id
     * @return 上下篇文章
     */
    @GET("https://www.cnblogs.com/{blogApp}/ajax/post/prevnext")
    @HTML(NextArticleParser.class)
    Observable<List<BlogInfo>> nextArticle(@Path("blogApp") String blogApp,
                                           @Query("postId") String postId);


    /**
     * 根据当前文章推荐博客
     * @param itemId postId
     * @param itemTitle 标题
     * @return 推荐列表
     */
    @JSON(transitive = true)
    @Headers({HttpHeader.ACCEPT_TYPE_JSON, HttpHeader.JSON_PARAM, HttpHeader.XHR})
    @POST("https://recomm.cnblogs.com/api/v2/recomm/blogpost/reco")
    @FormUrlEncoded
    Observable<List<RecommendInfo>> recommendBlogs(@Field("itemId") String itemId, @Field("itemTitle") String itemTitle);


    /**
     * 文章的顶、踩、评论、阅读数量
     * @param blogApp blogApp
     * @param body [postId]
     * @return 推荐列表
     */
    @JSON(transitive = true)
    @Headers({HttpHeader.ACCEPT_TYPE_JSON, HttpHeader.JSON_PARAM, HttpHeader.XHR})
    @POST("https://www.cnblogs.com/{blogApp}/ajax/GetPostStat")
    Observable<List<PostStatInfo>> postStat(@Path("blogApp") String blogApp, @Body JsonRequestBody body);


    /**
     * 作者的博客排行榜
     * @param blogApp blogApp
     * @return 排行榜列表
     */
    @Headers({HttpHeader.ACCEPT_TYPE_JSON, HttpHeader.JSON_PARAM, HttpHeader.XHR})
    @GET("https://www.cnblogs.com/{blogApp}/ajax/TopLists.aspx")
    @HTML(AuthorTopListParser.class)
    Observable<List<ArticleInfo>> authorTopList(@Path("blogApp") String blogApp);

    // endregion

    // region 评论相关


    /**
     * 文章评论列表
     * @param blogApp blogApp
     * @param postId 文章id
     * @param page 页码
     * @return 排行榜列表
     */
    @Headers({HttpHeader.ACCEPT_TYPE_JSON, HttpHeader.JSON_PARAM, HttpHeader.XHR})
    @GET("https://www.cnblogs.com/{blogApp}/ajax/GetComments.aspx")
    @HTML(CommentListParser.class)
    Observable<List<CommentInfo>> commentList(@Path("blogApp") String blogApp,
                                              @Query("postId") String postId,
                                              @Query("pageIndex") int page);

    // endregion

}
