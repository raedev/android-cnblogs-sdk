package com.cnblogs.sdk.internal.handler;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cnblogs.sdk.exception.CnblogsIOException;
import com.cnblogs.sdk.internal.utils.Logger;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.ResponseBody;

/**
 * @author RAE
 * @date 2022/02/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
abstract class BaseJsonHandler implements JsonHandler {

    @NonNull
    @Override
    public JsonReader handle(@NonNull Gson gson, @NonNull ResponseBody body, @Nullable String dataField) throws IOException {
        String json = body.string();
        try {
            handle(gson, json);
            // 字段不为空的时候解析
            if (!TextUtils.isEmpty(dataField) && dataField != null) {
                JSONObject object = new JSONObject(json);
                if (object.has(dataField)) {
                    json = object.getString(dataField);
                } else {
                    Logger.w("JSON处理器找不到解析的数据字段：" + dataField + "，返回数据：\n" + json);
                }
            }
        } catch (JSONException e) {
            throw new CnblogsIOException("数据解析错误");
        }

        return new JsonReader(new StringReader(json));
    }

    /**
     * 解析JSON
     * @param gson GSON
     * @param json 数据
     * @throws IOException IOException
     * @throws JSONException JSONException
     */
    protected abstract void handle(Gson gson, String json) throws IOException, JSONException;
}
