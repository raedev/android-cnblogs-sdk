package com.cnblogs.api.parser.user;

import com.cnblogs.api.http.IHtmlParser;
import com.cnblogs.api.model.UserInfoBean;
import com.cnblogs.api.parser.ParseUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rae on 2020-01-01.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class UserInfoParser implements IHtmlParser<UserInfoBean> {

    @Override
    public UserInfoBean parse(Document document) throws IOException {
        UserInfoBean user = new UserInfoBean();
        user.setProfiles(new HashMap<String, String>());
        user.setDisplayName(document.select(".display_name").text());
        user.setRemarkName(document.select("#remarkId").text());
        user.setAvatar(ParseUtils.getUrl(document.select(".img_avatar").attr("src")));
        user.setBlogApp(ParseUtils.getBlogApp(document.select(".link_account").attr("href")));
        user.setFollowCount(ParseUtils.getNumber(document.select("#following_count").text()));
        user.setFansCount(ParseUtils.getNumber(document.select("#follower_count").text()));
        // 添加关注如果隐藏则是已关注
        user.setHasFollow(document.select(".follow_button").attr("style").contains("none"));

        // 个人资料信息
        Elements profiles = document.select("#user_profile li");
        for (Element profile : profiles) {
            Elements nameElement = profile.select("span").eq(0);
            String name = nameElement.text();
            nameElement.remove();
            String value = profile.text();
            user.getProfiles().put(removeSymbol(name), value);
            if (name.contains("自我介绍")) {
                user.setIntroduce(value);
            }
            if (name.contains("园龄")) {
                user.setJoinDate(value);
            }

        }

        // 解析用户Id相关
        Elements scripts = document.select("script");
        for (Element script : scripts) {
            String text = script.html();
            if (!text.contains("currentUserId")) continue;
            // 把多余的空格去掉
            text = text.replace(" ", "");
            Matcher matcher = Pattern.compile("currentUserId=.+;").matcher(text);
            if (matcher.find()) {
                String userId = removeSymbol(matcher.group())
                        .replace("currentUserId", "");
                user.setUserId(userId);
                break;
            }
        }

        return user;
    }

    /**
     * 去除符号
     */
    private String removeSymbol(String text) {
        return text.replace("\"", "")
                .replace(";", "")
                .replace("=", "")
                .replace("'", "")
                .replace("：", "");
    }
}
