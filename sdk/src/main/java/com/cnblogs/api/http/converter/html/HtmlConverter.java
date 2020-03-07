package com.cnblogs.api.http.converter.html;

import androidx.annotation.NonNull;

import com.cnblogs.api.http.IHtmlParser;

import org.jsoup.Jsoup;

import java.io.IOException;

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
//        CLog.d("HTTP响应内容：");
//        CLog.d(html);
        // 交给自定义的解析器执行解析
        return mParser.parse(Jsoup.parse(html));
    }
}
