package com.cnblogs.sdk.api.web;

import com.cnblogs.sdk.internal.http.annotation.HTML;
import com.cnblogs.sdk.internal.parser.search.BlogSearchParser;
import com.cnblogs.sdk.internal.parser.search.KbSearchParser;
import com.cnblogs.sdk.internal.parser.search.NewsSearchParser;
import com.cnblogs.sdk.internal.parser.search.QuestionSearchParser;
import com.cnblogs.sdk.internal.parser.search.UserSearchParser;
import com.cnblogs.sdk.model.BlogInfo;
import com.cnblogs.sdk.model.KbInfo;
import com.cnblogs.sdk.model.NewsInfo;
import com.cnblogs.sdk.model.UserInfo;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 搜索接口
 * <p>
 * 如需要搜索某个作者的博客，搜索词使用blog链接，如（blog:rae java）
 * </p>
 * @author RAE
 * @date 2022/02/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface ISearchApi {

    /**
     * 搜索博客
     * @param keyword 搜索词
     * @param page 页码
     * @return Observable
     */
    @GET("https://zzk.cnblogs.com/s/blogpost")
    @HTML(BlogSearchParser.class)
    Observable<List<BlogInfo>> searchBlogs(@Query("Keywords") String keyword, @Query("pageindex") int page);

    /**
     * 搜索我的博客
     * @param keyword 搜索词
     * @param page 页码
     * @return Observable
     */
    @GET("https://zzk.cnblogs.com/my/s/blogpost-p")
    @HTML(BlogSearchParser.class)
    Observable<List<BlogInfo>> searchMyBlogs(@Query("Keywords") String keyword, @Query("pageindex") int page);

    /**
     * 搜索新闻
     * @param keyword 搜索词
     * @param page 页码
     * @return Observable
     */
    @GET("https://zzk.cnblogs.com/s/news")
    @HTML(NewsSearchParser.class)
    Observable<List<NewsInfo>> searchNews(@Query("Keywords") String keyword, @Query("pageindex") int page);

    /**
     * 搜索博问
     * @param keyword 搜索词
     * @param page 页码
     * @return Observable
     */
    @GET("https://zzk.cnblogs.com/s/question")
    @HTML(QuestionSearchParser.class)
    Observable<List<NewsInfo>> searchQuestions(@Query("Keywords") String keyword, @Query("pageindex") int page);

    /**
     * 搜索知识库
     * @param keyword 搜索词
     * @param page 页码
     * @return Observable
     */
    @GET("https://zzk.cnblogs.com/s/kb")
    @HTML(KbSearchParser.class)
    Observable<List<KbInfo>> searchKbs(@Query("Keywords") String keyword, @Query("pageindex") int page);

    /**
     * 搜索用户
     * @param keyword 搜索词
     * @param page 页码
     * @return Observable
     */
    @GET("https://home.cnblogs.com/user/search.aspx")
    @HTML(UserSearchParser.class)
    Observable<List<UserInfo>> searchUsers(@Query("key") String keyword, @Query("page") int page);

}
