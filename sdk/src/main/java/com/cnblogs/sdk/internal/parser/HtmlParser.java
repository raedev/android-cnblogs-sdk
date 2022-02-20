package com.cnblogs.sdk.internal.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * HTML解析器，将HTML代码转换成对象
 * @author RAE
 * @date 2022/02/15
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@SuppressWarnings("AlibabaAbstractClassShouldStartWithAbstractNaming")
public abstract class HtmlParser<T> {

    private String mSourceHtml;

    protected String getHtml() {
        return mSourceHtml;
    }

    /**
     * 解析HTML并转成对象
     * @param html 服务器返回的HTML
     * @return 对象
     */
    public T parseHtml(String html) throws IOException {
        mSourceHtml = html;
        return parseHtml(Jsoup.parse(html));
    }

    /**
     * 解析HTML并转成对象
     * @param doc HTML 文档
     * @return 对象
     * @throws IOException 抛IO异常
     */
    protected abstract T parseHtml(Document doc) throws IOException;
}
