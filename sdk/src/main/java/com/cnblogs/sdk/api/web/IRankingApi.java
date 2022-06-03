package com.cnblogs.sdk.api.web;

import com.cnblogs.sdk.internal.http.annotation.HTML;
import com.cnblogs.sdk.internal.parser.ranking.LiteBlogRankingParser;
import com.cnblogs.sdk.internal.parser.ranking.LiteNewsRankingParser;
import com.cnblogs.sdk.model.RankingInfo;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

/**
 * 排行榜接口
 * @author RAE
 * @date 2022/02/14
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface IRankingApi {

    /**
     * 获取首页的博客排行，其中包含：48小时阅读排行、10天推荐排行、48小时评论排行、编辑推荐
     * @return Observable
     */
    @GET("https://www.cnblogs.com/aggsite/SideRight")
    @HTML(LiteBlogRankingParser.class)
    Observable<List<RankingInfo>> liteBlogRanking();

    /**
     * 获取首页的新闻排行，其中包含：推荐新闻、热门新闻
     * @return Observable
     */
    @GET("https://www.cnblogs.com/aggsite/SideRight")
    @HTML(LiteNewsRankingParser.class)
    Observable<List<RankingInfo>> liteNewsRanking();

}
