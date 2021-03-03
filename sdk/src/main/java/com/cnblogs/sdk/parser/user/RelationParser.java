package com.cnblogs.sdk.parser.user;

import com.cnblogs.sdk.model.ProfileInfo;
import com.cnblogs.sdk.parser.BaseHtmlParser;
import com.cnblogs.sdk.parser.HtmlParserUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 关注/粉丝列表解析
 * @author RAE
 * @date 2021/02/25
 */
public class RelationParser extends BaseHtmlParser<List<ProfileInfo>> {

    @Override
    protected List<ProfileInfo> parseHtml(Document doc) {
        List<ProfileInfo> result = new ArrayList<>();
        Elements lis = doc.select(".avatar_list li");
        for (Element li : lis) {
            ProfileInfo m = new ProfileInfo();
            m.setUserId(li.attr("id"));
            m.setDisplayName(li.select(".avatar_name").text());
            m.setRemarkName(li.select(".remark_name").text());
            m.setAvatarUrl(HtmlParserUtils.fixUrl(li.select(".avatar_pic img").attr("src")));

            Elements link = li.select("a").eq(0);
            m.setBlogApp(findBlogApp(link.attr("href")));
            // 关注时间
            String linkTitle = link.attr("title");
            int dateIndex = linkTitle.lastIndexOf("关注于");
            if (dateIndex > 0) {
                m.setFollowDate(linkTitle.substring(dateIndex + 3));
            }

            result.add(m);
        }
        return result;
    }
}
