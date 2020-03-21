package com.cnblogs.api.parser.moment;

import androidx.annotation.NonNull;

import com.cnblogs.api.http.IHtmlParser;
import com.cnblogs.api.model.MomentBean;
import com.cnblogs.api.model.MomentCommentBean;
import com.cnblogs.api.parser.ParseUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 闪存详情
 * Created by ChenRui on 2017/9/25 0025 17:16.
 */
public class MomentDetailParser implements IHtmlParser<MomentBean> {

    @Override
    public MomentBean parse(Document document) throws IOException {

        MomentBean m = new MomentBean();
        m.id = ParseUtils.getNumber(document.select("#comment_btn").attr("onclick"));
        m.nickName = document.select(".ing_item_author").text();
        m.avatar = ParseUtils.getUrl(document.select(".ing_item_face").attr("src"));
        m.blogApp = ParseUtils.getBlogApp(document.select(".ing_item_author").attr("href"));
        List<TextNode> textNodes = document.selectFirst(".ing_detail_title").textNodes();
        m.postTime = textNodes.get(Math.max(0, textNodes.size() - 1)).text().trim();
        m.sourceContent = document.select("#ing_detail_body").html();
        m.userId = parseUserId(document);
        m.isLuckyStar = checkIsLuckyStar(document);
        m.comments = parseComments(document, m);
        m.images = parseImages(document);
        m.topics = parseTopics(document);

        // 正文的获取放到最后，因为前面要对数据进行处理
        m.content = document.select("#ing_detail_body").html();
        return m;
    }


    /**
     * 评论列表解析
     */
    @NonNull
    private List<MomentCommentBean> parseComments(Element li, MomentBean item) {
        List<MomentCommentBean> result = new ArrayList<>();
        String ingId = item.id;
        Elements elements = li.select("#comment_block_" + item.id + " li");
        for (Element element : elements) {
            MomentCommentBean m = new MomentCommentBean();
            result.add(m);
            m.ingId = ingId;
            m.id = ParseUtils.getNumber(element.attr("id"));
            m.nickName = element.select("#comment_author_" + m.id).text();
            m.avatar = ParseUtils.getUrl(element.select(".ing_comment_face").attr("src"));
            m.blogApp = ParseUtils.getBlogApp(element.select("#comment_author_" + m.id).attr("href"));
            m.content = element.select("bdo").text();
            m.postTime = element.select(".text_green").text().trim();
            List<String> ids = ParseUtils.findMatchers("\\d+", element.select(".gray3").attr("onclick"));
            m.userId = ParseUtils.getString(ids, 2);
        }
        item.commentCount = String.valueOf(result.size());
        return result;
    }

    /**
     * 是否为幸运星
     */
    private boolean checkIsLuckyStar(Element li) {
        Elements img = li.select("#ing_detail_body img");
        boolean result = false;
        for (Element element : img) {
            if (element.attr("title").contains("星")) {
                result = true;
                // 移除图片
                element.remove();
            }
        }
        return result;
    }

    /**
     * 解析用户ID
     */
    private String parseUserId(Element li) {
        String onclick = li.select("script").html();
        Pattern compile = Pattern.compile("replyToSpaceUserId.*\\d+");
        Matcher matcher = compile.matcher(onclick);
        String text = "";
        if (matcher.find()) {
            text = matcher.group();
        }
        return ParseUtils.getNumber(text);
    }

    /**
     * 解析图片
     */
    private List<String> parseImages(Element li) {
        List<String> result = new ArrayList<>();
        Elements body = li.select("#ing_detail_body");
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
    private List<String> parseTopics(Element li) {
        List<String> result = new ArrayList<>();
        Elements links = li.select("#ing_detail_body a");
        for (Element link : links) {
            // 标签类型
            if (link.hasClass("ing_tag")) {
                result.add(link.text().replace("[", "").replace("]", ""));
            }
            // 移除标签
            link.remove();
        }
        return result;
    }

}
