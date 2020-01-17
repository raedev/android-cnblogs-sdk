package com.cnblogs.api.http.converter.gson;

import com.cnblogs.api.CLog;
import com.cnblogs.api.CnblogsApiException;
import com.cnblogs.api.JsonParser;
import com.cnblogs.api.model.CnblogsJsonResult;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;

import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Converter;

/**
 * 将HTTP响应内容转换为对象
 * Created by rae on 2019-10-22.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private final JsonParser mAnnotation;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter, JsonParser annotation) {
        this.gson = gson;
        this.adapter = adapter;
        mAnnotation = annotation;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        String body = copyBufferBody(value.source());
        CLog.d("------------ JSON BODY ----------------");
        CLog.d(body);

        try {
            // 解析公共的实体
            if (mAnnotation.isDefault()) {
                TypeAdapter<JsonResult<T>> cnblogsAdapter = this.gson.getAdapter(new CnblogsTypeToken<T>());
                JsonResult<T> result = cnblogsAdapter.read(jsonReader);
                if (!result.success()) throw new CnblogsApiException(result.getMessage());
                T data = result.getData();

                // data 作为回调对象，一定不能空
                if (data == null) {
                    Type dataType = new TypeToken<T>() {
                    }.getType();
                    if (dataType == List.class) {
                        data = this.adapter.fromJson("[]");
                    } else {
                        data = this.adapter.fromJson("{}");
                    }
                }

                return data;
            }

            // 默认解析
            T data = adapter.read(jsonReader);
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new CnblogsApiException("服务器JSON数据格式错误");
            }

            return data;
        } finally {
            value.close();
        }
    }

    private String copyBufferBody(BufferedSource source) throws IOException {
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.getBuffer();
        Charset charset = Charset.forName("UTF-8");
        return buffer.clone().readString(charset);
    }


    public class JsonResult<E> extends CnblogsJsonResult {

        private E data;

        public E getData() {
            return data;
        }

        public void setData(E data) {
            this.data = data;
        }
    }

    private class CnblogsTypeToken<E> extends TypeToken<JsonResult<E>> {

    }
}