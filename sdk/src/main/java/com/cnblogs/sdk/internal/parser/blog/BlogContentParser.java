package com.cnblogs.sdk.internal.parser.blog;

import com.cnblogs.sdk.internal.parser.HtmlParser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Objects;

/**
 * 博客详情解析
 * @author RAE
 * @date 2022/02/17
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class BlogContentParser extends HtmlParser<String> {

    @Override
    protected String parseHtml(Document doc) {
        Element element = doc.selectFirst("#cnblogs_post_body");
        return Objects.requireNonNull(element, "博客内容解析为空").html();
    }
}
