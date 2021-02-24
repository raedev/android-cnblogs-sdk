package com.cnblogs.api.http;

import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * HTML解析器
 * Created by RAE on 2017/5/22 0022 0:07.
 */
public interface IHtmlParser<T> {

    /**
     * 解析HTML
     */
    T parse(Document document) throws IOException;
}
