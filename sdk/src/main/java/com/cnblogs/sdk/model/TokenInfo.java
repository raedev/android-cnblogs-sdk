package com.cnblogs.sdk.model;

import com.google.gson.annotations.SerializedName;

/**
 * AppKey 实体
 * @author RAE
 * @date 2021/07/24
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class TokenInfo {

    /**
     * 网关访问Token
     */
    @SerializedName("access_token")
    public String token;

    /**
     * 博客园接口访问Token: https://api.cnblogs.com
     */
    @SerializedName("cnblogs_api_token")
    public String apiToken;

    @Override
    public String toString() {
        return "AppKeyInfo{" +
                "token='" + token + '\'' +
                ", apiToken='" + apiToken + '\'' +
                '}';
    }
}
