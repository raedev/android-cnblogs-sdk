package com.cnblogs.api.parser.blog;

import android.text.TextUtils;

import com.cnblogs.api.http.IHtmlParser;
import com.cnblogs.api.model.AuthorBean;
import com.cnblogs.api.model.BlogBean;
import com.cnblogs.api.parser.ParseUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页分类解析
 * 解析地址：https://www.cnblogs.com/
 * Created by rae on 2019-10-22.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class HomeBlogParser implements IHtmlParser<List<BlogBean>> {

    private String getAvatar(String src) {
        if (TextUtils.isEmpty(src)) {
            return null;
        }
        if (!src.startsWith("http")) {
            src = "http:" + src;
        }
        return src;
    }

    @Override
    public List<BlogBean> parse(Document document) {
        // 解析HTML
        List<BlogBean> result = new ArrayList<>();
        Elements elements = document.select(".post-item");
        String id;
        for (Element item : elements) {
            Elements element = item.select(".post-item-body");
            id = ParseUtils.getNumber(item.select(".digg-tip").attr("id"));
            if (TextUtils.isEmpty(id)) continue; // 博客ID为空不添加

            BlogBean m = new BlogBean();
            m.setAuthor(new AuthorBean());
            m.setPostId(id);
            m.setBlogId(parseBlogId(item.select("a[onclick]").attr("onclick"), id));
            m.setTitle(element.select(".post-item-title").text()); // 标题
            m.setUrl(element.select(".post-item-title").attr("href"));  // 原文链接
            m.setSummary(element.select(".post-item-summary").text()); // 摘要


            // 底部
            Elements footItems = element.select(".post-item-foot .post-meta-item");
            for (int i = 0; i < footItems.size(); i++) {
                Element footItem = footItems.get(i);
                String footText = footItem.text();
                if (i == 0) {
                    // 发布时间
                    m.setPostDate(ParseUtils.getDate(footText));
                }
                if (i == 1) {
                    // 点赞数
                    m.setLikeCount(ParseUtils.getCount(footText));
                }
                if (i == 2) {
                    // 评论
                    m.setCommentCount(ParseUtils.getCount(footText));
                }
                if (i == 3) {
                    // 阅读
                    m.setViewCount(ParseUtils.getCount(footText));
                }
            }

            m.getAuthor().setAvatar(getAvatar(element.select(".avatar").attr("src"))); // 头像地址
            m.getAuthor().setName(element.select(".post-item-author").text()); // 作者
            m.getAuthor().setId(ParseUtils.getBlogApp(element.select(".post-item-author").attr("href"))); // 博客APP

            result.add(m);
        }

        return result;
    }

    private String parseBlogId(String onclickText, String def) {
        // DiggPost('abatei',11655984,33451,1)
        String[] strings = onclickText.split(",");
        for (int i = 0; i < strings.length; i++) {
            if (i == 2) return strings[i].trim();
        }
        return def;
    }


}
