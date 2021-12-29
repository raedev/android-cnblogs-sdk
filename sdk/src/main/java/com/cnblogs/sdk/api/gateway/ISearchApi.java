package com.cnblogs.sdk.api.gateway;

import com.cnblogs.sdk.model.EmptyInfo;
import com.cnblogs.sdk.model.SearchInfo;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 信息流接口
 * @author RAE
 * @date 2021/07/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface ISearchApi {

    /**
     * 搜索建议，根据用户喜好推荐搜索
     * @param word 搜索词
     * @return 观察者
     */
    @GET("search/hot")
    Observable<SearchInfo> getRecommend(@Query("word") String word);

    /**
     * 获取热门搜索
     * @return 观察者
     */
    @GET("search/hot")
    Observable<SearchInfo> getHot();

    /**
     * 提交搜索，网关会进行搜索统计，搜索结果的数据来源
     * @param word 搜索词
     * @return 观察者
     */
    @POST("search/history")
    @FormUrlEncoded
    Observable<EmptyInfo> post(@Field("word") String word);
}
