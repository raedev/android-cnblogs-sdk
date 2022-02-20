package com.cnblogs.sdk.internal.parser.search;

import com.cnblogs.sdk.internal.parser.HtmlParser;
import com.cnblogs.sdk.internal.utils.HtmlUtils;
import com.cnblogs.sdk.model.ArticleInfo;
import com.cnblogs.sdk.model.PersonalInfo;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RAE
 * @date 2022/02/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public abstract class BaseSearchParser<T extends ArticleInfo> extends HtmlParser<List<T>> {

    /**
     * 创建实例
     * @return Item 实例
     */
    protected abstract T create();

    @Override
    protected List<T> parseHtml(Document doc) {
        List<T> result = new ArrayList<>();
        Elements items = doc.select(".result-item");
        if (items.size() > 0) {
            return parsePersonalHtml(result, items);
        }
        items = doc.select(".searchItem");
        for (Element item : items) {
            T m = create();
            result.add(m);
            m.setTitle(item.select(".searchItemTitle").text());
            m.setSummary(item.select(".searchCon").text());
            m.setUrl(item.select(".searchURL").text());
            // 作者信息
            Elements authorLinks = item.select(".searchItemInfo-userName >a");
            if (authorLinks.size() > 0) {
                PersonalInfo author = new PersonalInfo();
                author.setDisplayName(item.select(".searchItemInfo-userName").text());
                author.setBlogApp(HtmlUtils.findBlogApp(authorLinks.attr("href")));
                m.setAuthorInfo(author);
            }
            // 发布日期
            m.setPostDate(item.select(".searchItemInfo-publishDate").text());
            // 点赞、评论、查看数量
            m.setLikeCount(HtmlUtils.getNumber(item.select(".searchItemInfo-good").text()));
            m.setCommentCount(HtmlUtils.getNumber(item.select(".searchItemInfo-comments").text()));
            m.setViewCount(HtmlUtils.getNumber(item.select(".searchItemInfo-views").text()));
            // 其他信息
            onParseItem(m, item);
        }
        return result;
    }

    /**
     * 解析搜索个人博客的列表
     */
    protected List<T> parsePersonalHtml(List<T> result, Elements items) {
        for (Element item : items) {
            T m = create();
            result.add(m);
            m.setTitle(item.select(".result-title").text());
            m.setSummary(item.select(".result-content").text());
            m.setUrl(item.select(".result-url").text());
            Elements spans = item.select(".result-widget span");
            for (int i = 0; i < spans.size(); i++) {
                Element element = spans.get(i);
                if (i == 0) {
                    m.setPostDate(element.text());
                } else if (i == 1) {
                    m.setLikeCount(element.text());
                } else if (i == 2) {
                    m.setCommentCount(element.text());
                } else if (i == 3) {
                    m.setViewCount(element.text());
                }
            }
        }
        return result;
    }

    /**
     * 解析Item的其他数据
     * @param m Item实例
     * @param item Item节点
     */
    protected void onParseItem(T m, Element item) {
        // 空实现
    }
}
