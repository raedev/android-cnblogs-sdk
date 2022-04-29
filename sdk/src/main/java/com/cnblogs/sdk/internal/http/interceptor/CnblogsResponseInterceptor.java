package com.cnblogs.sdk.internal.http.interceptor;

import android.text.TextUtils;
import android.util.JsonReader;
import android.util.JsonToken;

import com.cnblogs.sdk.exception.CnblogsHttpException;
import com.cnblogs.sdk.internal.utils.CnblogsUtils;
import com.cnblogs.sdk.internal.utils.Logger;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求拦截器
 * @author RAE
 * @date 2021/12/29
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class CnblogsResponseInterceptor implements Interceptor {

    private CnblogsResponseInterceptor() {
    }

    public static CnblogsResponseInterceptor create() {
        return new CnblogsResponseInterceptor();
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String body = CnblogsUtils.copyBufferBody(response.body());
        if (!response.isSuccessful()) {
            handleHttpException(request, response, body);
        }
        return response;
    }

    /**
     * 处理HTTP异常
     * @param request 请求
     * @param response 响应
     * @param body 内容
     */
    private void handleHttpException(Request request, Response response, String body) throws CnblogsHttpException {
        if (TextUtils.isEmpty(body)) {
            throw new CnblogsHttpException(response.code(), body, "服务器没有返回任何错误消息");
        }
        String message = "";
        try {
            JsonReader reader = new JsonReader(new StringReader(body));
            if (reader.peek() != JsonToken.BEGIN_OBJECT) {
                throw new CnblogsHttpException(response.code(), body, message);
            }
            reader.close();
            // 开始解析错误消息
            message = getErrorMessage(new JSONObject(body));
        } catch (IOException | JSONException e) {
            Logger.e("CnblogsResponseInterceptor", "解析错误消息异常", e);
        }
        throw new CnblogsHttpException(response.code(), body, message);
    }

    /**
     * 解析各式各样的错误返回
     * @param obj JSON对象
     * @return 消息
     * @throws JSONException 解析异常
     */
    private String getErrorMessage(JSONObject obj) throws JSONException {
        StringBuilder sb = new StringBuilder();

        // {"errors":["已存在同名文件"],"type":0}
        String key = "errors";
        if (obj.has(key)) {
            JSONArray arr = obj.getJSONArray(key);
            for (int i = 0; i < arr.length(); i++) {
                if (i != 0) {
                    sb.append(";");
                }
                sb.append(arr.getString(i));
            }
            return sb.toString();
        }

        // {"message":"错误消息"}
        key = "message";
        if (obj.has(key)) {
            return obj.getString(key);
        }


        return null;
    }
}
