package com.cnblogs.sdk.model;

import com.google.gson.annotations.SerializedName;

/**
 * AppKey 实体
 * @author RAE
 * @date 2021/07/24
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class TokenInfo {

    @SerializedName("access_token")
    private String token;

    public TokenInfo() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
