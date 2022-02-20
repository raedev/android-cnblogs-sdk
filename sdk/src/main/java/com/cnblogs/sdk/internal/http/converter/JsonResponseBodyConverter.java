package com.cnblogs.sdk.internal.http.converter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cnblogs.sdk.internal.handler.JsonHandler;
import com.cnblogs.sdk.internal.http.Empty;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * JSON 响应体转换
 * @author RAE
 * @date 2022/02/19
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private final JsonHandler handler;
    private final String dataField;
    private final Type type;

    public JsonResponseBodyConverter(Gson gson, Type type, TypeAdapter<T> adapter, String dataField, JsonHandler handler) {
        this.gson = gson;
        this.type = type;
        this.adapter = adapter;
        this.dataField = dataField;
        this.handler = handler;
    }

    @Nullable
    @Override
    public T convert(@NonNull ResponseBody value) throws IOException {
        try {
            if (handler != null) {
                JsonReader newReader = handler.handle(gson, value, this.dataField);
                return convert(newReader);
            }
            JsonReader reader = gson.newJsonReader(value.charStream());
            return convert(reader);
        } finally {
            value.close();
        }
    }

    /**
     * copy from retrofit2.converter.gson.GsonResponseBodyConverter#convert(ResponseBody)
     */
    protected T convert(JsonReader jsonReader) throws IOException {
        // Empty 类型不解析数据
        if (type == Empty.class) {
            //noinspection unchecked
            return (T) Empty.value();
        }
        T result = adapter.read(jsonReader);
        if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
            throw new JsonIOException("JSON document was not fully consumed.");
        }
        return result;
    }
}
