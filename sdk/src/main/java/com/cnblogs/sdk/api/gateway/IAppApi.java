package com.cnblogs.sdk.api.gateway;

import com.cnblogs.sdk.model.AppConfig;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

/**
 * App接口
 * @author RAE
 * @date 2022/03/25
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface IAppApi {

    /**
     * 获取APP配置信息
     * @return 配置
     */
    @GET("/app/config")
    Observable<AppConfig> getAppConfig();
}
