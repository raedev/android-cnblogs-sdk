package com.cnblogs.api.parser.moment;

import androidx.annotation.NonNull;

import com.cnblogs.api.http.IHtmlParser;
import com.cnblogs.api.model.MomentBean;
import com.cnblogs.api.model.MomentCommentBean;
import com.cnblogs.api.parser.ParseUtils;

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

    private final MomentCommonParser mCommonParser = new MomentCommonParser();

    @Override
    public MomentBean parse(Document document) throws IOException {
        Element bodyElement = document.selectFirst("#ing_detail_body");
        List<TextNode> textNodes = document.selectFirst(".ing_detail_title").textNodes();

        MomentBean m = new MomentBean();
        m.userId = parseUserId(document);
        m.sourceContent = bodyElement.html();
        m.sourceTextContent = bodyElement.text();
        m.id = ParseUtils.getNumber(document.select("#comment_btn").attr("onclick"));
        m.nickName = document.select(".ing_item_author").text();
        m.avatar = ParseUtils.getUrl(document.select(".ing_item_face").attr("src"));
        m.blogApp = ParseUtils.getBlogApp(document.select(".ing_item_author").attr("href"));
        m.postTime = textNodes.get(Math.max(0, textNodes.size() - 1)).text().trim();
        m.isLuckyStar = mCommonParser.checkIsLuckyStar(document.select("#ing_detail_body img"));
        m.canDelete = document.select(".recycle").size() > 0;
        m.images = mCommonParser.parseImages(bodyElement);
        m.topics = mCommonParser.parseTopics(bodyElement.select("a"));
        m.links = mCommonParser.parseLinks(bodyElement.select("a"));
        m.comments = parseComments(document, m);
        m.commentCount = String.valueOf(m.comments.size());

        // 正文的获取放到最后，因为前面要对数据进行处理
        m.content = bodyElement.html();
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
            m.content = mCommonParser.parseCommentContent(element.select("bdo"));
            m.postTime = element.select(".text_green").text().trim();
            List<String> ids = ParseUtils.findMatchers("\\d+", element.select(".gray3").attr("onclick"));
            m.userId = ParseUtils.getString(ids, 2);
            m.canDelete = element.select(".recycle").size() > 0;
        }
        item.commentCount = String.valueOf(result.size());
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
}
