package com.cnblogs.sdk.internal.parser.blog;

import com.cnblogs.sdk.internal.parser.HtmlParser;
import com.cnblogs.sdk.model.ArticleInfo;
import com.cnblogs.sdk.model.BlogInfo;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者博客排行榜解析
 * @author RAE
 * @date 2022/03/01
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class AuthorTopListParser extends HtmlParser<List<ArticleInfo>> {

    @Override
    protected List<ArticleInfo> parseHtml(Document doc) throws IOException {
        List<ArticleInfo> result = new ArrayList<>();

        // 阅读排行
        Elements items = doc.select("#TopViewPostsBlock li");
        for (Element item : items) {
            Elements link = item.select("a");
            ArticleInfo m = new BlogInfo();
            m.setTitle(link.text());
            m.setUrl(link.attr("href"));
            m.setCategoryId("view");
            result.add(m);
        }

        // 推荐排行榜
        items = doc.select("#TopDiggPostsBlock li");
        for (Element item : items) {
            Elements link = item.select("a");
            ArticleInfo m = new BlogInfo();
            m.setTitle(link.text());
            m.setUrl(link.attr("href"));
            m.setCategoryId("digg");
            result.add(m);
        }

        return result;
    }
}
