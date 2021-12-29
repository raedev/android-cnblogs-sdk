package com.cnblogs.sdk.http;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * HTTP流工具
 * @author RAE
 * @date 2021/07/30
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class BufferUtils {
    private BufferUtils() {
    }

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
