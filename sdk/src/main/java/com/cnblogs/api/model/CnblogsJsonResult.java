package com.cnblogs.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rae on 2020-01-08.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class CnblogsJsonResult {

    @SerializedName(value = "isSuccess", alternate = "success")
    private boolean success;

    @SerializedName(value = "message", alternate = {"msg", "Message", "responseText"})
    private String message;

    public boolean success() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
