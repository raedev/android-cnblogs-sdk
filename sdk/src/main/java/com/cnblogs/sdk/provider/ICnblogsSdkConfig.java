package com.cnblogs.sdk.provider;

import androidx.annotation.Nullable;

import com.cnblogs.sdk.model.FeedInfo;
import com.github.raedev.swift.annotation.Configuration;

/**
 * 接口配置
 * @author RAE
 * @date 2021/07/24
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@Configuration(value = "CnblogsSdkConfig", changed = true)
public interface ICnblogsSdkConfig {

    // region Token 相关配置

    /**
     * 设置Token
     * @param token 访问凭证
     */
    void setToken(String token);

    /**
     * 获取Token
     * @return 凭证
     */
    String getToken();

    /**
     * 移除Token
     */
    void removeToken();

    /**
     * 设置博客园接口Token
     * @param token 值
     */
    void setApiToken(String token);

    /**
     * 获取博客园接口Token
     * @return Token
     */
    String getApiToken();


    // endregion
    // region 信息流配置

    /**
     * 保存启动页信息流
     * @param value 实体
     */
    void setLauncherFeed(FeedInfo value);

    /**
     * 获取启动页信息流
     * @return 实体
     */
    @Nullable
    FeedInfo getLauncherFeed();

    // endregion
}
