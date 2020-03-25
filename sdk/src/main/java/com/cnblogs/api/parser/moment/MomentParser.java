package com.cnblogs.api.parser.moment;

import androidx.annotation.NonNull;

import com.cnblogs.api.http.IHtmlParser;
import com.cnblogs.api.model.MomentBean;
import com.cnblogs.api.model.MomentCommentBean;
import com.cnblogs.api.parser.ParseUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 闪存列表解析器
 * Created by ChenRui on 2017/9/25 0025 17:16.
 */
public class MomentParser implements IHtmlParser<List<MomentBean>> {

    @Override
    public List<MomentBean> parse(Document document) throws IOException {
        List<MomentBean> result = new ArrayList<>();

        Elements lis = document.select(".ing-item");

        for (Element li : lis) {
            MomentBean m = new MomentBean();
            result.add(m);

            m.id = ParseUtils.getNumber(li.select(".feed_body").attr("id"));
            m.nickName = li.select(".ing-author").text();
            m.avatar = ParseUtils.getUrl(li.select(".feed_avatar img").attr("src"));
            m.blogApp = ParseUtils.getBlogApp(li.select(".ing-author").attr("href"));
            m.postTime = li.select(".ing_time").text();
            m.detailUrl = "https://ing.cnblogs.com" + li.select(".ing_time").attr("href");
            m.sourceContent = li.select(".ing_body").html();
            m.userId = parseUserId(li);
            m.isLuckyStar = checkIsLuckyStar(li);
            m.commentCount = ParseUtils.getNumber(li.select(".ing_reply").text());
            m.comments = parseComments(li, m.id);
            m.images = parseImages(li);
            parseLinkAndTopics(m, li);

            // 正文的获取放到最后，因为前面要对数据进行处理
            m.content = li.select(".ing_body").html();
        }


        return result;
    }


    /**
     * 评论列表解析
     */
    @NonNull
    private List<MomentCommentBean> parseComments(Element li, String ingId) {
        List<MomentCommentBean> result = new ArrayList<>();
        Elements elements = li.select(".ing_comments li");
        for (Element element : elements) {
            // 过滤非评论的
            if (!element.attr("id").startsWith("comment")) {
                continue;
            }
            MomentCommentBean m = new MomentCommentBean();
            result.add(m);
            m.ingId = ingId;
            m.id = ParseUtils.getNumber(element.attr("id"));
            m.nickName = element.select("#comment_author_" + m.id).text();
            m.blogApp = ParseUtils.getBlogApp(element.select("#comment_author_" + m.id).attr("href"));
            m.content = element.select(".ing_comment").text();
            m.postTime = element.select(".ing_comment_time").text();
            m.userId = parseUserId(element);
        }
        return result;
    }

    /**
     * 是否为幸运星
     */
    private boolean checkIsLuckyStar(Element li) {
        Elements img = li.select("img");
        for (Element element : img) {
            if (element.attr("title").contains("星")) return true;
        }
        return false;
    }

    /**
     * 解析用户ID
     */
    private String parseUserId(Element li) {
        String onclick = li.select(".ing_reply").attr("onclick");
        Pattern compile = Pattern.compile("\\d+");
        Matcher matcher = compile.matcher(onclick);
        String text = null;
        while (matcher.find()) {
            text = matcher.group();
        }
        return text;
    }

    /**
     * 解析图片
     */
    private List<String> parseImages(Element li) {
        List<String> result = new ArrayList<>();
        Elements body = li.select(".ing_body");
        String content = body.html();
        // 确定为图片
        if (content.contains("#img") && content.contains("#end")) {
            // 截取字符串
            int startIndex = Math.max(0, content.indexOf("#img"));
            int endIndex = Math.min(content.length(), content.indexOf("#end"));
            String html = content.substring(startIndex, endIndex);
            Elements images = Jsoup.parse(html).select("a");
            for (Element image : images) {
                result.add(ParseUtils.getUrl(image.attr("href")));
            }
            // 去掉内容
            content = content.replace(html + "#end", "");
            body.html(content);
        }

        return result;
    }

    /**
     * 解析正文下的链接
     */
    private void parseLinkAndTopics(MomentBean m, Element li) {
        m.topics = new ArrayList<>();
        m.links = new ArrayList<>();
        Elements links = li.select(".ing_body a");
        for (Element link : links) {
            // 标签类型
            if (link.hasClass("ing_tag")) {
                m.topics.add(link.text().replace("[", "").replace("]", ""));
            } else {
                m.links.add(link.attr("href"));
            }
            // 移除标签
            link.remove();
        }
    }

}
