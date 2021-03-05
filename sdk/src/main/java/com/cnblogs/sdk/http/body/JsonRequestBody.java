package com.cnblogs.sdk.http.body;

import com.cnblogs.sdk.api.ApiConstant;
import com.cnblogs.sdk.exception.CnblogsSdkException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * @author RAE
 * @date 2021/03/01
 */
public abstract class JsonRequestBody extends RequestBody {

    /**
     * 转换成JSON
     * @return json字符串
     * @throws Exception 异常
     */
    public abstract String toJson() throws Exception;

    @Nullable
    @Override
    public MediaType contentType() {
        return MediaType.parse(ApiConstant.CONTENT_TYPE_JSON);
    }

    @Override
    public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {
        try {
            String json = toJson();
            if (json == null) {
                throw new CnblogsSdkException("Json类型请求参数不能为空");
            }
            bufferedSink.writeString(json, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
