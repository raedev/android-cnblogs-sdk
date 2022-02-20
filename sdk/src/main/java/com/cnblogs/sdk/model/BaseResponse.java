package com.cnblogs.sdk.model;

import com.google.gson.annotations.SerializedName;

/**
 * 公共返回实体
 * @author RAE
 * @date 2022/02/19
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class BaseResponse {

    @SerializedName(value = "Success", alternate = {"success", "IsSucceed"})
    private Boolean successful;

    @SerializedName(value = "Message", alternate = {"message"})
    private String message;

    @SerializedName("ErrorCode")
    private int code = 500;

    public Boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
