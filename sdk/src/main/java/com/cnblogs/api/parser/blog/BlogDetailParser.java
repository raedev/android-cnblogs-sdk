package com.cnblogs.api.parser.blog;

import android.text.TextUtils;

import com.cnblogs.api.http.IHtmlParser;
import com.cnblogs.api.model.AuthorBean;
import com.cnblogs.api.model.BlogBean;
import com.cnblogs.api.parser.ParseUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 博客详情解析
 */
public class BlogDetailParser implements IHtmlParser<BlogBean> {

    @SuppressWarnings("deprecation")
    @Override
    public BlogBean parse(Document document) {
        BlogBean m = new BlogBean();
        m.setAuthor(new AuthorBean());
        m.setPostId(parsePostId(document)); // postId
        m.setBlogId(parseBlogId(document.html())); // blogId
        m.setTitle(document.select("#cb_post_title_url").text()); // 标题
        m.setSummary(parseSummary(document)); // 摘要
        m.setPostDate(document.select("#post-date").text()); // 发表时间
        m.setContent(document.select("#cnblogs_post_body").html()); // 博文内容
        m.setUrl(document.select("#cb_post_title_url").attr("href")); // 原文链接
        if (document.select("#Header1_HeaderTitle").size() > 0) {
            m.getAuthor().setId(ParseUtils.getBlogApp(document.select("#Header1_HeaderTitle").attr("href"))); // blogApp
        } else {
            m.getAuthor().setId(ParseUtils.getBlogApp(document.select("#blog_nav_myhome").attr("href"))); // blogApp
        }
        m.getAuthor().setName(parseAuthorName(document, m.getAuthor().getId())); // 作者昵称
        m.getAuthor().setUserId(parseUserGuid(document.html())); // 用户ID

        return m;
    }


    /**
     * 摘要
     */
    private String parseSummary(Document document) {
        String text = document.select("#cnblogs_post_body").text();
        return text.substring(0, Math.min(text.length(), 200));
    }

    /**
     * 作者昵称
     */
    private String parseAuthorName(Document document, String blogApp) {
        Elements elements = document.select(".postDesc a[href]");
        for (Element element : elements) {
            String href = element.attr("href");
            if (!TextUtils.isEmpty(blogApp) && href.contains(blogApp)) return element.text().trim();
        }
        return "";
    }

    /**
     * 解析PostId
     */
    private String parsePostId(Document document) {
        Elements elements = document.select(".postDesc a[onclick]");
        for (Element element : elements) {
            String attr = element.attr("onclick");
            // 收藏的按钮含有PostID
            // <a href="javascript:void(0)" onclick="AddToWz(11665021); return false;">收藏</a>
            if (attr.contains("AddToWz")) {
                return ParseUtils.getNumber(attr);
            }
        }
        return "0";
    }

    // 解析 blog id
    private String parseBlogId(String text) {
        Pattern pattern = Pattern.compile("(currentBlogId).+");
        Matcher matcher = pattern.matcher(text);
        if (!matcher.find()) return null;
        return ParseUtils.getNumber(matcher.group());
    }

    /**
     * 解析用户的GUID
     *
     * @param text 全局HTML
     */
    private String parseUserGuid(String text) {
        Pattern pattern = Pattern.compile("(cb_blogUserGuid).+");
        Matcher matcher = pattern.matcher(text);
        if (!matcher.find()) return null;
        return matcher.group().replace("cb_blogUserGuid", "")
                .replace(" ", "")
                .replace("=", "")
                .replace("'", "")
                .replace(";", "")
                .trim();
    }
}
