package com.cnblogs.api;

import com.cnblogs.api.http.HtmlParser;
import com.cnblogs.api.model.ArticleBean;
import com.cnblogs.api.model.BlogBean;
import com.cnblogs.api.model.BlogPostInfoBean;
import com.cnblogs.api.model.CategoryBean;
import com.cnblogs.api.param.BlogListParam;
import com.cnblogs.api.param.BlogRecommendParam;
import com.cnblogs.api.parser.HtmlStringParser;
import com.cnblogs.api.parser.blog.BlogDetailParser;
import com.cnblogs.api.parser.blog.BlogPostInfoParser;
import com.cnblogs.api.parser.blog.HomeBlogParser;
import com.cnblogs.api.parser.blog.NextArticleParser;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.adapter.rxjava2.AndroidObservable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * 博客接口
 * Created by rae on 2019-10-19.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface IBlogApi {

    /**
     * 获取技术分类
     */
    @GET("https://cnblogs.raeblog.com/category")
    @JsonParser
    AndroidObservable<List<CategoryBean>> getHomeCategory();


    /**
     * 获取首页博客列表
     */
    @POST("https://www.cnblogs.com/AggSite/AggSitePostList")
    @HtmlParser(HomeBlogParser.class)
    AndroidObservable<List<BlogBean>> getHomeBlogs(@Body BlogListParam param);


    /**
     * 根据原文链接获取博客实体
     *
     * @param url 原文链接
     */
    @GET
    @HtmlParser(BlogDetailParser.class)
    AndroidObservable<BlogBean> getBlogDetail(@Url String url);

    /**
     * 获取博客阅读数量
     *
     * @param blogApp 博客APP
     * @param postId  博客PostId
     */
    @GET("https://www.cnblogs.com/{blogApp}/ajax/GetViewCount.aspx")
    @HtmlParser(HtmlStringParser.class)
    AndroidObservable<String> getBlogViewCount(@Path("blogApp") String blogApp, @Query("postId") String postId);

    /**
     * 获取博客评论数量
     *
     * @param blogApp 博客APP
     * @param postId  博客PostId
     */
    @GET("https://www.cnblogs.com/{blogApp}/ajax/GetCommentCount.aspx")
    @HtmlParser(HtmlStringParser.class)
    AndroidObservable<String> getBlogCommentCount(@Path("blogApp") String blogApp, @Query("postId") String postId);

    /**
     * 获取博客推荐数量
     *
     * @param blogApp 博客APP
     * @param blogId  博客Id
     * @param postId  博客PostId
     * @param userId  用户的GUID
     */
    @GET("https://www.cnblogs.com/{blogApp}/ajax/BlogPostInfo.aspx")
    @HtmlParser(BlogPostInfoParser.class)
    AndroidObservable<BlogPostInfoBean> getBlogPostInfo(@Path("blogApp") String blogApp,
                                                        @Query("blogId") String blogId,
                                                        @Query("postId") String postId,
                                                        @Query("blogUserGuid") String userId);


    /**
     * 获取当前文章的上一篇文章
     *
     * @param blogApp 博客APP
     * @param postId  文章Id
     */
    @GET("https://www.cnblogs.com/{blogApp}/ajax/post/prevnext")
    @HtmlParser(NextArticleParser.class)
    AndroidObservable<List<ArticleBean>> getNextArticle(@Path("blogApp") String blogApp,
                                                        @Query("postId") String postId);


    /**
     * 根据当前文章推荐博客
     */
    @POST("https://recomm.cnblogs.com/api/v2/recomm/blogpost/reco")
    @JsonParser
    AndroidObservable<List<ArticleBean>> getRecommendBlogs(@Body BlogRecommendParam param);

}
