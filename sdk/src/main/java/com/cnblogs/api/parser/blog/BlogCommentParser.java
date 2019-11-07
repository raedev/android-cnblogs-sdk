package com.cnblogs.api.parser.blog;

import android.text.TextUtils;

import com.cnblogs.api.http.IHtmlParser;
import com.cnblogs.api.model.AuthorBean;
import com.cnblogs.api.model.CommentBean;
import com.cnblogs.api.parser.ParseUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlogCommentParser implements IHtmlParser<List<CommentBean>> {

    @Override
    public List<CommentBean> parse(Document document) {
        List<CommentBean> result = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();

        // 解析XML
        Elements elements = document.select(".blog_comment_body");
        if (elements.size() <= 0) return result; // 没有评论
        for (Element body : elements) {
            CommentBean m = new CommentBean();
            m.setAuthor(new AuthorBean());
            result.add(m);

            m.setId(ParseUtils.getNumber(body.attr("id")));
            // 作者节点
            Element author = document.selectFirst("#a_comment_author_" + m.getId());
            m.getAuthor().setName(author.text().trim());
            m.getAuthor().setId(ParseUtils.getBlogApp(author.attr("href")));
            m.getAuthor().setAvatar(document.select("#comment_" + m.getId() + "_avatar").text());

            // 解析引用的评论
            String quoteContent = body.select(".comment_quote").text().replace("引用", "");
            nodes.clear();
            if (!TextUtils.isEmpty(quoteContent)) {
                m.setQuote(new CommentBean());
                m.getQuote().setContent(quoteContent);
                m.getQuote().setAuthor(new AuthorBean());
                // 移除空的节点
                for (Node childNode : body.childNodes()) {
                    if (TextUtils.isEmpty(childNode.outerHtml().trim())) continue;
                    nodes.add(childNode);
                }
                // 找引用的作者
                for (int i = 0; i < Math.min(4, nodes.size()); i++) {
                    Node node = nodes.get(i);
                    if (i == 1) m.getQuote().getAuthor().setName(node.outerHtml());
                    node.remove();
                }
            }
            m.setContent(body.text());
        }

        // 找时间
        Elements dateElements = document.select(".comment_date");
        if (dateElements.size() == elements.size()) {
            for (int i = 0; i < dateElements.size(); i++) {
                Element element = dateElements.get(i);
                CommentBean m = result.get(i);
                m.setDate(element.text());
            }
        }

        return result;
    }
}
