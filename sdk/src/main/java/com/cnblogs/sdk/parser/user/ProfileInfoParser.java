package com.cnblogs.sdk.parser.user;

import com.cnblogs.sdk.internal.CnblogsLogger;
import com.cnblogs.sdk.model.ProfileInfo;
import com.cnblogs.sdk.model.UserGroupInfo;
import com.cnblogs.sdk.parser.BaseHtmlParser;
import com.cnblogs.sdk.parser.HtmlParserUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 个人中心信息解析
 * @author RAE
 * @date 2021/02/25
 */
public class ProfileInfoParser extends BaseHtmlParser<ProfileInfo> {

    final Pattern mUserPattern = Pattern.compile("currentUserId.+;");

    @Override
    protected ProfileInfo parseHtml(Document doc) {
        ProfileInfo result = new ProfileInfo();
        result.setProfileMap(new HashMap<>(2));
        result.setDisplayName(doc.select(".display_name").text());
        result.setAvatarUrl(HtmlParserUtils.fixUrl(doc.select(".img_avatar").attr("src")));
        result.setRemarkName(doc.select("#remarkId").text());
        result.setFollowCount(parseInt(doc.select("#following_count").text()));
        result.setFansCount(parseInt(doc.select("#follower_count").text()));
        result.setBlogUrl(doc.select("#blog_url").attr("href"));
        result.setBlogApp(doc.select("#relation").attr("data-alias"));

        // 用户ID
        Elements scripts = doc.select("script");
        for (Element script : scripts) {
            String scriptText = script.html();
            Matcher matcher = mUserPattern.matcher(scriptText);
            if (matcher.find()) {
                String text = matcher.group();
                int startIndex = text.indexOf("\"");
                int endIndex = text.lastIndexOf("\"");
                if (startIndex > 0 && endIndex > 0) {
                    String userId = text.substring(startIndex + 1, endIndex);
                    result.setUserId(userId);
                    CnblogsLogger.d("找到了：" + userId);
                }
            }
        }

        // 是否关注
        Elements unFollowedPanel = doc.select("#unFollowedPanel");
        if (unFollowedPanel.size() > 0) {
            String style = unFollowedPanel.attr("style");
            result.setFollowing(style != null && style.contains("none"));
        }

        // 动态属性
        Elements profileLis = doc.select("#user_profile li");
        for (Element li : profileLis) {
            List<Node> children = li.childNodes();
            if (children.size() < 2) {
                continue;
            }
            Node titleNode = children.get(0);
            Node valueNode = children.get(1);
            String title = titleNode.toString();
            String value = valueNode.toString();
            if (titleNode instanceof Element) {
                title = ((Element) titleNode).text();
            }
            if (valueNode instanceof Element) {
                value = ((Element) valueNode).text();
            } else if (valueNode instanceof TextNode) {
                value = ((TextNode) valueNode).text();
            }
            title = HtmlParserUtils.removeText(title, "：");
            value = HtmlParserUtils.removeText(value, "：");
            result.getProfileMap().put(title, value);
            if (HtmlParserUtils.containsText(title, "园龄")) {
                result.setBlogAge(value);
            }
        }

        // 所在分组
        Elements groupList = doc.select("#groupList li");
        if (groupList.size() > 0) {
            result.setGroups(new ArrayList<>());
            for (Element element : groupList) {
                UserGroupInfo groupInfo = new UserGroupInfo();
                groupInfo.setName(element.text());
                groupInfo.setGroupId(HtmlParserUtils.removeText(element.select("a").attr("href"), "/followees/"));
                result.getGroups().add(groupInfo);
            }
        }

        return result;
    }
}
