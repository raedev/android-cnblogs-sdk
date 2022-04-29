package com.cnblogs.sdk.internal.parser.user;

import androidx.swift.util.GsonUtils;

import com.cnblogs.sdk.exception.CnblogsIOException;
import com.cnblogs.sdk.internal.parser.HtmlParser;
import com.cnblogs.sdk.internal.utils.Logger;
import com.google.gson.JsonSyntaxException;

import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * 检查昵称解析
 * @author RAE
 * @date 2022/02/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class CheckDisplayNameParser extends HtmlParser<Boolean> {

    @Override
    protected Boolean parseHtml(Document doc) throws IOException {
        // 通过返回 true, 否则都是错误信息。
        // 官方返回的内容五花八门，也不搞个JSON格式统一一下。
        String text = doc.text();
        //noinspection AlibabaUndefineMagicConstant
        if ("true".equalsIgnoreCase(text)) {
            return true;
        }
        try {
            String message = GsonUtils.fromJson(text, String.class);
            throw new CnblogsIOException(message);
        } catch (JsonSyntaxException ex) {
            Logger.e("检查昵称解析结果异常", ex);
        }
        throw new CnblogsIOException(text);
    }
}
