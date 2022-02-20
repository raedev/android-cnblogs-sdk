package com.cnblogs.sdk.internal.parser.user;

import android.text.TextUtils;

import com.cnblogs.sdk.internal.parser.HtmlParser;
import com.cnblogs.sdk.internal.utils.HtmlUtils;
import com.cnblogs.sdk.model.PersonalInfo;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 个人中心信息解析
 * @author RAE
 * @date 2022/02/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class ProfileParser extends HtmlParser<PersonalInfo> {

    private final Pattern mPattern = Pattern.compile("=.*;");

    @Override
    protected PersonalInfo parseHtml(Document doc) {
        PersonalInfo m = new PersonalInfo();
        m.setProfileMap(new HashMap<>(10));
        parseUserInfo(m, doc);
        return m;
    }

    @SuppressWarnings("AliEqualsAvoidNull")
    protected void parseUserInfo(PersonalInfo user, Document doc) {
        // 用户ID
        user.setUserId(parseUserId(doc));
        // 头像
        user.setAvatarUrl(HtmlUtils.fixUrl(doc.select(".img_avatar").attr("src")));
        // 博客地址
        user.setBlogUrl(HtmlUtils.fixUrl(doc.select("#blog_url").attr("href")));
        // 从博客地址解析BlogApp
        user.setBlogApp(HtmlUtils.findBlogApp(user.getBlogUrl()));
        // 显示名称/昵称
        user.setDisplayName(doc.select(".display_name").text());
        // 备注名称
        user.setRemarkName(doc.select("#remarkId").text());
        // 关注数
        user.setFollowCount(HtmlUtils.parseInt(HtmlUtils.getNumber(doc.select("#following_count").text())));
        // 粉丝数
        user.setFansCount(HtmlUtils.parseInt(HtmlUtils.getNumber(doc.select("#follower_count").text())));
        // 是否关注
        user.setFollowing(doc.select("#unFollowedPanel").attr("style").contains("none"));

        // 所有信息
        Elements profileLi = doc.select("#user_profile li");
        for (Element element : profileLi) {
            String text = element.text();
            String[] kv = text.split("：");
            if (kv.length < 2) {
                continue;
            }
            String name = kv[0].trim();
            String value = kv[1].trim();
            if (name.equals("博客")) {
                continue;
            }
            //noinspection IfCanBeSwitch
            if (name.equals("自我介绍")) {
                user.setIntroduce(value);
            } else if (name.equals("园龄")) {
                user.setBlogAge(value);
            } else if (name.equals("入园")) {
                user.setJoinDate(value);
            } else if (name.equals("园号")) {
                user.setParkId(value);
            } else {
                user.getProfileMap().put(name, value);
            }
        }
    }


    /**
     * 解析用户ID
     */
    private String parseUserId(Document doc) {
        Elements scripts = doc.select("script");
        for (Element script : scripts) {
            String text = script.html();
            /*
             *  <script type="text/javascript">
             *       var currentUserId = "bac2687c-5679-e111-aa3f-842b2b196315";
             *       var currentUserName = "chenXiaorui";
             *       var isLogined = true;
             *  </script>
             * */
            if (text.contains("currentUserId")) {
                Matcher matcher = mPattern.matcher(text);
                if (matcher.find()) {
                    String group = matcher.group();
                    // 解析用户ID userId
                    return group
                            .replace("=", "")
                            .replace("\"", "")
                            .replace(";", "")
                            .replace("\n", "")
                            .trim();
                }
            }
        }
        return null;
    }

    /**
     * 解析blogApp
     * @param url URL格式：/u/{blogApp}/detail/
     */
    private String parseBlogApp(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        String[] split = url.split("/");
        int length = split.length;
        return split[Math.min(length - 1, length > 3 ? 2 : 1)];
    }
}
