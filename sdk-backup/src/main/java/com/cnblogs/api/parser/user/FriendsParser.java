package com.cnblogs.api.parser.user;


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
 * 粉丝以及关注用户列表解析器
 * Created by ChenRui on 2017/2/7 0007 17:41.
 */
public class FriendsParser implements IHtmlParser<List<UserInfoBean>> {

    @Override
    public List<UserInfoBean> parse(Document document) throws IOException {
        List<UserInfoBean> result = new ArrayList<>();
        Elements elements = document.select(".avatar_list li");
        for (Element li : elements) {
            UserInfoBean m = new UserInfoBean();
            result.add(m);

            // 头像
            String avatarPath = ".avatar48 img"; // 搜索的
            if (li.select(avatarPath).size() <= 0) {
                avatarPath = ".avatar_pic img"; // 默认的
            }
            m.setAvatar(ParseUtils.getUrl(li.select(avatarPath).attr("src")));
            m.setUserId(li.attr("id"));
            m.setBlogApp(ParseUtils.getBlogApp(li.select("a[title]").attr("href")));
            m.setDisplayName(li.select("a[title]").attr("title"));
            m.setRemarkName(li.select(".remark_name a").eq(1).text());
            m.setHasFollow(li.select(".edit").text().contains("取消"));
        }
        return result;
    }
}
