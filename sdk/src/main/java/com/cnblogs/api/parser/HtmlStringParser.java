package com.cnblogs.api.parser;

import com.cnblogs.api.http.IHtmlParser;

import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * 存字符串解析
 */
public class HtmlStringParser implements IHtmlParser<String> {

    @Override
    public String parse(Document document) throws IOException {
        return document.body().text();
    }
}
