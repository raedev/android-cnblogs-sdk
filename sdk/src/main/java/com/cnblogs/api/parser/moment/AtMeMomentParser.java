package com.cnblogs.api.parser.moment;

import com.cnblogs.api.http.IHtmlParser;
import com.cnblogs.api.model.MomentCommentBean;
import com.cnblogs.api.parser.ParseUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 提到我的闪存
 * Created by ChenRui on 2017/9/25 0025 17:16.
 */
public class AtMeMomentParser implements IHtmlParser<List<MomentCommentBean>> {

    @Override
    public List<MomentCommentBean> parse(Document document) throws IOException {
        List<MomentCommentBean> result = new ArrayList<>();
        Elements elements = document.select("#feed_list li");
        for (Element element : elements) {
            MomentCommentBean m = new MomentCommentBean();
            result.add(m);
            m.id = ParseUtils.getNumber(element.select(".feed_body").attr("id"));
            m.avatar = ParseUtils.getUrl(element.select(".feed_avatar img").attr("src"));
            m.blogApp = ParseUtils.getBlogApp(element.select(".feed_avatar a").attr("href"));
            m.nickName = element.select("#comment_author_" + m.blogApp).text();
            m.postTime = element.select(".ing_time").text();
            String onclick = element.select(".ing_reply").attr("onclick");
            List<String> ids = ParseUtils.findMatchers("\\d+", onclick);
            m.ingId = ParseUtils.getString(ids, 0);
            m.userId = ids.size() > 3 ? ParseUtils.getString(ids, 2) : ParseUtils.getString(ids, 1);
            String parentCommentId = ids.size() > 3 ? ParseUtils.getString(ids, 1) : null;
            m.content = element.select(".ing_body").text();

            // 普通昵称
            if (element.select(".ing-author").size() > 0) {
                m.nickName = element.select(".ing-author").text();
            }

            // 在闪存提到的有引用的评论
            if (element.select(".comment-body-topline").size() > 0) {
                m.quote = new MomentCommentBean();
                m.quote.id = parentCommentId;
                m.quote.nickName = element.select(".ing_body a").eq(0).text().replace("@", "");
                m.quote.content = element.select(".comment-body-gray").text();
            }

        }

        return result;
    }
}
