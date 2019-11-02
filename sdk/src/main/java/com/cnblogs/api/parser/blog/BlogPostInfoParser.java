package com.cnblogs.api.parser.blog;

import com.cnblogs.api.http.IHtmlParser;
import com.cnblogs.api.model.AuthorBean;
import com.cnblogs.api.model.BlogPostInfoBean;

import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * 博客帖子信息解析
 * 主要解析推荐数和作者头像
 */
public class BlogPostInfoParser implements IHtmlParser<BlogPostInfoBean> {

    @Override
    public BlogPostInfoBean parse(Document document) throws IOException {
        BlogPostInfoBean m = new BlogPostInfoBean();
        m.setAuthor(new AuthorBean());
        m.getAuthor().setAvatar("https:" + document.select(".author_avatar").attr("src"));
        m.setDiggCount(document.select("#digg_count").text());
        return m;
    }
}
