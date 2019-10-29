package com.cnblogs.api;

import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Before;

import io.reactivex.Observable;
import io.reactivex.internal.observers.BlockingBaseObserver;

/**
 * Created by rae on 2019-10-21.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@SuppressWarnings("WeakerAccess")
public class CnblogsApiTest {

    CnblogsOpenApi mApi;

    public CnblogsApiTest() {
        mApi = CnblogsOpenApi.getInstance();
    }

    <T> void run(Observable<T> observable) {

        observable.blockingSubscribe(new BlockingBaseObserver<T>() {

            @Override
            public void onError(Throwable e) {
                CLog.e("请求异常！", e);
                Assert.fail(e.getMessage());
            }

            @Override
            public void onNext(T t) {
                String json = new Gson().toJson(t);
                CLog.d("请求成功，对象信息：");
                CLog.d(json);
            }
        });
    }

    @Before
    public void setup() {
    }
}
