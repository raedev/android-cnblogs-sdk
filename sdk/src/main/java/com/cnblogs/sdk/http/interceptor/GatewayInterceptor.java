package com.cnblogs.sdk.http.interceptor;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.cnblogs.sdk.CnblogsSdk;
import com.cnblogs.sdk.exception.CnblogsSdkIOException;
import com.cnblogs.sdk.http.BufferUtils;
import com.cnblogs.sdk.internal.CnblogsLogger;
import com.cnblogs.sdk.model.TokenInfo;
import com.cnblogs.sdk.provider.ICnblogsSdkConfig;
import com.cnblogs.sdk.security.CnblogsEncrypt;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网关接口拦截器
 * @author RAE
 * @date 2021/07/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class GatewayInterceptor implements Interceptor {

    private final ICnblogsSdkConfig mConfig;
    private final String mClientId;
    private final String mAppVersion;
    private final String mAppVersionName;

    public static Interceptor create(ICnblogsSdkConfig config) {
        return new GatewayInterceptor(config);
    }

    private GatewayInterceptor(ICnblogsSdkConfig config) {
        mConfig = config;
        mAppVersion = String.valueOf(AppUtils.getAppVersionCode());
        mAppVersionName = AppUtils.getAppVersionName();
        mClientId = DeviceUtils.getUniqueDeviceId();
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        return handlerResponse(chain, chain.proceed(prepareRequest(chain.request())));
    }

    /**
     * 准备请求阶段
     * @param request 原始请求
     * @return 新的请求
     */
    @NonNull
    private Request prepareRequest(Request request) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String token = this.getToken();
        Request.Builder builder = request.newBuilder();
        builder.addHeader("Authorization", "Bearer " + token);
        builder.addHeader("App-Version", mAppVersion);
        builder.addHeader("App-VersionName", mAppVersionName);
        builder.addHeader("Client-Id", mClientId);
        builder.addHeader("Client-Timestamp", timestamp);
        builder.addHeader("Client-Ticket", makeRequestTicket(request, timestamp));
        return builder.build();
    }

    /**
     * 响应处理
     * @param response 服务器响应
     * @return 响应结果
     */
    @NonNull
    private Response handlerResponse(Chain chain, Response response) throws IOException {
        if (response.isSuccessful()) {
            return response;
        }
        // 错误处理
        try {
            String content = BufferUtils.copyBufferBody(response.body());
            if (content == null) {
                return response;
            }
            JSONObject object = new JSONObject(content);
            int code = object.getInt("code");
            String message = object.getString("message");
            final int expiredCode = 403;
            if (code == expiredCode) {
                mConfig.removeToken();
                refreshToken();
                // 重新请求
                chain.proceed(chain.request());
            }
            throw new CnblogsSdkIOException(message, code, response.code());
        } catch (JSONException e) {
            CnblogsLogger.e("数据解析异常", e);
        }

        return response;
    }

    /**
     * 获取TOKEN
     */
    private String getToken() {
        String token = mConfig.getToken();
        return TextUtils.isEmpty(token) ? CnblogsEncrypt.md5("guest").toLowerCase() : token;
    }

    /**
     * 创建请求的访问票据
     * @param request 请求
     * @return 票据
     */
    private String makeRequestTicket(Request request, String timestamp) {
        Map<String, String> map = new TreeMap<>();
        // GET
        for (String name : request.url().queryParameterNames()) {
            map.put(name, request.url().queryParameter(name));
        }
        // POST
        RequestBody requestBody = request.body();
        if (requestBody instanceof FormBody) {
            FormBody body = (FormBody) requestBody;
            for (int i = 0; i < body.size(); i++) {
                String name = body.name(i);
                String value = body.value(i);
                map.put(name, value);
            }
        }
        map.put("timestamp", timestamp);
        map.put("clientId", mClientId);

        HttpUrl.Builder builder = new HttpUrl.Builder();
        builder = builder.scheme("https").host("cnblogs.com");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        // 生成票据
        return CnblogsEncrypt.encrypt(builder.build().query()).toLowerCase();
    }

    /**
     * 刷新Token
     */
    private void refreshToken() {
        TokenInfo tokenInfo = CnblogsSdk.getInstance().getDataProvider().getAppTokenDataApi().requestAppToken().blockingFirst();
        mConfig.setToken(tokenInfo.token);
    }

}
