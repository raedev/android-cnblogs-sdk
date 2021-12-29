package com.cnblogs.sdk.data.api;

import com.cnblogs.sdk.model.FeedInfo;

import io.reactivex.rxjava3.core.Observable;

/**
 * 信息流接口
 * @author RAE
 * @date 2021/07/24
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface IFeedDataApi {

    /**
     * 启动页信息流
     * @return 观察者
     */
    Observable<FeedInfo> getLauncherFeed();

}
