package com.cnblogs.sdk.internal.http.interceptor;

import com.cnblogs.sdk.internal.http.HttpHeader;
import com.cnblogs.sdk.internal.http.body.JsonRequestBody;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 请求拦截器
 * @author RAE
 * @date 2021/12/29
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class CnblogsRequestInterceptor implements Interceptor {

    private CnblogsRequestInterceptor() {
    }

    public static CnblogsRequestInterceptor create() {
        return new CnblogsRequestInterceptor();
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder()
                .addHeader("user-agent", HttpHeader.USER_AGENT);
        // 请求参数处理
        RequestBody requestBody = request.body();
        if (request.header("param") != null && requestBody instanceof FormBody) {
            builder.removeHeader("param");
            builder.method(request.method(), convertJsonRequestBody((FormBody) requestBody));
        }

        return chain.proceed(builder.build());
    }

    private RequestBody convertJsonRequestBody(FormBody body) {
        Map<String, String> map = new HashMap<>(body.size());
        for (int i = 0; i < body.size(); i++) {
            map.put(body.name(i), body.value(i));
        }
        return JsonRequestBody.create(map);
    }
}
