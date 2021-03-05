package com.cnblogs.sdk.http.interceptor;

import com.cnblogs.sdk.R;
import com.cnblogs.sdk.api.ApiConstant;
import com.cnblogs.sdk.exception.CnblogsSdkIOException;
import com.cnblogs.sdk.exception.CnblogsTokenException;
import com.cnblogs.sdk.internal.CnblogsLogger;
import com.github.raedev.swift.resource.AppResources;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 响应拦截器
 *
 * @author RAE
 * @date 2021/02/15
 */
public class CnblogsResponseInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        int httpCode = response.code();
        String contentType = response.header("content-type");
        if (contentType != null && contentType.contains(ApiConstant.CONTENT_TYPE_JSON)) {
            interceptJsonResponse(chain.request(), response);
        }

        if (!response.isSuccessful()) {
            throw new CnblogsSdkIOException(AppResources.getString(R.string.sdk_format_response_error, httpCode));
        }
        if (httpCode == 204) {
            throw new CnblogsTokenException(AppResources.getString(R.string.sdk_token_expired));
        }
        return response;
    }

    private void interceptJsonResponse(Request request, Response response) throws IOException {
        String body = copyBufferBody(response.body());
        try {
            Object obj = new JSONTokener(body).nextValue();
            String message = "";
            if (obj instanceof JSONObject) {
                JSONObject jsonObj = (JSONObject) obj;
                if (jsonObj.has("error")) {
                    message = jsonObj.getString("error");
                }
            }
            // 请求没有返回200的情况
            if (!response.isSuccessful()) {
                throw new CnblogsSdkIOException(message);
            }
        } catch (JSONException e) {
            CnblogsLogger.e("解析地址：" + request.url() + "流失败：" + e.getMessage(), e);
        }
    }

    /**
     * 复制响应流
     */
    public String copyBufferBody(ResponseBody body) {
        if (body == null) {
            return null;
        }
        BufferedSource source = body.source();
        try {
            // Buffer the entire body.
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            Charset charset = StandardCharsets.UTF_8;
            return buffer.clone().readString(charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
