package com.cnblogs.api.http.converter.html;

import androidx.annotation.NonNull;

import com.cnblogs.api.CnblogsApiException;
import com.cnblogs.api.http.IHtmlParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by rae on 2019-10-22.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
class HtmlConverter<T> implements Converter<ResponseBody, T> {

    private final IHtmlParser<T> mParser;

    HtmlConverter(IHtmlParser<T> parser) {
        mParser = parser;
    }

    @Override
    @NonNull
    public T convert(@NonNull ResponseBody value) throws IOException {
        String html = value.string();
        // 交给自定义的解析器执行解析
        Document document = Jsoup.parse(html);
        // 登录状态判断
        if (isNotLogin(document, html)) {
            throw new CnblogsApiException(CnblogsApiException.ERROR_LOGIN_EXPIRED, "登录过期，请重新登录");
        }

        return mParser.parse(document);
    }

    /**
     * 是否还没登录
     */
    private boolean isNotLogin(Document document, String html) {
        if (Pattern.compile("请先.+登录").matcher(html).find()) return true;
        return document.title().startsWith("用户登录");
    }
}
