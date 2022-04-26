package com.cnblogs.sdk.internal.utils;

import androidx.annotation.Nullable;
import androidx.core.util.Predicate;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 工具类
 * @author RAE
 * @date 2021/12/29
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class CnblogsUtils {

    /**
     * Copy HTTP 响应流
     * @param body Http响应
     * @return 复制后的流
     */
    @Nullable
    public static String copyBufferBody(ResponseBody body) {
        if (body == null) {
            return null;
        }
        BufferedSource source = body.source();
        try {
            // Buffer the entire body.
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.getBuffer();
            Charset charset = StandardCharsets.UTF_8;
            return buffer.clone().readString(charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> boolean remove(List<T> data, Predicate<T> comparable) {
        List<T> temp = new ArrayList<>();
        for (T item : data) {
            if (comparable.test(item)) {
                temp.add(item);
            }
        }
        if (temp.size() <= 0) {
            return false;
        }
        return data.removeAll(temp);
    }

    public static RequestBody createFileBody(String name, File file) {
        return new MultipartBody.Builder()
                .addFormDataPart(name, file.getName(), RequestBody.create(file, MediaType.parse("image/" + file.getName().split("\\.")[1])))
                .build();
    }

    public static <T> void filter(List<T> data, Predicate<T> comparable) {
        List<T> temp = new ArrayList<>();
        for (T item : data) {
            if (!comparable.test(item)) {
                temp.add(item);
            }
        }
        if (temp.size() <= 0) {
            return;
        }
        data.removeAll(temp);
    }

    public static boolean isHomeCategory(String categoryId) {
        return false;
    }
}
