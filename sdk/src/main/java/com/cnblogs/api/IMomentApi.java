package com.cnblogs.api;

import com.cnblogs.api.http.HtmlParser;
import com.cnblogs.api.http.HttpHeaders;
import com.cnblogs.api.model.MomentBean;
import com.cnblogs.api.model.MomentCommentBean;
import com.cnblogs.api.model.UserInfoBean;
import com.cnblogs.api.parser.moment.AtMeMomentParser;
import com.cnblogs.api.parser.moment.LuckyStarRankingParser;
import com.cnblogs.api.parser.moment.MomentDetailParser;
import com.cnblogs.api.parser.moment.MomentParser;
import com.cnblogs.api.parser.moment.ReplyMomentParser;

import java.util.List;

import retrofit2.adapter.rxjava2.AndroidObservable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 闪存动态接口
 * Created by rae on 2020-02-13.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface IMomentApi {

    /**
     * 全站闪存
     */
    String MOMENT_TYPE_ALL = "All";

    /**
     * 关注的闪存
     */
    String MOMENT_TYPE_FOLLOWING = "following";

    /**
     * 我自己的闪存
     */
    String MOMENT_TYPE_MY = "My";


    /**
     * 闪存
     *
     * @param type      类型，支持：{@link #MOMENT_TYPE_ALL} {@link #MOMENT_TYPE_MY} {@link #MOMENT_TYPE_FOLLOWING}
     * @param page      页码
     * @param pageSize  数据量
     * @param timestamp 传当前的时间戳
     */
    @GET("https://ing.cnblogs.com/ajax/ing/GetIngList")
    @Headers({HttpHeaders.XHR})
    @HtmlParser(MomentParser.class)
    AndroidObservable<List<MomentBean>> getMoments(@Query("IngListType") String type,
                                                   @Query("PageIndex") int page,
                                                   @Query("PageSize") int pageSize,
                                                   @Query("_") long timestamp);

    /**
     * 回复我的闪存
     *
     * @param page      页码
     * @param pageSize  数据量
     * @param timestamp 传当前的时间戳
     */
    @GET("https://ing.cnblogs.com/ajax/ing/GetIngList?IngListType=comment")
    @Headers({HttpHeaders.XHR})
    @HtmlParser(ReplyMomentParser.class)
    AndroidObservable<List<MomentCommentBean>> getReplyMoments(@Query("PageIndex") int page,
                                                               @Query("PageSize") int pageSize,
                                                               @Query("_") long timestamp);

    /**
     * 提到我的闪存
     *
     * @param timestamp 传当前的时间戳
     */
    @GET("https://ing.cnblogs.com/ajax/ing/GetIngList?IngListType=mention")
    @Headers({HttpHeaders.XHR})
    @HtmlParser(AtMeMomentParser.class)
    AndroidObservable<List<MomentCommentBean>> getAtMeMoments(@Query("PageIndex") int page,
                                                              @Query("PageSize") int pageSize,
                                                              @Query("_") long timestamp);

    /**
     * 话题闪存
     */
    @GET("https://ing.cnblogs.com/ajax/ing/GetIngList?IngListType=tag")
    @Headers({HttpHeaders.XHR})
    @HtmlParser(MomentParser.class)
    AndroidObservable<List<MomentBean>> getTopicMoments(@Query("Tag") String tag,
                                                        @Query("PageIndex") int page,
                                                        @Query("PageSize") int pageSize,
                                                        @Query("_") long timestamp);

    /**
     * 闪存详情页
     *
     * @param blogApp BlogAPP
     * @param ingId   闪存ID
     */
    @GET("https://ing.cnblogs.com/u/{blogApp}/status/{ingId}/")
    @HtmlParser(MomentDetailParser.class)
    AndroidObservable<MomentBean> getMomentDetail(@Path("blogApp") String blogApp,
                                                  @Path("ingId") String ingId);

    /**
     * 幸运星排行榜
     *
     * @param timestamp 传当前的时间戳
     */
    @GET("https://ing.cnblogs.com/ajax/ing/SideRight")
    @Headers({HttpHeaders.XHR})
    @HtmlParser(LuckyStarRankingParser.class)
    AndroidObservable<List<UserInfoBean>> getLuckyStarRanking(@Query("_") long timestamp);


}
