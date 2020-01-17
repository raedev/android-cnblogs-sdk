package com.cnblogs.api.parser.user;

import com.cnblogs.api.http.IHtmlParser;
import com.cnblogs.api.parser.ParseUtils;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by rae on 2020-01-01.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class CurrentUserInfoParser implements IHtmlParser<String> {

    @Override
    public String parse(Document document) throws IOException {
        Elements link = document.select("#header_user_right a").eq(0);
        return ParseUtils.getBlogApp(link.attr("href"));
    }
}
