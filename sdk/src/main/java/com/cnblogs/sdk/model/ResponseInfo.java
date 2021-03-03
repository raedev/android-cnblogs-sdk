package com.cnblogs.sdk.model;

import com.google.gson.annotations.SerializedName;

/**
 * 公共响应信息
 * @author RAE
 * @date 2021/02/25
 */
public class ResponseInfo {

    @SerializedName("IsSucceed")
    private boolean isSuccessful;

    private String message;

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
