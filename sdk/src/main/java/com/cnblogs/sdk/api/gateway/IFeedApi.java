package com.cnblogs.sdk.api.gateway;

import com.cnblogs.sdk.model.FeedInfo;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 信息流接口
 * @author RAE
 * @date 2021/07/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface IFeedApi {

    /**
     * 首页
     */
    String TYPE_HOME = "home";

    /**
     * 动态
     */
    String TYPE_MOMENT = "moment";

    /**
     * 发现
     */
    String TYPE_DISCOVER = "discover";

    /**
     * 启动页信息流
     * @return 观察者
     */
    @GET("feed/launcher")
    Observable<FeedInfo> getLauncherFeed();

    /**
     * 获取信息流内容
     * @param type 信息流类型，参考接口：TYPE_*
     * @return 观察者
     */
    @GET("feed")
    Observable<FeedInfo> getFeeds(@Query("type") String type);
}
