package com.cnblogs.sdk.model;

import com.google.gson.annotations.SerializedName;

/**
 * 公共响应信息
 * @author RAE
 * @date 2021/02/25
 */
public class ResponseInfo {

    public static  final  ResponseInfo S_SUCCESS_INFO = new ResponseInfo();

    public static ResponseInfo success(){
        S_SUCCESS_INFO.setSuccessful(true);
        return S_SUCCESS_INFO;
    }

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
