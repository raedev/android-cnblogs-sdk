package com.cnblogs.api;

import com.cnblogs.api.http.CHeaders;
import com.cnblogs.api.http.HtmlParser;
import com.cnblogs.api.model.MomentCommentBean;
import com.cnblogs.api.parser.moment.MomentReplyParser;

import java.util.List;

import retrofit2.adapter.rxjava2.AndroidObservable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * 闪存动态接口
 * Created by rae on 2020-02-13.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface IMomentApi {

    /**
     * 获取回复我的闪存
     *
     * @param type      回复我的：comment； 提到我的：mention；
     * @param page      页码
     * @param timestamp 传当前的时间戳
     */
    @GET("https://ing.cnblogs.com/ajax/ing/GetIngList")
    @Headers(CHeaders.XHR)
    @HtmlParser(MomentReplyParser.class)
    AndroidObservable<List<MomentCommentBean>> getReplyMeMoments(@Query("IngListType") String type,
                                                                 @Query("PageIndex") int page,
                                                                 @Query("pageSize") int pageSize,
                                                                 @Query("_") long timestamp);

}
