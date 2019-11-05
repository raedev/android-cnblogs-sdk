package com.cnblogs.api.parser.blog;

import com.cnblogs.api.http.IHtmlParser;
import com.cnblogs.api.model.ArticleBean;
import com.cnblogs.api.parser.ParseUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 下一篇文章解析器
 * Created by ChenRui on 2016/11/30 00:13.
 */
public class NextArticleParser implements IHtmlParser<List<ArticleBean>> {


    @Override
    public List<ArticleBean> parse(Document document) throws IOException {
        List<ArticleBean> data = new ArrayList<>();
        Elements elements = document.select("a");
        for (Element element : elements) {
            if (element.hasClass("p_n_p_prefix")) continue;
            ArticleBean m = new ArticleBean();
            m.setTitle(element.text());
            m.setPostDate(ParseUtils.getDate(element.attr("title")));
            m.setUrl(element.attr("href"));
            data.add(m);
        }
        return data;
    }
}
