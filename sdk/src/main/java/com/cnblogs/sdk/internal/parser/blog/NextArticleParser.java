package com.cnblogs.sdk.internal.parser.blog;

import com.cnblogs.sdk.internal.parser.HtmlParser;
import com.cnblogs.sdk.internal.utils.HtmlUtils;
import com.cnblogs.sdk.model.BlogInfo;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 上下篇文章解析
 * @author RAE
 * @date 2022/02/23
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class NextArticleParser extends HtmlParser<List<BlogInfo>> {

    @Override
    protected List<BlogInfo> parseHtml(Document doc) throws IOException {
        List<BlogInfo> data = new ArrayList<>();
        Elements elements = doc.select("a");
        for (Element element : elements) {
            if (element.hasClass("p_n_p_prefix")) {
                continue;
            }
            BlogInfo m = new BlogInfo();
            m.setTitle(element.text());
            m.setPostDate(HtmlUtils.findDate(element.attr("title")));
            m.setUrl(element.attr("href"));
            data.add(m);
        }
        return data;
    }
}
