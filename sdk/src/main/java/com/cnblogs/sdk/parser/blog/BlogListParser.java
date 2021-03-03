package com.cnblogs.sdk.parser.blog;

import com.cnblogs.sdk.model.ArticleInfo;
import com.cnblogs.sdk.model.ArticleType;
import com.cnblogs.sdk.model.AuthorInfo;
import com.cnblogs.sdk.parser.BaseHtmlParser;
import com.cnblogs.sdk.parser.HtmlParserUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 博客列表解析
 * @author RAE
 * @date 2021/03/01
 */
@SuppressWarnings("SpellCheckingInspection")
public final class BlogListParser extends BaseHtmlParser<List<ArticleInfo>> {

    @Override
    protected List<ArticleInfo> parseHtml(Document doc) {
        List<ArticleInfo> result = new ArrayList<>();
        Elements elements = doc.select(".post_item");
        // 临时字符串
        String temp = "";
        for (Element element : elements) {
            ArticleInfo m = new ArticleInfo();
            m.setAuthorInfo(new AuthorInfo());
            m.setType(ArticleType.BLOG.name());
            // 解析Id
            parseArticleId(element, m);
            // 没有获取到PostId
            if (m.getPostId() == null) {
                continue;
            }
            // 标题
            m.setTitle(element.select(".titlelnk").text());
            // 摘要
            m.setSummary(element.select(".post_item_summary").text());
            // 原文路径
            m.setUrl(element.select(".titlelnk").attr("href"));
            // 评论数量
            m.setCommentCount(getNumber(element.select(".article_comment").text()));
            // 阅读数量
            m.setViewCount(getNumber(element.select(".article_view").text()));
            // 点赞数量
            m.setLikeCount(getNumber(element.select(".diggnum").text()));
            // 发表时间
            parsePostDate(element, m);
            // 作者信息
            paserAuthorInfo(element, m);

            result.add(m);

        }
        return result;
    }

    /**
     * 解析Id
     * @param element 节点
     * @param m 实体
     */
    private void parseArticleId(Element element, ArticleInfo m) {
        // 解析：blogApp、postId、blogId
        // DiggPost('sheng-jie',14420239,326932,1)
        String temp = element.select(".diggit").attr("onclick");
        int start = temp.indexOf("(");
        int end = temp.lastIndexOf(")");
        if (start < 0 || end < 0) {
            return;
        }
        String[] strings = temp.substring(start + 1, end).split(",");
        for (int i = 0; i < strings.length; i++) {
            String text = strings[i].trim();
            if (i == 0) {
                m.getAuthorInfo().setBlogApp(text.replace("'", ""));
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
    private void parsePostDate(Element element, ArticleInfo m) {
        Element footElement = element.selectFirst(".post_item_foot");
        if (footElement != null) {
            for (TextNode textNode : footElement.textNodes()) {
                if (isEmpty(textNode.text())) {
                    continue;
                }
                // 解析日期
                String date = HtmlParserUtils.findDate(textNode.text());
                if (!isEmpty(date)) {
                    m.setPostDate(date);
                    break;
                }
            }
        }
    }

    /**
     * 解析作者信息
     * @param element 节点
     * @param m 实体
     */
    private void paserAuthorInfo(Element element, ArticleInfo m) {
        Element link = element.selectFirst(".post_item_foot a");
        if (link == null) {
            return;
        }
        String url = link.attr("href");
        String avatarUrl = element.select(".post_item_summary img").attr("src");
        m.getAuthorInfo().setUrl(url);
        m.getAuthorInfo().setName(link.text().trim());
        m.getAuthorInfo().setAvatar(avatarUrl);
    }
}
