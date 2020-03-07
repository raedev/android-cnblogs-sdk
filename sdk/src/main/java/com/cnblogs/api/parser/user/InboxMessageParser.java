package com.cnblogs.api.parser.user;


import com.cnblogs.api.http.IHtmlParser;
import com.cnblogs.api.model.MessageBean;
import com.cnblogs.api.parser.ParseUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 收件箱解析器
 */
public class InboxMessageParser implements IHtmlParser<List<MessageBean>> {

    @Override
    public List<MessageBean> parse(Document document) throws IOException {
        List<MessageBean> result = new ArrayList<>();
        Elements elements = document.select(".group_topic_block tr");
        for (Element element : elements) {
            if (!element.id().contains("msg_item")) continue;
            String id = ParseUtils.getNumber(element.id());
            String authorName = element.select("td").eq(0).text();
            String content = element.select("td").eq(1).text();
            String date = element.select("td").eq(2).text();

            if (content.startsWith("RE：")) {
                content = content.replace("RE：", "[回复] ");
            }

            MessageBean m = new MessageBean();
            m.setId(id);
            m.setAuthorName(authorName);
            m.setContent(content);
            m.setPostDate(date);
            result.add(m);

        }
        return result;
    }
}
