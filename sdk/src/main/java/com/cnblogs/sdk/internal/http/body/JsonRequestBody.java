package com.cnblogs.sdk.internal.http.body;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.raedev.swift.utils.JsonUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * JSON 请求参数
 * @author RAE
 * @date 2022/02/17
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class JsonRequestBody extends RequestBody {

    public static <T> JsonRequestBody create(T object) {
        return new JsonRequestBody() {
            @Override
            public void writeTo(@NonNull BufferedSink sink) throws IOException {
                writeObject(object, sink);
            }
        };
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return MediaType.parse("application/json; charset=UTF-8");
    }

    @Override
    public void writeTo(@NonNull BufferedSink sink) throws IOException {
        writeObject(this, sink);
    }

    protected void writeObject(Object obj, BufferedSink sink) throws IOException {
        sink.writeString(JsonUtils.toJson(obj), StandardCharsets.UTF_8);
    }
}
