package com.cnblogs.api.parser.moment;

import com.cnblogs.api.http.IHtmlParser;
import com.cnblogs.api.model.UserInfoBean;
import com.cnblogs.api.parser.ParseUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 今日闪存明星解析
 * Created by rae on 2020/3/19.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class LuckyStarRankingParser implements IHtmlParser<List<UserInfoBean>> {

    @Override
    public List<UserInfoBean> parse(Document document) throws IOException {
        List<UserInfoBean> result = new ArrayList<>();
        Elements lis = document.select(".avatar_list").eq(1).select("li");
        for (Element li : lis) {
            UserInfoBean m = new UserInfoBean();
            result.add(m);
            m.setDisplayName(li.select(".user_name_block").text());
            m.setAvatar(ParseUtils.getUrl(li.select("img").attr("src")));
            m.setBlogApp(ParseUtils.getBlogApp(li.select(".user_name_block a").attr("href")));
        }
        return result;
    }
}
