package com.cnblogs.sdk.internal.handler;

import com.cnblogs.sdk.exception.CnblogsIOException;
import com.cnblogs.sdk.model.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.io.StringReader;

/**
 * 前置公共JSON处理
 * @author RAE
 * @date 2022/02/19
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class BeforeJsonHandler extends BaseJsonHandler {

    @Override
    protected void handle(Gson gson, String json) throws IOException {
        JsonReader reader = gson.newJsonReader(new StringReader(json));
        if (reader.peek() != JsonToken.BEGIN_OBJECT) {
            return;
        }
        BaseResponse response = gson.getAdapter(BaseResponse.class).read(reader);
        if (!response.isSuccessful()) {
            throw new CnblogsIOException(response.getMessage());
        }
    }
}
