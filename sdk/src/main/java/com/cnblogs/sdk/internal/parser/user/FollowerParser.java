package com.cnblogs.sdk.internal.parser.user;

import com.cnblogs.sdk.internal.parser.HtmlParser;
import com.cnblogs.sdk.internal.utils.HtmlUtils;
import com.cnblogs.sdk.model.PersonalInfo;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 关注的人解析
 * @author RAE
 * @date 2022/02/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class FollowerParser extends HtmlParser<List<PersonalInfo>> {

    @Override
    protected List<PersonalInfo> parseHtml(Document doc) throws IOException {
        List<PersonalInfo> result = new ArrayList<>();
        Elements items = doc.select(".avatar_list li");
        for (Element item : items) {
            PersonalInfo m = new PersonalInfo();
            Elements link = item.select(">a");
            m.setDisplayName(item.select(".avatar_name").text());
            m.setAvatarUrl(HtmlUtils.fixUrl(item.select(".avatar_pic img").attr("src")));
            m.setBlogUrl("https://home.cnblogs.com" + link.attr("href"));
            m.setBlogApp(HtmlUtils.findBlogApp(m.getBlogUrl()));
            m.setUserId(item.attr("id"));
            m.setRemarkName(item.select(".remark_name").text());
            m.setFollowDate(HtmlUtils.removeText(link.attr("title"), m.getDisplayName(), "关注于", "\n"));
            result.add(m);
        }
        return result;
    }
}
