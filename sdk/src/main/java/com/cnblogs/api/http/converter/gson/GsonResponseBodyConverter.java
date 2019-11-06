package com.cnblogs.api.http.converter.gson;

import com.cnblogs.api.CnblogsApiException;
import com.cnblogs.api.JsonParser;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;

import okhttp3.ResponseBody;
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
        try {
            // 解析公共的实体
            if (mAnnotation.isDefault()) {
                TypeAdapter<CnblogsJsonResult<T>> cnblogsAdapter = this.gson.getAdapter(new CnblogsTypeToken<T>());
                CnblogsJsonResult<T> result = cnblogsAdapter.read(jsonReader);
                if (result.success) return result.data;
                throw new CnblogsApiException(result.message);
            }

            // 默认解析
            T result = adapter.read(jsonReader);
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new CnblogsApiException("JSON document was not fully consumed.");
            }
            return result;
        } finally {
            value.close();
        }
    }


    class CnblogsJsonResult<E> {

        @SerializedName("isSuccess")
        public boolean success;

        @SerializedName(value = "message", alternate = {"msg"})
        public String message;

        public E data;

    }

    class CnblogsTypeToken<E> extends TypeToken<CnblogsJsonResult<E>> {

    }
}