package com.cnblogs.api.parser.moment;

import android.text.TextUtils;

import com.cnblogs.api.http.IHtmlParser;
import com.cnblogs.api.model.MomentCommentBean;
import com.cnblogs.api.parser.ParseUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 回复我的闪存解析器
 * Created by ChenRui on 2017/9/25 0025 17:16.
 */
public class MomentReplyParser implements IHtmlParser<List<MomentCommentBean>> {

    @Override
    public List<MomentCommentBean> parse(Document doc) throws IOException {
        List<MomentCommentBean> result = new ArrayList<>();

        if (doc.select(".comment-list").size() > 0) {
            // 解析回复我的闪存
            parseReplyMeMoment(result, doc);
        } else {
            // 解析提到我的闪存
            parseAtMoment(result, doc);
        }


        return result;
    }

    /**
     * 解析提到我的闪存
     */
    private void parseAtMoment(List<MomentCommentBean> result, Document doc) {
        Elements elements = doc.select("#feed_list li");
        for (Element element : elements) {
            MomentCommentBean m = new MomentCommentBean();
            result.add(m);
            // 评论ID
            String id = ParseUtils.getNumber(element.select(".feed_body").attr("id"));
            if (TextUtils.isEmpty(id)) continue; // 跳过ID为空的

            m.setAuthorName(element.select(".ing-author").text());
            m.setBlogApp(ParseUtils.getBlogApp(element.select(".ing-author").attr("href")));
            m.setAvatar(ParseUtils.getUrl(element.select(".feed_avatar img").attr("src")));
            m.setContent(getContentText(element));
            // 发布时间
            m.setPostTime(element.select(".ing_time").text());
            // 闪存ID
            m.setIngId(getMomentIngId(element));
        }
    }

    /**
     * 解析回复我的闪存
     */
    private void parseReplyMeMoment(List<MomentCommentBean> result, Document doc) {
        Elements elements = doc.select("#feed_list li");
        for (Element element : elements) {
            MomentCommentBean m = new MomentCommentBean();
            // 评论ID
            String id = ParseUtils.getNumber(element.select(".feed_body").attr("id"));
            if (TextUtils.isEmpty(id)) continue; // 跳过ID为空的
            // 主键
            m.setId(id);
            // 作者名称
            m.setAuthorName(element.select("#comment_author_" + id).text());
            // blogApp
            m.setBlogApp(ParseUtils.getBlogApp(element.select("#comment_author_" + id).attr("href")));
            //头像地址
            m.setAvatar(ParseUtils.getUrl(element.select(".feed_avatar a img").attr("src")));
            // 闪存内容
            m.setContent(getContentText(element));
            // 引用的内容
            m.setReferenceContent(getReferenceContent(element, m.getAuthorName()));
            // 发布时间
            m.setPostTime(element.select(".ing_time").text());
            // 闪存ID
            m.setIngId(getMomentIngId(element));
            result.add(m);
        }

    }

    /**
     * 解析闪存的IngId
     */
    private String getMomentIngId(Element element) {
        // 解析ingId
        Matcher matcher = Pattern.compile("\\d+")
                .matcher(element.select(".ing_reply").attr("onclick"));
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 获取回复内容
     */
    private String getContentText(Element element) {
        Elements elements = element.select(".ing_body");
//        if (elements.size() > 1) return elements.text();

//        Element e = elements.get(0);
//        Elements links = e.select("a");

//        // 去掉第一个@自己的文本
//        if (links.size() > 0 && links.get(0).text().contains("@")) {
//            links.get(0).remove();
//            return e.text().substring(1); // 去掉冒号
//        }

//        return e.text();
        return elements.text();
    }

    /**
     * 获取回复应用内容
     */
    private String getReferenceContent(Element element, String authorName) {
        String text = element.select(".comment-body-topline").text();
        String content = element.select(".comment-body-gray").text();
        int startIndex = text.indexOf(content);

        if (startIndex >= 0) {
            String type = text.substring(0, startIndex)
                    .replace("对", "")
                    .replace("“", "")
                    .replace(authorName, "")
                    .trim();

            return String.format("回复%s：%s", type, content.replace("@" + authorName + "：", ""));
        }

        return text;
    }
}
