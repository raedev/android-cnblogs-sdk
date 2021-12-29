package com.cnblogs.sdk.data.impl;

import com.cnblogs.sdk.CnblogsSdk;
import com.cnblogs.sdk.api.gateway.IFeedApi;
import com.cnblogs.sdk.data.api.IFeedDataApi;
import com.cnblogs.sdk.exception.CnblogsSdkIOException;
import com.cnblogs.sdk.model.FeedInfo;
import com.cnblogs.sdk.provider.CnblogsGatewayApiProvider;
import com.cnblogs.sdk.provider.ICnblogsSdkConfig;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

/**
 * 信息流数据接口
 * @author RAE
 * @date 2021/07/24
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class FeedDataApiImpl implements IFeedDataApi {

    private final IFeedApi mFeedApi;
    private final ICnblogsSdkConfig mConfig;

    public FeedDataApiImpl() {
        CnblogsGatewayApiProvider provider = CnblogsSdk.getInstance().getGatewayApiProvider();
        mFeedApi = provider.getFeedApi();
        mConfig = CnblogsSdk.getConfig();
    }

    @Override
    public Observable<FeedInfo> getLauncherFeed() {
        // 加载逻辑：先从本地获取，后从接口获取缓存到本地
        return Observable.create((ObservableOnSubscribe<FeedInfo>) emitter -> {
            FeedInfo feed = mConfig.getLauncherFeed();
            if (feed != null) {
                emitter.onNext(feed);
            }
            // 请求网络
            FeedInfo apiFeed = mFeedApi.getLauncherFeed().blockingFirst();
            if (feed == null || !feed.id.equals(apiFeed.id)) {
                emitter.onNext(apiFeed);
            }
            // 保存本地
            mConfig.setLauncherFeed(apiFeed);

            emitter.onComplete();
        }).doOnError(error -> {
            if (error instanceof RuntimeException && error.getCause() instanceof CnblogsSdkIOException) {
                int errorCode = ((CnblogsSdkIOException) error.getCause()).getErrorCode();
                if (errorCode == CnblogsSdkIOException.ERROR_NONE_DATA) {
                    // 清除本地数据
                    mConfig.setLauncherFeed(null);
                }
            }
        });
    }
}
