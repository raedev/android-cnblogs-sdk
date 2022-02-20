package com.cnblogs.sdk.internal.parser.blog;

import com.cnblogs.sdk.internal.parser.HtmlParser;
import com.cnblogs.sdk.internal.utils.HtmlUtils;
import com.cnblogs.sdk.model.ArticleInfo;
import com.cnblogs.sdk.model.BlogInfo;
import com.cnblogs.sdk.model.PersonalInfo;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 博客列表解析
 * @author RAE
 * @date 2021/03/01
 */
public class BlogListParser extends HtmlParser<List<BlogInfo>> {

    @Override
    protected List<BlogInfo> parseHtml(Document doc) {
        List<BlogInfo> result = new ArrayList<>();
        Elements elements = doc.select(".post-item");
        for (Element element : elements) {
            BlogInfo m = new BlogInfo();
            m.setAuthorInfo(new PersonalInfo());
            // 解析Id
            parseBlogId(element, m);
            // 没有获取到PostId
            if (m.getPostId() == null) {
                continue;
            }
            // 标题
            m.setTitle(element.select(".post-item-title").text());
            // 摘要
            m.setSummary(element.select(".post-item-summary").text());
            // 原文路径
            m.setUrl(element.select(".post-item-title").attr("href"));
            // 点赞、评论、查看数量
            parseCount(element, m);
            // 发表时间
            parsePostDate(element, m);
            // 作者信息
            parseAuthorInfo(element, m);
            result.add(m);

        }
        return result;
    }

    /**
     * 解析数量
     */
    private void parseCount(Element element, BlogInfo m) {
        // 点赞数量
        m.setLikeCount(HtmlUtils.getNumber(element.select("#digg_control_" + m.getPostId()).select("span").text()));
        Elements links = element.select(".post-item-foot >a");
        for (Element link : links) {
            String href = link.attr("href");
            if (href.contains("#commentform")) {
                // 评论数量
                m.setCommentCount(HtmlUtils.getNumber(link.text()));
            }
            if (m.getUrl().equals(href)) {
                // 阅读数量
                m.setViewCount(HtmlUtils.getNumber(link.text()));
            }
        }
    }

    /**
     * 解析Id
     */
    private void parseBlogId(Element element, BlogInfo m) {
        // 解析：blogApp、postId、blogId
        // DiggPost('sheng-jie',14420239,326932,1)
        String temp = element.select(".post-meta-item").attr("onclick");
        int start = temp.indexOf("(");
        int end = temp.lastIndexOf(")");
        if (start < 0 || end < 0) {
            return;
        }
        String[] strings = temp.substring(start + 1, end).split(",");
        for (int i = 0; i < strings.length; i++) {
            String text = strings[i].trim();
            if (i == 0) {
                Objects.requireNonNull(m.getAuthorInfo()).setBlogApp(text.replace("'", ""));
            } else if (i == 1) {
                m.setPostId(text);
            } else if (i == 2) {
                m.setBlogId(text);
            }
        }
    }

    /**
     * 解析发布时间
     * @param element 节点
     * @param m 实体
     */
    private void parsePostDate(Element element, BlogInfo m) {
        Element footElement = element.selectFirst(".post-item-foot >span");
        String className = "post-meta-item";
        if (footElement != null && footElement.hasClass(className)) {
            String date = HtmlUtils.findDate(footElement.text());
            m.setPostDate(date);
        }
    }

    /**
     * 解析作者信息
     * @param element 节点
     * @param m 实体
     */
    private void parseAuthorInfo(Element element, ArticleInfo m) {
        Element avatar = element.selectFirst(".avatar");
        Element link = avatar == null ? null : avatar.parent();
        if (avatar == null || link == null) {
            return;
        }
        String url = link.attr("href");
        String avatarUrl = avatar.attr("src");
        PersonalInfo authorInfo = m.getAuthorInfo();
        if (authorInfo != null) {
            authorInfo.setBlogUrl(url);
            authorInfo.setDisplayName(link.text().trim());
            authorInfo.setAvatarUrl(avatarUrl);
        }
    }
}
