package com.cnblogs.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rae on 2020-01-08.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class UploadAvatarResult {
    @SerializedName(value = "IsSuccess", alternate = "success")
    private boolean isSuccess;

    @SerializedName(value = "message", alternate = "Message")
    private String message;


    public boolean success() {
        return !isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
