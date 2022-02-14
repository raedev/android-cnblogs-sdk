package com.cnblogs.sdk.internal.utils;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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
}
