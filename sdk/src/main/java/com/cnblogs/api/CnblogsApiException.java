package com.cnblogs.api;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * 博客园接口错误
 * Created by ChenRui on 2017/6/11 0011 0:36.
 */
public class CnblogsApiException extends IOException {

    public static final int ERROR_UNKNOWN = 0;
    public static final int ERROR_HTTP = 1;
    public static final int ERROR_NETWORK = 2;
    public static final int ERROR_CONNECT = 3;
    public static final int ERROR_LOGIN_EXPIRED = 4;
    public static final int ERROR_CONTENT_PARSER = 5;

    private int code;

    // Http 请求状态代码
    private int statusCode;

    public static CnblogsApiException valueOf(Throwable e) {
        if (e instanceof CnblogsApiException) return (CnblogsApiException) e;
        return new CnblogsApiException(e);
    }

    public CnblogsApiException() {
    }

    public CnblogsApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CnblogsApiException(String message) {
        super(message);
    }

    public CnblogsApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public CnblogsApiException(Throwable cause) {
        super(cause);
        String message = cause.getMessage();
        if (message.contains("登录过期") || message.contains("Authorization")) {
            this.code = ERROR_LOGIN_EXPIRED;
        } else if (cause instanceof ConnectException) {
            this.code = ERROR_CONNECT;
        } else if (cause instanceof UnknownHostException) {
            this.code = ERROR_NETWORK;
        }
        // HTTP 状态错误
        else if (cause instanceof HttpException) {
            int code = ((HttpException) cause).code();
            this.code = ERROR_HTTP;
            if (code == 401) {
                this.code = ERROR_LOGIN_EXPIRED;
            }
            this.statusCode = code;
        } else {
            this.code = ERROR_UNKNOWN;
        }
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        int code = getCode();
        switch (code) {
            case ERROR_HTTP:
                return String.format("[请求错误] 错误码：%s", statusCode);
            case ERROR_CONNECT:
                return "[网络错误] 无法连接到服务器";
            case ERROR_LOGIN_EXPIRED:
                return "[登录失效] 请重新登录";
            case ERROR_NETWORK:
                return "[网络错误] 请检查网络连接";
            case ERROR_CONTENT_PARSER:
                return "[数据解析错误] 返回数据：" + message;
            case ERROR_UNKNOWN:
            default:
                return message;
        }
    }
}
