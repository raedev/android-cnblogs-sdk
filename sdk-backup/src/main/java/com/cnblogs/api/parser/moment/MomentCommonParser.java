package com.cnblogs.api.parser.moment;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.cnblogs.api.parser.ParseUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 闪存共同的解析
 * Created by rae on 2020/4/15.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
class MomentCommonParser {

    /**
     * 评论内容
     */
    String parseCommentContent(Elements elements) {
        Elements links = elements.select("a");
        for (Element link : links) {
            handleMentionLink(link);
        }

        // 去除@之后的冒号
        Element contentElement = elements.first();
        List<TextNode> textNodes = contentElement.textNodes();
        if (textNodes.size() > 0) {
            TextNode textNode = textNodes.get(0);
            String text = textNode.text();
            if (text.startsWith("：")) {
                textNode.text(text.replaceFirst("：", " "));
            }
        }

        return elements.html();
    }

    /**
     * 解析正文下的链接
     */
    List<String> parseLinks(Elements linkElements) {
        List<String> result = new ArrayList<>();
        for (Element link : linkElements) {
            String url = link.attr("href").trim();
            String title = link.attr("title").trim();
            String text = link.text();
            //  url = title 的时候认为是存链接
            if (TextUtils.equals(url, title)) {
                result.add(url);
            }

            // 提到用户的链接处理
            if (text.startsWith("@") && url.contains("home.cnblogs.com")) {
                handleMentionLink(link);
            }
        }
        return result;
    }

    private void handleMentionLink(Element link) {
        String text = link.text();
        String url = link.attr("href");
        if (text.startsWith("@")) {
            link.clearAttributes();
            link.attr("href", "id:" + ParseUtils.getBlogApp(url));
        }
    }

    /**
     * 解析标签
     */
    @NonNull
    List<String> parseTopics(Elements links) {
        List<String> result = new ArrayList<>();
        for (Element link : links) {
            // 标签类型
            if (link.hasClass("ing_tag")) {
                result.add(link.text()
                        .replace("[", "")
                        .replace("]", "")
                );
                // 移除标签
                link.remove();
            }
        }
        return result;
    }


    /**
     * 解析图片
     */
    List<String> parseImages(Element body) {
        List<String> result = new ArrayList<>();
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

        // 解析规范的HTML img标签
        Elements images = body.select("img");
        for (Element img : images) {
            String url = ParseUtils.getUrl(img.attr("src"));
            String desc = img.attr("alt");
            if (!desc.contains("新人") && !desc.contains("星")) {
                result.add(url);
            }
        }
        // 移除所有的img标签
        images.remove();

        return result;
    }

    /**
     * 是否为幸运星
     */
    boolean checkIsLuckyStar(Elements imageElements) {
        for (Element element : imageElements) {
            String title = element.attr("title");
            if (title.contains("星") || title.contains("幸运")) {
                element.remove(); // 移除幸运星图片
                return true;
            }
        }
        return false;
    }

}
