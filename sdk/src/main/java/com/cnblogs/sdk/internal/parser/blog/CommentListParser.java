package com.cnblogs.sdk.internal.parser.blog;

import android.text.TextUtils;

import com.cnblogs.sdk.internal.parser.HtmlParser;
import com.cnblogs.sdk.internal.utils.HtmlUtils;
import com.cnblogs.sdk.model.CommentInfo;
import com.cnblogs.sdk.model.TextHtml;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 评论列表
 * @author RAE
 * @date 2022/03/01
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class CommentListParser extends HtmlParser<List<CommentInfo>> {

    @Override
    protected List<CommentInfo> parseHtml(Document doc) throws IOException {
        List<CommentInfo> result = new ArrayList<>();

        // 解析XML
        Elements feedbackItems = doc.select(".feedbackItem");
        for (Element item : feedbackItems) {
            // 评论Id
            Element body = item.selectFirst(".blog_comment_body");
            String id = body == null ? null : HtmlUtils.getNumber(body.attr("id"));
            if (TextUtils.isEmpty(id) || body == null) {
                continue;
            }

            CommentInfo m = new CommentInfo();
            result.add(m);
            m.setId(id);
            m.setBody(new TextHtml(body.html()));
            // 作者节点
            Elements author = doc.select("#a_comment_author_" + id);
            m.setAuthorName(author.text());
            m.setBlogApp(HtmlUtils.findBlogApp(author.attr("href")));
            m.setAvatar(item.select("#comment_" + id + "_avatar").text().replace("/face/", "/avatar/"));
            // 解析引用的评论
            Element blockquote = body.selectFirst("blockquote");
            if (blockquote != null) {
                m.setQuote(blockquote.text());
            }

            // 我自己的评论
            Elements actions = item.select(".comment_actions a");
            for (Element action : actions) {
                String onclick = action.attr("onclick");
                // onclick="return DelComment(5022008, this,'15953126') 匹配第3个参数
                if (onclick.contains("DelComment")) {
                    m.setPostId(HtmlUtils.getNumber(onclick));
                    break;
                }
            }
        }

        // 找时间
        Elements dateElements = doc.select(".comment_date");
        if (dateElements.size() == feedbackItems.size()) {
            for (int i = 0; i < dateElements.size(); i++) {
                Element element = dateElements.get(i);
                CommentInfo m = result.get(i);
                m.setDate(element.text());
            }
        }
        return result;
    }
}
