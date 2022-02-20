package com.cnblogs.sdk.internal.parser.search;

import com.cnblogs.sdk.internal.parser.HtmlParser;
import com.cnblogs.sdk.internal.utils.HtmlUtils;
import com.cnblogs.sdk.model.UserInfo;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 园友搜索列表解析
 * @author RAE
 * @date 2022/02/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class UserSearchParser extends HtmlParser<List<UserInfo>> {

    @Override
    protected List<UserInfo> parseHtml(Document doc) throws IOException {
        List<UserInfo> result = new ArrayList<>();
        Elements items = doc.select(".avatar_list li");
        for (Element item : items) {
            Element link = item.selectFirst("a");
            if (link == null) {
                continue;
            }
            UserInfo m = new UserInfo();
            m.setDisplayName(item.select("p").text());
            m.setAvatarName(HtmlUtils.fixUrl(item.select("img").attr("src")));
            m.setBlogLink("https://home.cnblogs.com" + link.attr("href"));
            m.setBlogApp(HtmlUtils.findBlogApp(m.getBlogLink()));
            result.add(m);
        }
        return result;
    }
}
