package com.cnblogs.api.parser;

import com.cnblogs.api.http.IHtmlParser;

import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * 直接返回网页源文
 */
public class HtmlSourceParser implements IHtmlParser<String> {

    @Override
    public String parse(Document document) throws IOException {
        return document.body().text();
    }
}
