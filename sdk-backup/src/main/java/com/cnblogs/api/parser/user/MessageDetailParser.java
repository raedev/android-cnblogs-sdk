package com.cnblogs.api.parser.user;


import com.cnblogs.api.http.IHtmlParser;
import com.cnblogs.api.model.MessageBean;
import com.cnblogs.api.parser.ParseUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 消息详情解析器
 */
public class MessageDetailParser implements IHtmlParser<List<MessageBean>> {

    @Override
    public List<MessageBean> parse(Document document) {
        List<MessageBean> result = new ArrayList<>();
        Elements elements = document.select(".bubble-wrap li");
        for (Element element : elements) {


            // 以下是解析默认的消息类型
            if (!element.id().contains("li_msg")) continue;
            MessageBean m = new MessageBean();
            String id = ParseUtils.getNumber(element.id()); // 当前消息Id
            String msgId = element.select(".span_msgId").text(); //  主消息Id
            String authorName = element.selectFirst("a").attr("title");
            String blogApp = ParseUtils.getBlogApp(element.selectFirst("a").attr("href"));
            String avatar = ParseUtils.getUrl(element.selectFirst("img").attr("src"));
            String content = element.select(".bubble-content").text();
            String date = element.selectFirst("div").text()
                    .replace(authorName, "");

            m.setId(id);
            m.setMessageId(ParseUtils.getString(msgId, id));
            m.setAvatar(avatar);
            m.setBlogApp(blogApp);
            m.setAuthorName(authorName);
            m.setContent(content);
            m.setPostDate(date);
            m.setAuthor(element.select(".fr").size() > 0);
            result.add(m);

            // 解析系统通知类型的详情
            if (element.select("*[title=系统通知]").size() > 0) {
                parseNotification(m, element);
            }

        }
        return result;
    }

    /**
     * 解析系统通知里面的内容
     */
    private void parseNotification(MessageBean m, Element element) {

        // 日期处理
        Element div = element.selectFirst("div");
        Elements divLinks = div.select("a");
        divLinks.remove();
        String text = div.text();

        // 正则表达式匹配日期
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String date = matcher.group();
            m.setPostDate(date);
        }

        Element contentElement = element.selectFirst(".bubble-content");

        List<Node> nodes = contentElement.childNodes();
        Elements links = contentElement.select("a");
        for (int i = 0; i < links.size(); i++) {
            Element item = links.get(i);
            if (item.attr("href").contains("home.cnblogs.com")) {
                m.setAuthorName(item.text());
                m.setBlogApp(ParseUtils.getBlogApp(item.attr("href")));
            }

            // 最后一条为来源地址
            if (i == links.size() - 1) {
                m.setSourceUrl(item.attr("href"));
            }
        }
        Element content = new Element("div");
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if ("hr".equals(node.nodeName())) break;
            if (i == 0) {
                m.setTitle(node.outerHtml());
                continue;
            }
            if ("br".equals(node.nodeName())) continue;
            content.appendChild(node.clone());
        }
        m.setContent(content.text());
    }
}
