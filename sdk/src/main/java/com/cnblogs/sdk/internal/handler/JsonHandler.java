package com.cnblogs.sdk.internal.handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Json处理器，返回的JSON将先经过该接口后继续调用原始的JSON解析方法
 * @author RAE
 * @date 2022/02/19
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface JsonHandler {

    /**
     * 处理JSON数据
     * @param gson GSON
     * @param body body
     * @param dataField 数据字段
     * @return 返回新的将替换原始的，返回空则不处理
     * @throws IOException IO异常
     */
    @NonNull
    JsonReader handle(@NonNull Gson gson, @NonNull ResponseBody body, @Nullable String dataField) throws IOException;

}
