package com.cnblogs.sdk.http;

import com.cnblogs.sdk.api.ApiConstant;
import com.cnblogs.sdk.exception.CnblogsSdkIOException;
import com.cnblogs.sdk.exception.CnblogsTokenException;
import com.cnblogs.sdk.internal.CnblogsLogger;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 响应拦截器
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
        } else {
            interceptHtmlResponse(chain.request(), response);
        }
        if (!response.isSuccessful()) {
            throw new CnblogsSdkIOException("接口发生异常，Code：" + httpCode);
        }
        if (httpCode == 204) {
            throw new CnblogsTokenException("登录过期，请重新登录");
        }
        return response;
    }

    private void interceptHtmlResponse(Request request, Response response) {

    }

    private void interceptJsonResponse(Request request, Response response) throws IOException {
        String body = HttpUtils.copyBufferBody(response.body());
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
}
