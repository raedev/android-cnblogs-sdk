package com.cnblogs.sdk.internal.parser.common;

import com.cnblogs.sdk.internal.parser.HtmlParser;
import com.cnblogs.sdk.model.Category;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 博客分类列表解析
 * @author RAE
 * @date 2022/02/15
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class CategoryParser extends HtmlParser<List<Category>> {

    @Override
    public List<Category> parseHtml(Document doc) {
        List<Category> data = new ArrayList<>();
        Elements uls = doc.select("body >ul");
        for (Element ul : uls) {
            String parentId = null;
            Elements lis = ul.select("li a");
            for (int i = 0; i < lis.size(); i++) {
                Element li = lis.get(i);
                Elements a = li.select("a");
                Category category = new Category();
                category.setUrl(a.attr("href"));
                category.setTitle(a.text().trim());
                category.setId(category.getUrl());
                category.setParentId(parentId);
                data.add(category);
                if (i == 0) {
                    // 第一个为Parent
                    parentId = category.getId();
                }
            }
        }
        return data;
    }
}
